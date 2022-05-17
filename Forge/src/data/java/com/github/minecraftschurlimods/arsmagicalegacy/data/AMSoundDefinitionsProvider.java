package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.RegistryObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.HashSet;
import java.util.Set;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds.ARCANE_GUARDIAN_ATTACK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD;

public class AMSoundDefinitionsProvider extends SoundDefinitionsProvider {
    private final Set<ResourceLocation> sounds = new HashSet<>();

    AMSoundDefinitionsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ArsMagicaAPI.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        sound(ARCANE_GUARDIAN_ATTACK, 5);
        sound(LIGHTNING_GUARDIAN_LIGHTNING_ROD, 3);
        AMRegistries.SOUND_EVENTS.getEntries().forEach(this::singleSound);
    }

    private void sound(RegistryObject<SoundEvent> supplier, int sounds) {
        if (sounds <= 0) return;
        ResourceLocation location = supplier.getId();
        if (this.sounds.contains(location)) return;
        this.sounds.add(location);
        String subtitle = "subtitle." + location.getNamespace() + "." + location.getPath();
        String sound = location.toString().replace('.', '/');
        if (sounds == 1) {
            add(supplier, definition().with(sound(sound)).subtitle(subtitle));
        } else {
            SoundDefinition def = definition();
            for (int value = 1; value <= sounds; value++) {
                def.with(sound(sound + "_" + value));
            }
            add(supplier, def.subtitle(subtitle));
        }
    }

    private void singleSound(RegistryObject<SoundEvent> supplier) {
        sound(supplier, 1);
    }
}
