package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Blizzard extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);

    public Blizzard(EntityType<? extends Blizzard> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DURATION, 200);
        entityData.define(OWNER, 0);
        entityData.define(DAMAGE, 0f);
        entityData.define(RADIUS, 1f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(DAMAGE, tag.getFloat("Damage"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Damage", entityData.get(DAMAGE));
        tag.putFloat("Radius", entityData.get(RADIUS));
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
        Entity entity = getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    @Override
    public void tick() {
        if (tickCount > getDuration()) {
            remove(RemovalReason.KILLED);
            return;
        }
        for (int i = 0; i < 20 * getRadius(); ++i) {
            level.addParticle(ParticleTypes.SNOWFLAKE, getRandomX(getRadius() * 2), getY() + (2d * random.nextDouble() - 1d) * getRadius() / 2, getRandomZ(getRadius() * 2), 0, 0, 0);
        }
        if (level.isClientSide()) return;
        if (tickCount % 5 == 0) {
            List<Entity> list = level.getEntities(this, new AABB(getX() - getRadius(), getY() - getRadius(), getZ() - getRadius(), getX() + getRadius(), getY() + getRadius(), getZ() + getRadius()));
            for (Entity entity : list) {
                if (entity == this || entity == getOwner()) continue;
                if (entity instanceof PartEntity) {
                    entity = ((PartEntity<?>) entity).getParent();
                }
                if (entity instanceof LivingEntity living && !living.hasEffect(AMMobEffects.REFLECT.get())) {
                    living.hurt(DamageSource.FREEZE, getDamage());
                    living.addEffect(new MobEffectInstance(AMMobEffects.FROST.get(), 50));
                }
            }
        }
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public float getRadius() {
        return entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        entityData.set(RADIUS, radius);
    }
}
