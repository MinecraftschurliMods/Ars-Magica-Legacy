package com.github.minecraftschurli.arsmagicalegacy.api;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IKnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public final class ArsMagicaAPI {
    public static final String MOD_ID = "arsmagicalegacy";
    private static final Lazy<IArsMagicaAPI> LAZY_INSTANCE = Lazy.concurrentOf(() -> {
        try {
            //noinspection unchecked
            Class<? extends IArsMagicaAPI> clazz = (Class<? extends IArsMagicaAPI>) Class.forName(
                    ArsMagicaAPI.class
                            .getModule()
                            .getDescriptor()
                            .provides()
                            .stream()
                            .flatMap(provides -> provides.providers().stream())
                            .findFirst().orElseThrow());
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LogManager.getLogger(MOD_ID).error("Unable to find implementation for IArsMagicaAPI, using a dummy");
            return StubArsMagicaAPI.INSTANCE;
        }
        /*var impl = ServiceLoader.load(FMLLoader.getGameLayer(), IArsMagicaAPI.class).findFirst();
        if (!FMLEnvironment.production) {
            return impl.orElseThrow(() -> LogManager.getLogger(MOD_ID).throwing(new IllegalStateException("Unable to find implementation for IArsMagicaAPI")));
        }
        return impl.orElseGet(() -> {
            LogManager.getLogger(MOD_ID).error("Unable to find implementation for IArsMagicaAPI, using a dummy");
            return StubArsMagicaAPI.INSTANCE;
        });*/
    });

    private ArsMagicaAPI() {}

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
    @NonExtendable
    public interface IArsMagicaAPI {
        /**
         * Get the {@link CreativeModeTab} of the mod
         *
         * @return the {@link CreativeModeTab} of the mod
         */
        CreativeModeTab getCreativeModeTab();

        /**
         * Get the Arcane Compendium {@link ItemStack}
         *
         * @return the {@link ItemStack} for the Arcane Compendium
         */
        ItemStack getBookStack();

        /**
         * Get the registry for occulus tabs.
         *
         * @return the registry for occulus tabs
         */
        IForgeRegistry<IOcculusTab> getOcculusTabRegistry();

        /**
         * Get the registry for skill points.
         *
         * @return the registry for skill points
         */
        IForgeRegistry<ISkillPoint> getSkillPointRegistry();

        /**
         * Get the registry for affinities.
         *
         * @return the registry for affinities
         */
        IForgeRegistry<IAffinity> getAffinityRegistry();

        /**
         * Get the {@link ISkillManager} instance.
         *
         * @return the {@link ISkillManager} instance
         */
        ISkillManager getSkillManager();

        /**
         * Get the {@link IKnowledgeHelper} instance.
         *
         * @return the {@link IKnowledgeHelper} instance
         */
        IKnowledgeHelper getKnowledgeHelper();

        /**
         * Get the {@link IAffinityHelper} instance.
         *
         * @return the {@link IAffinityHelper} instance
         */
        IAffinityHelper getAffinityHelper();

        /**
         * Register a renderer for a occulus tab.
         *
         * @param tab the tab to register the renderer for
         * @param factory the factory for the renderer
         */
        void registerOcculusTabRenderer(IOcculusTab tab, Function<IOcculusTab, ? extends OcculusTabRenderer> factory);

        /**
         * Open the occulus gui for the given player.
         *
         * @param player the player to open the gui for
         */
        void openOcculusGui(Player player);
    }

    @SuppressWarnings("ConstantConditions")
    private static class StubArsMagicaAPI implements IArsMagicaAPI {
        private static final IArsMagicaAPI INSTANCE = new StubArsMagicaAPI();

        @Override
        public CreativeModeTab getCreativeModeTab() {
            return CreativeModeTab.TAB_MISC;
        }

        @Override
        public ItemStack getBookStack() {
            return ItemStack.EMPTY;
        }

        @Override
        public IForgeRegistry<IOcculusTab> getOcculusTabRegistry() {
            return null;
        }

        @Override
        public IForgeRegistry<ISkillPoint> getSkillPointRegistry() {
            return null;
        }

        @Override
        public ISkillManager getSkillManager() {
            return null;
        }

        @Override
        public IKnowledgeHelper getKnowledgeHelper() {
            return null;
        }

        @Override
        public void openOcculusGui(final Player pPlayer) {
        }

        @Override
        public IAffinityHelper getAffinityHelper() {
            return null;
        }

        @Override
        public void registerOcculusTabRenderer(IOcculusTab tab, Function<IOcculusTab, ? extends OcculusTabRenderer> factory) {
        }

        @Override
        public IForgeRegistry<IAffinity> getAffinityRegistry() {
            return null;
        }
    }
}
