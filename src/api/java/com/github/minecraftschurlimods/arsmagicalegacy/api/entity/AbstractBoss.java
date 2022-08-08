package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBoss extends Monster implements ISpellCasterEntity {
    private final ServerBossEvent bossEvent;
    private int ticksInAction = 0;
    private int ticksSinceLastPlayerScan = 0;
    protected Action action;

    public AbstractBoss(EntityType<? extends AbstractBoss> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PINK);
    }

    public AbstractBoss(EntityType<? extends AbstractBoss> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        bossEvent = new ServerBossEvent(getType().getDescription(), color, BossEvent.BossBarOverlay.PROGRESS);
        if (!level.isClientSide()) {
            for (ServerPlayer player : ((ServerLevel) level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0.0D, 128.0D, 0.0D, 192.0D)))) {
                bossEvent.addPlayer(player);
            }
        }
        setIdle();
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public float getStepHeight() {
        return 1.02f;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1, 1));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8F));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        targetSelector.addGoal(0, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 2, true, false, null));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (bossEvent != null) {
            bossEvent.setProgress(getHealth() / getMaxHealth());
            if (!level.isClientSide()) {
                bossEvent.setVisible(isAlive());
                if (ticksSinceLastPlayerScan++ >= 20) {
                    updatePlayers();
                    ticksSinceLastPlayerScan = 0;
                }
            }
        }
        ticksInAction++;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof AbstractBoss) return false;
        if (pSource == DamageSource.IN_WALL) {
            if (!level.isClientSide()) {
                int width = Math.round(getBbWidth());
                int height = Math.round(getBbHeight());
                for (int x = -width; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        for (int z = -width; z < width; z++) {
                            level.destroyBlock(new BlockPos(getX() + x, getY() + y, getZ() + z), true, this);
                        }
                    }
                }
            }
            return false;
        }
        SoundEvent sound = getHurtSound(pSource);
        if (sound != null) {
            level.playSound(null, this, sound, SoundSource.HOSTILE, 1f, 0.5f + random.nextFloat() * 0.5f);
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void kill() {
        remove(Entity.RemovalReason.KILLED);
        bossEvent.setProgress(0);
        bossEvent.setVisible(false);
        bossEvent.removeAllPlayers();
        updatePlayers();
    }

    @Override
    public boolean canCastSpell() {
        return action == getIdleAction();
    }

    @Override
    public boolean isCastingSpell() {
        return action == getCastingAction();
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            action = getCastingAction();
        } else if (action == getCastingAction()) {
            setIdle();
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        for (Action a : getActions()) {
            if (a.getAnimationId() == pId) {
                action = a;
            }
        }
        super.handleEntityEvent(pId);
    }

    /**
     * @return The attack sound of this boss, or null if no attack sound should be played.
     */
    @Nullable
    public SoundEvent getAttackSound() {
        return null;
    }

    /**
     * @return The current action of this boss.
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the current action.
     * @param action The action to set.
     */
    public void setAction(Action action) {
        this.action = action;
        ticksInAction = 0;
        level.broadcastEntityEvent(this, action.getAnimationId());
    }

    /**
     * @return Whether this boss is currently in idle state or not.
     */
    public boolean isIdle() {
        return action == getIdleAction();
    }

    /**
     * Sets this boss into idle state.
     */
    public void setIdle() {
        setAction(getIdleAction());
    }

    /**
     * @return The amount of ticks the boss is already using the current action.
     */
    public int getTicksInAction() {
        return ticksInAction;
    }

    /**
     * @return The action representing an idle state.
     */
    public abstract Action getIdleAction();

    /**
     * @return The action representing a casting state.
     */
    public abstract Action getCastingAction();

    /**
     * @return All actions this boss can have.
     */
    protected abstract Action[] getActions();

    private void updatePlayers() {
        if (!level.isClientSide()) {
            Set<ServerPlayer> newSet = new HashSet<>();
            for (ServerPlayer player : ((ServerLevel) level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0, 128, 0, 192)))) {
                bossEvent.addPlayer(player);
                startSeenByPlayer(player);
                newSet.add(player);
            }
            Set<ServerPlayer> oldSet = Sets.newHashSet(bossEvent.getPlayers());
            oldSet.removeAll(newSet);
            for (ServerPlayer player : oldSet) {
                bossEvent.removePlayer(player);
                stopSeenByPlayer(player);
            }
        }
    }

    /**
     * Marker interface for actions the bosses can use.
     */
    public interface Action {
        byte IDLE_ID = (byte) 24;
        byte CASTING_ID = (byte) 25;
        byte ACTION_1_ID = (byte) 26;
        byte ACTION_2_ID = (byte) 27;
        byte ACTION_3_ID = (byte) 28;

        /**
         * @return The time using this action requires.
         */
        int getMaxActionTime();

        /**
         * @return The animation id, used in {@code broadcastEntityEvent} and {@code handleEntityEvent}.
         */
        byte getAnimationId();
    }
}
