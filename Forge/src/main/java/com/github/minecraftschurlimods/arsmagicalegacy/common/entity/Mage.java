package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.level.Level;

public class Mage extends Mob {
    public Mage(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MAX_HEALTH, 20D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new RandomLookAroundGoal(this));
    }
}
