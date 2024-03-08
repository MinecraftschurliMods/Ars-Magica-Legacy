package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
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
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlockEntity;
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
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMTalents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWoodTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag.RuneBagItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.TierMapping;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenSpellRecipeGuiInLecternPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.common.crafting.NBTIngredient;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;
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
        IEventBus forgeBus = NeoForge.EVENT_BUS;
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
        forgeBus.addListener(EventHandler::addReloadListener);
        forgeBus.addListener(EventHandler::entityJoinWorld);
        forgeBus.addListener(EventHandler::playerItemCrafted);
        forgeBus.addListener(EventHandler::playerRespawn);
        forgeBus.addListener(EventHandler::livingHurt);
        forgeBus.addListener(EventHandler::livingDamage);
        forgeBus.addListener(EventHandler::rightClickBlock);
        forgeBus.addListener(EventHandler::affinityChangingPre);
        forgeBus.addListener(EventPriority.HIGH, EventHandler::manaCostPre);
        forgeBus.addListener(EventHandler::modifyStats);
        forgeBus.addListener(EventHandler::playerLevelUp);
    }

    private static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WoodType.register(AMWoodTypes.WITCHWOOD);
            registerBrewingRecipes();
        });
        CompatManager.init(event);
    }

    private static void registerCreativeTabs(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) return;
        event.register(Registries.CREATIVE_MODE_TAB, ArsMagicaAPI.MAIN_CREATIVE_TAB, EventHandler::buildMainCreativeTab);
        event.register(Registries.CREATIVE_MODE_TAB, ArsMagicaAPI.PREFAB_SPELLS_CREATIVE_TAB, EventHandler::buildPrefabSpellsCreativeTab);
    }

    private static CreativeModeTab buildMainCreativeTab() {
        return CreativeModeTab
                .builder()
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS, CreativeModeTabs.OP_BLOCKS)
                .withTabsAfter(ArsMagicaAPI.PREFAB_SPELLS_CREATIVE_TAB)
                .icon(() -> ArsMagicaAPI.get().getBookStack())
                .title(Component.translatable(TranslationConstants.MAIN_CREATIVE_TAB))
                .displayItems((params, output) -> {
                    List<ItemStack> list = new ArrayList<>();
                    var api = ArsMagicaAPI.get();
                    for (DeferredHolder<Item, ? extends Item> o : AMRegistries.ITEMS.getEntries()) {
                        if (!o.isBound()) continue;
                        Item item = o.get();
                        if (item instanceof ISkillPointItem skillPointItem) {
                        for (SkillPoint point : api.getSkillPointRegistry()) {
                            if (point != AMSkillPoints.NONE.value()) {
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
                }).build();
    }

    private static CreativeModeTab buildPrefabSpellsCreativeTab() {
        return CreativeModeTab
                .builder()
                .withTabsBefore(ArsMagicaAPI.MAIN_CREATIVE_TAB)
                .icon(() -> AMItems.SPELL_PARCHMENT.asOptional().map(ItemStack::new).orElse(ItemStack.EMPTY))
                .title(Component.translatable(TranslationConstants.PREFAB_SPELL_CREATIVE_TAB))
                .displayItems((params, output) -> AMUtil.getRegistry(PrefabSpell.REGISTRY_KEY).stream().map(PrefabSpell::makeSpell).forEach(output::accept))
                .build();
    }

    private static void registerSpawnPlacements(SpawnPlacementRegisterEvent evt) {
        evt.register(AMEntities.DRYAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dryad::checkDryadSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        evt.register(AMEntities.MANA_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    private static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.LESSER_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.STANDARD_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.GREATER_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.EPIC_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.LEGENDARY_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.POTION), AMMobEffects.INFUSED_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.LESSER_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.STANDARD_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.GREATER_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.EPIC_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.LEGENDARY_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), AMMobEffects.INFUSED_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.CHIMERITE.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.LESSER_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.WAKEBLOOM.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.STANDARD_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.GREATER_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.ARCANE_ASH.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.EPIC_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.PURIFIED_VINTEUM_DUST.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.LEGENDARY_MANA.value()));
        BrewingRecipeRegistry.addRecipe(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)), Ingredient.of(AMItems.TARMA_ROOT.get()), PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), AMMobEffects.INFUSED_MANA.value()));
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, SpellBookItem::getItemCapability, AMItems.SPELL_BOOK);
        event.registerItem(Capabilities.ItemHandler.ITEM, RuneBagItem::getItemCapability, AMItems.RUNE_BAG);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, AMBlockEntities.OBELISK.get(), ObeliskBlockEntity::getItemCapability);
        event.registerBlockEntity(EtheriumHelper.ETHERIUM_PROVIDER, AMBlockEntities.OBELISK.get(), ObeliskBlockEntity::getEtheriumCapability);
        event.registerBlockEntity(EtheriumHelper.ETHERIUM_PROVIDER, AMBlockEntities.CELESTIAL_PRISM.get(), CelestialPrismBlockEntity::getEtheriumCapability);
        event.registerBlockEntity(EtheriumHelper.ETHERIUM_PROVIDER, AMBlockEntities.BLACK_AUREM.get(), BlackAuremBlockEntity::getEtheriumCapability);
        event.registerBlockEntity(EtheriumHelper.ETHERIUM_CONSUMER, AMBlockEntities.ALTAR_CORE.get(), AltarCoreBlockEntity::getEtheriumCapability);
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
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA.value());
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT.value());
        event.add(EntityType.PLAYER, AMAttributes.MANA_REGEN.value());
        event.add(EntityType.PLAYER, AMAttributes.BURNOUT_REGEN.value());
        for (EntityType<? extends LivingEntity> entity : event.getTypes()) {
            event.add(entity, AMAttributes.SCALE.value());
        }
    }

    private static void enqueueIMC(InterModEnqueueEvent event) {
        CompatManager.imcEnqueue(event);
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
        MagicHelper.instance().syncToPlayer(player);
        ManaHelper.instance().syncToPlayer(player);
        BurnoutHelper.instance().syncToPlayer(player);
    }

    private static void playerItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        var api = ArsMagicaAPI.get();
        var helper = api.getMagicHelper();
        if (helper.knowsMagic(event.getEntity())) return;
        if (!ItemStack.isSameItemSameTags(api.getBookStack(), event.getCrafting())) return;
        helper.awardXp(event.getEntity(), 0);
    }

    private static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        AttributeInstance maxManaAttr = event.getEntity().getAttribute(AMAttributes.MAX_MANA.value());
        if (maxManaAttr != null) {
            ArsMagicaAPI.get().getManaHelper().increaseMana(event.getEntity(), (float) (maxManaAttr.getBaseValue() / 2));
        }
    }

    private static void livingHurt(LivingHurtEvent event) {
        var api = ArsMagicaAPI.get();
        var helper = api.getManaHelper();
        if (event.getEntity() instanceof Player player && api.getSkillHelper().knows(player, AMTalents.SHIELD_OVERLOAD) && helper.getMana(player) == helper.getMaxMana(player)) {
            event.setAmount(event.getAmount() * 0.95f);
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
            if (player instanceof ServerPlayer sp) {
                PacketDistributor.PLAYER.with(sp).send(new OpenSpellRecipeGuiInLecternPacket(stack, pos, lectern.getPage()));
            }
            player.awardStat(Stats.INTERACT_WITH_LECTERN);
            event.setCanceled(true);
        }
    }

    private static void affinityChangingPre(AffinityChangingEvent.Pre event) {
        if (ArsMagicaAPI.get().getSkillHelper().knows(event.getEntity(), AMTalents.AFFINITY_GAINS_BOOST)) {
            event.shift *= 1.05f;
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

    private static void modifyStats(SpellEvent.ModifyStats event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!ArsMagicaAPI.get().getSkillHelper().knows(player, AMTalents.AUGMENTED_CASTING)) return;
        if (!(event.stat instanceof SpellPartStats sps)) return;
        switch (sps) {
            case DAMAGE, FORTUNE, PIERCING, POWER -> event.modified += 1;
            case DURATION, HEALING, RANGE, SPEED -> event.modified += event.base * 0.5f;
        }
    }

    private static void playerLevelUp(PlayerLevelUpEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        int magicLevel = event.getLevel();
        var api = ArsMagicaAPI.get();
        if (magicLevel == 1) {
            api.getSkillHelper().addSkillPoint(player, AMSkillPoints.BLUE, Config.SERVER.EXTRA_BLUE_SKILL_POINTS.get());
        }
        for (SkillPoint skillPoint : api.getSkillPointRegistry()) {
            int minEarnLevel = skillPoint.minEarnLevel();
            int levelsForPoint = skillPoint.levelsForPoint();
            if (minEarnLevel < 0 || levelsForPoint < 0) continue;
            if (magicLevel >= minEarnLevel && (magicLevel - minEarnLevel) % levelsForPoint == 0) {
                api.getSkillHelper().addSkillPoint(player, skillPoint);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), AMSounds.GET_KNOWLEDGE_POINT.value(), SoundSource.PLAYERS, 1f, 1f);
            }
        }
        float newMaxMana = Config.SERVER.MANA_BASE.get().floatValue() + Config.SERVER.MANA_MULTIPLIER.get().floatValue() * (magicLevel - 1);
        AttributeInstance maxManaAttr = player.getAttribute(AMAttributes.MAX_MANA.value());
        if (maxManaAttr != null) {
            IManaHelper manaHelper = api.getManaHelper();
            maxManaAttr.setBaseValue(newMaxMana);
            manaHelper.increaseMana(player, (newMaxMana - manaHelper.getMana(player)) / 2);
        }
        AttributeInstance manaRegenAttr = player.getAttribute(AMAttributes.MANA_REGEN.value());
        if (manaRegenAttr != null) {
            manaRegenAttr.setBaseValue(newMaxMana * Config.SERVER.MANA_REGEN_MULTIPLIER.get() );
        }
        float newMaxBurnout = Config.SERVER.BURNOUT_BASE.get().floatValue() + Config.SERVER.BURNOUT_MULTIPLIER.get().floatValue() * (magicLevel - 1);
        AttributeInstance maxBurnoutAttr = player.getAttribute(AMAttributes.MAX_BURNOUT.value());
        if (maxBurnoutAttr != null) {
            IBurnoutHelper burnoutHelper = api.getBurnoutHelper();
            maxBurnoutAttr.setBaseValue(newMaxBurnout);
            burnoutHelper.decreaseBurnout(player, burnoutHelper.getBurnout(player) / 2);
        }
        AttributeInstance burnoutRegenAttr = player.getAttribute(AMAttributes.BURNOUT_REGEN.value());
        if (burnoutRegenAttr != null) {
            burnoutRegenAttr.setBaseValue(newMaxMana * Config.SERVER.BURNOUT_REGEN_MULTIPLIER.get() );
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), AMSounds.MAGIC_LEVEL_UP.value(), SoundSource.PLAYERS, 1f, 1f);
    }
}
