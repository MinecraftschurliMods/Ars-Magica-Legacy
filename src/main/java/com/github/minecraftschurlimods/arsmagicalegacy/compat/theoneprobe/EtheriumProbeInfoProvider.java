package com.github.minecraftschurlimods.arsmagicalegacy.compat.theoneprobe;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.ITierCheckingBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import mcjty.theoneprobe.api.Color;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

class EtheriumProbeInfoProvider implements IProbeInfoProvider {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        BlockPos pos = iProbeHitData.getPos();
        int tier = blockState.getBlock() instanceof ITierCheckingBlock tierBlock ? tierBlock.getTier(level, pos) : -1;
        ArsMagicaAPI.get().getEtheriumHelper().getEtheriumProvider(level, pos).ifPresent(provider -> iProbeInfo.progress(provider.getAmount(), provider.getMax(), iProbeInfo.defaultProgressStyle().filledColor(provider.getType().getColor()).alternateFilledColor(Color.darker(provider.getType().getColor(), .8)).numberFormat(NumberFormat.FULL)));
        if (tier > -1) {
            iProbeInfo.mcText(Component.translatable(TranslationConstants.TIER, tier));
        }
    }
}
