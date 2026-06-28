package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class StrikeGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    public StrikeGoal(T boss) {
        super(boss, AbstractBoss.Action.STRIKE, 5, 15);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) <= 2;
    }

    @Override
    public void perform() {
        if (!(boss.level() instanceof ServerLevel level)) return;
        for (LivingEntity e : boss.level().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().expandTowards(Math.cos(boss.getYRot()) * 2, 0, Math.sin(boss.getYRot()) * 2).inflate(2.5, 2, 2.5), e -> !(e instanceof AbstractBoss))) {
            e.hurtServer(level, boss.damageSources().mobAttack(boss), 6);
        }
    }
}
