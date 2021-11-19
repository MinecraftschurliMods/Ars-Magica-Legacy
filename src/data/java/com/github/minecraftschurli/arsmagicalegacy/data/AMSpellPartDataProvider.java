package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SpellPartDataProvider;
import com.github.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.item.AffinityEssenceItem;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.IngredientSpellIngredient;
import net.minecraft.data.DataGenerator;
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
        createSpellPartData(AMSpellParts.SELF, 0.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(lesser_focus)))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.NEUTRAL), 500))
                .build();
        createSpellPartData(AMSpellParts.TOUCH, 1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .build();

        createSpellPartData(AMSpellParts.FIRE_DAMAGE, 120f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.FLINT_AND_STEEL)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.FROST_DAMAGE, 80f)
                .withAffinity(AMAffinities.ICE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.LIGHTNING_DAMAGE, 180f)
                .withAffinity(AMAffinities.LIGHTNING, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STICK), 1))
                .build();
        createSpellPartData(AMSpellParts.MAGIC_DAMAGE, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.01f)
                .withAffinity(AMAffinities.ENDER, 0.005f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BOOK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_LAPIS), 1))
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
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
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
        createSpellPartData(AMSpellParts.NAUSEA, 200f)
                .withAffinity(AMAffinities.LIFE, 0.05f)
                .build();
        createSpellPartData(AMSpellParts.REGENERATION, 540f)
                .withAffinity(AMAffinities.LIFE, 0.05f)
                .withAffinity(AMAffinities.NATURE, 0.05f)
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

        createSpellPartData(AMSpellParts.DIG, 10f)
                .withAffinity(AMAffinities.EARTH, 0.001f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_AXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_PICKAXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_SHOVEL)){}, 1))
                .build();

        createSpellPartData(AMSpellParts.RANGE, 1.2f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.FORTUNE, 1.25f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_GOLD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_GOLD), 1))
                .build();
        createSpellPartData(AMSpellParts.SILK_TOUCH, 1.25f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .build();
        createSpellPartData(AMSpellParts.MINING_POWER, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.DIAMOND_PICKAXE)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.TARGET_NON_SOLID, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(ArsMagicaAPI.get().getAffinityHelper().getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POPPY), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)){}, 1))
                .build();
    }
}
