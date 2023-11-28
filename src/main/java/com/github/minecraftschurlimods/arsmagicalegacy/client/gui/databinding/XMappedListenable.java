package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding;

import com.google.common.base.Preconditions;

import java.util.function.Consumer;
import java.util.function.Function;

class XMappedListenable<T, O> implements Listenable<O> {
    private final Listenable<T> parent;
    private final Function<T, O> mapper;
    private final Function<O, T> remapper;

    public XMappedListenable(Listenable<T> parent, Function<T, O> mapper, Function<O, T> remapper) {
        this.parent = Preconditions.checkNotNull(parent);
        this.mapper = Preconditions.checkNotNull(mapper);
        this.remapper = Preconditions.checkNotNull(remapper);
    }

    @Override
    public O get() {
        return mapper.apply(parent.get());
    }

    @Override
    public Canceller listen(Consumer<O> listener) {
        Preconditions.checkNotNull(listener);
        return parent.listen(value -> listener.accept(mapper.apply(value)));
    }

    @Override
    public <O1> Listenable<O1> map(Function<O, O1> mapper) {
        Preconditions.checkNotNull(mapper);
        Listenable<O1> listenable = Listenable.create();
        listen(value -> listenable.set(mapper.apply(value)));
        return listenable;
    }

    @Override
    public void set(O value) {
        try {
            parent.set(remapper.apply(value));
        } catch (Exception ignored) {}
    }

    @Override
    public <O1> Listenable<O1> xmap(Function<O, O1> mapper, Function<O1, O> remapper) {
        Preconditions.checkNotNull(mapper);
        Preconditions.checkNotNull(remapper);
        return parent.xmap(t -> mapper.apply(this.mapper.apply(t)), t -> this.remapper.apply(remapper.apply(t)));
    }

    @Override
    public Canceller bind(Listenable<O> listenable) {
        Preconditions.checkNotNull(listenable);
        return parent.bind(listenable.xmap(remapper, mapper));
    }

    @Override
    public Canceller bind(Consumer<O> setter, Consumer<Consumer<O>> listener) {
        Preconditions.checkNotNull(setter);
        Preconditions.checkNotNull(listener);
        return parent.bind(value -> setter.accept(mapper.apply(value)), listener1 -> listener.accept(value -> listener1.accept(remapper.apply(value))));
    }
}
