package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Transplace extends SpellComponent.CastEntity {
    public static final Identifier CASTER_PARTICLES = ArsMagicaApi.id("transplace_caster");

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        LivingEntity caster = context.caster();
        Entity entity = hitResult.getEntity();
        Component cancel = AMUtil.cancelTeleport(entity, caster);
        if (cancel != null) return SpellComponentCastResult.failure(spell, cancel);
        if (context.level().isClientSide() || caster == null) return SpellComponentCastResult.pass(spell);
        Vec3 targetPos = entity.position();
        Vec3 casterPos = caster.position();
        entity.teleportTo(casterPos.x(), casterPos.y(), casterPos.z());
        caster.teleportTo(targetPos.x(), targetPos.y(), targetPos.z());
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public void spawnParticles(List<SpellModifier> modifiers, SpellCastContext context) {
        LivingEntity caster = context.caster();
        if (caster == null || !(context.hitResult() instanceof EntityHitResult entityHitResult)) return;
        super.spawnParticles(modifiers, context);
        if (entityHitResult.getEntity() instanceof LivingEntity living) {
            AMClientUtil.spawnParticles(CASTER_PARTICLES, living.position(), ArsMagicaApi.spellHelper().getColor(modifiers, context.spell(), -1), living, living, new EntityHitResult(caster));
        }
    }
}
