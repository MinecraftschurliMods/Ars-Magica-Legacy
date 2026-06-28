package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
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
