package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
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
