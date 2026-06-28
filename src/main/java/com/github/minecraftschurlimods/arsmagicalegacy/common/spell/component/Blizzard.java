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

public class Blizzard extends SpellComponent {
    public Blizzard() {
        super(SpellStat.COLOR, AMSpells.DAMAGE_STAT, AMSpells.DURATION_STAT, AMSpells.RANGE_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.isHitResultNullOrMiss()) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        Level level = context.level();
        if (level.isClientSide()) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        var blizzard = AMEntities.BLIZZARD.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        blizzard.setPos(context.hitResult().getLocation());
        if (caster != null) {
            blizzard.setOwner(caster);
        }
        SpellHelper helper = ArsMagicaApi.spellHelper();
        blizzard.setColor(helper.getColor(modifiers, spell, -1));
        blizzard.setDuration((int) helper.getModifiedStat(AMServerConfig.BLIZZARD_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        blizzard.setFrostDuration((int) helper.getModifiedStat(AMServerConfig.BLIZZARD_FROST_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        blizzard.setDamage((float) helper.getModifiedStat(AMServerConfig.BLIZZARD_DAMAGE.get(), AMSpells.DAMAGE_STAT, modifiers, context));
        blizzard.setRange((float) helper.getModifiedStat(AMServerConfig.BLIZZARD_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context));
        level.addFreshEntity(blizzard);
        return SpellComponentCastResult.success(spell);
    }
}
