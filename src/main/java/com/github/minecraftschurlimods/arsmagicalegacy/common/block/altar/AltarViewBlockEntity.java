package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class AltarViewBlockEntity extends BlockEntity {
    public int itemRotation;
    private Optional<BlockPos> altar = Optional.empty();

    public AltarViewBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.ALTAR_VIEW.get(), pWorldPosition, pBlockState);
    }

    void setAltarPos(BlockPos pos) {
        this.altar = Optional.of(pos);
    }

    public Optional<AltarCoreBlockEntity> getAltar() {
        return this.altar.map(blockPos -> this.level != null && this.level.getBlockEntity(blockPos) instanceof AltarCoreBlockEntity ca ? ca : null);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.altar.ifPresent(blockPos -> tag.put("altar", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, blockPos)                 .getOrThrow(false, ArsMagicaLegacy.LOGGER::warn)));
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
