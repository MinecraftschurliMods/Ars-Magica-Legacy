package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ENTITIES;

public interface AMEntities {
    RegistryObject<EntityType<WaterGuardian>> WATER_GUARDIAN = ENTITIES.register("water_guardian", () -> EntityType.Builder.of(WaterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("water_guardian"));
    RegistryObject<EntityType<FireGuardian>> FIRE_GUARDIAN = ENTITIES.register("fire_guardian", () -> EntityType.Builder.of(FireGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("fire_guardian"));
    RegistryObject<EntityType<EarthGuardian>> EARTH_GUARDIAN = ENTITIES.register("earth_guardian", () -> EntityType.Builder.of(EarthGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("earth_guardian"));
    RegistryObject<EntityType<AirGuardian>> AIR_GUARDIAN = ENTITIES.register("air_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("air_guardian"));
    RegistryObject<EntityType<WinterGuardian>> WINTER_GUARDIAN = ENTITIES.register("winter_guardian", () -> EntityType.Builder.of(WinterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("winter_guardian"));
    RegistryObject<EntityType<LightningGuardian>> LIGHTNING_GUARDIAN = ENTITIES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("lightning_guardian"));
    RegistryObject<EntityType<NatureGuardian>> NATURE_GUARDIAN = ENTITIES.register("nature_guardian", () -> EntityType.Builder.of(NatureGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("nature_guardian"));
    RegistryObject<EntityType<LifeGuardian>> LIFE_GUARDIAN = ENTITIES.register("life_guardian", () -> EntityType.Builder.of(LifeGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("life_guardian"));
    RegistryObject<EntityType<ArcaneGuardian>> ARCANE_GUARDIAN = ENTITIES.register("arcane_guardian", () -> EntityType.Builder.of(ArcaneGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("arcane_guardian"));
    RegistryObject<EntityType<EnderGuardian>> ENDER_GUARDIAN = ENTITIES.register("ender_guardian", () -> EntityType.Builder.of(EnderGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("ender_guardian"));
    RegistryObject<EntityType<Mage>> MAGE = ENTITIES.register("mage", () -> EntityType.Builder.of(Mage::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mage"));
    RegistryObject<EntityType<ManaCreeper>> MANA_CREEPER = ENTITIES.register("mana_creeper", () -> EntityType.Builder.of(ManaCreeper::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mana_creeper"));
    RegistryObject<EntityType<Dryad>> DRYAD = ENTITIES.register("dryad", () -> EntityType.Builder.of(Dryad::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("dryad"));

    @Internal
    static void init() {
    }
}
