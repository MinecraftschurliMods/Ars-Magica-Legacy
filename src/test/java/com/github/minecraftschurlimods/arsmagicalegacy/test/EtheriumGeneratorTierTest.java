package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.ITierCheckingBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class EtheriumGeneratorTierTest {
    private static final String BATCH = "etherium_generator_tier_test";
    private static final BlockPos C1 = new BlockPos(1, 2, 1);
    private static final BlockPos C2 = new BlockPos(2, 2, 2);

    @GameTest(template = "obelisk_t0", batch = BATCH)
    public static void testObeliskT0(GameTestHelper helper) {
        assertTier(helper, AMBlocks.OBELISK.get(), C1, 0);
    }

    @GameTest(template = "obelisk_t1", batch = BATCH)
    public static void testObeliskT1(GameTestHelper helper) {
        assertTier(helper, AMBlocks.OBELISK.get(), C1, 1);
    }

    @GameTest(template = "obelisk_t2", batch = BATCH)
    public static void testObeliskT2(GameTestHelper helper) {
        assertTier(helper, AMBlocks.OBELISK.get(), C2, 2);
    }

    @GameTest(template = "celestial_prism_t0", batch = BATCH)
    public static void testCelestialPrismT0(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C1, 0);
    }

    @GameTest(template = "celestial_prism_t1", batch = BATCH)
    public static void testCelestialPrismT1(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C1, 1);
    }

    @GameTest(template = "celestial_prism_t2", batch = BATCH)
    public static void testCelestialPrismT2(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 2);
    }

    @GameTest(template = "celestial_prism_t3", batch = BATCH)
    public static void testCelestialPrismT3(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 3);
    }

    @GameTest(template = "celestial_prism_t4", batch = BATCH)
    public static void testCelestialPrismT4(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 4);
    }

    @GameTest(template = "celestial_prism_t5", batch = BATCH)
    public static void testCelestialPrismT5(GameTestHelper helper) {
        assertTier(helper, AMBlocks.CELESTIAL_PRISM.get(), C2, 5);
    }

    @GameTest(template = "black_aurem_t0", batch = BATCH)
    public static void testBlackAuremT0(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C1.above(), 0);
    }

    @GameTest(template = "black_aurem_t1", batch = BATCH)
    public static void testBlackAuremT1(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C1.above(), 1);
    }

    @GameTest(template = "black_aurem_t2", batch = BATCH)
    public static void testBlackAuremT2(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 2);
    }

    @GameTest(template = "black_aurem_t3", batch = BATCH)
    public static void testBlackAuremT3(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 3);
    }

    @GameTest(template = "black_aurem_t4", batch = BATCH)
    public static void testBlackAuremT4(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 4);
    }

    @GameTest(template = "black_aurem_t5", batch = BATCH)
    public static void testBlackAuremT5(GameTestHelper helper) {
        assertTier(helper, AMBlocks.BLACK_AUREM.get(), C2.above(), 5);
    }

    private static void assertTier(GameTestHelper helper, ITierCheckingBlock block, BlockPos pos, int expectedTier) {
        String blockClassName = block.getClass().getSimpleName();
        helper.assertBlock(pos, block::equals, blockClassName + " should be at " + pos);
        int tier = block.getTier(helper.getLevel(), helper.absolutePos(pos));
        if (tier != expectedTier) {
            helper.fail(blockClassName + " should be tier " + expectedTier + ", but is tier " + tier);
        } else {
            helper.succeed();
        }
    }
}
