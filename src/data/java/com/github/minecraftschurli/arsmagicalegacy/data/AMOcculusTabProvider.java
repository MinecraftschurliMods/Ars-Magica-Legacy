package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.OcculusTabBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.OcculusTabProvider;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusAffinityTabRenderer;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

public class AMOcculusTabProvider extends OcculusTabProvider {
    public AMOcculusTabProvider(DataGenerator generator) {
        super(generator, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public String getName() {
        return "AMOcculusTabProvider";
    }

    @Override
    protected void createOcculusTabs(Consumer<OcculusTabBuilder> consumer) {
        createOcculusTab("offense", 0).setStartX(226).setStartY(46).build(consumer);
        createOcculusTab("defense", 1).setStartX(181).setStartY(46).build(consumer);
        createOcculusTab("utility", 2).setStartX(136).setStartY(46).build(consumer);
        createOcculusTab("affinity", 3).setRenderer(OcculusAffinityTabRenderer.class).build(consumer);
    }
}
