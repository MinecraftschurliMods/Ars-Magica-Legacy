package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Attract extends SpellComponent {
    public Attract() {
        super(AMSpells.RANGE_STAT, AMSpells.SPEED_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.isHitResultNullOrMiss()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        double range = helper.getModifiedStat(AMServerConfig.ATTRACT_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context);
        double speed = helper.getModifiedStat(AMServerConfig.ATTRACT_SPEED.get(), AMSpells.SPEED_STAT, modifiers, context);
        LivingEntity caster = context.caster();
        Entity directEntity = context.directEntity();
        HitResult hitResult = context.hitResult();
        if (hitResult == null) return SpellComponentCastResult.pass(spell);
        Entity target = hitResult instanceof EntityHitResult result ? result.getEntity() : null;
        Vec3 targetPos = hitResult.getLocation();
        for (Entity entity : context.level().getEntities(target, target == null ? AABB.ofSize(targetPos, range, range, range) : target.getBoundingBox().inflate(range))) {
            if (entity == caster || entity == directEntity) continue;
            Vec3 vec = entity.position();
            entity.setDeltaMovement(entity.getDeltaMovement().add(targetPos.subtract(vec).scale(speed / (targetPos.distanceTo(vec) * 0.9 + 0.09))));
        }
        return SpellComponentCastResult.success(spell);
    }
}
