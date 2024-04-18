package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.server.NatureGuardianSpawnHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.test.util.CustomGameTestHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("CodeBlock2Expr")
@ForEachTest(groups = ArsMagicaAPI.MOD_ID + ".boss_spawn")
public class BossSpawnTest {

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "nature_guardian_spawn_test")
    @TestHolder(description = "Test if the nature guardian spawns after the required number of dryads have been killed by the player")
    public static void testNatureGuardianSpawns(CustomGameTestHelper helper) {
        final Map<UUID, Long> lastDryadKills;
        final Map<UUID, Integer> dryadKills;
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(NatureGuardianSpawnHandler.class, MethodHandles.lookup());
            //noinspection unchecked
            lastDryadKills = (Map<UUID, Long>) lookup.findStaticVarHandle(NatureGuardianSpawnHandler.class, "lastDryadKills", Map.class).get();
            //noinspection unchecked
            dryadKills = (Map<UUID, Integer>) lookup.findStaticVarHandle(NatureGuardianSpawnHandler.class, "dryadKills", Map.class).get();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            helper.failWithException(e);
            return; // unreachable but needed
        }
        int tickOffset = 2;
        int numDryads = Config.SERVER.DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN.get();
        var sequence = helper.startSequenceWithPlayer(new BlockPos(2, 4, 2));
        for (int i = 0; i < numDryads; i++) {
            final int index = i + 1;
            sequence.thenExecuteAfter(tickOffset, player -> {
                final Dryad dryad = helper.spawn(AMEntities.DRYAD.get(), new BlockPos(2, 3, 2));
                ServerLevel serverLevel = helper.getLevel();
                dryad.hurt(serverLevel.damageSources().playerAttack(player), dryad.getMaxHealth() * 2);
                if (lastDryadKills.get(player.getGameProfile().getId()) != serverLevel.getGameTime()) {
                    helper.fail("Dryad kill time not updated");
                }
                if (index != numDryads) {
                    if (dryadKills.get(player.getGameProfile().getId()) != index) {
                        helper.fail("Dryad kill count not updated");
                    }
                } else if (dryadKills.get(player.getGameProfile().getId()) != 0) {
                    helper.fail("Dryad kill count not updated");
                }
            });
        }
        sequence.thenAssertEntityPresentAfter(2, AMEntities.NATURE_GUARDIAN.get())
                .thenCleanup()
                .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "water_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the water guardian spawns after its spawn ritual has ben performed")
    public static void testWaterGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setBiome(Biomes.OCEAN);
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 4, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(Items.OAK_BOAT), false);
                  player.drop(new ItemStack(Items.WATER_BUCKET), false);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.WATER_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "fire_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO nether
    @TestHolder(description = "Test that the fire guardian spawns after its spawn ritual has ben performed")
    public static void testFireGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 4, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(Affinity.WATER), false);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.FIRE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "earth_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the earth guardian spawns after its spawn ritual has ben performed")
    public static void testEarthGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 4, 2))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(Items.EMERALD), false);
                  player.drop(new ItemStack(AMItems.CHIMERITE.get()), false);
                  player.drop(new ItemStack(AMItems.TOPAZ.get()), false);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.EARTH_GUARDIAN.get()).thenCleanup().thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "air_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO y > 127
    @TestHolder(description = "Test that the air guardian spawns after its spawn ritual has ben performed")
    public static void testAirGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 4, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(AMItems.TARMA_ROOT.get()), false);
              })
              .thenExecuteAfter(10, () -> helper.assertEntityPresent(AMEntities.AIR_GUARDIAN.get()))
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "ice_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the ice guardian spawns after its spawn ritual has ben performed")
    public static void testIceGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setBiome(Biomes.ICE_SPIKES);
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 4, 1))
              .thenExecuteAfter(2, () -> {
                  helper.setBlock(new BlockPos(1, 2, 1), Blocks.SNOW_BLOCK);
                  helper.setBlock(new BlockPos(1, 3, 1), Blocks.SNOW_BLOCK);
                  helper.setBlock(new BlockPos(1, 4, 1), Blocks.CARVED_PUMPKIN);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.ICE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "lightning_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the lightning guardian spawns after its spawn ritual has ben performed")
    public static void testLightningGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayer(new BlockPos(1, 4, 1))
              .thenExecuteAfter(2, () -> {
                  helper.spawn(EntityType.LIGHTNING_BOLT, new BlockPos(1, 4, 1));
              })
              .thenAssertEntityPresentAfter(15, AMEntities.LIGHTNING_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "life_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the life guardian spawns after its spawn ritual has ben performed")
    public static void testLifeGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayer(new BlockPos(1, 4, 1))
              .thenExecute(helper::setNight)
              .thenExecuteAfter(2, player -> {
                  Villager baby = helper.spawn(EntityType.VILLAGER, new BlockPos(3, 2, 3));
                  baby.setBaby(true);
                  baby.hurt(helper.getLevel().damageSources().playerAttack(player), 100);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.LIFE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "arcane_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO fix detection (fails often because of the guardian's teleport spell)
    @TestHolder(description = "Test that the arcane guardian spawns after its spawn ritual has ben performed")
    public static void testArcaneGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 4, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(ArsMagicaAPI.get().getBookStack(), false);
              })
              .thenAssertEntityPresentAfter(5, AMEntities.ARCANE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(templateNamespace = ArsMagicaAPI.MOD_ID, template = "ender_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO end
    @TestHolder(description = "Test that the ender guardian spawns after its spawn ritual has ben performed")
    public static void testEnderGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 4, 2))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(Items.ENDER_EYE), false);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.ENDER_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }
}
