package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class SpellProjectile extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> GRAVITY = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PIERCES = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> ICON = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(SpellProjectile.class, EntityDataSerializers.ITEM_STACK);

    public SpellProjectile(Level level) {
        this(AMEntities.SPELL_PROJECTILE.get(), level);
    }

    public SpellProjectile(EntityType<? extends SpellProjectile> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(GRAVITY, false);
        entityData.define(TARGET_NON_SOLID, false);
        entityData.define(BOUNCES, 0);
        entityData.define(INDEX, 0);
        entityData.define(PIERCES, 0);
        entityData.define(OWNER, 0);
        entityData.define(ICON, "arcane");
        entityData.define(STACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(GRAVITY, tag.getBoolean("Gravity"));
        entityData.set(TARGET_NON_SOLID, tag.getBoolean("TargetNonSolid"));
        entityData.set(BOUNCES, tag.getInt("Bounces"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(PIERCES, tag.getInt("Pierces"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(ICON, tag.getString("Icon"));
        entityData.set(STACK, ItemStack.of(tag.getCompound("Stack")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putBoolean("Gravity", entityData.get(GRAVITY));
        tag.putBoolean("TargetNonSolid", entityData.get(TARGET_NON_SOLID));
        tag.putInt("Bounces", entityData.get(BOUNCES));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Pierces", entityData.get(PIERCES));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putString("Icon", entityData.get(ICON));
        CompoundTag stack = new CompoundTag();
        entityData.get(STACK).save(stack);
        tag.put("Stack", stack);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        Entity entity = getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    @Override
    public void tick() {
        if (tickCount > 200 || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        HitResult result = getHitResult(this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
        if (result.getType().equals(HitResult.Type.BLOCK)) {
            level.getBlockState(((BlockHitResult) result).getBlockPos()).entityInside(level, ((BlockHitResult) result).getBlockPos(), this);
            if (getBounces() > 0) {
                Direction direction = ((BlockHitResult) result).getDirection();
                double speed = 1; // TODO modifier
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
            } else {
                ArsMagicaAPI.get().getSpellHelper().invoke(SpellItem.getSpell(getStack()), getOwner(), level, result, 0, getIndex(), true);
                decreaseBounces();
            }
        } else if (result.getType().equals(HitResult.Type.ENTITY)) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof LivingEntity && entity != getOwner()) {
                ArsMagicaAPI.get().getSpellHelper().invoke(SpellItem.getSpell(getStack()), getOwner(), level, result, 0, getIndex(), true);
                decreaseBounces();
            }
        }
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - (getGravity() ? 0.05F : 0), getDeltaMovement().z);
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    public ItemStack getItem() {
        return ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.ARCANE.get()); // TODO
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

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void setOwner(Entity owner) {
        if (owner instanceof LivingEntity) {
            entityData.set(OWNER, owner.getId());
        }
    }

    public boolean getGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(boolean gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public String getIcon() {
        return entityData.get(ICON);
    }

    public void setIcon(String icon) {
        entityData.set(ICON, icon);
    }

    public ItemStack getStack() {
        return entityData.get(STACK);
    }

    public void setStack(ItemStack stack) {
        entityData.set(STACK, stack);
    }

    public void decreaseBounces() {
        if (getPierces() == 0) {
            remove(RemovalReason.KILLED);
        } else {
            setPierces(getPierces() - 1);
        }
    }

    /**
     * Performs a ray trace from the given entity in its view direction. Modified version of {@link ProjectileUtil#getHitResult(Entity, Predicate)}
     *
     * @param entity       the entity that causes this ray trace
     * @param blockContext the block clipping context to use
     * @param fluidContext the fluid clipping context to use
     * @return A hit result, representing the ray trace.
     */
    private static HitResult getHitResult(Entity entity, ClipContext.Block blockContext, ClipContext.Fluid fluidContext) {
        Vec3 movement = entity.getDeltaMovement();
        Level level = entity.level;
        Vec3 pos = entity.position();
        Vec3 newPos = pos.add(movement);
        HitResult hitResult = level.clip(new ClipContext(pos, newPos, blockContext, fluidContext, entity));
        if (hitResult.getType() != HitResult.Type.MISS) {
            newPos = hitResult.getLocation();
        }
        HitResult entityHitResult = ProjectileUtil.getEntityHitResult(level, entity, pos, newPos, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1.0D), e -> true);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        return hitResult;
    }
}
