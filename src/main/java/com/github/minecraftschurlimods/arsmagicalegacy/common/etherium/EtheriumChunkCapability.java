package com.github.minecraftschurlimods.arsmagicalegacy.common.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.network.EtheriumPositionsSyncPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Adapted from <a href="https://github.com/Commoble/morered/blob/main/src/main/java/commoble/morered/wire_post/PostsInChunk.java">Commoble's MoreRed mod</a>.
 */
public class EtheriumChunkCapability implements ICapabilityProvider {
    public static final Capability<EtheriumChunkCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LevelChunk chunk;
    private final LazyOptional<EtheriumChunkCapability> holder = LazyOptional.of(() -> this);
    private Set<BlockPos> positions = new HashSet<>();

    public EtheriumChunkCapability(LevelChunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? CAPABILITY.orEmpty(cap, holder) : LazyOptional.empty();
    }

    public Set<BlockPos> getPositions() {
        return positions;
    }

    public void addPosition(BlockPos pos) {
        positions.add(pos);
        sendPacket();
    }

    public void addPositions(BlockPos... positions) {
        this.positions.addAll(Arrays.asList(positions));
        sendPacket();
    }

    public void removePosition(BlockPos pos) {
        positions.remove(pos);
        sendPacket();
    }

    public void removePositions(BlockPos... positions) {
        Arrays.asList(positions).forEach(this.positions::remove);
        sendPacket();
    }

    public void invalidate() {
        holder.invalidate();
    }

    private void sendPacket() {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToAllTracking(new EtheriumPositionsSyncPacket(chunk.getPos(), positions.stream().toList()), chunk);
    }
}
