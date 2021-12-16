package com.github.minecraftschurli.arsmagicalegacy.compat;

import com.github.minecraftschurli.arsmagicalegacy.compat.curios.CurioCompat;
import com.github.minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.google.common.reflect.ClassPath;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CompatManager {
    private static final Map<String, Lazy<ICompatHandler>> compatHandlers = new HashMap<>();

    public static void register(Class<? extends ICompatHandler> clazz) {
        if (!clazz.isAnnotationPresent(ModCompat.class)) throw new IllegalArgumentException();
        register(clazz.getAnnotation(ModCompat.class).value(), clazz);
    }

    public static void register(String modid, Class<? extends ICompatHandler> clazz) {
        register(modid, supplierFromClass(clazz));
    }

    public static void register(String modid, Supplier<ICompatHandler> supplier) {
        if (supplier instanceof Lazy<ICompatHandler> lazy) {
            compatHandlers.putIfAbsent(modid, lazy);
        } else {
            compatHandlers.putIfAbsent(modid, Lazy.of(supplier));
        }
    }

    public static void preInit() {
        //discoverModCompats();
        register(CurioCompat.class);
        register(PatchouliCompat.class);
        forEachLoaded(ICompatHandler::preInit);
    }

    public static void init(FMLCommonSetupEvent event) {
        forEachLoaded(wrap(ICompatHandler::init).apply(event));
    }

    public static void clientInit(FMLClientSetupEvent event) {
        forEachLoaded(wrap(ICompatHandler::clientInit).apply(event));
    }

    public static <T extends ICompatHandler> LazyOptional<T> getHandler(String modid) {
        if (!ModList.get().isLoaded(modid) || !compatHandlers.containsKey(modid)) return LazyOptional.empty();
        return LazyOptional.of(compatHandlers.get(modid)::get).cast();
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    private static void discoverModCompats() {
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(CompatManager.class.getClassLoader()).getAllClasses()) {
                Class<?> clazz = classInfo.load();
                if (ICompatHandler.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(ModCompat.class)) {
                    register((Class<? extends ICompatHandler>) clazz);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Supplier<ICompatHandler> supplierFromClass(Class<? extends ICompatHandler> clazz) {
        return Lazy.of(ThrowingSupplier.wrap(() -> clazz.getConstructor().newInstance()));
    }

    private static <T> Function<T, Consumer<ICompatHandler>> wrap(BiConsumer<ICompatHandler, T> consumer) {
        return t -> iCompatHandler -> consumer.accept(iCompatHandler, t);
    }

    private static void forEachLoaded(Consumer<ICompatHandler> consumer) {
        ModList modList = ModList.get();
        compatHandlers.entrySet()
                .stream()
                .filter(entry -> modList.isLoaded(entry.getKey()))
                .map(Map.Entry::getValue)
                .map(Lazy::get)
                .forEach(consumer);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModCompat {
        String value();
    }

    private interface ThrowingSupplier<T> extends Supplier<T> {
        static <T> Supplier<T> wrap(ThrowingSupplier<T> throwing) {
            return throwing;
        }

        @Override
        default T get() {
            try {
                return this.getThrowing();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        T getThrowing() throws Exception;
    }
}
