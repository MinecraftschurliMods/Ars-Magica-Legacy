package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class SpellEntity extends AbstractOwnableEntity {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(SpellEntity.class, EntityDataSerializers.INT);
    private static final String COLOR_KEY = "color";
    private static final String DURATION_KEY = "duration";

    public SpellEntity(EntityType<? extends SpellEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(COLOR, -1)
            .define(DURATION, 72000);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(tag -> {
            entityData.set(COLOR, tag.getIntOr(COLOR_KEY, -1));
            entityData.set(DURATION, tag.getIntOr(DURATION_KEY, 72000));
            readData(tag);
        });
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        ValueOutput tag = output.child(ArsMagicaApi.MOD_ID);
        tag.putInt(COLOR_KEY, entityData.get(COLOR));
        tag.putInt(DURATION_KEY, entityData.get(DURATION));
        writeData(tag);
    }

    protected abstract void readData(ValueInput tag);

    protected abstract void writeData(ValueOutput tag);

    public int getColor() {
        return entityData.get(COLOR);
    }

    public void setColor(int color) {
        entityData.set(COLOR, color);
    }

    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    @Override
    public float getYHeadRot() {
        return getYRot();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    protected boolean cancelTick(int tickInterval) {
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return true;
        }
        return tickCount % tickInterval != 0 || getOwner() == null;
    }

    @SuppressWarnings("DataFlowIssue")
    protected boolean tryReflect(Entity e) {
        if (!(e instanceof LivingEntity living)) return true;
        if (living.isDeadOrDying()) return false;
        if (!living.hasEffect(AMMobEffects.REFLECT)) return true;
        MobEffectInstance reflect = living.getEffect(AMMobEffects.REFLECT);
        if (reflect.getAmplifier() == 0) {
            living.removeEffect(AMMobEffects.REFLECT);
        } else {
            MobEffectInstance effect = new MobEffectInstance(reflect.getEffect(), reflect.getDuration(), reflect.getAmplifier(), reflect.isAmbient(), reflect.isVisible(), reflect.showIcon());
            living.removeEffect(AMMobEffects.REFLECT);
            living.addEffect(effect);
        }
        return false;
    }
}
