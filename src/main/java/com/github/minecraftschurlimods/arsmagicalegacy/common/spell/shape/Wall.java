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

public class Wall extends AbstractShape {
    public Wall() {
        super(SpellPartStats.RANGE,
              SpellPartStats.TARGET_NON_SOLID,
              SpellPartStats.SIZE,
              SpellPartStats.DURATION);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall wall = com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall.create(level);
            float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2.5f, SpellPartStats.RANGE, modifiers, spell, caster, hit);
            boolean tns = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0;
            float radius = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(1f, SpellPartStats.SIZE, modifiers, spell, caster, hit);
            int duration = (int)ArsMagicaAPI.get().getSpellHelper().getModifiedStat(200, SpellPartStats.DURATION, modifiers, spell, caster, hit);
            wall.setPos(ArsMagicaAPI.get().getSpellHelper().trace(caster, level, range, true, tns).getLocation());
            wall.setYRot(caster.getYHeadRot());
            wall.setDuration(duration);
            wall.setIndex(index);
            wall.setOwner(caster);
            wall.setRadius(radius);
            wall.setStack(caster.getMainHandItem());
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
