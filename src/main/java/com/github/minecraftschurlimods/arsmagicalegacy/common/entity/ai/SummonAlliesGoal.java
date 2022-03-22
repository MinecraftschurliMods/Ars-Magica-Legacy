package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SummonAlliesGoal extends Goal {
    private final LifeGuardian lifeGuardian;
    private int cooldown = 0;
    private boolean hasCasted = false;
    private int castTicks = 0;
    private List<EntityType<? extends Mob>> mobs;

    public SummonAlliesGoal(LifeGuardian lifeGuardian, EntityType<? extends Mob>... summons) {
        this.lifeGuardian = lifeGuardian;
        this.mobs = Arrays.stream(summons).collect(Collectors.toList());
    }

    @Override
    public boolean canUse() {
        cooldown--;
        if (lifeGuardian.getAction() != LifeGuardian.LifeGuardianAction.CASTING && cooldown <= 0) {
            hasCasted = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !hasCasted;
    }

    @Override
    public void stop() {
        lifeGuardian.setAction(LifeGuardian.LifeGuardianAction.IDLE);
        cooldown = 200;
        hasCasted = true;
        castTicks = 0;
    }

    @Override
    public void tick() {
        if (lifeGuardian.getAction() != LifeGuardian.LifeGuardianAction.CASTING) {
            lifeGuardian.setAction(LifeGuardian.LifeGuardianAction.CASTING);
        }
        castTicks++;
        if (castTicks == 16) {
            if (!lifeGuardian.getLevel().isClientSide()) {
                lifeGuardian.getLevel().playSound(null, lifeGuardian, AMSounds.LIFE_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, lifeGuardian.getRandom().nextFloat() * 0.5f + 0.5f); // should be LIFE_GUARDIAN_SUMMON
            }
//            for (int i = 0; i < 3; ++i) {
//                EntityType<?> summon = mobs.get(lifeGuardian.getLevel().getRandom().nextInt(mobs.size()));
//                Mob mob = (Mob) summon.create(lifeGuardian.getLevel());
//                if (mob == null) continue;
//                double newX = lifeGuardian.getX() + lifeGuardian.getLevel().getRandom().nextDouble() * 2 - 1;
//                double newZ = lifeGuardian.getZ() + lifeGuardian.getLevel().getRandom().nextDouble() * 2 - 1;
//                mob.moveTo(newX, lifeGuardian.getY(), newZ);
//                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 1));
//                mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 99999, 1));
//                mob.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1));
//                mob.addEffect(new MobEffectInstance(AMMobEffects.MAGIC_SHIELD.get(), 99999, 1));
//                if (lifeGuardian.getHealth() < lifeGuardian.getMaxHealth() / 2) {
//                    mob.addEffect(new MobEffectInstance(AMMobEffects.SHRINK.get(), 99999, 1));
//                }
//                EntityUtils.makeSummonMonsterFaction(mob, false);
//                EntityUtils.setOwner(mob, host);
//                EntityUtils.setSummonDuration(mob, 1800);
//                lifeGuardian.getLevel().addFreshEntity(mob);
//                lifeGuardian.addQueuedMinions(mob);
//            }
        }
        if (castTicks >= 23) {
            stop();
        }
    }
}
