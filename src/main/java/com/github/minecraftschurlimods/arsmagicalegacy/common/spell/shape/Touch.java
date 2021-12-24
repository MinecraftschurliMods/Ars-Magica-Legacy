package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Touch extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        ISpellHelper spellHelper = ArsMagicaAPI.get().getSpellHelper();
        boolean targetNonSolid = spellHelper.isModifierPresent(modifiers, AMSpellParts.TARGET_NON_SOLID.getId());
        float range = 2.5f + spellHelper.countModifiers(modifiers, AMSpellParts.RANGE.getId());
        HitResult trace = spellHelper.trace(caster, level, range, true, targetNonSolid);
        return spellHelper.invoke(spell, caster, level, trace, ticksUsed, index, awardXp);
    }

    @Override
    public boolean needsToComeFirst() {
        return true;
    }
}
