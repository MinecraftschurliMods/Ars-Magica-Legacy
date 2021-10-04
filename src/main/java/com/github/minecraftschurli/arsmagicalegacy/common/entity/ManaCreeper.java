package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ManaCreeper extends Creeper {
    public ManaCreeper(EntityType<? extends Monster> type, Level level) {
        super((EntityType<? extends Creeper>) type, level);
    }

    @Override
    protected void explodeCreeper() {
        super.explodeCreeper();
    }
}
