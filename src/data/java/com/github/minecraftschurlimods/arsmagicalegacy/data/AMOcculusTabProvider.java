package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.OcculusTabProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusAffinityTabRenderer;

class AMOcculusTabProvider extends OcculusTabProvider {
    AMOcculusTabProvider() {
        super(ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        add("offense", builder(0).setStartX(226).setStartY(46).build());
        add("defense", builder(1).setStartX(181).setStartY(46).build());
        add("utility", builder(2).setStartX(136).setStartY(46).build());
        add("affinity", builder(3).setRenderer(OcculusAffinityTabRenderer.class).build());
    }
}
