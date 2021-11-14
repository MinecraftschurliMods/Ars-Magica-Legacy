package com.github.minecraftschurli.arsmagicalegacy.common;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarMaterialManager;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
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
    private EventHandler() {
    }

    @Internal
    public static void register(IEventBus modBus) {
        modBus.addListener(EventHandler::setup);
        modBus.addListener(EventHandler::registerCapabilities);
        modBus.addListener(EventHandler::entityAttributeModification);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addGenericListener(Entity.class, EventHandler::attachCapabilities);
        forgeBus.addListener(EventHandler::playerClone);
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
        if (event.getObject() instanceof LivingEntity livingEntity) {
            AttributeSupplier attributes = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) livingEntity.getType());
            if (attributes.hasAttribute(AMAttributes.MAX_MANA.get())) {
                event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "mana"), new CodecCapabilityProvider<>(MagicHelper.ManaHolder.CODEC, MagicHelper.getManaCapability(), MagicHelper.ManaHolder::new));
            }
            if (attributes.hasAttribute(AMAttributes.MAX_BURNOUT.get())) {
                event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "burnout"), new CodecCapabilityProvider<>(MagicHelper.BurnoutHolder.CODEC, MagicHelper.getBurnoutCapability(), MagicHelper.BurnoutHolder::new));
            }
        }
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge"), new CodecCapabilityProvider<>(SkillHelper.KnowledgeHolder.CODEC, SkillHelper.getCapability(), SkillHelper.KnowledgeHolder::empty));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"), new CodecCapabilityProvider<>(AffinityHelper.AffinityHolder.CODEC, AffinityHelper.getCapability(), AffinityHelper.AffinityHolder::empty));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic"), new CodecCapabilityProvider<>(MagicHelper.MagicHolder.CODEC, MagicHelper.getMagicCapability(), MagicHelper.MagicHolder::new));
        }
    }

    private static void entityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT.get());
        event.add(EntityType.PLAYER, AMAttributes.MANA_REGEN.get());
        event.add(EntityType.PLAYER, AMAttributes.BURNOUT_REGEN.get());
    }

    private static void playerJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getWorld().isClientSide()) return;
        SkillHelper.instance().syncToPlayer(player);
        AffinityHelper.instance().syncToPlayer(player);
        MagicHelper.instance().syncAllToPlayer(player);
    }

    private static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        SkillHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        AffinityHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        MagicHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        event.getOriginal().invalidateCaps();
    }

    private static void compendiumPickup(PlayerEvent.ItemPickupEvent event) {
        if (event.getPlayer().isCreative()) return;
        if (event.getPlayer().isSpectator()) return;
        if (ArsMagicaAPI.get().getMagicHelper().knowsMagic(event.getPlayer())) return;
        if (!ItemStack.isSameItemSameTags(ArsMagicaAPI.get().getBookStack(), event.getStack())) return;
        ArsMagicaAPI.get().getMagicHelper().awardXp(event.getPlayer(), 0);
    }

    private static void registerDataManager(AddReloadListenerEvent event) {
        event.addListener(OcculusTabManager.instance());
        event.addListener(SkillManager.instance());
        event.addListener(SpellDataManager.instance());
        event.addListener(AltarMaterialManager.instance().cap);
        event.addListener(AltarMaterialManager.instance().structure);
    }

    private static void onTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER) return;
        if (event.phase != TickEvent.Phase.START) return;
        Player player = event.player;
        if (player.isDeadOrDying()) return;
        ArsMagicaAPI.get().getMagicHelper().increaseMana(player, (float) player.getAttributeValue(AMAttributes.MANA_REGEN.get()));
        ArsMagicaAPI.get().getMagicHelper().decreaseBurnout(player, (float) player.getAttributeValue(AMAttributes.BURNOUT_REGEN.get()));
    }

    private static void levelUp(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        int level = event.getLevel();
        // TODO change
        float newMaxMana = 10 * level;
        float newMaxBurnout = 10 * level;
        IMagicHelper magicHelper = ArsMagicaAPI.get().getMagicHelper();
        AttributeInstance maxManaAttr = player.getAttribute(AMAttributes.MAX_MANA.get());
        if (maxManaAttr != null) {
            maxManaAttr.setBaseValue(newMaxMana);
            magicHelper.increaseMana(player, (newMaxMana - magicHelper.getMana(player)) / 2);
        }
        AttributeInstance maxBurnoutAttr = player.getAttribute(AMAttributes.MAX_BURNOUT.get());
        if (maxBurnoutAttr != null) {
            maxBurnoutAttr.setBaseValue(newMaxBurnout);
            magicHelper.decreaseBurnout(player, magicHelper.getBurnout(player) / 2);
        }
    }
}
