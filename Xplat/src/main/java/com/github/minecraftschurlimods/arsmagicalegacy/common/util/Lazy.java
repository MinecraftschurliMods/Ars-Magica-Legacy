package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import java.util.function.Supplier;

public sealed interface Lazy<T> extends Supplier<T> {
    static <T> Lazy<T> of(Supplier<T> supplier) {
        return new LazyImpl<>(supplier);
    }

    final class LazyImpl<T> implements Lazy<T> {
        private T value;
        private Supplier<T> supplier;

        public LazyImpl(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            if (supplier != null) {
                value = supplier.get();
                supplier = null;
            }
            return value;
        }
    }
}
