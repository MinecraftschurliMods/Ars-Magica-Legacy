package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public record OpenSpellRecipeGuiInLecternPacket(ItemStack stack, BlockPos pos, int page) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "open_spell_recipe_gui_in_lectern");

    public OpenSpellRecipeGuiInLecternPacket(FriendlyByteBuf buf) {
        this(buf.readItem(), buf.readBlockPos(), buf.readInt());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeItem(stack);
        friendlyByteBuf.writeBlockPos(pos);
        friendlyByteBuf.writeInt(page);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> ClientHelper.openSpellRecipeGui(stack, false, page, pos));
    }
}
