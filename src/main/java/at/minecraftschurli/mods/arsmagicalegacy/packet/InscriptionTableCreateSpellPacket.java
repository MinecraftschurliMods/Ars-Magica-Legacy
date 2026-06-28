package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.blockentity.InscriptionTableBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InscriptionTableCreateSpellPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<InscriptionTableCreateSpellPacket> TYPE = new Type<>(ArsMagicaApi.id("inscription_table_create_spell"));
    public static final StreamCodec<ByteBuf, InscriptionTableCreateSpellPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(InscriptionTableCreateSpellPacket::new, InscriptionTableCreateSpellPacket::pos);

    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        if (player.isCreative() && level.isLoaded(pos) && level.getBlockEntity(pos) instanceof InscriptionTableBlockEntity blockEntity) {
            Spell spell = blockEntity.getMenuData().toSpell();
            if (!spell.isEmpty()) {
                player.getInventory().add(blockEntity.setSpell(AMItems.SPELL.toStack()));
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
