package com.github.minecraftschurlimods.arsmagicalegacy.compat.jade;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.AltarCoreBlockEntity;
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

final class AltarComponentProvider implements StreamServerDataProvider<BlockAccessor, Integer> {
    private static final Identifier ID = ArsMagicaApi.id("altar");
    static final AltarComponentProvider INSTANCE = new AltarComponentProvider();

    private AltarComponentProvider() {}

    @Override
    public @Nullable Integer streamData(BlockAccessor blockAccessor) {
        return blockAccessor.getBlockEntity() instanceof AltarCoreBlockEntity altar ? altar.getPower() : null;
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
            AltarComponentProvider.INSTANCE.decodeFromData(blockAccessor).ifPresent(power -> iTooltip.add(Component.translatable(AMTranslations.ALTAR_CORE_POWER_KEY, power)));
        }

        @Override
        public Identifier getUid() {
            return ID;
        }
    }
}
