package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
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

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ENTITIES;

@NonExtendable
public interface AMEntities {
    RegistryObject<EntityType<Projectile>>        PROJECTILE         = ENTITIES.register("projectile",         () -> EntityType.Builder.of(Projectile::new,        MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("projectile"));
    RegistryObject<EntityType<Wall>>              WALL               = ENTITIES.register("wall",               () -> EntityType.Builder.of(Wall::new,              MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("wall"));
    RegistryObject<EntityType<Wave>>              WAVE               = ENTITIES.register("wave",               () -> EntityType.Builder.of(Wave::new,              MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("wave"));
    RegistryObject<EntityType<Zone>>              ZONE               = ENTITIES.register("zone",               () -> EntityType.Builder.of(Zone::new,              MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("zone"));
    RegistryObject<EntityType<WaterGuardian>>     WATER_GUARDIAN     = ENTITIES.register("water_guardian",     () -> EntityType.Builder.of(WaterGuardian::new,     MobCategory.MONSTER).clientTrackingRange(8).sized(    1,  1.5F).build("water_guardian"));
    RegistryObject<EntityType<FireGuardian>>      FIRE_GUARDIAN      = ENTITIES.register("fire_guardian",      () -> EntityType.Builder.of(FireGuardian::new,      MobCategory.MONSTER).clientTrackingRange(8).sized(    1,     3).build("fire_guardian"));
    RegistryObject<EntityType<EarthGuardian>>     EARTH_GUARDIAN     = ENTITIES.register("earth_guardian",     () -> EntityType.Builder.of(EarthGuardian::new,     MobCategory.MONSTER).clientTrackingRange(8).sized( 1.5F,  2.5F).build("earth_guardian"));
    RegistryObject<EntityType<AirGuardian>>       AIR_GUARDIAN       = ENTITIES.register("air_guardian",       () -> EntityType.Builder.of(AirGuardian::new,       MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F, 1.75F).build("air_guardian"));
    RegistryObject<EntityType<IceGuardian>>       ICE_GUARDIAN       = ENTITIES.register("ice_guardian",       () -> EntityType.Builder.of(IceGuardian::new,       MobCategory.MONSTER).clientTrackingRange(8).sized( 1.5F,     3).build("ice_guardian"));
    RegistryObject<EntityType<LightningGuardian>> LIGHTNING_GUARDIAN = ENTITIES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized( 0.5F, 1.25F).build("lightning_guardian"));
    RegistryObject<EntityType<NatureGuardian>>    NATURE_GUARDIAN    = ENTITIES.register("nature_guardian",    () -> EntityType.Builder.of(NatureGuardian::new,    MobCategory.MONSTER).clientTrackingRange(8).sized(1.25F, 4.25F).build("nature_guardian"));
    RegistryObject<EntityType<LifeGuardian>>      LIFE_GUARDIAN      = ENTITIES.register("life_guardian",      () -> EntityType.Builder.of(LifeGuardian::new,      MobCategory.MONSTER).clientTrackingRange(8).sized(    1, 1.25F).build("life_guardian"));
    RegistryObject<EntityType<ArcaneGuardian>>    ARCANE_GUARDIAN    = ENTITIES.register("arcane_guardian",    () -> EntityType.Builder.of(ArcaneGuardian::new,    MobCategory.MONSTER).clientTrackingRange(8).sized( 0.9F, 2.25F).build("arcane_guardian"));
    RegistryObject<EntityType<EnderGuardian>>     ENDER_GUARDIAN     = ENTITIES.register("ender_guardian",     () -> EntityType.Builder.of(EnderGuardian::new,     MobCategory.MONSTER).clientTrackingRange(8).sized(    1, 2.25F).build("ender_guardian"));
    RegistryObject<EntityType<WintersGrasp>>      WINTERS_GRASP      = ENTITIES.register("winters_grasp",      () -> EntityType.Builder.of(WintersGrasp::new,      MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("winters_grasp"));
    RegistryObject<EntityType<NatureScythe>>      NATURE_SCYTHE      = ENTITIES.register("nature_scythe",      () -> EntityType.Builder.of(NatureScythe::new,      MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("nature_scythe"));
    RegistryObject<EntityType<ThrownRock>>        THROWN_ROCK        = ENTITIES.register("thrown_rock",        () -> EntityType.Builder.of(ThrownRock::new,        MobCategory.MISC)   .clientTrackingRange(8).sized( 0.5F,  0.5F).build("thrown_rock"));
    RegistryObject<EntityType<Shockwave>>         SHOCKWAVE          = ENTITIES.register("shockwave",          () -> EntityType.Builder.of(Shockwave::new,         MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("shockwave"));
    RegistryObject<EntityType<Whirlwind>>         WHIRLWIND          = ENTITIES.register("whirlwind",          () -> EntityType.Builder.of(Whirlwind::new,         MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("whirlwind"));
    RegistryObject<EntityType<Dryad>>             DRYAD              = ENTITIES.register("dryad",              () -> EntityType.Builder.of(Dryad::new,             MobCategory.AMBIENT).clientTrackingRange(8).sized( 0.6F,  1.8F).build("dryad"));
    RegistryObject<EntityType<Mage>>              MAGE               = ENTITIES.register("mage",               () -> EntityType.Builder.of(Mage::new,              MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F,  1.8F).build("mage"));
    RegistryObject<EntityType<ManaCreeper>>       MANA_CREEPER       = ENTITIES.register("mana_creeper",       () -> EntityType.Builder.of(ManaCreeper::new,       MobCategory.MONSTER).clientTrackingRange(8).sized( 0.6F,  1.7F).build("mana_creeper"));
    RegistryObject<EntityType<ManaVortex>>        MANA_VORTEX        = ENTITIES.register("mana_vortex",        () -> EntityType.Builder.of(ManaVortex::new,        MobCategory.MISC)   .clientTrackingRange(8).sized(0.25F, 0.25F).build("mana_vortex"));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
