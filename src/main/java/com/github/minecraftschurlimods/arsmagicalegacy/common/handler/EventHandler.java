package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Mage;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaCreeper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWoodTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.RiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.TierMapping;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenSpellRecipeGuiInLecternPacket;
import com.github.minecraftschurlimods.codeclib.CodecCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Central event handler. Registers the other event handlers, as well as some general-purpose event handlers.
 */
public final class EventHandler {
    private EventHandler() {}

    /**
     * Registers the common event handlers.
     *
     * @param modBus The mod event bus to register the mod events to.
     */
    @Internal
    public static void register(IEventBus modBus) {
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        TickHandler.init(forgeBus);
        EffectHandler.init(forgeBus);
        AbilityHandler.init(forgeBus);
        modBus.addListener(EventHandler::setup);
        modBus.addListener(EventHandler::registerCapabilities);
        modBus.addListener(EventHandler::entityAttributeCreation);
        modBus.addListener(EventHandler::entityAttributeModification);
        modBus.addListener(EventHandler::enqueueIMC);
        modBus.addListener(EventHandler::registerSpawnPlacements);
        modBus.addListener(EventHandler::registerCreativeTabs);
        forgeBus.addGenericListener(Entity.class, EventHandler::attachCapabilities);
        forgeBus.addListener(EventHandler::addReloadListener);
        forgeBus.addListener(EventHandler::entityJoinWorld);
        forgeBus.addListener(EventHandler::playerClone);
        forgeBus.addListener(EventHandler::playerItemCrafted);
        forgeBus.addListener(EventHandler::playerRespawn);
        forgeBus.addListener(EventHandler::livingDamage);
        forgeBus.addListener(EventHandler::rightClickBlock);
        forgeBus.addListener(EventPriority.HIGH, EventHandler::manaCostPre);
        forgeBus.addListener(EventHandler::playerLevelUp);
    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WoodType.register(AMWoodTypes.WITCHWOOD);
            registerBrewingRecipes();
            AMCriteriaTriggers.register();
        });
        CompatManager.init(event);
    }

    private static void registerCreativeTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(ArsMagicaAPI.MAIN_CREATIVE_TAB, EventHandler::buildMainCreativeTab);
        event.registerCreativeModeTab(ArsMagicaAPI.PREFAB_SPELLS_CREATIVE_TAB, List.of(), List.of(ArsMagicaAPI.MAIN_CREATIVE_TAB), EventHandler::buildPrefabSpellsCreativeTab);
    }

    private static void buildMainCreativeTab(CreativeModeTab.Builder builder) {
        builder.icon(() -> ArsMagicaAPI.get().getBookStack())
               .title(Component.translatable(TranslationConstants.MAIN_CREATIVE_TAB))
               .displayItems((params, output) -> {
                   List<ItemStack> list = new ArrayList<>();
                   var api = ArsMagicaAPI.get();
                   for (RegistryObject<? extends Item> o : AMRegistries.ITEMS.getEntries()) {
                       if (!o.isPresent()) continue;
                       Item item = o.get();
                       if (item instanceof ISkillPointItem skillPointItem) {
                           for (SkillPoint point : api.getSkillPointRegistry().getValues()) {
                               if (point != AMSkillPoints.NONE.get()) {
                                   list.add(skillPointItem.setSkillPoint(new ItemStack(item), point));
                               }
                           }
                           continue;
                       }
                       if (item instanceof IAffinityItem affinityItem) {
                           for (Affinity affinity : api.getAffinityRegistry()) {
                               if (Affinity.NONE.equals(affinity.getId())) continue;
                               list.add(affinityItem.setAffinity(new ItemStack(item), affinity));
                           }
                           continue;
                       }
                       if (!AMItems.HIDDEN_ITEMS.contains(o)) {
                           list.add(new ItemStack(item));
                       }
                   }
                   output.acceptAll(list);
               });
    }

    private static void buildPrefabSpellsCreativeTab(CreativeModeTab.Builder builder) {
        builder.icon(() -> AMItems.SPELL_PARCHMENT.map(ItemStack::new).orElse(ItemStack.EMPTY))
               .title(Component.translatable(TranslationConstants.PREFAB_SPELL_CREATIVE_TAB))
               .withSearchBar()
               .displayItems((params, output) -> ClientHelper.getRegistry(PrefabSpell.REGISTRY_KEY).stream().map(PrefabSpell::makeSpell).forEach(output::accept));
    }

    private static void registerSpawnPlacements(SpawnPlacementRegisterEvent evt) {
        evt.register(AMEntities.DRYAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dryad::checkDryadSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        evt.register(AMEntities.MANA_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    private static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.LESSER_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.STANDARD_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.GREATER_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.EPIC_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.LEGENDARY_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.INFUSED_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.SPLASH_POTION, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.LESSER_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.SPLASH_POTION, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.STANDARD_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.SPLASH_POTION, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.GREATER_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.SPLASH_POTION, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.EPIC_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.SPLASH_POTION, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.LEGENDARY_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.SPLASH_POTION, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.INFUSED_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.LINGERING_POTION, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.LESSER_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.LINGERING_POTION, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.STANDARD_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.LINGERING_POTION, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.GREATER_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.LINGERING_POTION, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.EPIC_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.LINGERING_POTION, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.LEGENDARY_MANA.get()));
        BrewingRecipeRegistry.addRecipe(PartialNBTIngredient.of(Items.LINGERING_POTION, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD).getOrCreateTag()), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.INFUSED_MANA.get()));
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SkillHelper.KnowledgeHolder.class);
        event.register(AffinityHelper.AffinityHolder.class);
        event.register(MagicHelper.MagicHolder.class);
        event.register(ManaHelper.ManaHolder.class);
        event.register(BurnoutHelper.BurnoutHolder.class);
    }

    private static void entityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(AMEntities.WATER_GUARDIAN.get(), WaterGuardian.createAttributes().build());
        event.put(AMEntities.FIRE_GUARDIAN.get(), FireGuardian.createAttributes().build());
        event.put(AMEntities.EARTH_GUARDIAN.get(), EarthGuardian.createAttributes().build());
        event.put(AMEntities.AIR_GUARDIAN.get(), AirGuardian.createAttributes().build());
        event.put(AMEntities.ICE_GUARDIAN.get(), IceGuardian.createAttributes().build());
        event.put(AMEntities.LIGHTNING_GUARDIAN.get(), LightningGuardian.createAttributes().build());
        event.put(AMEntities.NATURE_GUARDIAN.get(), NatureGuardian.createAttributes().build());
        event.put(AMEntities.LIFE_GUARDIAN.get(), LifeGuardian.createAttributes().build());
        event.put(AMEntities.ARCANE_GUARDIAN.get(), ArcaneGuardian.createAttributes().build());
        event.put(AMEntities.ENDER_GUARDIAN.get(), EnderGuardian.createAttributes().build());
        event.put(AMEntities.DRYAD.get(), Dryad.createAttributes().build());
        event.put(AMEntities.MAGE.get(), Mage.createAttributes().build());
        event.put(AMEntities.MANA_CREEPER.get(), ManaCreeper.createAttributes().build());
    }

    private static void entityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA.get());
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT.get());
        event.add(EntityType.PLAYER, AMAttributes.MANA_REGEN.get());
        event.add(EntityType.PLAYER, AMAttributes.BURNOUT_REGEN.get());
        for (EntityType<? extends LivingEntity> entity : event.getTypes()) {
            event.add(entity, AMAttributes.SCALE.get());
        }
    }

    private static void enqueueIMC(InterModEnqueueEvent event) {
        CompatManager.imcEnqueue(event);
    }

    @SuppressWarnings("unchecked")
    private static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity livingEntity) {
            AttributeSupplier attributes = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) livingEntity.getType());
            if (attributes.hasAttribute(AMAttributes.MAX_MANA.get())) {
                event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "mana"), new CodecCapabilityProvider<>(ManaHelper.ManaHolder.CODEC, ManaHelper.getManaCapability(), ManaHelper.ManaHolder::new));
            }
            if (attributes.hasAttribute(AMAttributes.MAX_BURNOUT.get())) {
                event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "burnout"), new CodecCapabilityProvider<>(BurnoutHelper.BurnoutHolder.CODEC, BurnoutHelper.getBurnoutCapability(), BurnoutHelper.BurnoutHolder::new));
            }
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "contingency"), new CodecCapabilityProvider<>(ContingencyHelper.Contingency.CODEC, ContingencyHelper.getCapability(), ContingencyHelper.Contingency::new));
        }
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knowledge"), new CodecCapabilityProvider<>(SkillHelper.KnowledgeHolder.CODEC, SkillHelper.getCapability(), SkillHelper.KnowledgeHolder::empty));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"), new CodecCapabilityProvider<>(AffinityHelper.AffinityHolder.CODEC, AffinityHelper.getCapability(), AffinityHelper.AffinityHolder::empty));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic"), new CodecCapabilityProvider<>(MagicHelper.MagicHolder.CODEC, MagicHelper.getMagicCapability(), MagicHelper.MagicHolder::new));
            event.addCapability(new ResourceLocation(ArsMagicaAPI.MOD_ID, "rift"), new CodecCapabilityProvider<>(RiftHelper.RiftHolder.CODEC, RiftHelper.getRiftCapability(), RiftHelper.RiftHolder::new));
        }
    }

    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(SpellDataManager.instance());
        event.addListener(TierMapping.instance());
    }

    private static void entityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getLevel().isClientSide()) return;
        SkillHelper.instance().syncToPlayer(player);
        AffinityHelper.instance().syncToPlayer(player);
        MagicHelper.instance().syncMagic(player);
        ManaHelper.instance().syncMana(player);
        BurnoutHelper.instance().syncBurnout(player);
    }

    private static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        SkillHelper.instance().syncOnDeath(event.getOriginal(), event.getEntity());
        AffinityHelper.instance().syncOnDeath(event.getOriginal(), event.getEntity());
        MagicHelper.instance().syncOnDeath(event.getOriginal(), event.getEntity());
        ManaHelper.instance().syncOnDeath(event.getOriginal(), event.getEntity());
        BurnoutHelper.instance().syncOnDeath(event.getOriginal(), event.getEntity());
        RiftHelper.instance().syncOnDeath(event.getOriginal(), event.getEntity());
        event.getOriginal().invalidateCaps();
    }

    private static void playerItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        var api = ArsMagicaAPI.get();
        var helper = api.getMagicHelper();
        if (helper.knowsMagic(event.getEntity())) return;
        if (!ItemStack.isSameItemSameTags(api.getBookStack(), event.getCrafting())) return;
        helper.awardXp(event.getEntity(), 0);
    }

    private static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        AttributeInstance maxManaAttr = event.getEntity().getAttribute(AMAttributes.MAX_MANA.get());
        if (maxManaAttr != null) {
            ArsMagicaAPI.get().getManaHelper().increaseMana(event.getEntity(), (float) (maxManaAttr.getBaseValue() / 2));
        }
    }

    private static void livingDamage(LivingDamageEvent event) {
        if (event.getEntity().getHealth() * 4 < event.getEntity().getMaxHealth()) {
            ArsMagicaAPI.get().getContingencyHelper().triggerContingency(event.getEntity(), ContingencyType.HEALTH);
        }
    }

    private static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide() == LogicalSide.CLIENT) return;
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos pos = event.getHitVec().getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (!(level.getBlockEntity(pos) instanceof LecternBlockEntity lectern) || !state.getValue(LecternBlock.HAS_BOOK)) return;
        ItemStack stack = lectern.getBook();
        if (!(stack.getItem() instanceof SpellRecipeItem)) return;
        if (player.isSecondaryUseActive()) {
            SpellRecipeItem.takeFromLectern(player, level, pos, state);
        } else {
            lectern.pageCount = SpellRecipeItem.getPageCount(stack);
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new OpenSpellRecipeGuiInLecternPacket(stack, pos, lectern.getPage()), player);
            player.awardStat(Stats.INTERACT_WITH_LECTERN);
            event.setCanceled(true);
        }
    }

    private static void manaCostPre(SpellEvent.ManaCost.Pre event) {
        LivingEntity caster = event.getEntity();
        if (!(caster instanceof Player player)) return;
        float cost = event.getBase();
        var api = ArsMagicaAPI.get();
        cost += api.getBurnoutHelper().getBurnout(caster) / 10f;
        for (ISpellPart iSpellPart : event.getSpell().parts()) {
            if (iSpellPart.getType() != ISpellPart.SpellPartType.COMPONENT) continue;
            ISpellPartData dataForPart = api.getSpellDataManager().getDataForPart(iSpellPart);
            if (dataForPart == null) continue;
            Set<Affinity> affinities = dataForPart.affinities();
            for (Affinity aff : affinities) {
                double value = api.getAffinityHelper().getAffinityDepthOrElse(player, aff, 0);
                if (value > 0) {
                    cost -= (float) (cost * 0.5f * value);
                }
            }
        }
        event.setBase(cost);
    }

    private static void playerLevelUp(PlayerLevelUpEvent event) {
        Player player = event.getEntity();
        int level = event.getLevel();
        var api = ArsMagicaAPI.get();
        if (level == 1) {
            api.getSkillHelper().addSkillPoint(player, AMSkillPoints.BLUE.getId(), Config.SERVER.EXTRA_BLUE_SKILL_POINTS.get());
        }
        for (SkillPoint skillPoint : api.getSkillPointRegistry()) {
            int minEarnLevel = skillPoint.minEarnLevel();
            int levelsForPoint = skillPoint.levelsForPoint();
            if (minEarnLevel < 0 || levelsForPoint < 0) continue;
            if (level >= minEarnLevel && (level - minEarnLevel) % levelsForPoint == 0) {
                api.getSkillHelper().addSkillPoint(player, skillPoint);
                event.getEntity().getLevel().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), AMSounds.GET_KNOWLEDGE_POINT.get(), SoundSource.PLAYERS, 1f, 1f);
            }
        }
        float newMaxMana = Config.SERVER.MANA_BASE.get().floatValue() + Config.SERVER.MANA_MULTIPLIER.get().floatValue() * (level - 1);
        AttributeInstance maxManaAttr = player.getAttribute(AMAttributes.MAX_MANA.get());
        if (maxManaAttr != null) {
            IManaHelper manaHelper = api.getManaHelper();
            maxManaAttr.setBaseValue(newMaxMana);
            manaHelper.increaseMana(player, (newMaxMana - manaHelper.getMana(player)) / 2);
        }
        AttributeInstance manaRegenAttr = player.getAttribute(AMAttributes.MANA_REGEN.get());
        if (manaRegenAttr != null) {
            manaRegenAttr.setBaseValue(newMaxMana * Config.SERVER.MANA_REGEN_MULTIPLIER.get() );
        }
        float newMaxBurnout = Config.SERVER.BURNOUT_BASE.get().floatValue() + Config.SERVER.BURNOUT_MULTIPLIER.get().floatValue() * (level - 1);
        AttributeInstance maxBurnoutAttr = player.getAttribute(AMAttributes.MAX_BURNOUT.get());
        if (maxBurnoutAttr != null) {
            IBurnoutHelper burnoutHelper = api.getBurnoutHelper();
            maxBurnoutAttr.setBaseValue(newMaxBurnout);
            burnoutHelper.decreaseBurnout(player, burnoutHelper.getBurnout(player) / 2);
        }
        AttributeInstance burnoutRegenAttr = player.getAttribute(AMAttributes.BURNOUT_REGEN.get());
        if (burnoutRegenAttr != null) {
            burnoutRegenAttr.setBaseValue(newMaxMana * Config.SERVER.BURNOUT_REGEN_MULTIPLIER.get() );
        }
        event.getEntity().getLevel().playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), AMSounds.MAGIC_LEVEL_UP.get(), SoundSource.PLAYERS, 1f, 1f);
    }
}
