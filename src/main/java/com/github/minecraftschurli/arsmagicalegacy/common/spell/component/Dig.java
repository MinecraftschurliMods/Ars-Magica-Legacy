package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.modifier.MiningPower;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class Dig extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.FAIL;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos blockPos = target.getBlockPos();
        BlockState state = level.getBlockState(blockPos);
        float hardness = state.getDestroySpeed(level, blockPos);
        if (hardness != -1 && (state.requiresCorrectToolForDrops() || TierSortingRegistry.isCorrectTierForDrops(getTier(modifiers), state))) {
            if (caster instanceof Player player) {
                state.getBlock().playerDestroy(level, player, blockPos, state, level.getBlockEntity(blockPos), createDummyStack(countModifiers(modifiers, AMSpellParts.FORTUNE.getId()), countModifiers(modifiers, AMSpellParts.SILK_TOUCH.getId())));
            } else {
                state.getBlock().destroy(level, blockPos, state);
            }
            level.destroyBlock(blockPos, false);
            ArsMagicaAPI.get().getMagicHelper().decreaseMana(caster, hardness * 1.28f);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.FAIL;
    }

    private ItemStack createDummyStack(int fortune, int silk_touch) {
        ItemStack stack = new ItemStack(ItemStack.EMPTY.getItem());
        stack.enchant(Enchantments.BLOCK_FORTUNE, fortune);
        stack.enchant(Enchantments.SILK_TOUCH, silk_touch);
        return stack;
    }

    private int countModifiers(List<ISpellModifier> modifiers, ResourceLocation modifier) {
        return modifiers.stream().map(IForgeRegistryEntry::getRegistryName).filter(modifier::equals).toList().size();
    }

    private Tier getTier(List<ISpellModifier> modifiers) {
        return modifiers.stream()
                        .filter(MiningPower.class::isInstance)
                        .map(MiningPower.class::cast)
                        .map(MiningPower::getTier)
                        .findFirst()
                        .orElse(Tiers.IRON);
    }
}
