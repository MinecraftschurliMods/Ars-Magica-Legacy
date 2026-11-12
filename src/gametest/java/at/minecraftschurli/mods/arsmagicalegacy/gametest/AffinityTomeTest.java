package at.minecraftschurli.mods.arsmagicalegacy.gametest;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.gametest.util.CustomGameTestHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;
import net.neoforged.testframework.gametest.GameTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ForEachTest(groups = ArsMagicaApi.MOD_ID + ".affinity_tome")
public class AffinityTomeTest {
    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Affinity tome should not be usable by players who do not know magic, and should increase affinity depth when used by players who do know magic")
    public static void testAffinityTome(CustomGameTestHelper helper) {
        MagicHelper magicHelper = ArsMagicaApi.magicHelper();
        Holder<Affinity> affinity = helper.getHolder(AMMagic.AIR);
        helper.startSequenceWithPlayer()
              .thenExecute(gameTestPlayer -> {
                  ItemStack tome = new ItemStack(AMItems.AFFINITY_TOME, 1, DataComponentPatch.builder().set(AMDataComponents.AFFINITY.get(), affinity).build());
                  Inventory inventory = gameTestPlayer.getInventory();
                  inventory.setItem(inventory.getSelectedSlot(), tome);
                  InteractionResult result = gameTestPlayer.gameMode.useItem(gameTestPlayer, gameTestPlayer.level(), tome, InteractionHand.MAIN_HAND);
                  helper.assertTrue(result == InteractionResult.FAIL, "Tome was used by player not knowing magic");
                  magicHelper.addXp(gameTestPlayer, 0);
                  double affinityDepthBefore = magicHelper.getAffinityDepth(gameTestPlayer, affinity);
                  result = gameTestPlayer.gameMode.useItem(gameTestPlayer, gameTestPlayer.level(), tome, InteractionHand.MAIN_HAND);
                  helper.assertTrue(result.consumesAction(), "Tome not used successfully");
                  double affinityDepthAfter = BigDecimal.valueOf(magicHelper.getAffinityDepth(gameTestPlayer, affinity)).setScale(5, RoundingMode.DOWN).doubleValue();
                  double shift = AMServerConfig.AFFINITY_TOME_SHIFT.getAsDouble();
                  helper.assertTrue(affinityDepthBefore + shift == affinityDepthAfter, "Affinity shift did not apply");
              })
              .thenCleanup()
              .thenSucceed();
    }
}
