package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.AMMobEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.Effect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SummonAllies extends Goal {
    private final LifeGuardian lifeGuardian;
    private int cooldown = 0;
    private boolean hasCasted = false;
    private int castTicks = 0;
    private Class<? extends Mob>[] mobs;  // should be EntityCreature

    public SummonAllies(LifeGuardian lifeGuardian, Class<? extends Mob>... summons) {
        this.lifeGuardian = lifeGuardian;
        this.mobs = summons;
    }

    @Override
    public boolean canUse() {
        cooldown--;
        if (lifeGuardian.getLifeGuardianAction() != LifeGuardian.LifeGuardianAction.CASTING && cooldown <= 0) {
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
        lifeGuardian.setLifeGuardianAction(LifeGuardian.LifeGuardianAction.IDLE);
        cooldown = 200;
        hasCasted = true;
        castTicks = 0;
    }

    @Override
    public void tick() {
        if (lifeGuardian.getLifeGuardianAction() != LifeGuardian.LifeGuardianAction.CASTING) {
            lifeGuardian.setLifeGuardianAction(LifeGuardian.LifeGuardianAction.CASTING);
        }
        castTicks++;
        if (castTicks == 16) {
            if (!lifeGuardian.level.isClientSide()) {
                lifeGuardian.level.playSound(null, lifeGuardian, AMSounds.LIFE_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, lifeGuardian.getRandom().nextFloat() * 0.5f + 0.5f); // should be LIFE_GUARDIAN_SUMMON
            }
            int numAllies = 3;
//            for (int i = 0; i < numAllies; ++i) {
//                Class<? extends Mob> summon = mobs[lifeGuardian.level.getRandom().nextInt(mobs.length)];
//                try {
//                    Constructor<? extends Mob> ctor = summon.getConstructor(World.class);
//                    Mob mob = ctor.newInstance(lifeGuardian.level);
//                    double newX = lifeGuardian.getX() + lifeGuardian.level.getRandom().nextDouble() * 2 - 1;
//                    double newZ = lifeGuardian.getZ() + lifeGuardian.level.getRandom().nextDouble() * 2 - 1;
//                    mob.setPos(newX, lifeGuardian.getY(), newZ);
//                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999, 1)); // maybe wrong
//                    mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 99999, 1));  // maybe wrong
//                    mob.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1));  // maybe wrong
//                    mob.addEffect(new MobEffectInstance(AMMobEffects.MAGIC_SHIELD.get(), 99999, 1));  // maybe wrong
//
//                    if (lifeGuardian.getHealth() < lifeGuardian.getMaxHealth() / 2) {
//                        mob.addEffect(new MobEffectInstance(AMMobEffects.SHRINK.get(), 99999, 1));  // maybe wrong
//                    }
//
//                    EntityUtils.makeSummon_MonsterFaction(mob, false);
//                    EntityUtils.setOwner(mob, host);
//                    EntityUtils.setSummonDuration(mob, 1800);
//                    lifeGuardian.level.addFreshEntity(mob);
//                    lifeGuardian.addQueuedMinions(mob);
//
//                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
//                    e.printStackTrace();
//                    return;
//                }
//            }
        }
        if (castTicks >= 23) {
            stop();
        }
    }
}
