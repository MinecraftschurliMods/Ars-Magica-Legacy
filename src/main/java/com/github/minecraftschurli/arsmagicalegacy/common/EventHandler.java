package com.github.minecraftschurli.arsmagicalegacy.common;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.codeclib.CodecCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

public final class EventHandler {
    private EventHandler() {}

    @Internal
    public static void register(final IEventBus modBus) {
        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modBus.addListener(EventHandler::setup);
        modBus.addListener(EventHandler::registerCapabilities);

        forgeBus.addGenericListener(Entity.class, EventHandler::attachCapabilities);
        forgeBus.addListener(EventHandler::playerJoinWorld);
        forgeBus.addListener(EventHandler::registerDataManager);
    }

    private static void setup(FMLCommonSetupEvent event) {
        AMItems.initSpawnEggs();
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(KnowledgeHelper.KnowledgeHolder.class);
        event.register(AffinityHelper.AffinityHolder.class);
    }

    private static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge"), new CodecCapabilityProvider<>(KnowledgeHelper.KnowledgeHolder.CODEC, KnowledgeHelper.getCapability(), KnowledgeHelper.KnowledgeHolder::empty));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"), new CodecCapabilityProvider<>(AffinityHelper.AffinityHolder.CODEC, AffinityHelper.getCapability(), AffinityHelper.AffinityHolder::empty));
        }
    }

    private static void playerJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Player player && !event.getWorld().isClientSide()) {
            KnowledgeHelper.instance().syncToPlayer(player);
            AffinityHelper.instance().syncToPlayer(player);
        }
    }

    private static void registerDataManager(AddReloadListenerEvent event) {
        event.addListener(OcculusTabManager.instance());
        event.addListener(SkillManager.instance());
    }
}
