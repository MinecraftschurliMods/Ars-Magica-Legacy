package at.minecraftschurli.mods.arsmagicalegacy.api.plant;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;

/// Variant of [GrowthType] that separates the replanting logic from [GrowthType#harvest(GrowthContext, boolean)] out into separate methods.
public interface ReplantableGrowthType extends GrowthType {
    @Override
    default List<ItemStack> harvest(GrowthContext context, boolean replant) {
        List<ItemStack> drops = harvest(context);
        if (replant && canReplant(context)) {
            replant(context);
            ItemStackTemplate seed = context.plant().seed().orElse(null);
            for (ItemStack stack : drops) {
                if (ItemStack.isSameItemSameComponents(stack, seed)) {
                    stack.shrink(1);
                    break;
                }
            }
        }
        return drops;
    }

    /// Harvests the plant, if possible.
    ///
    /// @param context The [GrowthContext] to use.
    /// @return A list of [ItemStack], representing the drops of the plant.
    List<ItemStack> harvest(GrowthContext context);

    /// @param context The [GrowthContext] to use.
    /// @return Whether the plant can currently be replanted or not.
    boolean canReplant(GrowthContext context);

    /// Replants the plant, if possible.
    ///
    /// @param context The [GrowthContext] to use.
    void replant(GrowthContext context);
}
