package com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface DropValidator {
    boolean validate(List<Draggable> items, Draggable item);

    @FunctionalInterface
    interface WithData<T> extends DropValidator {
        @Override
        default boolean validate(List<Draggable> items, Draggable item) {
            return validateData(DraggableWithData.dataList(items), DraggableWithData.data(item));
        }

        boolean validateData(List<T> dataList, T data);

        default <X> WithData<X> map(Function<X, T> mappingFunction) {
            return (dataList, data) -> validateData(dataList.stream().map(mappingFunction).toList(), mappingFunction.apply(data));
        }
    }
}
