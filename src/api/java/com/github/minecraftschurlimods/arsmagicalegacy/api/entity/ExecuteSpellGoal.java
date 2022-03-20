package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ExecuteSpellGoal<T extends Mob & ISpellCasterEntity> extends Goal {
    private final T caster;
    private final ISpell spell;
    private boolean hasCasted = false;
    private int castTicks = 0;
    private int cooldownTicks = 0;
    private int duration;
    private int cooldown;
    private SpellCastResult result;

    public ExecuteSpellGoal(T caster, ISpell spell, int duration, int cooldown) {
        this.caster = caster;
        this.spell = spell;
        this.duration = duration;
        this.cooldown = cooldown;
        this.result = SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public boolean canUse() {
        cooldownTicks--;
        boolean execute = caster.canCastSpell() && cooldownTicks <= 0;
        if (execute) {
            if (result == SpellCastResult.EFFECT_FAILED) {
                hasCasted = false;
            } else {
                execute = false;
            }
        }
        return execute;
    }

    @Override
    public boolean canContinueToUse() {
        return !hasCasted && caster.getTarget() != null && !caster.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        caster.setIsCastingSpell(false);
        cooldownTicks = cooldown;
        hasCasted = true;
        castTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (caster.getTarget() != null && caster.distanceToSqr(caster.getTarget()) > 64) {
            caster.getLookControl().setLookAt(caster.getTarget(), 30, 30);
            double deltaZ = caster.getTarget().getZ() - caster.getZ();
            double deltaX = caster.getTarget().getX() - caster.getX();
            double angle = -Math.atan2(deltaZ, deltaX);
            double newZ = caster.getTarget().getZ() + (Math.sin(angle) * 6);
            double newX = caster.getTarget().getX() + (Math.cos(angle) * 6);
            caster.getNavigation().moveTo(newX, caster.getTarget().getY(), newZ, 0.5f);
//        } else if (!caster.see) { // maybe falsch
//            caster.getNavigation().moveTo(caster.getTarget(), 0.5f);
        } else {
            if (caster.isCastingSpell()) {
                caster.setIsCastingSpell(true);
            }
            castTicks++;
            if (castTicks >= duration) {
                if (!caster.level.isClientSide() && caster instanceof AbstractBoss boss && boss.getAttackSound() != null) {
                    caster.level.playSound(null, caster, boss.getAttackSound(), SoundSource.HOSTILE, 1, 1);
                }
                if (caster.getTarget() != null) {
                    caster.lookAt(caster.getTarget(), 180, 180);
                }
                spell.cast(caster, caster.level, 0, false, false);
                stop();
            }
        }
    }
}
