package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.patchouli_datagen.BookBuilder;
import com.github.minecraftschurli.patchouli_datagen.PatchouliBookProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class ArsMagicaLegacyDatagen {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @param evt the event object of the {@link GatherDataEvent}
     */
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent evt) {
        LOGGER.info("Running Datagens");
        ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        DataGenerator generator = evt.getGenerator();
        if (evt.includeServer()) {
            TagsProvider.setup(generator, existingFileHelper);
        }
        //generator.addProvider(new AMPatchouliBookProvider(generator, evt.includeClient(), evt.includeServer()));
    }

    /*private static class AMPatchouliBookProvider extends PatchouliBookProvider {
        public AMPatchouliBookProvider(DataGenerator generator, boolean includeClient, boolean includeServer) {
            super(generator, ArsMagicaAPI.MOD_ID, "en_us", includeClient, includeServer);
        }

        @Override
        protected void addBooks(final Consumer<BookBuilder> consumer) {
            createBookBuilder("arcane_compendium")
                    .setModel(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"))
                    .setCreativeTab(ArsMagicaAPI.get().getItemGroup().getRecipeFolderName())
                    .setUseResourcepack()
                    .setUseI18n()
                    .build(consumer);
        }
    }*/
}
