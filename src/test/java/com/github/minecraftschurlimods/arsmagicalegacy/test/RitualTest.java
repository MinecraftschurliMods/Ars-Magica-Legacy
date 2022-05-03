package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class RitualTest {
    @GameTest(template = "arcane_guardian_spawn_test", timeoutTicks = 20)
    public void testArcaneGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(2, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> player.drop(ArsMagicaAPI.get().getBookStack(), false));
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.ARCANE_GUARDIAN.get());
            helper.killAllEntities();
            helper.succeed();
        });
    }
}
