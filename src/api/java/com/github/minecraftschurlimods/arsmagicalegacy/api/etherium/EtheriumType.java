package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enum for etherium type.
 */
public enum EtheriumType implements ITranslatable {
    LIGHT(0xff7fa7ef),
    NEUTRAL(0xff3fffbf),
    DARK(0xff800000);

    public static final Set<EtheriumType> ANY = EnumSet.allOf(EtheriumType.class);
    public static final Codec<EtheriumType> CODEC = CodecHelper.forEnum(EtheriumType.class);
    public static final StreamCodec<FriendlyByteBuf, EtheriumType> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(EtheriumType.class);

    private final int color;

    EtheriumType(int color) {
        this.color = color;
    }

    @Override
    public ResourceLocation getId() {
        return ArsMagicaAPI.resource(name().toLowerCase());
    }

    @Override
    public String getType() {
        return "etherium";
    }

    /**
     * @return This etherium type's color.
     */
    public int getColor() {
        return color;
    }
}
