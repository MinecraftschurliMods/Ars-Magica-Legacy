package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SOUND_EVENTS;

@NonExtendable
public interface AMSounds {
    RegistryObject<SoundEvent> WATER_GUARDIAN_AMBIENT = register("entity.water_guardian.ambient");
    RegistryObject<SoundEvent> WATER_GUARDIAN_ATTACK = register("entity.water_guardian.attack");
    RegistryObject<SoundEvent> WATER_GUARDIAN_DEATH = register("entity.water_guardian.death");
    RegistryObject<SoundEvent> WATER_GUARDIAN_HURT = register("entity.water_guardian.hurt");

    static void register() {
    }

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> new SoundEvent(new ResourceLocation(ArsMagicaAPI.MOD_ID, id)));
    }
}
