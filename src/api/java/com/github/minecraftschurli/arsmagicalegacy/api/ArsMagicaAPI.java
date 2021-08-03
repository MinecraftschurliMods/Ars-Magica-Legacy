package com.github.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.ServiceLoader;

/**
 * @author Minecraftschurli
 * @version 2021-06-18
 */
public final class ArsMagicaAPI {
    public static final String MOD_ID = "arsmagicalegacy";
    private static final Lazy<IArsMagicaAPI> LAZY_INSTANCE = Lazy.concurrentOf(() -> ServiceLoader
            .load(IArsMagicaAPI.class)
            .stream()
            .findFirst()
            .map(ServiceLoader.Provider::get)
            .orElseGet(() -> {
                LogManager.getLogger().warn("Unable to find ArsMagicaAPIImpl, using a dummy");
                return StubArsMagicaAPI.INSTANCE;
            }));

    private ArsMagicaAPI() { }

    /**
     * Get the API Instance
     *
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
        /**
         * Get the {@link CreativeModeTab} of the mod
         *
         * @return the {@link CreativeModeTab} of the mod
         */
        CreativeModeTab getItemGroup();

        /**
         * Get the Arcane Compendium {@link ItemStack}
         *
         * @return the {@link ItemStack} for the Arcane Compendium
         */
        ItemStack getBookStack();
    }

    private static class StubArsMagicaAPI implements IArsMagicaAPI {
        private static final IArsMagicaAPI INSTANCE = new StubArsMagicaAPI();

        @Override
        public CreativeModeTab getItemGroup() {
            return CreativeModeTab.TAB_MISC;
        }

        @Override
        public ItemStack getBookStack() {
            return ItemStack.EMPTY;
        }
    }
}
