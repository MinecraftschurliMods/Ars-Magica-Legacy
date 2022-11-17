package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.Util;
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
import java.util.Arrays;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArsMagicaAPI.MOD_ID)
public class EventHandler {
    private static final boolean RUNNING_IN_GH_ACTIONS = System.getenv("GITHUB_ACTIONS") != null;

    @SubscribeEvent
    static void gametest(RegisterGameTestsEvent event) throws ParserConfigurationException {
        if (Stream.of(System.getProperty("forge.enabledGameTestNamespaces")).flatMap(s -> Arrays.stream(s.split(","))).anyMatch(s -> s.equals(ArsMagicaAPI.MOD_ID))) {
            GlobalTestReporter.replaceWith(new CompoundTestReporter(RUNNING_IN_GH_ACTIONS ? new GHActionsTestReporter() : new LogTestReporter(), new JUnitLikeTestReporter(new File("./logs/report-%s.xml".formatted(Instant.now().toString().replace( "-" , "" ).replace( ":" , "" ))))));
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

        @Override
        public void finish() {
            for (final TestReporter reporter : reporters) {
                reporter.finish();
            }
        }
    }

    private static class GHActionsTestReporter implements TestReporter {

        GHActionsTestReporter() {
            System.out.println("::group::Test Results");
        }

        @Override
        public void onTestFailed(GameTestInfo pTestInfo) {
            String testName = pTestInfo.isRequired() ? "" : "(optional) " + "Test " + pTestInfo.getTestName() + " failed";
            Throwable error = pTestInfo.getError();
            assert error != null;
            StackTraceElement frame = error.getStackTrace()[1];
            String message = Util.describeError(error);
            System.out.printf("::%s file=%s,line=%d,title=%s::%s%n", pTestInfo.isRequired() ? "error" : "warning", frame.getFileName(), frame.getLineNumber(), testName, message);
        }

        @Override
        public void onTestSuccess(GameTestInfo pTestInfo) {}

        @Override
        public void finish() {
            System.out.println("::endgroup::");
        }
    }
}
