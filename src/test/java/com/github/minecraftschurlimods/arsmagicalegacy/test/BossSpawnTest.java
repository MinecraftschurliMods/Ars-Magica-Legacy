package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.server.NatureGuardianSpawnHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.BeforeBatch;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unused")
@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class BossSpawnTest {
    private static final String BATCH = "boss_spawn";
    private static Map<UUID, Long> lastDryadKills;
    private static Map<UUID, Integer> dryadKills;

    @SuppressWarnings("unchecked")
    @BeforeBatch(batch = BATCH)
    public static void beforeBatch(ServerLevel level) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(NatureGuardianSpawnHandler.class, MethodHandles.lookup());
            lastDryadKills = (Map<UUID, Long>) lookup.findStaticVarHandle(NatureGuardianSpawnHandler.class, "lastDryadKills", Map.class).get();
            dryadKills = (Map<UUID, Integer>) lookup.findStaticVarHandle(NatureGuardianSpawnHandler.class, "dryadKills", Map.class).get();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw wrapException(e);
        }
    }

    @GameTest(template = "nature_guardian_spawn_test", batch = BATCH)
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
            helper.runAfterDelay(index + tickOffset, () -> {
                dryad.hurt(serverlevel.damageSources().playerAttack(player), dryad.getMaxHealth() * 2);
                if (lastDryadKills.get(player.getGameProfile().getId()) != serverlevel.getGameTime()) {
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
        helper.runAfterDelay(numDryads + tickOffset + 2, () -> {
            helper.assertEntityPresent(AMEntities.NATURE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "water_guardian_spawn_test", batch = BATCH, timeoutTicks = 20)
    public static void testWaterGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        setupBiome(helper, serverlevel, Biomes.OCEAN);
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            player.drop(new ItemStack(Items.OAK_BOAT), false);
            player.drop(new ItemStack(Items.WATER_BUCKET), false);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.WATER_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "fire_guardian_spawn_test", batch = BATCH, timeoutTicks = 20, required = false)//TODO nether
    public static void testFireGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            player.drop(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(Affinity.WATER), false);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.FIRE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "earth_guardian_spawn_test", batch = BATCH, timeoutTicks = 20)
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

    @GameTest(template = "air_guardian_spawn_test", batch = BATCH, timeoutTicks = 20, required = false)//TODO y > 127
    public static void testAirGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            player.drop(new ItemStack(AMItems.TARMA_ROOT.get()), false);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.AIR_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "ice_guardian_spawn_test", batch = BATCH, timeoutTicks = 20)
    public static void testIceGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        setupBiome(helper, serverlevel, Biomes.ICE_SPIKES);
        Player player = FakePlayerFactory.getMinecraft(serverlevel);
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

    @GameTest(template = "lightning_guardian_spawn_test", batch = BATCH, timeoutTicks = 20)
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

    @GameTest(template = "life_guardian_spawn_test", batch = BATCH, timeoutTicks = 20)
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
            baby.hurt(serverlevel.damageSources().playerAttack(player), 100);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.LIFE_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    @GameTest(template = "arcane_guardian_spawn_test", batch = BATCH, timeoutTicks = 20, required = false)//TODO fix detection (fails often because of the guardian's teleport spell)
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

    @GameTest(template = "ender_guardian_spawn_test", batch = BATCH, timeoutTicks = 20, required = false)//TODO end
    public static void testEnderGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(2, 4, 2)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            player.drop(new ItemStack(Items.ENDER_EYE), false);
        });
        helper.runAfterDelay(10, () -> {
            helper.assertEntityPresent(AMEntities.ENDER_GUARDIAN.get());
            cleanup(helper, player);
        });
    }

    private static void setupBiome(GameTestHelper helper, ServerLevel serverlevel, ResourceKey<Biome> biomeKey) {
        AABB structureBounds = helper.testInfo.getStructureBounds();
        BlockPos structureMin = BlockPos.containing(structureBounds.minX, structureBounds.minY, structureBounds.minZ);
        BlockPos structureMax = BlockPos.containing(structureBounds.maxX, structureBounds.maxY, structureBounds.maxZ);
        BoundingBox boundingbox = BoundingBox.fromCorners(quantize(structureMin), quantize(structureMax));
        List<ChunkAccess> list = new ArrayList<>();

        for(int k = SectionPos.blockToSectionCoord(boundingbox.minZ()); k <= SectionPos.blockToSectionCoord(boundingbox.maxZ()); ++k) {
            for(int l = SectionPos.blockToSectionCoord(boundingbox.minX()); l <= SectionPos.blockToSectionCoord(boundingbox.maxX()); ++l) {
                ChunkAccess chunkaccess = serverlevel.getChunk(l, k, ChunkStatus.FULL, false);
                if (chunkaccess == null) {
                    throw new IllegalStateException("ServerWorld has no chunk at " + l + ", " + k + " (does not have a map chunk at that position)");
                }

                list.add(chunkaccess);
            }
        }

        Holder<Biome> biome = serverlevel.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(biomeKey);
        for(ChunkAccess chunkaccess1 : list) {
            chunkaccess1.fillBiomesFromNoise((x, y, z, sampler) -> {
                if (boundingbox.isInside(QuartPos.toBlock(x), QuartPos.toBlock(y), QuartPos.toBlock(z))) {
                    return biome;
                } else {
                    return chunkaccess1.getNoiseBiome(x, y, z);
                }
            }, serverlevel.getChunkSource().randomState().sampler());
            chunkaccess1.setUnsaved(true);
        }

        serverlevel.getChunkSource().chunkMap.resendBiomesForChunks(list);
    }

    private static BlockPos quantize(BlockPos pPos) {
        return new BlockPos(
                QuartPos.toBlock(QuartPos.fromBlock((pPos.getX()))),
                QuartPos.toBlock(QuartPos.fromBlock((pPos.getY()))),
                QuartPos.toBlock(QuartPos.fromBlock((pPos.getZ())))
        );
    }

    private static void cleanup(GameTestHelper helper, Player player) {
        helper.killAllEntities();
        player.discard();
        helper.succeed();
    }

    private static GameTestAssertException wrapException(Exception e) {
        return (GameTestAssertException) new GameTestAssertException(e.getMessage()).initCause(e);
    }
}
