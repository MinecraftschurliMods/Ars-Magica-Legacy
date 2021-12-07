package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Projectile extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurli.arsmagicalegacy.common.entity.Projectile projectile = com.github.minecraftschurli.arsmagicalegacy.common.entity.Projectile.create(level);
            projectile.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
            projectile.setDeltaMovement(caster.getLookAngle());
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.TARGET_NON_SOLID.getId()) > 0) {
                projectile.setTargetNonSolid();
            }
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.GRAVITY.getId()) > 0) {
                projectile.setGravity(true);
            }
            projectile.setBounces(ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.BOUNCE.getId()));
            projectile.setDuration(40 + 20 * ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.DURATION.getId()));
            projectile.setIndex(index);
            projectile.setPierces(ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.PIERCING.getId()));
            projectile.setOwner(caster);
            projectile.setSpeed(1f + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.VELOCITY.getId()) * 0.2f);
            IAffinity affinity = spell.primaryAffinity();
            projectile.setIcon(affinity == AMAffinities.NONE.get() ? new ItemStack(AMItems.BLANK_RUNE.get()) : ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(affinity));
            projectile.setStack(caster.getMainHandItem());
            level.addFreshEntity(projectile);
        }
        return SpellCastResult.SUCCESS;
    }
}
