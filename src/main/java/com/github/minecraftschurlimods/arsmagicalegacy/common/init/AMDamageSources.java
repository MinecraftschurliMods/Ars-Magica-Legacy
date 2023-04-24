package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface AMDamageSources {
    static DamageSource natureScythe(Entity directSource, @Nullable Entity indirectSource) {
        return new IndirectEntityDamageSource("nature_scythe", directSource, indirectSource).setProjectile();
    }

    static DamageSource shockwave(Entity source) {
        return new EntityDamageSource("shockwave", source);
    }

    static DamageSource fallingStar(Entity source) {
        return new EntityDamageSource("falling_star", source);
    }

    static DamageSource thrownRock(Entity directSource, @Nullable Entity indirectSource) {
        return new IndirectEntityDamageSource("thrown_rock", directSource, indirectSource).setProjectile();
    }

    static DamageSource wind(Entity directSource) {
        return new EntityDamageSource("wind", directSource);
    }
}
