package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ISpell { // TODO doc
    String SHAPE_GROUPS_KEY        = "shape_groups";
    String CURRENT_SHAPE_GROUP_KEY = "current_shape_group";
    String SPELL_STACK_KEY         = "spell_stack";
    String DATA_KEY                = "data";

    boolean isContinuous();

    boolean isEmpty();

    boolean isValid();

    Optional<ISpellShape> firstShape(byte currentShapeGroup);

    Optional<ShapeGroup> shapeGroup(byte shapeGroup);

    ShapeGroup currentShapeGroup();

    byte currentShapeGroupIndex();

    void currentShapeGroupIndex(byte shapeGroup);

    SpellCastResult cast(LivingEntity caster, Level level, int castingTicks, boolean consume, boolean awardXp);

    @UnmodifiableView List<Pair<? extends ISpellPart, List<ISpellModifier>>> partsWithModifiers();

    float manaCost(@Nullable LivingEntity caster);

    float burnout();

    List<Either<Ingredient, ItemStack>> reagents();

    @UnmodifiableView
    default List<ISpellPart> parts() {
        List<ISpellPart> list = new ArrayList<>();
        list.addAll(currentShapeGroup().parts());
        list.addAll(spellStack().parts());
        return Collections.unmodifiableList(list);
    }

    @UnmodifiableView
    @Contract(pure = true)
    List<ShapeGroup> shapeGroups();

    CompoundTag additionalData();

    SpellStack spellStack();
}
