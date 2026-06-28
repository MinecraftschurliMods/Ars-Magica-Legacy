package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/// Represents a spell ingredient.
public interface SpellIngredient {
    Codec<SpellIngredient> CODEC = Codec.lazyInitialized(() -> AMRegistries.SPELL_INGREDIENTS.byNameCodec().dispatch(SpellIngredient::codec, Function.identity()));

    /// @return The registered [MapCodec] of the spell ingredient.
    MapCodec<? extends SpellIngredient> codec();

    /// @return The count of the spell ingredient.
    int count();

    /// @param level The [Level] that the tooltip will be displayed in.
    /// @return A list of tooltip [Component]s for the spell ingredient.
    List<Component> tooltip(@Nullable Level level);

    /// @param other The other ingredient to combine with.
    /// @return Whether the spell ingredient and the other spell ingredient can be combined.
    /// @see SpellIngredient#combine(SpellIngredient)
    boolean canCombine(SpellIngredient other);

    /// @param other The other ingredient to combine with.
    /// @return The combination of the spell ingredient with the other spell ingredient, or null if they cannot be combined.
    /// @see SpellIngredient#canCombine(SpellIngredient)
    @Nullable
    SpellIngredient combine(SpellIngredient other);

    /// Attempts to consume the spell ingredient.
    ///
    /// @param level The [Level] in which the spell ingredient is consumed.
    /// @param pos   The [BlockPos] at which the spell ingredient is consumed.
    /// @return Whether the spell ingredient was consumed or not.
    boolean consume(Level level, BlockPos pos);

    /// @return A representation of the spell ingredients as item stacks, for use in GUI rendering.
    List<ItemStack> asItemStacks();
}
