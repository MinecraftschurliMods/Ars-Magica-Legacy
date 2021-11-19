package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.EventHandler;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class AMDatagen {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @param evt the event object of the {@link GatherDataEvent}
     */
    @SubscribeEvent
    static void gatherData(GatherDataEvent evt) {
        EventHandler.registerSpellIngredientTypes();
        LOGGER.info("Running Datagens");
        ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        DataGenerator generator = evt.getGenerator();
        if (evt.includeServer()) {
            generator.addProvider(new AMSkillProvider(generator));
            generator.addProvider(new AMAdvancementProvider(generator));
            generator.addProvider(new AMLootTableProvider(generator));
            generator.addProvider(new AMRecipeProvider(generator));
            AMTagsProvider.setup(generator, existingFileHelper);
            generator.addProvider(new AMSpellPartDataProvider(generator));
            generator.addProvider(new AMOcculusTabProvider(generator));
            generator.addProvider(new AMAltarStructureMaterialProvider(generator));
        }
        LanguageProvider lang = new AMEnglishLanguageProvider(generator);
        generator.addProvider(new AMPatchouliBookProvider(generator, ArsMagicaAPI.MOD_ID, lang, evt.includeClient(), evt.includeServer()));
        if (evt.includeClient()) {
            generator.addProvider(lang);
            generator.addProvider(new AMBlockStateProvider(generator, existingFileHelper));
            generator.addProvider(new AMItemModelProvider(generator, existingFileHelper));
        }
    }
}
