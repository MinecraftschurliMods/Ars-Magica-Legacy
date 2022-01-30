package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.AMFeatures;
import com.github.minecraftschurlimods.arsmagicalegacy.server.commands.SkillCommand;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ArsMagicaAPI.MOD_ID)
public final class ServerInit {
    public static final PermissionNode<Boolean> CAN_CAST_SPELL = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_cast_spell"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> true);

    @SubscribeEvent
    static void registerCommands(RegisterCommandsEvent event) {
        SkillCommand.register(event.getDispatcher());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    static void biomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        ResourceLocation biome = event.getName();
        Biome.BiomeCategory category = event.getCategory();
        if (category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.CHIMERITE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.VINTEUM_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.TOPAZ_PLACEMENT);
            if (category == Biome.BiomeCategory.MOUNTAIN) {
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMFeatures.TOPAZ_EXTRA_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.FOREST) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.AUM_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.JUNGLE || category == Biome.BiomeCategory.SWAMP) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.CERUBLOSSOM_PLACEMENT);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.WAKEBLOOM_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.DESERT_NOVA_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.MOUNTAIN || category == Biome.BiomeCategory.EXTREME_HILLS || category == Biome.BiomeCategory.UNDERGROUND) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.TARMA_ROOT_PLACEMENT);
            }
            if (biome != null && BiomeDictionary.getTypes(ResourceKey.create(Registry.BIOME_REGISTRY, biome)).contains(BiomeDictionary.Type.SPOOKY)) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AMFeatures.WITCHWOOD_TREE_VEGETATION);
            }
        }
    }

    @SubscribeEvent
    static void registerPermissionNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(CAN_CAST_SPELL);
    }
}
