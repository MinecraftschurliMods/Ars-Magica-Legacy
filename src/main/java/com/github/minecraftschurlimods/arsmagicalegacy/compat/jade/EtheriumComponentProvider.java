package com.github.minecraftschurlimods.arsmagicalegacy.compat.jade;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMCapabilities;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.impl.ui.ItemStackElement;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedSet;

final class EtheriumComponentProvider implements StreamServerDataProvider<BlockAccessor, EtheriumComponentProvider.Data> {
    private static final Identifier ID = ArsMagicaApi.id("etherium");
    public static final EtheriumComponentProvider INSTANCE = new EtheriumComponentProvider();

    private EtheriumComponentProvider() {}

    @Override
    public @Nullable Data streamData(BlockAccessor blockAccessor) {
        Level level = blockAccessor.getLevel();
        EtheriumHandler capability = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, blockAccessor.getPosition(), null);
        if (capability == null) return null;
        Map<Holder<EtheriumType>, Etherium> etheriumMap = new HashMap<>();
        for (Holder<EtheriumType> etheriumType : capability.getEtheriumTypes()) {
            int maxEtherium = capability.getMaxAmount(etheriumType);
            if (maxEtherium > 0) {
                etheriumMap.put(etheriumType, new Etherium(capability.getAmount(etheriumType), maxEtherium));
            }
        }
        Optional<SequencedSet<BlockPos>> positions;
        if (capability.canHaveConnectedPositions()) {
            positions = Optional.of(capability.getConnectedPositions());
        } else {
            positions = Optional.empty();
        }
        return new Data(etheriumMap, positions);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
        return Data.STREAM_CODEC;
    }

    @Override
    public Identifier getUid() {
        return ID;
    }

    public record Etherium(int amount, int maxAmount) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Etherium> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, Etherium::amount,
            ByteBufCodecs.VAR_INT, Etherium::maxAmount,
            Etherium::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, Map<Holder<EtheriumType>, Etherium>> MAP_STREAM_CODEC = ByteBufCodecs.map(
            HashMap::new,
            ByteBufCodecs.holderRegistry(AMRegistries.Keys.ETHERIUM_TYPE),
            STREAM_CODEC);
    }

    public record Data(Map<Holder<EtheriumType>, Etherium> etheriumMap, Optional<SequencedSet<BlockPos>> connectedPositions) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
            Etherium.MAP_STREAM_CODEC, Data::etheriumMap,
            ByteBufCodecs.optional(ByteBufCodecs.collection(LinkedHashSet::new, BlockPos.STREAM_CODEC)), Data::connectedPositions,
            Data::new);
    }

    public static class Client implements IBlockComponentProvider {
        public static final Client INSTANCE = new Client();

        private Client() {}

        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
            Optional<Data> optionalData = EtheriumComponentProvider.INSTANCE.decodeFromData(blockAccessor);
            if (optionalData.isEmpty()) return;
            Data data = optionalData.get();
            data.etheriumMap().forEach((holder, etherium) -> iTooltip.add(Component.translatable(AMTranslations.ETHERIUM_KEY, EtheriumType.getName(holder), etherium.amount(), etherium.maxAmount())));
            Level level = blockAccessor.getLevel();
            data.connectedPositions().ifPresent(positions -> {
                if (positions.isEmpty()) return;
                for (BlockPos pos : positions) {
                    iTooltip.add(ItemStackElement.of(level.getBlockState(pos).getCloneItemStack(pos, level, true, blockAccessor.getPlayer())));
                }
            });
        }

        @Override
        public Identifier getUid() {
            return ID;
        }
    }
}
