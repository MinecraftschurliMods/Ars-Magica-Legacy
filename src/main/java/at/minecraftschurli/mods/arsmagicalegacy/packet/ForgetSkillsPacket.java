package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public record ForgetSkillsPacket() implements CustomPacketPayload {
    public static final Type<ForgetSkillsPacket> TYPE = new Type<>(ArsMagicaApi.id("forget_skills"));
    public static final StreamCodec<ByteBuf, ForgetSkillsPacket> STREAM_CODEC = StreamCodec.unit(new ForgetSkillsPacket());

    public void handle(IPayloadContext context) {
        Player player = context.player();
        Inventory inventory = player.getInventory();
        if (inventory.contains(AMTags.Items.OCCULUS_FORGET_ALL)) {
            for (ItemStack stack : inventory) {
                if (stack.isEmpty() || !stack.is(AMTags.Items.OCCULUS_FORGET_ALL)) continue;
                forgetAll(player);
                stack.shrink(1);
                return;
            }
        } else if (player.isCreative()) {
            forgetAll(player);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private static void forgetAll(Player player) {
        MagicHelper helper = ArsMagicaApi.magicHelper();
        helper.getKnown(player)
            .stream()
            .map(Holder::value)
            .map(Skill::cost)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(cost -> helper.addSkillPoint(player, cost));
        helper.forgetAll(player);
    }
}
