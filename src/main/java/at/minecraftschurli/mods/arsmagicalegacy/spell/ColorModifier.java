package at.minecraftschurli.mods.arsmagicalegacy.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStatModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.component.DataComponentType;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class ColorModifier extends SpellModifier {
    public ColorModifier() {
        super(Map.of(SpellStat.COLOR, SpellStatModifier.NOOP));
    }

    @Override
    @Nullable
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_COLOR.get();
    }
}
