/**
 * Credit to amadornes for the original of this class {@link https://github.com/TechnicalitiesMC/TKLib/blob/1.18.X/src/main/java/com/technicalitiesmc/lib/inventory/ItemFilter.java}
 */
package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public interface ItemFilter extends Predicate<ItemStack> {
    Codec<ItemFilter> CODEC = new ExtraCodecs.EitherCodec<>(None.CODEC, new ExtraCodecs.EitherCodec<>(Combined.CODEC, Simple.CODEC)).xmap(e -> e.map(Function.identity(), e2 -> e2.map(Function.identity(), Function.identity())), f -> f instanceof None none ? Either.left(none) : f instanceof Combined combined ? Either.right(Either.left(combined)) : Either.right(Either.right((Simple) f)));
    Codec<ItemFilter> NETWORK_CODEC = new ExtraCodecs.EitherCodec<>(None.CODEC, new ExtraCodecs.EitherCodec<>(Combined.CODEC, Simple.NETWORK_CODEC)).xmap(e -> e.map(Function.identity(), e2 -> e2.map(Function.identity(), Function.identity())), f -> f instanceof None none ? Either.left(none) : f instanceof Combined combined ? Either.right(Either.left(combined)) : Either.right(Either.right((Simple) f)));

    boolean test(ItemStack stack);

    @Nullable Simple getMatchingFilter(ItemStack stack);

    static ItemFilter none() {
        return None.INSTANCE;
    }

    static Builder exactly(int amt) {
        return new Builder(AmountMatchMode.EXACTLY, amt);
    }

    static Builder atLeast(int amt) {
        return new Builder(AmountMatchMode.AT_LEAST, amt);
    }

    static Builder atMost(int amt) {
        return new Builder(AmountMatchMode.AT_MOST, amt);
    }

    static ItemFilter exactly(ItemStack stack) {
        return exactly(stack.getCount()).of(stack);
    }

    static ItemFilter atLeast(ItemStack stack) {
        return atLeast(stack.getCount()).of(stack);
    }

    static ItemFilter atMost(ItemStack stack) {
        return atMost(stack.getCount()).of(stack);
    }

    static ItemFilter anyOf(ItemFilter... filters) {
        return anyOf(Arrays.asList(filters));
    }

    static ItemFilter anyOf(Collection<ItemFilter> filters) {
        if (filters.isEmpty()) {
            return none();
        }
        var simpleFilters = new ArrayList<Simple>();
        for (var filter : filters) {
            if (filter instanceof Simple s) {
                simpleFilters.add(s);
                continue;
            }
            if (filter instanceof Combined c) {
                simpleFilters.addAll(c.filters);
                continue;
            }
            if (filter == None.INSTANCE) {
                continue;
            }
            throw new IllegalArgumentException("Only simple and combined filters are allowed to be combined.");
        }
        return new Combined(simpleFilters);
    }

    ItemStack[] getMatchedStacks();

    record Builder(AmountMatchMode mode, int amount) {
        public ItemFilter of(ItemStack stack) {
            return new Simple.Stack(mode, amount, stack);
        }

        public ItemFilter is(ItemLike item) {
            return new Simple.Item(mode, amount, item.asItem());
        }

        public ItemFilter is(TagKey<Item> tag) {
            return new Simple.Tag(mode, amount, tag);
        }

        public ItemFilter is(Ingredient ingredient) {
            return new Simple.Ingredient(mode, amount, ingredient);
        }
    }

    enum AmountMatchMode implements StringRepresentable {
        EXACTLY((in, ref) -> in == ref ? ref : 0),
        AT_LEAST((in, ref) -> in >= ref ? in : 0),
        AT_MOST(Math::min);
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

    final class None implements ItemFilter {
        static final None INSTANCE = new None();
        public static final Codec<None> CODEC = Codec.unit(INSTANCE);

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
            return new ItemStack[0];
        }
    }

    sealed abstract class Simple implements ItemFilter {
        public static final Codec<Simple> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                AmountMatchMode.CODEC.fieldOf("mode").forGetter(simple -> simple.mode),
                ExtraCodecs.POSITIVE_INT.optionalFieldOf("amount", 1).forGetter(simple -> simple instanceof Simple.Stack s && s.amount == s.stack.getCount() ? s.stack.getCount() : simple.amount),
                ItemStack.CODEC.optionalFieldOf("stack").forGetter(simple -> simple instanceof Simple.Stack s ? Optional.of(s.stack) : Optional.empty()),
                TagKey.codec(Registry.ITEM_REGISTRY).optionalFieldOf("tag").forGetter(simple -> simple instanceof Simple.Tag s ? Optional.of(s.tag) : Optional.empty()),
                CodecHelper.INGREDIENT.optionalFieldOf("ingredient").forGetter(simple -> simple instanceof Simple.Ingredient s ? Optional.of(s.ingredient) : Optional.empty()),
                ForgeRegistries.ITEMS.getCodec().optionalFieldOf("item").forGetter(simple -> simple instanceof Simple.Item s ? Optional.of(s.item.asItem()) : Optional.empty())
        ).apply(instance, Simple::create));
        public static final Codec<Simple> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                AmountMatchMode.CODEC.fieldOf("mode").forGetter(simple -> simple.mode),
                ExtraCodecs.POSITIVE_INT.optionalFieldOf("amount", 1).forGetter(simple -> simple instanceof Simple.Stack s && s.amount == s.stack.getCount() ? s.stack.getCount() : simple.amount),
                ItemStack.CODEC.optionalFieldOf("stack").forGetter(simple -> simple instanceof Simple.Stack s ? Optional.of(s.stack) : Optional.empty()),
                TagKey.codec(Registry.ITEM_REGISTRY).optionalFieldOf("tag").forGetter(simple -> simple instanceof Simple.Tag s ? Optional.of(s.tag) : Optional.empty()),
                CodecHelper.NETWORK_INGREDIENT.optionalFieldOf("ingredient").forGetter(simple -> simple instanceof Simple.Ingredient s ? Optional.of(s.ingredient) : Optional.empty()),
                ForgeRegistries.ITEMS.getCodec().optionalFieldOf("item").forGetter(simple -> simple instanceof Simple.Item s ? Optional.of(s.item.asItem()) : Optional.empty())
        ).apply(instance, Simple::create));

        final AmountMatchMode mode;
        final int amount;
        private final int minAmount;
        private final int maxAmount;
        private final Predicate<ItemStack> predicate;

        private Simple(AmountMatchMode mode, int amount, Predicate<ItemStack> predicate) {
            this.mode = mode;
            this.amount = amount;
            this.minAmount = mode == AmountMatchMode.AT_MOST ? 0 : amount;
            this.maxAmount = mode == AmountMatchMode.AT_LEAST ? 64 : amount;
            this.predicate = predicate;
        }

        @Override
        public boolean test(ItemStack stack) {
            return predicate.test(stack);
        }

        @Override
        @Nullable
        public Simple getMatchingFilter(ItemStack stack) {
            return test(stack) ? this : null;
        }

        public int getMinAmount() {
            return minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        private static Simple create(AmountMatchMode mode, Integer amount, Optional<ItemStack> stack, Optional<TagKey<net.minecraft.world.item.Item>> tag, Optional<net.minecraft.world.item.crafting.Ingredient> ingredient, Optional<net.minecraft.world.item.Item> item) {
            if (stack.isPresent()) {
                if (tag.isPresent() || ingredient.isPresent() || item.isPresent()) {
                    throw new IllegalArgumentException("Cannot have both stack and tag/ingredient/item");
                }
                ItemStack stack1 = stack.get();
                if (amount == 1 && stack1.getCount() != 1) {
                    return new Stack(mode, stack1.getCount(), stack1);
                }
                return new Stack(mode, amount, stack1);
            }
            if (tag.isPresent()) {
                if (ingredient.isPresent() || item.isPresent()) {
                    throw new IllegalArgumentException("Cannot have both tag and stack/ingredient/item");
                }
                return new Tag(mode, amount, tag.get());
            }
            if (ingredient.isPresent()) {
                if (item.isPresent()) {
                    throw new IllegalArgumentException("Cannot have both ingredient and stack/tag/item");
                }
                return new Ingredient(mode, amount, ingredient.get());
            }
            if (item.isPresent()) {
                return new Item(mode, amount, item.get());
            }
            throw new IllegalArgumentException("No stack/tag/ingredient/item");
        }

        private static final class Tag extends Simple {
            final TagKey<net.minecraft.world.item.Item> tag;

            private Tag(AmountMatchMode mode, int amount, TagKey<net.minecraft.world.item.Item> tag) {
                super(mode, amount, s -> s.is(tag));
                this.tag = tag;
            }

            @Override
            public ItemStack[] getMatchedStacks() {
                return ForgeRegistries.ITEMS.tags().getTag(tag).stream().map(pItem -> new ItemStack(pItem, amount)).toArray(ItemStack[]::new);
            }
        }

        private static final class Item extends Simple {
            final ItemLike item;

            private Item(AmountMatchMode mode, int amount, ItemLike item) {
                super(mode, amount, s -> s.is(item.asItem()));
                this.item = item;
            }

            @Override
            public ItemStack[] getMatchedStacks() {
                return new ItemStack[] { new ItemStack(item.asItem(), amount) };
            }
        }

        private static final class Stack extends Simple {
            final ItemStack stack;

            private Stack(AmountMatchMode mode, int amount, ItemStack stack) {
                super(mode, amount, s -> ItemHandlerHelper.canItemStacksStack(stack, s));
                this.stack = stack;
            }

            @Override
            public ItemStack[] getMatchedStacks() {
                return new ItemStack[] { stack.copy() };
            }
        }

        private static final class Ingredient extends Simple {
            final net.minecraft.world.item.crafting.Ingredient ingredient;

            private Ingredient(AmountMatchMode mode, int amount, net.minecraft.world.item.crafting.Ingredient ingredient) {
                super(mode, amount, ingredient);
                this.ingredient = ingredient;
            }

            @Override
            public ItemStack[] getMatchedStacks() {
                return Arrays.stream(ingredient.getItems()).map(i -> {
                    ItemStack copy = i.copy();
                    copy.setCount(amount);
                    return copy;
                }).toArray(ItemStack[]::new);
            }
        }
    }

    final class Combined implements ItemFilter {
        @SuppressWarnings({"rawtypes", "unchecked"})
        public static final Codec<Combined> CODEC = ItemFilter.CODEC.listOf().xmap(filters1 -> (Combined) anyOf(filters1), combined -> combined.filters instanceof List filters ? filters : new ArrayList<>(combined.filters));
        private final Collection<Simple> filters;

        private Combined(Collection<Simple> filters) {
            this.filters = filters;
        }

        @Override
        public boolean test(ItemStack stack) {
            for (var filter : filters) {
                if (filter.test(stack)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        @Nullable
        public Simple getMatchingFilter(ItemStack stack) {
            for (var filter : filters) {
                if (filter.test(stack)) {
                    return filter;
                }
            }
            return null;
        }

        @Override
        public ItemStack[] getMatchedStacks() {
            return filters.stream().flatMap(f -> Arrays.stream(f.getMatchedStacks())).toArray(ItemStack[]::new);
        }
    }
}
