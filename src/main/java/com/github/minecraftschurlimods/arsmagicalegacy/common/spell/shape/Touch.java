package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Touch extends AbstractShape {
    public Touch() {
        super(SpellPartStats.TARGET_NON_SOLID, SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        boolean targetNonSolid = helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0;
        float range = helper.getModifiedStat(2.5f, SpellPartStats.RANGE, modifiers, spell, caster, hit);
        HitResult trace = helper.trace(caster, level, range, true, targetNonSolid);
        return helper.invoke(spell, caster, level, trace, ticksUsed, index, awardXp);
    }

    @Override
    public boolean needsToComeFirst() {
        return true;
    }
}
