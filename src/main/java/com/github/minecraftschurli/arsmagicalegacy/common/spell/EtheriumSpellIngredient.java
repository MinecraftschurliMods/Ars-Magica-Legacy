package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurli.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.util.ComponentUtil;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public record EtheriumSpellIngredient(Set<EtheriumType> types, int amount) implements ISpellIngredient {
    public static final ResourceLocation ETHERIUM = new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium");
    public static final Codec<EtheriumSpellIngredient> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.setOf(CodecHelper.forStringEnum(EtheriumType.class)).fieldOf("types").forGetter(EtheriumSpellIngredient::types),
            Codec.INT.fieldOf("amount").forGetter(EtheriumSpellIngredient::amount)
    ).apply(inst, EtheriumSpellIngredient::new));

    @Override
    public ResourceLocation getType() {
        return ETHERIUM;
    }

    @Override
    public Component getTooltip() {
        if (types.size() == 1) return new TranslatableComponent("etherium." + ArsMagicaAPI.MOD_ID + "." + types.iterator().next().name().toLowerCase()).append(" x "+amount());
        return new TextComponent("(").append(types.stream().map(etheriumType -> new TranslatableComponent("etherium." + ArsMagicaAPI.MOD_ID + "." + etheriumType.name().toLowerCase())).collect(ComponentUtil.joiningComponents(" | "))).append(") x "+ amount());
    }

    @Override
    public boolean canCombine(ISpellIngredient other) {
        return false;
    }

    @Nullable
    @Override
    public ISpellIngredient combine(ISpellIngredient other) {
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
                    if (iEtheriumProvider.provides(types())) {
                        amount = iEtheriumProvider.consume(level, pos, amount);
                        if (amount <= 0) return null;
                    }
                }
                return new EtheriumSpellIngredient(types(), amount);
            }
        }
        return this;
    }

    @Override
    public ISpellIngredientRenderer<EtheriumSpellIngredient> getRenderer() {
        return (ingredient, poseStack, bufferSource, packedLight, packedOverlay) -> {};
    }
}
