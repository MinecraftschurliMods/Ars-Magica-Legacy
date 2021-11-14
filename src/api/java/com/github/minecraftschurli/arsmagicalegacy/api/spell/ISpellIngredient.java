package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface ISpellIngredient {
    ResourceLocation getType();
    Component getTooltip();
    ISpellIngredientRenderer getRenderer();
}
