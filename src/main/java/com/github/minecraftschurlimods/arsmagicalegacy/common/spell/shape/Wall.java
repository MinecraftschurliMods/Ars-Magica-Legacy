package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class Wall extends AbstractShape {
    public Wall() {
        super(SpellPartStats.DURATION, SpellPartStats.RANGE, SpellPartStats.SIZE, SpellPartStats.TARGET_NON_SOLID);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            var wall = Objects.requireNonNull(AMEntities.WALL.get().create(level));
            var helper = ArsMagicaAPI.get().getSpellHelper();
            wall.setPos(helper.trace(caster, level, helper.getModifiedStat(2.5f, SpellPartStats.RANGE, modifiers, spell, caster, hit), true, helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0).getLocation());
            wall.setYRot(caster.getYHeadRot());
            wall.setDuration((int) helper.getModifiedStat(200, SpellPartStats.DURATION, modifiers, spell, caster, hit));
            wall.setIndex(index);
            wall.setOwner(caster);
            wall.setRadius(helper.getModifiedStat(1f, SpellPartStats.SIZE, modifiers, spell, caster, hit));
            wall.setSpell(spell);
            level.addFreshEntity(wall);
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean needsPrecedingShape() {
        return true;
    }

    @Override
    public boolean isEndShape() {
        return true;
    }
}
