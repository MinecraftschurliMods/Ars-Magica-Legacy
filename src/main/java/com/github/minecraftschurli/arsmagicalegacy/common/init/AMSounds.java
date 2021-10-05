package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SOUND_EVENTS;

@NonExtendable
public interface AMSounds {
    RegistryObject<SoundEvent> AIR_GUARDIAN_AMBIENT = register("entity.air_guardian.ambient");
    RegistryObject<SoundEvent> AIR_GUARDIAN_ATTACK = register("entity.air_guardian.attack");
    RegistryObject<SoundEvent> AIR_GUARDIAN_DEATH = register("entity.air_guardian.death");
    RegistryObject<SoundEvent> AIR_GUARDIAN_HURT = register("entity.air_guardian.hurt");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_AMBIENT = register("entity.arcane_guardian.ambient");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_ATTACK = register("entity.arcane_guardian.attack");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_DEATH = register("entity.arcane_guardian.death");
    RegistryObject<SoundEvent> ARCANE_GUARDIAN_HURT = register("entity.arcane_guardian.hurt");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_AMBIENT = register("entity.earth_guardian.ambient");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_ATTACK = register("entity.earth_guardian.attack");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_DEATH = register("entity.earth_guardian.death");
    RegistryObject<SoundEvent> EARTH_GUARDIAN_HURT = register("entity.earth_guardian.hurt");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_AMBIENT = register("entity.ender_guardian.ambient");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_ATTACK = register("entity.ender_guardian.attack");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_DEATH = register("entity.ender_guardian.death");
    RegistryObject<SoundEvent> ENDER_GUARDIAN_HURT = register("entity.ender_guardian.hurt");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_AMBIENT = register("entity.fire_guardian.ambient");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_ATTACK = register("entity.fire_guardian.attack");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_DEATH = register("entity.fire_guardian.death");
    RegistryObject<SoundEvent> FIRE_GUARDIAN_HURT = register("entity.fire_guardian.hurt");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_AMBIENT = register("entity.life_guardian.ambient");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_ATTACK = register("entity.life_guardian.attack");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_DEATH = register("entity.life_guardian.death");
    RegistryObject<SoundEvent> LIFE_GUARDIAN_HURT = register("entity.life_guardian.hurt");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_AMBIENT = register("entity.lightning_guardian.ambient");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_ATTACK = register("entity.lightning_guardian.attack");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_DEATH = register("entity.lightning_guardian.death");
    RegistryObject<SoundEvent> LIGHTNING_GUARDIAN_HURT = register("entity.lightning_guardian.hurt");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_AMBIENT = register("entity.nature_guardian.ambient");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_ATTACK = register("entity.nature_guardian.attack");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_DEATH = register("entity.nature_guardian.death");
    RegistryObject<SoundEvent> NATURE_GUARDIAN_HURT = register("entity.nature_guardian.hurt");
    RegistryObject<SoundEvent> WATER_GUARDIAN_AMBIENT = register("entity.water_guardian.ambient");
    RegistryObject<SoundEvent> WATER_GUARDIAN_ATTACK = register("entity.water_guardian.attack");
    RegistryObject<SoundEvent> WATER_GUARDIAN_DEATH = register("entity.water_guardian.death");
    RegistryObject<SoundEvent> WATER_GUARDIAN_HURT = register("entity.water_guardian.hurt");
    RegistryObject<SoundEvent> WINTER_GUARDIAN_AMBIENT = register("entity.winter_guardian.ambient");
    RegistryObject<SoundEvent> WINTER_GUARDIAN_ATTACK = register("entity.winter_guardian.attack");
    RegistryObject<SoundEvent> WINTER_GUARDIAN_DEATH = register("entity.winter_guardian.death");
    RegistryObject<SoundEvent> WINTER_GUARDIAN_HURT = register("entity.winter_guardian.hurt");

    static void register() {
    }

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> new SoundEvent(new ResourceLocation(ArsMagicaAPI.MOD_ID, id)));
    }
}
