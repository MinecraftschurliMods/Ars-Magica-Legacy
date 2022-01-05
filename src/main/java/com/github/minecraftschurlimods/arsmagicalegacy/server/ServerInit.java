package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.AMFeatures;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.server.commands.SkillCommand;
import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ArsMagicaAPI.MOD_ID)
public final class ServerInit {
    public static final PermissionNode<Boolean> CAN_CAST_SPELL = new PermissionNode<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "can_cast_spell"), PermissionTypes.BOOLEAN, (player, playerUUID, context) -> true);

    @SubscribeEvent
    static void registerCommands(RegisterCommandsEvent event) {
        SkillCommand.register(event.getDispatcher());
        if (!FMLEnvironment.production) {
            event.getDispatcher().register(Commands.literal("givetestspell1").executes(context -> {
                ItemStack stack = new ItemStack(AMItems.SPELL.get());
                SpellItem.saveSpell(stack, Spell.of(
                        SpellStack.of(AMSpellParts.BLINK.get()),
                        ShapeGroup.of(AMSpellParts.SELF.get())
                ));
                context.getSource().getPlayerOrException().addItem(stack);
                return Command.SINGLE_SUCCESS;
            }));
            event.getDispatcher().register(Commands.literal("givetestspell2").executes(context -> {
                ItemStack stack = new ItemStack(AMItems.SPELL.get());
                SpellItem.saveSpell(stack, Spell.of(
                        SpellStack.of(AMSpellParts.DIG.get()),
                        ShapeGroup.of(AMSpellParts.PROJECTILE.get())
                ));
                context.getSource().getPlayerOrException().addItem(stack);
                return Command.SINGLE_SUCCESS;
            }));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    static void biomeLoading(BiomeLoadingEvent event) {
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

    @SubscribeEvent
    static void registerPermissionNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(CAN_CAST_SPELL);
    }
}
