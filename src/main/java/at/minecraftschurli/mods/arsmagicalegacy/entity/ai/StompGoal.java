package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.Shockwave;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class StompGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    public StompGoal(T boss) {
        super(boss, AbstractBoss.Action.STOMP, 5, 15);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 2 && boss.distanceTo(boss.getTarget()) <= 4;
    }

    @Override
    public void perform() {
        if (!(boss.level() instanceof ServerLevel level)) return;
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(4, 2, 4), e -> !(e instanceof AbstractBoss))) {
            e.hurtServer(level, boss.damageSources().mobAttack(boss), 4);
        }
        for (int i = -20; i <= 20; i++) {
            Shockwave entity = Objects.requireNonNull(AMEntities.SHOCKWAVE.get().create(level, EntitySpawnReason.MOB_SUMMONED));
            Vec3 movement = boss.getLookAngle().yRot((float) (Math.PI / 180 * i));
            entity.setDeltaMovement(movement.x(), 0, movement.z());
            entity.setPos(boss.getX() + movement.x(), boss.getY(), boss.getZ() + movement.z());
            level.addFreshEntity(entity);
        }
    }
}
