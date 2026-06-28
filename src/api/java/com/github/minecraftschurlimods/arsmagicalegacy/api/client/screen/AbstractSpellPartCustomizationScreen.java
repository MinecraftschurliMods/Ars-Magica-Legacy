package com.github.minecraftschurlimods.arsmagicalegacy.api.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

/// Dummy implementation of [SpellPartCustomizationScreen]. This implementation, and as such also [SpellPartCustomizationScreen.Factory], expects the screen to modify exactly one data component.
/// It is encouraged to follow that pattern. If you need to store multiple values for a single spell part, adjust the type of the data component to accommodate multiple values.
/// To modify the data component value, change [AbstractSpellPartCustomizationScreen#value]. When the screen is closed, the change will be pushed to the parent screen automatically.
///
/// @param <T> The type of the modified data component.
public abstract class AbstractSpellPartCustomizationScreen<T> extends Screen implements SpellPartCustomizationScreen {
    private final DataComponentType<T> type;
    private final BiConsumer<DataComponentType<T>, T> setter;
    @Nullable
    protected T value;

    /// @param title       The title of the screen.
    /// @param type        The [DataComponentType] to use.
    /// @param valueGetter A [Function] that extracts the data component value, for initial storage.
    /// @param valueSetter A [BiConsumer] that is called when the screen is closed, and is responsible for returning the data component value to the parent screen.
    public AbstractSpellPartCustomizationScreen(Component title, DataComponentType<T> type, Function<DataComponentType<T>, @Nullable T> valueGetter, BiConsumer<DataComponentType<T>, @Nullable T> valueSetter) {
        super(title);
        this.type = type;
        this.value = valueGetter.apply(type);
        this.setter = valueSetter;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        extractTransparentBackground(graphics);
    }

    @Override
    public void onClose() {
        if (value != null) {
            setValue();
        }
        super.onClose();
    }

    /// Sets the value as if the screen were closed. This does not null-check the value.
    @SuppressWarnings("DataFlowIssue")
    protected void setValue() {
        setter.accept(type, value);
    }
}
