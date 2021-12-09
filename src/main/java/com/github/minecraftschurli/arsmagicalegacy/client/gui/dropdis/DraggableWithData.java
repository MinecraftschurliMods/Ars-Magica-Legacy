package com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis;

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

    public T getData() {
        return data;
    }

    public static <T> List<T> dataList(List<Draggable> draggables) {
        return draggables.stream().<T>map(DraggableWithData::data).toList();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T data(Draggable draggable) {
        if (draggable instanceof DraggableWithData<?> withData) {
            return (T) withData.getData();
        }
        return null;
    }
}
