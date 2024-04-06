package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.data.update120.Update120BlockTagsProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.data.update120.Update120LootTableProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.data.update120.Update120RecipeProvider;
import com.github.minecraftschurlimods.easydatagenlib.api.DatapackRegistryGenerator;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import net.minecraft.DetectedVersion;
import net.minecraft.WorldVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import org.apache.commons.lang3.function.TriFunction;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class AMDatagen {
    @SubscribeEvent
    static void gatherData(GatherDataEvent evt) {
        ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        DataGenerator generator = evt.getGenerator();
        boolean includeClient = evt.includeClient();
        boolean includeServer = evt.includeServer();
        DataGenerator.PackGenerator common = generator.getVanillaPack(includeClient || includeServer);
        DataGenerator.PackGenerator server = generator.getVanillaPack(includeServer);
        DataGenerator.PackGenerator client = generator.getVanillaPack(includeClient);
        Map<String, String> lang = new HashMap<>();
        BiConsumer<String, String> langConsumer = lang::put;
        common.addProvider(wrapWith(AMDatagen::createMetaGenerator, evt.getModContainer().getModInfo()));
        CompletableFuture<HolderLookup.Provider> lookupProvider = server.addProvider(wrapWith(AMDatagen::createDatapackGenerator, evt.getLookupProvider(), langConsumer)).getHolderLookupProvider();
        common.addProvider(wrapWith(AMPatchouliBookProvider::new, lookupProvider, langConsumer, includeClient, includeServer));
        server.addProvider(wrapWith(AMAdvancements::new, lookupProvider, existingFileHelper));
        server.addProvider(AMLootTableProvider::new);
        server.addProvider(AMRecipeProvider::new);
        AMTagsProvider.Blocks blocks = server.addProvider(wrapWith(AMTagsProvider.Blocks::new, lookupProvider, existingFileHelper));
        server.addProvider(AMGlobalLootModifierProvider::new);
        server.addProvider(wrapWith(AMTagsProvider.Items::new, lookupProvider, blocks.contentsGetter(), existingFileHelper));
        server.addProvider(wrapWith(AMTagsProvider.Fluids::new, lookupProvider, existingFileHelper));
        server.addProvider(wrapWith(AMTagsProvider.EntityTypes::new, lookupProvider, existingFileHelper));
        server.addProvider(wrapWith(AMTagsProvider.Biomes::new, lookupProvider, existingFileHelper));
        server.addProvider(wrapWith(AMTagsProvider.DamageTypes::new, lookupProvider, existingFileHelper));
        server.addProvider(AMSpellPartDataProvider::new);
        client.addProvider(wrapWith(AMSpriteSourceProvider::new, existingFileHelper));
        client.addProvider(wrapWith(AMBlockStateProvider::new, existingFileHelper));
        client.addProvider(wrapWith(AMItemModelProvider::new, existingFileHelper));
        client.addProvider(wrapWith(AMParticleDefinitionsProvider::new, existingFileHelper));
        client.addProvider(wrapWith(AMSoundDefinitionsProvider::new, existingFileHelper));
        client.addProvider(wrapWith(AMEnglishLanguageProvider::new, lang));
        DataGenerator.PackGenerator update120 = generator.getBuiltinDatapack(includeServer, "update_1_20");
        update120.addProvider(Update120RecipeProvider::new);
        update120.addProvider(wrapWith(Update120BlockTagsProvider::new, lookupProvider, existingFileHelper));
        update120.addProvider(Update120LootTableProvider::new);
        new AMCompatDataProvider(evt);
    }

    private static PackMetadataGenerator createMetaGenerator(PackOutput output, IModInfo modInfo) {
        WorldVersion version = DetectedVersion.BUILT_IN;
        Map<PackType, Integer> versions = new EnumMap<>(PackType.class);
        int maxVersion = 0;
        for (PackType packType : PackType.values()) {
            versions.put(packType, version.getPackVersion(packType));
            maxVersion = Math.max(maxVersion, version.getPackVersion(packType));
        }
        PackMetadataSection metadataSection = new PackMetadataSection(Component.literal(modInfo.getDisplayName()), maxVersion, versions);
        return new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, metadataSection);
    }

    private static DatapackRegistryGenerator createDatapackGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BiConsumer<String, String> langConsumer) {
        return new DatapackRegistryGenerator(output, lookupProvider, Set.of(ArsMagicaAPI.MOD_ID), List.of(
                new AMAbilityProvider(),
                new AMAltarMaterialProvider.Structure(),
                new AMAltarMaterialProvider.Cap(),
                new AMObeliskFuelProvider(),
                new AMOcculusTabProvider(),
                new AMPrefabSpellProvider(langConsumer),
                new AMRitualProvider(),
                new AMSkillProvider(),
                new AMSpellTransformationProvider(),
                new AMWorldgenProvider.CF(),
                new AMWorldgenProvider.PF(),
                new AMWorldgenProvider.BM(),
                new AMDamageTypeProvider()
        ));
    }

    private static <T extends DataProvider, S> DataProvider.Factory<T> wrapWith(BiFunction<PackOutput, S, T> factory, S s) {
        return (output) -> factory.apply(output, s);
    }

    private static <T extends DataProvider, S, U> DataProvider.Factory<T> wrapWith(TriFunction<PackOutput, S, U, T> factory, S s, U u) {
        return (output) -> factory.apply(output, s, u);
    }

    private static <T extends DataProvider, S, U, V> DataProvider.Factory<T> wrapWith(Function4<PackOutput, S, U, V, T> factory, S s, U u, V v) {
        return (output) -> factory.apply(output, s, u, v);
    }

    private static <T extends DataProvider, S, U, V, W> DataProvider.Factory<T> wrapWith(Function5<PackOutput, S, U, V, W, T> factory, S s, U u, V v, W w) {
        return (output) -> factory.apply(output, s, u, v, w);
    }
}
