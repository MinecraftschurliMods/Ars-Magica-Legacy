package com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.SimpleEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class CelestialPrismBlockEntity extends BlockEntity {
    private final SimpleEtheriumProvider provider = new SimpleEtheriumProvider(EtheriumType.LIGHT, Config.SERVER.MAX_ETHERIUM_STORAGE.get()).setCallback(CelestialPrismBlockEntity::onConsume);

    private final LazyOptional<IEtheriumProvider> etheriumHolder = LazyOptional.of(() -> provider);

    public CelestialPrismBlockEntity(final BlockPos pWorldPosition, final BlockState pBlockState) {
        super(AMBlockEntities.CELESTIAL_PRISM.get(), pWorldPosition, pBlockState);
    }

    void tick(final Level level, final BlockPos pos, final BlockState state) {
        if (level.isDay() && level.canSeeSky(pos) && provider.canStore(0)) {
            provider.add(getEtheriumByTime(level));
        }
    }

    private int getEtheriumByTime(final Level level) {
        return (int) level.getTimeOfDay(0);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap) {
        return EtheriumHelper.instance().getEtheriumProviderCapability().orEmpty(cap, etheriumHolder);
    }

    private static void onConsume(Level level, BlockPos consumerPos, int amount) {
        // TODO spawn particles
    }
}
