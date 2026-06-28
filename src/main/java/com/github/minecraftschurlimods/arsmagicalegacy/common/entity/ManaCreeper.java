package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;

public class ManaCreeper extends Creeper {
    public ManaCreeper(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void explodeCreeper() {
        super.explodeCreeper();
        ManaVortex entity = AMEntities.MANA_VORTEX.get().create(level(), EntitySpawnReason.MOB_SUMMONED);
        if (entity != null) {
            entity.snapTo(position());
            level().addFreshEntity(entity);
        }
    }
}
