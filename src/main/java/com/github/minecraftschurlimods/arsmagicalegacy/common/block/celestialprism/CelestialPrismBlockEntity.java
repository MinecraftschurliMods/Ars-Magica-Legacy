package com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.SimpleEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CelestialPrismBlockEntity extends BlockEntity {
    private final SimpleEtheriumProvider provider = new SimpleEtheriumProvider(EtheriumType.LIGHT, Config.SERVER.MAX_ETHERIUM_STORAGE.get()).setCallback(CelestialPrismBlockEntity::onConsume);
    private int time;

    public CelestialPrismBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.CELESTIAL_PRISM.get(), pWorldPosition, pBlockState);
    }

    private static void onConsume(Level level, BlockPos consumerPos, int amount) {
        // TODO spawn particles
    }

    void tick(Level level, BlockPos pos, BlockState state) {
        int tier = state.getBlock() instanceof CelestialPrismBlock block ? block.getTier(level, pos) : 0;
        if (level.canSeeSky(pos) && (level.isDay() || tier == 5)) {
            if (time > 0) {
                time--;
            } else {
                time = 6 / (tier + 1);
                provider.add(1);
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.provider.set(tag.getInt("etheriumValue"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("etheriumValue", this.provider.getAmount());
    }

    public IEtheriumProvider getEtheriumCapability(Void $) {
        return provider;
    }
}
