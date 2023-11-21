package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.IPlantable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plant extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        if (!(caster instanceof Player player)) return SpellCastResult.EFFECT_FAILED;
        BlockPos pos = target.getBlockPos();
        BlockState state = level.getBlockState(pos);
        AbstractContainerMenu inv = player.containerMenu;
        Map<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < inv.slots.size(); i++) {
            ItemStack slotStack = inv.getSlot(i).getItem();
            if (slotStack.getItem() instanceof BlockItem block && block.getBlock() instanceof IPlantable) {
                map.put(i, slotStack);
            }
        }
        if (!map.isEmpty() && !state.isAir() && level.getBlockState(pos.above()).isAir()) {
            boolean success = false;
            for (int i : map.keySet()) {
                ItemStack seedStack = map.get(i);
                if (((BlockItem) seedStack.getItem()).getBlock() instanceof IPlantable plant && state.canSustainPlant(level, pos, Direction.UP, plant)) {
                    level.setBlock(pos.above(), plant.getPlant(level, pos), Block.UPDATE_ALL);
                    if (!player.isCreative()) {
                        seedStack.shrink(1);
                    }
                    success = true;
                    break;
                }
            }
            return success ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
