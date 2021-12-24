package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wall extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall wall = new com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall(AMEntities.WALL.get(), level);
            wall.setPos(ArsMagicaAPI.get().getSpellHelper().trace(caster, level, 2.5f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId()), true, ArsMagicaAPI.get().getSpellHelper().isModifierPresent(modifiers, AMSpellParts.TARGET_NON_SOLID.getId())).getLocation());
            wall.setYRot(caster.getYHeadRot());
            wall.setDuration(200 + 100 * ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            wall.setIndex(index);
            wall.setOwner(caster);
            wall.setRadius(1f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId()));
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