package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.CrystalPhylacteryContentsSize;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.CrystalPhylacteryItem;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

final class CrystalPhylacterySubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final CrystalPhylacterySubtypeInterpreter INSTANCE = new CrystalPhylacterySubtypeInterpreter();

    private CrystalPhylacterySubtypeInterpreter() {
    }

    @Override
    @Nullable
    public Object getSubtypeData(ItemStack ingredient, UidContext context) {
        CrystalPhylacteryItem.Contents contents = ingredient.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount() == 0) return null;
        EntityType<?> type = contents.type();
        return CrystalPhylacteryContentsSize.get(type) > 0 ? type : null;
    }
}
