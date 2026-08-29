package at.minecraftschurli.mods.arsmagicalegacy.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record LifeWardAttachment(float health, int timeUntilHeal) {
    public static final Codec<LifeWardAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ExtraCodecs.NON_NEGATIVE_FLOAT.fieldOf("health").forGetter(LifeWardAttachment::health),
        Codec.INT.fieldOf("time_until_heal").forGetter(LifeWardAttachment::timeUntilHeal)
    ).apply(inst, LifeWardAttachment::new));
    public static final StreamCodec<ByteBuf, LifeWardAttachment> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.FLOAT, LifeWardAttachment::health,
        ByteBufCodecs.INT, LifeWardAttachment::timeUntilHeal,
        LifeWardAttachment::new);
    public static final LifeWardAttachment EMPTY = new LifeWardAttachment(0, Integer.MAX_VALUE);

    public boolean isEmpty() {
        return timeUntilHeal == Integer.MAX_VALUE;
    }
}
