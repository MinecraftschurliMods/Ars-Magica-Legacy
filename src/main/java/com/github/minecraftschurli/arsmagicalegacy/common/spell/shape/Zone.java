package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ZoneEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Zone extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            ZoneEntity zone = ZoneEntity.create(level);
            zone.setPos(caster.getX(), caster.getY(), caster.getZ());
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.TARGET_NON_SOLID.getId()) > 0) {
                zone.setTargetNonSolid();
            }
            zone.setDuration(200 + 100 * ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            zone.setIndex(index);
            zone.setOwner(caster);
            zone.setRadius(1.4f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            zone.setStack(caster.getMainHandItem());
            level.addFreshEntity(zone);
        }
        return SpellCastResult.SUCCESS;
    }
}
