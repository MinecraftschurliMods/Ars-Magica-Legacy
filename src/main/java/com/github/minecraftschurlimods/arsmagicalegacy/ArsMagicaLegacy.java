package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.DistProxy;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskFuelManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.handler.EventHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.BEClientSyncPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.InscriptionTableSyncPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.LearnSkillPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.NextShapeGroupPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenOcculusGuiPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellBookNextSpellPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpellIconSelectPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.server.ServerInit;
import com.github.minecraftschurlimods.simplenetlib.NetworkHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.network.NetworkDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    public static final Logger LOGGER = LoggerFactory.getLogger(ArsMagicaAPI.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(ArsMagicaAPI.MOD_ID, "main", 1);
    private static ArsMagicaLegacy INSTANCE;
    private final IModInfo modInfo;

    public ArsMagicaLegacy() {
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
        modInfo = ModLoadingContext.get().getActiveContainer().getModInfo();
        GeckoLib.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        AMRegistries.init(bus);
        EventHandler.register(bus);
        ServerInit.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> DistProxy::init);
        Config.init();
        registerNetworkPackets();
        EtheriumHelper.instance();
        SpellDataManager.instance();
        ObeliskFuelManager.instance();
        CompatManager.preInit();
    }

    /**
     * @return The mod display name.
     */
    public static String getModName() {
        return INSTANCE.modInfo.getDisplayName();
    }

    private void registerNetworkPackets() {
        NETWORK_HANDLER.register(InscriptionTableSyncPacket.ID, InscriptionTableSyncPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(LearnSkillPacket.ID, LearnSkillPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(NextShapeGroupPacket.ID, NextShapeGroupPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(SpellBookNextSpellPacket.ID, SpellBookNextSpellPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(SpellIconSelectPacket.ID, SpellIconSelectPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(BEClientSyncPacket.ID, BEClientSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(OpenOcculusGuiPacket.ID, OpenOcculusGuiPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(SkillHelper.SkillSyncPacket.ID, SkillHelper.SkillSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(AffinityHelper.AffinitySyncPacket.ID, AffinityHelper.AffinitySyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(BurnoutHelper.BurnoutSyncPacket.ID, BurnoutHelper.BurnoutSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(MagicHelper.MagicSyncPacket.ID, MagicHelper.MagicSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(ManaHelper.ManaSyncPacket.ID, ManaHelper.ManaSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }
}
