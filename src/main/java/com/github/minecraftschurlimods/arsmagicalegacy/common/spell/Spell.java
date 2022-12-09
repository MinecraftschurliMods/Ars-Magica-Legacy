package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.server.AMPermissions;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.server.permission.PermissionAPI;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Spell implements ISpell {
    private static final ResourceLocation AFFINITY_GAINS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity_gains");
    private final List<ShapeGroup> shapeGroups;
    private final SpellStack spellStack;
    private final CompoundTag additionalData;
    private final Lazy<Boolean> continuous;
    private final Lazy<Boolean> empty;
    private final Lazy<Boolean> valid;

    public Spell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData) {
        this.shapeGroups = shapeGroups;
        this.spellStack = spellStack;
        this.additionalData = additionalData;
        continuous = Lazy.concurrentOf(() -> firstShape(currentShapeGroupIndex()).filter(ISpellShape::isContinuous).isPresent());
        empty = Lazy.concurrentOf(() -> (shapeGroups().isEmpty() || shapeGroups().stream().allMatch(ShapeGroup::isEmpty)) && spellStack().isEmpty());
        valid = Lazy.concurrentOf(() -> Stream.concat(shapeGroups().stream().map(ShapeGroup::parts).flatMap(Collection::stream), spellStack().parts().stream())
                .map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart)
                .allMatch(Objects::nonNull));
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
    public boolean isValid() {
        return valid.get();
    }

    @Override
    public Optional<ISpellShape> firstShape(byte currentShapeGroup) {
        try {
            return Optional.ofNullable(shapeGroup(currentShapeGroup).map(ShapeGroup::parts).filter(parts -> !parts.isEmpty()).orElse(spellStack().parts()).get(0))
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
        return shapeGroups().get(currentShapeGroupIndex());
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
    public SpellCastResult cast(LivingEntity caster, Level level, int castingTicks, boolean consume, boolean awardXp) {
        if (caster instanceof ServerPlayer player && !PermissionAPI.getPermission(player, AMPermissions.CAN_CAST_SPELL))
            return SpellCastResult.NO_PERMISSION;
        if (MinecraftForge.EVENT_BUS.post(new SpellEvent.Cast.Pre(caster, this))) return SpellCastResult.CANCELLED;
        if (caster.hasEffect(AMMobEffects.SILENCE.get())) return SpellCastResult.SILENCED;
        float mana = mana(caster);
        float burnout = burnout(caster);
        Collection<ItemFilter> reagents = reagents(caster);
        var api = ArsMagicaAPI.get();
        var manaHelper = api.getManaHelper();
        var burnoutHelper = api.getBurnoutHelper();
        var spellHelper = api.getSpellHelper();
        if (consume && !(caster instanceof Player p && p.isCreative())) {
            if (manaHelper.getMana(caster) < mana) return SpellCastResult.NOT_ENOUGH_MANA;
            if (burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) < burnout)
                return SpellCastResult.BURNED_OUT;
            if (!spellHelper.hasReagents(caster, reagents)) return SpellCastResult.MISSING_REAGENTS;
        }
        SpellCastResult result = spellHelper.invoke(this, caster, level, null, castingTicks, 0, awardXp);
        if (caster instanceof Player p && p.isCreative()) return result;
        if (consume && result.isConsume()) {
            manaHelper.decreaseMana(caster, mana, true);
            burnoutHelper.increaseBurnout(caster, burnout);
            spellHelper.consumeReagents(caster, reagents);
        }
        MinecraftForge.EVENT_BUS.post(new SpellEvent.Cast.Post(caster, this));
        if (awardXp && result.isSuccess() && caster instanceof Player player) {
            boolean affinityGains = api.getSkillHelper().knows(player, AFFINITY_GAINS) && level.registryAccess().registryOrThrow(Skill.REGISTRY_KEY).containsKey(AFFINITY_GAINS);
            boolean continuous = isContinuous();
            Map<Affinity, Double> affinityShifts = affinityShifts();
            for (Map.Entry<Affinity, Double> entry : affinityShifts.entrySet()) {
                Affinity affinity = entry.getKey();
                Double shift = entry.getValue();
                if (continuous) {
                    shift /= 4;
                }
                if (affinityGains) {
                    shift *= 1.1;
                }
                AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(player, affinity, shift.floatValue(), false);
                if (!MinecraftForge.EVENT_BUS.post(event)) {
                    api.getAffinityHelper().applyAffinityShift(event.getEntity(), event.affinity, event.shift);
                    MinecraftForge.EVENT_BUS.post(new AffinityChangingEvent.Post(player, affinity, shift.floatValue(), false));
                }
            }
            float xp = 0.05f * affinityShifts.size();
            if (continuous) xp /= 4;
            if (affinityGains) xp *= 0.9f;
            api.getMagicHelper().awardXp(player, xp);
        }
        return result;
    }

    @Override
    @UnmodifiableView
    public List<Pair<? extends ISpellPart, List<ISpellModifier>>> partsWithModifiers() {
        Optional<ShapeGroup> shapeGroup = shapeGroup(currentShapeGroupIndex());
        ArrayList<Pair<ISpellPart, List<ISpellModifier>>> pwm = new ArrayList<>(spellStack().partsWithModifiers());
        LinkedList<Pair<? extends ISpellPart, List<ISpellModifier>>> shapesWithModifiers = new LinkedList<>();
        shapeGroup.ifPresent(group -> {
            shapesWithModifiers.addAll(group.shapesWithModifiers());
            Pair<? extends ISpellPart, List<ISpellModifier>> last = shapesWithModifiers.getLast();
            ArrayList<ISpellModifier> tmp = new ArrayList<>();
            shapesWithModifiers.set(shapesWithModifiers.size() - 1, Pair.of(last.getFirst(), Collections.unmodifiableList(tmp)));
            tmp.addAll(last.getSecond());
            tmp.addAll(pwm.remove(0).getSecond());
        });
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
        MinecraftForge.EVENT_BUS.post(pre);
        cost = pre.getModifiedBase();
        multiplier = pre.getModifiedMultiplier();
        if (multiplier == 0) {
            multiplier = 1;
        }
        cost *= multiplier;
        SpellEvent.ManaCost.Post post = new SpellEvent.ManaCost.Post(caster, this, cost);
        MinecraftForge.EVENT_BUS.post(post);
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
        MinecraftForge.EVENT_BUS.post(event);
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
        MinecraftForge.EVENT_BUS.post(event);
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
        ingredients.add(new IngredientSpellIngredient(Ingredient.of(AMItems.BLANK_RUNE.get()), 1)); // TODO make datadriven
        for (ISpellPartData data : iSpellPartData) {
            if (data == null) return List.of();
            ingredients.addAll(data.recipe());
        }
        ingredients.add(new IngredientSpellIngredient(Ingredient.of(AMItems.SPELL_PARCHMENT.get()), 1)); // TODO make datadriven
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
                .orElseGet(AMAffinities.NONE);
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
}
