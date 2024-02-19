package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Map;

public record EntityDeathTrigger(EntityPredicate predicate) implements RitualTrigger {
    public static final Codec<EntityDeathTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.ENTITY_PREDICATE.fieldOf("entity").forGetter(EntityDeathTrigger::predicate)).apply(inst, EntityDeathTrigger::new));

    @Override
    public void register(Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((LivingDeathEvent event) -> {
            if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) return;
            if (event.getSource().getEntity() instanceof Player player) {
                ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Context.MapContext(Map.of("entity", event.getEntity())));
            } else {
                for (Player player : serverLevel.getEntitiesOfClass(Player.class, AABB.ofSize(event.getEntity().position(), 5, 5, 5))) {
                    if (ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Context.MapContext(Map.of("entity", event.getEntity())))) {
                        return;
                    }
                }
            }
        });
    }

    @Override
    public boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx) {
        return predicate.matches(level, Vec3.atCenterOf(pos), ctx.get("entity", LivingEntity.class));
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
