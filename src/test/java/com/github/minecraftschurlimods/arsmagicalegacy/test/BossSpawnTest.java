package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.server.NatureGuardianSpawnHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class BossSpawnTest {

    private static HashMap<UUID, Long> lastDryadKills;
    private static HashMap<UUID, Integer> dryadKills;

    @SuppressWarnings("unchecked")
    @BeforeBatch(batch = "defaultBatch")
    public static void beforeBatch(ServerLevel level) {
        try {
            Field lastDryadKillsField = NatureGuardianSpawnHandler.class.getDeclaredField("lastDryadKills");
            lastDryadKillsField.setAccessible(true);
            lastDryadKills = (HashMap<UUID, Long>) lastDryadKillsField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            throw wrapException(e);
        }
        try {
            Field dryadKillsField = NatureGuardianSpawnHandler.class.getDeclaredField("dryadKills");
            dryadKillsField.setAccessible(true);
            dryadKills = (HashMap<UUID, Integer>) dryadKillsField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            throw wrapException(e);
        }
    }

    @GameTest(template = "nature_guardian_spawn_test")
    public static void testNatureGuardianSpawns(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(2, 4, 2)), 0, 90);
        serverlevel.addFreshEntity(player);
        int tickOffset = 2;
        int numDryads = Config.SERVER.DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN.get();
        for (int i = 0; i < numDryads; i++) {
            final int index = i + 1;
            final Dryad dryad = helper.spawn(AMEntities.DRYAD.get(), new BlockPos(2, 3, 2));
            helper.runAfterDelay(index+tickOffset, () -> {
                dryad.hurt(DamageSource.playerAttack(player), dryad.getMaxHealth() * 2);
                if (lastDryadKills.get(player.getGameProfile().getId()) != serverlevel.getGameTime()) helper.fail("Dryad kill time not updated");
                if (index != numDryads) {
                    if (dryadKills.get(player.getGameProfile().getId()) != index) helper.fail("Dryad kill count not updated");
                } else {
                    if (dryadKills.get(player.getGameProfile().getId()) != 0) helper.fail("Dryad kill count not updated");
                }
            });
        }
        helper.runAfterDelay(numDryads+tickOffset+2, () -> {
            helper.assertEntityPresent(AMEntities.NATURE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "arcane_guardian_spawn_test", timeoutTicks = 20, required = false)
    public static void testArcaneGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(2, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> player.drop(ArsMagicaAPI.get().getBookStack(), false));
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.ARCANE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "earth_guardian_spawn_test", timeoutTicks = 20)
    public static void testEarthGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(2, 4, 2)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            player.drop(new ItemStack(Items.EMERALD), false);
            player.drop(new ItemStack(AMItems.CHIMERITE.get()), false);
            player.drop(new ItemStack(AMItems.TOPAZ.get()), false);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.EARTH_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    //TODO: ice guardian spawn ritual (needs snowy biome)
    @GameTest(template = "ice_guardian_spawn_test", timeoutTicks = 20, required = false)
    public static void testIceGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            helper.setBlock(new BlockPos(1, 2, 1), Blocks.SNOW_BLOCK);
            helper.setBlock(new BlockPos(1, 3, 1), Blocks.SNOW_BLOCK);
            helper.setBlock(new BlockPos(1, 4, 1), Blocks.CARVED_PUMPKIN);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.ICE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "life_guardian_spawn_test", timeoutTicks = 20)
    public static void testLifeGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        Villager baby = new Villager(EntityType.VILLAGER, serverlevel);
        baby.setBaby(true);
        baby.moveTo(helper.absolutePos(new BlockPos(3, 2, 3)), 0, 0);
        serverlevel.addFreshEntity(baby);
        helper.runAfterDelay(2, () -> {
            helper.setNight();
            baby.hurt(DamageSource.playerAttack(player), 100);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.LIFE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "lightning_guardian_spawn_test", timeoutTicks = 20)
    public static void testLightningGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverlevel);
            assert lightningBolt != null;
            lightningBolt.moveTo(helper.absoluteVec(Vec3.atCenterOf(new BlockPos(1, 4, 1))));
            serverlevel.addFreshEntity(lightningBolt);
        });
        helper.runAfterDelay(15, () -> {
            helper.assertEntityPresent(AMEntities.LIGHTNING_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    //TODO: air guardian spawn ritual (needs y > 128)
    //TODO: ender guardian spawn ritual (needs end dimension)
    //TODO: fire guardian spawn ritual (needs nether dimension)
    //TODO: water guardian spawn ritual (needs water biome)

    private static void cleanup(final GameTestHelper helper, final Player player) {
        helper.killAllEntities();
        player.discard();
        helper.succeed();
    }

    private static GameTestAssertException wrapException(final Exception e) {
        return (GameTestAssertException) new GameTestAssertException(e.getMessage()).initCause(e);
    }
}
