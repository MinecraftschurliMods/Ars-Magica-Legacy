package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.GlobalVec3;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Recall extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        GlobalVec3 position = spell.dataComponents().grammar().get(AMDataComponents.SPELL_RECALL_POSITION.get());
        Entity entity = hitResult.getEntity();
        if (position == null) {
            return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_RECALL);
        } else if (position.dimension() == context.level().dimension()) {
            Vec3 vec3 = position.position();
            entity.teleportTo(vec3.x(), vec3.y(), vec3.z());
            return SpellComponentCastResult.success(spell);
        }
        return SpellComponentCastResult.pass(spell);
    }

    @Override
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_RECALL_POSITION.get();
    }
}
