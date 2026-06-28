package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TakeSpellRecipeFromLecternPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<TakeSpellRecipeFromLecternPacket> TYPE = new Type<>(ArsMagicaApi.id("take_spell_recipe_from_lectern"));
    public static final StreamCodec<ByteBuf, TakeSpellRecipeFromLecternPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(TakeSpellRecipeFromLecternPacket::new, TakeSpellRecipeFromLecternPacket::pos);

    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        if (!level.isLoaded(pos)) return;
        AMUtil.takeLecternBook(player, level, pos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
