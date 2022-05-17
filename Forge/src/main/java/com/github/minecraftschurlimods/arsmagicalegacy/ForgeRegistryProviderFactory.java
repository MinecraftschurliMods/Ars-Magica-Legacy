package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.RegistrationProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class ForgeRegistryProviderFactory implements RegistrationProvider.Factory {
    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        ModContainer cont = ModList.get().getModContainerById(modId).orElseThrow(() -> new NullPointerException("Cannot find mod container for id " + modId));
        if (cont instanceof FMLModContainer fmlModContainer) {
            var register = DeferredRegister.create(registryKey, modId);
            register.register(fmlModContainer.getEventBus());
            return new Provider<>(modId, register);
        } else {
            throw new ClassCastException("The container of the mod " + modId + " is not a FML one!");
        }
    }

    private static final class Provider<T> implements RegistrationProvider<T> {

        private final String modId;
        private final DeferredRegister<T> register;

        private final Set<RegistryObject<T>> entries     = new HashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        public Provider(String modId, DeferredRegister<T> register) {
            this.modId = modId;
            this.register = register;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            var wrapped = new ForgeRegistryObjectWrapper<>(register.<I>register(name, supplier));
            entries.add((RegistryObject<T>) wrapped);
            return wrapped;
        }

        @Unmodifiable
        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return entriesView;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }

    private record ForgeRegistryObjectWrapper<T>(net.minecraftforge.registries.RegistryObject<T> wrapped) implements RegistryObject<T> {
        @Override
        public ResourceKey<T> getResourceKey() {
            return wrapped.getKey();
        }

        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public T get() {
            return wrapped.get();
        }

        @Override
        public Holder<T> asHolder() {
            return wrapped.getHolder().orElseThrow();
        }

        @Override
        public boolean isPresent() {
            return wrapped.isPresent();
        }
    }
}
