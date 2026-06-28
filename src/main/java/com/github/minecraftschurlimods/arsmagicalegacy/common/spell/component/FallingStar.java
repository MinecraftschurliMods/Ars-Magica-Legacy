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
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStat;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
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
