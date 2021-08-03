package com.github.minecraftschurli.easyimclib;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2021-06-15
 */
@SuppressWarnings("unused")
public final class IMCHandler {
    private static final Map<String, IMCHandler> HANDLERS = new HashMap<>();
    private static final String RECEIVED_IMC = "Received IMC message with method '{}' from '{}'";
    private static final String NO_HANDLER = RECEIVED_IMC + ", but no handler for {0} is available!";

    private final Map<String, IIMCMethodHandler<?>> handlers = new HashMap<>();
    private final Logger logger;

    private final String modid;

    private IMCHandler(final String modid) {
        this.modid = modid;
        this.logger = LogManager.getLogger("IMCHandler(%s)".formatted(modid));
    }

    /**
     * Registers this {@link IMCHandler} to the EventBus
     *
     * @param bus the eventbus to register the listener to
     */
    public void init(final IEventBus bus) {
        this.logger.debug("Initializing IMCHandler({})", this.modid);
        bus.addListener(this::processIMC);
    }

    /**
     * Register a handler for an IMCMethod
     *
     * @param method  the method that is beeng specified
     * @param handler the handler for the method
     * @param <T> the type of the handler parameter
     */
    public <T> void registerIMCMethodHandler(final String method, final IIMCMethodHandler<T> handler) {
        this.logger.debug("Registering method {} for IMCHandler({})", method, this.modid);
        this.handlers.put(method, handler);
    }

    /**
     * Creates or gets the IMCHandler for the given modid
     *
     * @param modid the id of the mod this handler is for
     * @return the {@link IMCHandler} associated with the modid
     */
    public static IMCHandler create(final String modid) {
        return HANDLERS.computeIfAbsent(modid, IMCHandler::new);
    }

    private void processIMC(final InterModProcessEvent event) {
        event.getIMCStream().forEach(imcMessage -> {
            if (this.handlers.containsKey(imcMessage.method())) {
                this.logger.debug(RECEIVED_IMC, imcMessage.method(), imcMessage.senderModId());
                this.handlers.get(imcMessage.method()).accept(imcMessage.messageSupplier());
            } else {
                this.logger.warn(NO_HANDLER, imcMessage.method(), imcMessage.senderModId());
            }
        });
    }

    @Override
    public String toString() {
        return String.format("IMCHandler(modid='%s', methods=%s)", this.modid, this.handlers.keySet());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final IMCHandler that = (IMCHandler) o;

        return modid.equals(that.modid);
    }

    @Override
    public int hashCode() {
        return modid.hashCode();
    }

    /**
     * @param <T> the Type of the received IMCMessage
     */
    @FunctionalInterface
    public interface IIMCMethodHandler<T> extends Consumer<T> {
        /**
         * @param supplier the {@link Supplier} of the transmitted value
         */
        @SuppressWarnings("unchecked")
        default void accept(Supplier<?> supplier) {
            accept((T) supplier.get());
        }
    }
}
