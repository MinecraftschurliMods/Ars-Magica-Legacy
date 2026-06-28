package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Blizzard;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FallingStar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireRain;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaCreeper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaVortex;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Projectile;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Shockwave;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownRock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wave;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WintersGrasp;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMEntities {
    DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ArsMagicaApi.MOD_ID);
    DeferredHolder<EntityType<?>, EntityType<Boat>> WITCHWOOD_BOAT =
        ENTITIES.registerEntityType("witchwood_boat", (type, level) -> new Boat(type, level, AMItems.WITCHWOOD_BOAT::get), MobCategory.MISC, builder -> builder.sized(1.375f, 0.5625f).eyeHeight(0.5625f).clientTrackingRange(10));
    DeferredHolder<EntityType<?>, EntityType<ChestBoat>> WITCHWOOD_CHEST_BOAT =
        ENTITIES.registerEntityType("witchwood_chest_boat", (type, level) -> new ChestBoat(type, level, AMItems.WITCHWOOD_CHEST_BOAT::get), MobCategory.MISC, builder -> builder.sized(1.375f, 0.5625f).eyeHeight(0.5625f).clientTrackingRange(10));
    // @formatter:off
    DeferredHolder<EntityType<?>, EntityType<Blizzard>>          BLIZZARD           = register("blizzard",           Blizzard::new,          MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<FallingStar>>       FALLING_STAR       = register("falling_star",       FallingStar::new,       MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<FireRain>>          FIRE_RAIN          = register("fire_rain",          FireRain::new,          MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Projectile>>        PROJECTILE         = register("projectile",         Projectile::new,        MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Wall>>              WALL               = register("wall",               Wall::new,              MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Wave>>              WAVE               = register("wave",               Wave::new,              MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Zone>>              ZONE               = register("zone",               Zone::new,              MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Dryad>>             DRYAD              = register("dryad",              Dryad::new,             MobCategory.CREATURE, 0.6f,  1.8f);
    DeferredHolder<EntityType<?>, EntityType<ManaCreeper>>       MANA_CREEPER       = register("mana_creeper",       ManaCreeper::new,       MobCategory.MONSTER,  0.6f,  1.7f);
    DeferredHolder<EntityType<?>, EntityType<ManaVortex>>        MANA_VORTEX        = register("mana_vortex",        ManaVortex::new,        MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<WaterGuardian>>     WATER_GUARDIAN     = register("water_guardian",     WaterGuardian::new,     MobCategory.MONSTER,  1f,    1.5f);
    DeferredHolder<EntityType<?>, EntityType<FireGuardian>>      FIRE_GUARDIAN      = register("fire_guardian",      FireGuardian::new,      MobCategory.MONSTER,  1f,    3f);
    DeferredHolder<EntityType<?>, EntityType<EarthGuardian>>     EARTH_GUARDIAN     = register("earth_guardian",     EarthGuardian::new,     MobCategory.MONSTER,  1.5f,  2.5f);
    DeferredHolder<EntityType<?>, EntityType<AirGuardian>>       AIR_GUARDIAN       = register("air_guardian",       AirGuardian::new,       MobCategory.MONSTER,  0.6f,  1.75f);
    DeferredHolder<EntityType<?>, EntityType<IceGuardian>>       ICE_GUARDIAN       = register("ice_guardian",       IceGuardian::new,       MobCategory.MONSTER,  1.5f,  3f);
    DeferredHolder<EntityType<?>, EntityType<LightningGuardian>> LIGHTNING_GUARDIAN = register("lightning_guardian", LightningGuardian::new, MobCategory.MONSTER,  0.5f,  1.25f);
    DeferredHolder<EntityType<?>, EntityType<NatureGuardian>>    NATURE_GUARDIAN    = register("nature_guardian",    NatureGuardian::new,    MobCategory.MONSTER,  1.25f, 4.25f);
    DeferredHolder<EntityType<?>, EntityType<LifeGuardian>>      LIFE_GUARDIAN      = register("life_guardian",      LifeGuardian::new,      MobCategory.MONSTER,  1f,    1.25f);
    DeferredHolder<EntityType<?>, EntityType<ArcaneGuardian>>    ARCANE_GUARDIAN    = register("arcane_guardian",    ArcaneGuardian::new,    MobCategory.MONSTER,  0.9f,  2.25f);
    DeferredHolder<EntityType<?>, EntityType<EnderGuardian>>     ENDER_GUARDIAN     = register("ender_guardian",     EnderGuardian::new,     MobCategory.MONSTER,  1f,    2.25f);
    DeferredHolder<EntityType<?>, EntityType<WintersGrasp>>      WINTERS_GRASP      = register("winters_grasp",      WintersGrasp::new,      MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<NatureScythe>>      NATURE_SCYTHE      = register("nature_scythe",      NatureScythe::new,      MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<ThrownRock>>        THROWN_ROCK        = register("thrown_rock",        ThrownRock::new,        MobCategory.MISC,     0.5f,  0.5f);
    DeferredHolder<EntityType<?>, EntityType<Shockwave>>         SHOCKWAVE          = register("shockwave",          Shockwave::new,         MobCategory.MISC,     0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Whirlwind>>         WHIRLWIND          = register("whirlwind",          Whirlwind::new,         MobCategory.MISC,     0.25f, 0.25f);
    // @formatter:on

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        return ENTITIES.registerEntityType(name, factory, category, b -> b.sized(width, height).clientTrackingRange(8));
    }
}
