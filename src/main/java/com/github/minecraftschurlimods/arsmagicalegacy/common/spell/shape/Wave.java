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

public class Wave extends AbstractShape {
    public Wave() {
        super(SpellPartStats.TARGET_NON_SOLID,
                SpellPartStats.DURATION,
                SpellPartStats.GRAVITY,
                SpellPartStats.SIZE,
                SpellPartStats.SPEED);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp) {
        if (!level.isClientSide()) {
            com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wave wave = com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wave.create(level);
            wave.setPos(caster.getX(), caster.getEyeY(), caster.getZ());
            wave.setDeltaMovement(caster.getLookAngle());
            var helper = ArsMagicaAPI.get().getSpellHelper();
            boolean tns = helper.getModifiedStat(0, SpellPartStats.TARGET_NON_SOLID, modifiers, spell, caster, hit) > 0;
            int duration = (int) helper.getModifiedStat(80, SpellPartStats.DURATION, modifiers, spell, caster, hit);
            float gravity = helper.getModifiedStat(0, SpellPartStats.GRAVITY, modifiers, spell, caster, hit) * 0.025f;
            float radius = helper.getModifiedStat(1, SpellPartStats.SIZE, modifiers, spell, caster, hit);
            float speed = 1 + helper.getModifiedStat(0.2f, SpellPartStats.SPEED, modifiers, spell, caster, hit);
            if (tns) {
                wave.setTargetNonSolid();
            }
            wave.setDuration(duration);
            wave.setIndex(index);
            wave.setOwner(caster);
            wave.setGravity(gravity);
            wave.setRadius(radius);
            wave.setSpeed(speed);
            wave.setStack(caster.getMainHandItem());
            level.addFreshEntity(wave);
        }
        return SpellCastResult.SUCCESS;
    }
}
