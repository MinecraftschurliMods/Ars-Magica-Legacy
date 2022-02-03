package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Zone extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.ITEM_STACK);

    /**
     * Use {@link Zone#create(Level)} instead.
     */
    @Internal
    public Zone(EntityType<? extends Zone> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setBoundingBox(new AABB(getX() - 0.1, getY() - 0.1, getZ() - 0.1, getX() + 0.1, getY() + 0.1, getZ() + 0.1));
    }

    /**
     * Creates a new instance of this class in the given level. This is necessary, as otherwise the entity registration yells at us with some weird overloading error.
     *
     * @param level the level to create the new instance in
     * @return a new instance of this class in the given level
     */
    public static Zone create(Level level) {
        return new Zone(AMEntities.ZONE.get(), level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TARGET_NON_SOLID, false);
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(OWNER, 0);
        entityData.define(GRAVITY, 0f);
        entityData.define(RADIUS, 1.4f);
        entityData.define(STACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(TARGET_NON_SOLID, tag.getBoolean("TargetNonSolid"));
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(GRAVITY, tag.getFloat("Gravity"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
        entityData.set(STACK, ItemStack.of(tag.getCompound("Stack")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putBoolean("TargetNonSolid", entityData.get(TARGET_NON_SOLID));
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Gravity", entityData.get(GRAVITY));
        tag.putFloat("Radius", entityData.get(RADIUS));
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
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        setPos(getX(), getY() - getGravity(), getZ());
        for (int i = 0; i < 8; ++i) {
            level.addParticle(ParticleTypes.PORTAL, getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), (random.nextDouble() - 0.5D) * 2D, -random.nextDouble(), (random.nextDouble() - 0.5D) * 2D);
        }
        if (!level.isClientSide() && tickCount % 10 == 0) {
            List<Entity> list = level.getEntities(this, new AABB(getX() - getRadius(), getY(), getZ() - getRadius(), getX() + getRadius(), getY() + getBbHeight(), getZ() + getRadius()));
            for (Entity entity : list) {
                if (entity == this) continue;
                if (entity instanceof PartEntity) {
                    entity = ((PartEntity<?>) entity).getParent();
                }
                if (entity instanceof LivingEntity living && !living.hasEffect(AMMobEffects.REFLECT.get())) {
                    ArsMagicaAPI.get().getSpellHelper().invoke(SpellItem.getSpell(getStack()), getOwner(), level, new EntityHitResult(entity), tickCount, getIndex(), true);
                }
            }
        }
        List<Vec3> list = new ArrayList<>();
        for (int x = (int) Math.rint(-getRadius()); x <= (int) Math.rint(getRadius()); x++) {
            for (int y = (int) Math.rint(-getBbHeight()); y <= (int) Math.rint(getBbHeight()); y++) {
                for (int z = (int) Math.rint(-getRadius()); z <= (int) Math.rint(getRadius()); z++) {
                    list.add(new Vec3(getX() + x, getY() + y, getZ() + z));
                }
            }
        }
        for (Vec3 vec : list) {
            HitResult result = AMUtil.getHitResult(vec, vec.add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
            ArsMagicaAPI.get().getSpellHelper().invoke(SpellItem.getSpell(getStack()), getOwner(), level, result, tickCount, getIndex(), true);
        }
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid() {
        entityData.set(TARGET_NON_SOLID, true);
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

    public ItemStack getStack() {
        return entityData.get(STACK);
    }

    public void setStack(ItemStack stack) {
        entityData.set(STACK, stack);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(AMItems.BLANK_RUNE.get());
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return false;
    }
}
