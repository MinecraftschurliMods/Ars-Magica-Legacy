package com.github.minecraftschurlimods.arsmagicalegacy.datagen.assets;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.HashSet;
import java.util.Set;

public final class AMSoundDefinitionProvider extends SoundDefinitionsProvider {
    private final Set<Identifier> sounds = new HashSet<>();

    public AMSoundDefinitionProvider(PackOutput output) {
        super(output, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void registerSounds() {
        sound(AMSounds.ARCANE_GUARDIAN_ATTACK, 5);
        sound(AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD, 3);
        AMSounds.SOUND_EVENTS.getEntries().forEach(this::sound);
    }

    @SuppressWarnings("DataFlowIssue")
    private void sound(Holder<SoundEvent> sound, int count) {
        if (count <= 0) return;
        Identifier identifier = sound.getKey().identifier();
        if (sounds.contains(identifier)) return;
        sounds.add(identifier);
        String subtitle = "subtitle." + identifier.getNamespace() + "." + identifier.getPath();
        String path = identifier.toString().replace('.', '/');
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
