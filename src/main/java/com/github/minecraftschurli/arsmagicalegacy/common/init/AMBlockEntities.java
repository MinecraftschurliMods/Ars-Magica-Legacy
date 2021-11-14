package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarViewBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Set;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.BLOCK_ENTITIES;

public interface AMBlockEntities {
    RegistryObject<BlockEntityType<AltarCoreBlockEntity>> ALTAR_CORE = BLOCK_ENTITIES.register("altar_core", () -> new BlockEntityType<>(AltarCoreBlockEntity::new, Set.of(AMBlocks.ALTAR_CORE.get()), null));
    RegistryObject<BlockEntityType<AltarViewBlockEntity>> ALTAR_VIEW = BLOCK_ENTITIES.register("altar_view", () -> new BlockEntityType<>(AltarViewBlockEntity::new, Set.of(AMBlocks.ALTAR_VIEW.get()), null));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
