package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.List;

public class SummonAlliesGoal extends AbstractBossGoal<LifeGuardian> {
    private final List<EntityType<? extends Mob>> list;
    private boolean hasCasted = false;

    public SummonAlliesGoal(LifeGuardian boss, List<EntityType<? extends Mob>> list) {
        super(boss, LifeGuardian.LifeGuardianAction.CASTING);
        this.list = list;
    }

    @Override
    public void stop() {
        super.stop();
        hasCasted = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (cooldownTicks >= 20) {
            SoundEvent sound = getAttackSound();
            if (sound != null) {
                boss.getLevel().playSound(null, boss, sound, SoundSource.HOSTILE, 1f, 0.5f + boss.getLevel().getRandom().nextFloat() * 0.5f);
            }
            perform();
            cooldownTicks = 0;
            hasCasted = true;
        }
    }

    @Override
    public void perform() {
        for (int i = 0; i < 3; i++) {
            Mob entity = list.get(boss.getLevel().getRandom().nextInt(list.size())).create(boss.getLevel());
            if (entity == null) continue;
            entity.moveTo(boss.getX() + boss.getLevel().getRandom().nextDouble() * 2 - 1, boss.getY(), boss.getZ() + boss.getLevel().getRandom().nextDouble() * 2 - 1);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10000, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10000, 1));
            entity.addEffect(new MobEffectInstance(AMMobEffects.MAGIC_SHIELD.get(), 10000, 1));
            if (boss.getHealth() < boss.getMaxHealth() / 2) {
                entity.addEffect(new MobEffectInstance(AMMobEffects.SHRINK.get(), 10000, 1));
            }
//            EntityUtils.makeSummonMonsterFaction(entity, false);
//            EntityUtils.setOwner(entity, host);
//            EntityUtils.setSummonDuration(entity, 1800);
            boss.getLevel().addFreshEntity(entity);
            boss.addQueuedMinion(entity);
        }
    }
}
