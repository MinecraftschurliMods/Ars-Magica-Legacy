package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class InscriptionTableBlockEntity extends BlockEntity implements Container, MenuProvider {
    private static final Component DEFAULT_NAME = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_CONTAINER_TITLE);

    private           ItemStack stack = ItemStack.EMPTY;
    private @Nullable Spell     spellRecipe;
    private @Nullable String    spellName;
    private           boolean   open;

    public InscriptionTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pWorldPosition, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.stack = ItemStack.of(pTag.getCompound("Inv"));
    }

    public void onSync(String name, Spell spell) {
        this.spellName = name;
        this.spellRecipe = spell;
    }

    @Nullable
    public String getSpellName() {
        return this.spellName;
    }

    @Nullable
    public Spell getSpellRecipe() {
        return this.spellRecipe;
    }

    @Override
    protected void saveAdditional(CompoundTag pCompound) {
        super.saveAdditional(pCompound);
        pCompound.put("Inv", this.stack.save(new CompoundTag()));
        if (this.spellName != null) {
            pCompound.putString("spell_name", this.spellName);
        }
        if (this.spellRecipe != null) {
            pCompound.put("spell_recipe", Spell.CODEC.encodeStart(NbtOps.INSTANCE, this.spellRecipe)
                                                     .getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        if (isOpen()) return null;
        return new InscriptionTableMenu(pContainerId, pInventory, this);
    }

    @Override
    public void startOpen(Player player) {
        open = true;
    }

    @Override
    public void stopOpen(Player player) {
        open = false;
    }

    private boolean isOpen() {
        return open;
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
        if (pIndex == 0) {
            ItemStack split = this.stack.split(pCount);
            if (this.stack.isEmpty()) {
                onRemove();
            }
            return split;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        if (pIndex == 0) {
            ItemStack itemstack = this.stack;
            this.stack = ItemStack.EMPTY;
            this.onRemove();
            return itemstack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
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
    public void setChanged() {
        // stub
    }

    private void onRemove() {
        // stub
    }
}
