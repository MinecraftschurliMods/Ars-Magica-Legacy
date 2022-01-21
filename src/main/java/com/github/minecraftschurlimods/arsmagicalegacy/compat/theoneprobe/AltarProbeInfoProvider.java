package com.github.minecraftschurlimods.arsmagicalegacy.compat.theoneprobe;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.Optional;

class AltarProbeInfoProvider implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, "altar");
    }

    @Override
    public void addProbeInfo(final ProbeMode probeMode, final IProbeInfo iProbeInfo, final Player player, final Level level, final BlockState blockState, final IProbeHitData iProbeHitData) {
        BlockEntity blockEntity = level.getBlockEntity(iProbeHitData.getPos());
        if (blockEntity instanceof AltarCoreBlockEntity altarCoreBlockEntity) {
            Collection<BlockPos> blockPos = altarCoreBlockEntity.getBoundPositions();
            if (probeMode == ProbeMode.NORMAL) {
                IProbeInfo horizontal = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().borderColor(0xffddddff).alignment(ElementAlignment.ALIGN_CENTER).bottomPadding(3));
                blockPos.forEach(pos -> horizontal.item(level.getBlockState(pos).getCloneItemStack(new BlockHitResult(Vec3.atCenterOf(pos), Direction.DOWN, pos, true), level, pos, player), iProbeInfo.defaultItemStyle().height(15)));
            } else {
                IProbeInfo vertical = iProbeInfo.vertical(iProbeInfo.defaultLayoutStyle().borderColor(0xffddddff).alignment(ElementAlignment.ALIGN_CENTER));
                blockPos.forEach(pos -> vertical.horizontal(vertical.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER).topPadding(2))
                                                .item(level.getBlockState(pos).getCloneItemStack(new BlockHitResult(Vec3.atCenterOf(pos), Direction.DOWN, pos, true), level, pos, player), iProbeInfo.defaultItemStyle().height(15))
                                                .mcText(new TextComponent(pos.toShortString()), iProbeInfo.defaultTextStyle().alignment(ElementAlignment.ALIGN_CENTER).topPadding(-2).rightPadding(5)));
            }
        }
    }
}
