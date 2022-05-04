package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 *
 */
public interface Context {
    Context EMPTY = new Context() {
        @Nullable
        @Contract("_, _ -> null")
        @Override
        public <T> T get(final String name, final Class<T> clazz) {
            return null;
        }
    };

    @Nullable <T> T get(String name, Class<T> clazz);


    record MapContext(Map<String, Object> data) implements Context {
        @Override
        public <T> T get(String name, Class<T> clazz) {
            if (data.containsKey(name)) {
                Object o = data.get(name);
                if (clazz.isInstance(o)) {
                    return clazz.cast(o);
                }
            }
            return null;
        }
    }
}
