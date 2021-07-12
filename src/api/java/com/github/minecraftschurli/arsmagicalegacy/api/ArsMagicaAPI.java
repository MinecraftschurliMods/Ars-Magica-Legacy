package com.github.minecraftschurli.arsmagicalegacy.api;

import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Minecraftschurli
 * @version 2021-06-18
 */
public final class ArsMagicaAPI {
    private static final Lazy<IArsMagicaAPI> LAZY_INSTANCE = Lazy.concurrentOf(() -> {
        final Iterator<IArsMagicaAPI> iterator = ServiceLoader.load(IArsMagicaAPI.class).iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
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

    /**
     * The Interface representing the API
     */
    @SuppressWarnings("unused")
    public interface IArsMagicaAPI {
    }

    private static class StubArsMagicaAPI implements IArsMagicaAPI {
        private static final IArsMagicaAPI INSTANCE = new StubArsMagicaAPI();
    }
}
