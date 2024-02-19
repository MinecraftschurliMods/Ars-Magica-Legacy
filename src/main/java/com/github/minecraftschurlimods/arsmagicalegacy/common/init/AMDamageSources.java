package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface AMDamageSources {
    ResourceKey<DamageType> NATURE_SCYTHE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature_scythe"));
    ResourceKey<DamageType> SHOCKWAVE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "shockwave"));
    ResourceKey<DamageType> THROWN_ROCK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "thrown_rock"));
    ResourceKey<DamageType> WIND = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "wind"));
    ResourceKey<DamageType> FALLING_STAR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "falling_star"));

    private static Holder<DamageType> type(RegistryAccess access, ResourceKey<DamageType> type) {
        return access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type);
    }

    static DamageSource natureScythe(Entity directSource, @Nullable Entity indirectSource) {
        return new DamageSource(type(directSource.level.registryAccess(), NATURE_SCYTHE), directSource, indirectSource);
    }

    static DamageSource shockwave(Entity source) {
        return new DamageSource(type(source.level.registryAccess(), SHOCKWAVE), source);
    }

    static DamageSource fallingStar(Entity source) {
        return new DamageSource(type(source.level.registryAccess(), FALLING_STAR), source);
    }

    static DamageSource thrownRock(Entity directSource, @Nullable Entity indirectSource) {
        return new DamageSource(type(directSource.level.registryAccess(), THROWN_ROCK), directSource, indirectSource);
    }

    static DamageSource wind(Entity directSource) {
        return new DamageSource(type(directSource.level.registryAccess(), WIND), directSource);
    }
}
