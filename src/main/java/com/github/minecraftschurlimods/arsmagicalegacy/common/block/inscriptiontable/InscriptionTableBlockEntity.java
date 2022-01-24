package com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
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
    private @Nullable Spell spellRecipe;
    private @Nullable String spellName;
    private boolean open;

    public InscriptionTableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pWorldPosition, pBlockState);
    }

    public static ItemStack makeRecipe(String name, String author, Spell spell) {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        SpellItem.saveSpell(book, spell);
        CompoundTag tag = book.getOrCreateTag();
        tag.putString(WrittenBookItem.TAG_TITLE, name);
        tag.putString(WrittenBookItem.TAG_AUTHOR, author);
        ListTag pages = new ListTag();
        makeSpellRecipePages(pages, spell);
        tag.put(WrittenBookItem.TAG_PAGES, pages);
        return book;
    }

    private static void makeSpellRecipePages(ListTag pages, Spell spell) {
        // TODO
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.stack = ItemStack.of(pTag.getCompound(INVENTORY_KEY));
        if (pTag.contains(SPELL_RECIPE_KEY)) {
            this.spellRecipe = Spell.CODEC.decode(NbtOps.INSTANCE, pTag.get(SPELL_RECIPE_KEY)).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn).getFirst();
        }
        if (pTag.contains(SPELL_NAME_KEY)) {
            this.spellName = pTag.getString(SPELL_NAME_KEY);
        }
    }

    public void onSync(@Nullable String name, @Nullable Spell spell) {
        this.spellName = name;
        this.spellRecipe = spell;
        this.setChanged();
    }

    @Nullable
    public String getSpellName() {
        return this.spellName;
    }

    @Nullable
    public Spell getSpellRecipe() {
        return this.spellRecipe;
    }

    public Optional<ItemStack> saveRecipe(Player player, ItemStack stack) {
        return Optional.ofNullable(getSpellRecipe())
                .map(spell -> {
                    if (spell.isEmpty()) return stack;
                    return makeRecipe(Objects.requireNonNullElseGet(spellName, () -> new TranslatableComponent(TranslationConstants.SPELL_RECIPE_TITLE).getString()), player.getDisplayName().getString(), spell);
                });
    }

    @Override
    protected void saveAdditional(CompoundTag pCompound) {
        super.saveAdditional(pCompound);
        pCompound.put(INVENTORY_KEY, this.stack.save(new CompoundTag()));
        if (this.spellName != null) {
            pCompound.putString(SPELL_NAME_KEY, this.spellName);
        }
        if (this.spellRecipe != null) {
            pCompound.put(SPELL_RECIPE_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, this.spellRecipe).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
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
        this.stack = pStack;
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
        super.setChanged();
        // TODO
    }

    private void onRemove() {
        // TODO
    }
}
