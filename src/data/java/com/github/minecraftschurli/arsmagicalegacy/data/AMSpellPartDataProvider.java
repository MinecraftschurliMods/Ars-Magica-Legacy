package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SpellPartDataProvider;
import com.github.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.NBTIngredient;

import java.util.Set;

public class AMSpellPartDataProvider extends SpellPartDataProvider {
    public AMSpellPartDataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "AMSpellPartData";
    }

    @Override
    protected void createSpellPartData() {
        createSpellPartData(AMSpellParts.PROJECTILE, 1.25f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.RUNE, 1.8f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .build();
        createSpellPartData(AMSpellParts.SELF, 0.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.LESSER_FOCUS.get())))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.NEUTRAL), 500))
                .build();
        createSpellPartData(AMSpellParts.TOUCH, 1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.FISHES), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1))
                .build();
        createSpellPartData(AMSpellParts.WALL, 2.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FENCES_WOODEN), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 2))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COBBLESTONE_WALL), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        createSpellPartData(AMSpellParts.WAVE, 3f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        createSpellPartData(AMSpellParts.ZONE, 4.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 2))
                .build();

        createSpellPartData(AMSpellParts.DROWNING_DAMAGE, 80f)
                .withAffinity(AMAffinities.WATER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STRING), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.FIRE_DAMAGE, 120f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.FLINT_AND_STEEL)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.FROST_DAMAGE, 80f)
                .withAffinity(AMAffinities.ICE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.LIGHTNING_DAMAGE, 180f)
                .withAffinity(AMAffinities.LIGHTNING, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_WOODEN), 1))
                .build();
        createSpellPartData(AMSpellParts.MAGIC_DAMAGE, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.01f)
                .withAffinity(AMAffinities.ENDER, 0.005f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_LAPIS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BOOK), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.STONE_SWORD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.PHYSICAL_DAMAGE, 40f)
                .withAffinity(AMAffinities.EARTH, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_SWORD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.ABSORPTION, 100f)
                .withAffinity(AMAffinities.LIFE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GOLDEN_APPLE), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.SHIELD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.BLINDNESS, 80f)
                .withAffinity(AMAffinities.ENDER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.NIGHT_VISION)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WEAKNESS)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.HASTE, 80f)
                .withAffinity(AMAffinities.LIGHTNING, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .build();
        createSpellPartData(AMSpellParts.INVISIBILITY, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.JUMP_BOOST, 70f)
                .withAffinity(AMAffinities.AIR, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.HOPPER), 1))
                .build();
        createSpellPartData(AMSpellParts.LEVITATION, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.NIGHT_VISION, 80f)
                .withAffinity(AMAffinities.ENDER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.NIGHT_VISION)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.NAUSEA, 200f)
                .withAffinity(AMAffinities.LIFE, 0.05f)
                .build();
        createSpellPartData(AMSpellParts.REGENERATION, 540f)
                .withAffinity(AMAffinities.NATURE, 0.05f)
                .withAffinity(AMAffinities.LIFE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GOLDEN_APPLE), 1))
                .build();
        createSpellPartData(AMSpellParts.SLOWNESS, 80f)
                .withAffinity(AMAffinities.ICE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        createSpellPartData(AMSpellParts.SLOW_FALLING, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .build();
        createSpellPartData(AMSpellParts.WATER_BREATHING, 80f)
                .withAffinity(AMAffinities.WATER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SUGAR_CANE), 1))
                .build();
        createSpellPartData(AMSpellParts.ASTRAL_DISTORTION, 80f)
                .withAffinity(AMAffinities.ENDER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        createSpellPartData(AMSpellParts.ENTANGLE, 80f)
                .withAffinity(AMAffinities.NATURE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.VINE), 1))
                .build();
        createSpellPartData(AMSpellParts.FLIGHT, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1))
                .build();
        createSpellPartData(AMSpellParts.FURY, 261f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withAffinity(AMAffinities.LIGHTNING, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.FISHES), 14))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .build();
        createSpellPartData(AMSpellParts.GRAVITY_WELL, 80f)
                .withAffinity(AMAffinities.EARTH, 0.03f)
                .withAffinity(AMAffinities.ENDER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STRING), 1))
                .build();
        createSpellPartData(AMSpellParts.SCRAMBLE_SYNAPSES, 7000f)
                .withAffinity(AMAffinities.LIGHTNING, 0.05f)
                .build();
        createSpellPartData(AMSpellParts.SHIELD, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_CHESTPLATE)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.SHRINK, 120f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NUGGETS_GOLD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STONE_BUTTON), 1))
                .build();
        createSpellPartData(AMSpellParts.SILENCE, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.WOOL), 2))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.ARCANE_ASH.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.JUKEBOX), 1))
                .build();
        createSpellPartData(AMSpellParts.SWIFT_SWIM, 80f)
                .withAffinity(AMAffinities.WATER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.FISHES), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.FISHING_ROD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.TEMPORAL_ANCHOR, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.15f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.TRUE_SIGHT, 80f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GLASS_PANES), 1))
                .build();
        createSpellPartData(AMSpellParts.WATERY_GRAVE, 80f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STONE), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.LEATHER_BOOTS)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.DIG, 10f)
                .withAffinity(AMAffinities.EARTH, 0.001f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_AXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_PICKAXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_SHOVEL)){}, 1))
                .build();

        createSpellPartData(AMSpellParts.BOUNCE, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        createSpellPartData(AMSpellParts.DAMAGE, 1.3f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_PICKAXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.DISMEMBERING, 1.25f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WITHER_SKELETON_SKULL), 1))
                .build();
        createSpellPartData(AMSpellParts.DURATION, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        createSpellPartData(AMSpellParts.EFFECT_POWER, 1.25f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.CROPS_NETHER_WART), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_EMERALD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GUNPOWDER), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GLASS_BOTTLE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
                .build();
        createSpellPartData(AMSpellParts.GRAVITY, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COMPASS), 1))
                .build();
        createSpellPartData(AMSpellParts.HEALING, 2f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIFE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.EGG), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.LUNAR, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.NATURE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.MINING_POWER, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.DIAMOND_PICKAXE)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.PIERCING, 1.5f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_EMERALD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .build();
        createSpellPartData(AMSpellParts.PROSPERITY, 1.25f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_GOLD), 2))
                .build();
        createSpellPartData(AMSpellParts.RADIUS, 2.5f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TNT), 1))
                .build();
        createSpellPartData(AMSpellParts.RANGE, 1.2f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.RUNE_PROCS, 1.65f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .build();
        createSpellPartData(AMSpellParts.SILK_TOUCH, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .build();
        createSpellPartData(AMSpellParts.SOLAR, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.NATURE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.TARGET_NON_SOLID, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POPPY), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.VELOCITY, 1.2f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIGHTNING.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.BOATS), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS)){}, 1))
                .build();
    }
}
