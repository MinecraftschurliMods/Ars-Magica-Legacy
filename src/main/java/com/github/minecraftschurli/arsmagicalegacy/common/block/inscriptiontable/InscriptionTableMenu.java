package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.network.InscriptionTableSyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

public class InscriptionTableMenu extends AbstractContainerMenu {
    private final InscriptionTableBlockEntity table;

    public InscriptionTableMenu(int pContainerId, Inventory inventory, InscriptionTableBlockEntity table) {
        super(AMMenuTypes.INSCRIPTION_TABLE.get(), pContainerId);
        table.startOpen(inventory.player);
        this.table = table;
        addSlot(new Slot(table, 0, 102, 74));
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
        return 5;
    }

    public void sendDataToServer(String name, List<ResourceLocation> spellStack, List<List<ResourceLocation>> shapeGroups) {
        Function<ResourceLocation, ISpellPart> registryAccess = ArsMagicaAPI.get().getSpellPartRegistry()::getValue;
        Spell spell = Spell.of(SpellStack.of(spellStack.stream().map(registryAccess).toList()), shapeGroups.stream().map(resourceLocations -> ShapeGroup.of(resourceLocations.stream().map(registryAccess).toList())).toArray(ShapeGroup[]::new));
        ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new InscriptionTableSyncPacket(table.getBlockPos(), name, spell));
    }
}
