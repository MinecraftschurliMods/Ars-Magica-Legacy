package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class WizardsAutumn extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        int range = 2 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId());
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    BlockPos pos = target.getEntity().blockPosition().offset(i, j, k);
                    BlockState state = level.getBlockState(pos);
                    if (BlockTags.LEAVES.contains(state.getBlock())) {
                        level.destroyBlock(pos, true, caster);
                    }
                }
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        int range = 2 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId());
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    BlockPos pos = target.getBlockPos().offset(i, j, k);
                    BlockState state = level.getBlockState(pos);
                    if (BlockTags.LEAVES.contains(state.getBlock())) {
                        level.destroyBlock(pos, true, caster);
                    }
                }
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }
}
