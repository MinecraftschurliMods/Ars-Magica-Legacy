package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Shockwave extends Entity {
    private final Map<Player, Integer> cooldowns = new HashMap<>();

    public Shockwave(EntityType<? extends Shockwave> type, Level level) {
        super(type, level);
    }

    /**
     * Creates a new instance of this class in the given level. This is necessary, as otherwise the entity registration yells at us with some weird overloading error.
     *
     * @param level the level to create the new instance in
     * @return a new instance of this class in the given level
     */
    public static Shockwave create(Level level) {
        return new Shockwave(AMEntities.SHOCKWAVE.get(), level);
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
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide() && tickCount > 60) {
            kill();
        }
        cooldowns.replaceAll((k, v) -> Math.max(v - 1, 0));
        for (float f = -1.5f; f <= 1.5f; f += 0.1f) {
            level.addParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 1), position().x() + f * getDeltaMovement().x() + random.nextDouble(0.5), position().y(), position().z() + f * getDeltaMovement().z() + random.nextDouble(0.5), 0, 0, 0);
        }
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    public void playerTouch(Player pPlayer) {
        super.playerTouch(pPlayer);
        if (level.isClientSide() || pPlayer.isCreative()) return;
        Integer cd = cooldowns.get(pPlayer);
        if (cd == null || cd <= 0) {
            pPlayer.hurt(AMDamageSources.SHOCKWAVE, 2);
            cooldowns.put(pPlayer, 20);
        }
    }
}
