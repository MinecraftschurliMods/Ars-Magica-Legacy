package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Telekinesis extends AbstractComponent {
    private static SpellCastResult performTelekinesis(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, HitResult target) { // TODO
        boolean performed = false;
        double dist = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(8, SpellPartStats.RANGE, modifiers, spell, caster, target);
        for (Entity e : level.getEntities(caster, new AABB(caster.getX() - dist, caster.getY() - dist, caster.getZ() - dist, caster.getX() + dist, caster.getY() + dist, caster.getZ() + dist))) {
            if (!(e instanceof ItemEntity || e instanceof ExperienceOrb)) continue;
            if (e.tickCount < 20) continue;
            Vec3 vec = e.position().subtract(caster.position()).normalize();
            Vec3 delta = e.getDeltaMovement();
            double x = delta.x() + vec.x() * 0.1;
            double y = delta.y() + vec.y() * 0.1;
            double z = delta.z() + vec.z() * 0.1;
            if (Math.abs(x) > Math.abs(vec.x())) {
                x = vec.x();
            }
            if (Math.abs(y) > Math.abs(vec.y())) {
                y = vec.y();
            }
            if (Math.abs(z) > Math.abs(vec.z())) {
                z = vec.z();
            }
            e.setDeltaMovement(x, y, z);
            performed = true;
        }
        return performed ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        return performTelekinesis(spell, caster, level, modifiers, target);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return performTelekinesis(spell, caster, level, modifiers, target);
    }
}
