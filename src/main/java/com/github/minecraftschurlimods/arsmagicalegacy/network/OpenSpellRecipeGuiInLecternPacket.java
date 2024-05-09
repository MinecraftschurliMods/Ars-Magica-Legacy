package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenSpellRecipeGuiInLecternPacket(ItemStack stack, BlockPos pos, int page) implements CustomPacketPayload {
    static final Type<OpenSpellRecipeGuiInLecternPacket> TYPE = new Type<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "open_spell_recipe_gui_in_lectern"));
    static final StreamCodec<RegistryFriendlyByteBuf, OpenSpellRecipeGuiInLecternPacket> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            OpenSpellRecipeGuiInLecternPacket::stack,
            BlockPos.STREAM_CODEC,
            OpenSpellRecipeGuiInLecternPacket::pos,
            ByteBufCodecs.VAR_INT,
            OpenSpellRecipeGuiInLecternPacket::page,
            OpenSpellRecipeGuiInLecternPacket::new
    );

    void handle(IPayloadContext context) {
        ClientHelper.openSpellRecipeGui(stack, false, page, pos);
    }

    @Override
    public Type<OpenSpellRecipeGuiInLecternPacket> type() {
        return TYPE;
    }
}
