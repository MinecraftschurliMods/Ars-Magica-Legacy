package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Knockback extends AbstractComponent {
    public Knockback() {
        super(SpellPartStats.SPEED);
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {
        Entity entity = target.getEntity();
        float velocity = ArsMagicaAPI.get().getSpellHelper().getModifiedStat(1, SpellPartStats.SPEED, modifiers, spell, caster, target);
        Vec3 delta = entity.getDeltaMovement();
        entity.setDeltaMovement(delta.x() + velocity * Math.cos(Math.atan2(entity.getZ() - caster.getZ(), entity.getX() - caster.getX())), delta.y() + velocity * 0.325f, delta.z() + velocity * Math.sin(Math.atan2(entity.getZ() - caster.getZ(), entity.getX() - caster.getX())));
        return SpellCastResult.SUCCESS;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {
        return SpellCastResult.EFFECT_FAILED;
    }
}
