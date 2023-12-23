/*
package com.github.minecraftschurlimods.arsmagicalegacy.compat.jade;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(AltarComponentProvider.INSTANCE, AltarCoreBlockEntity.class);
        registration.registerBlockDataProvider(EtheriumComponentProvider.INSTANCE, ObeliskBlockEntity.class);
        registration.registerBlockDataProvider(EtheriumComponentProvider.INSTANCE, CelestialPrismBlockEntity.class);
        registration.registerBlockDataProvider(EtheriumComponentProvider.INSTANCE, BlackAuremBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(AltarComponentProvider.INSTANCE, AltarCoreBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.INSTANCE, ObeliskBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.INSTANCE, CelestialPrismBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.INSTANCE, BlackAuremBlock.class);
        registration.addRayTraceCallback(((hitResult, accessor, original) -> {
            if (accessor instanceof BlockAccessor blockAccessor && hitResult instanceof BlockHitResult bhr) {
                BlockState state = blockAccessor.getBlockState();
                if (state.getBlock() instanceof ObeliskBlock) {
                    int offset = switch (state.getValue(ObeliskBlock.PART)) {
                        case UPPER -> -2;
                        case MIDDLE -> -1;
                        case LOWER -> 0;
                    };
                    BlockPos newPos = bhr.getBlockPos().offset(0, offset, 0);
                    return registration.blockAccessor()
                            .from(blockAccessor)
                            .hit(new BlockHitResult(bhr.getLocation(), bhr.getDirection(), newPos, bhr.isInside()))
                            .blockState(state.setValue(ObeliskBlock.PART, ObeliskBlock.Part.LOWER))
                            .blockEntity(blockAccessor.getLevel().getBlockEntity(newPos))
                            .build();
                }
                if (state.getBlock() instanceof CelestialPrismBlock) {
                    int offset = switch (state.getValue(CelestialPrismBlock.HALF)) {
                        case UPPER -> -1;
                        case LOWER -> 0;
                    };
                    BlockPos newPos = bhr.getBlockPos().offset(0, offset, 0);
                    return registration.blockAccessor()
                            .from(blockAccessor)
                            .hit(new BlockHitResult(bhr.getLocation(), bhr.getDirection(), newPos, bhr.isInside()))
                            .blockState(state.setValue(CelestialPrismBlock.HALF, DoubleBlockHalf.LOWER))
                            .blockEntity(blockAccessor.getLevel().getBlockEntity(newPos))
                            .build();
                }
            }
            return accessor;
        }));
    }
}
*/
