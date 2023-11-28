package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding;

import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Listenable<T> extends Supplier<T> {

    static <T> Listenable<T> create() {
        return new BaseListenable<>();
    }

    static <T> Listenable<T> create(T initialValue) {
        return new BaseListenable<>(initialValue);
    }

    static <T, T1, T2> Listenable<T> derived(Listenable<T1> listenable1, Listenable<T2> listenable2, BiFunction<T1, T2, T> mapper, Function<T, T1> inverse1, Function<T, T2> inverse2) {
        return new DerivedListenable2<>(listenable1, listenable2, mapper, inverse1, inverse2);
    }

    static <T, T1, T2, T3> Listenable<T> derived(Listenable<T1> listenable1, Listenable<T2> listenable2, Listenable<T3> listenable3, TriFunction<T1, T2, T3, T> mapper, Function<T, T1> inverse1, Function<T, T2> inverse2, Function<T, T3> inverse3) {
        return new DerivedListenable3<>(listenable1, listenable2, listenable3, mapper, inverse1, inverse2, inverse3);
    }

    @SafeVarargs
    static <T> Listenable<T[]> array(Class<T> type, Listenable<T>... listenables) {
        return new ArrayListenable<>(type, listenables);
    }

    void set(T value);

    Canceller listen(Consumer<T> listener);

    <O> Listenable<O> map(Function<T, O> mapper);

    <O> Listenable<O> xmap(Function<T, O> mapper, Function<O, T> remapper);

    Canceller bind(Listenable<T> listenable);

    Canceller bind(Consumer<T> setter, Consumer<Consumer<T>> listener);
}
