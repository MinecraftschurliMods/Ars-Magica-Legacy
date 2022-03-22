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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Attract extends AbstractComponent {
    public Attract() {
        super(SpellPartStats.RANGE, SpellPartStats.SPEED);
    }

    private static SpellCastResult performAttract(@Nullable Entity targetEntity, AABB aabb, LivingEntity caster, Vec3 targetPos, List<ISpellModifier> modifiers, ISpell spell, HitResult target) {
        boolean success = false;
        for (Entity e : caster.level.getEntities(targetEntity, aabb)) {
            if (e == caster) continue;
            success = true;
            Vec3 vec = e.position();
            double distance = targetPos.distanceTo(vec) + 0.1;
            double factor = 0.9 / (double) ArsMagicaAPI.get().getSpellHelper().getModifiedStat(1, SpellPartStats.SPEED, modifiers, spell, caster, target);
            Vec3 delta = vec.subtract(targetPos);
            e.setDeltaMovement(e.getDeltaMovement().x() - delta.x() / factor / distance, e.getDeltaMovement().y() - delta.y() / factor / distance, e.getDeltaMovement().z() - delta.z() / factor / distance);
        }
        return success ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target) * 2;
        Entity entity = target.getEntity();
        return performAttract(entity, entity.getBoundingBox().inflate(range, range, range), caster, target.getLocation(), modifiers, spell, target);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        float range = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(2, SpellPartStats.RANGE, modifiers, spell, caster, target) * 2;
        return performAttract(null, AABB.ofSize(target.getLocation(), range, range, range), caster, target.getLocation(), modifiers, spell, target);
    }
}
