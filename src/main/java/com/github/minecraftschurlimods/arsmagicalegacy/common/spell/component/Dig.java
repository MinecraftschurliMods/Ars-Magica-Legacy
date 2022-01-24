package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.TierMapping;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class Dig extends AbstractComponent {
    public Dig() {
        super(SpellPartStats.FORTUNE, SpellPartStats.MINING_TIER, SpellPartStats.SILK_TOUCH);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        BlockPos blockPos = target.getBlockPos();
        BlockState state = level.getBlockState(blockPos);
        float hardness = state.getDestroySpeed(level, blockPos);
        if (hardness < 0) return SpellCastResult.EFFECT_FAILED;
        var api = ArsMagicaAPI.get();
        var helper = api.getSpellHelper();
        if (!state.requiresCorrectToolForDrops() && !TierSortingRegistry.isCorrectTierForDrops(TierMapping.instance().getTierForPower((int) helper.getModifiedStat(2, SpellPartStats.MINING_TIER, modifiers, spell, caster, target)), state))
            return SpellCastResult.EFFECT_FAILED;
        if (!(caster instanceof Player player && player.isCreative()) && !api.getManaHelper().decreaseMana(caster, hardness * 1.28f))
            return SpellCastResult.NOT_ENOUGH_MANA;
        if (caster instanceof Player player) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            ItemStack dummyStack = AMUtil.createDummyStack((int) helper.getModifiedStat(0, SpellPartStats.FORTUNE, modifiers, spell, caster, target), (int) helper.getModifiedStat(0, SpellPartStats.SILK_TOUCH, modifiers, spell, caster, target));
            state.getBlock().playerDestroy(level, player, blockPos, state, blockEntity, dummyStack);
        }
        level.destroyBlock(blockPos, false);
        return SpellCastResult.SUCCESS;
    }
}
