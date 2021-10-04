package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.arsmagicalegacy.network.LearnSkillPacket;
import com.github.minecraftschurli.arsmagicalegacy.network.OpenOcculusGuiPacket;
import com.github.minecraftschurli.codeclib.CodecCapabilityProvider;
import com.github.minecraftschurli.codeclib.CodecPacket;
import com.github.minecraftschurli.easyimclib.IMCHandler;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArsMagicaAPI.MOD_ID)
public final class ArsMagicaLegacy {
    public static final Logger LOGGER = LogManager.getLogger(ArsMagicaAPI.MOD_ID);
    private static ArsMagicaLegacy INSTANCE;

    public static final IMCHandler IMC_HANDLER = IMCHandler.create(ArsMagicaAPI.MOD_ID);
    public static final NetworkHandler NETWORK_HANDLER = NetworkHandler.create(ArsMagicaAPI.MOD_ID, "main", 0);

    /**
     * The Mod Constructor
     */
    public ArsMagicaLegacy() {
        if (INSTANCE != null) throw new IllegalStateException("Tried to create mod %s more than once!".formatted(ArsMagicaAPI.MOD_ID));
        INSTANCE = this;
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::registerCapabilities);
        IMC_HANDLER.init(bus);
        AMRegistries.init(bus);
        Config.init();

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(this::playerJoinWorld);
        MinecraftForge.EVENT_BUS.addListener(this::registerDataManager);

        registerNetworkPackets();

        CodecPacket.register(NETWORK_HANDLER);

        OcculusTabManager.instance();
        SkillManager.instance();
    }

    private void registerNetworkPackets() {
        NETWORK_HANDLER.register(LearnSkillPacket.class, NetworkDirection.PLAY_TO_SERVER);
        NETWORK_HANDLER.register(OpenOcculusGuiPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }

    private void setup(FMLCommonSetupEvent event) {
        AMItems.initSpawnEggs();
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(KnowledgeHelper.KnowledgeHolder.class);
        event.register(AffinityHelper.AffinityHolder.class);
    }

    private void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge"), new CodecCapabilityProvider<>(KnowledgeHelper.KnowledgeHolder.CODEC, KnowledgeHelper.getCapability(), KnowledgeHelper.KnowledgeHolder::empty));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"), new CodecCapabilityProvider<>(AffinityHelper.AffinityHolder.CODEC, AffinityHelper.getCapability(), AffinityHelper.AffinityHolder::empty));
        }
    }

    private void playerJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player && !event.getWorld().isClientSide()) {
            KnowledgeHelper.instance().syncToPlayer(player);
            AffinityHelper.instance().syncToPlayer(player);
        }
    }

    private void registerDataManager(AddReloadListenerEvent event) {
        event.addListener(OcculusTabManager.instance());
        event.addListener(SkillManager.instance());
    }
}
