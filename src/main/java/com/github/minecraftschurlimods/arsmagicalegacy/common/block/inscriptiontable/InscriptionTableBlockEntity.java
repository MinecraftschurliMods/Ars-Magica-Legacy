package com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
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

import java.util.Objects;
import java.util.Optional;

public class InscriptionTableBlockEntity extends BlockEntity implements Container, MenuProvider {
    public static final String SPELL_RECIPE_KEY = ArsMagicaAPI.MOD_ID + ":spell_recipe";
    public static final String INVENTORY_KEY = ArsMagicaAPI.MOD_ID + ":inventory";
    public static final String SPELL_NAME_KEY = ArsMagicaAPI.MOD_ID + ":spell_name";
    private static final Component TITLE = new TranslatableComponent(TranslationConstants.INSCRIPTION_TABLE_TITLE);
    private ItemStack stack = ItemStack.EMPTY;
    private @Nullable ISpell spellRecipe;
    private @Nullable Component spellName;
    private boolean open;

    public InscriptionTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pWorldPosition, pBlockState);
    }

    /**
     * @param name   The spell name.
     * @param spell  The spell.
     * @return A written book with the spell written onto it.
     */
    public static ItemStack makeRecipe(Component name, ISpell spell) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        ItemStack stack = new ItemStack(AMItems.SPELL_RECIPE.get());
        helper.setSpell(stack, spell);
        helper.setSpellName(stack, name);
        return stack;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        stack = ItemStack.of(pTag.getCompound(INVENTORY_KEY));
        if (pTag.contains(SPELL_RECIPE_KEY)) {
            spellRecipe = ISpell.CODEC.decode(NbtOps.INSTANCE, pTag.get(SPELL_RECIPE_KEY)).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn).getFirst();
        }
        if (pTag.contains(SPELL_NAME_KEY)) {
            spellName = Component.Serializer.fromJson(pTag.getString(SPELL_NAME_KEY));
        }
    }

    /**
     * Synchronizes the block entity.
     *
     * @param name  The spell name.
     * @param spell The spell.
     */
    public void onSync(@Nullable Component name, @Nullable ISpell spell) {
        spellName = name;
        spellRecipe = spell;
        setChanged();
    }

    @Nullable
    public Component getSpellName() {
        return spellName;
    }

    @Nullable
    public ISpell getSpellRecipe() {
        return spellRecipe;
    }

    /**
     * @param stack  The written book item stack.
     * @return The given item stack with the spell written onto it, or just the given item stack if there is no spell laid out yet.
     */
    public Optional<ItemStack> saveRecipe(ItemStack stack) {
        return Optional.ofNullable(getSpellRecipe()).map(spell -> spell.isEmpty() ? stack : makeRecipe(Objects.requireNonNullElseGet(spellName, () -> new TranslatableComponent(TranslationConstants.SPELL_RECIPE_TITLE)), spell));
    }

    public void createSpell(ServerPlayer player) {
        ItemStack spell = new ItemStack(AMItems.SPELL.get());
        ArsMagicaAPI.get().getSpellHelper().setSpell(spell, Objects.requireNonNull(getSpellRecipe()));
        player.addItem(spell);
    }

    @Override
    protected void saveAdditional(CompoundTag pCompound) {
        super.saveAdditional(pCompound);
        pCompound.put(INVENTORY_KEY, stack.save(new CompoundTag()));
        if (spellName != null) {
            pCompound.putString(SPELL_NAME_KEY, Component.Serializer.toJson(spellName));
        }
        if (spellRecipe != null) {
            pCompound.put(SPELL_RECIPE_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spellRecipe).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
    }

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
        return TITLE;
    }

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
        return stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return pIndex == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        if (pIndex == 0) {
            ItemStack split = stack.split(pCount);
            if (stack.isEmpty()) {
                onRemove();
            }
            return split;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        if (pIndex == 0) {
            ItemStack itemstack = stack;
            stack = ItemStack.EMPTY;
            onRemove();
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
        stack = pStack;
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = getBlockPos();
        return player.level.getBlockEntity(pos) == this && player.distanceToSqr(Vec3.atCenterOf(pos)) <= 64D;
    }

    @Override
    public void clearContent() {
        stack = ItemStack.EMPTY;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        // TODO
    }

    private void onRemove() {
        // TODO
    }
}
