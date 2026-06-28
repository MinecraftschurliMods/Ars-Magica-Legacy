package at.minecraftschurli.mods.arsmagicalegacy.api.etherium;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Util;

/// Represents an etherium type.
///
/// @param color The color of the etherium type.
public record EtheriumType(int color) {
    public static final Codec<EtheriumType> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("color").forGetter(EtheriumType::color)
    ).apply(inst, EtheriumType::new));
    public static final Codec<Holder<EtheriumType>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.ETHERIUM_TYPE, DIRECT_CODEC);

    /// @param holder The etherium type [Holder] to query.
    /// @return The display name of the given etherium type.
    @SuppressWarnings("DataFlowIssue")
    public static MutableComponent getName(Holder<EtheriumType> holder) {
        return Component.translatable(Util.makeDescriptionId("etherium", holder.getKey().identifier()));
    }
}
