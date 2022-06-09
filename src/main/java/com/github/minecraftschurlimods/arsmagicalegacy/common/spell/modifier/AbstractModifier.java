package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;

import java.util.Objects;

public abstract class AbstractModifier implements ISpellModifier {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ISpellModifier mod && Objects.equals(getId(), mod.getId());
    }
}
