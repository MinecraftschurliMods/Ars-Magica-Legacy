package com.github.minecraftschurlimods.arsmagicalegacy.common.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;

public final class EtheriumHelper implements IEtheriumHelper {
    private static final Lazy<EtheriumHelper> INSTANCE = Lazy.concurrentOf(EtheriumHelper::new);
    private static final Capability<IEtheriumProvider> ETHERIUM_PROVIDER = CapabilityManager.get(new CapabilityToken<>() {});
    private static final Capability<IEtheriumConsumer> ETHERIUM_CONSUMER = CapabilityManager.get(new CapabilityToken<>() {});

    private EtheriumHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static EtheriumHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The etherium provider capability.
     */
    public Capability<IEtheriumProvider> getEtheriumProviderCapability() {
        return ETHERIUM_PROVIDER;
    }

    /**
     * @return The etherium consumer capability.
     */
    public Capability<IEtheriumConsumer> getEtheriumConsumerCapability() {
        return ETHERIUM_CONSUMER;
    }

    @Override
    public boolean hasEtheriumProvider(BlockEntity blockEntity) {
        return getEtheriumProvider(blockEntity).isPresent();
    }

    @Override
    public boolean hasEtheriumConsumer(BlockEntity blockEntity) {
        return getEtheriumConsumer(blockEntity).isPresent();
    }

    @Override
    public boolean hasEtheriumProvider(Level level, BlockPos pos) {
        return getEtheriumProvider(level, pos).isPresent();
    }

    @Override
    public boolean hasEtheriumConsumer(Level level, BlockPos pos) {
        return getEtheriumConsumer(level, pos).isPresent();
    }

    @Override
    public LazyOptional<IEtheriumProvider> getEtheriumProvider(BlockEntity blockEntity) {
        return blockEntity.getCapability(getEtheriumProviderCapability());
    }

    @Override
    public LazyOptional<IEtheriumConsumer> getEtheriumConsumer(BlockEntity blockEntity) {
        return blockEntity.getCapability(getEtheriumConsumerCapability());
    }

    @Override
    public LazyOptional<IEtheriumProvider> getEtheriumProvider(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return LazyOptional.empty();
        return getEtheriumProvider(blockEntity);
    }

    @Override
    public LazyOptional<IEtheriumConsumer> getEtheriumConsumer(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return LazyOptional.empty();
        return getEtheriumConsumer(blockEntity);
    }
}
