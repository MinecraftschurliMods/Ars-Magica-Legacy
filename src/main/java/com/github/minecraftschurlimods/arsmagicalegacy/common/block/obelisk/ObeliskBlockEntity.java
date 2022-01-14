package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class ObeliskBlockEntity extends BaseContainerBlockEntity {
    private final IEtheriumProvider etheriumProvider = new IEtheriumProvider() {
        @Override
        public boolean provides(Set<EtheriumType> types) {
            return types.contains(EtheriumType.NEUTRAL);
        }

        @Override
        public int consume(Level level, BlockPos consumerPos, int amount) {
            int min = Math.min(etheriumValue, amount);
            etheriumValue -= min;
            // TODO spawn particles
            return min;
        }
    };
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTimeRemaining;
                case 1 -> maxBurnTime;
                case 2 -> etheriumValue;
                case 3 -> maxEtherium;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTimeRemaining = value;
                case 1 -> maxBurnTime = value;
                case 2 -> etheriumValue = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private final LazyOptional<IEtheriumProvider> etheriumProviderHolder = LazyOptional.of(() -> etheriumProvider);
    private final LazyOptional<IItemHandler>      inventoryHolder        = LazyOptional.of(() -> new InvWrapper(this));

    private final int maxEtherium = Config.SERVER.MAX_ETHERIUM_STORAGE.get();

    private ItemStack slot = ItemStack.EMPTY;
    private int maxBurnTime = 0;
    private int burnTimeRemaining = 0;
    private int etheriumPerTick = 0;
    private int etheriumValue = 0;

    public ObeliskBlockEntity(BlockPos p_155077_, BlockState p_155078_) {
        super(AMBlockEntities.OBELISK.get(), p_155077_, p_155078_);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return slot.isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        if (index != 0) throw new IndexOutOfBoundsException("Index "+ index + " is out of bounds!");
        return slot;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        if (index != 0) throw new IndexOutOfBoundsException("Index "+ index + " is out of bounds!");
        return slot.split(count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if (index != 0) throw new IndexOutOfBoundsException("Index "+ index + " is out of bounds!");
        ItemStack slot = this.slot;
        this.slot = ItemStack.EMPTY;
        return slot;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index != 0) throw new IndexOutOfBoundsException("Index "+ index + " is out of bounds!");
        this.slot = stack;
    }

    @Override
    public boolean stillValid(final Player player) {
        return getBlockPos().distSqr(player.blockPosition()) < 128D;
    }

    @Override
    public void clearContent() {
        this.slot = ItemStack.EMPTY;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ObeliskBlockEntity blockEntity) {
        blockEntity.tick(level, pos, state);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTimeRemaining > 0) {
            etheriumValue += etheriumPerTick;
            burnTimeRemaining--;
            setChanged();
        }

        if (burnTimeRemaining <= 0) {
            Optional<ObeliskFuelManager.ObeliskFuel> fuel = ObeliskFuelManager.instance().getFuelFor(slot);
            fuel.ifPresent(obeliskFuel -> {
                int burnTime = obeliskFuel.burnTime();
                int perTick = obeliskFuel.etheriumPerTick();
                if (burnTime > 0 && perTick > 0 && etheriumValue + perTick*burnTime <= maxEtherium) {
                    if (slot.hasContainerItem()) {
                        slot = slot.getContainerItem();
                    } else {
                        slot.shrink(1);
                    }
                    burnTimeRemaining = burnTime;
                    maxBurnTime = burnTime;
                    etheriumPerTick = perTick;
                    setChanged();
                }
            });
        }

        if (state.getValue(BlockStateProperties.LIT) != burnTimeRemaining > 0) {
            level.setBlock(pos, state.setValue(BlockStateProperties.LIT, burnTimeRemaining > 0), Block.UPDATE_ALL);
        }
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent(TranslationConstants.OBELISK_DEFAULT_NAME);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ObeliskMenu(containerId, inventory, this, this.data);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.inventoryHolder.cast();
        }
        if (cap == EtheriumHelper.instance().getEtheriumProviderCapability()) {
            return this.etheriumProviderHolder.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inv", slot.save(new CompoundTag()));
        tag.putInt("burnTime", this.burnTimeRemaining);
        tag.putInt("burnTimeMax", this.maxBurnTime);
        tag.putInt("etheriumValue", this.etheriumValue);
        tag.putInt("etheriumPerTick", this.etheriumPerTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        slot = ItemStack.of(tag.getCompound("Inv"));
        burnTimeRemaining = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("burnTimeMax");
        etheriumValue = tag.getInt("etheriumValue");
        etheriumPerTick = tag.getInt("etheriumPerTick");
    }
}
