package com.github.minecraftschurlimods.arsmagicalegacy.test.util;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.testframework.conf.ClientConfiguration;
import net.neoforged.testframework.conf.Feature;
import net.neoforged.testframework.conf.FrameworkConfiguration;
import net.neoforged.testframework.impl.MutableTestFramework;
import net.neoforged.testframework.summary.GitHubActionsStepSummaryDumper;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class EventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    static void initTestframework(FMLConstructModEvent evt) {
        ModContainer container = ArsMagicaLegacy.getModContainer();
        FrameworkConfiguration configuration = FrameworkConfiguration
                .builder(new ResourceLocation(container.getModId(), "tests"))
                .clientConfiguration(() -> ClientConfiguration.builder().toggleOverlayKey(GLFW.GLFW_KEY_J).openManagerKey(GLFW.GLFW_KEY_N).build())
                .enable(Feature.CLIENT_SYNC, Feature.CLIENT_MODIFICATIONS, Feature.TEST_STORE)
                .dumpers(new GitHubActionsStepSummaryDumper(ArsMagicaLegacy.getModName() + " Gametest Summary"))
                .build();

        final MutableTestFramework framework = configuration.create();
        framework.init(container.getEventBus(), container);

        NeoForge.EVENT_BUS.addListener((final RegisterCommandsEvent event) -> {
            final LiteralArgumentBuilder<CommandSourceStack> node = Commands.literal("tests");
            framework.registerCommands(node);
            event.getDispatcher().register(node);
        });
    }
}
