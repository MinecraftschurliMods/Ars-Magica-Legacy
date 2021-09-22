package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Mage;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ManaCreeper;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ProjectileEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WallEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaveEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WinterGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ZoneEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ENTITIES;

@NonExtendable
public interface AMEntities {
    RegistryObject<EntityType<ProjectileEntity>> PROJECTILE = ENTITIES.register("projectile", () -> EntityType.Builder.of(ProjectileEntity::new, MobCategory.MISC).clientTrackingRange(8).sized(0.3125F, 0.3125F).build("projectile"));
    RegistryObject<EntityType<WallEntity>> WALL = ENTITIES.register("wall", () -> EntityType.Builder.of(WallEntity::new, MobCategory.MISC).clientTrackingRange(8).sized(0.3125F, 0.3125F).build("wall"));
    RegistryObject<EntityType<WaveEntity>> WAVE = ENTITIES.register("wave", () -> EntityType.Builder.of(WaveEntity::new, MobCategory.MISC).clientTrackingRange(8).sized(0.3125F, 0.3125F).build("wave"));
    RegistryObject<EntityType<ZoneEntity>> ZONE = ENTITIES.register("zone", () -> EntityType.Builder.of(ZoneEntity::new, MobCategory.MISC).clientTrackingRange(8).sized(0.3125F, 0.3125F).build("zone"));
    RegistryObject<EntityType<?>> FIRE_GUARDIAN = ENTITIES.register("fire_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("fire_guardian"));
    RegistryObject<EntityType<?>> WATER_GUARDIAN = ENTITIES.register("water_guardian", () -> EntityType.Builder.of(WaterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("water_guardian"));
    RegistryObject<EntityType<?>> EARTH_GUARDIAN = ENTITIES.register("earth_guardian", () -> EntityType.Builder.of(EarthGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("earth_guardian"));
    RegistryObject<EntityType<?>> AIR_GUARDIAN = ENTITIES.register("air_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("air_guardian"));
    RegistryObject<EntityType<?>> WINTER_GUARDIAN = ENTITIES.register("winter_guardian", () -> EntityType.Builder.of(WinterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("winter_guardian"));
    RegistryObject<EntityType<?>> LIGHTNING_GUARDIAN = ENTITIES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("lightning_guardian"));
    RegistryObject<EntityType<?>> NATRURE_GUARDIAN = ENTITIES.register("nature_guardian", () -> EntityType.Builder.of(NatureGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("nature_guardian"));
    RegistryObject<EntityType<?>> LIFE_GUARDIAN = ENTITIES.register("life_guardian", () -> EntityType.Builder.of(LifeGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("life_guardian"));
    RegistryObject<EntityType<?>> ARCANE_GUARDIAN = ENTITIES.register("arcane_guardian", () -> EntityType.Builder.of(ArcaneGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("arcane_guardian"));
    RegistryObject<EntityType<?>> ENDER_GUARDIAN = ENTITIES.register("ender_guardian", () -> EntityType.Builder.of(EnderGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("ender_guardian"));
    RegistryObject<EntityType<?>> MAGE = ENTITIES.register("mage", () -> EntityType.Builder.of(Mage::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mage"));
    RegistryObject<EntityType<?>> MANA_CREEPER = ENTITIES.register("mana_creeper", () -> EntityType.Builder.of(ManaCreeper::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mana_creeper"));
    RegistryObject<EntityType<?>> DRYAD = ENTITIES.register("dryad", () -> EntityType.Builder.of(Dryad::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("dryad"));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
