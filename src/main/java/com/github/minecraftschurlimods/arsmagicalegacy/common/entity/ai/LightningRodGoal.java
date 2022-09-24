package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
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
        return (!hasBolted || !hasThrown) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return (!hasBolted || !hasThrown) && super.canContinueToUse();
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
        int ticks = boss.getTicksInAction();
        LivingEntity target = boss.getTarget();
        if (target == null) return;
        if (ticks <= 20) {
            startPos = new Vec3((float) target.getX(), (float) target.getY(), (float) target.getZ());
            return;
        }
        if (ticks > 80 && ticks <= 140) {
            if (!boss.getLevel().isClientSide() && ticks % 20 == 0) {
                boss.getLevel().playSound(null, boss, AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD.get(), SoundSource.HOSTILE, 1.0f, boss.getRandom().nextFloat() * 0.5f + 0.5f);
            }
        }
        if (ticks <= 80) {
            boss.teleportTo(startPos.x(), startPos.y() + (ticks - 25) * 0.1, startPos.z());
            target.setNoGravity(true);
            if (!boss.getLevel().isClientSide() && ticks == 30) {
                boss.getLevel().playSound(null, boss, AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD.get(), SoundSource.HOSTILE, 1.0f, boss.getRandom().nextFloat() * 0.5f + 0.5f);
            }
        } else if (ticks <= 100) {
            boss.teleportTo(startPos.x(), startPos.y() + 6, startPos.z());
        } else if (ticks <= 140) {
            boss.teleportTo(startPos.x(), startPos.y() + 6, startPos.z());
            if (ticks > 120) {
                target.hurt(DamageSource.LIGHTNING_BOLT, 3);
            }
            if (!boss.getLevel().isClientSide() && ticks % 20 == 0) {
                boss.getLevel().playSound(null, boss, AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get(), SoundSource.HOSTILE, 1.0f, boss.getRandom().nextFloat() * 0.5f + 0.5f);
            }
            if (boss.getLevel().isClientSide()) {
                // Particle
            }
        } else if (ticks <= 150 && !hasThrown) {
            hasThrown = true;
            target.push(0, -3, 0);
            target.fallDistance = 5;
            target.setNoGravity(false);
        } else if (ticks > 160 && !hasBolted) {
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
