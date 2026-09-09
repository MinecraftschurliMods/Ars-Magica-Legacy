package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.ContingencyAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.DryadKillsAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.LifeWardAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.MagicAttachmentSyncHandler;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.RiftAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.SummonMinionsAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.TemporalAnchorAttachment;
import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Util;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public interface AMAttachments {
    DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ArsMagicaApi.MOD_ID);
    DeferredHolder<AttachmentType<?>, AttachmentType<MagicAttachment>> MAGIC = ATTACHMENTS.register("magic", () -> AttachmentType.builder(() -> MagicAttachment.DEFAULT).serialize(MagicAttachment.CODEC.fieldOf("magic")).sync(new MagicAttachmentSyncHandler()).copyOnDeath().build());
    // @formatter:off
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>>                   BURNOUT          = register("burnout",          () -> 0.,                            Codec.DOUBLE,                ByteBufCodecs.DOUBLE);
    DeferredHolder<AttachmentType<?>, AttachmentType<Integer>>                  COMPENDIUM_TIMER = register("compendium_timer", () -> 0,                             Codec.INT,                   ByteBufCodecs.INT);
    DeferredHolder<AttachmentType<?>, AttachmentType<ContingencyAttachment>>    CONTINGENCY      = register("contingency",      () -> ContingencyAttachment.DEFAULT, ContingencyAttachment.CODEC, ContingencyAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<Integer>>                  FROST            = register("frost",            () -> 0,                             Codec.INT,                   ByteBufCodecs.INT);
    DeferredHolder<AttachmentType<?>, AttachmentType<LifeWardAttachment>>       LIFE_WARD        = register("life_ward",        () -> LifeWardAttachment.EMPTY,      LifeWardAttachment.CODEC,    LifeWardAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<Double>>                   MANA             = register("mana",             () -> 0.,                            Codec.DOUBLE,                ByteBufCodecs.DOUBLE);
    DeferredHolder<AttachmentType<?>, AttachmentType<RiftAttachment>>           RIFT             = register("rift",             () -> RiftAttachment.DEFAULT,        RiftAttachment.CODEC,        RiftAttachment.STREAM_CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<DryadKillsAttachment>>     DRYAD_KILLS      = register("dryad_kills",      () -> DryadKillsAttachment.EMPTY,    DryadKillsAttachment.CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<SummonMinionsAttachment>>  SUMMON_MINIONS   = register("summon_minions",   () -> SummonMinionsAttachment.EMPTY, SummonMinionsAttachment.CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<UUID>>                     SUMMON_OWNER     = register("summon_owner",     () -> Util.NIL_UUID,                 UUIDUtil.CODEC);
    DeferredHolder<AttachmentType<?>, AttachmentType<TemporalAnchorAttachment>> TEMPORAL_ANCHOR  = register("temporal_anchor",  () -> null,                          TemporalAnchorAttachment.CODEC);
    // @formatter:on

    private static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name, Supplier<@Nullable T> defaultValueSupplier, Codec<T> codec) {
        return ATTACHMENTS.register(name, () -> AttachmentType.builder(defaultValueSupplier).serialize(codec.fieldOf(name)).copyOnDeath().build());
    }

    private static <T> DeferredHolder<AttachmentType<?>, AttachmentType<T>> register(String name, Supplier<@Nullable T> defaultValueSupplier, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return ATTACHMENTS.register(name, () -> AttachmentType.builder(defaultValueSupplier).serialize(codec.fieldOf(name)).sync(streamCodec).copyOnDeath().build());
    }
}
