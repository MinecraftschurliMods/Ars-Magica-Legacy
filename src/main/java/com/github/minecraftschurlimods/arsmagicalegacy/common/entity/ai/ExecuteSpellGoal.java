package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

public class ExecuteSpellGoal extends Goal {
    private final Mob mob;
    private int cooldownTicks = 0;
    private boolean hasCasted = false;
    private int castTicks = 0;

    private ItemStack stack;
    private int castPoint;
    private int duration;
    private int cooldown;
    private int bossAction;
    private boolean hasAction;
    //private ISpellCastCallback<Mob> callback;

    public ExecuteSpellGoal(Mob mob, ItemStack spell, int castPoint, int duration, int cooldown, int bossAction) {
        this.mob = mob;
        this.stack = spell;
        this.castPoint = castPoint;
        this.duration = duration;
        this.cooldown = cooldown;
        this.bossAction = bossAction;
        //this.callback = null;
        //this.setMutexBits(3);
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

    public void resetTask() {

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
