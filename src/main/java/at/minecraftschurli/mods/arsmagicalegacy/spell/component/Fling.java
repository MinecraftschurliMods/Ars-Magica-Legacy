package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
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
