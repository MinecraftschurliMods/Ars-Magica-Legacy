package at.minecraftschurli.mods.arsmagicalegacy.gametest.util;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.testframework.conf.ClientConfiguration;
import net.neoforged.testframework.conf.Feature;
import net.neoforged.testframework.conf.FrameworkConfiguration;
import net.neoforged.testframework.impl.MutableTestFramework;
import net.neoforged.testframework.summary.GitHubActionsStepSummaryDumper;
import org.lwjgl.glfw.GLFW;

@Mod(ArsMagicaApi.MOD_ID)
public class ArsMagicaGameTests {
    public ArsMagicaGameTests(IEventBus modEventBus, ModContainer modContainer) {
        FrameworkConfiguration configuration = FrameworkConfiguration
                .builder(Identifier.fromNamespaceAndPath(modContainer.getModId(), "tests"))
                .clientConfiguration(() -> ClientConfiguration.builder().toggleOverlayKey(GLFW.GLFW_KEY_J).openManagerKey(GLFW.GLFW_KEY_N).build())
                .enable(Feature.CLIENT_SYNC, Feature.CLIENT_MODIFICATIONS, Feature.TEST_STORE)
                .dumpers(new GitHubActionsStepSummaryDumper(modContainer.getModInfo().getDisplayName() + " Gametest Summary"))
                .build();

        final MutableTestFramework framework = configuration.create();
        framework.init(modEventBus, modContainer);

        NeoForge.EVENT_BUS.addListener((final RegisterCommandsEvent event) -> {
            final LiteralArgumentBuilder<CommandSourceStack> node = Commands.literal("tests");
            framework.registerCommands(node);
            event.getDispatcher().register(node);
        });
    }
}
