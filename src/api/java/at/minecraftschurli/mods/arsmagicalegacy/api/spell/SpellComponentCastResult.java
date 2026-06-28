package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

/// Represents the result of an individual [SpellPart] being cast. The result is then used to accordingly populate a [SpellCastResult].
/// Get an instance via [SpellComponentCastResult#success(Spell)], [SpellComponentCastResult#pass(Spell)] or [SpellComponentCastResult#failure(Spell, Component)].
public final class SpellComponentCastResult {
    private final Type type;
    private final Spell spell;
    @Nullable
    private final Component message;

    private SpellComponentCastResult(Type type, Spell spell, @Nullable Component message) {
        this.type = type;
        this.spell = spell;
        this.message = message;
    }

    /// @return Whether the result is considered successful. A successful result triggers behavior such as mana consumption or affinity awarding.
    public boolean isSuccess() {
        return type == Type.SUCCESS;
    }

    /// @return Whether the result is considered failing.
    public boolean isFailure() {
        return type == Type.FAILURE;
    }

    /// @return The [Spell] contained in the result.
    public Spell getSpell() {
        return spell;
    }

    /// @return The error message. This will be a non-null value iff [SpellComponentCastResult#isFailure()] returns true.
    @Nullable
    public Component getMessage() {
        return message;
    }

    /// @return A new [SpellCastResult] marked as successful. A successful result triggers behavior such as mana consumption or affinity awarding.
    public static SpellComponentCastResult success(Spell spell) {
        return new SpellComponentCastResult(Type.SUCCESS, spell, null);
    }

    /// @return A new [SpellCastResult] marked as neither successful nor failing. This should be used e.g. when only running code on one side.
    public static SpellComponentCastResult pass(Spell spell) {
        return new SpellComponentCastResult(Type.PASS, spell, null);
    }

    /// @param message The error message to set.
    /// @return A new [SpellCastResult] marked as failing and with the given error message set.
    public static SpellComponentCastResult failure(Spell spell, Component message) {
        return new SpellComponentCastResult(Type.FAILURE, spell, message);
    }

    private enum Type {
        SUCCESS,
        PASS,
        FAILURE
    }
}
