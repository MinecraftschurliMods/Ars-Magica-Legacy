package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Attract extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        int range = 2 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId());
        boolean success = false;
        Entity entity = target.getEntity();
        for (Entity e : level.getEntities(entity, entity.getBoundingBox().inflate(range, range, range))) {
            if (e == caster) continue;
            success = true;
            performAttract(entity.position(), e, modifiers);
        }
        return success ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        int range = 2 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId());
        boolean success = false;
        for (Entity e : level.getEntities(null, new AABB(target.getBlockPos().getX() - range, target.getBlockPos().getY() - range, target.getBlockPos().getZ() - range, target.getBlockPos().getX() + range, target.getBlockPos().getY() + range, target.getBlockPos().getZ() + range))) {
            if (e == caster) continue;
            success = true;
            performAttract(target.getLocation(), e, modifiers);
        }
        return success ? SpellCastResult.SUCCESS : SpellCastResult.EFFECT_FAILED;
    }

    private static void performAttract(Vec3 caster, Entity entity, List<ISpellModifier> modifiers) {
        Vec3 vec = entity.position();
        double distance = caster.distanceTo(vec) + 0.1;
        double factor = 0.9 / (double) (1 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.VELOCITY.getId()));
        Vec3 delta = vec.subtract(caster);
        entity.setDeltaMovement(entity.getDeltaMovement().x() - delta.x() / factor / distance, entity.getDeltaMovement().y() - delta.y() / factor / distance, entity.getDeltaMovement().z() - delta.z() / factor / distance);
    }
}
