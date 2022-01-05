package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class PlaceBlock extends AbstractComponent {
    private static final String KEY = "PlaceBlockID";

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        if (!(caster instanceof Player player)) return SpellCastResult.EFFECT_FAILED;
        ItemStack stack = AMUtil.getSpellStack(player);
        if (player.isShiftKeyDown()) {
            BlockState state = level.getBlockState(target.getBlockPos());
            if (!state.isAir() && state.getBlock().asItem() != Items.AIR) {
                stack.getOrCreateTag().putString(KEY, state.getBlock().getRegistryName().toString());
                return SpellCastResult.SUCCESS;
            }
        } else {
            String id = stack.getOrCreateTag().getString(KEY);
            if (id.isEmpty()) return SpellCastResult.EFFECT_FAILED;
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id));
            if (block == null) return SpellCastResult.EFFECT_FAILED;
            BlockPos pos = target.getBlockPos();
            if (!level.getBlockState(pos).canBeReplaced(new BlockPlaceContext(player, InteractionHand.MAIN_HAND, stack,target))) {
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
