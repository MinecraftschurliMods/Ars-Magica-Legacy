package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.HashSet;
import java.util.Set;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds.ARCANE_GUARDIAN_ATTACK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD;

class AMSoundDefinitionsProvider extends SoundDefinitionsProvider {
    private final Set<ResourceLocation> sounds = new HashSet<>();

    AMSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ArsMagicaAPI.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        sound(ARCANE_GUARDIAN_ATTACK, 5);
        sound(LIGHTNING_GUARDIAN_LIGHTNING_ROD, 3);
        AMRegistries.SOUND_EVENTS.getEntries().forEach(this::sound);
    }

    private void sound(Holder<SoundEvent> sound, int count) {
        if (count <= 0) return;
        ResourceLocation location = sound.unwrapKey().get().location();
        if (sounds.contains(location)) return;
        sounds.add(location);
        String subtitle = "subtitle." + location.getNamespace() + "." + location.getPath();
        String path = location.toString().replace('.', '/');
        if (count == 1) {
            add(sound.value(), definition().with(sound(path)).subtitle(subtitle));
        } else {
            SoundDefinition def = definition();
            for (int value = 1; value <= count; value++) {
                def.with(sound(path + "_" + value));
            }
            add(sound.value(), def.subtitle(subtitle));
        }
    }

    private void sound(Holder<SoundEvent> sound) {
        sound(sound, 1);
    }
}
