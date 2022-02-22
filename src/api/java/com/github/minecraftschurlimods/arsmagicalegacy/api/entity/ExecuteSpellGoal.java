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
        boolean execute = this.caster.canCastSpell() && this.cooldownTicks <= 0;
        if (execute) {
            if (this.result == SpellCastResult.EFFECT_FAILED) {
                this.hasCasted = false;
            } else {
                execute = false;
            }
        }
        return execute;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.hasCasted && this.caster.getTarget() != null && !this.caster.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        this.caster.setIsCastingSpell(false);
        this.cooldownTicks = cooldown;
        this.hasCasted = true;
        this.castTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        this.caster.getLookControl().setLookAt(this.caster, 30, 30);
        if (this.caster.distanceToSqr(this.caster.getTarget()) > 64) {
            double deltaZ = this.caster.getTarget().getZ() - this.caster.getZ();
            double deltaX = this.caster.getTarget().getX() - this.caster.getX();

            double angle = -Math.atan2(deltaZ, deltaX);

            double newZ = this.caster.getTarget().getZ() + (Math.sin(angle) * 6);
            double newX = this.caster.getTarget().getX() + (Math.cos(angle) * 6);

            this.caster.getNavigation().moveTo(newX, this.caster.getTarget().getY(), newZ, 0.5f);
//        } else if (!this.caster.see) { // maybe falsch
//            this.caster.getNavigation().moveTo(this.caster.getTarget(), 0.5f);
        } else {
            if (this.caster.isCastingSpell()) {
                this.caster.setIsCastingSpell(true);
            }

            this.castTicks++;
            if (this.castTicks >= duration) {
                if (!this.caster.level.isClientSide() && this.caster instanceof AbstractBoss boss) {
                    this.caster.level.playSound(null, this.caster, boss.getAttackSound(), SoundSource.HOSTILE, 1, 1);
                }
                this.caster.lookAt(this.caster.getTarget(), 180, 180);
                spell.cast(caster, caster.level, 0, false, false);
                this.stop();
            }
        }

    }
}
