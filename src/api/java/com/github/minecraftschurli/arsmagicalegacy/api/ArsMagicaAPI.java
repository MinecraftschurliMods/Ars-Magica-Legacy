package com.github.minecraftschurli.arsmagicalegacy.api;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTabManager;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.*;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.lang.reflect.InvocationTargetException;

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
         * Get the registry for spell parts.
         *
         * @return the registry for spell parts
         */
        IForgeRegistry<ISpellPart> getSpellPartRegistry();

        /**
         * Get the {@link ISkillManager} instance.
         *
         * @return the {@link ISkillManager} instance
         */
        ISkillManager getSkillManager();

        /**
         * Get the {@link IOcculusTabManager} instance.
         *
         * @return the {@link IOcculusTabManager} instance
         */
        IOcculusTabManager getOcculusTabManager();

        /**
         * Get the {@link ISpellDataManager} instance.
         *
         * @return the {@link ISpellDataManager} instance
         */
        ISpellDataManager getSpellDataManager();

        /**
         * Get the {@link ISkillHelper} instance.
         *
         * @return the {@link ISkillHelper} instance
         */
        ISkillHelper getSkillHelper();

        /**
         * Get the {@link IAffinityHelper} instance.
         *
         * @return the {@link IAffinityHelper} instance
         */
        IAffinityHelper getAffinityHelper();

        /**
         * Get the magic helper instance.
         *
         * @return the magic helper instance
         */
        IMagicHelper getMagicHelper();

        /**
         * Open the occulus gui for the given player.
         *
         * @param player the player to open the gui for
         */
        void openOcculusGui(Player player);

        /**
         * Get the spell helper instance.
         * @return the spell helper instance
         */
        ISpellHelper getSpellHelper();

        /**
         * Open the spell customization gui for the given spell (the given stack).<br>
         * Only works on the client.
         *
         * @side client
         */
        void openSpellCustomizationGui(Level level, Player player, ItemStack stack);
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
        public IForgeRegistry<ISkillPoint> getSkillPointRegistry() {
            return null;
        }

        @Override
        public ISkillManager getSkillManager() {
            return null;
        }

        @Override
        public IOcculusTabManager getOcculusTabManager() {
            return null;
        }

        @Override
        public ISpellDataManager getSpellDataManager() {
            return null;
        }

        @Override
        public ISkillHelper getSkillHelper() {
            return null;
        }

        @Override
        public void openOcculusGui(final Player pPlayer) {
        }

        @Override
        public ISpellHelper getSpellHelper() {
            return null;
        }

        @Override
        public void openSpellCustomizationGui(final Level level, final Player player, final ItemStack stack) {
        }

        @Override
        public IAffinityHelper getAffinityHelper() {
            return null;
        }

        @Override
        public IMagicHelper getMagicHelper() {
            return null;
        }

        @Override
        public IForgeRegistry<IAffinity> getAffinityRegistry() {
            return null;
        }

        @Override
        public IForgeRegistry<ISpellPart> getSpellPartRegistry() {
            return null;
        }
    }
}
