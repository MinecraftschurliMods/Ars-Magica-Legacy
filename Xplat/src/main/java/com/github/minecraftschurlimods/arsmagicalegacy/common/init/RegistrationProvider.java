package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * Utility interface for multiloader registration.
 *
 * @param <T> the type of the objects that this class registers
 */
public interface RegistrationProvider<T> {
    /**
     * Get a {@link RegistrationProvider} for the given {@link ResourceKey} and mod id,
     * using the {@link Factory#INSTANCE}.
     * @return the {@link RegistrationProvider} for the given {@link ResourceKey} and mod id
     */
    static <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> key, String modId) {
        return Factory.INSTANCE.create(key, modId);
    }

    /**
     * Registers an object.
     *
     * @param name     the name of the object
     * @param supplier a supplier of the object to register
     * @param <I>      the type of the object
     * @return a wrapper containing the lazy registered object. <strong>Calling {@link RegistryObject#get() get} too early
     * on the wrapper might result in crashes!</strong>
     */
    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);

    /**
     * Gets all the objects currently registered.
     *
     * @return an <strong>immutable</strong> view of all the objects currently registered
     */
    Collection<RegistryObject<T>> getEntries();

    /**
     * Gets the mod id that this provider registers objects for.
     *
     * @return the mod id
     */
    String getModId();

    /**
     * Factory interface for {@link RegistrationProvider registration providers}. <br>
     * This class is loaded using {@link java.util.ServiceLoader Service Loaders}, and only one
     * should exist per mod loader.
     */
    interface Factory {
        /**
         * The singleton instance of the {@link Factory}.
         */
        Factory INSTANCE = ServiceLoader.load(Factory.class).findFirst().orElseThrow();

        /**
         * Creates a new {@link RegistrationProvider} for the {@link Registry} with the given {@link ResourceKey key}.
         *
         * @param registryKey the {@link ResourceKey} of the {@link Registry}
         * @param mod_id      the mod id of the mod
         * @return a new {@link RegistrationProvider} for the type T
         * @param <T>         the type of the {@link Registry}
         */
        <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey, String mod_id);
    }
}
