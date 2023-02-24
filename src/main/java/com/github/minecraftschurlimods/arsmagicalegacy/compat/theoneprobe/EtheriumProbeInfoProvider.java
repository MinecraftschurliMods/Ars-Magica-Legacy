package com.github.minecraftschurlimods.arsmagicalegacy.compat.theoneprobe;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
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
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

class EtheriumProbeInfoProvider implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        BlockPos pos = iProbeHitData.getPos();
        int tier;
        if (blockState.getBlock() instanceof ObeliskBlock obeliskBlock) {
            pos = pos.below(blockState.getValue(ObeliskBlock.PART).ordinal());
            tier = obeliskBlock.getTier(level, pos);
        } else if (blockState.getBlock() instanceof CelestialPrismBlock celestialPrismBlock) {
            if (blockState.getValue(CelestialPrismBlock.HALF) != DoubleBlockHalf.LOWER) {
                pos = pos.below();
            }
            tier = celestialPrismBlock.getTier(level, pos);
        } else if (blockState.getBlock() instanceof BlackAuremBlock blackAuremBlock) {
            tier = blackAuremBlock.getTier(level, pos);
        } else {
            tier = -1;
        }
        ArsMagicaAPI.get().getEtheriumHelper().getEtheriumProvider(level, pos).ifPresent(provider -> iProbeInfo.progress(provider.getAmount(), provider.getMax(), iProbeInfo.defaultProgressStyle().filledColor(provider.getType().getColor()).alternateFilledColor(Color.darker(provider.getType().getColor(), .8)).numberFormat(NumberFormat.FULL)));
        if (tier > -1) {
            iProbeInfo.mcText(Component.translatable(TranslationConstants.TIER, tier));
        }
    }
}
