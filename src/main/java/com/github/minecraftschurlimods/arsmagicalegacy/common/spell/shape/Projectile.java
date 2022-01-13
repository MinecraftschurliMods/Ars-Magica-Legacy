package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Projectile extends AbstractShape {
    public Projectile() {
        super(SpellPartStats.TARGET_NON_SOLID,
              SpellPartStats.BOUNCE,
              SpellPartStats.DURATION,
              SpellPartStats.PIERCING,
              SpellPartStats.GRAVITY,
              SpellPartStats.SPEED);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Projectile projectile = com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Projectile.create(level);
            projectile.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
            projectile.setDeltaMovement(caster.getLookAngle());
            ISpellHelper spellHelper = ArsMagicaAPI.get().getSpellHelper();
            boolean tns = spellHelper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0;
            int bounces = (int)spellHelper.getModifiedStat(0, SpellPartStats.BOUNCE, modifiers, spell, caster, hit);
            int duration = (int)spellHelper.getModifiedStat(30, SpellPartStats.DURATION, modifiers, spell, caster, hit);
            int pierces = (int)spellHelper.getModifiedStat(0, SpellPartStats.PIERCING, modifiers, spell, caster, hit);
            float gravity = spellHelper.getModifiedStat(0, SpellPartStats.GRAVITY, modifiers, spell, caster, hit) * 0.025f;
            float speed = spellHelper.getModifiedStat(0.2f, SpellPartStats.SPEED, modifiers, spell, caster, hit);
            if (tns) {
                projectile.setTargetNonSolid();
            }
            projectile.setBounces(bounces);
            projectile.setDuration(duration);
            projectile.setIndex(index);
            projectile.setPierces(pierces);
            projectile.setOwner(caster);
            projectile.setGravity(gravity);
            projectile.setSpeed(speed);
            IAffinity affinity = spell.primaryAffinity();
            projectile.setIcon(affinity == AMAffinities.NONE.get() ? new ItemStack(AMItems.BLANK_RUNE.get()) : ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(affinity));
            projectile.setStack(caster.getMainHandItem());
            level.addFreshEntity(projectile);
        }
        return SpellCastResult.SUCCESS;
    }
}
