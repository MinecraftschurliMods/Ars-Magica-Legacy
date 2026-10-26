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
import net.minecraft.world.entity.Entity;
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
import net.neoforged.testframework.gametest.GameTestPlayer;

import java.util.List;
import java.util.function.Consumer;

@ForEachTest(groups = ArsMagicaApi.MOD_ID + ".boss_spawn")
public class BossSpawnTest {
    /// The time between checks for an item to trigger a ritual
    private static final int ITEM_CHECK_TIME = 20;
    /// The time to wait before doing the next step to allow for things to react
    private static final int SETTLE_TIME = 2;
    
    @GameTest(template = ArsMagicaApi.MOD_ID + ":nature_guardian_spawn_test")
    @TestHolder(description = "Test that the Nature Guardian spawns after the required number of dryads have been killed by the player")
    public static void testNatureGuardianSpawns(CustomGameTestHelper helper) {
        int numDryads = AMServerConfig.DRYAD_KILLS_FOR_NATURE_GUARDIAN_SPAWN.get();
        var sequence = helper.startSequenceWithPlayer(new BlockPos(2, 2, 2));
        for (int i = 0; i < numDryads; i++) {
            final int index = i + 1;
            sequence.thenExecuteAfter(SETTLE_TIME, player -> {
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
        sequence.thenAssertEntityPresentAfter(SETTLE_TIME, AMEntities.NATURE_GUARDIAN.get())
                .thenCleanup()
                .thenSucceed();
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":water_guardian_spawn_test")
    @TestHolder(description = "Test that the Water Guardian spawns after its spawn ritual has been performed")
    public static void testWaterGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setBiome(Biomes.OCEAN);
        testRitual(helper, AMEntities.WATER_GUARDIAN.get(), new BlockPos(1, 2, 1), new ItemStack(Items.OAK_BOAT), new ItemStack(Items.WATER_BUCKET));
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":fire_guardian_spawn_test", required = false)//TODO nether
    @TestHolder(description = "Test that the fire guardian spawns after its spawn ritual has ben performed")
    public static void testFireGuardianSpawnRitual(CustomGameTestHelper helper) {
        ItemStack essence = new ItemStack(AMItems.AFFINITY_ESSENCE, 1, DataComponentPatch.builder().set(AMDataComponents.AFFINITY.get(), helper.getHolder(AMMagic.WATER)).build());
        testRitual(helper, AMEntities.FIRE_GUARDIAN.get(), new BlockPos(1, 2, 1), essence);
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":earth_guardian_spawn_test")
    @TestHolder(description = "Test that the earth guardian spawns after its spawn ritual has ben performed")
    public static void testEarthGuardianSpawnRitual(CustomGameTestHelper helper) {
        testRitual(helper, AMEntities.EARTH_GUARDIAN.get(), new BlockPos(2, 2, 2), new ItemStack(Items.EMERALD), new ItemStack(AMItems.CHIMERITE.get()), new ItemStack(AMItems.TOPAZ.get()));
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":air_guardian_spawn_test", required = false)//TODO y > 127
    @TestHolder(description = "Test that the air guardian spawns after its spawn ritual has ben performed")
    public static void testAirGuardianSpawnRitual(CustomGameTestHelper helper) {
        testRitual(helper, AMEntities.AIR_GUARDIAN.get(), new BlockPos(1, 2, 1), new ItemStack(AMItems.TARMA_ROOT.get()));
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":ice_guardian_spawn_test")
    @TestHolder(description = "Test that the ice guardian spawns after its spawn ritual has ben performed")
    public static void testIceGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setBiome(Biomes.ICE_SPIKES);
        testRitual(helper, AMEntities.ICE_GUARDIAN.get(), new BlockPos(2, 2, 1), player -> {
            helper.setBlock(new BlockPos(1, 1, 1), Blocks.SNOW_BLOCK);
            helper.setBlock(new BlockPos(1, 2, 1), Blocks.SNOW_BLOCK);
            ItemStack stack = new ItemStack(Items.CARVED_PUMPKIN);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            helper.placeAt(player, stack, new BlockPos(1, 2, 1), Direction.UP);
        });
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":lightning_guardian_spawn_test", skyAccess = true)
    @TestHolder(description = "Test that the lightning guardian spawns after its spawn ritual has ben performed")
    public static void testLightningGuardianSpawnRitual(CustomGameTestHelper helper) {
        testRitual(helper, AMEntities.LIGHTNING_GUARDIAN.get(), new BlockPos(2, 1, 2), _ -> helper.spawn(EntityType.LIGHTNING_BOLT, new BlockPos(1, 3, 1)));
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":life_guardian_spawn_test")
    @TestHolder(description = "Test that the life guardian spawns after its spawn ritual has ben performed")
    public static void testLifeGuardianSpawnRitual(CustomGameTestHelper helper) {
        helper.setMoonPhase(MoonPhase.NEW_MOON);
        helper.setNight();
        testRitual(helper, AMEntities.LIFE_GUARDIAN.get(), new BlockPos(1, 2, 1), player -> {
            Villager baby = helper.spawn(EntityType.VILLAGER, new BlockPos(3, 1, 3));
            baby.setBaby(true);
            helper.hurt(baby, helper.getLevel().damageSources().playerAttack(player), 100);
        });
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":arcane_guardian_spawn_test", required = false)//TODO fix detection (fails often because of the guardian's teleport spell)
    @TestHolder(description = "Test that the arcane guardian spawns after its spawn ritual has ben performed")
    public static void testArcaneGuardianSpawnRitual(CustomGameTestHelper helper) {
        testRitual(helper, AMEntities.ARCANE_GUARDIAN.get(), new BlockPos(2, 2, 1), ArsMagicaApi.book().create());
    }

    @GameTest(template = ArsMagicaApi.MOD_ID + ":ender_guardian_spawn_test", required = false)//TODO end
    @TestHolder(description = "Test that the ender guardian spawns after its spawn ritual has ben performed")
    public static void testEnderGuardianSpawnRitual(CustomGameTestHelper helper) {
        testRitual(helper, AMEntities.ENDER_GUARDIAN.get(), new BlockPos(2, 2, 2), new ItemStack(Items.ENDER_EYE));
    }

    private static <T extends Entity> void testRitual(CustomGameTestHelper helper, EntityType<T> entityType, BlockPos playerPosition, ItemStack... items) {
        testRitual(helper, entityType, playerPosition, player -> {
            for (ItemStack item : items) {
                player.drop(item, false);
            }
        });
    }

    private static <T extends Entity> void testRitual(CustomGameTestHelper helper, EntityType<T> entityType, BlockPos playerPosition, Consumer<GameTestPlayer> ritualTriggerAction) {
        helper.startSequenceWithPlayerNoPickup(playerPosition)
              .thenExecuteAfter(SETTLE_TIME, ritualTriggerAction)
              .thenAssertEntityPresentAfter(ITEM_CHECK_TIME, entityType)
              .thenCleanup()
              .thenSucceed();
    }
}
