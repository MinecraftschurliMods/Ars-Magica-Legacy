package com.github.minecraftschurli.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.SpellProjectile;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.SpellHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Projectile extends AbstractShape {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            SpellProjectile projectile = new SpellProjectile(level);
            projectile.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
            projectile.setDeltaMovement(caster.getLookAngle());
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.TARGET_NON_SOLID.getId()) > 0) {
                projectile.setTargetNonSolid();
            }
            if (ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.GRAVITY.getId()) > 0) {
                projectile.setGravity(true);
            }
            projectile.setBounces(ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.BOUNCE.getId()));
            projectile.setIndex(index);
            projectile.setPierces(ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.PIERCING.getId()));
            projectile.setOwner(caster);
            projectile.setIcon(ArsMagicaAPI.MOD_ID + ":textures/item/affinity_essence_arcane.png");
            projectile.setStack(caster.getMainHandItem());
            level.addFreshEntity(projectile);
        }
        return SpellCastResult.SUCCESS;
    }
}
