package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class AMDatagen {
    @SubscribeEvent
    static void gatherData(GatherDataEvent evt) {
        ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        DataGenerator generator = evt.getGenerator();
        boolean includeClient = evt.includeClient();
        boolean includeServer = evt.includeServer();
        LanguageProvider lang = new AMEnglishLanguageProvider(generator);
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
        AMAbilityProvider abilityProvider = new AMAbilityProvider(generator, existingFileHelper, registryOps);
        AMSkillProvider skillProvider = new AMSkillProvider(generator, existingFileHelper, registryOps);
        generator.addProvider(includeServer, abilityProvider);
        generator.addProvider(includeServer, skillProvider);
        generator.addProvider(includeServer, new AMAdvancementProvider(generator, existingFileHelper, skillProvider));
        generator.addProvider(includeServer, new AMLootTableProvider(generator));
        generator.addProvider(includeServer, new AMRecipeProvider(generator));
        AMTagsProvider.add(includeServer, generator, existingFileHelper);
        generator.addProvider(includeServer, new AMGlobalLootModifierProvider(generator));
        generator.addProvider(includeServer, new AMAltarStructureMaterialProvider(generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMAltarCapMaterialProvider(generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMObeliskFuelProvider(generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMOcculusTabProvider(generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMPrefabSpellProvider(lang, generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMSpellPartDataProvider(generator));
        generator.addProvider(includeServer, new AMSpellTransformationProvider(generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMRitualProvider(generator, existingFileHelper, registryOps));
        generator.addProvider(includeServer, new AMWorldgenProvider(generator, existingFileHelper));
        generator.addProvider(includeClient, new AMBlockStateProvider(generator, existingFileHelper));
        generator.addProvider(includeClient, new AMItemModelProvider(generator, existingFileHelper));
        generator.addProvider(includeClient || includeServer, new AMPatchouliBookProvider(generator, abilityProvider, lang, includeClient, includeServer));
        generator.addProvider(includeClient, new AMParticleDefinitionsProvider(generator));
        generator.addProvider(includeClient, new AMSoundDefinitionsProvider(generator, existingFileHelper));
        generator.addProvider(includeClient, lang);
        new AMCompatDataProvider(evt);
    }
}
