package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.CrystalPhylacteryContentsSize;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;

import java.util.concurrent.CompletableFuture;

public final class AMDataMapProvider extends DataMapProvider {
    public AMDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.COMPOSTABLES)
            .add(AMItems.WITCHWOOD_LEAVES, new Compostable(0.3f, false), false)
            .add(AMItems.WITCHWOOD_SAPLING, new Compostable(0.3f, false), false)
            .add(AMItems.AUM, new Compostable(0.65f, false), false)
            .add(AMItems.CERUBLOSSOM, new Compostable(0.65f, false), false)
            .add(AMItems.DESERT_NOVA, new Compostable(0.65f, false), false)
            .add(AMItems.TARMA_ROOT, new Compostable(0.65f, false), false)
            .add(AMItems.WAKEBLOOM, new Compostable(0.65f, false), false);
        builder(ObeliskFuel.DATA_MAP)
            .add(AMTags.Items.DUSTS_VINTEUM, new ObeliskFuel(200, 1), false)
            .add(AMTags.Items.STORAGE_BLOCKS_VINTEUM, new ObeliskFuel(900, 2), false)
            .add(AMItems.LIQUID_ETHERIUM_BUCKET, new ObeliskFuel(1000, 2), false);
        builder(CrystalPhylacteryContentsSize.DATA_MAP)
            .add(AMTags.EntityTypes.SUMMONING_NOT_SUPPORTED, new CrystalPhylacteryContentsSize(0), false)
            .add(EntityType.DONKEY.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(20), false)
            .add(EntityType.HORSE.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(20), false)
            .add(EntityType.LLAMA.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(20), false)
            .add(EntityType.MAGMA_CUBE.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(16), false)
            .add(EntityType.MULE.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(20), false)
            .add(EntityType.SLIME.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(16), false)
            .add(EntityType.TRADER_LLAMA.builtInRegistryHolder(), new CrystalPhylacteryContentsSize(20), false);
        builder(NeoForgeDataMaps.STRIPPABLES)
            .add(AMBlocks.WITCHWOOD_LOG, new Strippable(AMBlocks.STRIPPED_WITCHWOOD_LOG.get()), false)
            .add(AMBlocks.WITCHWOOD_WOOD, new Strippable(AMBlocks.STRIPPED_WITCHWOOD_WOOD.get()), false);
    }
}
