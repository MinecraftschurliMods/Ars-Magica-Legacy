package at.minecraftschurli.mods.arsmagicalegacy.worldgen;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.List;
import java.util.function.Consumer;

public class AMOverworldRegion extends Region {
    private static final Identifier ID = ArsMagicaApi.id("overworld");
    private static final AMOverworldRegion INSTANCE = new AMOverworldRegion();

    private AMOverworldRegion() {
        super(ID, RegionType.OVERWORLD, 2); //TODO configure weight
    }

    public static void register() {
        Regions.register(INSTANCE);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        // Weirdness values taken from Biomes O' Plenty: https://github.com/Glitchfiend/BiomesOPlenty/blob/26.1.2/common/src/main/java/biomesoplenty/biome/BOPOverworldBiomeBuilder.java#L274-L281
        for (Climate.Parameter weirdness : List.of(
            Climate.Parameter.span(-1.0f, -0.93333334f),
            Climate.Parameter.span(-0.4f, -0.26666668f),
            Climate.Parameter.span(0.26666668f, 0.4f),
            Climate.Parameter.span(0.93333334f, 1.0f)
        )) {
            mapper.accept(Pair.of(Climate.parameters(
                // Temperature
                Climate.Parameter.span(-0.15f, 0.2f),
                // Humidity
                Climate.Parameter.span(-1f, 1f),
                // Continentalness
                Climate.Parameter.span(0.03f, 0.3f),
                // Erosion
                Climate.Parameter.span(-0.2225f, 0.05f),
                // Depth
                Climate.Parameter.point(0),
                weirdness,
                0), WitchwoodForest.BIOME));
            mapper.accept(Pair.of(Climate.parameters(
                Climate.Parameter.span(-0.15f, 0.2f),
                Climate.Parameter.span(-1f, 1f),
                Climate.Parameter.span(0.03f, 0.3f),
                Climate.Parameter.span(-0.2225f, 0.05f),
                Climate.Parameter.point(1),
                weirdness,
                0), WitchwoodForest.BIOME));
        }
    }
}
