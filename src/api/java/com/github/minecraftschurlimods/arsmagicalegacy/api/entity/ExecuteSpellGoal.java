package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
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
//        boolean execute = mob.getCurrentAction() == -1 && mob.getTarget() != null && cooldownTicks <= 0;
//        if (execute){
//            if (callback == null || callback.shouldCast(mob, stack)) {
//                hasCasted = false;
//            } else {
//                execute = false;
//            }
//        }
//        return execute;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
    }
}
