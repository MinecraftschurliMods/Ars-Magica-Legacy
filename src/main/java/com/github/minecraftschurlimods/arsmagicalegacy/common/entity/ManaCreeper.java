package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

public class ManaCreeper extends Creeper {
    public ManaCreeper(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void explodeCreeper() {
        super.explodeCreeper();
        ManaVortex entity = ManaVortex.create(level);
        entity.moveTo(position());
        level.addFreshEntity(entity);
    }
}
