package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Explosion extends AbstractComponent {
    public Explosion() {
        super(SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        level.explode(caster, target.getLocation().x(), target.getLocation().y(), target.getLocation().z(), ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target), BlockInteraction.BREAK);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        level.explode(caster, target.getLocation().x(), target.getLocation().y(), target.getLocation().z(), ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target), BlockInteraction.BREAK);
        return SpellCastResult.SUCCESS;
    }
}
