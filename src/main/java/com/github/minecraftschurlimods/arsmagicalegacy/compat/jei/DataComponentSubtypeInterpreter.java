package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

record DataComponentSubtypeInterpreter<T>(DataComponentType<T> type) implements ISubtypeInterpreter<ItemStack> {
    public static final DataComponentSubtypeInterpreter<Holder<Affinity>> AFFINITY = new DataComponentSubtypeInterpreter<>(AMDataComponents.AFFINITY.get());
    public static final DataComponentSubtypeInterpreter<Holder<SkillPoint>> SKILL_POINT = new DataComponentSubtypeInterpreter<>(AMDataComponents.SKILL_POINT.get());
    public static final DataComponentSubtypeInterpreter<Spell> SPELL = new DataComponentSubtypeInterpreter<>(AMDataComponents.SPELL.get());

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        return ingredient.has(type) ? ingredient.get(type) : null;
    }
}
