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

public class Projectile extends AbstractShape {
    public Projectile() {
        super(SpellPartStats.BOUNCE, SpellPartStats.DURATION, SpellPartStats.GRAVITY, SpellPartStats.PIERCING, SpellPartStats.SPEED, SpellPartStats.TARGET_NON_SOLID);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (level.isClientSide()) return SpellCastResult.SUCCESS;
        var projectile = Objects.requireNonNull(AMEntities.PROJECTILE.get().create(level));
        projectile.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
        projectile.setDeltaMovement(caster.getLookAngle());
        var helper = ArsMagicaAPI.get().getSpellHelper();
        if (helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit, index) > 0) {
            projectile.setTargetNonSolid();
        }
        projectile.setBounces((int) helper.getModifiedStat(0, SpellPartStats.BOUNCE, modifiers, spell, caster, hit, index));
        projectile.setDuration((int) helper.getModifiedStat(30, SpellPartStats.DURATION, modifiers, spell, caster, hit, index));
        projectile.setIndex(index);
        projectile.setOwner(caster);
        projectile.setPierces((int) helper.getModifiedStat(0, SpellPartStats.PIERCING, modifiers, spell, caster, hit, index));
        projectile.setGravity(helper.getModifiedStat(0, SpellPartStats.GRAVITY, modifiers, spell, caster, hit, index) * 0.025f);
        projectile.setSpeed(helper.getModifiedStat(0.2f, SpellPartStats.SPEED, modifiers, spell, caster, hit, index));
        projectile.setSpell(spell);
        level.addFreshEntity(projectile);
        return SpellCastResult.SUCCESS;
    }
}
