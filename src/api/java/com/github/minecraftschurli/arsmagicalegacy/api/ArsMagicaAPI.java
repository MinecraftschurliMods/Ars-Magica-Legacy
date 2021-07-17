package com.github.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
        /**
         * Get the {@link ItemGroup} of the mod
         *
         * @return the {@link ItemGroup} of the mod
         */
        ItemGroup getItemGroup();

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
        public ItemGroup getItemGroup() {
            return ItemGroup.TAB_MISC;
        }

        @Override
        public ItemStack getBookStack() {
            return ItemStack.EMPTY;
        }
    }
}
