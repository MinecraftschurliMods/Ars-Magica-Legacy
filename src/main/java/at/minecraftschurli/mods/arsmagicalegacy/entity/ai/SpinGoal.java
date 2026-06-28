package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class SpinGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    public SpinGoal(T boss) {
        super(boss, AbstractBoss.Action.SPIN, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) <= 2;
    }

    @Override
    public void performTick() {
        if (!(boss.level() instanceof ServerLevel level)) return;
        for (LivingEntity e : boss.level().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(2, 2, 2), e -> !(e instanceof AbstractBoss))) {
            e.hurtServer(level, boss.damageSources().mobAttack(boss), 4);
        }
    }
}
