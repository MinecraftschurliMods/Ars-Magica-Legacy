package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SpellPartDataProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

import java.util.EnumSet;

class AMSpellPartDataProvider extends SpellPartDataProvider {
    AMSpellPartDataProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void generate() {
        var helper = ArsMagicaAPI.get().getAffinityHelper();
        builder(AMSpellParts.AOE, 2f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TNT), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 1))
                .build();
        builder(AMSpellParts.BEAM, 1f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT), 2500))
                .build();
        builder(AMSpellParts.CHAIN, 1f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STRING), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LEAD), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TRIPWIRE_HOOK), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT), 2500))
                .build();
        builder(AMSpellParts.CHANNEL, 0.5f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .build();
        builder(AMSpellParts.CONTINGENCY_DAMAGE, 10f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIGHTNING.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 5000))
                .build();
        builder(AMSpellParts.CONTINGENCY_DEATH, 10f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.DARK), 5000))
                .build();
        builder(AMSpellParts.CONTINGENCY_FALL, 10f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 5000))
                .build();
        builder(AMSpellParts.CONTINGENCY_FIRE, 10f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 5000))
                .build();
        builder(AMSpellParts.CONTINGENCY_HEALTH, 10f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIFE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT), 5000))
                .build();
        builder(AMSpellParts.PROJECTILE, 1f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        builder(AMSpellParts.RUNE, 2f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .build();
        builder(AMSpellParts.SELF, 0.5f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .build();
        builder(AMSpellParts.TOUCH, 1f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1))
                .build();
        builder(AMSpellParts.WALL, 2.5f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FENCES_WOODEN), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.WALLS), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        builder(AMSpellParts.WAVE, 2.5f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        builder(AMSpellParts.ZONE, 2.5f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        builder(AMSpellParts.DROWNING_DAMAGE, 25f)
                .addAffinity(AMAffinities.WATER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1))
                .build();
        builder(AMSpellParts.FIRE_DAMAGE, 25f)
                .addAffinity(AMAffinities.FIRE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.FLINT_AND_STEEL)), 1))
                .build();
        builder(AMSpellParts.FROST_DAMAGE, 25f)
                .addAffinity(AMAffinities.ICE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.CYAN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ICE), 1))
                .build();
        builder(AMSpellParts.LIGHTNING_DAMAGE, 25f)
                .addAffinity(AMAffinities.LIGHTNING, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1))
                .build();
        builder(AMSpellParts.MAGIC_DAMAGE, 25f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BOOK), 1))
                .build();
        builder(AMSpellParts.PHYSICAL_DAMAGE, 25f)
                .addAffinity(AMAffinities.EARTH, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1))
                .build();
        builder(AMSpellParts.ABSORPTION, 50f)
                .addAffinity(AMAffinities.LIFE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GOLDEN_APPLE), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.SHIELD)), 1))
                .build();
        builder(AMSpellParts.BLINDNESS, 40f)
                .addAffinity(AMAffinities.ENDER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.NIGHT_VISION)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WEAKNESS)), 1))
                .build();
        builder(AMSpellParts.HASTE, 30f)
                .addAffinity(AMAffinities.LIGHTNING, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .build();
        builder(AMSpellParts.HEALTH_BOOST, 50f)
                .addAffinity(AMAffinities.LIFE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIFE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), 1))
                .build();
        builder(AMSpellParts.INVISIBILITY, 40f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY)), 1))
                .build();
        builder(AMSpellParts.JUMP_BOOST, 30f)
                .addAffinity(AMAffinities.AIR, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .build();
        builder(AMSpellParts.LEVITATION, 40f)
                .addAffinity(AMAffinities.AIR, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1))
                .build();
        builder(AMSpellParts.NIGHT_VISION, 30f)
                .addAffinity(AMAffinities.ENDER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GOLDEN_CARROT), 1))
                .build();
        builder(AMSpellParts.NAUSEA, 200f)
                .addAffinity(AMAffinities.LIFE, 0.0001f)
                .build();
        builder(AMSpellParts.REGENERATION, 30f)
                .addAffinity(AMAffinities.LIFE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1))
                .build();
        builder(AMSpellParts.SLOWNESS, 30f)
                .addAffinity(AMAffinities.ICE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        builder(AMSpellParts.SLOW_FALLING, 30f)
                .addAffinity(AMAffinities.AIR, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PHANTOM_MEMBRANE), 1))
                .build();
        builder(AMSpellParts.WATER_BREATHING, 40f)
                .addAffinity(AMAffinities.WATER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PUFFERFISH), 1))
                .build();
        builder(AMSpellParts.AGILITY, 40f)
                .addAffinity(AMAffinities.AIR, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .build();
        builder(AMSpellParts.ASTRAL_DISTORTION, 40f)
                .addAffinity(AMAffinities.ENDER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        builder(AMSpellParts.ENTANGLE, 40f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.VINE), 1))
                .build();
        builder(AMSpellParts.FLIGHT, 50f)
                .addAffinity(AMAffinities.AIR, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1))
                .build();
        builder(AMSpellParts.FROST, 40f)
                .addAffinity(AMAffinities.ICE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POWDER_SNOW_BUCKET), 1))
                .build();
        builder(AMSpellParts.FURY, 50f)
                .addAffinity(AMAffinities.LIGHTNING, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_BLAZE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TROPICAL_FISH), 1))
                .build();
        builder(AMSpellParts.GRAVITY_WELL, 40f)
                .addAffinity(AMAffinities.EARTH, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GRAY)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STONE), 1))
                .build();
        builder(AMSpellParts.REFLECT, 50f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_GRAY)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_BLAZE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GLASS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.WITCHWOOD_LOGS), 1))
                .build();
        builder(AMSpellParts.SCRAMBLE_SYNAPSES, 3000f)
                .addAffinity(AMAffinities.LIGHTNING, 0.0001f)
                .build();
        builder(AMSpellParts.SHIELD, 50f)
                .addAffinity(AMAffinities.EARTH, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BROWN)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.SHIELD)), 1))
                .build();
        builder(AMSpellParts.SHRINK, 30f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STONE_BUTTON), 1))
                .build();
        builder(AMSpellParts.SILENCE, 50f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BROWN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.WOOL), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.JUKEBOX), 1))
                .build();
        builder(AMSpellParts.SWIFT_SWIM, 40f)
                .addAffinity(AMAffinities.WATER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.FISHES), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.FISHING_ROD)), 1))
                .build();
        builder(AMSpellParts.TEMPORAL_ANCHOR, 50f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        builder(AMSpellParts.TRUE_SIGHT, 30f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GLASS), 1))
                .build();
        builder(AMSpellParts.WATERY_GRAVE, 40f)
                .addAffinity(AMAffinities.WATER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STONE), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.LEATHER_BOOTS)), 1))
                .build();
        builder(AMSpellParts.ATTRACT, 5f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1))
                .build();
        builder(AMSpellParts.BANISH_RAIN, 200f)
                .addAffinity(AMAffinities.WATER, 0.005f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1))
                .build();
        builder(AMSpellParts.BLINK, 80f)
                .addAffinity(AMAffinities.ENDER, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        builder(AMSpellParts.BLIZZARD, 1000f)
                .addAffinity(AMAffinities.ICE, 0.01f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ICE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PACKED_ICE), 1))
                .build();
        builder(AMSpellParts.CHARM, 60f)
                .addAffinity(AMAffinities.LIFE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WHEAT), 1))
                .build();
        builder(AMSpellParts.CREATE_WATER, 5f)
                .addAffinity(AMAffinities.WATER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1))
                .build();
        builder(AMSpellParts.DAYLIGHT, 2000f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        builder(AMSpellParts.DIG, 5f)
                .addAffinity(AMAffinities.EARTH, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BROWN)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.IRON_AXE)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.IRON_PICKAXE)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.IRON_SHOVEL)), 1))
                .build();
        builder(AMSpellParts.DISARM, 60f)
                .addAffinity(AMAffinities.ARCANE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.IRON_SWORD)), 1))
                .build();
        builder(AMSpellParts.DISPEL, 60f)
                .addAffinity(AMAffinities.ARCANE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.MILK_BUCKET), 1))
                .build();
        builder(AMSpellParts.DIVINE_INTERVENTION, 200f)
                .addAffinity(AMAffinities.ENDER, 0.005f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.BEDS), 1))
                .build();
        builder(AMSpellParts.DROUGHT, 5f)
                .addAffinity(AMAffinities.FIRE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.DEAD_BUSH), 1))
                .build();
        builder(AMSpellParts.ENDER_INTERVENTION, 200f)
                .addAffinity(AMAffinities.ENDER, 0.005f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1))
                .build();
        builder(AMSpellParts.EXPLOSION, 100f)
                .addAffinity(AMAffinities.FIRE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GRAY)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.FIRE_CHARGE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TNT), 1))
                .build();
        builder(AMSpellParts.FALLING_STAR, 1000f)
                .addAffinity(AMAffinities.ARCANE, 0.01f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_ARCANE_ASH), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.END_STONES), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1))
                .build();
        builder(AMSpellParts.FIRE_RAIN, 1000f)
                .addAffinity(AMAffinities.FIRE, 0.01f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_ARCANE_ASH), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHERRACK), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1))
                .build();
        builder(AMSpellParts.FLING, 80f)
                .addAffinity(AMAffinities.AIR, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PISTON), 1))
                .build();
        builder(AMSpellParts.FORGE, 80f)
                .addAffinity(AMAffinities.FIRE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.FURNACE), 1))
                .build();
        builder(AMSpellParts.GROW, 5f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BONE_MEAL), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.WITCHWOOD_LOGS), 1))
                .build();
        builder(AMSpellParts.HARVEST, 5f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.SHEARS)), 1))
                .build();
        builder(AMSpellParts.HEAL, 60f)
                .addAffinity(AMAffinities.LIFE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .build();
        builder(AMSpellParts.IGNITION, 80f)
                .addAffinity(AMAffinities.FIRE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.FLINT_AND_STEEL)), 1))
                .build();
        builder(AMSpellParts.KNOCKBACK, 80f)
                .addAffinity(AMAffinities.WATER, 0.002f)
                .addAffinity(AMAffinities.EARTH, 0.002f)
                .addAffinity(AMAffinities.AIR, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PISTON), 1))
                .build();
        builder(AMSpellParts.LIFE_DRAIN, 5f)
                .addAffinity(AMAffinities.LIFE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .build();
        builder(AMSpellParts.LIFE_TAP, 5f)
                .addAffinity(AMAffinities.LIFE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .build();
        builder(AMSpellParts.LIGHT, 60f)
                .addAffinity(AMAffinities.LIGHTNING, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.VINTEUM_TORCH.get()), 1))
                .build();
        builder(AMSpellParts.MANA_BLAST, 0f)
                .addAffinity(AMAffinities.ENDER, 0.001f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .build();
        builder(AMSpellParts.MANA_DRAIN, 5f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.CYAN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .build();
        builder(AMSpellParts.MELT_ARMOR, 200f)
                .addAffinity(AMAffinities.FIRE, 0.0001f)
                .build();
        builder(AMSpellParts.MOONRISE, 2000f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        builder(AMSpellParts.PLACE_BLOCK, 5f)
                .addAffinity(AMAffinities.EARTH, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_GRAY)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.CHESTS_WOODEN), 1))
                .build();
        builder(AMSpellParts.PLANT, 5f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WHEAT_SEEDS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1))
                .build();
        builder(AMSpellParts.PLOW, 5f)
                .addAffinity(AMAffinities.EARTH, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(new ItemStack(Items.IRON_HOE)), 1))
                .build();
        builder(AMSpellParts.RANDOM_TELEPORT, 80f)
                .addAffinity(AMAffinities.ENDER, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        builder(AMSpellParts.RECALL, 80f)
                .addAffinity(AMAffinities.ARCANE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.MAP), 1))
                .build();
        builder(AMSpellParts.REPEL, 5f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_PURIFIED_VINTEUM), 1))
                .build();
        builder(AMSpellParts.RIFT, 80f)
                .addAffinity(AMAffinities.ENDER, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENDER_CHEST), 1))
                .build();
        builder(AMSpellParts.STORM, 200f)
                .addAffinity(AMAffinities.LIGHTNING, 0.005f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1))
                .build();
        builder(AMSpellParts.SUMMON, 80f)
                .addAffinity(AMAffinities.LIFE, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GRAY)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_PURIFIED_VINTEUM), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .addIngredient(new EtheriumSpellIngredient(EnumSet.of(EtheriumType.DARK), 2500))
                .build();
        builder(AMSpellParts.TELEKINESIS, 5f)
                .addAffinity(AMAffinities.ARCANE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STICKY_PISTON), 1))
                .build();
        builder(AMSpellParts.TRANSPLACE, 80f)
                .addAffinity(AMAffinities.ENDER, 0.002f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1))
                .build();
        builder(AMSpellParts.WIZARDS_AUTUMN, 5f)
                .addAffinity(AMAffinities.NATURE, 0.001f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_WOODEN), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1))
                .build();
        builder(AMSpellParts.BOUNCE, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        builder(AMSpellParts.DAMAGE, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING)), 1))
                .build();
        builder(AMSpellParts.DISMEMBERING, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WITHER_SKELETON_SKULL), 1))
                .build();
        builder(AMSpellParts.DURATION, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        builder(AMSpellParts.EFFECT_POWER, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.CROPS_NETHER_WART), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GUNPOWDER), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GLASS_BOTTLE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
                .build();
        builder(AMSpellParts.GRAVITY, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COMPASS), 1))
                .build();
        builder(AMSpellParts.HEALING, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIFE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.EGG), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)), 1))
                .build();
        builder(AMSpellParts.LUNAR, 1f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.NATURE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        builder(AMSpellParts.MINING_POWER, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1))
                .build();
        builder(AMSpellParts.PIERCING, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        builder(AMSpellParts.PROSPERITY, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_GOLD), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_EMERALD), 1))
                .build();
        builder(AMSpellParts.RANGE, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .build();
        builder(AMSpellParts.RUNE_PROCS, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .build();
        builder(AMSpellParts.SILK_TOUCH, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .build();
        builder(AMSpellParts.SOLAR, 1f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.NATURE.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        builder(AMSpellParts.TARGET_NON_SOLID, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POPPY), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)), 1))
                .build();
        builder(AMSpellParts.VELOCITY, 1.25f)
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIGHTNING.get())), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.BOATS), 1))
                .addIngredient(new IngredientSpellIngredient(StrictNBTIngredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS)), 1))
                .build();
        builder(AMSpellParts.COLOR, 1.0f)
                .addIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.SPELL_DYE.get()), 1))
                .build();
    }
}
