package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Frost extends SpellComponent.CastEntity {
    public Frost() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        entity.setData(AMAttachments.FROST, Math.max(entity.getData(AMAttachments.FROST), (int) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.FROST_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context)));
        return SpellComponentCastResult.success(context.spell());
    }
}
