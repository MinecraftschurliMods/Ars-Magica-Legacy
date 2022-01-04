package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public record IngredientSpellIngredient(Ingredient ingredient, int count) implements ISpellIngredient {
    public static final ResourceLocation INGREDIENT = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ingredient");
    public static final Codec<IngredientSpellIngredient> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.INGREDIENT.fieldOf("ingredient").forGetter(IngredientSpellIngredient::ingredient),
            Codec.INT.fieldOf("count").forGetter(IngredientSpellIngredient::count)
    ).apply(inst, IngredientSpellIngredient::new));
    public static final Codec<IngredientSpellIngredient> NETWORK_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.NETWORK_INGREDIENT.fieldOf("ingredient").forGetter(IngredientSpellIngredient::ingredient),
            Codec.INT.fieldOf("count").forGetter(IngredientSpellIngredient::count)
    ).apply(inst, IngredientSpellIngredient::new));

    @Override
    public ResourceLocation getType() {
        return INGREDIENT;
    }

    @Override
    public Component getTooltip() {
        ItemStack[] items = ingredient().getItems();
        if (items.length == 1) return items[0].getDisplayName().copy().append(" x " + count());
        return new TextComponent("(").append(Arrays.stream(items).map(ItemStack::getDisplayName).map(Component::copy).collect(AMUtil.joiningComponents(" | "))).append(") x " + count());
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        if (other instanceof IngredientSpellIngredient i) {
            return Arrays.equals(i.ingredient().getItems(), this.ingredient().getItems());
        }
        return false;
    }

    @Override
    @Nullable
    public ISpellIngredient combine(ISpellIngredient other) {
        if (canCombine(other)) {
            return new IngredientSpellIngredient(ingredient(), ((IngredientSpellIngredient) other).count() + count());
        }
        return null;
    }

    @Override
    @Nullable
    public ISpellIngredient consume(Level level, BlockPos pos) {
        int count = count();
        for (ItemEntity entity : level.getEntities(EntityTypeTest.forClass(ItemEntity.class), new AABB(pos).inflate(0.5, 1, 0.5).move(0, -2, 0), itemEntity -> ingredient().test(itemEntity.getItem()))) {
            ItemStack item = entity.getItem();
            int min = Math.min(item.getCount(), count);
            item.shrink(min);
            count = count - min;
            if (count <= 0) {
                return null;
            }
        }
        return new IngredientSpellIngredient(ingredient(), count);
    }

    public static class IngredientSpellIngredientRenderer implements ISpellIngredientRenderer<IngredientSpellIngredient> {
        @Override
        public void renderInWorld(IngredientSpellIngredient ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
            Minecraft minecraft = Minecraft.getInstance();
            ItemStack stack = AMUtil.getByTick(ingredient.ingredient().getItems(), minecraft.player.tickCount / 20);
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            BakedModel model = itemRenderer.getModel(stack, null, null, 0);
            itemRenderer.render(stack, ItemTransforms.TransformType.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, model);
        }

        @Override
        public void renderInGui(IngredientSpellIngredient ingredient, PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
            Minecraft minecraft = Minecraft.getInstance();
            ItemStack stack = AMUtil.getByTick(ingredient.ingredient().getItems(), minecraft.player.tickCount / 20);
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            itemRenderer.renderGuiItem(stack, x, y);
        }
    }
}
