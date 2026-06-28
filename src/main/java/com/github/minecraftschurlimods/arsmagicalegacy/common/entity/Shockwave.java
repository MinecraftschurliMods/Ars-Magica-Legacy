package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Shockwave extends AbstractOwnableEntity {
    private static final ParticleOptions PARTICLE = new DustParticleOptions(0xffffff, 1);
    private final Map<LivingEntity, Integer> cooldowns = new HashMap<>();

    public Shockwave(EntityType<? extends Shockwave> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        Level level = level();
        if (!level.isClientSide() && tickCount > 60) {
            remove(RemovalReason.KILLED);
        }
        cooldowns.replaceAll((_, v) -> Math.max(v - 1, 0));
        if (level instanceof ServerLevel serverLevel) {
            for (Entity e : serverLevel.getEntities(this, getBoundingBox(), EntitySelector.pushableBy(this))) {
                if (!(e instanceof LivingEntity living) || living instanceof Player player && player.isCreative())
                    continue;
                Integer cooldown = cooldowns.get(living);
                if (cooldown == null || cooldown <= 0) {
                    living.hurtServer(serverLevel, damageSource(AMDamageTypes.SHOCKWAVE), 2);
                    cooldowns.put(living, 20);
                }
            }
        } else {
            for (float f = -1f; f <= 1f; f += 0.1f) {
                level.addParticle(PARTICLE, position().x() + f * getDeltaMovement().x() + random.nextDouble() / 2, position().y(), position().z() + f * getDeltaMovement().z() + random.nextDouble() / 2, 0, 0, 0);
            }
        }
        setPos(position().add(getDeltaMovement()));
    }
}
