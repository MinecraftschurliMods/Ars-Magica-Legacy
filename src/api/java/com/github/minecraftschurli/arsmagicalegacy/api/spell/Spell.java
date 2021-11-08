package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public record Spell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData) {
    public static final String SHAPE_GROUPS_KEY        = "shape_groups";
    public static final String CURRENT_SHAPE_GROUP_KEY = "current_shape_group";
    public static final String SPELL_STACK_KEY         = "spell_stack";
    public static final String DATA_KEY                = "data";

    //@formatter:off
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ShapeGroup.CODEC.listOf().fieldOf(SHAPE_GROUPS_KEY).forGetter(Spell::shapeGroups),
            SpellStack.CODEC.fieldOf(SPELL_STACK_KEY).forGetter(Spell::spellStack),
            CompoundTag.CODEC.fieldOf(DATA_KEY).forGetter(Spell::additionalData)
    ).apply(inst, Spell::new));
    //@formatter:on

    public static final Spell EMPTY = new Spell(List.of(), SpellStack.EMPTY, new CompoundTag());

    public boolean isContinuous() {
        return firstShape(currentShapeGroupIndex()).filter(ISpellShape::isContinuous).isPresent();
    }

    public boolean isEmpty() {
        return this.shapeGroups().isEmpty() && spellStack().isEmpty();
    }

    public Optional<ISpellShape> firstShape(byte currentShapeGroup) {
        try {
            return Optional.ofNullable(shapeGroup(currentShapeGroup).map(ShapeGroup::parts)
                                                                    .filter(parts -> !parts.isEmpty())
                                                                    .orElse(spellStack().parts())
                                                                    .get(0))
                           .filter(ISpellShape.class::isInstance)
                           .map(ISpellShape.class::cast);
        } catch (IndexOutOfBoundsException exception) {
            return Optional.empty();
        }
    }

    public Optional<ShapeGroup> shapeGroup(byte shapeGroup) {
        if (shapeGroup > shapeGroups().size() - 1) return Optional.empty();
        return Optional.of(shapeGroups().get(shapeGroup));
    }

    public ShapeGroup currentShapeGroup() {
        return shapeGroups().get(currentShapeGroupIndex());
    }

    public byte currentShapeGroupIndex() {
        return additionalData().getByte(CURRENT_SHAPE_GROUP_KEY);
    }

    public void currentShapeGroupIndex(byte shapeGroup) {
        if (shapeGroup >= shapeGroups().size() || shapeGroup < 0) throw new IllegalArgumentException();
        additionalData().putByte(CURRENT_SHAPE_GROUP_KEY, shapeGroup);
    }

    public SpellCastResult cast(LivingEntity caster, Level level, int castingTicks, boolean consume, boolean awardXp) {
        SpellCastEvent event = new SpellCastEvent(caster, this);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) return SpellCastResult.CANCELLED;
        float mana = event.mana;
        float burnout = event.burnout;
        Collection<Either<Ingredient, ItemStack>> reagents = event.reagents;
        ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
        IMagicHelper magicHelper = api.getMagicHelper();
        ISpellHelper spellHelper = api.getSpellHelper();
        if (consume) {
            if (magicHelper.getMana(caster) < mana) return SpellCastResult.NOT_ENOUGH_MANA;
            if (magicHelper.getMaxBurnout(caster) - magicHelper.getBurnout(caster) < burnout) return SpellCastResult.BURNED_OUT;
            if (!spellHelper.hasReagents(caster, reagents)) return SpellCastResult.MISSING_REAGENTS;
        }
        SpellCastResult result = spellHelper.invoke(this, caster, level, null, null, caster.position(), castingTicks, 0, awardXp);
        if (consume) {
            magicHelper.decreaseMana(caster, mana);
            magicHelper.increaseBurnout(caster, burnout);
            spellHelper.consumeReagents(caster, reagents);
        }
        if (awardXp && caster instanceof Player player) {
            magicHelper.awardXp(player, spellHelper.getXpForSpellCast(mana, burnout, reagents, this, player));
        }
        return result;
    }

    @UnmodifiableView
    public List<Pair<? extends ISpellPart, List<ISpellModifier>>> partsWithModifiers() {
        Optional<ShapeGroup> shapeGroup = shapeGroup(currentShapeGroupIndex());
        ArrayList<Pair<ISpellPart, List<ISpellModifier>>> pwm = new ArrayList<>(spellStack().partsWithModifiers());
        LinkedList<Pair<? extends ISpellPart, List<ISpellModifier>>> shapesWithModifiers = new LinkedList<>();
        shapeGroup.ifPresent(group -> {
            shapesWithModifiers.addAll(group.shapesWithModifiers());
            Pair<? extends ISpellPart, List<ISpellModifier>> last = shapesWithModifiers.getLast();
            ArrayList<ISpellModifier> tmp = new ArrayList<>();
            shapesWithModifiers.set(shapesWithModifiers.size() - 1,
                                    Pair.of(last.getFirst(), Collections.unmodifiableList(tmp)));
            tmp.addAll(last.getSecond());
            tmp.addAll(pwm.remove(0).getSecond());
        });
        shapesWithModifiers.addAll(pwm);
        return Collections.unmodifiableList(shapesWithModifiers);
    }

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

    public float burnout() {
        float cost = 0;
        for (ISpellPart part : parts()) {
            if (part.getType() == ISpellPart.SpellPartType.COMPONENT) {
                cost += ArsMagicaAPI.get().getSpellDataManager().getDataForPart(part).burnout();
            }
        }
        return cost;
    }

    public List<Either<Ingredient, ItemStack>> reagents() {
        ISpellDataManager spellDataManager = ArsMagicaAPI.get().getSpellDataManager();
        return parts().stream().filter(part -> part.getType() == ISpellPart.SpellPartType.COMPONENT).map(
                spellDataManager::getDataForPart).flatMap(data -> data.reagents().stream()).toList();
    }

    private List<ISpellPart> parts() {
        List<ISpellPart> list = new ArrayList<>();
        list.addAll(currentShapeGroup().parts());
        list.addAll(spellStack().parts());
        return Collections.unmodifiableList(list);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ShapeGroup> shapeGroups() {
        return Collections.unmodifiableList(shapeGroups);
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
        return shapeGroups().equals(spell.shapeGroups()) &&
               spellStack().equals(spell.spellStack()) &&
               additionalData().equals(spell.additionalData());
    }

    @Override
    public int hashCode() {
        int result = shapeGroups().hashCode();
        result = 31 * result + spellStack().hashCode();
        return result;
    }
}
