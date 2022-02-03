package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.DesertNovaBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.OcculusBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.TarmaRootBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.WakebloomBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.WizardsChalkBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarViewBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.BLOCKS;

@NonExtendable
public interface AMBlocks {
    BlockBehaviour.Properties FLOWER_POT = BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion();

    RegistryObject<OcculusBlock>          OCCULUS                  = BLOCKS.register("occulus",                  OcculusBlock::new);
    RegistryObject<InscriptionTableBlock> INSCRIPTION_TABLE        = BLOCKS.register("inscription_table",        InscriptionTableBlock::new);
    RegistryObject<AltarCoreBlock>        ALTAR_CORE               = BLOCKS.register("altar_core",               AltarCoreBlock::new);
    RegistryObject<AltarViewBlock>        ALTAR_VIEW               = BLOCKS.register("altar_view",               AltarViewBlock::new);
    RegistryObject<Block>                 MAGIC_WALL               = BLOCKS.register("magic_wall",               () -> new GlassBlock(BlockBehaviour.Properties.of(Material.ICE).strength(3F).noOcclusion().noOcclusion().isValidSpawn(AMBlocks::never).isRedstoneConductor(AMBlocks::never).isSuffocating(AMBlocks::never).isViewBlocking(AMBlocks::never)));
    RegistryObject<ObeliskBlock>          OBELISK                  = BLOCKS.register("obelisk",                  ObeliskBlock::new);
    RegistryObject<CelestialPrismBlock>   CELESTIAL_PRISM          = BLOCKS.register("celestial_prism",          CelestialPrismBlock::new);
    RegistryObject<BlackAuremBlock>       BLACK_AUREM              = BLOCKS.register("black_aurem",              BlackAuremBlock::new);
    RegistryObject<WizardsChalkBlock>     WIZARDS_CHALK            = BLOCKS.register("wizards_chalk",            WizardsChalkBlock::new);
    RegistryObject<SpellRuneBlock>        SPELL_RUNE               = BLOCKS.register("spell_rune",               SpellRuneBlock::new);
    RegistryObject<Block>                 CHIMERITE_ORE            = BLOCKS.register("chimerite_ore",            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 DEEPSLATE_CHIMERITE_ORE  = BLOCKS.register("deepslate_chimerite_ore",  () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block>                 CHIMERITE_BLOCK          = BLOCKS.register("chimerite_block",          () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 TOPAZ_ORE                = BLOCKS.register("topaz_ore",                () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 DEEPSLATE_TOPAZ_ORE      = BLOCKS.register("deepslate_topaz_ore",      () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block>                 TOPAZ_BLOCK              = BLOCKS.register("topaz_block",              () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.DIAMOND).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 VINTEUM_ORE              = BLOCKS.register("vinteum_ore",              () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 DEEPSLATE_VINTEUM_ORE    = BLOCKS.register("deepslate_vinteum_ore",    () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block>                 VINTEUM_BLOCK            = BLOCKS.register("vinteum_block",            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.LAPIS).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 MOONSTONE_ORE            = BLOCKS.register("moonstone_ore",            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 DEEPSLATE_MOONSTONE_ORE  = BLOCKS.register("deepslate_moonstone_ore",  () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F)));
    RegistryObject<Block>                 MOONSTONE_BLOCK          = BLOCKS.register("moonstone_block",          () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block>                 SUNSTONE_ORE             = BLOCKS.register("sunstone_ore",             () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50F, 1200F)));
    RegistryObject<Block>                 SUNSTONE_BLOCK           = BLOCKS.register("sunstone_block",           () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<RotatedPillarBlock>    WITCHWOOD_LOG            = BLOCKS.register("witchwood_log",            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, s -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_LIGHT_BLUE : MaterialColor.TERRACOTTA_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<RotatedPillarBlock>    WITCHWOOD                = BLOCKS.register("witchwood",                () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<RotatedPillarBlock>    STRIPPED_WITCHWOOD_LOG   = BLOCKS.register("stripped_witchwood_log",   () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<RotatedPillarBlock>    STRIPPED_WITCHWOOD       = BLOCKS.register("stripped_witchwood",       () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<LeavesBlock>           WITCHWOOD_LEAVES         = BLOCKS.register("witchwood_leaves",         () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.QUARTZ).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((a, b, c, d) -> d == EntityType.OCELOT || d == EntityType.PARROT).isSuffocating(AMBlocks::never).isViewBlocking(AMBlocks::never)));
    //    RegistryObject<SaplingBlock>          WITCHWOOD_SAPLING        = BLOCKS.register("witchwood_sapling",              () -> new SaplingBlock(null, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))); // TODO tree type
    RegistryObject<BushBlock>             WITCHWOOD_SAPLING        = BLOCKS.register("witchwood_sapling",        () -> new BushBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))); // TODO tree type
    RegistryObject<FlowerPotBlock>        POTTED_WITCHWOOD_SAPLING = flowerPot(WITCHWOOD_SAPLING);
    RegistryObject<Block>                 WITCHWOOD_PLANKS         = BLOCKS.register("witchwood_planks",         () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<SlabBlock>             WITCHWOOD_SLAB           = BLOCKS.register("witchwood_slab",           () -> new SlabBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<StairBlock>            WITCHWOOD_STAIRS         = BLOCKS.register("witchwood_stairs",         () -> new StairBlock(() -> AMBlocks.WITCHWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<FenceBlock>            WITCHWOOD_FENCE          = BLOCKS.register("witchwood_fence",          () -> new FenceBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<FenceGateBlock>        WITCHWOOD_FENCE_GATE     = BLOCKS.register("witchwood_fence_gate",     () -> new FenceGateBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<DoorBlock>             WITCHWOOD_DOOR           = BLOCKS.register("witchwood_door",           () -> new DoorBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get()).noOcclusion()));
    RegistryObject<TrapDoorBlock>         WITCHWOOD_TRAPDOOR       = BLOCKS.register("witchwood_trapdoor",       () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get()).noOcclusion().isValidSpawn(AMBlocks::never)));
    RegistryObject<WoodButtonBlock>       WITCHWOOD_BUTTON         = BLOCKS.register("witchwood_button",         () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<PressurePlateBlock>    WITCHWOOD_PRESSURE_PLATE = BLOCKS.register("witchwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<FlowerBlock>           AUM                      = BLOCKS.register("aum",                      () -> new FlowerBlock(AMMobEffects.mana_regen, 7, BlockBehaviour.Properties.copy(Blocks.POPPY)));
    RegistryObject<FlowerBlock>           CERUBLOSSOM              = BLOCKS.register("cerublossom",              () -> new FlowerBlock(MobEffects.LEVITATION, 7, BlockBehaviour.Properties.copy(Blocks.POPPY)));
    RegistryObject<FlowerBlock>           DESERT_NOVA              = BLOCKS.register("desert_nova",              DesertNovaBlock::new);
    RegistryObject<FlowerBlock>           TARMA_ROOT               = BLOCKS.register("tarma_root",               TarmaRootBlock::new);
    RegistryObject<FlowerBlock>           WAKEBLOOM                = BLOCKS.register("wakebloom",                WakebloomBlock::new);
    RegistryObject<FlowerPotBlock>        POTTED_AUM               = flowerPot(AUM);
    RegistryObject<FlowerPotBlock>        POTTED_CERUBLOSSOM       = flowerPot(CERUBLOSSOM);
    RegistryObject<FlowerPotBlock>        POTTED_DESERT_NOVA       = flowerPot(DESERT_NOVA);
    RegistryObject<FlowerPotBlock>        POTTED_TARMA_ROOT        = flowerPot(TARMA_ROOT);
    RegistryObject<FlowerPotBlock>        POTTED_WAKEBLOOM         = flowerPot(WAKEBLOOM);
    RegistryObject<TorchBlock>            VINTEUM_TORCH            = BLOCKS.register("vinteum_torch",            () -> new TorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((p_50886_) -> 14).sound(SoundType.WOOD), ParticleTypes.SMOKE));
    RegistryObject<WallTorchBlock>        VINTEUM_WALL_TORCH       = BLOCKS.register("vinteum_wall_torch",       () -> new WallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel((p_50886_) -> 14).sound(SoundType.WOOD), ParticleTypes.SMOKE));

    private static RegistryObject<FlowerPotBlock> flowerPot(RegistryObject<? extends BushBlock> flower) {
        RegistryObject<FlowerPotBlock> register = BLOCKS.register("potted_" + flower.getId().getPath(), () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), flower, FLOWER_POT));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(flower.getId(), register);
        return register;
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> type) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
