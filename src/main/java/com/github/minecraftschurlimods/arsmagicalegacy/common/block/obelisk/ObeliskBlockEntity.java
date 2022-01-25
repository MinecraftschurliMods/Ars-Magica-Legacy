package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.SimpleEtheriumProvider;
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
import javax.annotation.Nonnull;

public class ObeliskBlockEntity extends BaseContainerBlockEntity {
    private final SimpleEtheriumProvider etheriumProvider = new SimpleEtheriumProvider(EtheriumType.NEUTRAL, Config.SERVER.MAX_ETHERIUM_STORAGE.get()).setCallback(ObeliskBlockEntity::onConsume);
    private final LazyOptional<IEtheriumProvider> etheriumProviderHolder = LazyOptional.of(() -> etheriumProvider);
    private final LazyOptional<IItemHandler> inventoryHolder = LazyOptional.of(() -> new InvWrapper(this));
    private ItemStack slot = ItemStack.EMPTY;
    private int maxBurnTime = 0;
    private int burnTimeRemaining = 0;
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTimeRemaining;
                case 1 -> maxBurnTime;
                case 2 -> etheriumProvider.getAmount();
                case 3 -> etheriumProvider.getMax();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTimeRemaining = value;
                case 1 -> maxBurnTime = value;
                case 2 -> etheriumProvider.set(value);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private int etheriumPerTick = 0;

    public ObeliskBlockEntity(BlockPos p_155077_, BlockState p_155078_) {
        super(AMBlockEntities.OBELISK.get(), p_155077_, p_155078_);
    }

    private static void onConsume(Level level, BlockPos consumerPos, int amount) {
        // TODO spawn particles
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
        if (index != 0) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        return slot;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        if (index != 0) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        return slot.split(count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if (index != 0) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        ItemStack slot = this.slot;
        this.slot = ItemStack.EMPTY;
        return slot;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index != 0) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        slot = stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return getBlockPos().distSqr(player.blockPosition()) < 128D;
    }

    @Override
    public void clearContent() {
        slot = ItemStack.EMPTY;
    }

    void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTimeRemaining > 0) {
            int c = state.getBlock() instanceof ObeliskBlock block ? block.getTier(state, level, pos) : 0;
            float mul = c * 0.5f + 0.5f;
            etheriumProvider.add(Math.round(etheriumPerTick * mul));
            burnTimeRemaining--;
            setChanged();
        }
        if (burnTimeRemaining <= 0) {
            Optional<ObeliskFuelManager.ObeliskFuel> fuel = ObeliskFuelManager.instance().getFuelFor(slot);
            fuel.ifPresent(obeliskFuel -> {
                int burnTime = obeliskFuel.burnTime();
                int perTick = obeliskFuel.etheriumPerTick();
                if (burnTime > 0 && perTick > 0 && etheriumProvider.canStore(perTick * burnTime)) {
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
        return new TranslatableComponent(TranslationConstants.OBELISK_TITLE);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ObeliskMenu(containerId, inventory, this, data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventoryHolder.cast();
        }
        if (cap == EtheriumHelper.instance().getEtheriumProviderCapability()) {
            return etheriumProviderHolder.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inv", slot.save(new CompoundTag()));
        tag.putInt("burnTime", burnTimeRemaining);
        tag.putInt("burnTimeMax", maxBurnTime);
        tag.putInt("etheriumValue", etheriumProvider.getAmount());
        tag.putInt("etheriumPerTick", etheriumPerTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        slot = ItemStack.of(tag.getCompound("Inv"));
        burnTimeRemaining = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("burnTimeMax");
        etheriumProvider.set(tag.getInt("etheriumValue"));
        etheriumPerTick = tag.getInt("etheriumPerTick");
    }
}
