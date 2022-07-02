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
import net.minecraftforge.common.crafting.NBTIngredient;

import java.util.Set;

class AMSpellPartDataProvider extends SpellPartDataProvider {
    public AMSpellPartDataProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createSpellPartData() {
        var helper = ArsMagicaAPI.get().getAffinityHelper();
        createSpellPartData(AMSpellParts.AOE, 2f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TNT), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 1))
                .build();
        createSpellPartData(AMSpellParts.BEAM, 1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT), 2500))
                .build();
        createSpellPartData(AMSpellParts.CHAIN, 1.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STRING), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LEAD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TRIPWIRE_HOOK), 1))
                .build();
        createSpellPartData(AMSpellParts.CHANNEL, 1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.CONTINGENCY_DAMAGE, 10f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIGHTNING.get())), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 5000))
                .build();
        createSpellPartData(AMSpellParts.CONTINGENCY_DEATH, 10f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                //.withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.DARK), 5000))
                .build();
        createSpellPartData(AMSpellParts.CONTINGENCY_FALL, 10f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 5000))
                .build();
        createSpellPartData(AMSpellParts.CONTINGENCY_FIRE, 10f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 5000))
                .build();
        createSpellPartData(AMSpellParts.CONTINGENCY_HEALTH, 10f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIFE.get())), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                //.withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT), 5000))
                .build();
        createSpellPartData(AMSpellParts.PROJECTILE, 1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.RUNE, 2f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .build();
        createSpellPartData(AMSpellParts.SELF, 0.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                //.withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.NEUTRAL), 500))
                .build();
        createSpellPartData(AMSpellParts.TOUCH, 1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1))
                .build();
        createSpellPartData(AMSpellParts.WALL, 2.5f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FENCES_WOODEN), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.WALLS), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        createSpellPartData(AMSpellParts.WAVE, 3f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.LIGHT, EtheriumType.NEUTRAL, EtheriumType.DARK), 2500))
                .build();
        createSpellPartData(AMSpellParts.ZONE, 3.5f)
                .withIngredient(new IngredientSpellIngredient(NBTIngredient.of(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.DROWNING_DAMAGE, 80f)
                .withAffinity(AMAffinities.WATER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.FIRE_DAMAGE, 80f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.FLINT_AND_STEEL)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.FROST_DAMAGE, 80f)
                .withAffinity(AMAffinities.ICE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.CYAN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ICE), 1))
                .build();
        createSpellPartData(AMSpellParts.LIGHTNING_DAMAGE, 120f)
                .withAffinity(AMAffinities.LIGHTNING, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1))
                .build();
        createSpellPartData(AMSpellParts.MAGIC_DAMAGE, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BOOK), 1))
                .build();
        createSpellPartData(AMSpellParts.PHYSICAL_DAMAGE, 40f)
                .withAffinity(AMAffinities.EARTH, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1))
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
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.INVISIBILITY)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.JUMP_BOOST, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.LEVITATION, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1))
                .build();
        createSpellPartData(AMSpellParts.NIGHT_VISION, 80f)
                .withAffinity(AMAffinities.ENDER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GOLDEN_CARROT), 1))
                .build();
        createSpellPartData(AMSpellParts.NAUSEA, 200f)
                .build();
        createSpellPartData(AMSpellParts.REGENERATION, 480f)
                .withAffinity(AMAffinities.LIFE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1))
                .build();
        createSpellPartData(AMSpellParts.SLOWNESS, 80f)
                .withAffinity(AMAffinities.ICE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        createSpellPartData(AMSpellParts.SLOW_FALLING, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PHANTOM_MEMBRANE), 1))
                .build();
        createSpellPartData(AMSpellParts.WATER_BREATHING, 80f)
                .withAffinity(AMAffinities.WATER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PUFFERFISH), 1))
                .build();
        createSpellPartData(AMSpellParts.AGILITY, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .build();
        createSpellPartData(AMSpellParts.ASTRAL_DISTORTION, 80f)
                .withAffinity(AMAffinities.ENDER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        createSpellPartData(AMSpellParts.ENTANGLE, 80f)
                .withAffinity(AMAffinities.NATURE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.VINE), 1))
                .build();
        createSpellPartData(AMSpellParts.FLIGHT, 80f)
                .withAffinity(AMAffinities.AIR, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1))
                .build();
        createSpellPartData(AMSpellParts.FURY, 80f)
                .withAffinity(AMAffinities.LIGHTNING, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_BLAZE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TROPICAL_FISH), 1))
                .build();
        createSpellPartData(AMSpellParts.GRAVITY_WELL, 80f)
                .withAffinity(AMAffinities.EARTH, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GRAY)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STONE), 1))
                .build();
        createSpellPartData(AMSpellParts.REFLECT, 480f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_GRAY)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_BLAZE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GLASS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.WITCHWOOD_LOGS), 1))
                .build();
        createSpellPartData(AMSpellParts.SCRAMBLE_SYNAPSES, 6000f)
                .build();
        createSpellPartData(AMSpellParts.SHIELD, 80f)
                .withAffinity(AMAffinities.EARTH, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BROWN)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.SHIELD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.SHRINK, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STONE_BUTTON), 1))
                .build();
        createSpellPartData(AMSpellParts.SILENCE, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BROWN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.WOOL), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.JUKEBOX), 1))
                .build();
        createSpellPartData(AMSpellParts.SWIFT_SWIM, 80f)
                .withAffinity(AMAffinities.WATER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.FISHES), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.FISHING_ROD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.TEMPORAL_ANCHOR, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHER_STARS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.TRUE_SIGHT, 80f)
                .withAffinity(AMAffinities.ARCANE, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GLASS), 1))
                .build();
        createSpellPartData(AMSpellParts.WATERY_GRAVE, 80f)
                .withAffinity(AMAffinities.WATER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.STONE), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.LEATHER_BOOTS)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.ATTRACT, 2f)
                .withAffinity(AMAffinities.NATURE, 0.001f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_IRON), 1))
                .build();
        createSpellPartData(AMSpellParts.BANISH_RAIN, 750f)
                .withAffinity(AMAffinities.WATER, 0.1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1))
                .build();
        createSpellPartData(AMSpellParts.BLINK, 160f)
                .withAffinity(AMAffinities.ENDER, 0.05f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        createSpellPartData(AMSpellParts.BLIZZARD, 2000f)
                .withAffinity(AMAffinities.ICE, 0.1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_TOPAZ), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ICE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PACKED_ICE), 1))
                .build();
        createSpellPartData(AMSpellParts.CHARM, 40f)
                .withAffinity(AMAffinities.LIFE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.RED)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WHEAT), 1))
                .build();
        createSpellPartData(AMSpellParts.CREATE_WATER, 25f)
                .withAffinity(AMAffinities.WATER, 0.001f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.DAYLIGHT, 25000f)
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.DIG, 10f)
                .withAffinity(AMAffinities.EARTH, 0.001f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BROWN)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_AXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_PICKAXE)){}, 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_SHOVEL)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.DISARM, 150f)
                .withAffinity(AMAffinities.ARCANE, 0.003f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_SWORD)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.DISPEL, 40f)
                .withAffinity(AMAffinities.ARCANE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.MILK_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.DIVINE_INTERVENTION, 400f)
                .withAffinity(AMAffinities.ENDER, 0.4f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.BEDS), 1))
                .build();
        createSpellPartData(AMSpellParts.DROUGHT, 75f)
                .withAffinity(AMAffinities.FIRE, 0.001f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.DEAD_BUSH), 1))
                .build();
        createSpellPartData(AMSpellParts.ENDER_INTERVENTION, 400f)
                .withAffinity(AMAffinities.ENDER, 0.4f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1))
                .build();
        createSpellPartData(AMSpellParts.EXPLOSION, 80f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GRAY)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.FIRE_CHARGE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.TNT), 1))
                .build();
        createSpellPartData(AMSpellParts.FALLING_STAR, 1000f)
                .withAffinity(AMAffinities.ARCANE, 0.1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_ARCANE_ASH), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.END_STONES), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.FIRE_RAIN, 3000f)
                .withAffinity(AMAffinities.FIRE, 0.1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_ARCANE_ASH), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.NETHERRACK), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.FLING, 40f)
                .withAffinity(AMAffinities.AIR, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PISTON), 1))
                .build();
        createSpellPartData(AMSpellParts.FORGE, 60f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.FURNACE), 1))
                .build();
        createSpellPartData(AMSpellParts.FROST, 40f)
                .withAffinity(AMAffinities.ICE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POWDER_SNOW_BUCKET), 1))
                .build();
        createSpellPartData(AMSpellParts.GROW, 80f)
                .withAffinity(AMAffinities.NATURE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.BONE_MEAL), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.WITCHWOOD_LOGS), 1))
                .build();
        createSpellPartData(AMSpellParts.HARVEST, 80f)
                .withAffinity(AMAffinities.NATURE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.SHEARS)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.HEAL, 40f)
                .withAffinity(AMAffinities.LIFE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .build();
        createSpellPartData(AMSpellParts.IGNITION, 60f)
                .withAffinity(AMAffinities.FIRE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.ORANGE)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.FLINT_AND_STEEL)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.KNOCKBACK, 40f)
                .withAffinity(AMAffinities.WATER, 0.01f)
                .withAffinity(AMAffinities.EARTH, 0.01f)
                .withAffinity(AMAffinities.AIR, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.PISTON), 1))
                .build();
        createSpellPartData(AMSpellParts.LIFE_DRAIN, 40f)
                .withAffinity(AMAffinities.LIFE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.LIFE_TAP, 0f)
                .withAffinity(AMAffinities.LIFE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.LIGHT, 40f)
                .withAffinity(AMAffinities.LIGHTNING, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.VINTEUM_TORCH.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.MANA_BLAST, 0f)
                .withAffinity(AMAffinities.ENDER, 0.1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MANA_FOCUS.get())))
                .build();
        createSpellPartData(AMSpellParts.MANA_DRAIN, 20f)
                .withAffinity(AMAffinities.ARCANE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.CYAN)), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.MANA_SHIELD, 0f)
                .withAffinity(AMAffinities.WATER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.MANA_FOCUS.get())))
                .build();
        createSpellPartData(AMSpellParts.MELT_ARMOR, 200f)
                .build();
        createSpellPartData(AMSpellParts.MOONRISE, 25000f)
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.PLACE_BLOCK, 5f)
                .withAffinity(AMAffinities.EARTH, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_GRAY)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.CHESTS_WOODEN), 1))
                .build();
        createSpellPartData(AMSpellParts.PLANT, 80f)
                .withAffinity(AMAffinities.NATURE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WHEAT_SEEDS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.PLOW, 80f)
                .withAffinity(AMAffinities.EARTH, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(new ItemStack(Items.IRON_HOE)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.RANDOM_TELEPORT, 40f)
                .withAffinity(AMAffinities.ENDER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.ENDER_PEARLS), 1))
                .build();
        createSpellPartData(AMSpellParts.RECALL, 400f)
                .withAffinity(AMAffinities.ARCANE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.LIME)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.MAP), 1))
                .build();
        createSpellPartData(AMSpellParts.REPEL, 2f)
                .withAffinity(AMAffinities.NATURE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_PURIFIED_VINTEUM), 1))
                .build();
        createSpellPartData(AMSpellParts.RIFT, 75f)
                .withAffinity(AMAffinities.ENDER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENDER_CHEST), 1))
                .build();
        createSpellPartData(AMSpellParts.STORM, 750f)
                .withAffinity(AMAffinities.LIGHTNING, 0.1f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1))
                .build();
        createSpellPartData(AMSpellParts.SUMMON, 400f)
                .withAffinity(AMAffinities.LIFE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GRAY)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.DUSTS_PURIFIED_VINTEUM), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .withIngredient(new EtheriumSpellIngredient(Set.of(EtheriumType.DARK), 2500))
                .build();
        createSpellPartData(AMSpellParts.TELEKINESIS, 4f)
                .withAffinity(AMAffinities.ARCANE, 0.001f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PURPLE)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.STICKY_PISTON), 1))
                .build();
        createSpellPartData(AMSpellParts.TRANSPLACE, 160f)
                .withAffinity(AMAffinities.ENDER, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1))
                .build();
        createSpellPartData(AMSpellParts.WIZARDS_AUTUMN, 4f)
                .withAffinity(AMAffinities.NATURE, 0.01f)
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.GREEN)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.RODS_WOODEN), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1))
                .build();
        createSpellPartData(AMSpellParts.BOUNCE, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIGHTNING.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        createSpellPartData(AMSpellParts.DAMAGE, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.DISMEMBERING, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.FIRE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.BONES), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.WITHER_SKELETON_SKULL), 1))
                .build();
        createSpellPartData(AMSpellParts.DURATION, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.SLIMEBALLS), 1))
                .build();
        createSpellPartData(AMSpellParts.EFFECT_POWER, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.CROPS_NETHER_WART), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_GLOWSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GUNPOWDER), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.GLASS_BOTTLE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1))
                .build();
        createSpellPartData(AMSpellParts.GRAVITY, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ENDER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.COMPASS), 1))
                .build();
        createSpellPartData(AMSpellParts.HEALING, 1.75f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIFE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.EGG), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.LUNAR, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.NATURE.get())){}, 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_MOONSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.MINING_POWER, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_DIAMOND), 1))
                .build();
        createSpellPartData(AMSpellParts.PIERCING, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ICE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.SNOWBALL), 1))
                .build();
        createSpellPartData(AMSpellParts.PROSPERITY, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.EARTH.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.INGOTS_GOLD), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.GEMS_EMERALD), 1))
                .build();
        createSpellPartData(AMSpellParts.RANGE, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.ARROW), 1))
                .build();
        createSpellPartData(AMSpellParts.RUNE_PROCS, 1.75f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.ARCANE.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.BLACK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.PINK)), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.COLORED_RUNE.get(DyeColor.WHITE)), 1))
                .build();
        createSpellPartData(AMSpellParts.SILK_TOUCH, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.AIR.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_CHIMERITE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .build();
        createSpellPartData(AMSpellParts.SOLAR, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.NATURE.get())){}, 1))
                //.withIngredient(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.GEMS_SUNSTONE), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.CLOCK), 1))
                .build();
        createSpellPartData(AMSpellParts.TARGET_NON_SOLID, 1f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.WATER.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Items.POPPY), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)){}, 1))
                .build();
        createSpellPartData(AMSpellParts.VELOCITY, 1.25f)
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(helper.getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), AMAffinities.LIGHTNING.get())){}, 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(Tags.Items.FEATHERS), 1))
                .withIngredient(new IngredientSpellIngredient(Ingredient.of(ItemTags.BOATS), 1))
                .withIngredient(new IngredientSpellIngredient(new NBTIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.SWIFTNESS)){}, 1))
                .build();
    }
}
