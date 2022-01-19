package com.github.minecraftschurlimods.arsmagicalegacy.compat.theoneprobe;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

class EtheriumProbeInfoProvider implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium");
    }

    @Override
    public void addProbeInfo(final ProbeMode probeMode, final IProbeInfo iProbeInfo, final Player player, final Level level, final BlockState blockState, final IProbeHitData iProbeHitData) {
        Optional.ofNullable(level.getBlockEntity(iProbeHitData.getPos()))
                .filter(ArsMagicaAPI.get().getEtheriumHelper()::hasEtheriumProvider)
                .map(ArsMagicaAPI.get().getEtheriumHelper()::getEtheriumProvider)
                .flatMap(LazyOptional::resolve)
                .ifPresent(provider -> iProbeInfo.progress(provider.getAmount(),
                                                           provider.getMax(),
                                                           iProbeInfo.defaultProgressStyle()
                                                                     .filledColor(provider.getType().getColor())
                                                                     .numberFormat(NumberFormat.COMMAS)));
    }
}
