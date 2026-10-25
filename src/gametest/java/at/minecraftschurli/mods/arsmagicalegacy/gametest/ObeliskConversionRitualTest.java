package at.minecraftschurli.mods.arsmagicalegacy.gametest;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.gametest.util.CustomGameTestHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.GameTest;

import java.util.List;

@ForEachTest(groups = ArsMagicaApi.MOD_ID + ".obelisk_conversion")
public class ObeliskConversionRitualTest {
    @GameTest(template = ArsMagicaApi.MOD_ID + ":corruption_ritual")
    @TestHolder(description = "Test if the corruption ritual correctly converts the obelisk to a black aurem")
    public static void testCorruptionRitual(CustomGameTestHelper helper) {
        BlockPos center = new BlockPos(3, 1, 2);
        // create player
        helper.startSequenceWithPlayerNoPickup(new BlockPos(3, 4, 2), 0, 90)
        // drop item
        .thenExecuteAfter(2, player -> player.drop(new ItemStack(AMItems.SUNSTONE.get()), false))
        // cast spell
        .thenCastSpellAfter(4, helper.makeSpell(List.of(AMSpells.PROJECTILE.get()), List.of(AMSpells.FIRE_DAMAGE.get())))
        // check for black aurem existence and item removal
        .thenExecuteAfter(10, () -> {
            helper.assertBlock(center.above(), block -> block == AMBlocks.BLACK_AUREM.get(), _ -> Component.literal("Black aurem not found"));
            helper.assertItemEntityCountIs(AMItems.SUNSTONE.get(), center, 4, 0);
        })
        .thenCleanup()
        .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":purification_ritual")
    @TestHolder(description = "Test if the purification ritual correctly converts the obelisk to a celestial prism")
    public static void testPurificationRitual(CustomGameTestHelper helper) {
        BlockPos center = new BlockPos(3, 1, 3);
        // create player
        helper.startSequenceWithPlayerNoPickup(new BlockPos(3, 4, 3), 0, 90)
        // drop item
        .thenExecuteAfter(2, player -> player.drop(new ItemStack(AMItems.MOONSTONE.get()), false))
        // cast spell
        .thenCastSpellAfter(4, helper.makeSpell(List.of(AMSpells.SELF.get()), List.of(AMSpells.LIGHT.get())))
        // check for celestial prism and item removal
        .thenExecuteAfter(10, () -> {
            helper.assertBlock(center, block -> block == AMBlocks.CELESTIAL_PRISM.get(), _ -> Component.literal("Celestial Prism not found"));
            helper.assertItemEntityCountIs(AMItems.MOONSTONE.get(), center, 4, 0);
        })
        .thenCleanup()
        .thenSucceed();
    }
}
