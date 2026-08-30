package at.minecraftschurli.mods.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.InlayBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.SpellRuneBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.WizardsChalkBlock;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.AltarCoreModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalPhylacteryItemTintSource;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalPhylacteryRangeSelectItemModelProperty;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.CrystalWrenchActiveItemModelProperty;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.DataComponentOverridesModel;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.EtheriumTypeItemTintSource;
import at.minecraftschurli.mods.arsmagicalegacy.client.model.item.SpellItemModel;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import at.minecraftschurli.mods.easydatagenlib.AbstractModelProvider;
import at.minecraftschurli.mods.easydatagenlib.util.BlockModelDatagenUtil;
import at.minecraftschurli.mods.easydatagenlib.util.WrappingCustomBlockStateModelBuilder;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public final class AMModelProvider extends AbstractModelProvider {
    private static final TextureSlot TEX = TextureSlot.create("tex");
    private static final ModelTemplate CELESTIAL_PRISM_TEMPLATE = ModelTemplates.create(TextureSlot.PARTICLE, TEX)
        .extend()
        .customLoader(ObjModelBuilder::new, b -> b
            .modelLocation(ArsMagicaApi.id("models/block/celestial_prism.obj"))
            .emissiveAmbient(false)
            .automaticCulling(false)
            .shadeQuads(false))
        .build();
    private static final ModelTemplate OBELISK_TEMPLATE = ModelTemplates.create(TextureSlot.PARTICLE, TEX)
        .extend()
        .customLoader(ObjModelBuilder::new, b -> b
            .modelLocation(ArsMagicaApi.id("models/block/obelisk.obj"))
            .emissiveAmbient(false)
            .automaticCulling(false)
            .shadeQuads(false))
        .build();
    private static final ModelTemplate PARTICLE_ONLY_TEMPLATE = ModelTemplates.PARTICLE_ONLY.extend().suffix("_particle").build();
    private static final Map<Direction, ModelTemplate> SPELL_RUNE_TEMPLATE = Util.makeEnumMap(Direction.class, direction -> new ModelTemplate(Optional.empty(), Optional.of("_" + direction.getName()), TextureSlot.TEXTURE, TextureSlot.PARTICLE)
        .extend()
        .element(element -> {
            AABB aabb = SpellRuneBlock.SHAPES.get(direction).bounds();
            element
                .from((float) aabb.minX * 16, (float) aabb.minY * 16, (float) aabb.minZ * 16)
                .to((float) aabb.maxX * 16, (float) aabb.maxY * 16, (float) aabb.maxZ * 16)
                .face(direction.getOpposite(), face -> face.texture(TextureSlot.TEXTURE));
        })
        .build());
    private static final Identifier SPELL_PARENT_ID = ArsMagicaApi.id("item/template_spell");
    private static final ModelTemplate SPELL_PARENT_TEMPLATE = ModelTemplates.createItem("generated").extend()
        .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, b -> b.translation(-1.5f, 8.5f, 0f).scale(0.5f, 0.5f, 0.01f))
        .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, b -> b.translation(-1.5f, 8.5f, 0f).scale(0.5f, 0.5f, 0.01f))
        .build();
    private static final ModelTemplate SPELL_TEMPLATE = ModelTemplates.FLAT_ITEM.extend().parent(SPELL_PARENT_ID).build();
    private static final TextureSlot ALTAR_CORE_OVERLAY = TextureSlot.create("overlay");
    private static final ModelTemplate ALTAR_CORE_OVERLAY_TEMPLATE = new ModelTemplate(Optional.empty(), Optional.of("_overlay"), ALTAR_CORE_OVERLAY, TextureSlot.PARTICLE).extend()
        .element(element -> element
            .from(0, 0, 0)
            .to(16, 0, 16)
            .face(Direction.DOWN, face -> face.texture(ALTAR_CORE_OVERLAY)))
        .build();
    private static final List<DeferredBlock<?>> IGNORED_BLOCKS = List.of(AMBlocks.INSCRIPTION_TABLE);
    private static final List<DeferredItem<?>> IGNORED_ITEMS = List.of(AMItems.WATER_ORBS, AMItems.FIRE_ANTENNAE, AMItems.EARTH_ARMOR, AMItems.AIR_SLED, AMItems.WINTERS_GRASP, AMItems.NATURE_SCYTHE);

    public AMModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks().filter(h -> IGNORED_BLOCKS.stream().noneMatch(e -> e.is(h)));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return super.getKnownItems().filter(h -> IGNORED_ITEMS.stream().noneMatch(e -> e.is(h)));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {
        BlockFamily blockFamily = AMBlocks.WITCHWOOD_BLOCK_FAMILY.get();
        blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily);
        blockModels.createNonTemplateModelBlock(AMBlocks.SPELL_LIGHT.get(), Blocks.AIR);
        blockModels.woodProvider(AMBlocks.WITCHWOOD_LOG.get()).logWithHorizontal(AMBlocks.WITCHWOOD_LOG.get()).wood(AMBlocks.WITCHWOOD_WOOD.get());
        blockModels.woodProvider(AMBlocks.STRIPPED_WITCHWOOD_LOG.get()).logWithHorizontal(AMBlocks.STRIPPED_WITCHWOOD_LOG.get()).wood(AMBlocks.STRIPPED_WITCHWOOD_WOOD.get());
        blockModels.createHangingSign(AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
        blockModels.createPlantWithDefaultItem(AMBlocks.WITCHWOOD_SAPLING.get(), AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createTrivialCube(AMBlocks.WITCHWOOD_LEAVES.get());
        blockModels.createPlantWithDefaultItem(AMBlocks.AUM.get(), AMBlocks.POTTED_AUM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.CERUBLOSSOM.get(), AMBlocks.POTTED_CERUBLOSSOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.DESERT_NOVA.get(), AMBlocks.POTTED_DESERT_NOVA.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.TARMA_ROOT.get(), AMBlocks.POTTED_TARMA_ROOT.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.WAKEBLOOM.get(), AMBlocks.POTTED_WAKEBLOOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createTrivialCube(AMBlocks.CHIMERITE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.CHIMERITE_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.TOPAZ_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_TOPAZ_ORE.get());
        blockModels.createTrivialCube(AMBlocks.TOPAZ_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.VINTEUM_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_VINTEUM_ORE.get());
        blockModels.createTrivialCube(AMBlocks.VINTEUM_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.MOONSTONE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.MOONSTONE_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.SUNSTONE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.SUNSTONE_BLOCK.get());
        createInlay(blockModels, AMBlocks.REDSTONE_INLAY.get());
        createInlay(blockModels, AMBlocks.IRON_INLAY.get());
        createInlay(blockModels, AMBlocks.GOLD_INLAY.get());
        blockModels.createNormalTorch(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get());
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.WIZARDS_CHALK)
            .withModelDispatch(WizardsChalkBlock.VARIANT, i -> ModelTemplates.RAIL_FLAT.createWithSuffix(AMBlocks.WIZARDS_CHALK.get(), "_" + i, TextureMapping.rail(TextureMapping.getBlockTexture(AMBlocks.WIZARDS_CHALK.get(), "_" + i)), blockModels.modelOutput))
            .withHorizontalRotation()
            .build();
        blockModels.createNonTemplateHorizontalBlock(AMBlocks.OCCULUS.get());
        blockModels.createTrivialCube(AMBlocks.MAGIC_WALL.get());
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.CELESTIAL_PRISM)
            .withModelDispatch(CelestialPrismBlock.PART, part -> switch (part) {
                case LOWER -> CELESTIAL_PRISM_TEMPLATE;
                case UPPER -> PARTICLE_ONLY_TEMPLATE;
            }, TextureMapping.particle(AMBlocks.CELESTIAL_PRISM.get()).put(TEX, TextureMapping.getBlockTexture(AMBlocks.CELESTIAL_PRISM.get())))
            .withItemModel(ArsMagicaApi.id("item/celestial_prism"))
            .build();
        Identifier obeliskParticleOnly = PARTICLE_ONLY_TEMPLATE.create(
            AMBlocks.OBELISK.get(),
            TextureMapping.particle(Blocks.STONE_BRICKS),
            blockModels.modelOutput);
        Identifier obeliskLit = blockModels.createSuffixedVariant(
            AMBlocks.OBELISK.get(),
            "_lit",
            OBELISK_TEMPLATE,
            mat -> TextureMapping.particle(Blocks.STONE_BRICKS).put(TEX, mat));
        Identifier obeliskUnlit = OBELISK_TEMPLATE.create(
            AMBlocks.OBELISK.get(),
            TextureMapping.particle(Blocks.STONE_BRICKS).put(TEX, TextureMapping.getBlockTexture(AMBlocks.OBELISK.get())),
            blockModels.modelOutput);
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.OBELISK)
            .withModelDispatch(ObeliskBlock.PART, ObeliskBlock.LIT, (part, lit) -> part != ObeliskBlock.Part.LOWER ? obeliskParticleOnly : lit ? obeliskLit : obeliskUnlit)
            .withVariantDispatch(BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT)
            .withItemModel(ArsMagicaApi.id("item/obelisk"))
            .build();
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.ALTAR_CORE)
            .withModelDispatch(BlockModelGenerators.createBooleanModelDispatch(
                AltarCoreBlock.FORMED,
                MultiVariant.of(new AltarCoreModelBuilder(BlockModelGenerators.plainVariant(ALTAR_CORE_OVERLAY_TEMPLATE.create(AMBlocks.ALTAR_CORE.get(), TextureMapping.particle(AMBlocks.ALTAR_CORE.get()).put(ALTAR_CORE_OVERLAY, TextureMapping.getBlockTexture(AMBlocks.ALTAR_CORE.get(), "_overlay")), blockModels.modelOutput)))),
                BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ALL.create(AMBlocks.ALTAR_CORE.get(), TextureMapping.cube(AMBlocks.ALTAR_CORE.get()), blockModels.modelOutput))))
            .build();
        blockModels.createParticleOnlyBlock(AMBlocks.BLACK_AUREM.get());
        blockModels.registerSimpleFlatItemModel(AMBlocks.BLACK_AUREM.get());
        blockModels.createNonTemplateModelBlock(AMBlocks.LIQUID_ETHERIUM.get());
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
            AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(),
            BlockModelGenerators.plainVariant(ModelTemplates.CAULDRON_FULL.create(
                AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(),
                TextureMapping.cauldron(TextureMapping.getBlockTexture(AMBlocks.LIQUID_ETHERIUM.get(), "_still")),
                blockModels.modelOutput))));
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.SPELL_RUNE)
            .withModelDispatch(SpellRuneBlock.FACING, SPELL_RUNE_TEMPLATE::get, TextureMapping.defaultTexture(AMBlocks.SPELL_RUNE.get()).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(AMBlocks.SPELL_RUNE.get())))
            .build();
    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        itemModels.itemModelOutput.register(ArsMagicaApi.id("arcane_compendium"), new ClientItem(
            ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(
                ArsMagicaApi.id("item/arcane_compendium"),
                TextureMapping.layer0(new Material(ArsMagicaApi.id("item/arcane_compendium"))),
                itemModels.modelOutput)),
            ClientItem.Properties.DEFAULT));
        SPELL_PARENT_TEMPLATE.create(SPELL_PARENT_ID, new TextureMapping(), itemModels.modelOutput);
        itemWithVariants(itemModels, AMItems.SPELL, new SpellItemModel.Unbaked(ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.SPELL.get(), SPELL_TEMPLATE))), SPELL_TEMPLATE, AMMagic.AFFINITIES_WITH_NONE);
        itemModels.generateFlatItem(AMItems.SPELL_RECIPE.get(), Items.WRITTEN_BOOK, ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(AMItems.ETHERIUM_PLACEHOLDER.get(), ItemModelUtils.tintedModel(itemModels.createFlatItemModel(AMItems.ETHERIUM_PLACEHOLDER.get(), ModelTemplates.FLAT_ITEM), new EtheriumTypeItemTintSource()));
        itemModels.itemModelOutput.accept(AMItems.LIQUID_ETHERIUM_BUCKET.get(), new DynamicFluidContainerModel.Unbaked(new DynamicFluidContainerModel.Textures(
            Optional.of(new Material(Identifier.withDefaultNamespace("item/bucket"))),
            Optional.of(new Material(Identifier.withDefaultNamespace("item/bucket"))),
            Optional.of(new Material(Identifier.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid"))),
            Optional.empty()
        ), AMFluids.LIQUID_ETHERIUM.get(), false, true, true));
        itemModels.itemModelOutput.accept(AMItems.INSCRIPTION_TABLE.get(), ItemModelUtils.plainModel(ArsMagicaApi.id("item/inscription_table")));
        basicItem(itemModels, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1);
        basicItem(itemModels, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2);
        basicItem(itemModels, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3);
        itemModels.generateBooleanDispatch(
            AMItems.CRYSTAL_WRENCH.get(),
            new CrystalWrenchActiveItemModelProperty(),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.CRYSTAL_WRENCH.get(), "_active", ModelTemplates.FLAT_ITEM)),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.CRYSTAL_WRENCH.get(), ModelTemplates.FLAT_ITEM))
        );
        basicItem(itemModels, AMItems.WIZARDS_CHALK);
        basicItem(itemModels, AMItems.SPELL_PARCHMENT);
        itemModels.itemModelOutput.accept(AMItems.SPELL_BOOK.get(), new SpellItemModel.Unbaked(ItemModelUtils.tintedModel(
            itemModels.generateLayeredItem(
                AMItems.SPELL_BOOK.get(),
                TextureMapping.getItemTexture(AMItems.SPELL_BOOK.get()),
                TextureMapping.getItemTexture(AMItems.SPELL_BOOK.get(), "_overlay")),
            ItemModelGenerators.BLANK_LAYER,
            new Dye(0xff000000))));
        basicItem(itemModels, AMItems.MAGITECH_GOGGLES);
        basicItem(itemModels, AMItems.MAGE_HELMET);
        basicItem(itemModels, AMItems.MAGE_CHESTPLATE);
        basicItem(itemModels, AMItems.MAGE_LEGGINGS);
        basicItem(itemModels, AMItems.MAGE_BOOTS);
        basicItem(itemModels, AMItems.BATTLEMAGE_HELMET);
        basicItem(itemModels, AMItems.BATTLEMAGE_CHESTPLATE);
        basicItem(itemModels, AMItems.BATTLEMAGE_LEGGINGS);
        basicItem(itemModels, AMItems.BATTLEMAGE_BOOTS);
        basicItem(itemModels, AMItems.MANA_CAKE);
        basicItem(itemModels, AMItems.MANA_MARTINI);
        itemWithVariants(itemModels, AMItems.INFINITY_ORB, new DataComponentOverridesModel.Unbaked<>(
            AMDataComponents.SKILL_POINT.get(),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.INFINITY_ORB.get(), ModelTemplates.FLAT_ITEM))
        ), ModelTemplates.FLAT_ITEM, AMMagic.SKILL_POINTS);
        itemModels.itemModelOutput.register(AMItems.WINTERS_GRASP.getId(), new ClientItem(ItemModelUtils.plainModel(AMItems.WINTERS_GRASP.getId().withPrefix("item/")), ClientItem.Properties.DEFAULT));
        basicItem(itemModels, AMItems.LIGHTNING_CHARM);
        itemModels.itemModelOutput.register(AMItems.NATURE_SCYTHE.getId(), new ClientItem(ItemModelUtils.plainModel(AMItems.NATURE_SCYTHE.getId().withPrefix("item/")), ClientItem.Properties.DEFAULT));
        basicItem(itemModels, AMItems.LIFE_WARD);
        basicItem(itemModels, AMItems.ARCANE_SPELL_BOOK);
        basicItem(itemModels, AMItems.ENDER_BOOTS);
        itemWithVariants(itemModels, AMItems.AFFINITY_ESSENCE, new DataComponentOverridesModel.Unbaked<>(
            AMDataComponents.AFFINITY.get(),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.AFFINITY_ESSENCE.get(), ModelTemplates.FLAT_ITEM))
        ), ModelTemplates.FLAT_ITEM, AMMagic.AFFINITIES);
        itemWithVariants(itemModels, AMItems.AFFINITY_TOME, new DataComponentOverridesModel.Unbaked<>(
            AMDataComponents.AFFINITY.get(),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.AFFINITY_TOME.get(), ModelTemplates.FLAT_ITEM))
        ), ModelTemplates.FLAT_ITEM, AMMagic.AFFINITIES_WITH_NONE);
        basicItem(itemModels, AMItems.BLANK_RUNE);
        basicItem(itemModels, AMItems.WHITE_RUNE);
        basicItem(itemModels, AMItems.ORANGE_RUNE);
        basicItem(itemModels, AMItems.MAGENTA_RUNE);
        basicItem(itemModels, AMItems.LIGHT_BLUE_RUNE);
        basicItem(itemModels, AMItems.YELLOW_RUNE);
        basicItem(itemModels, AMItems.LIME_RUNE);
        basicItem(itemModels, AMItems.PINK_RUNE);
        basicItem(itemModels, AMItems.GRAY_RUNE);
        basicItem(itemModels, AMItems.LIGHT_GRAY_RUNE);
        basicItem(itemModels, AMItems.CYAN_RUNE);
        basicItem(itemModels, AMItems.PURPLE_RUNE);
        basicItem(itemModels, AMItems.BLUE_RUNE);
        basicItem(itemModels, AMItems.BROWN_RUNE);
        basicItem(itemModels, AMItems.GREEN_RUNE);
        basicItem(itemModels, AMItems.RED_RUNE);
        basicItem(itemModels, AMItems.BLACK_RUNE);
        basicItem(itemModels, AMItems.RUNE_BAG);
        basicItem(itemModels, AMItems.CHIMERITE);
        basicItem(itemModels, AMItems.TOPAZ);
        basicItem(itemModels, AMItems.VINTEUM_DUST);
        basicItem(itemModels, AMItems.MOONSTONE);
        basicItem(itemModels, AMItems.SUNSTONE);
        basicItem(itemModels, AMItems.ARCANE_COMPOUND);
        basicItem(itemModels, AMItems.ARCANE_ASH);
        basicItem(itemModels, AMItems.PURIFIED_VINTEUM_DUST);
        basicItem(itemModels, AMItems.WITCHWOOD_BOAT);
        basicItem(itemModels, AMItems.WITCHWOOD_CHEST_BOAT);
        basicItem(itemModels, AMItems.DRYAD_SPAWN_EGG);
        basicItem(itemModels, AMItems.MANA_CREEPER_SPAWN_EGG);
        basicItem(itemModels, AMItems.WATER_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.FIRE_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.EARTH_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.AIR_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.ICE_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.LIGHTNING_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.NATURE_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.LIFE_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.ARCANE_GUARDIAN_SPAWN_EGG);
        basicItem(itemModels, AMItems.ENDER_GUARDIAN_SPAWN_EGG);
        CrystalPhylacteryItem item = AMItems.CRYSTAL_PHYLACTERY.get();
        Material baseTexture = TextureMapping.getItemTexture(item);
        Identifier modelLocation = ModelLocationUtils.getModelLocation(item);
        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Material fillTexture = TextureMapping.getItemTexture(item, "/fill_" + i);
            Identifier modelId = itemModels.generateLayeredItem(modelLocation.withSuffix("/fill_" + i), baseTexture, fillTexture);
            ItemModel.Unbaked model = ItemModelUtils.tintedModel(modelId, ItemModelGenerators.BLANK_LAYER, CrystalPhylacteryItemTintSource.INSTANCE);
            entries.add(new RangeSelectItemModel.Entry((i + 1) / 8f, model));
        }
        itemModels.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new CrystalPhylacteryRangeSelectItemModelProperty(), ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM)), entries));
    }

    /// Adds a flat item model.
    ///
    /// @param itemModels The item model generators.
    /// @param item       The item to add the model for.
    private void basicItem(ItemModelGenerators itemModels, DeferredItem<?> item) {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    /// Adds a flat item model and flat variant item models for an item with variants.
    ///
    /// @param item     The item to add the models for.
    /// @param variants The variants to add models for.
    private void itemWithVariants(ItemModelGenerators itemModels, DeferredItem<?> item, ItemModel.Unbaked model, ModelTemplate template, List<? extends ResourceKey<?>> variants) {
        itemModels.itemModelOutput.accept(item.get(), model);
        for (ResourceKey<?> variant : variants) {
            Identifier identifier = variant.identifier().withPrefix(item.getId().getPath() + "/");
            itemModels.itemModelOutput.register(identifier, new ClientItem(ItemModelUtils.plainModel(identifier.withPrefix("item/")), new ClientItem.Properties(true, false, 1)));
            template.create(identifier.withPrefix("item/"), TextureMapping.layer0(new Material(identifier.withPrefix("item/"))), itemModels.modelOutput);
        }
    }

    public void createInlay(BlockModelGenerators blockModels, Block block) {
        TextureMapping texture = TextureMapping.rail(block);
        TextureMapping cornerTexture = TextureMapping.rail(TextureMapping.getBlockTexture(block, "_corner"));
        MultiVariant flat = BlockModelGenerators.plainVariant(ModelTemplates.RAIL_FLAT.create(block, texture, blockModels.modelOutput));
        MultiVariant curved = BlockModelGenerators.plainVariant(ModelTemplates.RAIL_CURVED.create(block, cornerTexture, blockModels.modelOutput));
        blockModels.registerSimpleFlatItemModel(block);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(InlayBlock.SHAPE)
            .select(RailShape.NORTH_SOUTH, flat)
            .select(RailShape.EAST_WEST, flat.with(BlockModelGenerators.Y_ROT_90))
            .select(RailShape.SOUTH_EAST, curved)
            .select(RailShape.SOUTH_WEST, curved.with(BlockModelGenerators.Y_ROT_90))
            .select(RailShape.NORTH_WEST, curved.with(BlockModelGenerators.Y_ROT_180))
            .select(RailShape.NORTH_EAST, curved.with(BlockModelGenerators.Y_ROT_270))));
    }

    private static class AltarCoreModelBuilder extends WrappingCustomBlockStateModelBuilder {
        private AltarCoreModelBuilder(MultiVariant wrapped) {
            super(wrapped);
        }

        @Override
        public CustomBlockStateModelBuilder with(VariantMutator variantMutator) {
            return new AltarCoreModelBuilder(wrapped.with(variantMutator));
        }

        @Override
        public CustomUnbakedBlockStateModel toUnbaked() {
            BlockStateModel.Unbaked unbaked = wrapped.toUnbaked();
            if (unbaked instanceof AltarCoreModel.Unbaked altarCore) return altarCore;
            if (unbaked instanceof SingleVariant.Unbaked singleUnbaked)
                return new AltarCoreModel.Unbaked(singleUnbaked);
            throw new IllegalStateException("Unexpected unbaked variant: " + unbaked);
        }
    }
}
