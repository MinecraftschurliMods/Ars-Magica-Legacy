package com.github.minecraftschurlimods.arsmagicalegacy.common.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SummonMinionsAttachment(List<UUID> uuids) {
    public static final Codec<SummonMinionsAttachment> CODEC = UUIDUtil.CODEC.listOf().xmap(SummonMinionsAttachment::new, SummonMinionsAttachment::uuids);
    public static final SummonMinionsAttachment EMPTY = new SummonMinionsAttachment(List.of());

    public SummonMinionsAttachment add(UUID uuid) {
        List<UUID> list = new ArrayList<>(uuids);
        list.add(uuid);
        return new SummonMinionsAttachment(list);
    }

    public SummonMinionsAttachment remove(UUID uuid) {
        List<UUID> list = new ArrayList<>(uuids);
        list.remove(uuid);
        return new SummonMinionsAttachment(list);
    }

    public int size() {
        return uuids.size();
    }
}
