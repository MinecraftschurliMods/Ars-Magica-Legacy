package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFluids;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.AFFINITY_ESSENCE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.AFFINITY_TOME;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.AIR_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ALTAR_CORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ARCANE_ASH;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ARCANE_COMPOUND;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ARCANE_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.AUM;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.BATTLEMAGE_BOOTS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.BATTLEMAGE_CHESTPLATE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.BATTLEMAGE_HELMET;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.BATTLEMAGE_LEGGINGS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.BLACK_AUREM;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.BLANK_RUNE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.CELESTIAL_PRISM;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.CERUBLOSSOM;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.CHIMERITE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.CHIMERITE_BLOCK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.CHIMERITE_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.COLORED_RUNE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.CRYSTAL_WRENCH;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.DEEPSLATE_CHIMERITE_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.DEEPSLATE_MOONSTONE_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.DEEPSLATE_TOPAZ_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.DEEPSLATE_VINTEUM_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.DESERT_NOVA;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.DRYAD_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.EARTH_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ENDER_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ETHERIUM_PLACEHOLDER;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.FIRE_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.GOLD_INLAY;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.ICE_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.INFINITY_ORB;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.IRON_INLAY;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.LIFE_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.LIGHTNING_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.LIQUID_ESSENCE_BUCKET;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGE_BOOTS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGE_CHESTPLATE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGE_HELMET;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGE_LEGGINGS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGE_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGIC_WALL;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MAGITECH_GOGGLES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MANA_CAKE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MANA_CREEPER_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MANA_MARTINI;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MOONSTONE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MOONSTONE_BLOCK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.MOONSTONE_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.NATURE_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.OBELISK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.OCCULUS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.PURIFIED_VINTEUM_DUST;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.REDSTONE_INLAY;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.RUNE_BAG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SPELL;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SPELL_BOOK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SPELL_PARCHMENT;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SPELL_RECIPE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.STRIPPED_WITCHWOOD;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.STRIPPED_WITCHWOOD_LOG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SUNSTONE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SUNSTONE_BLOCK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.SUNSTONE_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.TARMA_ROOT;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.TOPAZ;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.TOPAZ_BLOCK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.TOPAZ_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.VINTEUM_BLOCK;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.VINTEUM_DUST;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.VINTEUM_ORE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.VINTEUM_TORCH;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WAKEBLOOM;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WATER_GUARDIAN_SPAWN_EGG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_BUTTON;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_DOOR;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_FENCE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_FENCE_GATE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_HANGING_SIGN;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_LEAVES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_LOG;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_PLANKS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_PRESSURE_PLATE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_SAPLING;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_SIGN;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_SLAB;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_STAIRS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WITCHWOOD_TRAPDOOR;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.WIZARDS_CHALK;

@SuppressWarnings({"SameParameterValue", "unused"})
class AMItemModelProvider extends ItemModelProvider {
    AMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture("arcane_compendium", new ResourceLocation("item/generated"), "layer0", modLoc("item/arcane_compendium"));
        skillPointItem(INFINITY_ORB);
        blockItem(OCCULUS);
        itemGenerated(INSCRIPTION_TABLE_UPGRADE_TIER_1);
        itemGenerated(INSCRIPTION_TABLE_UPGRADE_TIER_2);
        itemGenerated(INSCRIPTION_TABLE_UPGRADE_TIER_3);
        blockItem(ALTAR_CORE);
        blockItem(MAGIC_WALL);
        blockItem(OBELISK).transforms()
            .transform(ItemDisplayContext.GUI).rotation(30, -45, 0).translation(0, -2, 0).scale(0.3f).end()
            .transform(ItemDisplayContext.GROUND).translation(0, 3, 0).scale(0.25f).end()
            .transform(ItemDisplayContext.FIXED).scale(0.5f).end()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75, 45, 0).translation(0, 2.5f, 0).scale(0.375f).end()
            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(0, 45, 0).scale(0.4f).end()
            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(0, 225, 0).scale(0.4f).end().end();
        blockItem(CELESTIAL_PRISM).transforms().transform(ItemDisplayContext.GUI).translation(0, -2, 0).scale(0.5f).end().end();
        itemGenerated(BLACK_AUREM, "block/" + BLACK_AUREM.getId().getPath());
        itemHandheld(WIZARDS_CHALK);
        itemGenerated(MAGITECH_GOGGLES);
        itemHandheld(CRYSTAL_WRENCH);
        blockItem(CHIMERITE_ORE);
        blockItem(DEEPSLATE_CHIMERITE_ORE);
        itemGenerated(CHIMERITE);
        blockItem(CHIMERITE_BLOCK);
        blockItem(TOPAZ_ORE);
        blockItem(DEEPSLATE_TOPAZ_ORE);
        itemGenerated(TOPAZ);
        blockItem(TOPAZ_BLOCK);
        blockItem(VINTEUM_ORE);
        blockItem(DEEPSLATE_VINTEUM_ORE);
        itemGenerated(VINTEUM_DUST);
        blockItem(VINTEUM_BLOCK);
        blockItem(MOONSTONE_ORE);
        blockItem(DEEPSLATE_MOONSTONE_ORE);
        itemGenerated(MOONSTONE);
        blockItem(MOONSTONE_BLOCK);
        blockItem(SUNSTONE_ORE);
        itemGenerated(SUNSTONE);
        blockItem(SUNSTONE_BLOCK);
        blockItem(WITCHWOOD_LOG);
        blockItem(WITCHWOOD);
        blockItem(STRIPPED_WITCHWOOD_LOG);
        blockItem(STRIPPED_WITCHWOOD);
        blockItem(WITCHWOOD_LEAVES);
        itemGenerated(WITCHWOOD_SAPLING, "block/witchwood_sapling");
        blockItem(WITCHWOOD_PLANKS);
        blockItem(WITCHWOOD_SLAB);
        blockItem(WITCHWOOD_STAIRS);
        withExistingParent(WITCHWOOD_FENCE, modLoc("block/witchwood_fence_inventory"));
        blockItem(WITCHWOOD_FENCE_GATE);
        itemGenerated(WITCHWOOD_DOOR);
        withExistingParent(WITCHWOOD_TRAPDOOR, modLoc("block/witchwood_trapdoor_bottom"));
        withExistingParent(WITCHWOOD_BUTTON, modLoc("block/witchwood_button_inventory"));
        blockItem(WITCHWOOD_PRESSURE_PLATE);
        itemGenerated(WITCHWOOD_SIGN);
        itemGenerated(WITCHWOOD_HANGING_SIGN);
        itemGenerated(BLANK_RUNE);
        for (DyeColor color : DyeColor.values()) {
            itemGenerated(COLORED_RUNE.registryObject(color));
        }
        itemGenerated(RUNE_BAG);
        itemGenerated(ARCANE_COMPOUND);
        itemGenerated(ARCANE_ASH);
        itemGenerated(PURIFIED_VINTEUM_DUST);
        itemGenerated(AUM, "block/aum");
        itemGenerated(CERUBLOSSOM, "block/cerublossom");
        itemGenerated(DESERT_NOVA, "block/desert_nova");
        itemGenerated(TARMA_ROOT, "block/tarma_root");
        itemGenerated(WAKEBLOOM, "block/wakebloom");
        itemGenerated(VINTEUM_TORCH, "block/vinteum_torch");
        itemGenerated(IRON_INLAY, "block/iron_inlay");
        itemGenerated(REDSTONE_INLAY, "block/redstone_inlay");
        itemGenerated(GOLD_INLAY, "block/gold_inlay");
        affinityItem(AFFINITY_ESSENCE, true);
        affinityItem(AFFINITY_TOME, false);
        itemGenerated(ETHERIUM_PLACEHOLDER);
        itemGenerated(SPELL_PARCHMENT);
        withExistingParent(SPELL_RECIPE, mcLoc("item/written_book"));
        affinityItem(SPELL, false);
        spellBookItem(SPELL_BOOK);
        itemGenerated(MANA_CAKE);
        itemGenerated(MANA_MARTINI);
        itemGenerated(MAGE_HELMET);
        itemGenerated(MAGE_CHESTPLATE);
        itemGenerated(MAGE_LEGGINGS);
        itemGenerated(MAGE_BOOTS);
        itemGenerated(BATTLEMAGE_HELMET);
        itemGenerated(BATTLEMAGE_CHESTPLATE);
        itemGenerated(BATTLEMAGE_LEGGINGS);
        itemGenerated(BATTLEMAGE_BOOTS);
        spawnEggItem(DRYAD_SPAWN_EGG);
        spawnEggItem(MAGE_SPAWN_EGG);
        spawnEggItem(MANA_CREEPER_SPAWN_EGG);
        spawnEggItem(WATER_GUARDIAN_SPAWN_EGG);
        spawnEggItem(FIRE_GUARDIAN_SPAWN_EGG);
        spawnEggItem(EARTH_GUARDIAN_SPAWN_EGG);
        spawnEggItem(AIR_GUARDIAN_SPAWN_EGG);
        spawnEggItem(ICE_GUARDIAN_SPAWN_EGG);
        spawnEggItem(LIGHTNING_GUARDIAN_SPAWN_EGG);
        spawnEggItem(NATURE_GUARDIAN_SPAWN_EGG);
        spawnEggItem(LIFE_GUARDIAN_SPAWN_EGG);
        spawnEggItem(ARCANE_GUARDIAN_SPAWN_EGG);
        spawnEggItem(ENDER_GUARDIAN_SPAWN_EGG);
        withExistingParent(LIQUID_ESSENCE_BUCKET, new ResourceLocation("forge", "item/bucket")).customLoader(DynamicFluidContainerModelBuilder::begin).fluid(AMFluids.LIQUID_ESSENCE.get()).end();
    }

    /**
     * Adds a 2D flat item model. Uses the item id as the item texture.
     *
     * @param item The item to generate the model for.
     */
    private void itemGenerated(RegistryObject<? extends Item> item) {
        itemGenerated(item, "item/" + item.getId().getPath());
    }

    /**
     * Adds a 2D flat item model.
     *
     * @param item The item to generate the model for.
     * @param name The texture id to use.
     */
    private void itemGenerated(RegistryObject<? extends Item> item, String name) {
        singleTexture(item.getId().getPath(), new ResourceLocation("item/generated"), "layer0", modLoc(name));
    }

    /**
     * Adds a handheld item model, for tools and similar. Uses the item id as the item texture.
     *
     * @param item The item to generate the model for.
     */
    private void itemHandheld(RegistryObject<? extends Item> item) {
        itemHandheld(item, "item/" + item.getId().getPath());
    }

    /**
     * Adds a handheld item model, for tools and similar.
     *
     * @param item The item to generate the model for.
     * @param name The texture id to use.
     */
    private void itemHandheld(RegistryObject<? extends Item> item, String name) {
        singleTexture(item.getId().getPath(), new ResourceLocation("item/handheld"), "layer0", modLoc(name));
    }

    /**
     * Adds an item model that uses a parent model.
     *
     * @param item   The item to generate the model for.
     * @param parent The parent model to use.
     */
    private ItemModelBuilder withExistingParent(RegistryObject<? extends Item> item, ResourceLocation parent) {
        return withExistingParent(item.getId().getPath(), parent);
    }

    /**
     * Adds a block item model that uses the corresponding block model as the parent model.
     *
     * @param item The item to generate the model for.
     */
    private ItemModelBuilder blockItem(RegistryObject<? extends BlockItem> item) {
        return withExistingParent(item, new ResourceLocation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(item.get().getBlock())).getNamespace(), "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(item.get().getBlock())).getPath()));
    }

    /**
     * Adds a block item model that uses the corresponding block model as the parent model.
     *
     * @param item The item to generate the model for.
     */
    private void spawnEggItem(RegistryObject<? extends SpawnEggItem> item) {
        withExistingParent(item, new ResourceLocation("item/template_spawn_egg"));
    }

    /**
     * Adds an item model for this item for each affinity.
     *
     * @param item The affinity item to add this for.
     * @param skipNone Whether to skip the model generation for {@link Affinity#NONE} or not.
     */
    private void affinityItem(RegistryObject<? extends Item> item, boolean skipNone) {
        getBuilder(item.getId().toString());
        for (Affinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
            if (affinity.getId().equals(Affinity.NONE) && skipNone) continue;
            ResourceLocation rl = new ResourceLocation(affinity.getId().getNamespace(), item.getId().getPath() + "_" + affinity.getId().getPath());
            singleTexture(rl.toString(), mcLoc("item/generated"), "layer0", new ResourceLocation(rl.getNamespace(), "item/" + rl.getPath()));
        }
    }

    /**
     * Adds an item model for this item for each skill point.
     *
     * @param item The skill point item to add this for.
     */
    private void skillPointItem(RegistryObject<? extends Item> item) {
        getBuilder(item.getId().toString());
        for (SkillPoint skillPoint : ArsMagicaAPI.get().getSkillPointRegistry()) {
            ResourceLocation rl = new ResourceLocation(skillPoint.getId().getNamespace(), item.getId().getPath() + "_" + skillPoint.getId().getPath());
            singleTexture(rl.toString(), mcLoc("item/generated"), "layer0", new ResourceLocation(rl.getNamespace(), "item/" + rl.getPath()));
        }
    }

    /**
     * Adds an item model for this item, and a handheld version to be used by a {@link com.github.minecraftschurlimods.arsmagicalegacy.client.model.item.SpellBookItemModel SpellBookItemModel}.
     *
     * @param item The item to generate the model for.
     */
    private void spellBookItem(RegistryObject<? extends Item> item) {
        getBuilder(item.getId().toString());
        withExistingParent(item.getId().getPath() + "_handheld", mcLoc("item/generated"))
            .texture("layer0", modLoc("item/" + item.getId().getPath() + "_cover"))
            .texture("layer1", modLoc("item/" + item.getId().getPath() + "_decoration"));
    }
}
