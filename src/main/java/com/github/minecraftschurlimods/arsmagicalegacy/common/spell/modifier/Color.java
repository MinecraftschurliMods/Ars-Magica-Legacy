package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class Color extends AbstractModifier {
    @Override
    public ISpellPartStatModifier getStatModifier(ISpellPartStat stat) {
        return Color::modify;
    }

    @Override
    public Set<ISpellPartStat> getStatsModified() {
        return Set.of(SpellPartStats.COLOR);
    }

    private static float modify(float base, float modified, ISpell spell, LivingEntity caster, @Nullable HitResult target, int componentIndex) {
        int size = spell.currentShapeGroup().shapesWithModifiers().size();
        int shapeGroup;
        int innerIndex;
        if (size > componentIndex) {
            shapeGroup = spell.currentShapeGroupIndex();
            innerIndex = componentIndex;
        } else {
            shapeGroup = -1;
            innerIndex = componentIndex - size;
        }
        Integer data = getData(spell.additionalData(), shapeGroup, innerIndex);
        return data != null ? data : modified;
    }

    public static @Nullable Integer getData(CompoundTag tag, int shapeGroupIndex, int innerIndex) {
        return (tag.get(getKey(shapeGroupIndex, innerIndex)) instanceof NumericTag numericTag ? numericTag.getAsInt() : null);
    }

    public static String getKey(int shapeGroupIndex, int innerIndex) {
        return "color-" + (shapeGroupIndex < 0 ? "x" : String.valueOf(shapeGroupIndex)) + "-" + innerIndex;
    }
}
