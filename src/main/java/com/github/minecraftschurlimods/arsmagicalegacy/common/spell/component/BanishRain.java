package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.server.level.ServerLevel;

import java.util.List;
import java.util.Objects;

public class BanishRain extends SpellComponent {
    public BanishRain() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.level() instanceof ServerLevel level) {
            if (level.isRaining()) {
                Objects.requireNonNull(level.getServer()).setWeatherParameters((int) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.BANISH_RAIN_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context), 0, false, false);
                return SpellComponentCastResult.success(spell);
            }
            return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_BANISH_RAIN);
        }
        return SpellComponentCastResult.pass(spell);
    }
}
