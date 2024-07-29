package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FallingStar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Shockwave;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownRock;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public interface AMDamageSources {
    ResourceKey<DamageType> SPELL_DROWNING =        ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_drowning"));
    ResourceKey<DamageType> SPELL_FIRE =            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_fire"));
    ResourceKey<DamageType> SPELL_FROST =           ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_frost"));
    ResourceKey<DamageType> SPELL_LIGHTNING =       ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_lightning"));
    ResourceKey<DamageType> SPELL_MAGIC =           ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_magic"));
    ResourceKey<DamageType> SPELL_PHYSICAL =        ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_physical"));
    ResourceKey<DamageType> SPELL_PHYSICAL_PLAYER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_physical_player"));
    ResourceKey<DamageType> NATURE_SCYTHE =         ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature_scythe"));
    ResourceKey<DamageType> SHOCKWAVE =             ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "shockwave"));
    ResourceKey<DamageType> THROWN_ROCK =           ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "thrown_rock"));
    ResourceKey<DamageType> WIND =                  ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "wind"));
    ResourceKey<DamageType> FALLING_STAR =          ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "falling_star"));

    private static Holder<DamageType> type(RegistryAccess access, ResourceKey<DamageType> type) {
        return access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type);
    }

    static DamageSource spellDrowning(LivingEntity source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_DROWNING), source, directSource);
    }

    static DamageSource spellFire(LivingEntity source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_FIRE), source, directSource);
    }

    static DamageSource spellFrost(LivingEntity source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_FROST), source, directSource);
    }

    static DamageSource spellLightning(LivingEntity source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_LIGHTNING), source, directSource);
    }

    static DamageSource spellMagic(LivingEntity source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_MAGIC), source, directSource);
    }

    static DamageSource spellPhysical(LivingEntity source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_PHYSICAL), source, directSource);
    }

    static DamageSource spellPhysicalPlayer(Player source, @Nullable Entity directSource) {
        return new DamageSource(type(source.level().registryAccess(), SPELL_PHYSICAL_PLAYER), source, directSource);
    }

    static DamageSource natureScythe(NatureScythe source) {
        return new DamageSource(type(source.level().registryAccess(), NATURE_SCYTHE), source.getOwner(), source);
    }

    static DamageSource shockwave(Shockwave source) {
        return new DamageSource(type(source.level().registryAccess(), SHOCKWAVE), source);
    }

    static DamageSource fallingStar(FallingStar source) {
        return new DamageSource(type(source.level().registryAccess(), FALLING_STAR), source.getOwner(), source);
    }

    static DamageSource thrownRock(ThrownRock source) {
        return new DamageSource(type(source.level().registryAccess(), THROWN_ROCK), source.getOwner(), source);
    }

    static DamageSource wind(Entity source) {
        return new DamageSource(type(source.level().registryAccess(), WIND), source);
    }
}
