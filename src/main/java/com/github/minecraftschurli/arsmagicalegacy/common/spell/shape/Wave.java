package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wave extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurli.arsmagicalegacy.common.entity.Wave wave = new com.github.minecraftschurli.arsmagicalegacy.common.entity.Wave(AMEntities.WAVE.get(), level);
            wave.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
            wave.setDeltaMovement(caster.getLookAngle());
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.TARGET_NON_SOLID.getId()) > 0) {
                wave.setTargetNonSolid();
            }
            wave.setDuration(200 + 100 * ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            wave.setIndex(index);
            wave.setOwner(caster);
            wave.setRadius(1.4f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            wave.setSpeed(1f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.VELOCITY.getId()) * 0.2f);
            wave.setStack(caster.getMainHandItem());
            level.addFreshEntity(wave);
        }
        return SpellCastResult.SUCCESS;
    }
}
