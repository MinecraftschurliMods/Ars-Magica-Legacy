package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        LanguageProvider lang = new AMEnglishLanguageProvider(generator);
        generator.addProvider(new AMPatchouliBookProvider(generator, ArsMagicaAPI.MOD_ID, lang, evt.includeClient(), evt.includeServer()));
        if (evt.includeClient()) {
            generator.addProvider(lang);
            generator.addProvider(new AMItemModelProvider(generator, existingFileHelper));
            generator.addProvider(new AMBlockStateProvider(generator, existingFileHelper));
        }
    }

}
