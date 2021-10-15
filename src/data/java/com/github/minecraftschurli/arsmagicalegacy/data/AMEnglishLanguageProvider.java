package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.function.Supplier;

class AMEnglishLanguageProvider extends AMLanguageProvider {
    AMEnglishLanguageProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        creativeTabTranslation(AMItems.TAB, ArsMagicaLegacy.getModName());
        blockIdTranslation(AMBlocks.OCCULUS);
        blockIdTranslation(AMBlocks.INSCRIPTION_TABLE);
        blockIdTranslation(AMBlocks.ALTAR_CORE);
        blockIdTranslation(AMBlocks.MAGIC_WALL);
        addBlock(AMBlocks.WIZARDS_CHALK, "Wizard's Chalk");
        blockIdTranslation(AMBlocks.CHIMERITE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        itemIdTranslation(AMItems.CHIMERITE);
        blockIdTranslation(AMBlocks.CHIMERITE_BLOCK);
        blockIdTranslation(AMBlocks.TOPAZ_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        itemIdTranslation(AMItems.TOPAZ);
        blockIdTranslation(AMBlocks.TOPAZ_BLOCK);
        blockIdTranslation(AMBlocks.VINTEUM_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        itemIdTranslation(AMItems.VINTEUM_DUST);
        blockIdTranslation(AMBlocks.VINTEUM_BLOCK);
        blockIdTranslation(AMBlocks.MOONSTONE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        itemIdTranslation(AMItems.MOONSTONE);
        blockIdTranslation(AMBlocks.MOONSTONE_BLOCK);
        blockIdTranslation(AMBlocks.SUNSTONE_ORE);
        itemIdTranslation(AMItems.SUNSTONE);
        blockIdTranslation(AMBlocks.SUNSTONE_BLOCK);
        blockIdTranslation(AMBlocks.WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.WITCHWOOD);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD);
        blockIdTranslation(AMBlocks.WITCHWOOD_LEAVES);
        blockIdTranslation(AMBlocks.WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.WITCHWOOD_PLANKS);
        blockIdTranslation(AMBlocks.WITCHWOOD_SLAB);
        blockIdTranslation(AMBlocks.WITCHWOOD_STAIRS);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_DOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_BUTTON);
        blockIdTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        itemIdTranslation(AMItems.BLANK_RUNE);
        for (DyeColor color : DyeColor.values()) {
            itemIdTranslation(AMItems.COLORED_RUNE.registryObject(color));
        }
        itemIdTranslation(AMItems.RUNE_BAG);
        itemIdTranslation(AMItems.ARCANE_COMPOUND);
        itemIdTranslation(AMItems.ARCANE_ASH);
        itemIdTranslation(AMItems.PURIFIED_VINTEUM_DUST);
        blockIdTranslation(AMBlocks.AUM);
        blockIdTranslation(AMBlocks.CERUBLOSSOM);
        blockIdTranslation(AMBlocks.DESERT_NOVA);
        blockIdTranslation(AMBlocks.TARMA_ROOT);
        blockIdTranslation(AMBlocks.WAKEBLOOM);
        blockIdTranslation(AMBlocks.VINTEUM_TORCH);
        for (RegistryObject<IAffinity> affinity : AMRegistries.AFFINITIES.getEntries()) {
            affinityIdTranslation(affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_ESSENCE, affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_TOME, affinity);
        }
        itemIdTranslation(AMItems.SPELL);
        entityIdTranslation(AMEntities.PROJECTILE);
        entityIdTranslation(AMEntities.WAVE);
        entityIdTranslation(AMEntities.WALL);
        entityIdTranslation(AMEntities.ZONE);
        effectIdTranslation(AMMobEffects.AGILITY);
        effectIdTranslation(AMMobEffects.ASTRAL_DISTORTION);
        effectIdTranslation(AMMobEffects.BURNOUT_REDUCTION);
        effectIdTranslation(AMMobEffects.CLARITY);
        effectIdTranslation(AMMobEffects.ENTANGLE);
        effectIdTranslation(AMMobEffects.FLIGHT);
        effectIdTranslation(AMMobEffects.FROST);
        effectIdTranslation(AMMobEffects.FURY);
        effectIdTranslation(AMMobEffects.GRAVITY_WELL);
        effectIdTranslation(AMMobEffects.ILLUMINATION);
        effectIdTranslation(AMMobEffects.INSTANT_MANA);
        effectIdTranslation(AMMobEffects.MAGIC_SHIELD);
        effectIdTranslation(AMMobEffects.MANA_BOOST);
        effectIdTranslation(AMMobEffects.MANA_REGEN);
        effectIdTranslation(AMMobEffects.REFLECT);
        effectIdTranslation(AMMobEffects.SCRAMBLE_SYNAPSES);
        effectIdTranslation(AMMobEffects.SHIELD);
        effectIdTranslation(AMMobEffects.SHRINK);
        effectIdTranslation(AMMobEffects.SILENCE);
        effectIdTranslation(AMMobEffects.SWIFT_SWIM);
        effectIdTranslation(AMMobEffects.TEMPORAL_ANCHOR);
        effectIdTranslation(AMMobEffects.TRUE_SIGHT);
        effectIdTranslation(AMMobEffects.WATERY_GRAVE);
        skillPointIdTranslation(AMSkillPoints.BLUE);
        skillPointIdTranslation(AMSkillPoints.GREEN);
        skillPointIdTranslation(AMSkillPoints.RED);
        advancementTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "root"), ArsMagicaLegacy.getModName(), "A renewed look into Minecraft with a splash of magic...");
        add(SkillCommand.LANG_KEY_PREFIX + ".empty", "");
        add(SkillCommand.LANG_KEY_PREFIX + ".forgetAll.success", "Forgot all skills");
        add(SkillCommand.LANG_KEY_PREFIX + ".forget.success", "Forgot skill %s");
        add(SkillCommand.LANG_KEY_PREFIX + ".learnAll.success", "Learned all skills");
        add(SkillCommand.LANG_KEY_PREFIX + ".learn.success", "Learned skill %s");
        add(SkillCommand.LANG_KEY_PREFIX + ".skillNotKnown", "Skill %s must be learned first");
        add(SkillCommand.LANG_KEY_PREFIX + ".skillAlreadyKnown", "Skill %s has already been learned");
        add(SpellItem.BURNOUT, "Burnout: %d");
        add(SpellItem.HOLD_SHIFT_FOR_DETAILS, "Hold Shift for details");
        add(SpellItem.INVALID_SPELL, "[Invalid Spell]");
        add(SpellItem.INVALID_SPELL_DESC, "Something is wrong with this spell, please check the log for warnings or errors!");
        add(SpellItem.MANA_COST, "Mana cost: %d");
        add(SpellItem.REAGENTS, "Reagents:");
        add(SpellItem.SPELL_CAST_RESULT + "burned_out", "Burned out!");
        add(SpellItem.SPELL_CAST_RESULT + "cancelled", "Spell cast failed!");
        add(SpellItem.SPELL_CAST_RESULT + "fail", "Spell cast failed!");
        add(SpellItem.SPELL_CAST_RESULT + "missing_reagents", "Missing reagents!");
        add(SpellItem.SPELL_CAST_RESULT + "not_enough_mana", "Not enough mana!");
        add(SpellItem.SPELL_CAST_RESULT + "silenced", "Silence!");
        add(SpellItem.UNKNOWN_ITEM, "Unknown Item");
        add(SpellItem.UNKNOWN_ITEM_DESC, "Mythical forces prevent you from using this item!");
        add(SpellItem.UNNAMED_SPELL, "Unnamed Spell");
        add("message." + ArsMagicaAPI.MOD_ID + ".prevent", "Mythical forces prevent you from using this block!");
    }

    /**
     * Adds a block translation that matches the block id.
     *
     * @param block The block to generate the translation for.
     */
    private void blockIdTranslation(RegistryObject<? extends Block> block) {
        addBlock(block, idToTranslation(block.getId().getPath()));
    }

    /**
     * Adds an item translation that matches the item id.
     *
     * @param item The item to generate the translation for.
     */
    private void itemIdTranslation(RegistryObject<? extends Item> item) {
        addItem(item, idToTranslation(item.getId().getPath()));
    }

    /**
     * Adds an effect translation that matches the effect id.
     *
     * @param effect The effect to generate the translation for.
     */
    private void effectIdTranslation(RegistryObject<? extends MobEffect> effect) {
        addEffect(effect, idToTranslation(effect.getId().getPath()));
    }

    /**
     * Adds an affinity translation that matches the affinity id.
     *
     * @param affinity The affinity to generate the translation for.
     */
    private void affinityIdTranslation(RegistryObject<? extends IAffinity> affinity) {
        addAffinity(affinity, idToTranslation(affinity.getId().getPath()));
    }

    /**
     * Adds an skillPoint translation that matches the skillPoint id.
     *
     * @param skillPoint The skillPoint to generate the translation for.
     */
    private void skillPointIdTranslation(RegistryObject<? extends ISkillPoint> skillPoint) {
        addSkillPoint(skillPoint, idToTranslation(skillPoint.getId().getPath()));
    }

    /**
     * Adds an enchantment translation that matches the enchantment id.
     *
     * @param enchantment The enchantment to generate the translation for.
     */
    private void enchantmentIdTranslation(RegistryObject<? extends Enchantment> enchantment) {
        addEnchantment(enchantment, idToTranslation(enchantment.getId().getPath()));
    }

    /**
     * Adds an entity translation that matches the entity id.
     *
     * @param entity The entity to generate the translation for.
     */
    private void entityIdTranslation(RegistryObject<? extends EntityType<?>> entity) {
        addEntityType(entity, idToTranslation(entity.getId().getPath()));
    }

    /**
     * Adds a creative tab translation that matches the effect id.
     *
     * @param translation The creative tab to generate the translation for.
     */
    private void creativeTabTranslation(CreativeModeTab tab, String translation) {
        add("itemGroup." + tab.getRecipeFolderName(), translation);
    }

    /**
     * Adds an advancement translation.
     *
     * @param advancement The advancement id.
     * @param title       The advancement title.
     * @param description The advancement description.
     */
    private void advancementTranslation(ResourceLocation advancement, String title, String description) {
        add(Util.makeDescriptionId("advancement", advancement) + ".title", title);
        add(Util.makeDescriptionId("advancement", advancement) + ".description", description);
    }

    /**
     * @param id A string of format "word_word_word".
     * @return A string of format "Word Word Word".
     */
    private static String idToTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split("_"))
            result.append(string.substring(0, 1).toUpperCase()).append(string.substring(1)).append(" ");
        return result.substring(0, result.length() - 1);
    }

    /**
     * Adds an affinity translation.
     *
     * @param affinity    The affinity to add the translation for.
     * @param translation The translation for the affinity.
     */
    private void addAffinity(Supplier<? extends IAffinity> affinity, String translation) {
        addAffinity(affinity.get(), translation);
    }

    /**
     * Adds an affinity translation.
     *
     * @param affinity    The affinity to add the translation for.
     * @param translation The translation for the affinity.
     */
    private void addAffinity(IAffinity affinity, String translation) {
        add(affinity.getTranslationKey(), translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItem The affinity item to add the translation for.
     * @param affinity     The affinity to generate the translation from.
     */
    private void affinityItemIdTranslation(RegistryObject<? extends IAffinityItem> affinityItem, RegistryObject<? extends IAffinity> affinity) {
        affinityItemIdTranslation(affinityItem.getId(), affinity.getId());
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItemId The affinity item to add the translation for.
     * @param affinityId     The affinity to generate the translation from.
     */
    private void affinityItemIdTranslation(ResourceLocation affinityItemId, ResourceLocation affinityId) {
        String translation = idToTranslation(affinityId.getPath()) + " " + idToTranslation(affinityItemId.getPath());
        affinityItemTranslation(affinityItemId, affinityId, translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItem The affinity item to add the translation for.
     * @param affinity     The affinity to generate the translation from.
     * @param translation  The custom translation to use.
     */
    private void affinityItemTranslation(RegistryObject<? extends IAffinityItem> affinityItem, RegistryObject<? extends IAffinity> affinity, String translation) {
        affinityItemTranslation(affinityItem.getId(), affinity.getId(), translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItemId The affinity item to add the translation for.
     * @param affinityId     The affinity to generate the translation from.
     * @param translation    The custom translation to use.
     */
    private void affinityItemTranslation(ResourceLocation affinityItemId, ResourceLocation affinityId, String translation) {
        add(Util.makeDescriptionId(Util.makeDescriptionId("item", affinityItemId), affinityId), translation);
    }

    /**
     * Adds a skill point translation.
     *
     * @param skillPoint  The skill point to add the translation for.
     * @param translation The translation for the skill point.
     */
    private void addSkillPoint(Supplier<? extends ISkillPoint> skillPoint, String translation) {
        addSkillPoint(skillPoint.get(), translation);
    }

    /**
     * Adds a skill point translation.
     *
     * @param skillPoint  The skill point to add the translation for.
     * @param translation The translation for the skill point.
     */
    private void addSkillPoint(ISkillPoint skillPoint, String translation) {
        add(skillPoint.getTranslationKey(), translation);
    }
}
