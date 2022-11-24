package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbilityManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskFuelManager;
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
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.AMFeatures;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.RiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellTransformationManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.TierMapping;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenSpellRecipeGuiInLecternPacket;
import com.github.minecraftschurlimods.codeclib.CodecCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.OptionalInt;
import java.util.Set;

/**
 * Central event handler. Registers the other event handlers, as well as some general-purpose event handlers.
 */
public final class EventHandler {
    private EventHandler() {
    }

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
        modBus.addGenericListener(Feature.class, EventPriority.LOWEST, EventHandler::registerFeature);
        modBus.addListener(EventHandler::setup);
        modBus.addListener(EventHandler::registerCapabilities);
        modBus.addListener(EventHandler::entityAttributeCreation);
        modBus.addListener(EventHandler::entityAttributeModification);
        modBus.addListener(EventHandler::enqueueIMC);
        forgeBus.addGenericListener(Entity.class, EventHandler::attachCapabilities);
        forgeBus.addListener(EventHandler::addReloadListener);
        forgeBus.addListener(EventHandler::entityJoinWorld);
        forgeBus.addListener(EventHandler::playerClone);
        forgeBus.addListener(EventHandler::playerItemPickup);
        forgeBus.addListener(EventHandler::playerItemCrafted);
        forgeBus.addListener(EventHandler::playerRespawn);
        forgeBus.addListener(EventHandler::livingDamage);
        forgeBus.addListener(EventHandler::rightClickBlock);
        forgeBus.addListener(EventPriority.HIGH, EventHandler::manaCostPre);
        forgeBus.addListener(EventHandler::playerLevelUp);
    }

    private static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
        AMFeatures.CHIMERITE_FEATURE = AMFeatures.ore("chimerite_ore", AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0F);
        AMFeatures.VINTEUM_FEATURE = AMFeatures.ore("vinteum_ore", AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0F);
        AMFeatures.TOPAZ_FEATURE = AMFeatures.ore("topaz_ore", AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5F);
        AMFeatures.TOPAZ_EXTRA_FEATURE = AMFeatures.ore("topaz_ore_extra", AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0F);
        AMFeatures.CHIMERITE_PLACEMENT = AMFeatures.orePlacement("chimerite_ore", AMFeatures.CHIMERITE_FEATURE, 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16)));
        AMFeatures.VINTEUM_PLACEMENT = AMFeatures.orePlacement("vinteum_ore", AMFeatures.VINTEUM_FEATURE, 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80)));
        AMFeatures.TOPAZ_PLACEMENT = AMFeatures.orePlacement("topaz_ore", AMFeatures.TOPAZ_FEATURE, 7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)));
        AMFeatures.TOPAZ_EXTRA_PLACEMENT = AMFeatures.orePlacement("topaz_ore_extra", AMFeatures.TOPAZ_EXTRA_FEATURE, 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)));
        AMFeatures.AUM_FEATURE = AMFeatures.flower("aum", 64, AMBlocks.AUM);
        AMFeatures.CERUBLOSSOM_FEATURE = AMFeatures.flower("cerublossom", 64, AMBlocks.CERUBLOSSOM);
        AMFeatures.DESERT_NOVA_FEATURE = AMFeatures.flower("desert_nova", 64, AMBlocks.DESERT_NOVA);
        AMFeatures.TARMA_ROOT_FEATURE = AMFeatures.flower("tarma_root", 64, AMBlocks.TARMA_ROOT);
        AMFeatures.WAKEBLOOM_FEATURE = AMFeatures.flower("wakebloom", 64, AMBlocks.WAKEBLOOM);
        AMFeatures.AUM_PLACEMENT = AMFeatures.flowerPlacement("aum", AMFeatures.AUM_FEATURE, 32);
        AMFeatures.CERUBLOSSOM_PLACEMENT = AMFeatures.flowerPlacement("cerublossom", AMFeatures.CERUBLOSSOM_FEATURE, 32);
        AMFeatures.DESERT_NOVA_PLACEMENT = AMFeatures.flowerPlacement("desert_nova", AMFeatures.DESERT_NOVA_FEATURE, 32);
        AMFeatures.TARMA_ROOT_PLACEMENT = AMFeatures.flowerPlacement("tarma_root", AMFeatures.TARMA_ROOT_FEATURE, 32);
        AMFeatures.WAKEBLOOM_PLACEMENT = AMFeatures.flowerPlacement("wakebloom", AMFeatures.WAKEBLOOM_FEATURE, 32);
        AMFeatures.WITCHWOOD_TREE_FEATURE = AMFeatures.tree("witchwood_tree", AMBlocks.WITCHWOOD_LOG, new DarkOakTrunkPlacer(9, 3, 1), AMBlocks.WITCHWOOD_LEAVES, new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)), new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty()));
        AMFeatures.WITCHWOOD_TREE_PLACEMENT = AMFeatures.treePlacement("witchwood_tree", AMFeatures.WITCHWOOD_TREE_FEATURE, AMBlocks.WITCHWOOD_SAPLING);
        AMFeatures.WITCHWOOD_TREE_VEGETATION = AMFeatures.treeVegetation("trees_witchwood", AMFeatures.WITCHWOOD_TREE_FEATURE, PlacementUtils.countExtra(1, 0.1F, 0), 8, AMBlocks.WITCHWOOD_SAPLING);
    }

    private static void setup(FMLCommonSetupEvent event) {
        registerBrewingRecipes();
        registerSpellIngredientTypes();
        AMCriteriaTriggers.register();
        CompatManager.init(event);
    }

    private static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.LESSER_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.STANDARD_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.GREATER_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.EPIC_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.LEGENDARY_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.INFUSED_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.LESSER_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.STANDARD_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.GREATER_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.EPIC_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.LEGENDARY_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.INFUSED_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.LESSER_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.STANDARD_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.GREATER_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.EPIC_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.LEGENDARY_MANA.get())));
        BrewingRecipeRegistry.addRecipe(new BrewingRecipe(NBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.INFUSED_MANA.get())));
    }

    public static void registerSpellIngredientTypes() {
        var spellDataManager = ArsMagicaAPI.get().getSpellDataManager();
        spellDataManager.registerSpellIngredientType(IngredientSpellIngredient.INGREDIENT, IngredientSpellIngredient.CODEC, IngredientSpellIngredient.NETWORK_CODEC, IngredientSpellIngredient.IngredientSpellIngredientRenderer::new);
        spellDataManager.registerSpellIngredientType(EtheriumSpellIngredient.ETHERIUM, EtheriumSpellIngredient.CODEC, EtheriumSpellIngredient.EtheriumSpellIngredientRenderer::new);
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
        event.addListener(OcculusTabManager.instance());
        event.addListener(SkillManager.instance());
        event.addListener(SpellDataManager.instance());
        event.addListener(AbilityManager.instance());
        event.addListener(AltarMaterialManager.instance().cap);
        event.addListener(AltarMaterialManager.instance().structure);
        event.addListener(TierMapping.instance());
        event.addListener(PrefabSpellManager.instance());
        event.addListener(ObeliskFuelManager.instance());
        event.addListener(SpellTransformationManager.instance());
        event.addListener(RitualManager.instance());
    }

    private static void entityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getWorld().isClientSide()) return;
        SkillHelper.instance().syncToPlayer(player);
        AffinityHelper.instance().syncToPlayer(player);
        MagicHelper.instance().syncMagic(player);
        ManaHelper.instance().syncMana(player);
        BurnoutHelper.instance().syncBurnout(player);
    }

    private static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        SkillHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        AffinityHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        MagicHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        ManaHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        BurnoutHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        RiftHelper.instance().syncOnDeath(event.getOriginal(), event.getPlayer());
        event.getOriginal().invalidateCaps();
    }

    private static void playerItemPickup(PlayerEvent.ItemPickupEvent event) {
/*
        if (event.getPlayer().isCreative()) return;
        if (event.getPlayer().isSpectator()) return;
        var api = ArsMagicaAPI.get();
        var helper = api.getMagicHelper();
        if (helper.knowsMagic(event.getPlayer())) return;
        if (!ItemStack.isSameItemSameTags(api.getBookStack(), event.getStack())) return;
        helper.awardXp(event.getPlayer(), 0);
*/
    }

    private static void playerItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getPlayer().isCreative()) return;
        if (event.getPlayer().isSpectator()) return;
        var api = ArsMagicaAPI.get();
        var helper = api.getMagicHelper();
        if (helper.knowsMagic(event.getPlayer())) return;
        if (!ItemStack.isSameItemSameTags(api.getBookStack(), event.getCrafting())) return;
        helper.awardXp(event.getPlayer(), 0);
    }

    private static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        AttributeInstance maxManaAttr = event.getPlayer().getAttribute(AMAttributes.MAX_MANA.get());
        if (maxManaAttr != null) {
            ArsMagicaAPI.get().getManaHelper().increaseMana(event.getPlayer(), (float) (maxManaAttr.getBaseValue() / 2));
        }
    }

    private static void livingDamage(LivingDamageEvent event) {
        if (event.getEntityLiving().getHealth() * 4 < event.getEntityLiving().getMaxHealth()) {
            ArsMagicaAPI.get().getContingencyHelper().triggerContingency(event.getEntityLiving(), ContingencyType.HEALTH);
        }
    }

    private static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide() == LogicalSide.CLIENT) return;
        Player player = event.getPlayer();
        Level level = event.getWorld();
        BlockPos pos = event.getHitVec().getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (level.getBlockEntity(pos) instanceof LecternBlockEntity lectern && state.getValue(LecternBlock.HAS_BOOK)) {
            ItemStack stack = lectern.getBook();
            if (stack.getItem() instanceof SpellRecipeItem) {
                if (player.isSecondaryUseActive()) {
                    SpellRecipeItem.takeFromLectern(player, level, pos, state);
                } else {
                    lectern.pageCount = SpellRecipeItem.getPageCount(stack);
                    ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new OpenSpellRecipeGuiInLecternPacket(stack, pos, lectern.getPage()), player);
                    player.awardStat(Stats.INTERACT_WITH_LECTERN);
                    event.setCanceled(true);
                }
            }
        }
    }

    private static void manaCostPre(SpellEvent.ManaCost.Pre event) {
        LivingEntity caster = event.getEntityLiving();
        float cost = event.getBase();
        if (caster instanceof Player player) {
            var api = ArsMagicaAPI.get();
            for (ISpellPart iSpellPart : event.getSpell().parts()) {
                if (iSpellPart.getType() != ISpellPart.SpellPartType.COMPONENT) continue;
                ISpellPartData dataForPart = api.getSpellDataManager().getDataForPart(iSpellPart);
                if (dataForPart == null) continue;
                Set<IAffinity> affinities = dataForPart.affinities();
                for (IAffinity affinity : affinities) {
                    double value = api.getAffinityHelper().getAffinityDepthOrElse(player, affinity, 0);
                    if (value > 0) {
                        cost -= (float) (cost * (0.5f * value / 100f));
                    }
                }
            }
        }
        event.setBase(cost);
    }

    private static void playerLevelUp(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        int level = event.getLevel();
        var api = ArsMagicaAPI.get();
        if (level == 1) {
            api.getSkillHelper().addSkillPoint(player, AMSkillPoints.BLUE.getId(), Config.SERVER.EXTRA_BLUE_SKILL_POINTS.get());
        }
        for (ISkillPoint iSkillPoint : api.getSkillPointRegistry()) {
            int minEarnLevel = iSkillPoint.getMinEarnLevel();
            if (level >= minEarnLevel && (level - minEarnLevel) % iSkillPoint.getLevelsForPoint() == 0) {
                api.getSkillHelper().addSkillPoint(player, iSkillPoint);
                event.getPlayer().getLevel().playSound(null, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), AMSounds.GET_KNOWLEDGE_POINT.get(), SoundSource.PLAYERS, 1f, 1f);
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
        event.getPlayer().getLevel().playSound(null, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), AMSounds.MAGIC_LEVEL_UP.get(), SoundSource.PLAYERS, 1f, 1f);
    }
}
