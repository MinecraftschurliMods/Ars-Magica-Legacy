package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AMParticleDefinitionsProvider extends AbstractParticleDefinitionsProvider {
    protected AMParticleDefinitionsProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void addParticleDefinitions() {
        addMultiple("none_hand", 15);
        addMultiple("water_hand", 30);
        addMultiple("fire_hand", 25);
        addMultiple("earth_hand", 18);
        addMultiple("air_hand", 25);
        addMultiple("ice_hand", 30);
        addMultiple("lightning_hand", 20);
        addMultiple("nature_hand", 30);
        addMultiple("life_hand", 25);
        addMultiple("arcane_hand", 28);
        addMultiple("ender_hand", 30);
        addMultiple("arcane", 8);
        addSingle("clock");
        addSingle("ember");
        addMultiple("explosion", 24);
        addSingle("ghost");
        addSingle("leaf");
        addMultiple("lens_flare", 13);
        addMultiple("lights", 8);
        addMultiple("plant", 13);
        addMultiple("pulse", 24);
        addMultiple("rock", 16);
        addMultiple("rotating_rings", 60);
        addSingle("stardust");
        addSingle("water_ball");
        addMultiple("wind", 10);
        for (int i = 0; i < 28; i++) {
            addSingle("symbols_" + i);
        }
    }

    /**
     * Adds a particle with the given name and the particle texture arsmagicalegacy:name.
     *
     * @param name The particle name and texture path to use.
     */
    private void addSingle(String name) {
        add(name, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    /**
     * Adds a particle with the given name and the particle textures arsmagicalegacy:name_0 through arsmagicalegacy:name_(count - 1).
     *
     * @param name  The particle name to use.
     * @param count The texture count to use.
     */
    private void addMultiple(String name, int count) {
        add(name, IntStream.range(0, count).mapToObj(e -> new ResourceLocation(name + "_" + e)).toList());
    }
}
