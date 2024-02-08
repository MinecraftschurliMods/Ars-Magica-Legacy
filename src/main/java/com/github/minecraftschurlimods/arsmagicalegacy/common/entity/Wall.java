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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class Wall extends AbstractSpellEntity {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ISpell> SPELL = SynchedEntityData.defineId(Wall.class, AMDataSerializers.SPELL.get());

    public Wall(EntityType<? extends Wall> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(COLOR, -1);
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(OWNER, 0);
        entityData.define(RADIUS, 1f);
        entityData.define(SPELL, ISpell.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(COLOR, tag.getInt("Color"));
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
        entityData.set(SPELL, ISpell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("Spell")).getOrThrow(false, ArsMagicaLegacy.LOGGER::error).getFirst());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Color", entityData.get(COLOR));
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Radius", entityData.get(RADIUS));
        tag.put("Spell", ISpell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow(false, ArsMagicaLegacy.LOGGER::error));
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide() || tickCount % 5 != 0) return;
        LivingEntity owner = getOwner();
        int index = getIndex();
        float radius = getRadius();
        ISpell spell = getSpell();
        double cos = Math.cos(Math.toRadians(getYRot())) * radius;
        double sin = Math.sin(Math.toRadians(getYRot())) * radius;
        Vec3 a = new Vec3(getX() - cos, getY(), getZ() - sin);
        Vec3 b = new Vec3(getX() + cos, getY(), getZ() + sin);
        double minX = getX() - radius;
        double minY = getY() - 1;
        double minZ = getZ() - radius;
        double maxX = getX() + radius;
        double maxY = getY() + 3;
        double maxZ = getZ() + radius;
        for (Entity e : level.getEntities(this, new AABB(minX, minY, minZ, maxX, maxY, maxZ))) {
            if (e == this) continue;
            if (e instanceof PartEntity<?> part) {
                e = part.getParent();
            }
            Vec3 closest = AMUtil.closestPointOnLine(e.position(), a, b);
            closest = new Vec3(closest.x, getY(), closest.z);
            if (tryReflect(e) && closest.distanceTo(e.position()) < 0.75 && Math.abs(getY() - e.getY()) < 2) {
                ArsMagicaAPI.get().getSpellHelper().invoke(spell, owner, level, new EntityHitResult(e), tickCount, index, true);
            }
        }
        if (tickCount > 0) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToAllAround(new SpawnAMParticlesPacket(this), level, blockPosition(), 128);
        }
    }

    @Override
    public int getColor() {
        return entityData.get(COLOR);
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
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public float getRadius() {
        return entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        entityData.set(RADIUS, radius);
    }

    public ISpell getSpell() {
        return entityData.get(SPELL);
    }

    public void setSpell(ISpell spell) {
        entityData.set(SPELL, spell);
    }
}
