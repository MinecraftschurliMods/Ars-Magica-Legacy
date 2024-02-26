package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Map;

public record LearnSkillPacket(ResourceLocation skill) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "learn_skill");

    LearnSkillPacket(FriendlyByteBuf buf) {
        this(buf.readResourceLocation());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(skill());
    }

    void handle(PlayPayloadContext ctx) {
        ctx.workHandler().execute(() -> {
            var api = ArsMagicaAPI.get();
            var skillHelper = api.getSkillHelper();
            Player player = ctx.player().orElse(null);
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
        });
    }
}
