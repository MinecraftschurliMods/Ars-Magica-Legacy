package com.github.minecraftschurli.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Repel extends AbstractComponent {
    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        int range = 2 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.RANGE.getId());
        Entity entity = target.getEntity();
        for (Entity e : level.getEntities(entity, entity.getBoundingBox().inflate(range, range, range))) {
            if (e instanceof LivingEntity) {
                performRepel(entity, e, modifiers);
            }
        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }

    private static void performRepel(Entity caster, Entity entity, List<ISpellModifier> modifiers) {
        Vec3 casterPos = caster.position();
        Vec3 entityPos = entity.position();
        double distance = casterPos.distanceTo(entityPos) + 0.1;
        double factor = 0.9 / (double) (1 + ArsMagicaAPI.get().getSpellHelper().countModifiers(modifiers, AMSpellParts.VELOCITY.getId()));
        Vec3 delta = entityPos.subtract(casterPos);
        entity.setDeltaMovement(entity.getDeltaMovement().x() + delta.x() / factor / distance, entity.getDeltaMovement().y() + delta.y() / factor / distance, entity.getDeltaMovement().z() + delta.z() / factor / distance);
    }
}
