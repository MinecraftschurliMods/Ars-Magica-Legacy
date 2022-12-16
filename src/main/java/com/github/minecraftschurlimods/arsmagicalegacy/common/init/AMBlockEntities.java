package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarViewBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign.CustomHangingSignBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign.CustomSignBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.BLOCK_ENTITY_TYPES;

@NonExtendable
public interface AMBlockEntities {
    RegistryObject<BlockEntityType<AltarCoreBlockEntity>>         ALTAR_CORE             = BLOCK_ENTITY_TYPES.register("altar_core", () -> BlockEntityType.Builder.of(AltarCoreBlockEntity::new, AMBlocks.ALTAR_CORE.get()).build(null));
    RegistryObject<BlockEntityType<AltarViewBlockEntity>>         ALTAR_VIEW             = BLOCK_ENTITY_TYPES.register("altar_view", () -> BlockEntityType.Builder.of(AltarViewBlockEntity::new, AMBlocks.ALTAR_VIEW.get()).build(null));
    RegistryObject<BlockEntityType<BlackAuremBlockEntity>>        BLACK_AUREM            = BLOCK_ENTITY_TYPES.register("black_aurem", () -> BlockEntityType.Builder.of(BlackAuremBlockEntity::new, AMBlocks.BLACK_AUREM.get()).build(null));
    RegistryObject<BlockEntityType<CelestialPrismBlockEntity>>    CELESTIAL_PRISM        = BLOCK_ENTITY_TYPES.register("celestial_prism", () -> BlockEntityType.Builder.of(CelestialPrismBlockEntity::new, AMBlocks.CELESTIAL_PRISM.get()).build(null));
    RegistryObject<BlockEntityType<InscriptionTableBlockEntity>>  INSCRIPTION_TABLE      = BLOCK_ENTITY_TYPES.register("inscription_table", () -> BlockEntityType.Builder.of(InscriptionTableBlockEntity::new, AMBlocks.INSCRIPTION_TABLE.get()).build(null));
    RegistryObject<BlockEntityType<ObeliskBlockEntity>>           OBELISK                = BLOCK_ENTITY_TYPES.register("obelisk", () -> BlockEntityType.Builder.of(ObeliskBlockEntity::new, AMBlocks.OBELISK.get()).build(null));
    RegistryObject<BlockEntityType<SpellRuneBlockEntity>>         SPELL_RUNE             = BLOCK_ENTITY_TYPES.register("spell_rune", () -> BlockEntityType.Builder.of(SpellRuneBlockEntity::new, AMBlocks.SPELL_RUNE.get()).build(null));
    RegistryObject<BlockEntityType<CustomSignBlockEntity>>        WITCHWOOD_SIGN         = BLOCK_ENTITY_TYPES.register("witchwood_sign", () -> BlockEntityType.Builder.of(CustomSignBlockEntity::new, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get()).build(null));
    RegistryObject<BlockEntityType<CustomHangingSignBlockEntity>> WITCHWOOD_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("witchwood_hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get()).build(null));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
