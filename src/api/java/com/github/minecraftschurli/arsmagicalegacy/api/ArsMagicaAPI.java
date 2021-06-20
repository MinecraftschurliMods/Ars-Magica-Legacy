package com.github.minecraftschurli.arsmagicalegacy.api;

import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

//import java.util.ServiceLoader;

/**
 * @author Minecraftschurli
 * @version 2021-06-18
 */
public final class ArsMagicaAPI {
    private static final Lazy<IArsMagicaAPI> LAZY_INSTANCE = Lazy.concurrentOf(() -> {
        /*return ServiceLoader.load(ArsMagicaAPI.class)
                .stream()
                .findFirst()
                .map(ServiceLoader.Provider::get)
                .orElse(StubArsMagicaAPI.INSTANCE);*/ // TODO: 1.17
        try {
            return (IArsMagicaAPI) Class.forName("com.github.minecraftschurli.arsmagicalegacy.ArsMagicaAPIImpl").getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            LogManager.getLogger().warn("Unable to find ArsMagicaAPIImpl, using a dummy");
            return StubArsMagicaAPI.INSTANCE;
        }
    });

    /**
     * Get the API Instance
     * @return the API Instance
     */
    public static IArsMagicaAPI get() {
        return LAZY_INSTANCE.get();
    }

    public interface IArsMagicaAPI {
    }

    private static class StubArsMagicaAPI implements IArsMagicaAPI {
        private static final IArsMagicaAPI INSTANCE = new StubArsMagicaAPI();
    }
}
