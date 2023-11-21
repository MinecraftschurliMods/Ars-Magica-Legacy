package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellEffectEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataSerializers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpawnAMParticlesPacket;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class Projectile extends Entity implements ISpellEffectEntity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PIERCES = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(Projectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ISpell> SPELL = SynchedEntityData.defineId(Projectile.class, AMDataSerializers.SPELL.get());

    public Projectile(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TARGET_NON_SOLID, false);
        entityData.define(BOUNCES, 0);
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(PIERCES, 0);
        entityData.define(OWNER, 0);
        entityData.define(GRAVITY, 0f);
        entityData.define(SPEED, 1f);
        entityData.define(SPELL, ISpell.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(TARGET_NON_SOLID, tag.getBoolean("TargetNonSolid"));
        entityData.set(BOUNCES, tag.getInt("Bounces"));
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(PIERCES, tag.getInt("Pierces"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(GRAVITY, tag.getFloat("Gravity"));
        entityData.set(SPEED, tag.getFloat("Speed"));
        entityData.set(SPELL, ISpell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("Spell")).getOrThrow(false, ArsMagicaLegacy.LOGGER::error).getFirst());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putBoolean("TargetNonSolid", entityData.get(TARGET_NON_SOLID));
        tag.putInt("Bounces", entityData.get(BOUNCES));
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Pierces", entityData.get(PIERCES));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Gravity", entityData.get(GRAVITY));
        tag.putFloat("Speed", entityData.get(SPEED));
        tag.put("Spell", ISpell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow(false, ArsMagicaLegacy.LOGGER::error));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        Entity entity = getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    @Override
    public void tick() {
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        HitResult result = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
        if (result.getType().equals(HitResult.Type.BLOCK)) {
            level.getBlockState(((BlockHitResult) result).getBlockPos()).entityInside(level, ((BlockHitResult) result).getBlockPos(), this);
            if (getBounces() > 0) {
                Direction direction = ((BlockHitResult) result).getDirection();
                double speed = getSpeed();
                double newX = getDeltaMovement().x;
                double newY = getDeltaMovement().y;
                double newZ = getDeltaMovement().z;
                if (direction.getAxis() == Direction.Axis.Y) {
                    newY = -newY;
                } else if (direction.getAxis() == Direction.Axis.X) {
                    newX = -newX;
                } else if (direction.getAxis() == Direction.Axis.Z) {
                    newZ = -newZ;
                }
                setDeltaMovement(newX * speed, newY * speed, newZ * speed);
                setBounces(getBounces() - 1);
            } else if (!level.isClientSide()) {
                ArsMagicaAPI.get().getSpellHelper().invoke(getSpell(), getOwner(), level, result, tickCount, getIndex(), true);
                decreaseBounces();
            }
        } else if (result.getType().equals(HitResult.Type.ENTITY) && !level.isClientSide()) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof PartEntity) {
                entity = ((PartEntity<?>) entity).getParent();
            }
            if (entity != getOwner() && entity instanceof LivingEntity living) {
                if (living.hasEffect(AMMobEffects.REFLECT.get())) {
                    MobEffectInstance reflect = living.getEffect(AMMobEffects.REFLECT.get());
                    if (reflect.getAmplifier() == 0) {
                        living.removeEffect(AMMobEffects.REFLECT.get());
                    } else {
                        MobEffectInstance effect = new MobEffectInstance(reflect.getEffect(), reflect.getDuration(), reflect.getAmplifier(), reflect.isAmbient(), reflect.isVisible(), reflect.showIcon());
                        living.removeEffect(AMMobEffects.REFLECT.get());
                        living.addEffect(effect);
                    }
                } else {
                    ArsMagicaAPI.get().getSpellHelper().invoke(getSpell(), getOwner(), level, result, tickCount, getIndex(), true);
                    decreaseBounces();
                }
            }
        }
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - getGravity(), getDeltaMovement().z);
        setPos(position().add(getDeltaMovement()));
        if (!level.isClientSide()) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToAllTracking(new SpawnAMParticlesPacket(this), this);
        }
    }

    public float getGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid() {
        entityData.set(TARGET_NON_SOLID, true);
    }

    public int getBounces() {
        return entityData.get(BOUNCES);
    }

    public void setBounces(int bounces) {
        entityData.set(BOUNCES, bounces);
    }

    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    public int getIndex() {
        return entityData.get(INDEX);
    }

    public void setIndex(int index) {
        entityData.set(INDEX, index);
    }

    public int getPierces() {
        return entityData.get(PIERCES);
    }

    public void setPierces(int pierces) {
        entityData.set(PIERCES, pierces);
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public float getSpeed() {
        return entityData.get(SPEED);
    }

    public void setSpeed(float speed) {
        entityData.set(SPEED, speed);
    }

    public ISpell getSpell() {
        return entityData.get(SPELL);
    }

    public void setSpell(ISpell spell) {
        entityData.set(SPELL, spell);
    }

    private void decreaseBounces() {
        if (getPierces() == 0) {
            remove(RemovalReason.KILLED);
        } else {
            setPierces(getPierces() - 1);
        }
    }
}
