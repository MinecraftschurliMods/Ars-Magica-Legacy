package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualTrigger;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public record SpellComponentCastRitualTrigger(List<ISpellComponent> components, List<ISpellModifier> modifiers) implements RitualTrigger {
    public static final Codec<SpellComponentCastRitualTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.forRegistry(AMRegistries.SPELL_PART_REGISTRY).comapFlatMap(part -> part.getType() == ISpellPart.SpellPartType.COMPONENT ? DataResult.success(((ISpellComponent) part)) : DataResult.error("Not a spell component"), Function.identity()).listOf().fieldOf("components").forGetter(SpellComponentCastRitualTrigger::components),
            CodecHelper.forRegistry(AMRegistries.SPELL_PART_REGISTRY).comapFlatMap(part -> part.getType() == ISpellPart.SpellPartType.MODIFIER ? DataResult.success(((ISpellModifier) part)) : DataResult.error("Not a spell modifier"), Function.identity()).listOf().fieldOf("modifiers").forGetter(SpellComponentCastRitualTrigger::modifiers)
    ).apply(inst, SpellComponentCastRitualTrigger::new));

    public SpellComponentCastRitualTrigger(List<ISpellComponent> components) {
        this(components, List.of());
    }

    @Override
    public void register(Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((SpellEvent.Cast.Component evt) -> {
            if (evt.getEntity() instanceof Player player && player.getLevel() instanceof ServerLevel level && components.contains(evt.getComponent())) {
                HitResult target = evt.getTarget();
                if (target == null) return;
                BlockPos pos = switch (target.getType()) {
                    case MISS -> null;
                    case BLOCK -> ((BlockHitResult) target).getBlockPos();
                    case ENTITY -> ((EntityHitResult) target).getEntity().blockPosition();
                };
                if (pos == null) return;
                Entity entity = target.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) target).getEntity() : null;
                ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
                builder.put("spell", evt.getSpell());
                builder.put("parts", evt.getSpell().spellStack().parts());
                builder.put("caster", player);
                if (entity != null) builder.put("entity", entity);
                if (ritual.perform(player, level, pos, new Context.MapContext(builder.build()))) {
                    evt.setCanceled(true);
                }
            }
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx) {
        Set<Object> parts = new HashSet<>(Objects.requireNonNull(ctx.get("parts", List.class)));
        return parts.containsAll(components) && parts.containsAll(modifiers);
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
