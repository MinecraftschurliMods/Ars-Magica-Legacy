package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.block.SpellRuneBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarViewBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.Set;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.BLOCK_ENTITIES;

@NonExtendable
public interface AMBlockEntities {
    RegistryObject<BlockEntityType<AltarCoreBlockEntity>> ALTAR_CORE = BLOCK_ENTITIES.register("altar_core", () -> new BlockEntityType<>(AltarCoreBlockEntity::new, Set.of(AMBlocks.ALTAR_CORE.get()), null));
    RegistryObject<BlockEntityType<AltarViewBlockEntity>> ALTAR_VIEW = BLOCK_ENTITIES.register("altar_view", () -> new BlockEntityType<>(AltarViewBlockEntity::new, Set.of(AMBlocks.ALTAR_VIEW.get()), null));
    RegistryObject<BlockEntityType<SpellRuneBlockEntity>> SPELL_RUNE = BLOCK_ENTITIES.register("spell_rune", () -> new BlockEntityType<>(SpellRuneBlockEntity::new, Set.of(AMBlocks.SPELL_RUNE.get()), null));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}