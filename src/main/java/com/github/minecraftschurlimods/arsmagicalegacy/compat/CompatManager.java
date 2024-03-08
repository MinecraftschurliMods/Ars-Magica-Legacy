package com.github.minecraftschurlimods.arsmagicalegacy.compat;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CompatManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<String, Lazy<ICompatHandler>> compatHandlers = new HashMap<>();

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
    public static void preInit(IEventBus bus) {
        discoverModCompats();
        forEachLoaded(wrap(ICompatHandler::preInit).apply(bus));
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

    private static void discoverModCompats() {
        Type annotationType = Type.getType(ModCompat.class);
        for (ModFileScanData modFileScanData : ModList.get().getAllScanData()) {
            Set<ModFileScanData.AnnotationData> annotations = modFileScanData.getAnnotations();
            for (ModFileScanData.AnnotationData annotationData : annotations) {
                if (Objects.equals(annotationData.annotationType(), annotationType)) {
                    String memberName = annotationData.memberName();
                    String modid = annotationData.annotationData().get("value").toString();
                    register(modid, Lazy.of(() -> {
                        Class<? extends ICompatHandler> clazz; 
                        try {
                            clazz = Class.forName(memberName).asSubclass(ICompatHandler.class);
                        } catch (ReflectiveOperationException | LinkageError | ClassCastException e) {
                            LOGGER.error("Failed to load compat manager for modid {}: {}", modid, memberName, e);
                            return null;
                        }
                        try {
                            return clazz.getConstructor().newInstance();
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            LOGGER.error("Failed to instantiate compat manager for modid {}: {}", modid, memberName, e);
                            return null;
                        }
                    }));
                }
            }
        }
    }

    private static <T> Function<T, Consumer<ICompatHandler>> wrap(BiConsumer<ICompatHandler, T> consumer) {
        return t -> iCompatHandler -> consumer.accept(iCompatHandler, t);
    }

    private static void forEachLoaded(Consumer<ICompatHandler> consumer) {
        ModList modList = ModList.get();
        compatHandlers
                .entrySet()
                .stream()
                .filter(entry -> modList.isLoaded(entry.getKey()))
                .map(Map.Entry::getValue)
                .map(Supplier::get)
                .forEach(consumer);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModCompat {
        String value();
    }
}
