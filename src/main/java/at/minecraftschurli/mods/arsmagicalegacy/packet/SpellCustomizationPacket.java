package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMExtraCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpellCustomizationPacket(Spell spell, InteractionHand hand) implements CustomPacketPayload {
    public static final Type<SpellCustomizationPacket> TYPE = new Type<>(ArsMagicaApi.id("spell_customization"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellCustomizationPacket> STREAM_CODEC = StreamCodec.composite(
        Spell.STREAM_CODEC, SpellCustomizationPacket::spell,
        AMExtraCodecs.INTERACTION_HAND_STREAM_CODEC, SpellCustomizationPacket::hand,
        SpellCustomizationPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        ItemStack stack = context.player().getItemInHand(hand);
        if (stack.has(AMDataComponents.SPELL)) {
            stack.set(AMDataComponents.SPELL, spell);
        }
    }
}
