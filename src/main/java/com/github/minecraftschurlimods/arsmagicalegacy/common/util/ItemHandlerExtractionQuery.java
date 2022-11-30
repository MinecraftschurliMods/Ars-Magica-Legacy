/**
 * Credit to amadornes for the original of this class.
 * {@link https://github.com/TechnicalitiesMC/TKLib/blob/1.18.X/src/main/java/com/technicalitiesmc/lib/inventory/ItemHandlerExtractionQuery.java}
 */
package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

public class ItemHandlerExtractionQuery {
    private final InventoryExtractor extractor;
    private final int size;
    private final ItemStack[] items;
    private final int[] extracted;
    private Extraction lastExtraction;
    private boolean committed;

    public ItemHandlerExtractionQuery(Container inventory) {
        this((slot, amount, simulate) -> {
            if (amount == 0) return ItemStack.EMPTY;
            ItemStack stackInSlot = inventory.getItem(slot);
            if (stackInSlot.isEmpty()) return ItemStack.EMPTY;
            if (simulate) {
                ItemStack stack = stackInSlot.copy();
                if (stackInSlot.getCount() >= amount) {
                    stack.setCount(amount);
                }
                return stack;
            } else {
                ItemStack stack = inventory.removeItem(slot, Math.min(stackInSlot.getCount(), amount));
                inventory.setChanged();
                return stack;
            }
        }, inventory.getContainerSize());
    }

    public ItemHandlerExtractionQuery(IItemHandler inventory) {
        this(inventory::extractItem, inventory.getSlots());
    }

    public ItemHandlerExtractionQuery(InventoryExtractor extractor, int size) {
        this.extractor = extractor;
        this.size = size;
        this.items = new ItemStack[size];
        this.extracted = new int[size];
    }

    public static PrimitiveIterator.OfInt defaultVisitOrder(int size) {
        return IntStream.range(0, size).iterator();
    }

    public boolean canExtract(ItemFilter filter) {
        return canExtract(filter, defaultVisitOrder(size));
    }

    public boolean canExtract(ItemFilter filter, PrimitiveIterator.OfInt order) {
        ItemStack stack = ItemStack.EMPTY;
        int amount = 0, min = 0, max = 0;
        while (order.hasNext()) {
            int i = order.nextInt();
            if (stack.isEmpty()) {
                ItemFilter.Simple simple = getMatch(i, filter);
                if (simple == null) continue;
                stack = items[i].copy();
                min = simple.minAmount;
                max = simple.maxAmount;
            } else if (!ItemHandlerHelper.canItemStacksStack(stack, getStack(i))) continue;
            amount += Math.min(getStack(i).getCount(), max - amount);
            if (amount == max) break;
        }
        return amount >= min;
    }

    public Extraction extract(ItemFilter filter) {
        return extract(filter, defaultVisitOrder(size));
    }

    public Extraction extract(ItemFilter filter, PrimitiveIterator.OfInt order) {
        ItemStack stack = ItemStack.EMPTY;
        int amount = 0, min = 0, max = 0;
        int[] amounts = new int[size];
        while (order.hasNext()) {
            int i = order.nextInt();
            if (stack.isEmpty()) {
                ItemFilter.Simple simple = getMatch(i, filter);
                if (simple == null) continue;
                stack = items[i].copy();
                min = simple.minAmount;
                max = simple.maxAmount;
            } else if (!ItemHandlerHelper.canItemStacksStack(stack, getStack(i))) continue;
            int extracted = Math.min(getStack(i).getCount(), max - amount);
            amounts[i] = extracted;
            amount += extracted;
            if (amount == max) break;
        }
        if (amount < min) return new Extraction(ItemStack.EMPTY, null, 0);
        stack.setCount(amount);
        return lastExtraction = new Extraction(stack, amounts, min);
    }

    public void commit() {
        if (committed) throw new IllegalStateException("This query has already been committed.");
        committed = true;
        for (var i = 0; i < size; i++) {
            int amount = extracted[i];
            if (amount != 0) {
                extractor.extractItem(i, amount, false);
            }
        }
    }

    private ItemStack getStack(int slot) {
        ItemStack stack = items[slot];
        if (stack == null) {
            items[slot] = stack = extractor.extractItem(slot, 64, true).copy();
        }
        return stack;
    }

    @Nullable
    private ItemFilter.Simple getMatch(int slot, ItemFilter filter) {
        ItemStack stack = getStack(slot);
        return stack.isEmpty() ? null : filter.getMatchingFilter(stack);
    }

    @FunctionalInterface
    interface InventoryExtractor {
        ItemStack extractItem(int slot, int amount, boolean simulate);
    }

    public final class Extraction {
        private final ItemStack extracted;
        private final int[] extractedAmounts;
        private final int minExtracted;

        private Extraction(ItemStack extracted, int @Nullable [] extractedAmounts, int minExtracted) {
            this.extracted = extracted;
            this.extractedAmounts = extractedAmounts;
            this.minExtracted = minExtracted;
        }

        public ItemStack getExtracted() {
            return extracted;
        }

        public boolean isEmpty() {
            return extracted.isEmpty();
        }

        public void commit() {
            if (lastExtraction != this) throw new IllegalStateException("Only the most recent extraction can be committed.");
            lastExtraction = null;
            if (extracted.isEmpty() || extractedAmounts == null)
                throw new IllegalStateException("Attempted to commit a failed extraction.");
            for (int i = 0; i < size; i++) {
                int amount = extractedAmounts[i];
                if (amount == 0) continue;
                ItemStack stack = items[i];
                stack.shrink(amount);
                ItemHandlerExtractionQuery.this.extracted[i] += amount;
            }
        }

        public boolean commitAtMost(int maxExtracted) {
            if (lastExtraction != this)
                throw new IllegalStateException("Only the most recent extraction can be committed.");
            lastExtraction = null;
            if (extracted.isEmpty() || extractedAmounts == null)
                throw new IllegalStateException("Attempted to commit a failed extraction.");
            if (maxExtracted < minExtracted) return false;
            int left = maxExtracted;
            for (int i = 0; i < size && left > 0; i++) {
                int amount = extractedAmounts[i];
                if (amount == 0) continue;
                int actualAmount = Math.min(left, amount);
                ItemStack stack = items[i];
                stack.shrink(actualAmount);
                ItemHandlerExtractionQuery.this.extracted[i] += actualAmount;
                left -= actualAmount;
            }
            return true;
        }

        public boolean tryCommit() {
            try {
                commit();
                return true;
            } catch (IllegalStateException e) {
                return false;
            }
        }

        public boolean tryCommitAtMost(int maxExtracted) {
            try {
                return commitAtMost(maxExtracted);
            } catch (IllegalStateException e) {
                return false;
            }
        }
    }
}
