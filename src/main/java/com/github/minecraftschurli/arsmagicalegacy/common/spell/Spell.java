package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record Spell(List<ShapeGroup> shapeGroups, SpellStack spellStack) {
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ShapeGroup.CODEC.listOf().fieldOf("shape_groups").forGetter(Spell::shapeGroups),
            SpellStack.CODEC.fieldOf("spell_stack").forGetter(Spell::spellStack)
    ).apply(inst, Spell::new));

    public static final Spell EMPTY = new Spell(List.of(), SpellStack.EMPTY);

    public boolean isContinuous(byte currentShapeGroup) {
        return getFirstShape(currentShapeGroup).filter(ISpellPart.ISpellShape::isContinuous).isPresent();
    }

    private Optional<ISpellPart.ISpellShape> getFirstShape(byte currentShapeGroup) {
        ISpellPart.ISpellShape first = null;
        try {
            var part = shapeGroups.get(currentShapeGroup).parts().get(0);
            if (part instanceof ISpellPart.ISpellShape shape) {
                first = shape;
            }
        } catch (IndexOutOfBoundsException exception) {
            var part = spellStack.parts().get(0);
            if (part instanceof ISpellPart.ISpellShape shape) {
                first = shape;
            }
        }
        return Optional.ofNullable(first);
    }

    public SpellCastResult cast(ItemStack stack, LivingEntity caster, Level level, boolean consume, boolean awardXp, int ticksUsed) {
        return SpellCastResult.FAIL;
    }

    public enum SpellCastResult {
        SUCCESS, FAIL;

        public boolean isFail() {
            return this == FAIL;
        }
    }
}
