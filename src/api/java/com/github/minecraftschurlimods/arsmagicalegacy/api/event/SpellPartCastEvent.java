package com.github.minecraftschurlimods.arsmagicalegacy.api.event;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrimarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SecondarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;

import java.util.List;

/// Event that is fired when a particular spell part is cast. Has type-specific sub events.
///
/// In order to perform additional functionality when the spell as a whole is cast, use [SpellCastEvent].
///
/// This event is not cancelable. This event is fired on the main event bus.
@SuppressWarnings("unused")
public abstract class SpellPartCastEvent<T extends SpellPart> extends SpellEvent {
    private final T spellPart;
    private final List<SpellModifier> modifiers;
    private final SpellCastContext context;

    /// @param spellPart The [SpellPart] being cast.
    /// @param modifiers The list of [SpellModifier]s used by the spell part.
    /// @param context   The [SpellCastContext] used by the spell cast.
    @SuppressWarnings("DataFlowIssue")
    public SpellPartCastEvent(T spellPart, List<SpellModifier> modifiers, SpellCastContext context) {
        super(context.caster(), context.spell());
        this.spellPart = spellPart;
        this.modifiers = modifiers;
        this.context = context;
    }

    /// @return The [SpellPart] being cast.
    public T getSpellPart() {
        return spellPart;
    }

    /// @return The list of [SpellModifier]s used by the spell part.
    public List<SpellModifier> getModifiers() {
        return modifiers;
    }

    /// @return The [SpellCastContext] used by the spell cast.
    public SpellCastContext getContext() {
        return context;
    }

    /// Event that is fired when a [PrimarySpellShape] is cast.
    public static class PrimaryShape extends SpellPartCastEvent<PrimarySpellShape> {
        /// @param shape     The [PrimarySpellShape] being cast.
        /// @param modifiers The list of [SpellModifier]s used by the spell part.
        /// @param context   The [SpellCastContext] used by the spell cast.
        public PrimaryShape(PrimarySpellShape shape, List<SpellModifier> modifiers, SpellCastContext context) {
            super(shape, modifiers, context);
        }
    }

    /// Event that is fired when a [SecondarySpellShape] is cast.
    public static class SecondaryShape extends SpellPartCastEvent<SecondarySpellShape> {
        /// @param shape     The [SecondarySpellShape] being cast.
        /// @param modifiers The list of [SpellModifier]s used by the spell part.
        /// @param context   The [SpellCastContext] used by the spell cast.
        public SecondaryShape(SecondarySpellShape shape, List<SpellModifier> modifiers, SpellCastContext context) {
            super(shape, modifiers, context);
        }
    }

    /// Event that is fired when a [SpellComponent] is cast.
    public static class Component extends SpellPartCastEvent<SpellComponent> {
        /// @param component The [SpellComponent] being cast.
        /// @param modifiers The list of [SpellModifier]s used by the spell part.
        /// @param context   The [SpellCastContext] used by the spell cast.
        public Component(SpellComponent component, List<SpellModifier> modifiers, SpellCastContext context) {
            super(component, modifiers, context);
        }
    }
}
