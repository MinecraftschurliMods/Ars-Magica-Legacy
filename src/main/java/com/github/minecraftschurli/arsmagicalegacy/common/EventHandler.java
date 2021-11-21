package com.github.minecraftschurli.arsmagicalegacy.common;

import com.github.minecraftschurli.arsmagicalegacy.Config;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import com.github.minecraftschurli.arsmagicalegacy.common.effect.AMMobEffect;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurli.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.TierMapping;
import com.github.minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.github.minecraftschurli.codeclib.CodecCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.ApiStatus.Internal;
import top.theillusivec4.curios.api.SlotTypeMessage;

public final class EventHandler {
    private EventHandler() {
    }

    @Internal
    public static void register(IEventBus modBus) {
        modBus.addListener(EventHandler::setup);
        modBus.addListener(EventHandler::registerCapabilities);
        modBus.addListener(EventHandler::entityAttributeModification);
        modBus.addListener(EventHandler::enqueueIMC);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addGenericListener(Entity.class, EventHandler::attachCapabilities);
        forgeBus.addListener(EventHandler::entityJoinWorld);
        forgeBus.addListener(EventHandler::playerClone);
        forgeBus.addListener(EventHandler::playerItemPickup);
        forgeBus.addListener(EventHandler::playerTick);
        forgeBus.addListener(EventHandler::livingUpdate);
        forgeBus.addListener(EventPriority.HIGHEST, EventHandler::livingDeath);
        forgeBus.addListener(EventPriority.LOWEST, EventHandler::livingHurt);
        forgeBus.addListener(EventHandler::livingJump);
        forgeBus.addListener(EventHandler::livingFall);
        forgeBus.addListener(EventHandler::potionAdded);
        forgeBus.addListener(EventHandler::potionExpiry);
        forgeBus.addListener(EventHandler::potionRemove);
        forgeBus.addListener(EventHandler::addReloadListener);
        forgeBus.addListener(EventHandler::playerLevelUp);
    }

    private static void enqueueIMC(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("head").build());
    }

    private static void setup(FMLCommonSetupEvent event) {
        PatchouliCompat.init();
        registerSpellIngredientTypes();
    }

    public static void registerSpellIngredientTypes() {
        ISpellDataManager spellDataManager = ArsMagicaAPI.get().getSpellDataManager();
        spellDataManager.registerSpellIngredientType(IngredientSpellIngredient.INGREDIENT, IngredientSpellIngredient.CODEC);
        spellDataManager.registerSpellIngredientType(EtheriumSpellIngredient.ETHERIUM, EtheriumSpellIngredient.CODEC);
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

    private static void entityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getWorld().isClientSide()) return;
        SkillHelper.instance().syncToPlayer(player);
        AffinityHelper.instance().syncToPlayer(player);
        MagicHelper.instance().syncAllToPlayer(player);
        for (MobEffectInstance instance : player.getActiveEffects()) {
            if (instance.getEffect() instanceof AMMobEffect) {
                ((AMMobEffect) instance.getEffect()).startEffect(player, instance);
            }
        }
    }

    private static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        SkillHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        AffinityHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        MagicHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        event.getOriginal().invalidateCaps();
    }

    private static void playerItemPickup(PlayerEvent.ItemPickupEvent event) {
        if (event.getPlayer().isCreative()) return;
        if (event.getPlayer().isSpectator()) return;
        if (ArsMagicaAPI.get().getMagicHelper().knowsMagic(event.getPlayer())) return;
        if (!ItemStack.isSameItemSameTags(ArsMagicaAPI.get().getBookStack(), event.getStack())) return;
        ArsMagicaAPI.get().getMagicHelper().awardXp(event.getPlayer(), 0);
    }

    private static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != LogicalSide.SERVER) return;
        if (event.phase != TickEvent.Phase.START) return;
        Player player = event.player;
        if (player.isDeadOrDying()) return;
        ArsMagicaAPI.get().getMagicHelper().increaseMana(player, (float) player.getAttributeValue(AMAttributes.MANA_REGEN.get()));
        ArsMagicaAPI.get().getMagicHelper().decreaseBurnout(player, (float) player.getAttributeValue(AMAttributes.BURNOUT_REGEN.get()));
    }

    private static void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(AMMobEffects.WATERY_GRAVE.get()) && (entity.isInWaterOrBubble() || entity.getPose() == Pose.SWIMMING)) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getPose() == Pose.SWIMMING ? 0 : Math.min(0, entity.getDeltaMovement().y()), entity.getDeltaMovement().z());
        }
    }

    private static void livingDeath(LivingDeathEvent event) {
        if (event.getEntityLiving().hasEffect(AMMobEffects.TEMPORAL_ANCHOR.get())) {
            event.getEntityLiving().removeEffect(AMMobEffects.TEMPORAL_ANCHOR.get());
            event.setCanceled(true);
        }
    }

    private static void livingHurt(LivingHurtEvent event) {
        if (event.getSource() != DamageSource.OUT_OF_WORLD && event.getEntityLiving().hasEffect(AMMobEffects.MAGIC_SHIELD.get())) {
            event.setAmount(event.getAmount() / (float) event.getEntityLiving().getEffect(AMMobEffects.MAGIC_SHIELD.get()).getAmplifier());
        }
    }

    private static void livingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(AMMobEffects.AGILITY.get())) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1f * (entity.getEffect(AMMobEffects.AGILITY.get()).getAmplifier() + 1), 0));
        }
    }

    private static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(AMMobEffects.AGILITY.get())) {
            event.setDistance(event.getDistance() / (1.1f * (entity.getEffect(AMMobEffects.AGILITY.get()).getAmplifier() + 1)));
        }
        if (entity.hasEffect(AMMobEffects.GRAVITY_WELL.get())) {
            event.setDistance(event.getDistance() * (entity.getEffect(AMMobEffects.GRAVITY_WELL.get()).getAmplifier() + 1));
        }
    }

    private static void potionAdded(PotionEvent.PotionAddedEvent event) {
        if (!event.getEntity().level.isClientSide() && event.getPotionEffect().getEffect() instanceof AMMobEffect) {
            ((AMMobEffect) event.getPotionEffect().getEffect()).startEffect(event.getEntityLiving(), event.getPotionEffect());
        }
    }

    private static void potionExpiry(PotionEvent.PotionExpiryEvent event) {
        if (!event.getEntity().level.isClientSide() && event.getPotionEffect().getEffect() instanceof AMMobEffect) {
            ((AMMobEffect) event.getPotionEffect().getEffect()).stopEffect(event.getEntityLiving(), event.getPotionEffect());
        }
    }

    private static void potionRemove(PotionEvent.PotionRemoveEvent event) {
        if (!event.getEntity().level.isClientSide() && event.getPotionEffect().getEffect() instanceof AMMobEffect) {
            ((AMMobEffect) event.getPotionEffect().getEffect()).stopEffect(event.getEntityLiving(), event.getPotionEffect());
        }
    }

    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(OcculusTabManager.instance());
        event.addListener(SkillManager.instance());
        event.addListener(SpellDataManager.instance());
        event.addListener(AltarMaterialManager.instance().cap);
        event.addListener(AltarMaterialManager.instance().structure);
        event.addListener(TierMapping.instance());
    }

    private static void playerLevelUp(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        int level = event.getLevel();
        // TODO change
        float newMaxMana = Config.SERVER.DEFAULT_MAX_MANA.get().floatValue() + 10 * (level - 1);
        float newMaxBurnout = Config.SERVER.DEFAULT_MAX_BURNOUT.get().floatValue() + 10 * (level-1);
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
