package com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.MultiblockMatcher;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEtheriumTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ObeliskBlockEntity extends EtheriumGeneratorBlockEntity implements StackedContentsCompatible, WorldlyContainer {
    private static final MultiblockMatcher CHALK = new MultiblockMatcher(AMMultiblocks.OBELISK_CHALK);
    private static final MultiblockMatcher PILLARS = new MultiblockMatcher(AMMultiblocks.OBELISK_PILLARS);
    private static final String ITEMS_KEY = "Items";
    private static final String BURN_TIME_KEY = "burn_time";
    private static final String MAX_BURN_TIME_KEY = "max_burn_time";
    private static final String ETHERIUM_PER_TICK_KEY = "etherium_per_tick";
    private static final int[] SLOTS = new int[]{0};
    private ItemStack stack = ItemStack.EMPTY;
    private int burnTime;
    private int maxBurnTime;
    private int etheriumPerTick;

    public ObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.OBELISK.get(), pos, state, AMEtheriumTypes.NEUTRAL);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTime > 0) {
            etherium = Math.clamp(etherium + etheriumPerTick, 0, getMaxAmount());
            burnTime--;
            setChanged();
        }
        if (burnTime <= 0) {
            maxBurnTime = 0;
            ObeliskFuel fuel = ObeliskFuel.getFuel(stack);
            if (fuel != null) {
                burnTime = fuel.burnTime();
                maxBurnTime = fuel.burnTime();
                etheriumPerTick = fuel.etheriumPerTick() * (getTier(level, pos) + 1);
                ItemStackTemplate craftingRemainder = stack.getCraftingRemainder();
                if (craftingRemainder != null) {
                    stack = craftingRemainder.create();
                } else {
                    stack.shrink(1);
                }
                setChanged();
            }
        }
        boolean lit = burnTime > 0;
        if (state.getValue(ObeliskBlock.LIT) != lit) {
            level.setBlockAndUpdate(pos, state.setValue(ObeliskBlock.LIT, lit));
        }
    }

    @Override
    public int getMaxAmount() {
        return AMServerConfig.OBELISK_MAX_ETHERIUM.get();
    }

    @Override
    public int getTier(Level level, BlockPos pos) {
        return PILLARS.test(level, pos) ? 2 : CHALK.test(level, pos) ? 1 : 0;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        stack = input.read(ITEMS_KEY, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        burnTime = input.getIntOr(BURN_TIME_KEY, 0);
        maxBurnTime = input.getIntOr(MAX_BURN_TIME_KEY, 0);
        etheriumPerTick = input.getIntOr(ETHERIUM_PER_TICK_KEY, 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!stack.isEmpty()) {
            output.store(ITEMS_KEY, ItemStack.CODEC, stack);
        }
        output.putInt(BURN_TIME_KEY, burnTime);
        output.putInt(MAX_BURN_TIME_KEY, maxBurnTime);
        output.putInt(ETHERIUM_PER_TICK_KEY, etheriumPerTick);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        components.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(AMUtil.nonNullList(ItemStack.EMPTY, stack));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(NonNullList.copyOf(List.of(stack))));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(ValueOutput output) {
        output.discard(ITEMS_KEY);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot != 0) return ItemStack.EMPTY;
        ItemStack stack = getItem(slot).split(amount);
        setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot != 0) return ItemStack.EMPTY;
        ItemStack stack = this.stack;
        this.stack = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot != 0) return;
        this.stack = stack;
        this.stack.limitSize(getMaxStackSize(this.stack));
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        stack = ItemStack.EMPTY;
        setChanged();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return canTakeItem(this, index, stack);
    }

    @Override
    public void fillStackedContents(StackedItemContents contents) {
        contents.accountStack(stack);
        setChanged();
    }

    @Override
    @Nullable
    public AABB getOutline(Level level, BlockPos pos, BlockState state) {
        return state.getValue(ObeliskBlock.PART) == ObeliskBlock.Part.LOWER ? new AABB(Vec3.ZERO, new Vec3(1, 3, 1)) : null;
    }

    @Override
    public int getOutlineColor(Level level, BlockPos pos, BlockState state) {
        return AMRegistries.etheriumTypes(level.registryAccess()).getValueOrThrow(AMEtheriumTypes.NEUTRAL).color();
    }
}
