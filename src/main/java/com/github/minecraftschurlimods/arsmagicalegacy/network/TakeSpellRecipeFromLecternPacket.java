package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record TakeSpellRecipeFromLecternPacket(BlockPos pos) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "take_spell_recipe_from_lectern");

    TakeSpellRecipeFromLecternPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(pos);
    }

    void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            Player player = context.player().orElseThrow();
            Level level = player.level();
            SpellRecipeItem.takeFromLectern(player, level, pos, level.getBlockState(pos));
        });
    }
}
