package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.CrystalPhylacteryContentsSize;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
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
