package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.Objects;

public class FireRain extends AbstractComponent {
    public FireRain() {
        super(SpellPartStats.DAMAGE, SpellPartStats.DURATION, SpellPartStats.RANGE);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        spawn(spell, caster, level, modifiers, target, index);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        spawn(spell, caster, level, modifiers, target, index);
        return SpellCastResult.SUCCESS;
    }

    private static void spawn(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, HitResult target, int index) {
        if (!level.isClientSide()) {
            var fireRain = Objects.requireNonNull(AMEntities.FIRE_RAIN.get().create(level));
            var helper = ArsMagicaAPI.get().getSpellHelper();
            fireRain.setPos(target.getLocation());
            fireRain.setColor(helper.getColor(modifiers, spell, caster, index, -1));
            fireRain.setDuration((int) helper.getModifiedStat(200, SpellPartStats.DURATION, modifiers, spell, caster, target, index));
            fireRain.setOwner(caster);
            fireRain.setDamage(helper.getModifiedStat(2, SpellPartStats.DAMAGE, modifiers, spell, caster, target, index));
            fireRain.setRadius(helper.getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target, index));
            level.addFreshEntity(fireRain);
        }
    }
}
