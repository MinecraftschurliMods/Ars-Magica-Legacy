package com.github.minecraftschurlimods.arsmagicalegacy.compat;

import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CompatManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, Lazy<ICompatHandler>> compatHandlers = new HashMap<>();

    /**
     * Registers a compat handler.
     *
     * @param clazz The compat handler class to register.
     */
    public static void register(Class<? extends ICompatHandler> clazz) {
        if (!clazz.isAnnotationPresent(ModCompat.class)) throw new IllegalArgumentException("Tried to register an invalid mod compatibility handler!");
        register(clazz.getAnnotation(ModCompat.class).value(), clazz);
    }

    /**
     * Registers a compat handler.
     *
     * @param modid The compat mod id to register.
     * @param clazz The compat handler class to register.
     */
    public static void register(String modid, Class<? extends ICompatHandler> clazz) {
        register(modid, supplierFromClass(clazz));
    }

    /**
     * Registers a compat handler.
     *
     * @param modid    The compat mod id to register.
     * @param supplier A supplier of the compat handler class to register.
     */
    public static void register(String modid, Supplier<ICompatHandler> supplier) {
        if (supplier instanceof Lazy<ICompatHandler> lazy) {
            compatHandlers.putIfAbsent(modid, lazy);
        } else {
            compatHandlers.putIfAbsent(modid, Lazy.of(supplier));
        }
    }

    /**
     * Discovers and pre-initializes mod compats.
     */
    public static void preInit() {
        discoverModCompats();
        forEachLoaded(ICompatHandler::preInit);
    }

    /**
     * Initializes mod compats.
     */
    public static void init(FMLCommonSetupEvent event) {
        forEachLoaded(wrap(ICompatHandler::init).apply(event));
    }

    /**
     * Initializes client-sided mod compats.
     */
    public static void clientInit(FMLClientSetupEvent event) {
        forEachLoaded(wrap(ICompatHandler::clientInit).apply(event));
    }

    /**
     * Enqueues mod compats for the InterModEnqueueEvent.
     */
    public static void imcEnqueue(InterModEnqueueEvent event) {
        forEachLoaded(wrap(ICompatHandler::imcEnqueue).apply(event));
    }

    /**
     * @param modid The mod id to get the compat handler for.
     * @param <T>   The type of the compat handler.
     * @return The compat handler for the given id, if present.
     */
    public static <T extends ICompatHandler> LazyOptional<T> getHandler(String modid) {
        if (!ModList.get().isLoaded(modid) || !compatHandlers.containsKey(modid)) return LazyOptional.empty();
        return LazyOptional.of(compatHandlers.get(modid)::get).cast();
    }

    private static void discoverModCompats() {
        getClasses(ModCompat.class, ICompatHandler.class).forEach(CompatManager::register);
    }

    private static <T> List<Class<? extends T>> getClasses(Class<?> annotationClass, Class<T> instanceClass) {
        Type annotationType = Type.getType(annotationClass);
        return ModList.get().getAllScanData()
                .stream()
                .map(ModFileScanData::getAnnotations)
                .flatMap(Collection::stream)
                .filter(annotationData -> Objects.equals(annotationData.annotationType(), annotationType))
                .map(ModFileScanData.AnnotationData::memberName)
                .<Class<? extends T>>map(memberName -> {
                    try {
                        return Class.forName(memberName).asSubclass(instanceClass);
                    } catch (ReflectiveOperationException | LinkageError | ClassCastException e) {
                        LOGGER.error("Failed to load: {}", memberName, e);
                    }
                    return null;
                }).toList();
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
                return getThrowing();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        T getThrowing() throws Exception;
    }
}
