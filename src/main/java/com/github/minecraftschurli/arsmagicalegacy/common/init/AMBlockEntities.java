package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.block.SpellRuneBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarViewBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.BLOCK_ENTITIES;

@NonExtendable
public interface AMBlockEntities {
    RegistryObject<BlockEntityType<AltarCoreBlockEntity>>        ALTAR_CORE        = BLOCK_ENTITIES.register("altar_core",        () -> BlockEntityType.Builder.of(AltarCoreBlockEntity::new,        AMBlocks.ALTAR_CORE.get())       .build(null));
    RegistryObject<BlockEntityType<AltarViewBlockEntity>>        ALTAR_VIEW        = BLOCK_ENTITIES.register("altar_view",        () -> BlockEntityType.Builder.of(AltarViewBlockEntity::new,        AMBlocks.ALTAR_VIEW.get())       .build(null));
    RegistryObject<BlockEntityType<InscriptionTableBlockEntity>> INSCRIPTION_TABLE = BLOCK_ENTITIES.register("inscription_table", () -> BlockEntityType.Builder.of(InscriptionTableBlockEntity::new, AMBlocks.INSCRIPTION_TABLE.get()).build(null));
    RegistryObject<BlockEntityType<SpellRuneBlockEntity>>        SPELL_RUNE        = BLOCK_ENTITIES.register("spell_rune",        () -> BlockEntityType.Builder.of(SpellRuneBlockEntity::new,        AMBlocks.SPELL_RUNE.get())       .build(null));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
