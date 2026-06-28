package com.github.minecraftschurlimods.arsmagicalegacy.api.client.screen;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.component.DataComponentType;
import org.jspecify.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

/// Represents a screen for customizing a [SpellPart].
/// Implementers are expected to extend [Screen], however this is left as a marker interface to allow other screen classes to be used, e.g. [AbstractContainerScreen].
/// See [AbstractSpellPartCustomizationScreen] for a dummy implementation.
public interface SpellPartCustomizationScreen {
    /// The factory interface for [SpellPartCustomizationScreen]s, used in [RegisterSpellPartCustomizationScreensEvent].
    ///
    /// @param <T> A [Function] to get the initial value.
    /// @param <S> A [BiConsumer] to set the value when the screen is closed.
    @FunctionalInterface
    interface Factory<T, S extends Screen & SpellPartCustomizationScreen> {
        S create(Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter);
    }
}
