package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.OcculusTabProvider;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusAffinityTabRenderer;
import net.minecraft.data.DataGenerator;

public class AMOcculusTabProvider extends OcculusTabProvider {
    public AMOcculusTabProvider(DataGenerator generator) {
        super(generator, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public String getName() {
        return "AMOcculusTabProvider";
    }

    @Override
    protected void createOcculusTabs() {
        add("offense", 0);
        add("defense", 1);
        add("utility", 2);
        add("affinity", 3, OcculusAffinityTabRenderer.class);
        add("talent", 4);
    }
}
