package at.minecraftschurli.mods.arsmagicalegacy.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record RiftAttachment(List<ItemStack> contents) {
    public static final Codec<RiftAttachment> CODEC = ItemStack.OPTIONAL_CODEC.listOf().xmap(RiftAttachment::new, RiftAttachment::contents);
    public static final StreamCodec<RegistryFriendlyByteBuf, RiftAttachment> STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).map(RiftAttachment::new, RiftAttachment::contents);
    public static final RiftAttachment DEFAULT = new RiftAttachment(List.of());
}
