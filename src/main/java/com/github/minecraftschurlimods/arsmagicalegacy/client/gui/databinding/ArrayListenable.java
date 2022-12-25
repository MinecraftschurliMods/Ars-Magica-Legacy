package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.function.Consumer;

public class ArrayListenable<T> extends BaseListenable<T[]> {
    private final Class<T> type;
    private final Listenable<T>[] listenables;
    private Consumer<T[]> ignored;

    public ArrayListenable(Class<T> type, Listenable<T>[] listenables) {
        this.type = type;
        this.listenables = listenables;
        for (Listenable<T> listenable : listenables) {
            listenable.listen(v -> notifyListeners(null));
        }
    }

    @Override
    public T[] get() {
        int length = listenables.length;
        T[] arr = (T[]) Array.newInstance(type, length);
        for (int i = 0; i < length; i++) {
            arr[i] = listenables[i].get();
        }
        return arr;
    }

    @Override
    protected void notifyListeners(@Nullable Consumer<T[]> skip) {
        super.notifyListeners(skip == null ? ignored : skip);
    }

    @Override
    protected void setSafe(T[] value, @Nullable Consumer<T[]> listener) {
        Preconditions.checkArgument(value.length == listenables.length);
        ignored = listener;
        for (int i = 0; i < listenables.length; i++) {
            if (value[i] != listenables[i].get()) {
                listenables[i].set(value[i]);
            }
        }
        ignored = null;
    }

    @Override
    public void set(T[] value) {
        Preconditions.checkArgument(value.length == listenables.length);
        ignored = null;
        for (int i = 0; i < listenables.length; i++) {
            if (value[i] != listenables[i].get()) {
                listenables[i].set(value[i]);
            }
        }
    }
}
