package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.OcculusTabProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusAffinityTabRenderer;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMOcculusTabProvider extends OcculusTabProvider {
    AMOcculusTabProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate() {
        add(builder("offense", 0).setStartX(226).setStartY(46));
        add(builder("defense", 1).setStartX(181).setStartY(46));
        add(builder("utility", 2).setStartX(136).setStartY(46));
        add(builder("affinity", 3).setRenderer(OcculusAffinityTabRenderer.class));
    }
}
