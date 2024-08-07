package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;

public record LearnSkillPacket(ResourceLocation skill) implements CustomPacketPayload {
    static final Type<LearnSkillPacket> TYPE = new Type<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "learn_skill"));
    static final StreamCodec<ByteBuf, LearnSkillPacket> STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(LearnSkillPacket::new, LearnSkillPacket::skill);

    void handle(IPayloadContext ctx) {
        var api = ArsMagicaAPI.get();
        var skillHelper = api.getSkillHelper();
        Player player = ctx.player();
        if (!(player instanceof ServerPlayer sender)) return;
        if (!sender.isCreative()) {
            Skill skill = sender.level().registryAccess().registryOrThrow(Skill.REGISTRY_KEY).get(skill());
            assert skill != null;
            Map<ResourceLocation, Integer> cost = skill.cost();
            for (Map.Entry<ResourceLocation, Integer> entry : cost.entrySet()) {
                if (skillHelper.getSkillPoint(sender, entry.getKey()) < entry.getValue()) {
                    return;
                }
            }
            for (Map.Entry<ResourceLocation, Integer> entry : cost.entrySet()) {
                skillHelper.consumeSkillPoint(sender, entry.getKey(), entry.getValue());
            }
        }
        skillHelper.learn(sender, skill());
    }

    @Override
    public Type<LearnSkillPacket> type() {
        return TYPE;
    }
}
