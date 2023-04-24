package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

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
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Mage;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ENTITY_TYPES;

@NonExtendable
public interface AMEntities {
    RegistryObject<EntityType<Projectile>>        PROJECTILE         = ENTITY_TYPES.register("projectile",         () -> EntityType.Builder.of(Projectile::new,        MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("projectile"));
    RegistryObject<EntityType<Wall>>              WALL               = ENTITY_TYPES.register("wall",               () -> EntityType.Builder.of(Wall::new,              MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("wall"));
    RegistryObject<EntityType<Wave>>              WAVE               = ENTITY_TYPES.register("wave",               () -> EntityType.Builder.of(Wave::new,              MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("wave"));
    RegistryObject<EntityType<Zone>>              ZONE               = ENTITY_TYPES.register("zone",               () -> EntityType.Builder.of(Zone::new,              MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("zone"));
    RegistryObject<EntityType<Blizzard>>          BLIZZARD           = ENTITY_TYPES.register("blizzard",           () -> EntityType.Builder.of(Blizzard::new,          MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("blizzard"));
    RegistryObject<EntityType<FireRain>>          FIRE_RAIN          = ENTITY_TYPES.register("fire_rain",          () -> EntityType.Builder.of(FireRain::new,          MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("fire_rain"));
    RegistryObject<EntityType<FallingStar>>       FALLING_STAR       = ENTITY_TYPES.register("falling_star",       () -> EntityType.Builder.of(FallingStar::new,       MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("falling_star"));
    RegistryObject<EntityType<WaterGuardian>>     WATER_GUARDIAN     = ENTITY_TYPES.register("water_guardian",     () -> EntityType.Builder.of(WaterGuardian::new,     MobCategory.MONSTER).clientTrackingRange(8).sized(    1,  1.5F).build("water_guardian"));
    RegistryObject<EntityType<FireGuardian>>      FIRE_GUARDIAN      = ENTITY_TYPES.register("fire_guardian",      () -> EntityType.Builder.of(FireGuardian::new,      MobCategory.MONSTER).clientTrackingRange(8).sized(    1,     3).build("fire_guardian"));
    RegistryObject<EntityType<EarthGuardian>>     EARTH_GUARDIAN     = ENTITY_TYPES.register("earth_guardian",     () -> EntityType.Builder.of(EarthGuardian::new,     MobCategory.MONSTER).clientTrackingRange(8).sized( 1.5F,  2.5F).build("earth_guardian"));
    RegistryObject<EntityType<AirGuardian>>       AIR_GUARDIAN       = ENTITY_TYPES.register("air_guardian",       () -> EntityType.Builder.of(AirGuardian::new,       MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F, 1.75F).build("air_guardian"));
    RegistryObject<EntityType<IceGuardian>>       ICE_GUARDIAN       = ENTITY_TYPES.register("ice_guardian",       () -> EntityType.Builder.of(IceGuardian::new,       MobCategory.MONSTER).clientTrackingRange(8).sized( 1.5F,     3).build("ice_guardian"));
    RegistryObject<EntityType<LightningGuardian>> LIGHTNING_GUARDIAN = ENTITY_TYPES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.5F, 1.25F).build("lightning_guardian"));
    RegistryObject<EntityType<NatureGuardian>>    NATURE_GUARDIAN    = ENTITY_TYPES.register("nature_guardian",    () -> EntityType.Builder.of(NatureGuardian::new,    MobCategory.MONSTER).clientTrackingRange(8).sized(1.25F, 4.25F).build("nature_guardian"));
    RegistryObject<EntityType<LifeGuardian>>      LIFE_GUARDIAN      = ENTITY_TYPES.register("life_guardian",      () -> EntityType.Builder.of(LifeGuardian::new,      MobCategory.MONSTER).clientTrackingRange(8).sized(    1, 1.25F).build("life_guardian"));
    RegistryObject<EntityType<ArcaneGuardian>>    ARCANE_GUARDIAN    = ENTITY_TYPES.register("arcane_guardian",    () -> EntityType.Builder.of(ArcaneGuardian::new,    MobCategory.MONSTER).clientTrackingRange(8).sized( 0.9F, 2.25F).build("arcane_guardian"));
    RegistryObject<EntityType<EnderGuardian>>     ENDER_GUARDIAN     = ENTITY_TYPES.register("ender_guardian",     () -> EntityType.Builder.of(EnderGuardian::new,     MobCategory.MONSTER).clientTrackingRange(8).sized(    1, 2.25F).build("ender_guardian"));
    RegistryObject<EntityType<WintersGrasp>>      WINTERS_GRASP      = ENTITY_TYPES.register("winters_grasp",      () -> EntityType.Builder.of(WintersGrasp::new,      MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("winters_grasp"));
    RegistryObject<EntityType<NatureScythe>>      NATURE_SCYTHE      = ENTITY_TYPES.register("nature_scythe",      () -> EntityType.Builder.of(NatureScythe::new,      MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("nature_scythe"));
    RegistryObject<EntityType<ThrownRock>>        THROWN_ROCK        = ENTITY_TYPES.register("thrown_rock",        () -> EntityType.Builder.of(ThrownRock::new,        MobCategory.MISC)   .clientTrackingRange(8).sized( 0.5F,  0.5F).build("thrown_rock"));
    RegistryObject<EntityType<Shockwave>>         SHOCKWAVE          = ENTITY_TYPES.register("shockwave",          () -> EntityType.Builder.of(Shockwave::new,         MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("shockwave"));
    RegistryObject<EntityType<Whirlwind>>         WHIRLWIND          = ENTITY_TYPES.register("whirlwind",          () -> EntityType.Builder.of(Whirlwind::new,         MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("whirlwind"));
    RegistryObject<EntityType<Dryad>>             DRYAD              = ENTITY_TYPES.register("dryad",              () -> EntityType.Builder.of(Dryad::new,             MobCategory.AMBIENT).clientTrackingRange(8).sized( 0.6F,  1.8F).build("dryad"));
    RegistryObject<EntityType<Mage>>              MAGE               = ENTITY_TYPES.register("mage",               () -> EntityType.Builder.of(Mage::new,              MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F,  1.8F).build("mage"));
    RegistryObject<EntityType<ManaCreeper>>       MANA_CREEPER       = ENTITY_TYPES.register("mana_creeper",       () -> EntityType.Builder.of(ManaCreeper::new,       MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F,  1.7F).build("mana_creeper"));
    RegistryObject<EntityType<ManaVortex>>        MANA_VORTEX        = ENTITY_TYPES.register("mana_vortex",        () -> EntityType.Builder.of(ManaVortex::new,        MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("mana_vortex"));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
