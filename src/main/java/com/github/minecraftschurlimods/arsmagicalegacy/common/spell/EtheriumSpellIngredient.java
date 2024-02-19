package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredientType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellIngredientTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record EtheriumSpellIngredient(Set<EtheriumType> types, int amount) implements ISpellIngredient {
    public static final Codec<EtheriumSpellIngredient> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.setOf(CodecHelper.forStringEnum(EtheriumType.class)).fieldOf("types").forGetter(EtheriumSpellIngredient::types),
            Codec.INT.fieldOf("amount").forGetter(EtheriumSpellIngredient::amount)
    ).apply(inst, EtheriumSpellIngredient::new));

    public EtheriumSpellIngredient(EtheriumType type, int amount) {
        this(EnumSet.of(type), amount);
    }

    @Override
    public SpellIngredientType<EtheriumSpellIngredient> getType() {
        return AMSpellIngredientTypes.ETHERIUM.get();
    }

    @Override
    public int getCount() {
        return amount;
    }

    @Override
    public List<Component> getTooltip() {
        if (types.size() == 1)
            return List.of(types.iterator().next().getDisplayName(), Component.literal("x " + amount()));
        ArrayList<Component> components = new ArrayList<>(types.stream().map(ITranslatable::getDisplayName).toList());
        components.add(Component.literal("x " + amount()));
        return components;
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return other instanceof EtheriumSpellIngredient e && Objects.equals(e.types(), types());
    }

    @Nullable
    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        return canCombine(other) ? new EtheriumSpellIngredient(types(), ((EtheriumSpellIngredient) other).amount() + amount()) : null;
    }

    @Nullable
    @Override
    public ISpellIngredient consume(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AltarCoreBlockEntity altarCore) {
            boolean leverActive = altarCore.isLeverActive();
            if (leverActive) {
                int amount = amount();
                for (IEtheriumProvider iEtheriumProvider : altarCore.getBoundProviders()) {
                    if (types().contains(iEtheriumProvider.getType())) {
                        amount -= iEtheriumProvider.consume(level, pos, amount);
                        if (amount <= 0) return null;
                    }
                }
                return new EtheriumSpellIngredient(types(), amount);
            }
        }
        return this;
    }

    public static class EtheriumSpellIngredientRenderer implements ISpellIngredientRenderer<EtheriumSpellIngredient> {
        @Override
        public void renderInWorld(EtheriumSpellIngredient ingredient, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
            Minecraft minecraft = Minecraft.getInstance();
            EtheriumType type = AMUtil.getByTick(ingredient.types().toArray(new EtheriumType[0]), Objects.requireNonNull(ClientHelper.getLocalPlayer()).tickCount / 20);
            ItemStack stack = new ItemStack(AMItems.ETHERIUM_PLACEHOLDER.get());
            ArsMagicaAPI.get().getEtheriumHelper().setEtheriumType(stack, type);
            ItemRenderer itemRenderer = minecraft.getItemRenderer();
            BakedModel model = itemRenderer.getModel(stack, null, null, 0);
            itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, model);
        }

        @Override
        public void renderInGui(EtheriumSpellIngredient ingredient, GuiGraphics graphics, int x, int y, int mouseX, int mouseY) {
            EtheriumType type = AMUtil.getByTick(ingredient.types().toArray(new EtheriumType[0]), Objects.requireNonNull(ClientHelper.getLocalPlayer()).tickCount / 20);
            ItemStack stack = new ItemStack(AMItems.ETHERIUM_PLACEHOLDER.get());
            ArsMagicaAPI.get().getEtheriumHelper().setEtheriumType(stack, type);
            stack.setCount(ingredient.getCount());
            ClientHelper.drawItemStack(graphics, stack, x, y);
        }
    }
}
