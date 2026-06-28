package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class Plow extends SpellComponent.CastBlock {
    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        ItemStack stack = new ItemStack(Items.WOODEN_HOE);
        stack.useOn(new UseOnContext(context.level(), context.caster() instanceof Player player ? player : null, InteractionHand.MAIN_HAND, stack, hitResult));
        return SpellComponentCastResult.success(context.spell());
    }
}
