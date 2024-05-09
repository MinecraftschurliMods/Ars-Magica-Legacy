package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Spell implements ISpell {
    private final List<ShapeGroup> shapeGroups;
    private final SpellStack spellStack;
    private final CompoundTag additionalData;
    private final Supplier<Boolean> continuous;
    private final Supplier<Boolean> empty;
    private final Supplier<Boolean> nonNull;
    private final Supplier<Boolean> valid;

    public Spell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData) {
        this.shapeGroups = shapeGroups;
        this.spellStack = spellStack;
        this.additionalData = additionalData;
        continuous = Lazy.concurrentOf(() -> firstShape(currentShapeGroupIndex()).filter(ISpellShape::isContinuous).isPresent());
        empty = Lazy.concurrentOf(() -> (shapeGroups().isEmpty() || shapeGroups().stream().allMatch(ShapeGroup::isEmpty)) && spellStack().isEmpty());
        nonNull = Lazy.concurrentOf(() -> Stream.concat(shapeGroups().stream().map(ShapeGroup::parts).flatMap(Collection::stream), spellStack().parts().stream())
                .map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart)
                .allMatch(Objects::nonNull));
        valid = Lazy.concurrentOf(this::validate);
    }

    public static Spell of(SpellStack spellStack, ShapeGroup... shapeGroups) {
        return new Spell(List.of(shapeGroups), spellStack, new CompoundTag());
    }

    @Override
    public boolean isContinuous() {
        return continuous.get();
    }

    @Override
    public boolean isEmpty() {
        return empty.get();
    }

    @Override
    public boolean isNonNull() {
        return nonNull.get();
    }

    @Override
    public boolean isValid() {
        return valid.get();
    }

    @Override
    public Optional<ISpellShape> firstShape(byte currentShapeGroup) {
        try {
            return Optional.ofNullable(shapeGroup(currentShapeGroup).map(ShapeGroup::parts).filter(parts -> !parts.isEmpty()).orElse(spellStack().parts()).getFirst())
                    .filter(ISpellShape.class::isInstance)
                    .map(ISpellShape.class::cast);
        } catch (IndexOutOfBoundsException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ShapeGroup> shapeGroup(byte shapeGroup) {
        if (shapeGroup > shapeGroups().size() - 1) return Optional.empty();
        return Optional.of(shapeGroups().get(shapeGroup));
    }

    @Override
    public ShapeGroup currentShapeGroup() {
        return shapeGroup(currentShapeGroupIndex()).orElse(ShapeGroup.EMPTY);
    }

    @Override
    public byte currentShapeGroupIndex() {
        return additionalData().getByte(CURRENT_SHAPE_GROUP_KEY);
    }

    @Override
    public void currentShapeGroupIndex(byte shapeGroup) {
        if (shapeGroup >= shapeGroups().size() || shapeGroup < 0)
            throw new IndexOutOfBoundsException("Invalid shape group index!");
        additionalData().putByte(CURRENT_SHAPE_GROUP_KEY, shapeGroup);
    }

    @Override
    @UnmodifiableView
    public List<Pair<? extends ISpellPart, List<ISpellModifier>>> partsWithModifiers() {
        Optional<ShapeGroup> shapeGroup = shapeGroup(currentShapeGroupIndex());
        ArrayList<Pair<ISpellPart, List<ISpellModifier>>> pwm = new ArrayList<>(spellStack().partsWithModifiers());
        LinkedList<Pair<? extends ISpellPart, List<ISpellModifier>>> shapesWithModifiers = new LinkedList<>();
        shapeGroup.ifPresentOrElse(group -> {
            shapesWithModifiers.addAll(group.shapesWithModifiers());
            Pair<? extends ISpellPart, List<ISpellModifier>> last = shapesWithModifiers.getLast();
            ArrayList<ISpellModifier> tmp = new ArrayList<>();
            shapesWithModifiers.set(shapesWithModifiers.size() - 1, Pair.of(last.getFirst(), Collections.unmodifiableList(tmp)));
            tmp.addAll(last.getSecond());
            tmp.addAll(pwm.removeFirst().getSecond());
        }, pwm::removeFirst);
        shapesWithModifiers.addAll(pwm);
        return Collections.unmodifiableList(shapesWithModifiers);
    }

    @Override
    public float mana(LivingEntity caster) {
        float cost = 0;
        float multiplier = 1;
        var spellDataManager = ArsMagicaAPI.get().getSpellDataManager();
        for (ISpellPart part : parts()) {
            ISpellPartData data = spellDataManager.getDataForPart(part);
            if (data == null) continue;
            switch (part.getType()) {
                case SHAPE, MODIFIER -> multiplier *= data.manaCost();
                case COMPONENT -> cost += data.manaCost();
            }
        }
        SpellEvent.ManaCost.Pre pre = new SpellEvent.ManaCost.Pre(caster, this, cost, multiplier);
        NeoForge.EVENT_BUS.post(pre);
        cost = pre.getModifiedBase();
        multiplier = pre.getModifiedMultiplier();
        if (multiplier == 0) {
            multiplier = 1;
        }
        cost *= multiplier;
        SpellEvent.ManaCost.Post post = new SpellEvent.ManaCost.Post(caster, this, cost);
        NeoForge.EVENT_BUS.post(post);
        return post.getModifiedMana();
    }

    @Override
    public float burnout(LivingEntity caster) {
        float cost = 0;
        for (ISpellPart part : parts()) {
            ISpellPartData data = ArsMagicaAPI.get().getSpellDataManager().getDataForPart(part);
            if (data != null && part.getType() == ISpellPart.SpellPartType.COMPONENT) {
                cost += data.getBurnout();
            }
        }
        SpellEvent.BurnoutCost event = new SpellEvent.BurnoutCost(caster, this, cost);
        NeoForge.EVENT_BUS.post(event);
        return event.getModifiedBurnout();
    }

    @Override
    public List<ItemFilter> reagents(LivingEntity caster) {
        ISpellDataManager spellDataManager = ArsMagicaAPI.get().getSpellDataManager();
        List<ItemFilter> reagents = new ArrayList<>();
        for (ISpellPart part : parts()) {
            if (part.getType() != ISpellPart.SpellPartType.COMPONENT) continue;
            ISpellPartData dataForPart = spellDataManager.getDataForPart(part);
            if (dataForPart == null) continue;
            reagents.addAll(dataForPart.reagents());
        }
        SpellEvent.ReagentCost event = new SpellEvent.ReagentCost(caster, this, reagents);
        NeoForge.EVENT_BUS.post(event);
        return event.reagents;
    }

    @Override
    @UnmodifiableView
    @Contract(pure = true)
    public List<ShapeGroup> shapeGroups() {
        return Collections.unmodifiableList(shapeGroups);
    }

    @Override
    public SpellStack spellStack() {
        return spellStack;
    }

    @Override
    public List<ISpellIngredient> recipe() {
        List<ISpellPartData> iSpellPartData = Stream.concat(shapeGroups.stream().map(ShapeGroup::parts).flatMap(Collection::stream), spellStack.parts().stream())
                .map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart)
                .toList();
        List<ISpellIngredient> ingredients = new ArrayList<>();
        ingredients.add(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.SPELLCRAFTING_START), 1));
        for (ISpellPartData data : iSpellPartData) {
            if (data == null) return List.of();
            ingredients.addAll(data.recipe());
        }
        ingredients.add(new IngredientSpellIngredient(Ingredient.of(AMTags.Items.SPELLCRAFTING_END), 1));
        return ingredients;
    }

    @Override
    public Map<Affinity, Double> affinityShifts() {
        return partsWithModifiers()
                .stream()
                .map(Pair::getFirst)
                .map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart)
                .filter(Objects::nonNull)
                .map(ISpellPartData::affinityShifts)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)));
    }

    @Override
    public Set<Affinity> affinities() {
        return partsWithModifiers().stream()
                .map(Pair::getFirst)
                .map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart)
                .filter(Objects::nonNull)
                .map(ISpellPartData::affinityShifts)
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Affinity primaryAffinity() {
        return affinityShifts().entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseGet(AMAffinities.NONE::value);
    }

    @Override
    public CompoundTag additionalData() {
        return isEmpty() ? new CompoundTag() : additionalData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return shapeGroups().equals(spell.shapeGroups()) && spellStack().equals(spell.spellStack()) && additionalData().equals(spell.additionalData());
    }

    @Override
    public int hashCode() {
        int result = shapeGroups().hashCode();
        result = 31 * result + spellStack().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Spell[shapeGroups=" + shapeGroups + ", spellStack=" + spellStack + ", additionalData=" + additionalData + ']';
    }

    private boolean validate() {
        if (isEmpty() || !isNonNull()) return false;
        //check spell stack
        if (spellStack().isEmpty()) return false;
        if (spellStack().parts().getFirst().getType() != ISpellPart.SpellPartType.COMPONENT) return false;
        //find last non-empty shape group
        List<ShapeGroup> groups = shapeGroups();
        if (groups.stream().allMatch(ShapeGroup::isEmpty)) return false;
        int last = -1;
        for (int i = 0; i < groups.size(); i++) {
            if (!groups.get(i).isEmpty()) {
                last = i;
            }
        }
        //check for empty shape groups between other non-empty shape groups
        if (last == -1) return false;
        groups = groups.stream().filter(e -> !e.isEmpty()).toList();
        if (last != groups.size() - 1) return false;
        //check shape groups themselves
        for (ShapeGroup group : groups) {
            if (group.parts().getFirst().getType() != ISpellPart.SpellPartType.SHAPE) return false;
        }
        return true;
    }
}
