package com.github.minecraftschurlimods.arsmagicalegacy.common.recipe.spelltransformation;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRecipes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMExtraCodecs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public record SpellTransformationRecipe(RuleTest ruleTest, Holder<SpellPart> spellPart, BlockState result) implements Recipe<SpellTransformationInput> {
    public static final MapCodec<SpellTransformationRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        RuleTest.CODEC.fieldOf("predicate").forGetter(SpellTransformationRecipe::ruleTest),
        AMRegistries.SPELL_PARTS.holderByNameCodec().fieldOf("spell_part").forGetter(SpellTransformationRecipe::spellPart),
        BlockState.CODEC.fieldOf("result").forGetter(SpellTransformationRecipe::result)
    ).apply(inst, SpellTransformationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellTransformationRecipe> STREAM_CODEC = StreamCodec.composite(
        AMExtraCodecs.toStreamCodec(RuleTest.CODEC), SpellTransformationRecipe::ruleTest,
        ByteBufCodecs.holderRegistry(AMRegistries.Keys.SPELL_PART), SpellTransformationRecipe::spellPart,
        AMExtraCodecs.toStreamCodec(BlockState.CODEC), SpellTransformationRecipe::result,
        SpellTransformationRecipe::new);

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean matches(SpellTransformationInput input, Level level) {
        return AMUtil.doRuleTest(ruleTest, input.state()) && input.spellPart().is(spellPart.getKey());
    }

    @Override
    public ItemStack assemble(SpellTransformationInput spellTransformationInput) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends SpellTransformationRecipe> getSerializer() {
        return AMRecipes.SPELL_TRANSFORMATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends SpellTransformationRecipe> getType() {
        return AMRecipes.SPELL_TRANSFORMATION_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return AMRecipes.SPELL_RECIPE_BOOK_CATEGORY;
    }
}
