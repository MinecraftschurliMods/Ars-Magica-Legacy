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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Zone extends AbstractSpellEntity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ISpell> SPELL = SynchedEntityData.defineId(Zone.class, AMDataSerializers.SPELL.get());

    public Zone(EntityType<? extends Zone> type, Level level) {
        super(type, level);
        setBoundingBox(new AABB(getX() - 0.1, getY() - 0.1, getZ() - 0.1, getX() + 0.1, getY() + 0.1, getZ() + 0.1));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TARGET_NON_SOLID, false)
               .define(COLOR, -1)
               .define(DURATION, 200)
               .define(INDEX, 0)
               .define(OWNER, 0)
               .define(GRAVITY, 0f)
               .define(RADIUS, 1.4f)
               .define(SPELL, ISpell.EMPTY);
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
        entityData.set(SPELL, ISpell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("Spell")).getOrThrow().getFirst());
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
        tag.put("Spell", ISpell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow());
    }

    @Override
    public void tick() {
        super.tick();
        setPos(getX(), getY() - getGravity(), getZ());
        Level level = level();
        if (level.isClientSide() || tickCount % 5 != 0) return;
        LivingEntity owner = getOwner();
        int index = getIndex();
        float radius = getRadius();
        ISpell spell = getSpell();
        forAllInRange(radius, false, e -> ArsMagicaAPI.get().getSpellHelper().invoke(spell, owner, level, new EntityHitResult(e), tickCount, index, true));
        List<Vec3> list = new ArrayList<>();
        for (int x = (int) Math.rint(-radius); x <= (int) Math.rint(radius); x++) {
            for (int y = (int) Math.rint(-getBbHeight()); y <= (int) Math.rint(getBbHeight()); y++) {
                for (int z = (int) Math.rint(-radius); z <= (int) Math.rint(radius); z++) {
                    list.add(new Vec3(getX() + x, getY() + y, getZ() + z));
                }
            }
        }
        for (Vec3 vec : list) {
            HitResult result = AMUtil.getHitResult(vec, vec.add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
            ArsMagicaAPI.get().getSpellHelper().invoke(spell, owner, level, result, tickCount, index, true);
        }
        if (tickCount > 0) {
            PacketDistributor.sendToPlayersNear((ServerLevel) level(), null, getX(), getY(), getZ(), 128, new SpawnAMParticlesPacket(this));
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
        Entity entity = level().getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    @Override
    public double getDefaultGravity() {
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

    public ISpell getSpell() {
        return entityData.get(SPELL);
    }

    public void setSpell(ISpell spell) {
        entityData.set(SPELL, spell);
    }
}
