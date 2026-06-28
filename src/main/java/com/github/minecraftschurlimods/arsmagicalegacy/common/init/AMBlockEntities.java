package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.BlackAuremBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.CelestialPrismBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.ObeliskBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.SpellRuneBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;

public interface AMBlockEntities {
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<BlockEntityType<?>, BlockEntityType<InscriptionTableBlockEntity>> INSCRIPTION_TABLE = register("inscription_table", InscriptionTableBlockEntity::new, AMBlocks.INSCRIPTION_TABLE);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarCoreBlockEntity>>        ALTAR_CORE        = register("altar_core",        AltarCoreBlockEntity::new,        AMBlocks.ALTAR_CORE);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<ObeliskBlockEntity>>          OBELISK           = register("obelisk",           ObeliskBlockEntity::new,          AMBlocks.OBELISK);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<CelestialPrismBlockEntity>>   CELESTIAL_PRISM   = register("celestial_prism",   CelestialPrismBlockEntity::new,   AMBlocks.CELESTIAL_PRISM);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<BlackAuremBlockEntity>>       BLACK_AUREM       = register("black_aurem",       BlackAuremBlockEntity::new,       AMBlocks.BLACK_AUREM);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<SpellRuneBlockEntity>>        SPELL_RUNE        = register("spell_rune",        SpellRuneBlockEntity::new,        AMBlocks.SPELL_RUNE);
    // @formatter:on

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... blocks) {
        return BLOCK_ENTITIES.register(name, () -> new BlockEntityType<>(factory, Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new)));
    }
}
