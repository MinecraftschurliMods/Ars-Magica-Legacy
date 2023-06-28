package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;

public record LearnSkillPacket(ResourceLocation skill) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "learn_skill");

    public LearnSkillPacket(FriendlyByteBuf buf) {
        this(buf.readResourceLocation());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeResourceLocation(skill());
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            var api = ArsMagicaAPI.get();
            var skillHelper = api.getSkillHelper();
            ServerPlayer sender = ctx.getSender();
            assert sender != null;
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
