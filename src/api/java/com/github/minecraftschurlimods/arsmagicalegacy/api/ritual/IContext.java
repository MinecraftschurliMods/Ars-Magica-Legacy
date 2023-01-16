package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IContext {
    IContext EMPTY = new IContext() {
        @Nullable
        @Override
        public <T> T get(final String name, final Class<T> clazz) {
            return null;
        }
    };

    @Nullable <T> T get(String name, Class<T> clazz);

    record MapContext(Map<String, Object> data) implements IContext {
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
