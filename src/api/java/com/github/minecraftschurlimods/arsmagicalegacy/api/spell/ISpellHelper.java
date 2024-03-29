package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ISpellHelper {
    /**
     * @param stack The stack to get the spell for.
     * @return An optional containing the spell, or an empty optional if the given stack does not have a spell.
     */
    ISpell getSpell(ItemStack stack);

    /**
     * Sets the given spell to the given stack.
     *
     * @param stack The stack to set the spell on.
     * @param spell The spell to set.
     */
    void setSpell(ItemStack stack, ISpell spell);

    /**
     * Validates the given spell. This checks for non-emptiness of the spell stack, the shape groups being correct, and correct location of modifiers (if present).
     *
     * @param spell The spell to validate.
     * @return Whether the given spell is valid or not.
     */
    boolean isValidSpell(ISpell spell);

    /**
     * Returns the item stack containing the spell the given entity is currently using, taking spell books into consideration.
     * Returns empty if the entity is not currently holding anything that can be interpreted as a spell.
     * This is needed e.g. when using a spell's shift-right click action.
     *
     * @param entity The entity to get the spell item stack from.
     * @return The item stack containing the spell the given entity is currently using.
     */
    ItemStack getSpellItemStackFromEntity(LivingEntity entity);

    /**
     * Returns the item stack the given entity is currently holding in the given hand if said stack is a spell, taking spell books into consideration.
     * Returns empty if the stack is not actually anything that can be interpreted as a spell.
     *
     * @param entity The entity to get the spell item stack from.
     * @param hand   The hand to get the spell item stack from.
     * @return The spell item stack the given entity is currently holding in the given hand.
     */
    ItemStack getSpellItemStackInHand(LivingEntity entity, InteractionHand hand);

    /**
     * @param stack The stack to get the spell name for.
     * @return An optional containing the spell name, or an empty optional if the given stack does not have a spell name.
     */
    Optional<Component> getSpellName(ItemStack stack);

    /**
     * Sets the given name to the given stack.
     *
     * @param stack The stack to set the name on.
     * @param name  The name to set.
     */
    void setSpellName(ItemStack stack, String name);

    /**
     * Sets the given name to the given stack.
     *
     * @param stack The stack to set the name on.
     * @param name  The name to set.
     */
    void setSpellName(ItemStack stack, Component name);

    /**
     * @param stack The stack to get the spell icon for.
     * @return An optional containing the spell icon id, or an empty optional if the given stack does not have a spell icon.
     */
    Optional<ResourceLocation> getSpellIcon(ItemStack stack);

    /**
     * Sets the given icon to the given stack.
     *
     * @param stack The stack to set the icon on.
     * @param icon  The icon to set.
     */
    void setSpellIcon(ItemStack stack, ResourceLocation icon);

    /**
     * @param entity   The entity to check on.
     * @param reagents The reagents to search for.
     * @return Whether the given entity has the required reagents in their inventory or not.
     */
    boolean hasReagents(LivingEntity entity, Collection<ItemFilter> reagents);

    /**
     * Consumes the reagents.
     *
     * @param entity   The entity to consume the ingredients from.
     * @param reagents The reagents to consume.
     */
    void consumeReagents(LivingEntity entity, Collection<ItemFilter> reagents);

    /**
     * Registers a spell particle spawner for a given component. This is only required on the client and may thus be left untouched on the server.
     *
     * @param component       The component to register a particle spawner for.
     * @param particleSpawner The particle spawner to register.
     */
    void registerParticleSpawner(ISpellComponent component, ISpellParticleSpawner particleSpawner);

    /**
     * Spawns spell particles for the given component. This should only ever be called on the client!
     *
     * @param component The component to spawn spell particles for.
     * @param spell     The spell that spawned the particles.
     * @param caster    The entity that cast the spell.
     * @param hit       The hit result to use.
     * @param color     The color to use. May be -1, which indicates that no color should be used.
     */
    void spawnParticles(ISpellComponent component, ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color);

    /**
     * Performs a ray trace and returns the entity the given entity is currently looking at.
     *
     * @param entity The entity to start the ray trace from.
     * @param range  The range of the ray trace.
     * @return The entity the given entity is currently looking at, or null if no entity was found.
     */
    @Nullable
    Entity getPointedEntity(Entity entity, double range);

    /**
     * Performs a ray trace.
     *
     * @param entity         The entity to start the ray trace from.
     * @param level          The level to perform the ray trace in.
     * @param range          The range of the ray trace.
     * @param entities       Whether to include entities. If false, only checks blocks.
     * @param targetNonSolid False if non-solid blocks and fluids should block the ray trace, true if non-solid blocks and fluids should be ignored.
     * @return A hit result, representing the ray trace.
     */
    HitResult trace(Entity entity, Level level, double range, boolean entities, boolean targetNonSolid);

    /**
     * Get the stat value modified by the modifiers.
     *
     * @param baseValue      The base value for the stat.
     * @param stat           The stat that is modified.
     * @param spell          The spell that the part belongs to.
     * @param caster         The entity casting the spell.
     * @param target         The target of the spell cast.
     * @param componentIndex The 1 based index of the currently invoked part.
     * @return The modified value of the stat.
     */
    float getModifiedStat(float baseValue, ISpellPartStat stat, List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, @Nullable HitResult target, int componentIndex);

    /**
     * Casts the spell.
     *
     * @param spell        The spell to cast.
     * @param caster       The entity casting the spell.
     * @param level        The level the spell is cast in.
     * @param target       The target of the spell cast.
     * @param castingTicks How long the spell has already been cast.
     * @param index        The 1 based index of the currently invoked part.
     * @param awardXp      The magic xp awarded for casting this spell.
     * @return A SpellCastResult that represents the spell casting outcome.
     */
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp);

    /**
     * Selects the next shape group of the given spell item stack, wrapping around.
     *
     * @param stack The spell item stack to select the next shape group of.
     */
    void nextShapeGroup(ItemStack stack);

    /**
     * Selects the previous shape group of the given spell item stack, wrapping around.
     *
     * @param stack The spell item stack to select the next shape group of.
     */
    void prevShapeGroup(ItemStack stack);

    /**
     * {@return the color for the current part}
     * 
     * @param modifiers    The list of modifiers for the currently invoked part.
     * @param spell        The spell being cast.
     * @param caster       The entity casting the spell.
     * @param index        The 1 based index of the currently invoked part.
     * @param defaultColor The default color to use as fallback.
     */
    int getColor(List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, int index, int defaultColor);
}
