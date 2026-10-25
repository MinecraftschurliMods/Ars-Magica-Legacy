package at.minecraftschurli.mods.arsmagicalegacy.gametest;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.DryadKillsAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.entity.Dryad;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.gametest.util.CustomGameTestHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.GameTest;

import java.util.List;

@SuppressWarnings("CodeBlock2Expr")
@ForEachTest(groups = ArsMagicaApi.MOD_ID + ".boss_spawn")
public class BossSpawnTest {

    @GameTest(template = ArsMagicaApi.MOD_ID + ":nature_guardian_spawn_test")
    @TestHolder(description = "Test if the nature guardian spawns after the required number of dryads have been killed by the player")
    public static void testNatureGuardianSpawns(CustomGameTestHelper helper) {
        int tickOffset = 2;
        int numDryads = AMServerConfig.DRYAD_KILLS_FOR_NATURE_GUARDIAN_SPAWN.get();
        var sequence = helper.startSequenceWithPlayer(new BlockPos(2, 2, 2));
        for (int i = 0; i < numDryads; i++) {
            final int index = i + 1;
            sequence.thenExecuteAfter(tickOffset, player -> {
                final Dryad dryad = helper.spawn(AMEntities.DRYAD.get(), new BlockPos(2, 1, 2));
                ServerLevel serverLevel = helper.getLevel();
                helper.hurt(dryad, serverLevel.damageSources().playerAttack(player), dryad.getMaxHealth() * 2);
                DryadKillsAttachment dryadKills = player.getData(AMAttachments.DRYAD_KILLS);
                List<Integer> timers = dryadKills.timers();
                if (!timers.isEmpty() && !timers.getLast().equals(AMServerConfig.DRYAD_KILL_COOLDOWN.get())) {
                    helper.fail("Dryad kill time not updated");
                }
                if (index != numDryads) {
                    if (timers.size() != index) {
                        helper.fail("Dryad kill count not updated");
                    }
                } else if (!timers.isEmpty()) {
                    helper.fail("Dryad kill count not updated");
                }
            });
        }
        sequence.thenAssertEntityPresentAfter(2, AMEntities.NATURE_GUARDIAN.get())
                .thenCleanup()
                .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":water_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the water guardian spawns after its spawn ritual has ben performed")
    public static void testWaterGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setBiome(Biomes.OCEAN);
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 2, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(Items.OAK_BOAT), false);
                  player.drop(new ItemStack(Items.WATER_BUCKET), false);
              })
              .thenAssertEntityPresentAfter(17, AMEntities.WATER_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":fire_guardian_spawn_test", timeoutTicks = 25, required = false)//TODO nether
    @TestHolder(description = "Test that the fire guardian spawns after its spawn ritual has ben performed")
    public static void testFireGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 2, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(AMItems.AFFINITY_ESSENCE, 1, DataComponentPatch.builder().set(AMDataComponents.AFFINITY.get(), helper.getHolder(AMMagic.WATER)).build()), false);
              })
              .thenAssertEntityPresentAfter(23, AMEntities.FIRE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":earth_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the earth guardian spawns after its spawn ritual has ben performed")
    public static void testEarthGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 2, 2))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(Items.EMERALD), false);
                  player.drop(new ItemStack(AMItems.CHIMERITE.get()), false);
                  player.drop(new ItemStack(AMItems.TOPAZ.get()), false);
              })
              .thenAssertEntityPresentAfter(18, AMEntities.EARTH_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":air_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO y > 127
    @TestHolder(description = "Test that the air guardian spawns after its spawn ritual has ben performed")
    public static void testAirGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 2, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(AMItems.TARMA_ROOT.get()), false);
              })
              .thenExecuteAfter(15, () -> helper.assertEntityPresent(AMEntities.AIR_GUARDIAN.get()))
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":ice_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the ice guardian spawns after its spawn ritual has ben performed")
    public static void testIceGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setBiome(Biomes.ICE_SPIKES);
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 2, 1))
              .thenExecuteAfter(2, player -> {
                  helper.setBlock(new BlockPos(1, 1, 1), Blocks.SNOW_BLOCK);
                  helper.setBlock(new BlockPos(1, 2, 1), Blocks.SNOW_BLOCK);
                  ItemStack stack = new ItemStack(Items.CARVED_PUMPKIN);
                  player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                  helper.placeAt(player, stack, new BlockPos(1, 2, 1), Direction.UP);
              })
              .thenAssertEntityPresentAfter(15, AMEntities.ICE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":lightning_guardian_spawn_test", timeoutTicks = 20, skyAccess = true)
    @TestHolder(description = "Test that the lightning guardian spawns after its spawn ritual has ben performed")
    public static void testLightningGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 1, 2))
              .thenExecuteAfter(2, () -> {
                  helper.spawn(EntityType.LIGHTNING_BOLT, new BlockPos(1, 3, 1));
              })
              .thenAssertEntityPresentAfter(15, AMEntities.LIGHTNING_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":life_guardian_spawn_test", timeoutTicks = 20)
    @TestHolder(description = "Test that the life guardian spawns after its spawn ritual has ben performed")
    public static void testLifeGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setMoonPhase(MoonPhase.NEW_MOON);
        helper.setNight();
        helper.startSequenceWithPlayerNoPickup(new BlockPos(1, 2, 1))
              .thenExecuteAfter(2, player -> {
                  Villager baby = helper.spawn(EntityType.VILLAGER, new BlockPos(3, 1, 3));
                  baby.setBaby(true);
                  helper.hurt(baby, helper.getLevel().damageSources().playerAttack(player), 100);
              })
              .thenAssertEntityPresentAfter(15, AMEntities.LIFE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":arcane_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO fix detection (fails often because of the guardian's teleport spell)
    @TestHolder(description = "Test that the arcane guardian spawns after its spawn ritual has ben performed")
    public static void testArcaneGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 2, 1))
              .thenExecuteAfter(2, player -> {
                  player.drop(ArsMagicaApi.book().create(), false);
              })
              .thenAssertEntityPresentAfter(15, AMEntities.ARCANE_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":ender_guardian_spawn_test", timeoutTicks = 20, required = false)//TODO end
    @TestHolder(description = "Test that the ender guardian spawns after its spawn ritual has ben performed")
    public static void testEnderGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.startSequenceWithPlayerNoPickup(new BlockPos(2, 2, 2))
              .thenExecuteAfter(2, player -> {
                  player.drop(new ItemStack(Items.ENDER_EYE), false);
              })
              .thenAssertEntityPresentAfter(10, AMEntities.ENDER_GUARDIAN.get())
              .thenCleanup()
              .thenSucceed();
    }
}
