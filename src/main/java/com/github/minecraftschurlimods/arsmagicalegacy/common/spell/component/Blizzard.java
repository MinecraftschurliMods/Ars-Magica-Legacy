package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class Blizzard extends AbstractComponent {
    public Blizzard() {
        super(SpellPartStats.DAMAGE, SpellPartStats.DURATION, SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        spawn(spell, caster, level, modifiers, target);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        spawn(spell, caster, level, modifiers, target);
        return SpellCastResult.SUCCESS;
    }

    private static void spawn(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, HitResult target) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Blizzard blizzard = com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Blizzard.create(level);
            var helper = ArsMagicaAPI.get().getSpellHelper();
            blizzard.setPos(target.getLocation());
            blizzard.setDuration((int) helper.getModifiedStat(200, SpellPartStats.DURATION, modifiers, spell, caster, target));
            blizzard.setOwner(caster);
            blizzard.setDamage(helper.getModifiedStat(2, SpellPartStats.DAMAGE, modifiers, spell, caster, target));
            blizzard.setRadius(helper.getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target));
            level.addFreshEntity(blizzard);
        }
    }
}
