package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
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

/**
 *
 */
public record EntityDeathTrigger(EntityPredicate predicate) implements RitualTrigger {
    public static final Codec<EntityDeathTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(AMUtil.ENTITY_PREDICATE.fieldOf("entity").forGetter(EntityDeathTrigger::predicate)).apply(inst, EntityDeathTrigger::new));

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((LivingDeathEvent event) -> {
            if (!(event.getEntity().getLevel() instanceof ServerLevel serverLevel)) return;
            if (event.getSource().getEntity() instanceof Player player) {
                ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Context.MapContext(Map.of("player", player, "entity", event.getEntityLiving())));
            } else {
                for (Player player : serverLevel.getEntitiesOfClass(Player.class, AABB.ofSize(event.getEntityLiving().position(), 5, 5, 5))) {
                    if (ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Context.MapContext(Map.of("player", player, "entity", event.getEntityLiving())))) {
                        return;
                    }
                }
            }
        });
    }

    @Override
    public boolean trigger(final ServerLevel level, final BlockPos pos, Context ctx) {
        return predicate.matches(level, Vec3.atCenterOf(pos), ctx.get("entity", LivingEntity.class));
    }

    @Override
    public Codec<? extends RitualTrigger> codec() {
        return CODEC;
    }
}
