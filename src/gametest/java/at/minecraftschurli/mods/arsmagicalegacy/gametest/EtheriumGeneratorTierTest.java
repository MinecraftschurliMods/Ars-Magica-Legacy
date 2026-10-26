package at.minecraftschurli.mods.arsmagicalegacy.gametest;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumHandlerBlock;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.GameTest;

@ForEachTest(groups = ArsMagicaApi.MOD_ID + ".etherium_generator_tier_test")
public class EtheriumGeneratorTierTest {
    private static final BlockPos C1 = new BlockPos(1, 1, 1);
    private static final BlockPos C2 = new BlockPos(2, 1, 2);

    @GameTest(template = ArsMagicaApi.MOD_ID + ":obelisk_t0")
    @TestHolder(description = "Test if the obelisk detects its tier correctly as T0")
    public static void testObeliskT0(GameTestHelper helper) {
        assertTier(helper, AMBlocks.OBELISK.get(), C1, 0);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":obelisk_t1")
    @TestHolder(description = "Test if the obelisk detects its tier correctly as T1")
    public static void testObeliskT1(GameTestHelper helper) {
        assertTier(helper, AMBlocks.OBELISK.get(), C1, 1);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":obelisk_t2")
    @TestHolder(description = "Test if the obelisk detects its tier correctly as T2")
    public static void testObeliskT2(GameTestHelper helper) {
        assertTier(helper, AMBlocks.OBELISK.get(), C2, 2);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":celestial_prism_t0")
    @TestHolder(description = "Test if the celestial prism detects its tier correctly as T0")
    public static void testCelestialPrismT0(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C1, 0);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":celestial_prism_t1")
    @TestHolder(description = "Test if the celestial prism detects its tier correctly as T1")
    public static void testCelestialPrismT1(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C1, 1);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":celestial_prism_t2")
    @TestHolder(description = "Test if the celestial prism detects its tier correctly as T2")
    public static void testCelestialPrismT2(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 2);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":celestial_prism_t3")
    @TestHolder(description = "Test if the celestial prism detects its tier correctly as T3")
    public static void testCelestialPrismT3(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 3);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":celestial_prism_t4")
    @TestHolder(description = "Test if the celestial prism detects its tier correctly as T4")
    public static void testCelestialPrismT4(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 4);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":celestial_prism_t5")
    @TestHolder(description = "Test if the celestial prism detects its tier correctly as T5")
    public static void testCelestialPrismT5(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 5);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":black_aurem_t0")
    @TestHolder(description = "Test if the black aurem detects its tier correctly as T0")
    public static void testBlackAuremT0(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C1.above(), 0);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":black_aurem_t1")
    @TestHolder(description = "Test if the black aurem detects its tier correctly as T1")
    public static void testBlackAuremT1(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C1.above(), 1);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":black_aurem_t2")
    @TestHolder(description = "Test if the black aurem detects its tier correctly as T2")
    public static void testBlackAuremT2(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 2);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":black_aurem_t3")
    @TestHolder(description = "Test if the black aurem detects its tier correctly as T3")
    public static void testBlackAuremT3(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 3);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":black_aurem_t4")
    @TestHolder(description = "Test if the black aurem detects its tier correctly as T4")
    public static void testBlackAuremT4(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 4);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":black_aurem_t5")
    @TestHolder(description = "Test if the black aurem detects its tier correctly as T5")
    public static void testBlackAuremT5(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 5);
    }

    private static <T extends Block & EtheriumHandlerBlock> void assertTier(GameTestHelper helper, T block, BlockPos pos, int expectedTier) {
        helper.assertBlock(pos, block::equals, b -> Component.literal("Expected %s at %s but found %s".formatted(block.builtInRegistryHolder().key(), pos, b.builtInRegistryHolder().key())));
        BlockEntity blockEntity = block.getBlockEntity(helper.getLevel(), helper.absolutePos(pos), helper.getBlockState(pos));
        if (blockEntity == null) {
            throw helper.assertionException(pos, "test.error.missing_block_entity");
        }
        if (!(blockEntity instanceof EtheriumGeneratorBlockEntity etheriumGeneratorBlockEntity)) {
            throw helper.assertionException(pos, "test.error.wrong_block_entity", blockEntity.typeHolder().getRegisteredName());
        }
        int tier = etheriumGeneratorBlockEntity.getTier(helper.getLevel(), helper.absolutePos(pos));
        if (tier != expectedTier) {
            helper.fail(block.getClass().getSimpleName() + " should be tier " + expectedTier + ", but is tier " + tier);
        } else {
            helper.succeed();
        }
    }
}
