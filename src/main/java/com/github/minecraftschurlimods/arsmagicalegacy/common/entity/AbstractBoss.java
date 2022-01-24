package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBoss extends Monster {
    private final ServerBossEvent bossEvent;
    private int ticksSinceLastPlayerScan = 0;

    public AbstractBoss(EntityType<? extends AbstractBoss> type, Level level) {
        this(type, level, BossEvent.BossBarColor.PINK);
    }

    public AbstractBoss(EntityType<? extends AbstractBoss> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        bossEvent = new ServerBossEvent(getType().getDescription(), color, BossEvent.BossBarOverlay.PROGRESS);
        maxUpStep = 1.02F;
        if (!level.isClientSide) {
            for (ServerPlayer player : ((ServerLevel) level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0D, 128D, 0D, 192D)))) {
                bossEvent.addPlayer(player);
            }
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
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
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1, 10));
        goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8F));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, null));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (bossEvent != null) {
            bossEvent.setProgress(getHealth() / getMaxHealth());
            if (!level.isClientSide) {
                bossEvent.setVisible(isAlive());
                if (++ticksSinceLastPlayerScan >= 20) {
                    updatePlayers();
                    ticksSinceLastPlayerScan = 0;
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.IN_WALL) {
            if (!level.isClientSide) {
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

    protected SoundEvent getAttackSound() {
        return null;
    }

    private void updatePlayers() {
        if (!level.isClientSide) {
            Set<ServerPlayer> newSet = new HashSet<>();
            for (ServerPlayer player : ((ServerLevel) level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0D, 128D, 0D, 192D)))) {
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
}
