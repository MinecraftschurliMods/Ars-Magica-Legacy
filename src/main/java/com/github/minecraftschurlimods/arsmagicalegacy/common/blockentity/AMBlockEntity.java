package com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AMBlockEntity<T> extends BlockEntity {
    private static final String DATA_KEY = ArsMagicaApi.id("data").toString();
    private final Codec<T> codec;

    public AMBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, Codec<T> codec) {
        super(type, pos, state);
        this.codec = codec;
    }

    public abstract void fromData(T data);

    public abstract T toData();

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        input.read(DATA_KEY, codec).ifPresent(this::fromData);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store(DATA_KEY, codec, toData());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
