package com.github.minecraftschurli.arsmagicalegacy.common;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurli.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurli.codeclib.CodecCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

public final class EventHandler {
    private EventHandler() {}

    @Internal
    public static void register(final IEventBus modBus) {
        final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modBus.addListener(EventHandler::setup);
        modBus.addListener(EventHandler::registerCapabilities);
        modBus.addListener(EventHandler::entityAttributeModification);

        forgeBus.addGenericListener(Entity.class, EventHandler::attachCapabilities);
        forgeBus.addListener(EventHandler::playerJoinWorld);
        forgeBus.addListener(EventHandler::registerDataManager);
        forgeBus.addListener(EventHandler::levelUp);
        forgeBus.addListener(EventHandler::onTick);
        forgeBus.addListener(EventHandler::compendiumPickup);
    }

    private static void setup(FMLCommonSetupEvent event) {
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SkillHelper.KnowledgeHolder.class);
        event.register(AffinityHelper.AffinityHolder.class);
        event.register(MagicHelper.MagicHolder.class);
        event.register(MagicHelper.ManaHolder.class);
        event.register(MagicHelper.BurnoutHolder.class);
    }

    private static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;

        event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge"),
                            new CodecCapabilityProvider<>(SkillHelper.KnowledgeHolder.CODEC,
                                                          SkillHelper.getCapability(),
                                                          SkillHelper.KnowledgeHolder::empty));
        event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"),
                            new CodecCapabilityProvider<>(AffinityHelper.AffinityHolder.CODEC,
                                                          AffinityHelper.getCapability(),
                                                          AffinityHelper.AffinityHolder::empty));
        event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic"),
                            new CodecCapabilityProvider<>(MagicHelper.MagicHolder.CODEC,
                                                          MagicHelper.getMagicCapability(),
                                                          MagicHelper.MagicHolder::new));
        event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "mana"),
                            new CodecCapabilityProvider<>(MagicHelper.ManaHolder.CODEC,
                                                          MagicHelper.getManaCapability(),
                                                          MagicHelper.ManaHolder::new));
        event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "burnout"),
                            new CodecCapabilityProvider<>(MagicHelper.BurnoutHolder.CODEC,
                                                          MagicHelper.getBurnoutCapability(),
                                                          MagicHelper.BurnoutHolder::new));
    }

    private static void entityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT.get());
    }

    private static void playerJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getWorld().isClientSide()) return;

        SkillHelper.instance().syncToPlayer(player);
        AffinityHelper.instance().syncToPlayer(player);
        MagicHelper.instance().syncAllToPlayer(player);
    }

    private static void registerDataManager(AddReloadListenerEvent event) {
        event.addListener(OcculusTabManager.instance());
        event.addListener(SkillManager.instance());
        event.addListener(SpellDataManager.instance());
    }

    private static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER) return;
        if (event.phase != TickEvent.Phase.START) return;
        if (event.player.isDeadOrDying()) return;

        ArsMagicaAPI.get().getMagicHelper().increaseMana(event.player, 0.1f /*+ 0.3f * CapabilityHelper.getManaRegenLevel(event.player)*/);
        ArsMagicaAPI.get().getMagicHelper().decreaseBurnout(event.player, 0.4f);
    }

    private static void compendiumPickup(PlayerEvent.ItemPickupEvent event) {
        if (event.getPlayer().isCreative()) return;
        if (event.getPlayer().isSpectator()) return;
        if (ArsMagicaAPI.get().getMagicHelper().knowsMagic(event.getPlayer())) return;
        if (!ItemStack.isSameItemSameTags(ArsMagicaAPI.get().getBookStack(), event.getStack())) return;

        ArsMagicaAPI.get().getMagicHelper().awardXp(event.getPlayer(), 0);
    }

    private static void levelUp(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        int level = event.getLevel();
        // TODO change
        float newMaxMana = 10 * level;
        float newMaxBurnout = 10 * level;
        var magicHelper = ArsMagicaAPI.get().getMagicHelper();
        var maxManaAttr = player.getAttribute(AMAttributes.MAX_MANA.get());
        if (maxManaAttr != null) {
            maxManaAttr.setBaseValue(newMaxMana);
            magicHelper.increaseMana(player, (newMaxMana-magicHelper.getMana(player))/2);
        }
        var maxBurnoutAttr = player.getAttribute(AMAttributes.MAX_BURNOUT.get());
        if (maxBurnoutAttr != null) {
            maxBurnoutAttr.setBaseValue(newMaxBurnout);
            magicHelper.decreaseBurnout(player, magicHelper.getBurnout(player)/2);
        }
    }
}
