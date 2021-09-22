package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;

public interface AMEntities extends AMRegistries {
    public static final RegistryObject<EntityType<?>> FIRE_GUARDIAN = ENTITIES.register("fire_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("fire_guardian"));
    public static final RegistryObject<EntityType<?>> WATER_GUARDIAN = ENTITIES.register("water_guardian", () -> EntityType.Builder.of(WaterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("water_guardian"));
    public static final RegistryObject<EntityType<?>> EARTH_GUARDIAN = ENTITIES.register("earth_guardian", () -> EntityType.Builder.of(EarthGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("earth_guardian"));
    public static final RegistryObject<EntityType<?>> AIR_GUARDIAN = ENTITIES.register("air_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("air_guardian"));
    public static final RegistryObject<EntityType<?>> WINTER_GUARDIAN = ENTITIES.register("winter_guardian", () -> EntityType.Builder.of(WinterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("winter_guardian"));
    public static final RegistryObject<EntityType<?>> LIGHTNING_GUARDIAN = ENTITIES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("lightning_guardian"));
    public static final RegistryObject<EntityType<?>> NATRURE_GUARDIAN = ENTITIES.register("nature_guardian", () -> EntityType.Builder.of(NatureGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("nature_guardian"));
    public static final RegistryObject<EntityType<?>> LIFE_GUARDIAN = ENTITIES.register("life_guardian", () -> EntityType.Builder.of(LifeGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("life_guardian"));
    public static final RegistryObject<EntityType<?>> ARCANE_GUARDIAN = ENTITIES.register("arcane_guardian", () -> EntityType.Builder.of(ArcaneGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("arcane_guardian"));
    public static final RegistryObject<EntityType<?>> ENDER_GUARDIAN = ENTITIES.register("ender_guardian", () -> EntityType.Builder.of(EnderGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("ender_guardian"));
    public static final RegistryObject<EntityType<?>> MAGE = ENTITIES.register("mage", () -> EntityType.Builder.of(Mage::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mage"));
    public static final RegistryObject<EntityType<?>> MANA_CREEPER = ENTITIES.register("mana_creeper", () -> EntityType.Builder.of(ManaCreeper::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mana_creeper"));
    public static final RegistryObject<EntityType<?>> DRYAD = ENTITIES.register("dryad", () -> EntityType.Builder.of(Dryad::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("dryad"));
    public static void init(){}
}
