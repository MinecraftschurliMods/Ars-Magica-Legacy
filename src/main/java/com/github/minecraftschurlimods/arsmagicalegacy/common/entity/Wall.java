package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataSerializers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class Wall extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ISpell> SPELL = SynchedEntityData.defineId(Wall.class, AMDataSerializers.SPELL_SERIALIZER);

    /**
     * Use {@link Wall#create(Level)} instead.
     */
    @Internal
    public Wall(EntityType<? extends Wall> type, Level level) {
        super(type, level);
    }

    /**
     * Creates a new instance of this class in the given level. This is necessary, as otherwise the entity registration yells at us with some weird overloading error.
     *
     * @param level the level to create the new instance in
     * @return a new instance of this class in the given level
     */
    public static Wall create(Level level) {
        return new Wall(AMEntities.WALL.get(), level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(OWNER, 0);
        entityData.define(RADIUS, 1f);
        entityData.define(SPELL, ISpell.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
        entityData.set(SPELL, ISpell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("Spell")).getOrThrow(false, ArsMagicaLegacy.LOGGER::error).getFirst());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Radius", entityData.get(RADIUS));
        tag.put("Spell", ISpell.CODEC.encodeStart(NbtOps.INSTANCE, getSpell()).getOrThrow(false, ArsMagicaLegacy.LOGGER::error));
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
        Entity entity = getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    @Override
    public void tick() {
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        Vec3 a = new Vec3(getX() - Math.cos(3.1415926f / 180f * getYRot()) * getRadius(), getY(), getZ() - Math.sin(3.1415926f / 180f * getYRot()) * getRadius());
        Vec3 b = new Vec3(getX() + Math.cos(3.1415926f / 180f * getYRot()) * getRadius(), getY(), getZ() + Math.sin(3.1415926f / 180f * getYRot()) * getRadius());
        double minX = getX() - getRadius();
        double minY = getY() - 1;
        double minZ = getZ() - getRadius();
        double maxX = getX() + getRadius();
        double maxY = getY() + 3;
        double maxZ = getZ() + getRadius();
        if (level.isClientSide()) {
            for (double x = minX; x <= maxX; x += 0.2) {
                for (double y = minY; y <= maxY; y += 0.2) {
                    for (double z = minZ; z <= maxZ; z += 0.2) {
                        double newX = x + random.nextDouble(0.2) - 0.1;
                        double newZ = z + random.nextDouble(0.2) - 0.1;
                        if (newX > minX && newX < maxX && newZ > minZ && newZ < maxZ) {
                            Vec3 newVec = new Vec3(newX, getY(), newZ);
                            if (newVec.distanceTo(a) < 0.5 || newVec.distanceTo(b) < 0.5 || newVec.distanceTo(position()) < 0.5) {
                                level.addParticle(ParticleTypes.PORTAL, newX, y + random.nextDouble(0.2) - 0.1, newZ, (random.nextDouble() - 0.5) * 2, -random.nextDouble(), (random.nextDouble() - 0.5) * 2);
                            }
                        }
                    }
                }
            }
        } else if (tickCount % 4 == 0) {
            for (Entity e : level.getEntities(this, new AABB(minX, minY, minZ, maxX, maxY, maxZ))) {
                if (e == this) continue;
                if (e instanceof PartEntity) {
                    e = ((PartEntity<?>) e).getParent();
                }
                Vec3 closest = AMUtil.closestPointOnLine(e.position(), a, b);
                closest = new Vec3(closest.x, getY(), closest.z);
                if (e instanceof LivingEntity living && !living.hasEffect(AMMobEffects.REFLECT.get()) && closest.distanceTo(e.position()) < 0.75 && Math.abs(getY() - e.getY()) < 2) {
                    ArsMagicaAPI.get().getSpellHelper().invoke(getSpell(), getOwner(), level, new EntityHitResult(e), tickCount, getIndex(), true);
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

    public int getIndex() {
        return entityData.get(INDEX);
    }

    public void setIndex(int index) {
        entityData.set(INDEX, index);
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

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
