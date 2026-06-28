package at.minecraftschurli.mods.arsmagicalegacy.datagen;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMEquipmentAssetProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMLanguageProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMModelProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMParticleDescriptionProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMParticleSpawnerProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMSoundDefinitionProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.assets.AMSpriteSourceProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMAdvancementProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMCuriosProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMDataMapProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMDatapackBuiltinEntriesProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMGlobalLootModifierProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMLootTableProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMMagicProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMRecipeProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMTagsProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMToolTierProvider;
import at.minecraftschurli.mods.arsmagicalegacy.datagen.data.AMWorldgenProvider;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@EventBusSubscriber(modid = ArsMagicaApi.MOD_ID)
public final class AMDatagen {
    @SubscribeEvent
    private static void gatherData(GatherDataEvent.Client event) {
        DataGenerator.PackGenerator pack = event.getGenerator().getVanillaPack(true);
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        lookupProvider = pack.addProvider(wrap(
            DatapackBuiltinEntriesProvider::new,
            lookupProvider,
            new RegistrySetBuilder()
                .add(Registries.DAMAGE_TYPE, AMDatapackBuiltinEntriesProvider::addDamageTypes)
                .add(Registries.ENCHANTMENT, AMDatapackBuiltinEntriesProvider::addEnchantments)
                .add(AMRegistries.Keys.ALTAR_CAP_MATERIAL, AMDatapackBuiltinEntriesProvider::addAltarCapMaterials)
                .add(AMRegistries.Keys.ALTAR_MATERIAL, AMDatapackBuiltinEntriesProvider::addAltarMaterials)
                .add(AMRegistries.Keys.ETHERIUM_TYPE, AMDatapackBuiltinEntriesProvider::addEtheriumTypes)
                .add(AMRegistries.Keys.PLANT, AMDatapackBuiltinEntriesProvider::addPlants)
                .add(Registries.CONFIGURED_FEATURE, AMWorldgenProvider::addConfiguredFeatures)
                .add(Registries.PLACED_FEATURE, AMWorldgenProvider::addPlacedFeatures)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, AMWorldgenProvider::addBiomeModifiers)
                .add(AMRegistries.Keys.AFFINITY, AMMagicProvider::addAffinities)
                .add(AMRegistries.Keys.ABILITY, AMMagicProvider::addAbilities)
                .add(AMRegistries.Keys.OCCULUS_TAB, AMMagicProvider::addOcculusTabs)
                .add(AMRegistries.Keys.SKILL_POINT, AMMagicProvider::addSkillPoints)
                .add(AMRegistries.Keys.SKILL, AMMagicProvider::addSkills)
                .add(AMRegistries.Keys.SPELL_PART_DATA, AMMagicProvider::addSpellPartData)
                .add(AMRegistries.Keys.SPELL_PREFAB, AMMagicProvider::addSpellPrefabs)
                .add(AMRegistries.Keys.RITUAL, AMMagicProvider::addRituals),
            Set.of(ArsMagicaApi.MOD_ID))
        ).getRegistryProvider();

        var blocks = pack.addProvider(wrap(AMTagsProvider.Blocks::new, lookupProvider)).contentsGetter();
        pack.addProvider(wrap(AMTagsProvider.Items::new, lookupProvider, blocks));
        pack.addProvider(wrap(AMTagsProvider.Fluids::new, lookupProvider));
        pack.addProvider(wrap(AMTagsProvider.EntityTypes::new, lookupProvider));
        pack.addProvider(wrap(AMTagsProvider.DamageTypes::new, lookupProvider));
        pack.addProvider(wrap(AMTagsProvider.Enchantments::new, lookupProvider));
        pack.addProvider(wrap(AMTagsProvider.Biomes::new, lookupProvider));
        pack.addProvider(wrap(AMAdvancementProvider::new, lookupProvider));
        pack.addProvider(wrap(AMCompatDataProvider::new, lookupProvider));
        pack.addProvider(wrap(AMCuriosProvider::new, lookupProvider));
        pack.addProvider(wrap(AMDataMapProvider::new, lookupProvider));
        pack.addProvider(wrap(AMGlobalLootModifierProvider::new, lookupProvider));
        pack.addProvider(wrap(AMLootTableProvider::new, lookupProvider));
        pack.addProvider(wrap(AMRecipeProvider.Runner::new, lookupProvider));
        pack.addProvider(wrap(AMToolTierProvider::new, lookupProvider));
        pack.addProvider(wrap(AMModelProvider::new, lookupProvider));
        pack.addProvider(AMEquipmentAssetProvider::new);
        pack.addProvider(AMParticleDescriptionProvider::new);
        pack.addProvider(wrap(AMParticleSpawnerProvider::new, lookupProvider));
        pack.addProvider(AMSoundDefinitionProvider::new);
        pack.addProvider(wrap(AMSpriteSourceProvider::new, lookupProvider));
        Map<String, String> cached = new HashMap<>();
        pack.addProvider(wrap(AMPatchouliBookProvider::new, lookupProvider, cached::put));
        pack.addProvider(wrap(AMLanguageProvider::new, cached));
    }

    private static <T extends DataProvider, P> DataProvider.Factory<T> wrap(BiFunction<PackOutput, P, T> provider, P param) {
        return output -> provider.apply(output, param);
    }

    private static <T extends DataProvider, P1, P2> DataProvider.Factory<T> wrap(Function3<PackOutput, P1, P2, T> provider, P1 param1, P2 param2) {
        return output -> provider.apply(output, param1, param2);
    }

    private static <T extends DataProvider, P1, P2, P3> DataProvider.Factory<T> wrap(Function4<PackOutput, P1, P2, P3, T> provider, P1 param1, P2 param2, P3 param3) {
        return output -> provider.apply(output, param1, param2, param3);
    }
}
