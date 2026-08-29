package at.minecraftschurli.mods.arsmagicalegacy;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.mods.arsmagicalegacy.api.event.ManaBurnoutCostEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.event.SpellCastEvent;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.CrystalPhylacteryContentsSize;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.DryadKillsAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.SummonMinionsAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.block.LiquidEtheriumCauldronBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.mods.arsmagicalegacy.command.AffinityCommand;
import at.minecraftschurli.mods.arsmagicalegacy.command.MagicXpCommand;
import at.minecraftschurli.mods.arsmagicalegacy.command.SkillCommand;
import at.minecraftschurli.mods.arsmagicalegacy.command.SkillPointCommand;
import at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.mods.arsmagicalegacy.effect.AMMobEffect;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AirGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ArcaneGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.Dryad;
import at.minecraftschurli.mods.arsmagicalegacy.entity.EarthGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.EnderGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.FireGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.IceGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.LifeGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.LightningGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ManaCreeper;
import at.minecraftschurli.mods.arsmagicalegacy.entity.NatureGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.WaterGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAbilities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMRituals;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.FireAntennaeItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.LifeWardItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.RuneBagItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellBookItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.WaterOrbsItem;
import at.minecraftschurli.mods.arsmagicalegacy.packet.EnderBootsJumpPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.ForgetSkillsPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.InscriptionTableCreateSpellPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.InscriptionTableSyncPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.LearnSkillPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.LecternSyncPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.OpenBookInLecternPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SetActiveShapeGroupPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SetLecternPagePacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SetSpellRuneOwnerPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SpellBookScrollPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.SpellCustomizationPacket;
import at.minecraftschurli.mods.arsmagicalegacy.packet.TakeSpellRecipeFromLecternPacket;
import at.minecraftschurli.mods.arsmagicalegacy.spell.ToolTiers;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.BossBar;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.RegisterCauldronInteractionEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.VanillaGameEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.neoforged.neoforge.transfer.fluid.DispenseFluidContainer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID)
final class AMEventHandler {
    private AMEventHandler() {}

    @SubscribeEvent
    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerFlammability();
            DispenserBlock.registerBehavior(AMItems.LIQUID_ETHERIUM_BUCKET, DispenseFluidContainer.getInstance());
            DispenserBlock.registerBehavior(AMItems.WITCHWOOD_BOAT, new BoatDispenseItemBehavior(AMEntities.WITCHWOOD_BOAT.get()));
            DispenserBlock.registerBehavior(AMItems.WITCHWOOD_CHEST_BOAT, new BoatDispenseItemBehavior(AMEntities.WITCHWOOD_CHEST_BOAT.get()));
            FlowerPotBlock flowerPot = (FlowerPotBlock) Blocks.FLOWER_POT;
            flowerPot.addPlant(AMBlocks.AUM.getId(), AMBlocks.POTTED_AUM);
            flowerPot.addPlant(AMBlocks.CERUBLOSSOM.getId(), AMBlocks.POTTED_CERUBLOSSOM);
            flowerPot.addPlant(AMBlocks.DESERT_NOVA.getId(), AMBlocks.POTTED_DESERT_NOVA);
            flowerPot.addPlant(AMBlocks.TARMA_ROOT.getId(), AMBlocks.POTTED_TARMA_ROOT);
            flowerPot.addPlant(AMBlocks.WAKEBLOOM.getId(), AMBlocks.POTTED_WAKEBLOOM);
            AMMultiblocks.init();
        });
    }

    private static void registerFlammability() {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(AMBlocks.WITCHWOOD_LOG.get(), 5, 5);
        fire.setFlammable(AMBlocks.WITCHWOOD_WOOD.get(), 5, 5);
        fire.setFlammable(AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), 5, 5);
        fire.setFlammable(AMBlocks.STRIPPED_WITCHWOOD_WOOD.get(), 5, 5);
        fire.setFlammable(AMBlocks.WITCHWOOD_PLANKS.get(), 5, 20);
        fire.setFlammable(AMBlocks.WITCHWOOD_SLAB.get(), 5, 20);
        fire.setFlammable(AMBlocks.WITCHWOOD_STAIRS.get(), 5, 20);
        fire.setFlammable(AMBlocks.WITCHWOOD_FENCE.get(), 5, 20);
        fire.setFlammable(AMBlocks.WITCHWOOD_FENCE_GATE.get(), 5, 20);
        fire.setFlammable(AMBlocks.WITCHWOOD_LEAVES.get(), 30, 60);
        fire.setFlammable(AMBlocks.AUM.get(), 60, 100);
        fire.setFlammable(AMBlocks.CERUBLOSSOM.get(), 60, 100);
        fire.setFlammable(AMBlocks.DESERT_NOVA.get(), 60, 100);
        fire.setFlammable(AMBlocks.TARMA_ROOT.get(), 60, 100);
        fire.setFlammable(AMBlocks.WAKEBLOOM.get(), 60, 100);
    }

    @SubscribeEvent
    private static void registerCauldronInteractions(RegisterCauldronInteractionEvent.Dispatcher event) {
        event.register(LiquidEtheriumCauldronBlock.CAULDRON_INTERACTIONS_ID, LiquidEtheriumCauldronBlock.CAULDRON_INTERACTIONS);
    }

    @SubscribeEvent
    private static void registerCauldronInteractions(RegisterCauldronInteractionEvent.Interaction event) {
        event.register(LiquidEtheriumCauldronBlock.CAULDRON_INTERACTIONS_ID, Items.BUCKET, LiquidEtheriumCauldronBlock::fillBucket);
        event.register(LiquidEtheriumCauldronBlock.CAULDRON_INTERACTIONS_ID, Items.LAVA_BUCKET, CauldronInteractions::fillLavaInteraction);
        event.register(LiquidEtheriumCauldronBlock.CAULDRON_INTERACTIONS_ID, Items.WATER_BUCKET, CauldronInteractions::fillWaterInteraction);
        event.register(LiquidEtheriumCauldronBlock.CAULDRON_INTERACTIONS_ID, Items.POWDER_SNOW_BUCKET, CauldronInteractions::fillPowderSnowInteraction);
        event.registerToAll(AMItems.LIQUID_ETHERIUM_BUCKET.get(), LiquidEtheriumCauldronBlock::emptyBucket);
    }

    @SubscribeEvent
    private static void addBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
    }

    @SubscribeEvent
    private static void newRegistry(NewRegistryEvent event) {
        event.register(AMRegistries.ABILITY_EFFECTS);
        event.register(AMRegistries.GROWTH_TYPES);
        event.register(AMRegistries.RITUAL_EFFECTS);
        event.register(AMRegistries.RITUAL_REQUIREMENTS);
        event.register(AMRegistries.RITUAL_TRIGGERS);
        event.register(AMRegistries.SPELL_INGREDIENTS);
        event.register(AMRegistries.SPELL_PARTS);
    }

    @SubscribeEvent
    private static void newDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AMRegistries.Keys.ABILITY, Ability.DIRECT_CODEC, Ability.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.AFFINITY, Affinity.DIRECT_CODEC, Affinity.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.ALTAR_CAP_MATERIAL, AltarCapMaterial.DIRECT_CODEC, AltarCapMaterial.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.ALTAR_MATERIAL, AltarMaterial.DIRECT_CODEC, AltarMaterial.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.ETHERIUM_TYPE, EtheriumType.DIRECT_CODEC, EtheriumType.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.OCCULUS_TAB, OcculusTab.DIRECT_CODEC, OcculusTab.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.PLANT, Plant.DIRECT_CODEC, Plant.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.RITUAL, Ritual.DIRECT_CODEC, Ritual.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.SKILL, Skill.DIRECT_CODEC, Skill.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.SKILL_POINT, SkillPoint.DIRECT_CODEC, SkillPoint.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.SPELL_PART_DATA, SpellPartData.DIRECT_CODEC, SpellPartData.DIRECT_CODEC);
        event.dataPackRegistry(AMRegistries.Keys.SPELL_PREFAB, Spell.CODEC, Spell.CODEC);
    }

    @SubscribeEvent
    private static void addReloadListener(AddServerReloadListenersEvent event) {
        event.addListener(ToolTiers.ID, ToolTiers.INSTANCE);
    }

    @SubscribeEvent
    private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(CrystalPhylacteryContentsSize.DATA_MAP);
        event.register(ObeliskFuel.DATA_MAP);
    }

    @SubscribeEvent
    private static void entityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(AMEntities.DRYAD.get(), Dryad.createAttributes().build());
        event.put(AMEntities.MANA_CREEPER.get(), ManaCreeper.createAttributes().build());
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
    }

    @SubscribeEvent
    private static void entityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AMAttributes.BURNOUT_REGENERATION);
        event.add(EntityType.PLAYER, AMAttributes.MANA_REGENERATION);
        event.add(EntityType.PLAYER, AMAttributes.MAX_BURNOUT);
        event.add(EntityType.PLAYER, AMAttributes.MAX_MANA);
    }

    @SubscribeEvent
    private static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(AMEntities.DRYAD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Dryad::checkSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(AMEntities.MANA_CREEPER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.Item.ITEM, RuneBagItem::getItemHandler, AMItems.RUNE_BAG);
        event.registerItem(Capabilities.Item.ITEM, SpellBookItem::getItemHandler, AMItems.SPELL_BOOK);
        event.registerItem(Capabilities.Item.ITEM, SpellBookItem::getItemHandler, AMItems.ARCANE_SPELL_BOOK);
        event.registerBlock(Capabilities.Item.BLOCK, ObeliskBlock::getItemHandler, AMBlocks.OBELISK.get());
        event.registerBlockEntity(AMCapabilities.BLOCK_ETHERIUM, AMBlockEntities.ALTAR_CORE.get(), (blockEntity, _) -> blockEntity);
        event.registerBlockEntity(AMCapabilities.BLOCK_ETHERIUM, AMBlockEntities.OBELISK.get(), (blockEntity, _) -> blockEntity);
        event.registerBlockEntity(AMCapabilities.BLOCK_ETHERIUM, AMBlockEntities.CELESTIAL_PRISM.get(), (blockEntity, _) -> blockEntity);
        event.registerBlockEntity(AMCapabilities.BLOCK_ETHERIUM, AMBlockEntities.BLACK_AUREM.get(), (blockEntity, _) -> blockEntity);
    }

    @SubscribeEvent
    private static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar(ModList.get().getModFileById(ArsMagicaApi.MOD_ID).versionString())
            .playToClient(OpenBookInLecternPacket.TYPE, OpenBookInLecternPacket.STREAM_CODEC, OpenBookInLecternPacket::handle)
            .playToClient(LecternSyncPacket.TYPE, LecternSyncPacket.STREAM_CODEC, LecternSyncPacket::handle)
            .playToClient(SetSpellRuneOwnerPacket.TYPE, SetSpellRuneOwnerPacket.STREAM_CODEC, SetSpellRuneOwnerPacket::handle)
            .playToServer(EnderBootsJumpPacket.TYPE, EnderBootsJumpPacket.STREAM_CODEC, EnderBootsJumpPacket::handle)
            .playToServer(ForgetSkillsPacket.TYPE, ForgetSkillsPacket.STREAM_CODEC, ForgetSkillsPacket::handle)
            .playToServer(InscriptionTableCreateSpellPacket.TYPE, InscriptionTableCreateSpellPacket.STREAM_CODEC, InscriptionTableCreateSpellPacket::handle)
            .playToServer(InscriptionTableSyncPacket.TYPE, InscriptionTableSyncPacket.STREAM_CODEC, InscriptionTableSyncPacket::handle)
            .playToServer(LearnSkillPacket.TYPE, LearnSkillPacket.STREAM_CODEC, LearnSkillPacket::handle)
            .playToServer(SetActiveShapeGroupPacket.TYPE, SetActiveShapeGroupPacket.STREAM_CODEC, SetActiveShapeGroupPacket::handle)
            .playToServer(SetLecternPagePacket.TYPE, SetLecternPagePacket.STREAM_CODEC, SetLecternPagePacket::handle)
            .playToServer(SpellBookScrollPacket.TYPE, SpellBookScrollPacket.STREAM_CODEC, SpellBookScrollPacket::handle)
            .playToServer(SpellCustomizationPacket.TYPE, SpellCustomizationPacket.STREAM_CODEC, SpellCustomizationPacket::handle)
            .playToServer(TakeSpellRecipeFromLecternPacket.TYPE, TakeSpellRecipeFromLecternPacket.STREAM_CODEC, TakeSpellRecipeFromLecternPacket::handle);
    }

    @SubscribeEvent
    private static void registerCommands(RegisterCommandsEvent event) {
        CommandBuildContext context = event.getBuildContext();
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(ArsMagicaApi.MOD_ID).requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS));
        AffinityCommand.register(builder, context);
        MagicXpCommand.register(builder);
        SkillCommand.register(builder, context);
        SkillPointCommand.register(builder, context);
        event.getDispatcher().register(builder);
    }

    @SubscribeEvent
    private static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, AMItems.CHIMERITE.get(), AMMobEffects.LESSER_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.WAKEBLOOM.get(), AMMobEffects.STANDARD_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.VINTEUM_DUST.get(), AMMobEffects.GREATER_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.ARCANE_ASH.get(), AMMobEffects.EPIC_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.PURIFIED_VINTEUM_DUST.get(), AMMobEffects.LEGENDARY_MANA);
        builder.addMix(Potions.AWKWARD, AMItems.TARMA_ROOT.get(), AMMobEffects.INFUSED_MANA);
    }

    @SubscribeEvent
    private static void registerCauldronFluidContent(RegisterCauldronFluidContentEvent event) {
        event.register(AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(), AMFluids.LIQUID_ETHERIUM.get(), FluidType.BUCKET_VOLUME, null);
    }

    @SubscribeEvent
    private static void gameEvent(VanillaGameEvent event) {
        Ritual.perform(AMRituals.GAME_EVENT_TRIGGER.get(), event.getCause() instanceof Player player ? player : null, event.getLevel(), event.getEventPosition(), event.getVanillaEvent());
    }

    @SubscribeEvent
    private static void advancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        String advancement = AMServerConfig.MAGIC_ADVANCEMENT.get();
        if (!advancement.isEmpty() && event.getAdvancement().id().toString().equals(advancement)) {
            ArsMagicaApi.magicHelper().initiateMagic(event.getEntity());
        }
    }

    @SubscribeEvent
    private static void entityPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity == null) return;
        Level level = entity.level();
        BlockPos pos = event.getPos();
        Ritual.perform(AMRituals.SET_BLOCK_STATE_TRIGGER.get(), entity instanceof Player player ? player : null, level, Vec3.atLowerCornerOf(pos), level.getBlockState(pos));
    }

    @SubscribeEvent
    private static void playerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if (level.getBlockEntity(pos) instanceof LecternBlockEntity lectern && AMUtil.handleLecternUse(level, pos, level.getBlockState(pos), lectern, event.getEntity(), event.getHand())) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void entityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect() instanceof AMMobEffect effect) {
                effect.startEffect(entity, instance);
            }
        }
        if (!(event.getLevel() instanceof ServerLevel level) || !entity.hasData(AMAttachments.SUMMON_MINIONS)) return;
        SummonMinionsAttachment attachment = entity.getData(AMAttachments.SUMMON_MINIONS);
        for (UUID uuid : attachment.uuids()) {
            Entity e = level.getEntity(uuid);
            if (!(e instanceof Mob) || !e.isAlive()) {
                attachment = attachment.remove(uuid);
            }
        }
        entity.setData(AMAttachments.SUMMON_MINIONS, attachment);
    }

    @SubscribeEvent
    private static void levelTickPost(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level) {
            BossBar.tickAll(level);
        }
    }

    @SubscribeEvent
    private static void entityTickPost(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        if (entity instanceof ItemEntity itemEntity) {
            Ritual.perform(AMRituals.DROPPED_ITEM_TRIGGER.get(), itemEntity.getOwner() instanceof Player player ? player : null, level, itemEntity.position(), itemEntity);
        }
        if (entity instanceof ItemFrame itemFrame && (itemFrame.hasData(AMAttachments.COMPENDIUM_TIMER) || level.getGameTime() % AMServerConfig.ARCANE_COMPENDIUM_CONVERSION_CHECK_INTERVAL.getAsInt() == 0)) {
            AMUtil.doCompendiumConversion(itemFrame);
        }
        if (entity instanceof LivingEntity living) {
            ManaHelper manaHelper = ArsMagicaApi.manaHelper();
            manaHelper.increaseMana(living, manaHelper.getManaRegeneration(living));
            BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
            burnoutHelper.decreaseBurnout(living, burnoutHelper.getBurnoutRegeneration(living));
            if (living.hasEffect(AMMobEffects.WATERY_GRAVE) && entity.isInWater()) {
                entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getPose() == Pose.SWIMMING ? 0 : Math.min(0, entity.getDeltaMovement().y()), entity.getDeltaMovement().z());
            }
            if (living.getHealth() * 4 < living.getMaxHealth()) {
                ArsMagicaApi.spellHelper().triggerContingency(living, AMSpells.CONTINGENCY_HEALTH_ID);
            }
            if (living.isOnFire()) {
                if (FireAntennaeItem.isEquipped(living)) {
                    living.clearFire();
                } else {
                    ArsMagicaApi.spellHelper().triggerContingency(living, AMSpells.CONTINGENCY_FIRE_ID);
                }
            }
            if (WaterOrbsItem.isEquipped(living)) {
                living.setAirSupply(living.getMaxAirSupply());
            }
            LifeWardItem.tick(living);
        }
        if (!entity.hasData(AMAttachments.FROST)) return;
        int frost = entity.getData(AMAttachments.FROST);
        if (frost <= 0) return;
        if (frost == 1 || entity.isOnFire()) {
            entity.removeData(AMAttachments.FROST);
        } else {
            entity.setData(AMAttachments.FROST, frost - 1);
        }
        entity.setTicksFrozen(entity.getTicksFrozen() + 3);
    }

    @SubscribeEvent
    private static void playerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        ArsMagicaApi.abilityHelper().getActiveAbilities(player).forEach(holder -> holder.value().effects().forEach(effect -> effect.tick(player, holder)));
        FireAntennaeItem.tick(player);
        DryadKillsAttachment.tick(player);
    }

    @SubscribeEvent
    private static void entityInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity() instanceof LivingEntity living && FireAntennaeItem.isEquipped(living)) {
            event.setInvulnerable(true);
        }
    }

    @SubscribeEvent
    private static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (!source.is(AMTags.DamageTypes.BYPASSES_SHIELD_OVERLOAD) && entity instanceof Player player && ArsMagicaApi.magicHelper().knows(player, player.registryAccess().getOrThrow(AMMagic.SHIELD_OVERLOAD))) {
            event.addReductionModifier(DamageContainer.Reduction.INNATE_RESISTANCE, (_, damage) -> damage * (float) AMServerConfig.SHIELD_OVERLOAD_MULTIPLIER.getAsDouble());
        }
        if (!(source.getEntity() instanceof LivingEntity living)) return;
        if (entity.level() instanceof ServerLevel serverLevel) {
            AMUtil.setMinionTargets(serverLevel, living, entity);
        }
        if (!(living instanceof Player player)) return;
        AbilityHelper abilityHelper = ArsMagicaApi.abilityHelper();
        if (!entity.fireImmune()) {
            abilityHelper.getActiveAbilitiesWithEffect(player, AMAbilities.FIRE_PUNCH_EFFECT.get()).forEach(pair -> entity.setRemainingFireTicks(Math.max(entity.getRemainingFireTicks(), (int) pair.getSecond()
                .stream()
                .mapToDouble(e -> abilityHelper.scaleToDepth(player, pair.getFirst().value(), e.min(), e.max()))
                .sum())));
        }
        if (entity.canFreeze()) {
            abilityHelper.getActiveAbilitiesWithEffect(player, AMAbilities.FROST_PUNCH_EFFECT.get()).forEach(pair -> entity.setData(AMAttachments.FROST, Math.max(entity.getData(AMAttachments.FROST), (int) pair.getSecond()
                .stream()
                .mapToDouble(e -> abilityHelper.scaleToDepth(player, pair.getFirst().value(), e.min(), e.max()))
                .sum())));
        }
    }

    @SubscribeEvent
    private static void livingDamagePre(LivingDamageEvent.Pre event) {
        AbilityHelper abilityHelper = ArsMagicaApi.abilityHelper();
        if (event.getSource().getEntity() instanceof Player player) {
            abilityHelper.triggerEventEffect(event, player, AMAbilities.EXTRA_DAMAGE_EFFECT.get());
        }
        if (event.getEntity() instanceof Player player) {
            abilityHelper.triggerEventEffect(event, player, AMAbilities.DAMAGE_MODIFIER_EFFECT.get());
        }
    }

    @SubscribeEvent
    private static void livingDamagePost(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        ArsMagicaApi.spellHelper().triggerContingency(entity, AMSpells.CONTINGENCY_DAMAGE_ID);
        if (entity instanceof Player player) {
            ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, AMAbilities.THORNS_EFFECT.get());
        }
        if (!(event.getSource().getEntity() instanceof LivingEntity source)) return;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        AMUtil.setMinionTargets(serverLevel, entity, source);
    }

    @SubscribeEvent
    private static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.TEMPORAL_ANCHOR)) {
            entity.removeEffect(AMMobEffects.TEMPORAL_ANCHOR);
            event.setCanceled(true);
            return;
        }
        Level level = entity.level();
        ArsMagicaApi.spellHelper().triggerContingency(entity, AMSpells.CONTINGENCY_DEATH_ID);
        if (event.getSource().getEntity() instanceof Player player) {
            ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, AMAbilities.KILL_EFFECT_EFFECT.get());
            CrystalPhylacteryItem.addFill(player, entity);
            if (entity.getType() == AMEntities.DRYAD.get()) {
                DryadKillsAttachment.kill(player, entity);
            }
            Ritual.perform(AMRituals.KILL_ENTITY_TRIGGER.get(), player, level, entity.position(), entity);
        }
        if (!(level instanceof ServerLevel serverLevel) || !entity.hasData(AMAttachments.SUMMON_OWNER)) return;
        UUID uuid = entity.getData(AMAttachments.SUMMON_OWNER);
        if (uuid.equals(Util.NIL_UUID)) return;
        Entity owner = serverLevel.getEntity(uuid);
        if (owner == null) return;
        owner.setData(AMAttachments.SUMMON_MINIONS, owner.getData(AMAttachments.SUMMON_MINIONS).remove(entity.getUUID()));
    }

    @SubscribeEvent
    private static void livingExperienceDrop(LivingExperienceDropEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasData(AMAttachments.SUMMON_OWNER) && !entity.getData(AMAttachments.SUMMON_OWNER).equals(Util.NIL_UUID)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void livingUseItemStart(LivingEntityUseItemEvent.Start event) {
        ItemStack item = event.getItem();
        if (item.has(AMDataComponents.SPELL) && !Objects.requireNonNull(item.get(AMDataComponents.SPELL)).isEmpty() && event.getEntity() instanceof Player player && ArsMagicaApi.magicHelper().knows(player, AMRegistries.skills(player.registryAccess()).getOrThrow(AMMagic.SPELL_MOTION))) {
            item.set(DataComponents.USE_EFFECTS, SpellItem.SPELL_MOTION_USE_EFFECTS);
        }
    }

    @SubscribeEvent
    private static void livingUseItemStop(LivingEntityUseItemEvent.Stop event) {
        ItemStack item = event.getItem();
        if (item.has(DataComponents.USE_EFFECTS) && item.has(AMDataComponents.SPELL) && !Objects.requireNonNull(item.get(AMDataComponents.SPELL)).isEmpty()) {
            item.remove(DataComponents.USE_EFFECTS);
        }
    }

    @SubscribeEvent
    private static void livingJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, AMAbilities.JUMP_BOOST_EFFECT.get());
    }

    @SubscribeEvent
    private static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(AMItems.ENDER_BOOTS)) {
            event.setDamageMultiplier((float) (event.getDamageMultiplier() * AMServerConfig.ENDER_BOOTS_FALL_DAMAGE_MULTIPLIER.getAsDouble()));
        }
        ArsMagicaApi.spellHelper().triggerContingency(entity, AMSpells.CONTINGENCY_FALL_ID);
    }

    @SubscribeEvent
    private static void livingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel level)) return;
        UUID uuid = entity.getData(AMAttachments.SUMMON_OWNER);
        LivingEntity target = event.getNewAboutToBeSetTarget();
        if (target == null) return;
        if (uuid.equals(target.getUUID())) {
            event.setCanceled(true);
        } else {
            AMUtil.setMinionTargets(level, entity, target);
        }
    }

    @SubscribeEvent
    private static void potionAdded(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance.getEffect().value() instanceof AMMobEffect effect) {
            effect.startEffect(entity, effectInstance);
        }
    }

    @SubscribeEvent
    private static void potionExpiry(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.getEffect().value() instanceof AMMobEffect effect) {
            effect.stopEffect(entity, effectInstance);
        }
    }

    @SubscribeEvent
    private static void potionRemove(MobEffectEvent.Remove event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();
        if (effectInstance != null && effectInstance.getEffect().value() instanceof AMMobEffect effect) {
            effect.stopEffect(entity, effectInstance);
        }
    }

    @SubscribeEvent
    private static void enderEntityTeleport(EntityTeleportEvent.EnderEntity event) {
        onTeleport(event);
    }

    @SubscribeEvent
    private static void enderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        onTeleport(event);
    }

    @SubscribeEvent
    private static void itemTeleport(EntityTeleportEvent.ItemConsumption event) {
        onTeleport(event);
    }
    
    private static void onTeleport(EntityTeleportEvent event) {
        LivingEntity entity = event.getEntity().asLivingEntity();
        if (entity != null && entity.hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            if (entity instanceof Player player) {
                player.sendSystemMessage(AMTranslations.NO_TELEPORT);
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    private static void enderManAnger(EnderManAngerEvent event) {
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, event.getPlayer(), AMAbilities.ENDERMAN_PUMPKIN_EFFECT.get());
    }

    @SubscribeEvent
    private static void manaBurnoutCost(ManaBurnoutCostEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, AMAbilities.BURNOUT_COST_MODIFIER_EFFECT.get());
    }

    @SubscribeEvent
    private static void spellCastPost(SpellCastEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ArsMagicaApi.abilityHelper().triggerEventEffect(event, player, AMAbilities.SPELL_CAST_EFFECT_EFFECT.get());
        Spell spell = event.getSpell();
        Set<SpellPart> spellParts = new HashSet<>(spell.currentShapeGroup().parts());
        spellParts.addAll(spell.grammar().parts());
        Ritual.perform(AMRituals.SPELL_CAST_TRIGGER.get(), player, player.level(), player.position(), spellParts);
    }
}
