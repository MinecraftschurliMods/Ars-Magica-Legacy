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
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class FallingStar extends SpellComponent {
    public FallingStar() {
        super(SpellStat.COLOR, AMSpells.DAMAGE_STAT, AMSpells.RANGE_STAT, AMSpells.SPEED_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        if (level.isClientSide()) return SpellComponentCastResult.pass(spell);
        if (level.dimensionType().hasCeiling()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_FALLING_STAR);
        if (context.isHitResultNullOrMiss()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        LivingEntity caster = context.caster();
        var fallingStar = AMEntities.FALLING_STAR.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        fallingStar.setPos(context.hitResult().getLocation().add(0, AMServerConfig.FALLING_STAR_SPAWN_HEIGHT.get(), 0));
        if (caster != null) {
            fallingStar.setOwner(caster);
        }
        SpellHelper helper = ArsMagicaApi.spellHelper();
        fallingStar.setColor(helper.getColor(modifiers, spell, -1));
        fallingStar.setDeltaMovement(0, -helper.getModifiedStat(AMServerConfig.FALLING_STAR_SPEED.get(), AMSpells.SPEED_STAT, modifiers, context), 0);
        fallingStar.setDamage((float) helper.getModifiedStat(AMServerConfig.FALLING_STAR_DAMAGE.get(), AMSpells.DAMAGE_STAT, modifiers, context));
        fallingStar.setRange((float) helper.getModifiedStat(AMServerConfig.FALLING_STAR_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context));
        level.addFreshEntity(fallingStar);
        return SpellComponentCastResult.success(spell);
    }
}
