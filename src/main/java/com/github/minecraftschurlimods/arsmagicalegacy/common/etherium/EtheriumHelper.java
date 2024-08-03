package com.github.minecraftschurlimods.arsmagicalegacy.common.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public final class EtheriumHelper implements IEtheriumHelper {
    public static final BlockCapability<IEtheriumProvider, Void> ETHERIUM_PROVIDER = BlockCapability.createVoid(ArsMagicaAPI.resource("etherium_provider"), IEtheriumProvider.class);
    public static final BlockCapability<IEtheriumConsumer, Void> ETHERIUM_CONSUMER = BlockCapability.createVoid(ArsMagicaAPI.resource("etherium_consumer"), IEtheriumConsumer.class);
    private static final Lazy<EtheriumHelper> INSTANCE = Lazy.of(EtheriumHelper::new);

    private EtheriumHelper() {}

    /**
     * @return The only instance of this class.
     */
    public static EtheriumHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public boolean hasEtheriumProvider(BlockEntity blockEntity) {
        return getEtheriumProvider(blockEntity) != null;
    }

    @Override
    public boolean hasEtheriumConsumer(BlockEntity blockEntity) {
        return getEtheriumConsumer(blockEntity) != null;
    }

    @Override
    public boolean hasEtheriumProvider(Level level, BlockPos pos) {
        return getEtheriumProvider(level, pos) != null;
    }

    @Override
    public boolean hasEtheriumConsumer(Level level, BlockPos pos) {
        return getEtheriumConsumer(level, pos) != null;
    }

    @Nullable
    @Override
    public IEtheriumProvider getEtheriumProvider(BlockEntity blockEntity) {
        Level level = blockEntity.getLevel();
        if (level == null) return null;
        return level.getCapability(ETHERIUM_PROVIDER, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, null);
    }

    @Nullable
    @Override
    public IEtheriumConsumer getEtheriumConsumer(BlockEntity blockEntity) {
        Level level = blockEntity.getLevel();
        if (level == null) return null;
        return level.getCapability(ETHERIUM_CONSUMER, blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity, null);
    }

    @Nullable
    @Override
    public IEtheriumProvider getEtheriumProvider(Level level, BlockPos pos) {
        return level.getCapability(ETHERIUM_PROVIDER, pos, null);
    }

    @Nullable
    @Override
    public IEtheriumConsumer getEtheriumConsumer(Level level, BlockPos pos) {
        return level.getCapability(ETHERIUM_CONSUMER, pos, null);
    }

    @Override
    public EtheriumType getEtheriumType(ItemStack stack) {
        return stack.get(AMDataComponents.ETHERIUM_TYPE);
    }

    @Override
    public void setEtheriumType(ItemStack stack, EtheriumType type) {
        stack.set(AMDataComponents.ETHERIUM_TYPE, type);
    }
}
