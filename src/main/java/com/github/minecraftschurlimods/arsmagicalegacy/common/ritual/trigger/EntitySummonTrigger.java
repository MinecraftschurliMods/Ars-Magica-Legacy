package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
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
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import java.util.Map;

/**
 *
 */
public record EntitySummonTrigger(EntityPredicate predicate) implements Ritual.RitualTrigger {
    public static final Codec<EntitySummonTrigger> CODEC = RecordCodecBuilder.create(inst -> inst.group(AMUtil.ENTITY_PREDICATE.fieldOf("entity").forGetter(EntitySummonTrigger::predicate)).apply(inst, EntitySummonTrigger::new));

    @Override
    public void register(final Ritual ritual) {
        MinecraftForge.EVENT_BUS.addListener((LivingSpawnEvent.SpecialSpawn event) -> {
            if (!(event.getEntity().getLevel() instanceof ServerLevel serverLevel)) return;
            for (Player player : serverLevel.getEntitiesOfClass(Player.class, AABB.ofSize(event.getEntityLiving().position(), 5, 5, 5))) {
                if (ritual.perform(player, serverLevel, event.getEntity().blockPosition(), new Ritual.MapContext(Map.of("player", player, "entity", event.getEntityLiving())))) {
                    return;
                }
            }
        });
    }

    @Override
    public boolean trigger(final ServerLevel level, final BlockPos pos, Ritual.Context ctx) {
        LivingEntity entity = ctx.get("entity", LivingEntity.class);
        if (entity == null || !predicate.matches(level, Vec3.atCenterOf(pos), entity)) return false;
        entity.kill();
        return true;
    }

    @Override
    public Codec<? extends Ritual.RitualTrigger> codec() {
        return CODEC;
    }
}
