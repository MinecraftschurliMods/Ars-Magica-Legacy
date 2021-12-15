package com.github.minecraftschurli.arsmagicalegacy.server;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.common.level.AMFeatures;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.server.commands.SkillCommand;
import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ArsMagicaAPI.MOD_ID)
public final class ServerInit {
    @SubscribeEvent
    static void registerCommands(RegisterCommandsEvent event) {
        SkillCommand.register(event.getDispatcher());
        event.getDispatcher().register(Commands.literal("givetestspell").executes(context -> {
            ItemStack stack = new ItemStack(AMItems.SPELL.get());
            SpellItem.saveSpell(stack, Spell.of(
                    SpellStack.of(AMSpellParts.FORGE.get()),
                    ShapeGroup.of(AMSpellParts.PROJECTILE.get())
            ));
            context.getSource().getPlayerOrException().addItem(stack);
            return Command.SINGLE_SUCCESS;
        }));
        event.getDispatcher().register(Commands.literal("givetestspell2").executes(context -> {
            ItemStack stack = new ItemStack(AMItems.SPELL.get());
            SpellItem.saveSpell(stack, Spell.of(
                    SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get()),
                    ShapeGroup.of(AMSpellParts.TOUCH.get()),
                    ShapeGroup.of(AMSpellParts.PROJECTILE.get()),
                    ShapeGroup.of(AMSpellParts.TOUCH.get(), AMSpellParts.RUNE.get()),
                    ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.RUNE.get())
            ));
            context.getSource().getPlayerOrException().addItem(stack);
            return Command.SINGLE_SUCCESS;
        }));
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
}
