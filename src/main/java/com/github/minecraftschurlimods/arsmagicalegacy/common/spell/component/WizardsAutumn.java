package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class WizardsAutumn extends AbstractComponent {
    public WizardsAutumn() {
        super(SpellPartStats.RANGE);
    }

    private static SpellCastResult performWizardsAutumn(BlockPos origin, ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, HitResult target, int index) {
        int range = (int) ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target, index);
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    BlockPos pos = origin.offset(i, j, k);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(BlockTags.LEAVES)) {
                        level.destroyBlock(pos, true, caster);
                    }
                }
            }
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return performWizardsAutumn(target.getEntity().blockPosition(), spell, caster, level, modifiers, target, index);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return performWizardsAutumn(target.getBlockPos(), spell, caster, level, modifiers, target, index);
    }
}
