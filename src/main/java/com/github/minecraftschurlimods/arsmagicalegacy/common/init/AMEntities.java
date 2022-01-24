package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Mage;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaCreeper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Projectile;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wave;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WinterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ENTITIES;

public interface AMEntities {
    RegistryObject<EntityType<Projectile>> PROJECTILE = ENTITIES.register("projectile", () -> EntityType.Builder.of(Projectile::new, MobCategory.MISC).clientTrackingRange(8).sized(0.25F, 0.25F).build("projectile"));
    RegistryObject<EntityType<Wall>> WALL = ENTITIES.register("wall", () -> EntityType.Builder.of(Wall::new, MobCategory.MISC).clientTrackingRange(8).sized(0.25F, 0.25F).build("wall"));
    RegistryObject<EntityType<Wave>> WAVE = ENTITIES.register("wave", () -> EntityType.Builder.of(Wave::new, MobCategory.MISC).clientTrackingRange(8).sized(0.25F, 0.25F).build("wave"));
    RegistryObject<EntityType<Zone>> ZONE = ENTITIES.register("zone", () -> EntityType.Builder.of(Zone::new, MobCategory.MISC).clientTrackingRange(8).sized(0.25F, 0.25F).build("zone"));
    RegistryObject<EntityType<WaterGuardian>> WATER_GUARDIAN = ENTITIES.register("water_guardian", () -> EntityType.Builder.of(WaterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1, 2).build("water_guardian"));
    RegistryObject<EntityType<FireGuardian>> FIRE_GUARDIAN = ENTITIES.register("fire_guardian", () -> EntityType.Builder.of(FireGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1, 4).build("fire_guardian"));
    RegistryObject<EntityType<EarthGuardian>> EARTH_GUARDIAN = ENTITIES.register("earth_guardian", () -> EntityType.Builder.of(EarthGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1.5F, 3.5F).build("earth_guardian"));
    RegistryObject<EntityType<AirGuardian>> AIR_GUARDIAN = ENTITIES.register("air_guardian", () -> EntityType.Builder.of(AirGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.6F, 2.5F).build("air_guardian"));
    RegistryObject<EntityType<WinterGuardian>> WINTER_GUARDIAN = ENTITIES.register("winter_guardian", () -> EntityType.Builder.of(WinterGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1.25F, 3.25F).build("winter_guardian"));
    RegistryObject<EntityType<LightningGuardian>> LIGHTNING_GUARDIAN = ENTITIES.register("lightning_guardian", () -> EntityType.Builder.of(LightningGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1.75F, 3).build("lightning_guardian"));
    RegistryObject<EntityType<NatureGuardian>> NATURE_GUARDIAN = ENTITIES.register("nature_guardian", () -> EntityType.Builder.of(NatureGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1.65F, 4.75F).build("nature_guardian"));
    RegistryObject<EntityType<LifeGuardian>> LIFE_GUARDIAN = ENTITIES.register("life_guardian", () -> EntityType.Builder.of(LifeGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1, 2).build("life_guardian"));
    RegistryObject<EntityType<ArcaneGuardian>> ARCANE_GUARDIAN = ENTITIES.register("arcane_guardian", () -> EntityType.Builder.of(ArcaneGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1, 3).build("arcane_guardian"));
    RegistryObject<EntityType<EnderGuardian>> ENDER_GUARDIAN = ENTITIES.register("ender_guardian", () -> EntityType.Builder.of(EnderGuardian::new, MobCategory.MONSTER).clientTrackingRange(8).sized(1, 3).build("ender_guardian"));
    RegistryObject<EntityType<Dryad>> DRYAD = ENTITIES.register("dryad", () -> EntityType.Builder.of(Dryad::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("dryad"));
    RegistryObject<EntityType<Mage>> MAGE = ENTITIES.register("mage", () -> EntityType.Builder.of(Mage::new, MobCategory.MONSTER).clientTrackingRange(8).sized(5, 2).build("mage"));
    RegistryObject<EntityType<ManaCreeper>> MANA_CREEPER = ENTITIES.register("mana_creeper", () -> EntityType.Builder.of(ManaCreeper::new, MobCategory.MONSTER).clientTrackingRange(8).sized(0.6F, 1.7F).build("mana_creeper"));

    @Internal
    static void register() {
    }
}
