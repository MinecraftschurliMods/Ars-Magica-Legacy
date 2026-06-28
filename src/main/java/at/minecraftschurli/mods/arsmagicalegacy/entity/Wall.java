package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Wall extends SpellShapeEntity {
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Wall.class, EntityDataSerializers.FLOAT);
    private static final String RANGE_KEY = "range";

    public Wall(EntityType<? extends Wall> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(RANGE, 1f);
    }

    @Override
    protected void readData(ValueInput tag) {
        super.readData(tag);
        entityData.set(RANGE, tag.getFloatOr(RANGE_KEY, 1f));
    }

    @Override
    protected void writeData(ValueOutput tag) {
        super.writeData(tag);
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
    }

    @Override
    public void tick() {
        if (cancelTick(AMServerConfig.WALL_TICK_INTERVAL.get())) return;
        float range = getRange();
        double yRot = Math.toRadians(getYRot());
        double cos = Math.cos(yRot) * range;
        double sin = Math.sin(yRot) * range;
        Vec3 a = new Vec3(getX() - cos, getY(), getZ() - sin);
        Vec3 b = new Vec3(getX() + cos, getY(), getZ() + sin);
        AABB aabb = new AABB(position().add(-range, 0, -range), position().add(range, range * AMServerConfig.WALL_HEIGHT.get(), range));
        castArea(aabb, pos -> isAffected(pos.getBottomCenter(), 1, a, b, aabb.minY, aabb.maxY), entity -> isAffected(entity.position(), entity.getBbHeight(), a, b, aabb.minY, aabb.maxY), false);
    }

    public float getRange() {
        return entityData.get(RANGE);
    }

    public void setRange(float range) {
        entityData.set(RANGE, range);
    }

    private static boolean isAffected(Vec3 targetPos, double targetHeight, Vec3 a, Vec3 b, double y1, double y2) {
        Vec3 vec = b.subtract(a).normalize();
        double p = vec.dot(targetPos.subtract(a));
        Vec3 closest = p <= 0 ? a : p >= a.distanceTo(b) ? b : a.add(vec.scale(p));
        double minY = targetPos.y();
        double maxY = minY + targetHeight;
        return new Vec3(closest.x, minY, closest.z).distanceTo(targetPos) < 0.75 && (y1 >= minY && y1 < maxY || y2 >= minY && y2 < maxY || minY >= y1 && minY < y2 || maxY >= y1 && maxY < y2);
    }
}
