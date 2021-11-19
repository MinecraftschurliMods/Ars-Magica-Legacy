package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Experimental
public interface ISpellIngredient {
    Codec<ISpellIngredient> CODEC = CompoundTag.CODEC.flatXmap(tag -> {
        String type = tag.getString("type");
        tag.remove("type");
        return ArsMagicaAPI.get().getSpellDataManager().getSpellIngredientCodec(new ResourceLocation(type)).decode(
                NbtOps.INSTANCE, tag).map(Pair::getFirst);
    }, ingredient -> ArsMagicaAPI.get().getSpellDataManager().getSpellIngredientCodec(ingredient.getType()).encodeStart(NbtOps.INSTANCE, ingredient).map(CompoundTag.class::cast).map(tag -> {
        tag.putString("type", ingredient.getType().toString());
        return tag;
    }));

    ResourceLocation getType();
    Component getTooltip();

    boolean canCombine(ISpellIngredient other);

    @Nullable ISpellIngredient combine(ISpellIngredient other);

    @Nullable ISpellIngredient consume(Level level, BlockPos pos);

    ISpellIngredientRenderer<?> getRenderer();
}
