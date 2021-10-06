package com.github.minecraftschurli.arsmagicalegacy.common;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.Mage;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ManaCreeper;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WinterGuardian;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
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
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
        modBus.addListener(EventHandler::createEntityAttributes);

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

    private static void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(AMEntities.WATER_GUARDIAN.get(), WaterGuardian.createAttributes().build());
        event.put(AMEntities.FIRE_GUARDIAN.get(), FireGuardian.createAttributes().build());
        event.put(AMEntities.EARTH_GUARDIAN.get(), EarthGuardian.createAttributes().build());
        event.put(AMEntities.AIR_GUARDIAN.get(), AirGuardian.createAttributes().build());
        event.put(AMEntities.WINTER_GUARDIAN.get(), WinterGuardian.createAttributes().build());
        event.put(AMEntities.LIGHTNING_GUARDIAN.get(), LightningGuardian.createAttributes().build());
        event.put(AMEntities.NATURE_GUARDIAN.get(), NatureGuardian.createAttributes().build());
        event.put(AMEntities.LIFE_GUARDIAN.get(), LifeGuardian.createAttributes().build());
        event.put(AMEntities.ARCANE_GUARDIAN.get(), ArcaneGuardian.createAttributes().build());
        event.put(AMEntities.ENDER_GUARDIAN.get(), EnderGuardian.createAttributes().build());
        event.put(AMEntities.DRYAD.get(), Dryad.createAttributes().build());
        event.put(AMEntities.MAGE.get(), Mage.createAttributes().build());
        event.put(AMEntities.MANA_CREEPER.get(), ManaCreeper.createAttributes().build());
    }
}
