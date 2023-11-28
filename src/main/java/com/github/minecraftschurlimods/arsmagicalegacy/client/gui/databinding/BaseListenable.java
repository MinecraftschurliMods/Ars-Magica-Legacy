package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BaseListenable<T> implements Listenable<T> {
    private final List<Consumer<T>> listeners = new ArrayList<>();
    private T value;

    BaseListenable() {}

    BaseListenable(T value) {
        this.value = value;
    }

    protected void addListener(Consumer<T> listener) {
        listeners.add(listener);
    }

    protected void removeListener(Consumer<T> listener) {
        listeners.remove(listener);
    }

    protected final void _set(T value) {
        this.value = value;
    }

    protected void notifyListeners(@Nullable Consumer<T> skip) {
        T t = get();
        for (Consumer<T> listener : listeners) {
            if (listener != skip) {
                listener.accept(t);
            }
        }
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        if (value == this.value) return;
        _set(value);
        notifyListeners(null);
    }

    protected void setSafe(T value, @Nullable Consumer<T> listener) {
        if (value == this.value) return;
        this._set(value);
        notifyListeners(listener);
    }

    @Override
    public Canceller listen(Consumer<T> listener) {
        Preconditions.checkNotNull(listener);
        addListener(listener);
        listener.accept(get());
        return () -> removeListener(listener);
    }

    @Override
    public <O> Listenable<O> map(Function<T, O> mapper) {
        Preconditions.checkNotNull(mapper);
        Listenable<O> listenable = Listenable.create();
        listen(value -> listenable.set(mapper.apply(value)));
        return listenable;
    }

    @Override
    public <O> Listenable<O> xmap(Function<T, O> mapper, Function<O, T> remapper) {
        return new XMappedListenable<>(this, mapper, remapper);
    }

    @Override
    public Canceller bind(Listenable<T> listenable) {
        Preconditions.checkNotNull(listenable);
        return bind(listenable::set, listenable::listen);
    }

    @Override
    public Canceller bind(Consumer<T> setter, Consumer<Consumer<T>> listener) {
        Preconditions.checkNotNull(setter);
        Preconditions.checkNotNull(listener);
        listener.accept(value1 -> setSafe(value1, setter));
        return listen(setter);
    }
}
