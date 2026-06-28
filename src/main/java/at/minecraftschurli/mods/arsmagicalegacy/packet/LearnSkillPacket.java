package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record LearnSkillPacket(Holder<Skill> skill) implements CustomPacketPayload {
    public static final Type<LearnSkillPacket> TYPE = new Type<>(ArsMagicaApi.id("learn_skill"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LearnSkillPacket> STREAM_CODEC = ByteBufCodecs.holderRegistry(AMRegistries.Keys.SKILL).map(LearnSkillPacket::new, LearnSkillPacket::skill);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        MagicHelper helper = ArsMagicaApi.magicHelper();
        Player player = context.player();
        boolean creative = player.isCreative();
        if (helper.canLearn(player, skill) || creative) {
            helper.learn(player, skill);
            if (!creative) {
                skill.value().cost().ifPresent(cost -> helper.addSkillPoint(player, cost, -1));
            }
        }
    }
}
