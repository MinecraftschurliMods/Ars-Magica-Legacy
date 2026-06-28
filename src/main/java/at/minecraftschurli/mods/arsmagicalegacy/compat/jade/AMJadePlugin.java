package at.minecraftschurli.mods.arsmagicalegacy.compat.jade;

import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.BlackAuremBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.mods.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class AMJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(AltarComponentProvider.INSTANCE, AltarCoreBlockEntity.class);
        registration.registerBlockDataProvider(EtheriumComponentProvider.INSTANCE, AltarCoreBlockEntity.class);
        registration.registerBlockDataProvider(EtheriumComponentProvider.INSTANCE, EtheriumGeneratorBlockEntity.class);
        registration.registerBlockDataProvider(TierComponentProvider.INSTANCE, EtheriumGeneratorBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(AltarComponentProvider.Client.INSTANCE, AltarCoreBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.Client.INSTANCE, AltarCoreBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.Client.INSTANCE, ObeliskBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.Client.INSTANCE, CelestialPrismBlock.class);
        registration.registerBlockComponent(EtheriumComponentProvider.Client.INSTANCE, BlackAuremBlock.class);
        registration.registerBlockComponent(TierComponentProvider.Client.INSTANCE, ObeliskBlock.class);
        registration.registerBlockComponent(TierComponentProvider.Client.INSTANCE, CelestialPrismBlock.class);
        registration.registerBlockComponent(TierComponentProvider.Client.INSTANCE, BlackAuremBlock.class);
        registration.addRayTraceCallback(((hitResult, accessor, original) -> {
            if (!(accessor instanceof BlockAccessor blockAccessor) || hitResult.getType() != HitResult.Type.BLOCK || !(hitResult instanceof BlockHitResult bhr)) return accessor;
            BlockPos pos = bhr.getBlockPos();
            BlockState state = blockAccessor.getBlockState();
            if (state.getBlock() instanceof ObeliskBlock) {
                BlockPos newPos = pos.below(switch (state.getValue(ObeliskBlock.PART)) {
                    case UPPER -> 2;
                    case MIDDLE -> 1;
                    case LOWER -> 0;
                });
                return registration.blockAccessor()
                    .from(blockAccessor)
                    .hit(new BlockHitResult(bhr.getLocation(), bhr.getDirection(), newPos, bhr.isInside()))
                    .blockState(state.setValue(ObeliskBlock.PART, ObeliskBlock.Part.LOWER))
                    .blockEntity(blockAccessor.getLevel().getBlockEntity(newPos))
                    .build();
            }
            if (state.getBlock() instanceof CelestialPrismBlock) {
                BlockPos newPos = state.getValue(CelestialPrismBlock.PART) == CelestialPrismBlock.Part.LOWER ? pos : pos.below();
                return registration.blockAccessor()
                    .from(blockAccessor)
                    .hit(new BlockHitResult(bhr.getLocation(), bhr.getDirection(), newPos, bhr.isInside()))
                    .blockState(state.setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER))
                    .blockEntity(blockAccessor.getLevel().getBlockEntity(newPos))
                    .build();
            }
            return accessor;
        }));
    }
}
