package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualTrigger;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;
import java.util.function.Function;

public record SpellComponentCastRitualTrigger(ISpellComponent component) implements RitualTrigger {
    public static final Codec<SpellComponentCastRitualTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.forRegistry(AMRegistries.SPELL_PART_REGISTRY).comapFlatMap(iSpellPart -> iSpellPart.getType() == ISpellPart.SpellPartType.COMPONENT ? DataResult.success(((ISpellComponent) iSpellPart)) : DataResult.error("Not a spell component"), Function.identity()).fieldOf("component").forGetter(SpellComponentCastRitualTrigger::component)
    ).apply(inst, SpellComponentCastRitualTrigger::new));

    @Override
    public void register(Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((SpellEvent.Cast.Component evt) -> {
            if (evt.getEntity() instanceof Player player && player.getLevel() instanceof ServerLevel level && evt.getComponent() == component) {
                HitResult target = evt.getTarget();
                if (target == null) return;
                BlockPos pos = switch (target.getType()) {
                    case MISS -> null;
                    case BLOCK -> ((BlockHitResult) target).getBlockPos();
                    case ENTITY -> ((EntityHitResult) target).getEntity().blockPosition();
                };
                if (pos == null) return;
                Entity entity = target.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) target).getEntity() : null;
                Map<String, Object> context = Map.of(
                        "spell", evt.getSpell(),
                        "component", evt.getComponent(),
                        "modifiers", evt.getModifiers(),
                        "caster", player,
                        "entity", entity);
                if (ritual.perform(player, level, pos, new Context.MapContext(context))) {
                    evt.setCanceled(true);
                }
            }
        });
    }

    @Override
    public boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx) {
        return false;
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
