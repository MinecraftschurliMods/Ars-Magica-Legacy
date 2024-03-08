/**
 * Credit to amadornes for the original of this class.
 * {@link https://github.com/TechnicalitiesMC/TKLib/blob/1.18.X/src/main/java/com/technicalitiesmc/lib/inventory/ItemFilter.java}
 */
package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.EitherCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;

public interface ItemFilter extends Predicate<ItemStack> {
    Codec<ItemFilter> CODEC = new EitherCodec<>(new EitherCodec<>(Combined.CODEC, Simple.CODEC), None.CODEC).xmap(e1 -> e1.map(e2 -> e2.map(Function.identity(), Function.identity()), Function.identity()), f -> f instanceof None none ? Either.right(none) : f instanceof Combined combined ? Either.left(Either.left(combined)) : Either.left(Either.right((Simple) f)));
    Codec<ItemFilter> NETWORK_CODEC = new EitherCodec<>(new EitherCodec<>(Combined.NETWORK_CODEC, Simple.NETWORK_CODEC), None.CODEC).xmap(e1 -> e1.map(e2 -> e2.map(Function.identity(), Function.identity()), Function.identity()), f -> f instanceof None none ? Either.right(none) : f instanceof Combined combined ? Either.left(Either.left(combined)) : Either.left(Either.right((Simple) f)));

    static ItemFilter none() {
        return None.INSTANCE;
    }

    static Builder exactly(int amount) {
        return new Builder(AmountMatchMode.EXACTLY, amount);
    }

    static ItemFilter exactly(ItemStack stack) {
        return exactly(stack.getCount()).of(stack);
    }

    static Builder atLeast(int amount) {
        return new Builder(AmountMatchMode.AT_LEAST, amount);
    }

    static ItemFilter atLeast(ItemStack stack) {
        return atLeast(stack.getCount()).of(stack);
    }

    static Builder atMost(int amount) {
        return new Builder(AmountMatchMode.AT_MOST, amount);
    }

    static ItemFilter atMost(ItemStack stack) {
        return atMost(stack.getCount()).of(stack);
    }

    static ItemFilter anyOf(ItemFilter... filters) {
        return anyOf(Arrays.asList(filters));
    }

    static ItemFilter anyOf(Collection<ItemFilter> filters) {
        if (filters.isEmpty()) return none();
        List<Simple> list = new ArrayList<>();
        for (ItemFilter filter : filters) {
            if (filter instanceof Simple s) {
                list.add(s);
                continue;
            }
            if (filter instanceof Combined c) {
                list.addAll(c.filters);
                continue;
            }
            if (filter != None.INSTANCE)
                throw new IllegalArgumentException("Only simple and combined filters are allowed to be combined.");
        }
        return new Combined(list);
    }

    boolean test(ItemStack stack);

    @Nullable
    Simple getMatchingFilter(ItemStack stack);

    ItemStack[] getMatchedStacks();

    enum AmountMatchMode implements StringRepresentable {
        EXACTLY((amount, ref) -> amount == ref ? ref : 0),
        AT_LEAST((amount, ref) -> amount >= ref ? amount : 0),
        AT_MOST((amount, ref) -> amount <= ref ? amount : 0);
        public static final Codec<AmountMatchMode> CODEC = StringRepresentable.fromEnum(AmountMatchMode::values);
        private final IntBinaryOperator test;

        AmountMatchMode(IntBinaryOperator test) {
            this.test = test;
        }

        public int test(int amount, int reference) {
            return test.applyAsInt(amount, reference);
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }

    record Builder(AmountMatchMode mode, int amount) {
        public ItemFilter of(ItemStack stack) {
            return new Simple.ItemStack(mode, amount, stack);
        }

        public ItemFilter is(ItemLike itemLike) {
            return new Simple.ItemLike(mode, amount, itemLike.asItem());
        }

        public ItemFilter is(TagKey<Item> tagKey) {
            return new Simple.TagKey(mode, amount, tagKey);
        }

        public ItemFilter is(Ingredient ingredient) {
            return new Simple.Ingredient(mode, amount, ingredient);
        }
    }

    sealed abstract class Simple implements ItemFilter {
        public static final Codec<Simple> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                AmountMatchMode.CODEC.fieldOf("mode").forGetter(e -> e.mode),
                ExtraCodecs.POSITIVE_INT.optionalFieldOf("amount", 1).forGetter(e -> e instanceof ItemStack s && s.amount == s.itemStack.getCount() ? 1 : e.amount),
                net.minecraft.world.item.ItemStack.CODEC.optionalFieldOf("stack").forGetter(e -> e instanceof ItemStack s ? Optional.of(s.itemStack) : Optional.empty()),
                net.minecraft.tags.TagKey.codec(Registries.ITEM).optionalFieldOf("tag").forGetter(e -> e instanceof TagKey s ? Optional.of(s.tagKey) : Optional.empty()),
                net.minecraft.world.item.crafting.Ingredient.CODEC_NONEMPTY.optionalFieldOf("ingredient").forGetter(e -> e instanceof Simple.Ingredient s ? Optional.of(s.ingredient) : Optional.empty()),
                BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("item").forGetter(e -> e instanceof ItemLike s ? Optional.of(s.itemLike.asItem()) : Optional.empty())
        ).apply(inst, Simple::create));
        public final int minAmount;
        public final int maxAmount;
        final AmountMatchMode mode;
        final int amount;
        private final Predicate<net.minecraft.world.item.ItemStack> predicate;

        private Simple(AmountMatchMode mode, int amount, Predicate<net.minecraft.world.item.ItemStack> predicate) {
            this.mode = mode;
            this.amount = amount;
            this.minAmount = mode == AmountMatchMode.AT_MOST ? 0 : amount;
            this.maxAmount = mode == AmountMatchMode.AT_LEAST ? 64 : amount;
            this.predicate = predicate;
        }

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        private static Simple create(AmountMatchMode mode, Integer amount, Optional<net.minecraft.world.item.ItemStack> itemStack, Optional<net.minecraft.tags.TagKey<Item>> tagKey, Optional<net.minecraft.world.item.crafting.Ingredient> ingredient, Optional<net.minecraft.world.item.Item> item) {
            if (itemStack.isPresent()) {
                if (tagKey.isPresent() || ingredient.isPresent() || item.isPresent())
                    throw new IllegalArgumentException("Cannot have both item stack and tag key/ingredient/item");
                net.minecraft.world.item.ItemStack stack = itemStack.get();
                return new ItemStack(mode, amount == 1 && stack.getCount() != 1 ? stack.getCount() : amount, stack);
            }
            if (tagKey.isPresent()) {
                if (ingredient.isPresent() || item.isPresent())
                    throw new IllegalArgumentException("Cannot have both tag key and item stack/ingredient/item");
                return new TagKey(mode, amount, tagKey.get());
            }
            if (ingredient.isPresent()) {
                if (item.isPresent())
                    throw new IllegalArgumentException("Cannot have both ingredient and item stack/tag key/item");
                return new Ingredient(mode, amount, ingredient.get());
            }
            if (item.isPresent()) return new ItemLike(mode, amount, item.get());
            throw new IllegalArgumentException("No item stack/tag key/ingredient/item");
        }

        @Override
        public boolean test(net.minecraft.world.item.ItemStack stack) {
            return predicate.test(stack);
        }

        @Override
        @Nullable
        public Simple getMatchingFilter(net.minecraft.world.item.ItemStack stack) {
            return test(stack) ? this : null;
        }

        public static final class TagKey extends Simple {
            final net.minecraft.tags.TagKey<Item> tagKey;

            private TagKey(AmountMatchMode mode, int amount, net.minecraft.tags.TagKey<Item> tagKey) {
                super(mode, amount, s -> s.is(tagKey));
                this.tagKey = tagKey;
            }

            @Override
            public net.minecraft.world.item.ItemStack[] getMatchedStacks() {
                return BuiltInRegistries.ITEM.getTag(tagKey).stream().flatMap(HolderSet::stream).map(pItem -> new net.minecraft.world.item.ItemStack(pItem, amount)).toArray(net.minecraft.world.item.ItemStack[]::new);
            }
        }

        public static final class ItemLike extends Simple {
            final net.minecraft.world.level.ItemLike itemLike;

            private ItemLike(AmountMatchMode mode, int amount, net.minecraft.world.level.ItemLike itemLike) {
                super(mode, amount, s -> s.is(itemLike.asItem()));
                this.itemLike = itemLike;
            }

            @Override
            public net.minecraft.world.item.ItemStack[] getMatchedStacks() {
                return new net.minecraft.world.item.ItemStack[]{new net.minecraft.world.item.ItemStack(itemLike.asItem(), amount)};
            }
        }

        public static final class ItemStack extends Simple {
            final net.minecraft.world.item.ItemStack itemStack;

            private ItemStack(AmountMatchMode mode, int amount, net.minecraft.world.item.ItemStack itemStack) {
                super(mode, amount, s -> ItemHandlerHelper.canItemStacksStack(itemStack, s));
                this.itemStack = itemStack;
            }

            @Override
            public net.minecraft.world.item.ItemStack[] getMatchedStacks() {
                return new net.minecraft.world.item.ItemStack[]{itemStack.copy()};
            }
        }

        public static final class Ingredient extends Simple {
            final net.minecraft.world.item.crafting.Ingredient ingredient;

            private Ingredient(AmountMatchMode mode, int amount, net.minecraft.world.item.crafting.Ingredient ingredient) {
                super(mode, amount, ingredient);
                this.ingredient = ingredient;
            }

            @Override
            public net.minecraft.world.item.ItemStack[] getMatchedStacks() {
                return Arrays.stream(ingredient.getItems()).map(i -> {
                    net.minecraft.world.item.ItemStack copy = i.copy();
                    copy.setCount(amount);
                    return copy;
                }).toArray(net.minecraft.world.item.ItemStack[]::new);
            }
        }
    }

    final class None implements ItemFilter {
        static final None INSTANCE = new None();
        public static final Codec<None> CODEC = Codec.unit(INSTANCE);
        private static final ItemStack[] MATCHED_STACKS = new ItemStack[0];

        private None() {}

        @Override
        public boolean test(ItemStack stack) {
            return false;
        }

        @Override
        @Nullable
        public Simple getMatchingFilter(ItemStack stack) {
            return null;
        }

        @Override
        public ItemStack[] getMatchedStacks() {
            return MATCHED_STACKS;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    final class Combined implements ItemFilter {
        public static final Codec<Combined> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ItemFilter.CODEC.listOf().xmap(f -> (Combined) anyOf(f), combined -> combined.filters instanceof List filters ? filters : new ArrayList<>(combined.filters)));
        public static final Codec<Combined> NETWORK_CODEC = ExtraCodecs.lazyInitializedCodec(() -> ItemFilter.NETWORK_CODEC.listOf().xmap(f -> (Combined) anyOf(f), combined -> combined.filters instanceof List filters ? filters : new ArrayList<>(combined.filters)));
        private final Collection<Simple> filters;

        private Combined(Collection<Simple> filters) {
            this.filters = filters;
        }

        @Override
        public boolean test(ItemStack stack) {
            for (Simple filter : filters) {
                if (filter.test(stack)) return true;
            }
            return false;
        }

        @Override
        @Nullable
        public Simple getMatchingFilter(ItemStack stack) {
            for (Simple filter : filters) {
                if (filter.test(stack)) return filter;
            }
            return null;
        }

        @Override
        public ItemStack[] getMatchedStacks() {
            return filters.stream().flatMap(f -> Arrays.stream(f.getMatchedStacks())).toArray(ItemStack[]::new);
        }
    }
}
