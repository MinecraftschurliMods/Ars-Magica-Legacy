package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("deprecation")
public class FallingStar extends SpellEntity {
    public static final Identifier FALL_PARTICLES = ArsMagicaApi.id("falling_star_fall");
    public static final Identifier GROUND_PARTICLES = ArsMagicaApi.id("falling_star_ground");
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.FLOAT);
    private static final String DAMAGE_KEY = "damage";
    private static final String RANGE_KEY = "range";
    private static final String DAMAGED_KEY = "damaged";
    private final IntSet damaged = new IntOpenHashSet();
    private int timeSinceImpact = -1;

    public FallingStar(EntityType<? extends FallingStar> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DAMAGE, 0f)
            .define(RANGE, 1f);
    }

    @Override
    protected void readData(ValueInput tag) {
        entityData.set(DAMAGE, tag.getFloatOr(DAMAGE_KEY, 0));
        entityData.set(RANGE, tag.getFloatOr(RANGE_KEY, 1));
        tag.listOrEmpty(DAMAGED_KEY, Codec.INT).forEach(damaged::add);
    }

    @Override
    protected void writeData(ValueOutput tag) {
        tag.putFloat(DAMAGE_KEY, entityData.get(DAMAGE));
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
        ValueOutput.TypedOutputList<Integer> list = tag.list(DAMAGED_KEY, Codec.INT);
        damaged.forEach(list::add);
    }

    @Override
    public void tick() {
        Level level = level();
        float damage = getDamage();
        if (timeSinceImpact == -1) {
            setPos(position().add(getDeltaMovement()));
            if (level.isClientSide()) {
                AMClientUtil.spawnFallingStarParticles(this, false);
            }
            HitResult result = AMUtil.getHitResult(position(), position().add(0, 0.01, 0), this, false);
            if (result.getType() == HitResult.Type.MISS) return;
            if (result.getType() == HitResult.Type.BLOCK) {
                Vec3 vec = result.getLocation();
                do {
                    vec = vec.add(0, 1, 0);
                } while (level.getBlockState(BlockPos.containing(vec)).isSolid());
                setPos(vec.x(), (int) vec.y(), vec.z());
                if (level.isClientSide()) {
                    AMClientUtil.spawnFallingStarParticles(this, true);
                }
            }
        }
        timeSinceImpact++;
        for (Entity entity : level.getEntities(this, getBoundingBox().inflate(timeSinceImpact, AMServerConfig.FALLING_STAR_HEIGHT.get(), timeSinceImpact))) {
            int id = entity.getId();
            if (damaged.contains(id) || entity instanceof Player player && player.isCreative() || distanceTo(entity) > timeSinceImpact) continue;
            if (level instanceof ServerLevel serverLevel) {
                entity.hurtServer(serverLevel, damageSource(AMDamageTypes.FALLING_STAR), damage);
            }
            damaged.add(id);
        }
        if (timeSinceImpact > getRange()) {
            remove(RemovalReason.KILLED);
        }
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public float getRange() {
        return entityData.get(RANGE);
    }

    public void setRange(float radius) {
        entityData.set(RANGE, radius);
    }
}
