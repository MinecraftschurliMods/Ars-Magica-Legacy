package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.SimpleEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ObeliskBlockEntity extends BaseContainerBlockEntity {
    private final SimpleEtheriumProvider etheriumProvider = new SimpleEtheriumProvider(EtheriumType.NEUTRAL, Config.SERVER.MAX_ETHERIUM_STORAGE.get()).setCallback(ObeliskBlockEntity::onConsume);
    private final IItemHandler inventoryHolder = new InvWrapper(this);
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
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
    public boolean stillValid(Player player) {
        return getBlockPos().distSqr(player.blockPosition()) < 128D;
    }

    void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTimeRemaining > 0) {
            int tier = state.getBlock() instanceof ObeliskBlock block ? block.getTier(level, pos) : 0;
            etheriumProvider.add(Math.round(etheriumPerTick * tier * 0.5f + 0.5f));
            burnTimeRemaining--;
            setChanged();
        }
        if (burnTimeRemaining <= 0) {
            Optional<ObeliskFuel> fuel = ObeliskFuelManager.getFuelFor(level.registryAccess(), items.getFirst());
            fuel.ifPresent(obeliskFuel -> {
                int burnTime = obeliskFuel.burnTime();
                int perTick = obeliskFuel.etheriumPerTick();
                if (burnTime > 0 && perTick > 0 && etheriumProvider.canStore(perTick * burnTime)) {
                    if (items.getFirst().hasCraftingRemainingItem()) {
                        items.set(0, items.getFirst().getCraftingRemainingItem());
                    } else {
                        items.getFirst().shrink(1);
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
        return Component.translatable(TranslationConstants.OBELISK_TITLE);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> p_332640_) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ObeliskMenu(containerId, inventory, this, data);
    }

    public IEtheriumProvider getEtheriumCapability(Void $) {
        return etheriumProvider;
    }

    public IItemHandler getItemCapability(@Nullable Direction side) {
        return inventoryHolder;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        items.set(0, ItemStack.parseOptional(provider, tag.getCompound("Inv")));
        burnTimeRemaining = tag.getInt("burnTime");
        maxBurnTime = tag.getInt("burnTimeMax");
        etheriumProvider.set(tag.getInt("etheriumValue"));
        etheriumPerTick = tag.getInt("etheriumPerTick");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put("Inv", items.getFirst().save(provider));
        tag.putInt("burnTime", burnTimeRemaining);
        tag.putInt("burnTimeMax", maxBurnTime);
        tag.putInt("etheriumValue", etheriumProvider.getAmount());
        tag.putInt("etheriumPerTick", etheriumPerTick);
    }
}
