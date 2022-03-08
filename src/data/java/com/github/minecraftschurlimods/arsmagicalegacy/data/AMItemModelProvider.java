package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems.*;

class AMItemModelProvider extends ItemModelProvider {
    AMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture("arcane_compendium", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/arcane_compendium"));
        skillPointItem(INFINITY_ORB);
        blockItem(OCCULUS);
        blockItem(ALTAR_CORE);
        blockItem(MAGIC_WALL);
        blockItem(OBELISK).transforms()
                .transform(ModelBuilder.Perspective.GUI).rotation(30, -45, 0).translation(0, -2, 0).scale(0.3f).end()
                .transform(ModelBuilder.Perspective.GROUND).translation(0, 3, 0).scale(0.25f).end()
                .transform(ModelBuilder.Perspective.FIXED).scale(0.5f).end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT).rotation(75, 45, 0).translation(0, 2.5f, 0).scale(0.375f).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT).rotation(0, 45, 0).scale(0.4f).end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_LEFT).rotation(0, 225, 0).scale(0.4f).end().end();
        blockItem(CELESTIAL_PRISM).transforms().transform(ModelBuilder.Perspective.GUI).translation(0, -2, 0).scale(0.5f).end().end();
        itemGenerated(BLACK_AUREM, "block/" + BLACK_AUREM.getId().getPath());
        itemGenerated(WIZARDS_CHALK);
        itemGenerated(MAGITECH_GOGGLES);
        itemGenerated(CRYSTAL_WRENCH);
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
        withExistingParent(WITCHWOOD_FENCE, "witchwood_fence_inventory");
        blockItem(WITCHWOOD_FENCE_GATE);
        itemGenerated(WITCHWOOD_DOOR);
        withExistingParent(WITCHWOOD_TRAPDOOR, "witchwood_trapdoor_bottom");
        withExistingParent(WITCHWOOD_BUTTON, "witchwood_button_inventory");
        blockItem(WITCHWOOD_PRESSURE_PLATE);
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
        affinityItem(AFFINITY_ESSENCE);
        affinityItem(AFFINITY_TOME);
        itemGenerated(SPELL_PARCHMENT);
        spellItem(SPELL);
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
        singleTexture(item.getId().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
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
        singleTexture(item.getId().getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    /**
     * Adds an item model that uses a parent model.
     *
     * @param item   The item to generate the model for.
     * @param parent The parent model to use.
     */
    private ItemModelBuilder withExistingParent(RegistryObject<? extends Item> item, String parent) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/" + parent));
    }

    /**
     * Adds a block item model that uses the corresponding block model as the parent model.
     *
     * @param item The item to generate the model for.
     */
    private ItemModelBuilder blockItem(RegistryObject<? extends BlockItem> item) {
        return withExistingParent(item, item.get().getBlock().getRegistryName().getPath());
    }

    /**
     * Adds an item model for this item for each affinity, excluding {@link IAffinity#NONE}.
     *
     * @param item The affinity item to add this for.
     * @param <T>  An {@link Item} that must also implement {@link IAffinityItem}.
     */
    private <T extends Item & IAffinityItem> void affinityItem(RegistryObject<T> item) {
        getBuilder(item.getId().toString());
        for (IAffinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
            if (affinity.getId().equals(IAffinity.NONE)) continue;
            ResourceLocation rl = new ResourceLocation(affinity.getId().getNamespace(), item.getId().getPath() + "_" + affinity.getId().getPath());
            singleTexture(rl.toString(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(rl.getNamespace(), "item/" + rl.getPath()));
        }
    }

    /**
     * Adds an item model for this item for each skill point.
     *
     * @param item The skill point item to add this for.
     * @param <T>  An {@link Item} that must also implement {@link ISkillPointItem}.
     */
    private <T extends Item & ISkillPointItem> void skillPointItem(RegistryObject<T> item) {
        getBuilder(item.getId().toString());
        for (ISkillPoint skillPoint : ArsMagicaAPI.get().getSkillPointRegistry()) {
            ResourceLocation rl = new ResourceLocation(skillPoint.getId().getNamespace(), item.getId().getPath() + "_" + skillPoint.getId().getPath());
            singleTexture(rl.toString(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(rl.getNamespace(), "item/" + rl.getPath()));
        }
    }

    /**
     * Adds an item model for this item for each affinity.
     *
     * @param item The spell item to add this for.
     * @param <T>  An {@link Item} that must also implement {@link ISpellItem}.
     */
    private <T extends Item & ISpellItem> void spellItem(RegistryObject<T> item) {
        for (IAffinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
            ResourceLocation rl = new ResourceLocation(affinity.getId().getNamespace(), item.getId().getPath() + "_" + affinity.getId().getPath());
            singleTexture(rl.toString(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_handheld"), "layer0", new ResourceLocation(rl.getNamespace(), "item/" + rl.getPath()));
        }
    }
}
