package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record EtheriumSpellIngredient(Set<EtheriumType> types, int amount) implements ISpellIngredient {
    public static final ResourceLocation ETHERIUM = new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium");
    public static final Codec<EtheriumSpellIngredient> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.setOf(CodecHelper.forStringEnum(EtheriumType.class))
                    .fieldOf("types")
                    .forGetter(EtheriumSpellIngredient::types),
            Codec.INT.fieldOf("amount")
                    .forGetter(EtheriumSpellIngredient::amount)
    ).apply(inst, EtheriumSpellIngredient::new));

    @Override
    public ResourceLocation getType() {
        return ETHERIUM;
    }

    @Override
    public List<Component> getTooltip() {
        if (types.size() == 1)
            return List.of(types.iterator().next().getDisplayName(), new TextComponent("x " + amount()));
        ArrayList<Component> components = new ArrayList<>(types.stream().map(ITranslatable::getDisplayName).toList());
        components.add(new TextComponent("x " + amount()));
        return components;
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        if (other instanceof EtheriumSpellIngredient eth) {
            return Objects.equals(eth.types(), types());
        }
        return false;
    }

    @Nullable
    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
        if (canCombine(other)) {
            return new EtheriumSpellIngredient(types(), ((EtheriumSpellIngredient) other).amount() + amount());
        }
        return null;
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
                        amount = iEtheriumProvider.consume(level, pos, amount);
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
            // TODO render
        }

        @Override
        public void renderInGui(EtheriumSpellIngredient ingredient, PoseStack poseStack, int x, int y, int mouseX, int mouseY) {
            // TODO render
        }
    }
}
