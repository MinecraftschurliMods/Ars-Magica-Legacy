package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Dryad extends PathfinderMob {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_dryad");
    private int timer = AMServerConfig.DRYAD_GROW_INTERVAL.get();

    public Dryad(EntityType<? extends Dryad> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.TEMPT_RANGE, 10.0);
    }

    public static boolean checkSpawnRules(EntityType<Dryad> entityType, ServerLevelAccessor level, EntitySpawnReason spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AMTags.Blocks.DRYADS_SPAWNABLE_ON) || checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new PanicGoal(this, 1.5));
        goalSelector.addGoal(3, new TemptGoal(this, 1.25, is -> is.is(ItemTags.SAPLINGS), false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!(level() instanceof ServerLevel level)) return;
        timer--;
        if (timer > 0) return;
        timer = AMServerConfig.DRYAD_GROW_INTERVAL.get();
        RandomSource random = level.getRandom();
        if (random.nextDouble() >= AMServerConfig.DRYAD_GROW_CHANCE.get()) return;
        int radius = AMServerConfig.DRYAD_GROW_RADIUS.get();
        FakePlayer player = FakePlayerFactory.get(level, GAME_PROFILE);
        List<BlockPos> list = new ArrayList<>();
        BlockPos.betweenClosed(blockPosition().offset(-radius, -radius, -radius), blockPosition().offset(radius, radius, radius)).forEach(list::add);
        Collections.shuffle(list);
        for (BlockPos pos : list) {
            BlockState state = level().getBlockState(pos);
            boolean grown = false;
            for (Plant plant : AMUtil.getPlants(state, registryAccess())) {
                GrowthContext context = plant.createContext(player, level, pos, state, ItemStack.EMPTY);
                if (plant.growthType().canGrow(context)) {
                    plant.growthType().grow(context);
                    grown = true;
                    break;
                }
            }
            if (!grown && state.getBlock() instanceof BonemealableBlock block && block.isValidBonemealTarget(level(), pos, state)) {
                if (block.isBonemealSuccess(level, random, pos, state)) {
                    block.performBonemeal(level, random, pos, state);
                }
                break;
            }
        }
    }
}
