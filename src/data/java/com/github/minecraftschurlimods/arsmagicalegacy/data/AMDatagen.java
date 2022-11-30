package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.handler.EventHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class AMDatagen {
    private static final Logger LOGGER = LogUtils.getLogger();

    /**
     * @param evt The event object of the {@link GatherDataEvent}.
     */
    @SubscribeEvent
    static void gatherData(GatherDataEvent evt) {
        EventHandler.registerSpellIngredientTypes();
        LOGGER.info("Running Datagens");
        ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        DataGenerator generator = evt.getGenerator();
        LanguageProvider lang = new AMEnglishLanguageProvider(generator);
        AMAbilityProvider abilityProvider = new AMAbilityProvider(generator, existingFileHelper);
        AMSkillProvider skillProvider = new AMSkillProvider(generator, existingFileHelper);
        generator.addProvider(evt.includeServer(), abilityProvider);
        generator.addProvider(evt.includeServer(), skillProvider);
        generator.addProvider(evt.includeServer(), new AMAdvancementProvider(generator, existingFileHelper, skillProvider));
        generator.addProvider(evt.includeServer(), new AMLootTableProvider(generator));
        generator.addProvider(evt.includeServer(), new AMRecipeProvider(generator));
        AMTagsProvider.add(evt.includeServer(), generator, existingFileHelper);
        generator.addProvider(evt.includeServer(), new AMAltarStructureMaterialProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeServer(), new AMObeliskFuelProvider(generator));
        generator.addProvider(evt.includeServer(), new AMOcculusTabProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeServer(), new AMPrefabSpellProvider(generator, existingFileHelper, lang));
        generator.addProvider(evt.includeServer(), new AMSpellPartDataProvider(generator));
        generator.addProvider(evt.includeServer(), new AMSpellTransformationProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeServer(), new AMRitualProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeServer(), new AMWorldgenProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeClient(), new AMBlockStateProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeClient(), new AMItemModelProvider(generator, existingFileHelper));
        generator.addProvider(true, new AMPatchouliBookProvider(generator, abilityProvider, lang, evt.includeClient(), evt.includeServer()));
        generator.addProvider(evt.includeClient(), new AMSoundDefinitionsProvider(generator, existingFileHelper));
        generator.addProvider(evt.includeClient(), lang);
    }
}
