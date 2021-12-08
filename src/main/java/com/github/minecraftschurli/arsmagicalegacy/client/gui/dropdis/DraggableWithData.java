package com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

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

    @SuppressWarnings("unchecked")
    public static <T> List<T> dataList(List<Draggable> draggables) {
        return (List<T>) draggables.stream()
                                   .filter(DraggableWithData.class::isInstance)
                                   .map(DraggableWithData.class::cast)
                                   .map(DraggableWithData::getData)
                                   .toList();
    }
}
