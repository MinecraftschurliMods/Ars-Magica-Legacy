package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class SyncAffinityPacket implements IPacket {

    private final AffinityHelper.AffinityHolder affinityHolder;

    public SyncAffinityPacket(AffinityHelper.AffinityHolder affinityHolder) {
        this.affinityHolder = affinityHolder;
    }

    public SyncAffinityPacket(FriendlyByteBuf buf) {
        this(buf.readWithCodec(AffinityHelper.AffinityHolder.CODEC));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeWithCodec(AffinityHelper.AffinityHolder.CODEC, affinityHolder);
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        Minecraft.getInstance().player.getCapability(KnowledgeHelper.getCapability())
                .filter(AffinityHelper.AffinityHolder.class::isInstance)
                .map(AffinityHelper.AffinityHolder.class::cast)
                .ifPresent(knowledgeHolder -> {
            knowledgeHolder.onSync(this.affinityHolder);
        });
    }
}
