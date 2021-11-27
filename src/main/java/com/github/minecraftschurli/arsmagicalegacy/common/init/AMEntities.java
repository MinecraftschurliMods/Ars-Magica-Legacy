package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.ProjectileEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WallEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaveEntity;
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

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
