package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class Dryad extends PathfinderMob {
    private int timer = Config.SERVER.DRYAD_BONEMEAL_TIMER.get();

    public Dryad(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ItemTags.SAPLINGS), false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6F));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!(level() instanceof ServerLevel serverLevel)) return;
        timer--;
        if (timer > 0) return;
        timer = Config.SERVER.DRYAD_BONEMEAL_TIMER.get();
        if (level().random.nextDouble() >= Config.SERVER.DRYAD_BONEMEAL_CHANCE.get()) return;
        int radius = Config.SERVER.DRYAD_BONEMEAL_RADIUS.get();
        for (final BlockPos pos : BlockPos.betweenClosed(blockPosition().offset(-radius, -radius, -radius), blockPosition().offset(radius, radius, radius))) {
            BlockState state = level().getBlockState(pos);
            if (!(state.getBlock() instanceof BonemealableBlock bonemealableBlock)) continue;
            if (!bonemealableBlock.isValidBonemealTarget(level(), pos, state, false)) continue;
            if (bonemealableBlock.isBonemealSuccess(serverLevel, serverLevel.random, pos, state)) {
                bonemealableBlock.performBonemeal(serverLevel, serverLevel.random, pos, state);
            }
            break;
        }
    }

    public static boolean checkDryadSpawnRules(EntityType<Dryad> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8;
    }
}
