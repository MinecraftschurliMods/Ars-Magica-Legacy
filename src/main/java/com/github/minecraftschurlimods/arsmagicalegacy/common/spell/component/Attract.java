package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Attract extends AbstractComponent {
    public Attract() {
        super(SpellPartStats.RANGE, SpellPartStats.SPEED);
    }

    private static void performAttract(LivingEntity caster, Vec3 targetPos, Entity entity, List<ISpellModifier> modifiers, ISpell spell, HitResult target) {
        Vec3 vec = entity.position();
        double distance = targetPos.distanceTo(vec) + 0.1;
        double factor = 0.9 / (double) ArsMagicaAPI.get().getSpellHelper().getModifiedStat(1, SpellPartStats.SPEED, modifiers, spell, caster, target);
        Vec3 delta = vec.subtract(targetPos);
        entity.setDeltaMovement(entity.getDeltaMovement().x() - delta.x() / factor / distance, entity.getDeltaMovement().y() - delta.y() / factor / distance, entity.getDeltaMovement().z() - delta.z() / factor / distance);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target);
        boolean success = false;
        Entity entity = target.getEntity();
        for (Entity e : level.getEntities(entity, entity.getBoundingBox().inflate(range, range, range))) {
            if (e == caster) continue;
            success = true;
            performAttract(caster, entity.position(), e, modifiers, spell, target);
        }
        return success ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target) * 2;
        boolean success = false;
        for (Entity e : level.getEntities(null, AABB.ofSize(target.getLocation(), range, range, range))) {
            if (e == caster) continue;
            success = true;
            performAttract(caster, target.getLocation(), e, modifiers, spell, target);
        }
        return success ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }
}
