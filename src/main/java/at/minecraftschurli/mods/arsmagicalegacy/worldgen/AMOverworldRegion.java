package at.minecraftschurli.mods.arsmagicalegacy.worldgen;

import at.minecraftschurli.mods.arsmagicalegacy.AMCommonConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.function.Consumer;

public class AMOverworldRegion extends Region {
    private static final Identifier ID = ArsMagicaApi.id("overworld");
    private static final AMOverworldRegion INSTANCE = new AMOverworldRegion();

    private AMOverworldRegion() {
        super(ID, RegionType.OVERWORLD, AMCommonConfig.WITCHWOOD_FOREST_WEIGHT.get());
    }

    public static void register() {
        Regions.register(INSTANCE);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> builder.replaceBiome(Biomes.FOREST, WitchwoodForest.BIOME));
    }
}
