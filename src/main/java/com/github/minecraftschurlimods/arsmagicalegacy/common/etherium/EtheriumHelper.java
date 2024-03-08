package com.github.minecraftschurlimods.arsmagicalegacy.common.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public final class EtheriumHelper implements IEtheriumHelper {
    public static final BlockCapability<IEtheriumProvider, Void> ETHERIUM_PROVIDER = BlockCapability.createVoid(new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium_provider"), IEtheriumProvider.class);
    public static final BlockCapability<IEtheriumConsumer, Void> ETHERIUM_CONSUMER = BlockCapability.createVoid(new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium_consumer"), IEtheriumConsumer.class);
    private static final Lazy<EtheriumHelper> INSTANCE = Lazy.concurrentOf(EtheriumHelper::new);
    private static final String KEY = "etherium_type";

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
        try {
            return EtheriumType.valueOf(stack.getOrCreateTag().getCompound(ArsMagicaAPI.MOD_ID).getString(KEY).toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void setEtheriumType(ItemStack stack, EtheriumType type) {
        CompoundTag tag = stack.getOrCreateTag().getCompound(ArsMagicaAPI.MOD_ID);
        tag.putString(KEY, type.name().toLowerCase());
        stack.getOrCreateTag().put(ArsMagicaAPI.MOD_ID, tag);
    }
}
