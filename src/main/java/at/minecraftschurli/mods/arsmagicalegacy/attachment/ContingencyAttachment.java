package at.minecraftschurli.mods.arsmagicalegacy.attachment;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record ContingencyAttachment(Identifier contingency, Spell spell) {
    public static final Codec<ContingencyAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Identifier.CODEC.fieldOf("contingency").forGetter(ContingencyAttachment::contingency),
        Spell.CODEC.fieldOf("spell").forGetter(ContingencyAttachment::spell)
    ).apply(inst, ContingencyAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ContingencyAttachment> STREAM_CODEC = StreamCodec.composite(
        Identifier.STREAM_CODEC, ContingencyAttachment::contingency,
        Spell.STREAM_CODEC, ContingencyAttachment::spell,
        ContingencyAttachment::new);
    public static final ContingencyAttachment DEFAULT = new ContingencyAttachment(AMUtil.MISSINGNO, Spell.EMPTY);
}
