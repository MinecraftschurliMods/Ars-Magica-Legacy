/**
 * Credit to amadornes for the original of this class {@link https://github.com/TechnicalitiesMC/TKLib/blob/1.18.X/src/main/java/com/technicalitiesmc/lib/inventory/ItemHandlerExtractionQuery.java}
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
    public static PrimitiveIterator.OfInt defaultVisitOrder(int size) {
        return IntStream.range(0, size).iterator();
    }

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
                ItemStack copy = stackInSlot.copy();
                if (stackInSlot.getCount() >= amount) {
                    copy.setCount(amount);
                }
                return copy;
            } else {
                ItemStack decrStack = inventory.removeItem(slot, Math.min(stackInSlot.getCount(), amount));
                inventory.setChanged();
                return decrStack;
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

    public boolean canExtract(ItemFilter filter) {
        return canExtract(filter, defaultVisitOrder(size));
    }

    public boolean canExtract(ItemFilter filter, PrimitiveIterator.OfInt visitOrder) {
        var stack = ItemStack.EMPTY;
        var extractedAmount = 0;
        var minExtracted = 0;
        var maxExtracted = 0;
        while (visitOrder.hasNext()) {
            var i = visitOrder.nextInt();
            if (stack.isEmpty()) {
                var matchedFilter = getMatch(i, filter);
                if (matchedFilter == null) {
                    continue;
                }
                stack = items[i].copy();
                minExtracted = matchedFilter.getMinAmount();
                maxExtracted = matchedFilter.getMaxAmount();
            } else if (!ItemHandlerHelper.canItemStacksStack(stack, getStack(i))) {
                continue;
            }
            var s = getStack(i);
            var extracted = Math.min(s.getCount(), maxExtracted - extractedAmount);
            extractedAmount += extracted;
            if (extractedAmount == maxExtracted) {
                break;
            }
        }
        return extractedAmount >= minExtracted;
    }

    public Extraction extract(ItemFilter filter) {
        return extract(filter, defaultVisitOrder(size));
    }

    public Extraction extract(ItemFilter filter, PrimitiveIterator.OfInt visitOrder) {
        var stack = ItemStack.EMPTY;
        var extractedAmount = 0;
        var minExtracted = 0;
        var maxExtracted = 0;
        var extractedAmounts = new int[size];
        while (visitOrder.hasNext()) {
            var i = visitOrder.nextInt();
            if (stack.isEmpty()) {
                var matchedFilter = getMatch(i, filter);
                if (matchedFilter == null) {
                    continue;
                }
                stack = items[i].copy();
                minExtracted = matchedFilter.getMinAmount();
                maxExtracted = matchedFilter.getMaxAmount();
            } else if (!ItemHandlerHelper.canItemStacksStack(stack, getStack(i))) {
                continue;
            }
            var s = getStack(i);
            var extracted = Math.min(s.getCount(), maxExtracted - extractedAmount);
            extractedAmounts[i] = extracted;
            extractedAmount += extracted;
            if (extractedAmount == maxExtracted) {
                break;
            }
        }
        if (extractedAmount < minExtracted) {
            return new Extraction(ItemStack.EMPTY, null, 0);
        }
        stack.setCount(extractedAmount);
        return lastExtraction = new Extraction(stack, extractedAmounts, minExtracted);
    }

    public void commit() {
        if (committed) {
            throw new IllegalStateException("This query has already been committed.");
        }
        committed = true;

        for (var i = 0; i < size; i++) {
            var amt = extracted[i];
            if (amt != 0) {
                extractor.extractItem(i, amt, false);
            }
        }
    }

    private ItemStack getStack(int slot) {
        ItemStack currentItem = items[slot];
        if (currentItem == null) {
            items[slot] = currentItem = extractor.extractItem(slot, 64, true).copy();
        }
        return currentItem;
    }

    @Nullable
    private ItemFilter.Simple getMatch(int slot, ItemFilter filter) {
        var currentItem = getStack(slot);
        if (currentItem.isEmpty()) {
            return null;
        }
        return filter.getMatchingFilter(currentItem);
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
            if (lastExtraction != this) {
                throw new IllegalStateException("Only the most recent extraction can be committed.");
            }
            lastExtraction = null;

            if (extracted.isEmpty() || extractedAmounts == null) {
                throw new IllegalStateException("Attempted to commit a failed extraction.");
            }

            for (int i = 0; i < size; i++) {
                int amt = extractedAmounts[i];
                if (amt == 0) {
                    continue;
                }

                var currentItem = items[i];
                currentItem.shrink(amt);
                ItemHandlerExtractionQuery.this.extracted[i] += amt;
            }
        }

        public boolean commitAtMost(int maxExtracted) {
            if (lastExtraction != this) {
                throw new IllegalStateException("Only the most recent extraction can be committed.");
            }
            lastExtraction = null;

            if (extracted.isEmpty() || extractedAmounts == null) {
                throw new IllegalStateException("Attempted to commit a failed extraction.");
            }

            if (maxExtracted < minExtracted) {
                return false;
            }

            var left = maxExtracted;
            for (var i = 0; i < size && left > 0; i++) {
                var amt = extractedAmounts[i];
                if (amt == 0) {
                    continue;
                }

                var actualAmt = Math.min(left, amt);

                var currentItem = items[i];
                currentItem.shrink(actualAmt);
                ItemHandlerExtractionQuery.this.extracted[i] += actualAmt;
                left -= actualAmt;
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
