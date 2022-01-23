package com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.InscriptionTableScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.network.InscriptionTableSyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class InscriptionTableMenu extends AbstractContainerMenu {
    private final InscriptionTableBlockEntity table;

    public InscriptionTableMenu(int pContainerId, Inventory inventory, InscriptionTableBlockEntity table) {
        super(AMMenuTypes.INSCRIPTION_TABLE.get(), pContainerId);
        table.startOpen(inventory.player);
        this.table = table;
        addSlot(new InscriptionTableSlot(table));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 30 + j * 18, 170 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 30 + i * 18, 228));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack slotstack = slot.getItem();
            stack = slotstack.copy();
            if (pIndex > 0) {
                if (slots.get(0).mayPlace(slotstack)) {
                    if (!moveItemStackTo(slotstack, 0, 1, false)) return ItemStack.EMPTY;
                } else if (!moveItemStackTo(slotstack, 1, slots.size(), pIndex < 10)) return ItemStack.EMPTY;
            } else if (!moveItemStackTo(slotstack, 1, slots.size(), true))
                return ItemStack.EMPTY;
            if (slotstack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        table.stopOpen(player);
    }

    public InscriptionTableMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, ((InscriptionTableBlockEntity) Objects.requireNonNull(inv.player.level.getBlockEntity(data.readBlockPos()))));
    }

    @Override
    public boolean stillValid(Player player) {
        return this.table.stillValid(player);
    }

    public Optional<String> getSpellName() {
        return Optional.ofNullable(this.table).map(InscriptionTableBlockEntity::getSpellName);
    }

    public int allowedShapeGroups() {
        return Config.SERVER.MAX_SHAPE_GROUPS.get();
    }

    public void sendDataToServer(String name, List<ResourceLocation> spellStack, List<List<ResourceLocation>> shapeGroups) {
        Function<ResourceLocation, ISpellPart> registryAccess = ArsMagicaAPI.get().getSpellPartRegistry()::getValue;
        Spell spell = Spell.of(SpellStack.of(spellStack.stream().map(registryAccess).toList()), shapeGroups.stream().map(resourceLocations -> ShapeGroup.of(resourceLocations.stream().map(registryAccess).toList())).toArray(ShapeGroup[]::new));
        table.onSync(name, spell);
        ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new InscriptionTableSyncPacket(table.getBlockPos(), name, spell));
    }

    public Optional<Spell> getSpellRecipe() {
        return Optional.ofNullable(this.table).map(InscriptionTableBlockEntity::getSpellRecipe);
    }

    private static class InscriptionTableSlot extends Slot {
        private final InscriptionTableBlockEntity table;

        public InscriptionTableSlot(InscriptionTableBlockEntity table) {
            super(table, 0, 102, 74);
            this.table = table;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return /*stack.getItem() instanceof ISpellItem || */stack.getItem() == Items.WRITABLE_BOOK;
        }

        @Override
        public Optional<ItemStack> tryRemove(int p_150642_, int p_150643_, Player player) {
            player.getLevel().playSound(null, table.getBlockPos().getX(), table.getBlockPos().getY(), table.getBlockPos().getZ(), AMSounds.INSCRIPTION_TABLE_TAKE_BOOK.get(), SoundSource.BLOCKS, 1f, 1f);
            return super.tryRemove(p_150642_, p_150643_, player).flatMap(stack -> stack.getItem() instanceof ISpellItem ? Optional.of(stack) : table.saveRecipe(player, stack));
        }

        @Override
        public void setChanged() {
            super.setChanged();
            if (table.getLevel().isClientSide()) {
                DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> InscriptionTableScreen::onSlotChanged);
            }
        }

        @Override
        public void set(ItemStack stack) {
            super.set(stack);
            if (stack.getItem() instanceof ISpellItem) {
                Spell spell = SpellItem.getSpell(stack);
                this.table.onSync(SpellItem.getSpellName(stack).orElse(null), spell.isEmpty() ? null : spell);
            }
        }
    }
}
