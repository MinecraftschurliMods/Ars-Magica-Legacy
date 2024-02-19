package com.github.minecraftschurlimods.arsmagicalegacy.compat.jade;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Arrays;
import java.util.List;

class AltarComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    private static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "altar");
    private static final String POWER = "power";
    private static final String POSITIONS = "positions";
    static final AltarComponentProvider INSTANCE = new AltarComponentProvider();

    private AltarComponentProvider() {}

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if (tag.contains(POSITIONS) || tag.contains(POWER)) {
            iTooltip.add(Component.empty());
        }
        if (tag.contains(POSITIONS)) {
            List<BlockPos> list = Arrays.stream(tag.getLongArray(POSITIONS)).mapToObj(BlockPos::of).toList();
            list.forEach(pos -> iTooltip.append(iTooltip.getElementHelper().item(blockAccessor.getLevel().getBlockState(pos).getCloneItemStack(blockAccessor.getHitResult(), blockAccessor.getLevel(), pos, blockAccessor.getPlayer())).message(null)));
            if (!list.isEmpty()) {
                iTooltip.add(Component.empty());
            }
        }
        if (tag.contains(POWER)) {
            iTooltip.append(Component.translatable(TranslationConstants.ALTAR_POWER, tag.getInt(POWER)));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        if (blockEntity instanceof AltarCoreBlockEntity altar) {
            compoundTag.putLongArray(POSITIONS, altar.getBoundPositions().stream().map(BlockPos::asLong).toList());
            compoundTag.putInt(POWER, altar.getPowerLevel());
        }
    }
}
