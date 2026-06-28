package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.LightningGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

public class StaticGoal extends AbstractBossGoal<LightningGuardian> {
    public StaticGoal(LightningGuardian boss) {
        super(boss, AbstractBoss.Action.SPIN, 10);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_STATIC.value();
    }

    @Override
    public void perform() {
        if (!(boss.level() instanceof ServerLevel level)) return;
        for (LivingEntity e : boss.level().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(8, 3, 8), e -> !(e instanceof AbstractBoss))) {
            e.hurtServer(level, boss.damageSources().lightningBolt(), 20);
        }
    }
}
