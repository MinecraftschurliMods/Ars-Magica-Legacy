package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.Skill;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;

public record SyncSkillsPacket(Collection<ISkill> values) implements IPacket {

    @SuppressWarnings("unused")
    public SyncSkillsPacket(FriendlyByteBuf buf) {
        this(deserializeValues(buf));
    }

    private static Collection<ISkill> deserializeValues(FriendlyByteBuf buf) {
        var len = buf.readInt();
        var collection = new ArrayList<ISkill>(len);
        for (int i = 0; i < len; i++) {
            collection.add(buf.readWithCodec(Skill.NETWORK_CODEC));
        }
        return collection;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(this.values.size());
        this.values.forEach(iSkill -> buf.writeWithCodec(Skill.NETWORK_CODEC, iSkill));
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        SkillManager.instance().receiveSync(this.values);
    }
}
