package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
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

    @GameTest(template = "earth_guardian_spawn_test", timeoutTicks = 20)
    public void testEarthGuardianSpawnRitual(GameTestHelper helper) {
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
            helper.killAllEntities();
            helper.succeed();
        });
    }

    @GameTest(template = "ice_guardian_spawn_test", timeoutTicks = 20)
    public void testIceGuardianSpawnRitual(GameTestHelper helper) {
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
            helper.killAllEntities();
            helper.succeed();
        });
    }

    @GameTest(template = "life_guardian_spawn_test", timeoutTicks = 20)
    public void testLifeGuardianSpawnRitual(GameTestHelper helper) {
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
            helper.killAllEntities();
            helper.succeed();
        });
    }

    @GameTest(template = "lightning_guardian_spawn_test", timeoutTicks = 20)
    public void testLightningGuardianSpawnRitual(GameTestHelper helper) {
        ServerLevel serverlevel = helper.getLevel();
        Player player = helper.makeMockPlayer();
        player.moveTo(helper.absolutePos(new BlockPos(1, 4, 1)), 0, 90);
        serverlevel.addFreshEntity(player);
        helper.runAfterDelay(2, () -> {
            LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverlevel);
            lightningBolt.moveTo(helper.absoluteVec(Vec3.atCenterOf(new BlockPos(1, 4, 1))));
            serverlevel.addFreshEntity(lightningBolt);
        });
        helper.runAfterDelay(15, () -> {
            helper.assertEntityPresent(AMEntities.LIGHTNING_GUARDIAN.get());
            helper.killAllEntities();
            helper.succeed();
        });
    }

    //TODO: air guardian spawn ritual (needs y > 128)
    //TODO: ender guardian spawn ritual (needs end dimension)
    //TODO: fire guardian spawn ritual (needs nether dimension)
    //TODO: water guardian spawn ritual (needs water biome)
}
