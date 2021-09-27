package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHolder;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IKnowledgeHolder;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.network.LearnSkillPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.SyncAffinityPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.SyncKnowledgePacket;
import com.github.minecraftschurli.arsmagicalegacy.network.SyncSkillsPacket;
import com.github.minecraftschurli.easyimclib.IMCHandler;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MOD_ID);
    private static ArsMagicaLegacy INSTANCE;

    public static final IMCHandler IMC_HANDLER = IMCHandler.create(ArsMagicaAPI.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(ArsMagicaAPI.MOD_ID, "main", 0);

    private final IModInfo modInfo;

    /**
     * The Mod Constructor
     */
    public ArsMagicaLegacy() {
        INSTANCE = this;
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::registerCapabilities);
        IMC_HANDLER.init(bus);
        AMRegistries.init(bus);
        final ModLoadingContext context = ModLoadingContext.get();
        Config.init(context);
        modInfo = context.getActiveContainer().getModInfo();
        NETWORK_HANDLER.register(LearnSkillPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(SyncSkillsPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(SyncKnowledgePacket.class, NetworkDirection.PLAY_TO_CLIENT);
        NETWORK_HANDLER.register(SyncAffinityPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::playerJoinWorld);
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

    private void setup(FMLCommonSetupEvent event) {
        AMItems.initSpawnEggs();
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IKnowledgeHolder.class);
        event.register(IAffinityHolder.class);
    }

    private void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge"), new KnowledgeHelper.KnowledgeHolderProvider());
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"), new AffinityHelper.AffinityHolderProvider());
        }
    }

    private void playerJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player && !event.getWorld().isClientSide()) {
            NETWORK_HANDLER.sendToPlayer(new SyncKnowledgePacket(KnowledgeHelper.instance().getKnowledgeHolder(player)), player);
            NETWORK_HANDLER.sendToPlayer(new SyncAffinityPacket(AffinityHelper.instance().getAffinityHolder(player)), player);
        }
    }
}
