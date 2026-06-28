package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

/// Represents the result of a [Spell] cast. Holds the [Spell] itself, whether the [Spell] cast was successful, and an error message if one was set.
public final class SpellCastResult {
    private boolean success = false;
    private Spell spell;
    @Nullable
    private Component message = null;

    /// @param spell The [Spell] to set on the result.
    public SpellCastResult(Spell spell) {
        this.spell = spell;
    }

    /// @return Whether the [Spell] cast was successful. A successful result triggers behavior such as mana consumption or affinity awarding.
    public boolean isSuccess() {
        return success;
    }

    /// Marks the [Spell] cast as successful. A successful result triggers behavior such as mana consumption or affinity awarding. This is not reversible.
    ///
    /// @return This object, for chaining.
    public SpellCastResult setSuccess() {
        success = true;
        return this;
    }

    /// @return The [Spell] of the result.
    public Spell getSpell() {
        return spell;
    }

    /// @param spell The [Spell] to set on the result.
    /// @return This object, for chaining.
    public SpellCastResult setSpell(Spell spell) {
        this.spell = spell;
        return this;
    }

    /// @return The error message. May be null, which indicates that no error message was recorded.
    @Nullable
    public Component getMessage() {
        return message;
    }

    /// Sets the given error message if none was recorded yet. This behavior is to ensure that the earliest error message is returned.
    ///
    /// @param message The error message to set.
    /// @return This object, for chaining.
    public SpellCastResult setMessage(Component message) {
        if (this.message == null) {
            this.message = message;
        }
        return this;
    }
}
