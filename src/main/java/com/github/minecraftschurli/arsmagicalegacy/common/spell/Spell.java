package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.*;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.minecraftschurli.arsmagicalegacy.common.util.MiscConstants.AFFINITY_GAINS;

public final class Spell implements ISpell {
    //@formatter:off
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ShapeGroup.CODEC.listOf().fieldOf(SHAPE_GROUPS_KEY).forGetter(Spell::shapeGroups),
            SpellStack.CODEC.fieldOf(SPELL_STACK_KEY).forGetter(Spell::spellStack),
            CompoundTag.CODEC.fieldOf(DATA_KEY).forGetter(Spell::additionalData)
    ).apply(inst, Spell::new));
    //@formatter:on
    public static final Spell EMPTY = new Spell(List.of(), SpellStack.EMPTY, new CompoundTag());

    private final List<ShapeGroup> shapeGroups;
    private final SpellStack spellStack;
    private final CompoundTag additionalData;
    private final Lazy<Boolean> continuous;
    private final Lazy<Boolean> empty;
    private final Lazy<Boolean> valid;

    public static Spell of(SpellStack spellStack, ShapeGroup... shapeGroups) {
        return new Spell(List.of(shapeGroups), spellStack, new CompoundTag());
    }

    public Spell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData) {
        this.shapeGroups = shapeGroups;
        this.spellStack = spellStack;
        this.additionalData = additionalData;
        this.continuous = Lazy.concurrentOf(() -> this.firstShape(this.currentShapeGroupIndex()).filter(ISpellShape::isContinuous).isPresent());
        this.empty = Lazy.concurrentOf(() -> this.shapeGroups().isEmpty() && spellStack().isEmpty());
        this.valid = Lazy.concurrentOf(() -> Stream.concat(this.shapeGroups().stream().map(ShapeGroup::parts).flatMap(Collection::stream), this.spellStack().parts().stream()).map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart).allMatch(Objects::nonNull));
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
            return Optional.ofNullable(shapeGroup(currentShapeGroup).map(ShapeGroup::parts).filter(parts -> !parts.isEmpty()).orElse(spellStack().parts()).get(0)).filter(ISpellShape.class::isInstance).map(ISpellShape.class::cast);
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
        if (shapeGroup >= shapeGroups().size() || shapeGroup < 0) throw new IllegalArgumentException();
        additionalData().putByte(CURRENT_SHAPE_GROUP_KEY, shapeGroup);
    }

    @Override
    public SpellCastResult cast(LivingEntity caster, Level level, int castingTicks, boolean consume, boolean awardXp) {
        SpellCastEvent event = new SpellCastEvent(caster, this);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return SpellCastResult.CANCELLED;
        if (caster.hasEffect(AMMobEffects.SILENCE.get())) return SpellCastResult.SILENCED;
        float mana = event.mana;
        float burnout = event.burnout;
        Collection<Either<Ingredient, ItemStack>> reagents = event.reagents;
        ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
        IMagicHelper magicHelper = api.getMagicHelper();
        ISpellHelper spellHelper = api.getSpellHelper();
        if (consume && !(caster instanceof Player p && p.isCreative())) {
            if (magicHelper.getMana(caster) < mana) return SpellCastResult.NOT_ENOUGH_MANA;
            if (magicHelper.getMaxBurnout(caster) - magicHelper.getBurnout(caster) < burnout)
                return SpellCastResult.BURNED_OUT;
            if (!spellHelper.hasReagents(caster, reagents)) return SpellCastResult.MISSING_REAGENTS;
        }
        SpellCastResult result = spellHelper.invoke(this, caster, level, null, castingTicks, 0, awardXp);
        if (caster instanceof Player p && p.isCreative()) return result;
        if (consume && result.isConsume()) {
            magicHelper.decreaseMana(caster, mana, true);
            magicHelper.increaseBurnout(caster, burnout);
            spellHelper.consumeReagents(caster, reagents);
        }
        if (awardXp && result.isSuccess() && caster instanceof Player player) {
            boolean affinityGains = ArsMagicaAPI.get().getSkillHelper().knows(player, AFFINITY_GAINS) && SkillManager.instance().containsKey(AFFINITY_GAINS);
            boolean continuous = isContinuous();
            Map<IAffinity, Double> affinityShifts = partsWithModifiers().stream().map(Pair::getFirst).map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart).filter(Objects::nonNull).map(ISpellPartData::affinityShifts).map(Map::entrySet).flatMap(Collection::stream).collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)));
            for (Map.Entry<IAffinity, Double> entry : affinityShifts.entrySet()) {
                IAffinity affinity = entry.getKey();
                Double shift = entry.getValue();
                if (continuous) {
                    shift /= 4;
                }
                if (affinityGains) {
                    shift *= 1.1;
                }
                AffinityChangingEvent evt = new AffinityChangingEvent(player, affinity, shift.floatValue());
                if (!evt.isCanceled()) {
                    ArsMagicaAPI.get().getAffinityHelper().applyAffinityShift(evt.getPlayer(), evt.affinity, evt.shift);
                }
            }
            float xp = 0.05f * affinityShifts.size();
            if (continuous) xp /= 4;
            if (affinityGains) xp *= 0.9f;
            magicHelper.awardXp(player, xp);
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
    public float manaCost(@Nullable LivingEntity caster) {
        float cost = 0;
        float multiplier = 1;
        ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
        ISpellDataManager spellDataManager = api.getSpellDataManager();
        IAffinityHelper affinityHelper = api.getAffinityHelper();
        for (ISpellPart part : parts()) {
            switch (part.getType()) {
                case SHAPE, MODIFIER -> multiplier *= spellDataManager.getDataForPart(part).manaCost();
                case COMPONENT -> {
                    ISpellPartData data = spellDataManager.getDataForPart(part);
                    cost += data.manaCost();
                    if (caster instanceof Player player) {
                        for (IAffinity aff : data.affinities()) {
                            if (affinityHelper.getAffinityDepth(player, aff) > 0) {
                                cost -= (float) (cost * (0.5f * affinityHelper.getAffinityDepth(player, aff) / 100f));
                            }// else {
                            //cost = cost + (cost * (0.10f));
                            //}
                        }
                    }
                }
            }
        }
        if (multiplier == 0) multiplier = 1;
        cost *= multiplier;
        if (caster instanceof Player player && affinityHelper.getAffinityDepth(player, IAffinity.ARCANE) > 0.5f) {
            float reduction = (float) (1 - (0.5 * affinityHelper.getAffinityDepth(player, IAffinity.ARCANE)));
            cost *= reduction;
        }
        return cost;
    }

    @Override
    public float burnout() {
        float cost = 0;
        for (ISpellPart part : parts()) {
            if (part.getType() == ISpellPart.SpellPartType.COMPONENT) {
                cost += ArsMagicaAPI.get().getSpellDataManager().getDataForPart(part).burnout();
            }
        }
        return cost;
    }

    @Override
    public List<Either<Ingredient, ItemStack>> reagents() {
        ISpellDataManager spellDataManager = ArsMagicaAPI.get().getSpellDataManager();
        return parts().stream().filter(part -> part.getType() == ISpellPart.SpellPartType.COMPONENT).map(spellDataManager::getDataForPart).flatMap(data -> data.reagents().stream()).toList();
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
        List<ISpellPartData> iSpellPartData = Stream.concat(shapeGroups.stream().map(ShapeGroup::parts).flatMap(Collection::stream), spellStack.parts().stream()).map(ArsMagicaAPI.get().getSpellDataManager()::getDataForPart).toList();
        List<ISpellIngredient> ingredients = new ArrayList<>();
        for (ISpellPartData data : iSpellPartData) {
            if (data == null) return List.of();
            ingredients.addAll(data.recipe());
        }
        return ingredients;
    }

    @Override
    public CompoundTag additionalData() {
        if (isEmpty()) return new CompoundTag();
        return additionalData;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Spell spell = (Spell) o;
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
