package com.github.minecraftschurlimods.arsmagicalegacy.compat.jade;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;

final class TierComponentProvider implements StreamServerDataProvider<BlockAccessor, Integer> {
    private static final Identifier ID = ArsMagicaApi.id("tier");
    static final TierComponentProvider INSTANCE = new TierComponentProvider();

    private TierComponentProvider() {}

    @Override
    public @Nullable Integer streamData(BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof EtheriumGeneratorBlockEntity blockEntity) {
            int tier = blockEntity.getTier(blockAccessor.getLevel(), blockAccessor.getPosition());
            if (tier > 0) {
                return tier;
            }
        }
        return null;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Integer> streamCodec() {
        return ByteBufCodecs.VAR_INT.cast();
    }

    @Override
    public Identifier getUid() {
        return ID;
    }

    public static final class Client implements IBlockComponentProvider {
        public static final Client INSTANCE = new Client();

        private Client() {}

        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
            TierComponentProvider.INSTANCE.decodeFromData(blockAccessor).ifPresent(tier -> iTooltip.add(Component.translatable(AMTranslations.TIER_KEY, tier)));
        }

        @Override
        public Identifier getUid() {
            return ID;
        }
    }
}
