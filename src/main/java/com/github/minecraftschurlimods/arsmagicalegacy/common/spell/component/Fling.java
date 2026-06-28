package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Fling extends SpellComponent.CastEntity {
    public Fling() {
        super(AMSpells.SPEED_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.FLING_SPEED.get(), AMSpells.SPEED_STAT, modifiers, context), 0));
        return SpellComponentCastResult.success(context.spell());
    }
}
