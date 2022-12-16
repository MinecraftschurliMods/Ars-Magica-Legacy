package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class Shockwave extends Entity {
    private static final ParticleOptions PARTICLE = new DustParticleOptions(new Vector3f(1, 1, 1), 1);
    private final Map<LivingEntity, Integer> cooldowns = new HashMap<>();
    private final DamageSource damageSource = AMDamageSources.shockwave(this);

    public Shockwave(EntityType<? extends Shockwave> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide() && tickCount > 60) {
            kill();
        }
        cooldowns.replaceAll((k, v) -> Math.max(v - 1, 0));
        if (level.isClientSide()) {
            for (float f = -1f; f <= 1f; f += 0.1f) {
                level.addParticle(PARTICLE, position().x() + f * getDeltaMovement().x() + random.nextDouble() / 2, position().y(), position().z() + f * getDeltaMovement().z() + random.nextDouble() / 2, 0, 0, 0);
            }
        } else {
            for (Entity e : level.getEntities(this, getBoundingBox(), EntitySelector.pushableBy(this))) {
                if (!(e instanceof LivingEntity living) || living instanceof Player player && player.isCreative())
                    continue;
                Integer cooldown = cooldowns.get(living);
                if (cooldown == null || cooldown <= 0) {
                    living.hurt(damageSource, 2);
                    cooldowns.put(living, 20);
                }
            }
        }
        setPos(position().add(getDeltaMovement()));
    }
}
