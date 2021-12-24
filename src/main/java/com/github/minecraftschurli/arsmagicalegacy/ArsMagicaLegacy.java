package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.ClientInit;
import com.github.minecraftschurli.arsmagicalegacy.client.DistProxy;
import com.github.minecraftschurli.arsmagicalegacy.common.EventHandler;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurli.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurli.arsmagicalegacy.network.BEClientSyncPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.InscriptionTableSyncPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.LearnSkillPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.NextShapeGroupPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.OpenOcculusGuiPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.SpellIconSelectPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.UpdateStepHeightPacket;
import com.github.minecraftschurli.easyimclib.IMCHandler;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
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
            throw new IllegalStateException("Tried to create mod " + ArsMagicaAPI.MOD_ID + " more than once!");
        INSTANCE = this;
        modInfo = ModLoadingContext.get().getActiveContainer().getModInfo();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IMC_HANDLER.init(bus);
        AMRegistries.init(bus);
        EventHandler.register(bus);
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
