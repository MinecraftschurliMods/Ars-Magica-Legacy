package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class PlaceBlock extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        if (!(caster instanceof Player player)) return SpellCastResult.EFFECT_FAILED;
        ItemStack stack = ArsMagicaAPI.get().getSpellHelper().getSpellItemStackFromEntity(player);
        if (player.isShiftKeyDown()) {
            BlockState state = level.getBlockState(target.getBlockPos());
            if (!state.isAir() && state.getBlock().asItem() != Items.AIR) {
                stack.set(AMDataComponents.SELECTED_BLOCK, state.getBlock());
                return SpellCastResult.SUCCESS;
            }
        } else {
            Block block = stack.get(AMDataComponents.SELECTED_BLOCK);
            if (block == null || block == Blocks.AIR) return SpellCastResult.EFFECT_FAILED;
            BlockPos pos = target.getBlockPos();
            if (!level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(player, InteractionHand.MAIN_HAND, stack, target))) {
                pos = pos.offset(target.getDirection().getNormal());
            }
            ItemStack itemStack = new ItemStack(block);
            Inventory inv = player.getInventory();
            if (player.isCreative() || inv.contains(itemStack)) {
                level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);
                if (!player.isCreative()) {
                    inv.getItem(inv.findSlotMatchingItem(itemStack)).shrink(1);
                }
                return SpellCastResult.SUCCESS;
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
