package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.List;

/// Helper for spell-related operations.
public interface SpellHelper {
    /// Casts the given [Spell].
    ///
    /// @param spell   The [Spell] to cast.
    /// @param level   The [Level] the [Spell] is cast in.
    /// @param caster  The [LivingEntity] casting the [Spell].
    /// @param consume Whether to consume mana and burnout or not.
    /// @param awardXp Whether to award xp or not.
    /// @return `null` if the cast was successful, or an error message if not.
    SpellCastResult cast(Spell spell, Level level, @Nullable LivingEntity caster, boolean consume, boolean awardXp);

    /// Casts the given [Spell]'s primary shape. Note that [SpellCastContext#directEntity()] and [SpellCastContext#hitResult()] are guaranteed to return null here.
    ///
    /// @param context The [SpellCastContext] to use.
    /// @return A [SpellCastResult] representing the result of the cast.
    /// @see PrimarySpellShape#cast(List, SpellCastContext)
    SpellCastResult castPrimary(SpellCastContext context);

    /// Casts the given [Spell]'s secondary shape.
    ///
    /// @param context The [SpellCastContext] to use.
    /// @return A [SpellCastResult] representing the result of the cast.
    /// @see SecondarySpellShape#cast(List, SpellCastContext)
    SpellCastResult castSecondary(SpellCastContext context);

    /// Casts the given [Spell]'s grammar.
    ///
    /// @param context The [SpellCastContext] to use.
    /// @return A [SpellCastResult] representing the result of the cast.
    SpellCastResult castGrammar(SpellCastContext context);

    /// If present, casts the given [Spell]'s secondary shape. Otherwise, casts the given [Spell]'s grammar.
    ///
    /// @param context The [SpellCastContext] to use.
    /// @return A [SpellCastResult] representing the result of the cast.
    SpellCastResult castSecondaryOrGrammar(SpellCastContext context);

    /// Calculates the modifier-changed value from the base value.
    ///
    /// @param base      The base value to use.
    /// @param stat      The [SpellStat] that is modified.
    /// @param modifiers The [SpellModifier]s to check.
    /// @param context   The [SpellCastContext] to use.
    /// @return A modifier-changed value.
    double getModifiedStat(double base, SpellStat stat, List<SpellModifier> modifiers, SpellCastContext context);

    /// @param modifiers       The [SpellModifier]s to check.
    /// @param spell           The [Spell] to cast.
    /// @param shapeGroupIndex The index of the shape group to query the data components for. Pass a negative to use the grammar's data components instead.
    /// @return The color of the [Spell]'s visual effects.
    int getColor(List<SpellModifier> modifiers, Spell spell, int shapeGroupIndex);

    /// @param part The [SpellPart] to query.
    /// @return A list of [SpellModifier] that can modify the given part.
    List<SpellModifier> getModifiers(SpellPart part);

    /// Sets a contingency [Spell].
    ///
    /// @param entity      The [LivingEntity] to set the contingency [Spell] on.
    /// @param contingency The name of the contingency to trigger the contingency [Spell] for.
    /// @param spell       The [Spell] to cast when the contingency is triggered.
    void setContingency(LivingEntity entity, Identifier contingency, Spell spell);

    /// Triggers a contingency.
    ///
    /// @param entity      The [LivingEntity] to trigger the contingency for.
    /// @param contingency The name of the contingency to trigger.
    void triggerContingency(LivingEntity entity, Identifier contingency);

    /// @param toolTier The tool tier to get the incorrect block tag for.
    /// @return A tag specifying which blocks are not breakable by the given tool tier.
    TagKey<Block> getIncorrectTagForToolTier(int toolTier);

    /// @param entity The [LivingEntity] to get the max summons for.
    /// @return The maximum amount of summoned minions for the given [LivingEntity].
    int getMaxSummons(LivingEntity entity);

    /// @return The mana to burnout conversion ratio, used in spell cost calculation.
    double getManaToBurnoutRatio();

    /// Calculates a [Spell]'s recipe.
    ///
    /// @param spell          The [Spell] to calculate the recipe for.
    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The recipe for the [Spell].
    List<SpellIngredient> getRecipe(Spell spell, RegistryAccess registryAccess);

    /// Calculates a [Spell]'s recipe and combines the ingredients where possible.
    ///
    /// @param spell          The [Spell] to calculate the recipe for.
    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The recipe for the [Spell].
    List<SpellIngredient> getFlatRecipe(Spell spell, RegistryAccess registryAccess);

    /// On the client, spawns particles for the given [SpellPart]. On the server, does nothing.
    ///
    /// @param part      The id of the spell part to spawn the particles for.
    /// @param modifiers The [SpellModifier]s to consider.
    /// @param context   The [SpellCastContext] to use.
    void spawnParticles(Identifier part, List<SpellModifier> modifiers, SpellCastContext context);
}
