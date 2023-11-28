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
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class FallingStar extends AbstractComponent {
    public FallingStar() {
        super(SpellPartStats.DAMAGE, SpellPartStats.RANGE);
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
            var fallingStar = Objects.requireNonNull(AMEntities.FALLING_STAR.get().create(level));
            var helper = ArsMagicaAPI.get().getSpellHelper();
            fallingStar.setPos(new Vec3(target.getLocation().x(), target.getLocation().y() + 64, target.getLocation().z()));
            fallingStar.setOwner(caster);
            fallingStar.setDamage(helper.getModifiedStat(6, SpellPartStats.DAMAGE, modifiers, spell, caster, target, index));
            fallingStar.setRadius(helper.getModifiedStat(6, SpellPartStats.RANGE, modifiers, spell, caster, target, index));
            level.addFreshEntity(fallingStar);
        }
    }
}
