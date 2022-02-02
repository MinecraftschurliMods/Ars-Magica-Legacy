package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DraggableWithData<T> extends Draggable {
    private final T data;

    public DraggableWithData(int x, int y, int width, int height, TextureAtlasSprite sprite, Component name, T data) {
        super(x, y, width, height, sprite, name);
        this.data = data;
    }

    /**
     * @param draggables A list of draggables to get as a draggable-with-data list.
     * @param <T>        The type of the list.
     * @return A list of draggables-with-data, created from the given list.
     */
    public static <T> List<T> dataList(List<Draggable> draggables) {
        return draggables.stream().<T>map(DraggableWithData::data).toList();
    }

    /**
     * @param draggable A draggable to get as a draggable-with-data.
     * @param <T>       The type of the draggable.
     * @return A draggable-with-data, created from the given draggable.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T data(Draggable draggable) {
        if (draggable instanceof DraggableWithData<?> withData) {
            return (T) withData.getData();
        }
        return null;
    }

    public T getData() {
        return data;
    }
}
