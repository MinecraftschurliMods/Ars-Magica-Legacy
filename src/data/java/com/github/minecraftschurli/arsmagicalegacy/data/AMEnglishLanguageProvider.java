package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

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
        itemIdTranslation(AMItems.MAGITECH_GOGGLES);
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
        itemIdTranslation(AMItems.SPELL_PARCHMENT);
        itemIdTranslation(AMItems.SPELL);
        for (RegistryObject<IAffinity> affinity : AMRegistries.AFFINITIES.getEntries()) {
            affinityIdTranslation(affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_ESSENCE, affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_TOME, affinity);
        }
        for (RegistryObject<ISkillPoint> skillPoint : AMRegistries.SKILL_POINTS.getEntries()) {
            skillPointIdTranslation(skillPoint);
            skillPointItemIdTranslation(AMItems.INFINITY_ORB, skillPoint);
        }
        addAttribute(AMAttributes.BURNOUT_REGEN, "Burnout Regeneration");
        addAttribute(AMAttributes.MANA_REGEN, "Mana Regeneration");
        attributeIdTranslation(AMAttributes.MAGIC_VISION);
        attributeIdTranslation(AMAttributes.MAX_BURNOUT);
        attributeIdTranslation(AMAttributes.MAX_MANA);
        entityIdTranslation(AMEntities.PROJECTILE);
        entityIdTranslation(AMEntities.WAVE);
        entityIdTranslation(AMEntities.WALL);
        entityIdTranslation(AMEntities.ZONE);
        entityIdTranslation(AMEntities.WATER_GUARDIAN);
        entityIdTranslation(AMEntities.FIRE_GUARDIAN);
        entityIdTranslation(AMEntities.EARTH_GUARDIAN);
        entityIdTranslation(AMEntities.AIR_GUARDIAN);
        entityIdTranslation(AMEntities.WINTER_GUARDIAN);
        entityIdTranslation(AMEntities.LIGHTNING_GUARDIAN);
        entityIdTranslation(AMEntities.NATURE_GUARDIAN);
        entityIdTranslation(AMEntities.LIFE_GUARDIAN);
        entityIdTranslation(AMEntities.ARCANE_GUARDIAN);
        entityIdTranslation(AMEntities.ENDER_GUARDIAN);
        entityIdTranslation(AMEntities.DRYAD);
        entityIdTranslation(AMEntities.MAGE);
        entityIdTranslation(AMEntities.MANA_CREEPER);
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
        advancementTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "root"), ArsMagicaLegacy.getModName(), "A renewed look into Minecraft with a splash of magic...");
        add(TranslationConstants.BURNOUT_TOOLTIP, "Burnout: %d");
        add(TranslationConstants.HOLD_SHIFT_FOR_DETAILS, "Hold Shift for details");
        add(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME_VALUE, "Default Spell");
        add(TranslationConstants.INSCRIPTION_TABLE_NAME_LABEL, "Name");
        add(TranslationConstants.INSCRIPTION_TABLE_SEARCH_BAR_LABEL, "Search");
        add(TranslationConstants.INVALID_SPELL, "[Invalid Spell]");
        add(TranslationConstants.INVALID_SPELL_DESC, "Something is wrong with this spell, please check the log for warnings or errors!");
        add(TranslationConstants.MAGIC_UNKNOWN_MESSAGE, "Mythical forces prevent you from using this block!");
        add(TranslationConstants.MANA_COST_TOOLTIP, "Mana cost: %d");
        add(TranslationConstants.REAGENTS_TOOLTIP, "Reagents:");
        add(TranslationConstants.SKILL_COMMAND_ALREADY_KNOWN, "Skill %s has already been learned");
        add(TranslationConstants.SKILL_COMMAND_EMPTY, "");
        add(TranslationConstants.SKILL_COMMAND_FORGET_ALL_SUCCESS, "Forgot all skills");
        add(TranslationConstants.SKILL_COMMAND_FORGET_SUCCESS, "Forgot skill %s");
        add(TranslationConstants.SKILL_COMMAND_LEARN_ALL_SUCCESS, "Learned all skills");
        add(TranslationConstants.SKILL_COMMAND_LEARN_SUCCESS, "Learned skill %s");
        add(TranslationConstants.SKILL_COMMAND_NOT_KNOWN_SKILL, "Skill %s must be learned first");
        add(TranslationConstants.SPELL_CAST_RESULT + "burned_out", "Burned out!");
        add(TranslationConstants.SPELL_CAST_RESULT + "cancelled", "Spell cast failed!");
        add(TranslationConstants.SPELL_CAST_RESULT + "fail", "Spell cast failed!");
        add(TranslationConstants.SPELL_CAST_RESULT + "missing_reagents", "Missing reagents!");
        add(TranslationConstants.SPELL_CAST_RESULT + "not_enough_mana", "Not enough mana!");
        add(TranslationConstants.SPELL_CAST_RESULT + "silenced", "Silence!");
        add(TranslationConstants.UNKNOWN_ITEM, "Unknown Item");
        add(TranslationConstants.UNKNOWN_ITEM_DESC, "Mythical forces prevent you from using this item!");
        add(TranslationConstants.UNNAMED_SPELL, "Unnamed Spell");
        skillTranslation(AMSpellParts.ABSORPTION.getId(), "Absorption", "Like a slightly flimsier shield.");
        skillTranslation(AMSpellParts.AOE.getId(), "AoE", "Zone control.");
        skillTranslation(AMSpellParts.ASTRAL_DISTORTION.getId(), "Astral Distortion", "Going nowhere...");
        skillTranslation(AMSpellParts.ATTRACT.getId(), "Attract", "Come closer... or not, just stay where you are.");
        skillTranslation(AMSpellParts.BANISH_RAIN.getId(), "Banish Rain", "Come back latter. Or don't. It would be kind.");
        skillTranslation(AMSpellParts.BEAM.getId(), "Beam", "Beam me up, Scotty!");
        skillTranslation(AMSpellParts.BLIZZARD.getId(), "Blizzard", "Snow. Lots of snow");
        skillTranslation(AMSpellParts.BOUNCE.getId(), "Bounce", "Hey! Come back!");
        skillTranslation(AMSpellParts.CHAIN.getId(), "Chain", "Looks like you brought friends. Well I don't mind, you're all gonna die.");
        skillTranslation(AMSpellParts.CHANNEL.getId(), "Channel", "You might want to concentrate.");
        skillTranslation(AMSpellParts.CHARM.getId(), "Charm", "Friends! Lots of friends! And they blew up...");
        skillTranslation(AMSpellParts.CREATE_WATER.getId(), "Create Water", "Yop, totally intended turning my snow into water...");
        skillTranslation(AMSpellParts.DAMAGE.getId(), "Damage", "Now it hurts...");
        skillTranslation(AMSpellParts.DAYLIGHT.getId(), "Daylight", "Does that mean I can control time?");
        skillTranslation(AMSpellParts.DIG.getId(), "Dig", "Diggy Diggy Hole");
        skillTranslation(AMSpellParts.DISARM.getId(), "Disarm", "Woops, you dropped something?");
        skillTranslation(AMSpellParts.DISMEMBERING.getId(), "Dismembering", "Wasn't me. I swear he had no head when I came in.");
        skillTranslation(AMSpellParts.DISPEL.getId(), "Dispel", "Cleaning up the mess.");
        skillTranslation(AMSpellParts.DIVINE_INTERVENTION.getId(), "Divine Intervention", "Help from across the cosmos.");
        skillTranslation(AMSpellParts.DROUGHT.getId(), "Drought", "Sand. Who doesn't know that?");
        skillTranslation(AMSpellParts.DURATION.getId(), "Duration", "Time manipulation tricks.");
        skillTranslation(AMSpellParts.ENDER_INTERVENTION.getId(), "Ender Intervention", "Well, I didn't know what hell looked like...");
        skillTranslation(AMSpellParts.ENTANGLE.getId(), "Entangle", "Stop right there.");
        skillTranslation(AMSpellParts.FIRE_DAMAGE.getId(), "Fire Damage", "You shall burn!");
        skillTranslation(AMSpellParts.FIRE_RAIN.getId(), "Fire Rain", "Well, at least I'm not going to catch a cold...");
        skillTranslation(AMSpellParts.FLIGHT.getId(), "Flight", "I'd rather use a plane.");
        skillTranslation(AMSpellParts.FLING.getId(), "Fling", "Ready for an air fight?");
        skillTranslation(AMSpellParts.FORGE.getId(), "Forge", "Portable furnace!");
        skillTranslation(AMSpellParts.FROST_DAMAGE.getId(), "Frost Damage", "Cold...");
        skillTranslation(AMSpellParts.FURY.getId(), "Fury", "Berserker rage!");
        skillTranslation(AMSpellParts.GRAVITY.getId(), "Gravity", "Falling... okay...");
        skillTranslation(AMSpellParts.GRAVITY_WELL.getId(), "Gravity Well", "NOT like a chicken. The opposite.");
        skillTranslation(AMSpellParts.GROW.getId(), "Grow", "I won't sit all day.");
        skillTranslation(AMSpellParts.HARVEST.getId(), "Harvest", "Let's just say you're all grown.");
        skillTranslation(AMSpellParts.HASTE.getId(), "Haste", "I'm out of here...");
        skillTranslation(AMSpellParts.HEAL.getId(), "Heal", "Instant healing.");
        skillTranslation(AMSpellParts.HEALING.getId(), "Healing", "Efficiency over number.");
        skillTranslation(AMSpellParts.IGNITION.getId(), "Ignition", "Burn harder!");
        skillTranslation(AMSpellParts.INVISIBILITY.getId(), "Invisibility", "Wanna play Hide & Seek?");
        skillTranslation(AMSpellParts.JUMP_BOOST.getId(), "Jump Boost", "Not a frog? Who cares?");
        skillTranslation(AMSpellParts.KNOCKBACK.getId(), "Knockback", "Punch from a distance!");
        skillTranslation(AMSpellParts.LEVITATION.getId(), "Levitation", "Use the force.");
        skillTranslation(AMSpellParts.LIFE_DRAIN.getId(), "Life Drain", "Ahem... I'm taking all of it. Including you.");
        skillTranslation(AMSpellParts.LIFE_TAP.getId(), "Life Tap", "I'm burrowing this.");
        skillTranslation(AMSpellParts.LIGHT.getId(), "Light", "The end of a tunnel.");
        skillTranslation(AMSpellParts.LIGHTNING_DAMAGE.getId(), "Lightning Damage", "Zap!");
        skillTranslation(AMSpellParts.LUNAR.getId(), "Lunar", "I'm gonna be a werewolf!");
        skillTranslation(AMSpellParts.MAGIC_DAMAGE.getId(), "Magic Damage", "Hit from the void!");
        skillTranslation(AMSpellParts.MANA_BLAST.getId(), "Mana Blast", "I LOVE mana, especially when it blows up in someone's face.");
        skillTranslation(AMSpellParts.MANA_DRAIN.getId(), "Mana Drain", "So much pools at my disposal!");
        skillTranslation(AMSpellParts.MANA_SHIELD.getId(), "Mana Shield", "Well, now I know what boredom looks like.");
        skillTranslation(AMSpellParts.MINING_POWER.getId(), "Mining Power", "Who needs diamond?");
        skillTranslation(AMSpellParts.MOONRISE.getId(), "Moonrise", "Full Moon.");
        skillTranslation(AMSpellParts.NIGHT_VISION.getId(), "Night Vision", "Oh? There was a tunnel?");
        skillTranslation(AMSpellParts.PHYSICAL_DAMAGE.getId(), "Physical Damage", "Ranged sword... why not?");
        skillTranslation(AMSpellParts.PIERCING.getId(), "Piercing", "Armor here I come!");
        skillTranslation(AMSpellParts.PLACE_BLOCK.getId(), "Place Block", "Don't mind me, I'm just sending an Anvil.");
        skillTranslation(AMSpellParts.PLANT.getId(), "Plant", "Why bother using hand when magic can do the same?");
        skillTranslation(AMSpellParts.PLOW.getId(), "Plow", "Hoes are useless. Everyone knows that.");
        skillTranslation(AMSpellParts.PROJECTILE.getId(), "Projectile", "Snowball!");
        skillTranslation(AMSpellParts.PROSPERITY.getId(), "Prosperity", "Bling!");
        skillTranslation(AMSpellParts.RANDOM_TELEPORT.getId(), "Random Teleport", "I wanna go... there! Woops, wrong spot.");
        skillTranslation(AMSpellParts.RANGE.getId(), "Range", "Think you're far enough? No, you're not.");
        skillTranslation(AMSpellParts.RECALL.getId(), "Recall", "Can someone remind me who marked the trash?");
        skillTranslation(AMSpellParts.REGENERATION.getId(), "Regeneration", "A little bit of health");
        skillTranslation(AMSpellParts.REPEL.getId(), "Repel", "Go away from me!");
        skillTranslation(AMSpellParts.RUNE.getId(), "Rune", "Placeable magic.");
        skillTranslation(AMSpellParts.RUNE_PROCS.getId(), "Rune Procs", "I want more!");
        skillTranslation(AMSpellParts.SELF.getId(), "Self", "It's all about me.");
        skillTranslation(AMSpellParts.SHIELD.getId(), "Shield", "Don't worry about the weight, it's magic.");
        skillTranslation(AMSpellParts.SHRINK.getId(), "Shrink", "Looks like I'm smaller now...");
        skillTranslation(AMSpellParts.SILENCE.getId(), "Silence", "No talking! (or casting in this case...)");
        skillTranslation(AMSpellParts.SILK_TOUCH.getId(), "Silk Touch", "Feels soft...");
        skillTranslation(AMSpellParts.SOLAR.getId(), "Solar", "Sun power!");
        skillTranslation(AMSpellParts.STORM.getId(), "Storm", "It's raining...");
        skillTranslation(AMSpellParts.SUMMON.getId(), "Summon", "Rise creation! Oh you look like the others...");
        skillTranslation(AMSpellParts.SWIFT_SWIM.getId(), "Swift Swim", "No more swimming for hours.");
        skillTranslation(AMSpellParts.TELEKINESIS.getId(), "Telekinesis", "Up, down, up, down, I think you get and idea.");
        skillTranslation(AMSpellParts.TOUCH.getId(), "Touch", "Someone in there?");
        skillTranslation(AMSpellParts.TRANSPLACE.getId(), "Transplace", "From point A to point B.");
        skillTranslation(AMSpellParts.TRUE_SIGHT.getId(), "True Sight", "Reveal what is hidden.");
        skillTranslation(AMSpellParts.WALL.getId(), "Wall", "You shall not pass.");
        skillTranslation(AMSpellParts.WATER_BREATHING.getId(), "Water Breathing", "Creating air directly inside my lungs? Cool!");
        skillTranslation(AMSpellParts.WATERY_GRAVE.getId(), "Watery Grave", "Bottom of the ocean.");
        skillTranslation(AMSpellParts.WAVE.getId(), "Wave", "You might not want to surf on this one...");
        skillTranslation(AMSpellParts.WIZARDS_AUTUMN.getId(), "Wizard's Autumn", "Leaves fall in autumn. But it's kind of a slow process...");
        skillTranslation(AMSpellParts.ZONE.getId(), "Zone", "No one can beat me in my sanctuary!");



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
     * Adds an attribute translation that matches the attribute id.
     *
     * @param attribute The attribute to generate the translation for.
     */
    private void attributeIdTranslation(RegistryObject<? extends Attribute> attribute) {
        add(attribute.get().getDescriptionId(), idToTranslation(attribute.getId().getPath()));
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
     * Adds a skill translation.
     *
     * @param skill       The skill id.
     * @param name        The skill name.
     * @param description The skill description.
     */
    private void skillTranslation(ResourceLocation skill, String name, String description) {
        add(Util.makeDescriptionId("skill", skill) + ".name", name);
        add(Util.makeDescriptionId("skill", skill) + ".description", description);
    }

    /**
     * Adds an attribute translation.
     *
     * @param attribute   The attribute to add the translation for.
     * @param translation The translation for the attribute.
     */
    private void addAttribute(Supplier<? extends Attribute> attribute, String translation) {
        add(attribute.get().getDescriptionId(), translation);
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

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItem The skill point item to add the translation for.
     * @param skillPoint     The skill point to generate the translation from.
     */
    private void skillPointItemIdTranslation(RegistryObject<? extends ISkillPointItem> skillPointItem, RegistryObject<? extends ISkillPoint> skillPoint) {
        skillPointItemIdTranslation(skillPointItem.getId(), skillPoint.getId());
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItemId The skill point item to add the translation for.
     * @param skillPointId     The skill point to generate the translation from.
     */
    private void skillPointItemIdTranslation(ResourceLocation skillPointItemId, ResourceLocation skillPointId) {
        String translation = idToTranslation(skillPointId.getPath()) + " " + idToTranslation(skillPointItemId.getPath());
        skillPointItemTranslation(skillPointItemId, skillPointId, translation);
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItem The skill point item to add the translation for.
     * @param skillPoint     The skill point to generate the translation from.
     * @param translation    The custom translation to use.
     */
    private void skillPointItemTranslation(RegistryObject<? extends ISkillPointItem> skillPointItem, RegistryObject<? extends ISkillPoint> skillPoint, String translation) {
        skillPointItemTranslation(skillPointItem.getId(), skillPoint.getId(), translation);
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItemId The skill point item to add the translation for.
     * @param skillPointId     The skill point to generate the translation from.
     * @param translation      The custom translation to use.
     */
    private void skillPointItemTranslation(ResourceLocation skillPointItemId, ResourceLocation skillPointId, String translation) {
        add(Util.makeDescriptionId(Util.makeDescriptionId("item", skillPointItemId), skillPointId), translation);
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
}
