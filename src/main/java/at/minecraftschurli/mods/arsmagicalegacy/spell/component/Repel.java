package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Repel extends SpellComponent {
    public Repel() {
        super(AMSpells.RANGE_STAT, AMSpells.SPEED_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.isHitResultNullOrMiss()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        double range = helper.getModifiedStat(AMServerConfig.REPEL_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context);
        double speed = helper.getModifiedStat(AMServerConfig.REPEL_SPEED.get(), AMSpells.SPEED_STAT, modifiers, context);
        LivingEntity caster = context.caster();
        Entity directEntity = context.directEntity();
        HitResult hitResult = context.hitResult();
        if (hitResult == null) return SpellComponentCastResult.pass(spell);
        Entity target = hitResult instanceof EntityHitResult result ? result.getEntity() : null;
        Vec3 targetPos = hitResult.getLocation();
        for (Entity entity : context.level().getEntities(target, target == null ? AABB.ofSize(targetPos, range, range, range) : target.getBoundingBox().inflate(range))) {
            if (entity == caster || entity == directEntity) continue;
            Vec3 vec = entity.position();
            entity.setDeltaMovement(entity.getDeltaMovement().add(vec.subtract(targetPos).scale(speed / (targetPos.distanceTo(vec) * 0.9 + 0.09))));
        }
        return SpellComponentCastResult.success(spell);
    }
}
