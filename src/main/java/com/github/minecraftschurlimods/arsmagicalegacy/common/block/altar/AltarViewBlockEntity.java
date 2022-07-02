package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AltarViewBlockEntity extends BlockEntity {
    public int itemRotation;
    private Optional<BlockPos> altar = Optional.empty();

    public AltarViewBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.ALTAR_VIEW.get(), pWorldPosition, pBlockState);
    }

    void setAltarPos(BlockPos pos) {
        altar = Optional.of(pos);
    }

    /**
     * @return The altar core block entity this altar view belongs to.
     */
    public Optional<AltarCoreBlockEntity> getAltar() {
        return altar.map(blockPos -> level != null && level.getBlockEntity(blockPos) instanceof AltarCoreBlockEntity ca ? ca : null);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        altar.ifPresent(blockPos -> tag.put("altar", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, blockPos).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn)));
    }

    @Override
    public void load(CompoundTag nbt) {
        if (nbt.contains("altar")) {
            setAltarPos(BlockPos.CODEC.decode(NbtOps.INSTANCE, nbt.get("altar"))
                    .map(Pair::getFirst)
                    .getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
        super.load(nbt);
    }
}
