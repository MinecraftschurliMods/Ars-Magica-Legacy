package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WallEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wall extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            WallEntity wall = WallEntity.create(level);
            wall.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
            wall.setXRot((float) caster.getLookAngle().x());
            wall.setYRot((float) caster.getLookAngle().y());
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.TARGET_NON_SOLID.getId()) > 0) {
                wall.setTargetNonSolid();
            }
            wall.setDuration(200 + 100 * ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            wall.setIndex(index);
            wall.setOwner(caster);
            wall.setRadius(1.4f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            wall.setStack(caster.getMainHandItem());
            level.addFreshEntity(wall);
        }
        return SpellCastResult.SUCCESS;
    }
}
