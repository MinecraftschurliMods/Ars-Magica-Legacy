package at.minecraftschurli.mods.arsmagicalegacy.gametest.util;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.ClockTimeMarker;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.phys.Vec3;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;
import net.neoforged.testframework.gametest.ExtendedSequence;
import net.neoforged.testframework.gametest.GameTestPlayer;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Optional;
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
        gameTestPlayer.snapTo(absoluteVec(pos));
        return gameTestPlayer;
    }

    public GameTestPlayer makeSurvivalPlayer(BlockPos pos, float yRot, float xRot) {
        GameTestPlayer gameTestPlayer = makeTickingMockServerPlayerInLevel(GameType.SURVIVAL);
        gameTestPlayer.snapTo(absolutePos(pos), yRot, xRot);
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
    
    public void setDaytime(ResourceKey<ClockTimeMarker> timeMarker) {
        ServerLevel level = this.getLevel();
        Holder<WorldClock> clock = level.dimensionType().defaultClock().orElseThrow();
        level.clockManager().moveToTimeMarker(clock, timeMarker);
    }

    public void setMoonPhase(MoonPhase phase) {
        ServerLevel level = this.getLevel();
        Holder<WorldClock> clock = level.dimensionType().defaultClock().orElseThrow();
        long totalTicks = level.clockManager().getTotalTicks(clock);
        long periodLength = (long) MoonPhase.COUNT * MoonPhase.PHASE_LENGTH;
        long duration = phase.startTick() - (totalTicks % periodLength);
        level.clockManager().setTotalTicks(clock, totalTicks + (duration > 0L ? duration : periodLength + duration));
    }

    public void setNight() {
        setDaytime(ClockTimeMarkers.NIGHT);
    }

    @Contract("_ -> fail")
    public final void failWithException(Exception e) throws GameTestAssertException {
        throw (GameTestAssertException) new GameTestAssertException(Component.literal(e.getMessage()), (int) this.getTick()).initCause(e);
    }

    public Spell makeSpell(List<SpellPart> shapeGroup, List<SpellPart> grammar) {
        return new Spell(Optional.empty(), Optional.empty(), List.of(SpellShapeGroup.of(shapeGroup)), 0, SpellGrammar.of(grammar), SpellDataComponentMap.EMPTY);
    }

    public Spell makeSpell(List<SpellPart> shapeGroup, List<SpellPart> grammar, SpellDataComponentMap data) {
        return new Spell(Optional.empty(), Optional.empty(), List.of(SpellShapeGroup.of(shapeGroup)), 0, SpellGrammar.of(grammar), data);
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

        public GameTestSequenceWithPlayer thenCastSpell(Spell spell) {
            return thenCastSpell(spell, false, false, 0, 0);
        }

        public GameTestSequenceWithPlayer thenCastSpell(Spell spell, boolean consume, boolean awardXp, double innateManaMultiplier, double innateStatMultiplier) {
            return thenExecute(player -> ArsMagicaApi.spellHelper().cast(spell, player.level(), player, consume, awardXp, innateManaMultiplier, innateStatMultiplier));
        }

        public GameTestSequenceWithPlayer thenCastSpellAfter(int ticks, Spell spell) {
            return thenCastSpellAfter(ticks, spell, false, false, 0, 0);
        }

        public GameTestSequenceWithPlayer thenCastSpellAfter(int ticks, Spell spell, boolean consume, boolean awardXp, double innateManaMultiplier, double innateStatMultiplier) {
            return thenExecuteAfter(ticks, player -> ArsMagicaApi.spellHelper().cast(spell, player.level(), player, consume, awardXp, innateManaMultiplier, innateStatMultiplier));
        }
    }
}
