package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LightningRodGoal extends AbstractBossGoal<LightningGuardian> {
    private Vec3 startPos = null;
    private boolean hasBolted = false;
    private boolean hasThrown = false;

    public LightningRodGoal(LightningGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 160);
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public void stop() {
        super.stop();
        startPos = null;
        hasBolted = false;
        hasThrown = false;
        if (boss.getTarget() != null) {
            boss.getTarget().setNoGravity(false);
        }
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = boss.getTarget();
        if (target == null) return;
        if (!boss.getLevel().isClientSide() && ticks % 20 == 0) {
            boss.getLevel().playSound(null, boss, AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD.get(), SoundSource.HOSTILE, 1.0f, boss.getRandom().nextFloat() * 0.5f + 0.5f);
        }
        if (ticks <= 10) {
            startPos = new Vec3((float) target.getX(), (float) target.getY(), (float) target.getZ());
        } else if (ticks <= 40) {
            target.moveTo(startPos.x(), startPos.y() + (ticks - 10) * 0.2, startPos.z());
            target.fallDistance = 0;
            if (!boss.getLevel().isClientSide() && ticks == 30) {
                boss.getLevel().playSound(null, boss, AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD.get(), SoundSource.HOSTILE, 1.0f, boss.getRandom().nextFloat() * 0.5f + 0.5f);
            }
        } else if (ticks <= 80) {
            target.moveTo(startPos.x(), startPos.y() + 6, startPos.z());
            if (ticks > 50) {
                target.hurt(DamageSource.LIGHTNING_BOLT, 20);
            }
            if (!boss.getLevel().isClientSide() && ticks % 20 == 0) {
                boss.getLevel().playSound(null, boss, AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get(), SoundSource.HOSTILE, 1.0f, boss.getRandom().nextFloat() * 0.5f + 0.5f);
            }
        } else if (ticks <= 90 && !hasThrown) {
            hasThrown = true;
            target.push(0, -4, 0);
            target.fallDistance = 8;
        } else if (ticks <= 100 && !hasBolted) {
            hasBolted = true;
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, boss.getLevel());
            bolt.setPos(target.getX(), target.getY(), target.getZ());
            boss.getLevel().addFreshEntity(bolt);
        }
    }

    @Override
    public void perform() {
    }
}
