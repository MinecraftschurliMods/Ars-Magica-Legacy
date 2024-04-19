package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.test.util.CustomGameTestHelper;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ForEachTest(groups = ArsMagicaAPI.MOD_ID + ".affinity_tome")
public class AffinityTomeTest {
    @GameTest
    @EmptyTemplate
    @TestHolder(description = "")
    public static void testAffinityTome(CustomGameTestHelper helper) {
        ArsMagicaAPI api = ArsMagicaAPI.get();
        IAffinityHelper affinityHelper = api.getAffinityHelper();
        Holder<Affinity> affinity = AMAffinities.AIR;
        helper.startSequenceWithPlayer()
              .thenExecute(gameTestPlayer -> {
                  ItemStack tome = affinityHelper.getTomeForAffinity(affinity);
                  Inventory inventory = gameTestPlayer.getInventory();
                  inventory.setItem(inventory.selected, tome);
                  InteractionResult result = gameTestPlayer.gameMode.useItem(gameTestPlayer, gameTestPlayer.level(), tome, InteractionHand.MAIN_HAND);
                  helper.assertTrue(result == InteractionResult.FAIL, "Tome was used by player not knowing magic");
                  api.getMagicHelper().awardXp(gameTestPlayer, 0);
                  double affinityDepthBefore = affinityHelper.getAffinityDepth(gameTestPlayer, affinity);
                  result = gameTestPlayer.gameMode.useItem(gameTestPlayer, gameTestPlayer.level(), tome, InteractionHand.MAIN_HAND);
                  helper.assertTrue(result.shouldAwardStats(), "Tome not used successfully");
                  double affinityDepthAfter = BigDecimal.valueOf(affinityHelper.getAffinityDepth(gameTestPlayer, affinity)).setScale(5, RoundingMode.DOWN).doubleValue();
                  double shift = Config.SERVER.AFFINITY_TOME_SHIFT.getAsDouble();
                  helper.assertTrue(affinityDepthBefore + shift == affinityDepthAfter, "Affinity shift did not apply");
              })
              .thenCleanup()
              .thenSucceed();
    }
}
