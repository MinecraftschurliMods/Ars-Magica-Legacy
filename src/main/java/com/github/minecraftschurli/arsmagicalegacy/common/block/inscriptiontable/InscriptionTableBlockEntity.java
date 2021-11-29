package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlockEntities;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class InscriptionTableBlockEntity extends BaseContainerBlockEntity {
    private static final Component DEFAULT_NAME = new TranslatableComponent(Util.makeDescriptionId("container", new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table")));

    private ItemStack stack = ItemStack.EMPTY;

    public InscriptionTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.stack = ItemStack.of(pTag.getCompound("Inv"));
    }

    @Override
    public CompoundTag save(CompoundTag pCompound) {
        pCompound.put("Inv", this.stack.save(new CompoundTag()));
        return super.save(pCompound);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return pIndex == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return pIndex == 0 ? this.stack.split(pCount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return pIndex == 0 ? this.stack.split(this.stack.getCount()) : ItemStack.EMPTY;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        if (pIndex == 0) this.stack = pStack;
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = getBlockPos();
        return player.level.getBlockEntity(pos) == this && player.distanceToSqr(Vec3.atCenterOf(pos)) <= 64D;
    }

    @Override
    public void clearContent() {
        this.stack = ItemStack.EMPTY;
    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new InscriptionTableMenu(pContainerId, pInventory, this);
    }
}
