package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.GlobalTestReporter;
import net.minecraft.gametest.framework.JUnitLikeTestReporter;
import net.minecraft.gametest.framework.LogTestReporter;
import net.minecraft.gametest.framework.TestReporter;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class EventHandler {
    @SubscribeEvent
    static void gametest(RegisterGameTestsEvent event) throws ParserConfigurationException {
        if (Stream.of(System.getProperty("forge.enabledGameTestNamespaces"))
                  .flatMap(s -> Arrays.stream(s.split(",")))
                  .anyMatch(s -> s.equals(ArsMagicaAPI.MOD_ID))) {
            GlobalTestReporter.replaceWith(new CompoundTestReporter(
                    new LogTestReporter(),
                    new JUnitLikeTestReporter(new File("./logs/report-%s.xml".formatted(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))))
            ));
        }
    }

    private record CompoundTestReporter(TestReporter... reporters) implements TestReporter {

        @Override
        public void onTestFailed(final GameTestInfo testInfo) {
            for (final TestReporter reporter : reporters) {
                reporter.onTestFailed(testInfo);
            }
        }

        @Override
        public void onTestSuccess(final GameTestInfo testInfo) {
            for (final TestReporter reporter : reporters) {
                reporter.onTestSuccess(testInfo);
            }
        }
    }
}
