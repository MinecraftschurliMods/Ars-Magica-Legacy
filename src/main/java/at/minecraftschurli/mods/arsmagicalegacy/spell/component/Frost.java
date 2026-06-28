package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
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
