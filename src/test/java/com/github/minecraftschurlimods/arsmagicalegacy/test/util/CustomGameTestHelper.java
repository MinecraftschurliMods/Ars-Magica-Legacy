package com.github.minecraftschurlimods.arsmagicalegacy.test.util;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;
import net.neoforged.testframework.gametest.ExtendedSequence;
import net.neoforged.testframework.gametest.GameTestPlayer;
import org.jetbrains.annotations.Contract;

import java.util.function.Supplier;

public class CustomGameTestHelper extends ExtendedGameTestHelper {
    public CustomGameTestHelper(GameTestInfo info) {
        super(info);
    }

    public GameTestPlayer makeSurvivalPlayer() {
        return makeTickingMockServerPlayerInLevel(GameType.SURVIVAL).moveToCentre();
    }

    public GameTestPlayer makeSurvivalPlayer(Vec3 pos) {
        GameTestPlayer gameTestPlayer = makeTickingMockServerPlayerInLevel(GameType.SURVIVAL);
        gameTestPlayer.moveTo(absoluteVec(pos));
        return gameTestPlayer;
    }

    public GameTestPlayer makeSurvivalPlayer(BlockPos pos, float yRot, float xRot) {
        GameTestPlayer gameTestPlayer = makeTickingMockServerPlayerInLevel(GameType.SURVIVAL);
        gameTestPlayer.moveTo(absolutePos(pos), yRot, xRot);
        return gameTestPlayer;
    }

    public GameTestPlayer makeSurvivalPlayer(BlockPos pos) {
        return makeSurvivalPlayer(pos, 0, 90);
    }

    @Override
    public <T> CustomGameTestSequence<T, ?> startSequence(Supplier<T> value) {
        return new CustomGameTestSequence<>(this, this.startSequence(), value);
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayer() {
        return new GameTestSequenceWithPlayer(this, this.startSequence(), this::makeSurvivalPlayer);
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayer(Vec3 pos) {
        return new GameTestSequenceWithPlayer(this, this.startSequence(), () -> makeSurvivalPlayer(pos));
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayer(BlockPos pos, float yRot, float xRot) {
        return new GameTestSequenceWithPlayer(this, this.startSequence(), () -> makeSurvivalPlayer(pos, yRot, xRot));
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayer(BlockPos pos) {
        return new GameTestSequenceWithPlayer(this, this.startSequence(), () -> makeSurvivalPlayer(pos));
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayerNoPickup(Vec3 pos) {
        return startSequenceWithPlayer(pos).thenExecute(GameTestPlayer::preventItemPickup);
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayerNoPickup(BlockPos pos, float yRot, float xRot) {
        return startSequenceWithPlayer(pos, yRot, xRot).thenExecute(GameTestPlayer::preventItemPickup);
    }

    public GameTestSequenceWithPlayer startSequenceWithPlayerNoPickup(BlockPos pos) {
        return startSequenceWithPlayer(pos).thenExecute(GameTestPlayer::preventItemPickup);
    }

    @Contract("_ -> fail")
    public final void failWithException(Exception e) throws GameTestAssertException {
        throw (GameTestAssertException) new GameTestAssertException(e.getMessage()).initCause(e);
    }

    public static class GameTestSequenceWithPlayer extends CustomGameTestSequence<GameTestPlayer, GameTestSequenceWithPlayer> {
        public GameTestSequenceWithPlayer(CustomGameTestHelper helper, ExtendedSequence sequence, Supplier<GameTestPlayer> value) {
            super(helper, sequence, value);
        }

        @Override
        protected void cleanup(GameTestPlayer it) {
            it.discard();
            super.cleanup(it);
        }
    }
}
