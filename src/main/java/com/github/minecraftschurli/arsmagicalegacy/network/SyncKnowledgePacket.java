package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IKnowledgeHolder;
import com.github.minecraftschurli.arsmagicalegacy.api.util.ISyncable;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class SyncKnowledgePacket implements IPacket {

    private final IKnowledgeHolder knowledgeHolder;

    public SyncKnowledgePacket(IKnowledgeHolder knowledgeHolder) {
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
