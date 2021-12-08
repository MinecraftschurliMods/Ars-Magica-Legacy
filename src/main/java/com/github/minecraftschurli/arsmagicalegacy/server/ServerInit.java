package com.github.minecraftschurli.arsmagicalegacy.server;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.common.level.Ores;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.server.commands.SkillCommand;
import com.mojang.brigadier.Command;
import net.minecraft.commands.Commands;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
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
                    SpellStack.of(AMSpellParts.ABSORPTION.get()),
                    ShapeGroup.of(AMSpellParts.WALL.get())
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
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Ores.CHIMERITE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Ores.VINTEUM_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Ores.TOPAZ_PLACEMENT);
            if (event.getCategory() == Biome.BiomeCategory.MOUNTAIN) {
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Ores.TOPAZ_EXTRA_PLACEMENT);
            }
        }
    }
}
