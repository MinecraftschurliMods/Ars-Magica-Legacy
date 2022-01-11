package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.AMFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public final class ServerInit {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(Permissions::registerPermissionNodes);
        MinecraftForge.EVENT_BUS.addListener(Commands::registerCommands);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ServerInit::biomeLoading);
    }

    private static void biomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        if (event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.CHIMERITE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.VINTEUM_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.TOPAZ_PLACEMENT);
            if (event.getCategory() == Biome.BiomeCategory.MOUNTAIN) {
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.TOPAZ_EXTRA_PLACEMENT);
            }
            if (event.getCategory() == Biome.BiomeCategory.FOREST) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.AUM_PLACEMENT);
            }
            if (event.getCategory() == Biome.BiomeCategory.JUNGLE || event.getCategory() == Biome.BiomeCategory.SWAMP) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.CERUBLOSSOM_PLACEMENT);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.WAKEBLOOM_PLACEMENT);
            }
            if (event.getCategory() == Biome.BiomeCategory.DESERT || event.getCategory() == Biome.BiomeCategory.MESA) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.DESERT_NOVA_PLACEMENT);
            }
            if (event.getCategory() == Biome.BiomeCategory.MOUNTAIN || event.getCategory() == Biome.BiomeCategory.EXTREME_HILLS || event.getCategory() == Biome.BiomeCategory.UNDERGROUND) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.TARMA_ROOT_PLACEMENT);
            }
        }
    }
}
