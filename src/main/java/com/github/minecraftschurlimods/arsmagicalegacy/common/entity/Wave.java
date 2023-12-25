package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataSerializers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpawnAMParticlesPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Wave extends AbstractSpellEntity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ISpell> SPELL = SynchedEntityData.defineId(Wave.class, AMDataSerializers.SPELL.get());

    public Wave(EntityType<? extends Wave> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TARGET_NON_SOLID, false);
        entityData.define(COLOR, -1);
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(OWNER, 0);
        entityData.define(GRAVITY, 0f);
        entityData.define(RADIUS, 1f);
        entityData.define(SPEED, 1f);
        entityData.define(SPELL, ISpell.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(TARGET_NON_SOLID, tag.getBoolean("TargetNonSolid"));
        entityData.set(COLOR, tag.getInt("Color"));
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(GRAVITY, tag.getFloat("Gravity"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
        entityData.set(SPEED, tag.getFloat("Speed"));
        entityData.set(SPELL, ISpell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("Spell")).getOrThrow(false, ArsMagicaLegacy.LOGGER::error).getFirst());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putBoolean("TargetNonSolid", entityData.get(TARGET_NON_SOLID));
        tag.putInt("Color", entityData.get(COLOR));
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Gravity", entityData.get(GRAVITY));
        tag.putFloat("Radius", entityData.get(RADIUS));
        tag.putFloat("Speed", entityData.get(SPEED));
        tag.put("Spell", ISpell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow(false, ArsMagicaLegacy.LOGGER::error));
    }

    @Override
    public void tick() {
        super.tick();
        float speed = getSpeed();
        setPos(getX() + getDeltaMovement().x() * speed / 10f, getY() + getDeltaMovement().y() * speed / 10f, getZ() + getDeltaMovement().z() * speed / 10f);
        if (level().isClientSide() || tickCount % 5 != 0) return;
        LivingEntity owner = getOwner();
        int index = getIndex();
        float radius = getRadius();
        ISpell spell = getSpell();
        forAllInRange(radius, false, e -> ArsMagicaAPI.get().getSpellHelper().invoke(spell, owner, level(), new EntityHitResult(e), tickCount, index, true));
        List<Vec3> list = new ArrayList<>();
        for (int x = (int) Math.rint(-radius); x <= (int) Math.rint(radius); x++) {
            for (int y = (int) Math.rint(-radius); y <= (int) Math.rint(radius); y++) {
                for (int z = (int) Math.rint(-radius); z <= (int) Math.rint(radius); z++) {
                    list.add(new Vec3(getX() + x, getY() + y, getZ() + z));
                }
            }
        }
        for (Vec3 vec : list) {
            HitResult result = AMUtil.getHitResult(vec, vec.add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
            ArsMagicaAPI.get().getSpellHelper().invoke(spell, owner, level(), result, tickCount, index, true);
        }
        if (tickCount > 0) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToAllAround(new SpawnAMParticlesPacket(this), level(), blockPosition(), 128);
        }
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid() {
        entityData.set(TARGET_NON_SOLID, true);
    }

    @Override
    public int getColor() {
        return 0;
    }

    public void setColor(int color) {
        entityData.set(COLOR, color);
    }

    @Override
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

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level().getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public float getGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public float getRadius() {
        return entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        entityData.set(RADIUS, radius);
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
}
