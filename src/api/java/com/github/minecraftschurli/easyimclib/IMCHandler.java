package com.github.minecraftschurli.easyimclib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
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
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, IMCHandler> HANDLERS = new HashMap<>();

    private final Map<String, IIMCMethodHandler<?>> handlers = new HashMap<>();
    private final String modid;

    private IMCHandler(final String modid) {
        this.modid = modid;
    }

    /**
     * Registers this {@link IMCHandler} to the EventBus
     */
    public void init() {
        MinecraftForge.EVENT_BUS.addListener(this::processIMC);
    }

    /**
     * Register a handler for an IMCMethod
     *
     * @param method  the method that is beeng specified
     * @param handler the handler for the method
     * @param <T> the type of the handler parameter
     */
    public <T> void registerIMCHandler(final String method, final IIMCMethodHandler<T> handler) {
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
        this.handlers.forEach((method, handler) -> event.getIMCStream(method::equals).forEach(imcMessage -> handleIMC(imcMessage, handler)));
    }

    private <T> void handleIMC(final InterModComms.IMCMessage imcMessage, final IIMCMethodHandler<T> handler) {
        LOGGER.debug("Received IMC message from {}", imcMessage.getSenderModId());
        handler.accept(imcMessage.getMessageSupplier());
    }

    /**
     * @param <T> the Type of the received IMCMessage
     */
    public interface IIMCMethodHandler<T> extends Consumer<Supplier<T>> {
    }
}
