package com.github.minecraftschurlimods.arsmagicalegacy.compat.jade;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.ITierCheckingBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

class EtheriumComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium");
    private static final String ETHERIUM = "etherium";
    private static final String MAX_ETHERIUM = "max_etherium";
    private static final String TIER = "tier";
    static final EtheriumComponentProvider INSTANCE = new EtheriumComponentProvider();

    private EtheriumComponentProvider() {}

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if (tag.contains(TIER)) {
            iTooltip.add(Component.translatable(TranslationConstants.TIER, tag.getInt(TIER)));
        }
        if (tag.contains(ETHERIUM) && tag.contains(MAX_ETHERIUM)) {
            iTooltip.add(Component.translatable(TranslationConstants.ETHERIUM_AMOUNT, tag.getInt(ETHERIUM), tag.getInt(MAX_ETHERIUM)));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor accessor) {
        BlockPos pos = accessor.getPosition();
        int tier = accessor.getBlockState().getBlock() instanceof ITierCheckingBlock tierBlock ? tierBlock.getTier(accessor.getLevel(), pos) : -1;
        compoundTag.putInt(TIER, tier);
        ArsMagicaAPI.get().getEtheriumHelper().getEtheriumProvider(accessor.getLevel(), pos).ifPresent(provider -> {
            compoundTag.putInt(ETHERIUM, provider.getAmount());
            compoundTag.putInt(MAX_ETHERIUM, provider.getMax());
        });
    }
}
