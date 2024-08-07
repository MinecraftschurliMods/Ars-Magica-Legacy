package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientInit;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.handler.EventHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.RiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.NetworkInit;
import com.github.minecraftschurlimods.arsmagicalegacy.server.ServerInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    public static final Logger LOGGER = LoggerFactory.getLogger(ArsMagicaAPI.MOD_ID);
    private static ArsMagicaLegacy INSTANCE;

    private final ModContainer modContainer;

    public ArsMagicaLegacy(ModContainer container, IEventBus bus) {
        if (INSTANCE != null) {
            IllegalStateException exception = new IllegalStateException("Tried to create mod " + ArsMagicaAPI.MOD_ID + " more than once!");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
        if (!(ArsMagicaAPI.get() instanceof ArsMagicaAPIImpl)) {
            IllegalStateException exception = new IllegalStateException("API was not initialized!");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
        INSTANCE = this;
        modContainer = container;
        //GeckoLib.initialize(bus);
        AMRegistries.init(bus);
        EventHandler.register(bus);
        ServerInit.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientInit.init(bus);
        }
        Config.init(container);
        NetworkInit.init(bus);
        EtheriumHelper.instance();
        MagicHelper.instance();
        BurnoutHelper.instance();
        ManaHelper.instance();
        SkillHelper.instance();
        AffinityHelper.instance();
        RiftHelper.instance();
        ContingencyHelper.instance();
        SpellDataManager.instance();
        CompatManager.preInit(bus);
    }

    public static ModContainer getModContainer() {
        return INSTANCE.modContainer;
    }

    /**
     * @return The mod display name.
     */
    public static String getModName() {
        return getModContainer().getModInfo().getDisplayName();
    }
}
