package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.InscriptionTableScreen;
import net.neoforged.bus.api.IEventBus;

public final class DistProxy {
    /**
     * Runs client initialization. Needs a separate class to prevent unwanted classloading.
     */
    public static void init(IEventBus modEventBus) {
        ClientInit.init(modEventBus);
    }

    public static void onInscriptionTableSlotChanged() {
        InscriptionTableScreen.onSlotChanged();
    }
}
