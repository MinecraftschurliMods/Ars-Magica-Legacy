package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding;

import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class DerivedListenable2<T, T1, T2> extends BaseListenable<T> {
    private final Listenable<T1> listenable1;
    private final Listenable<T2> listenable2;
    private final BiFunction<T1, T2, T> mapper;
    private final Function<T, T1> inverse1;
    private final Function<T, T2> inverse2;
    private Consumer<T> ignored;

    public DerivedListenable2(Listenable<T1> listenable1, Listenable<T2> listenable2, BiFunction<T1, T2, T> mapper, Function<T, T1> inverse1, Function<T, T2> inverse2) {
        this.listenable1 = listenable1;
        this.listenable2 = listenable2;
        this.mapper = mapper;
        this.inverse1 = inverse1;
        this.inverse2 = inverse2;
        listenable1.listen(v -> notifyListeners(null));
        listenable2.listen(v -> notifyListeners(null));
    }

    @Override
    public T get() {
        try {
            return mapper.apply(listenable1.get(), listenable2.get());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void notifyListeners(@Nullable Consumer<T> skip) {
        super.notifyListeners(skip == null ? ignored : skip);
    }

    @Override
    protected void setSafe(T value, @Nullable Consumer<T> listener) {
        ignored = listener;
        listenable1.set(inverse1.apply(value));
        listenable2.set(inverse2.apply(value));
        ignored = null;
    }

    @Override
    public void set(T value) {
        ignored = null;
        listenable1.set(inverse1.apply(value));
        listenable2.set(inverse2.apply(value));
    }
}
