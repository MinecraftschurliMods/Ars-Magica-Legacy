package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.DistProxy;
import com.github.minecraftschurlimods.arsmagicalegacy.common.EventHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.BEClientSyncPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.InscriptionTableSyncPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.LearnSkillPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.NextShapeGroupPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenOcculusGuiPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellIconSelectPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.UpdateStepHeightPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.server.ServerInit;
import com.github.minecraftschurlimods.easyimclib.IMCHandler;
import com.github.minecraftschurlimods.simplenetlib.NetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MOD_ID);
    public static final IMCHandler IMC_HANDLER = IMCHandler.create(ArsMagicaAPI.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(ArsMagicaAPI.MOD_ID, "main", 0);

    private static ArsMagicaLegacy INSTANCE;

    private final IModInfo modInfo;

    /**
     * The mod constructor
     */
    public ArsMagicaLegacy() {
        if (INSTANCE != null)
            throw LOGGER.throwing(new IllegalStateException("Tried to create mod " + ArsMagicaAPI.MOD_ID + " more than once!"));
        if (!(ArsMagicaAPI.get() instanceof ArsMagicaAPIImpl))
            throw LOGGER.throwing(new IllegalStateException("API was not initialized!"));
        INSTANCE = this;
        modInfo = ModLoadingContext.get().getActiveContainer().getModInfo();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IMC_HANDLER.init(bus);
        AMRegistries.init(bus);
        EventHandler.register(bus);
        ServerInit.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> DistProxy::init);
        Config.init();
        registerNetworkPackets();
        SkillHelper.init();
        AffinityHelper.init();
        OcculusTabManager.instance();
        SkillManager.instance();
        SpellDataManager.instance();
        AltarMaterialManager.instance();

        CompatManager.preInit();
    }

    public static String getModName() {
        return INSTANCE.modInfo.getDisplayName();
    }

    private void registerNetworkPackets() {
        NETWORK_HANDLER.register(LearnSkillPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(SpellIconSelectPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(OpenOcculusGuiPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(UpdateStepHeightPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(BEClientSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(ManaHelper.ManaSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(BurnoutHelper.BurnoutSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(MagicHelper.MagicSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(NextShapeGroupPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(InscriptionTableSyncPacket.class, NetworkDirection.PLAY_TO_SERVER);
    }
}
