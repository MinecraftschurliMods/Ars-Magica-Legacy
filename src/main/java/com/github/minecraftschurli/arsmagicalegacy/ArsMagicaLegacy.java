package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.easyimclib.IMCHandler;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Minecraftschurli
 * @version 2021-06-19
 */
@Mod(Constants.MOD_ID)
public final class ArsMagicaLegacy {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ArsMagicaLegacy INSTANCE;

    public static final IMCHandler IMC_HANDLER = IMCHandler.create(Constants.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(Constants.MOD_ID, "main", 0);

    private final IModInfo modInfo;

    /**
     * The Mod Constructor
     */
    public ArsMagicaLegacy() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        IMC_HANDLER.init(FMLJavaModLoadingContext.get().getModEventBus());
        modInfo = ModLoadingContext.get().getActiveContainer().getModInfo();
        INSTANCE = this;
    }

    /**
     * @return the mod name
     */
    public static String getModName() {
        return INSTANCE.modInfo.getDisplayName();
    }

    /**
     * @return the mod version as a string
     */
    public static String getVersion() {
        return INSTANCE.modInfo.getVersion().toString();
    }

    private void setup(final FMLCommonSetupEvent event) {
    }
}
