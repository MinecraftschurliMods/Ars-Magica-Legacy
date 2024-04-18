package com.github.minecraftschurlimods.arsmagicalegacy.test.util;

import it.unimi.dsi.fastutil.ints.IntIterables;
import it.unimi.dsi.fastutil.ints.IntIterators;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.testframework.gametest.ExtendedSequence;
import net.neoforged.testframework.gametest.ParametrizedGameTestSequence;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class CustomGameTestSequence<T, SELF extends CustomGameTestSequence<T, SELF>> extends ParametrizedGameTestSequence<T> {
    protected final CustomGameTestHelper helper;
    private final ExtendedSequence sequence;

    protected CustomGameTestSequence(CustomGameTestHelper helper, ExtendedSequence sequence, Supplier<T> value) {
        super(helper.testInfo, sequence, value);
        this.helper = helper;
        this.sequence = sequence;
    }

    @Override
    public SELF thenWaitUntil(Runnable condition) {
        return (SELF) super.thenWaitUntil(condition);
    }

    @Override
    public SELF thenWaitUntil(Consumer<T> condition) {
        return (SELF) super.thenWaitUntil(condition);
    }

    @Override
    public SELF thenWaitUntil(long ticks, Runnable condition) {
        return (SELF) super.thenWaitUntil(ticks, condition);
    }

    @Override
    public SELF thenWaitUntil(long ticks, Consumer<T> condition) {
        return (SELF) super.thenWaitUntil(ticks, condition);
    }

    @Override
    public SELF thenExecute(Runnable runnable) {
        return (SELF) super.thenExecute(runnable);
    }

    @Override
    public SELF thenExecute(Consumer<T> runnable) {
        return (SELF) super.thenExecute(runnable);
    }

    @Override
    public SELF thenExecuteAfter(int ticks, Runnable runnable) {
        return (SELF) super.thenExecuteAfter(ticks, runnable);
    }

    @Override
    public SELF thenExecuteAfter(int ticks, Consumer<T> runnable) {
        return (SELF) super.thenExecuteAfter(ticks, runnable);
    }

    @Override
    public SELF thenExecuteFor(int ticks, Runnable runnable) {
        return (SELF) super.thenExecuteFor(ticks, runnable);
    }

    @Override
    public SELF thenExecuteFor(int ticks, Consumer<T> runnable) {
        return (SELF) super.thenExecuteFor(ticks, runnable);
    }

    @Override
    public SELF thenIdle(int amount) {
        return (SELF) super.thenIdle(amount);
    }

    public SELF thenAssert(Predicate<T> condition, String message) {
        return thenExecute(player -> helper.assertTrue(condition.test(player), message));
    }

    public <E extends Entity> SELF thenAssertEntityPresent(EntityType<E> type) {
        return thenExecute(() -> helper.assertEntityPresent(type));
    }

    public <E extends Entity> SELF thenAssertEntityPresentAfter(int ticks, EntityType<E> type) {
        return thenExecuteAfter(ticks, () -> helper.assertEntityPresent(type));
    }

    public SELF thenCleanup() {
        return thenExecute(this::cleanup);
    }

    public SELF thenSequentially(int number, Consumer<CustomGameTestSequence<Integer, ?>> consumer) {
        return thenSequentially(() -> IntIterators.fromTo(0, number), consumer);
    }

    public SELF thenSequentially(int timeBetween, int number, Consumer<CustomGameTestSequence<Integer, ?>> consumer) {
        return thenSequentially(timeBetween, () -> IntIterators.fromTo(0, number), consumer);
    }

    public <I> SELF thenSequentially(Iterable<I> iterable, Consumer<CustomGameTestSequence<I, ?>> consumer) {
        return thenSequentially(0, iterable, consumer);
    }

    public <I> SELF thenSequentially(int timeBetween, Iterable<I> iterable, Consumer<CustomGameTestSequence<I, ?>> consumer) {
        for (I item : iterable) {
            consumer.accept(thenSubSequence(() -> item));
            if (timeBetween > 0) {
                thenIdle(timeBetween);
            }
        }
        return (SELF) this;
    }

    public <V> SubSequence<V> thenSubSequence(Supplier<V> value) {
        return new SubSequence<V>(value);
    }

    protected void cleanup(T it) {
        helper.killAllEntities();
    }

    public class SubSequence<V> extends CustomGameTestSequence<V, SubSequence<V>> {
        protected SubSequence(Supplier<V> value) {
            super(CustomGameTestSequence.this.helper, sequence, value);
        }

        public SELF endSubSequence() {
            return (SELF) CustomGameTestSequence.this;
        }
    }
}
