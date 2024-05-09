package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TakeSpellRecipeFromLecternPacket(BlockPos pos) implements CustomPacketPayload {
    static final Type<TakeSpellRecipeFromLecternPacket> TYPE = new Type<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "take_spell_recipe_from_lectern"));
    static final StreamCodec<ByteBuf, TakeSpellRecipeFromLecternPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(TakeSpellRecipeFromLecternPacket::new, TakeSpellRecipeFromLecternPacket::pos);

    void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        SpellRecipeItem.takeFromLectern(player, level, pos, level.getBlockState(pos));
    }

    @Override
    public Type<TakeSpellRecipeFromLecternPacket> type() {
        return TYPE;
    }
}
