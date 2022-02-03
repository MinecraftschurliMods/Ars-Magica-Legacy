package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Interface that represents a spell.
 */
public interface ISpell {
    String SHAPE_GROUPS_KEY = "shape_groups";
    String CURRENT_SHAPE_GROUP_KEY = "current_shape_group";
    String SPELL_STACK_KEY = "spell_stack";
    String DATA_KEY = "data";

    /**
     * @return Whether the spell is continuous or not.
     */
    boolean isContinuous();

    /**
     * @return Whether the spell is empty or not.
     */
    boolean isEmpty();

    /**
     * @return Whether the spell is valid or not.
     */
    boolean isValid();

    /**
     * @param currentShapeGroup The shape group to get the shape for.
     * @return The first shape of the given shape group.
     */
    Optional<ISpellShape> firstShape(byte currentShapeGroup);

    /**
     * @param shapeGroup The shape group index to get the shape group for.
     * @return The shape group by the given shape group index.
     */
    Optional<ShapeGroup> shapeGroup(byte shapeGroup);

    /**
     * @return The currently selected shape group.
     */
    ShapeGroup currentShapeGroup();

    /**
     * @return The index of the currently selected shape group.
     */
    byte currentShapeGroupIndex();

    /**
     * Sets the current shape group index.
     *
     * @param shapeGroup The shape group index to set.
     */
    void currentShapeGroupIndex(byte shapeGroup);

    /**
     * Casts the spell.
     *
     * @param caster       The player that casts the spell.
     * @param level        The level that the caster is in.
     * @param castingTicks The amount of ticks this spell has been cast already.
     * @param consume      Whether to consume the spell result or not.
     * @param awardXp      Whether to grant the player magic xp or not.
     * @return A SpellCastResult that represents the spell casting outcome.
     */
    SpellCastResult cast(LivingEntity caster, Level level, int castingTicks, boolean consume, boolean awardXp);

    /**
     * @return An unmodifiable list that represents a part list with their corresponding modifiers.
     */
    @UnmodifiableView
    List<Pair<? extends ISpellPart, List<ISpellModifier>>> partsWithModifiers();

    /**
     * @param caster The player that casts this spell.
     * @return The amount of mana this spell costs.
     */
    float mana(LivingEntity caster);

    /**
     * @param caster The player that casts this spell.
     * @return The burnout this spell causes.
     */
    float burnout(LivingEntity caster);

    /**
     * @return The reagents of this spell.
     */
    List<Either<Ingredient, ItemStack>> reagents(LivingEntity caster);

    /**
     * @return The list of spell parts in this spell.
     */
    @UnmodifiableView
    default List<ISpellPart> parts() {
        List<ISpellPart> list = new ArrayList<>();
        list.addAll(currentShapeGroup().parts());
        list.addAll(spellStack().parts());
        return Collections.unmodifiableList(list);
    }

    /**
     * @return The list of shape groups in this spell.
     */
    @UnmodifiableView
    @Contract(pure = true)
    List<ShapeGroup> shapeGroups();

    /**
     * @return The nbt data for this spell.
     */
    CompoundTag additionalData();

    /**
     * @return The spell stack for this spell.
     */
    SpellStack spellStack();

    /**
     * @return The recipe for this spell.
     */
    List<ISpellIngredient> recipe();

    /**
     * @return A map from affinity to double, representing the affinity shift of the spell for each affinity.
     */
    @UnmodifiableView
    Map<IAffinity, Double> affinityShifts();

    /**
     * @return A set of affinities containing the affinities of this spell.
     */
    Set<IAffinity> affinities();

    /**
     * @return The affinity that the given spell has the greatest shift in.
     */
    IAffinity primaryAffinity();
}
