package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class SyncKnowledgePacket implements IPacket {

    private final KnowledgeHelper.KnowledgeHolder knowledgeHolder;

    public SyncKnowledgePacket(KnowledgeHelper.KnowledgeHolder knowledgeHolder) {
        this.knowledgeHolder = knowledgeHolder;
    }

    public SyncKnowledgePacket(FriendlyByteBuf buf) {
        this(buf.readWithCodec(KnowledgeHelper.KnowledgeHolder.CODEC));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeWithCodec(KnowledgeHelper.KnowledgeHolder.CODEC, knowledgeHolder);
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        Minecraft.getInstance().player.getCapability(KnowledgeHelper.getCapability())
                .filter(KnowledgeHelper.KnowledgeHolder.class::isInstance)
                .map(KnowledgeHelper.KnowledgeHolder.class::cast)
                .ifPresent(knowledgeHolder -> {
            knowledgeHolder.onSync(this.knowledgeHolder);
        });
    }
}
