package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.InlayBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.OcculusBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.WizardsChalkBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarViewBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower.DesertNovaBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower.TarmaRootBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower.WakebloomBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign.CustomCeilingHangingSignBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign.CustomStandingSignBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign.CustomWallHangingSignBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign.CustomWallSignBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.Optional;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.BLOCKS;

@NonExtendable
public interface AMBlocks {
    BlockBehaviour.Properties FLOWER_POT = BlockBehaviour.Properties.of().instabreak().noOcclusion();

    DeferredBlock<OcculusBlock>            OCCULUS                     = BLOCKS.register("occulus",                            OcculusBlock::new);
    DeferredBlock<InscriptionTableBlock>   INSCRIPTION_TABLE           = BLOCKS.register("inscription_table",                  InscriptionTableBlock::new);
    DeferredBlock<AltarCoreBlock>          ALTAR_CORE                  = BLOCKS.register("altar_core",                         AltarCoreBlock::new);
    DeferredBlock<AltarViewBlock>          ALTAR_VIEW                  = BLOCKS.register("altar_view",                         AltarViewBlock::new);
    DeferredBlock<Block>                   MAGIC_WALL                  = BLOCKS.registerBlock("magic_wall",                    TransparentBlock::new, BlockBehaviour.Properties.of().strength(3F).noOcclusion().noOcclusion().isValidSpawn(AMBlocks::never).isRedstoneConductor(AMBlocks::never).isSuffocating(AMBlocks::never).isViewBlocking(AMBlocks::never));
    DeferredBlock<ObeliskBlock>            OBELISK                     = BLOCKS.register("obelisk",                            ObeliskBlock::new);
    DeferredBlock<CelestialPrismBlock>     CELESTIAL_PRISM             = BLOCKS.register("celestial_prism",                    CelestialPrismBlock::new);
    DeferredBlock<BlackAuremBlock>         BLACK_AUREM                 = BLOCKS.register("black_aurem",                        BlackAuremBlock::new);
    DeferredBlock<WizardsChalkBlock>       WIZARDS_CHALK               = BLOCKS.register("wizards_chalk",                      WizardsChalkBlock::new);
    DeferredBlock<SpellRuneBlock>          SPELL_RUNE                  = BLOCKS.register("spell_rune",                         SpellRuneBlock::new);
    DeferredBlock<Block>                   CHIMERITE_ORE               = BLOCKS.registerSimpleBlock("chimerite_ore",           BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   DEEPSLATE_CHIMERITE_ORE     = BLOCKS.registerSimpleBlock("deepslate_chimerite_ore", BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   CHIMERITE_BLOCK             = BLOCKS.registerSimpleBlock("chimerite_block",         BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   TOPAZ_ORE                   = BLOCKS.registerSimpleBlock("topaz_ore",               BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   DEEPSLATE_TOPAZ_ORE         = BLOCKS.registerSimpleBlock("deepslate_topaz_ore",     BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   TOPAZ_BLOCK                 = BLOCKS.registerSimpleBlock("topaz_block",             BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   VINTEUM_ORE                 = BLOCKS.registerSimpleBlock("vinteum_ore",             BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   DEEPSLATE_VINTEUM_ORE       = BLOCKS.registerSimpleBlock("deepslate_vinteum_ore",   BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F).sound(SoundType.DEEPSLATE));
    DeferredBlock<Block>                   VINTEUM_BLOCK               = BLOCKS.registerSimpleBlock("vinteum_block",           BlockBehaviour.Properties.of().mapColor(MapColor.LAPIS).requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   MOONSTONE_ORE               = BLOCKS.registerSimpleBlock("moonstone_ore",           BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   DEEPSLATE_MOONSTONE_ORE     = BLOCKS.registerSimpleBlock("deepslate_moonstone_ore", BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3F));
    DeferredBlock<Block>                   MOONSTONE_BLOCK             = BLOCKS.registerSimpleBlock("moonstone_block",         BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<Block>                   SUNSTONE_ORE                = BLOCKS.registerSimpleBlock("sunstone_ore",            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50F, 1200F));
    DeferredBlock<Block>                   SUNSTONE_BLOCK              = BLOCKS.registerSimpleBlock("sunstone_block",          BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3F, 3F));
    DeferredBlock<RotatedPillarBlock>      WITCHWOOD_LOG               = BLOCKS.registerBlock("witchwood_log",                 RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(s -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_LIGHT_BLUE : MapColor.TERRACOTTA_BLUE).strength(2F).sound(SoundType.WOOD));
    DeferredBlock<RotatedPillarBlock>      WITCHWOOD                   = BLOCKS.registerBlock("witchwood",                     RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BLUE).strength(2F).sound(SoundType.WOOD));
    DeferredBlock<RotatedPillarBlock>      STRIPPED_WITCHWOOD_LOG      = BLOCKS.registerBlock("stripped_witchwood_log",        RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD));
    DeferredBlock<RotatedPillarBlock>      STRIPPED_WITCHWOOD          = BLOCKS.registerBlock("stripped_witchwood",            RotatedPillarBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD));
    DeferredBlock<LeavesBlock>             WITCHWOOD_LEAVES            = BLOCKS.registerBlock("witchwood_leaves",              LeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((a, b, c, d) -> d == EntityType.OCELOT || d == EntityType.PARROT).isSuffocating(AMBlocks::never).isViewBlocking(AMBlocks::never));
    DeferredBlock<SaplingBlock>            WITCHWOOD_SAPLING           = BLOCKS.register("witchwood_sapling",                  () -> new SaplingBlock(new TreeGrower(ArsMagicaAPI.MOD_ID + ":witchwood", Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "witchwood_tree"))), Optional.empty(), Optional.empty()), BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SAPLING)));
    DeferredBlock<FlowerPotBlock>          POTTED_WITCHWOOD_SAPLING    = flowerPot(WITCHWOOD_SAPLING);
    DeferredBlock<Block>                   WITCHWOOD_PLANKS            = BLOCKS.registerSimpleBlock("witchwood_planks",        BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD));
    DeferredBlock<SlabBlock>               WITCHWOOD_SLAB              = BLOCKS.register("witchwood_slab",                     () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(AMBlocks.WITCHWOOD_PLANKS.get().defaultMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    DeferredBlock<StairBlock>              WITCHWOOD_STAIRS            = BLOCKS.register("witchwood_stairs",                   () -> new StairBlock(() -> AMBlocks.WITCHWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofLegacyCopy(AMBlocks.WITCHWOOD_PLANKS.get())));
    DeferredBlock<FenceBlock>              WITCHWOOD_FENCE             = BLOCKS.register("witchwood_fence",                    () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(AMBlocks.WITCHWOOD_PLANKS.get().defaultMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    DeferredBlock<FenceGateBlock>          WITCHWOOD_FENCE_GATE        = BLOCKS.register("witchwood_fence_gate",               () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(AMBlocks.WITCHWOOD_PLANKS.get().defaultMapColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    DeferredBlock<DoorBlock>               WITCHWOOD_DOOR              = BLOCKS.register("witchwood_door",                     () -> new DoorBlock(AMWoodTypes.WITCHWOOD_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(AMBlocks.WITCHWOOD_PLANKS.get().defaultMapColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    DeferredBlock<TrapDoorBlock>           WITCHWOOD_TRAPDOOR          = BLOCKS.register("witchwood_trapdoor",                 () -> new TrapDoorBlock(AMWoodTypes.WITCHWOOD_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(AMBlocks.WITCHWOOD_PLANKS.get().defaultMapColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(AMBlocks::never)));
    DeferredBlock<ButtonBlock>             WITCHWOOD_BUTTON            = BLOCKS.register("witchwood_button",                   () -> new ButtonBlock(AMWoodTypes.WITCHWOOD_BLOCK_SET_TYPE, 30, BlockBehaviour.Properties.of().noCollission().strength(0.5F).sound(SoundType.WOOD)));
    DeferredBlock<PressurePlateBlock>      WITCHWOOD_PRESSURE_PLATE    = BLOCKS.register("witchwood_pressure_plate",           () -> new PressurePlateBlock(AMWoodTypes.WITCHWOOD_BLOCK_SET_TYPE, BlockBehaviour.Properties.of().mapColor(AMBlocks.WITCHWOOD_PLANKS.get().defaultMapColor()).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    DeferredBlock<StandingSignBlock>       WITCHWOOD_SIGN              = BLOCKS.register("witchwood_sign",                     () -> new CustomStandingSignBlock(AMWoodTypes.WITCHWOOD, BlockBehaviour.Properties.ofLegacyCopy(AMBlocks.WITCHWOOD_PLANKS.get()).noCollission().strength(1.0F)));
    DeferredBlock<WallSignBlock>           WITCHWOOD_WALL_SIGN         = BLOCKS.register("witchwood_wall_sign",                () -> new CustomWallSignBlock(AMWoodTypes.WITCHWOOD, BlockBehaviour.Properties.ofLegacyCopy(AMBlocks.WITCHWOOD_PLANKS.get()).noCollission().strength(1.0F).lootFrom(WITCHWOOD_SIGN)));
    DeferredBlock<CeilingHangingSignBlock> WITCHWOOD_HANGING_SIGN      = BLOCKS.register("witchwood_hanging_sign",             () -> new CustomCeilingHangingSignBlock(AMWoodTypes.WITCHWOOD, BlockBehaviour.Properties.of().mapColor(WITCHWOOD_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.HANGING_SIGN)));
    DeferredBlock<WallHangingSignBlock>    WITCHWOOD_WALL_HANGING_SIGN = BLOCKS.register("witchwood_wall_hanging_sign",        () -> new CustomWallHangingSignBlock(AMWoodTypes.WITCHWOOD, BlockBehaviour.Properties.of().mapColor(WITCHWOOD_LOG.get().defaultMapColor()).noCollission().strength(1.0F).sound(SoundType.HANGING_SIGN).lootFrom(WITCHWOOD_HANGING_SIGN)));
    DeferredBlock<FlowerBlock>             AUM                         = BLOCKS.register("aum",                                () -> new FlowerBlock(AMMobEffects.MANA_REGEN::value, 7, BlockBehaviour.Properties.ofLegacyCopy(Blocks.POPPY)));
    DeferredBlock<FlowerBlock>             CERUBLOSSOM                 = BLOCKS.register("cerublossom",                        () -> new FlowerBlock(() -> MobEffects.LEVITATION, 7, BlockBehaviour.Properties.ofLegacyCopy(Blocks.POPPY)));
    DeferredBlock<FlowerBlock>             DESERT_NOVA                 = BLOCKS.register("desert_nova",                        DesertNovaBlock::new);
    DeferredBlock<FlowerBlock>             TARMA_ROOT                  = BLOCKS.register("tarma_root",                         TarmaRootBlock::new);
    DeferredBlock<FlowerBlock>             WAKEBLOOM                   = BLOCKS.register("wakebloom",                          WakebloomBlock::new);
    DeferredBlock<FlowerPotBlock>          POTTED_AUM                  = flowerPot(AUM);
    DeferredBlock<FlowerPotBlock>          POTTED_CERUBLOSSOM          = flowerPot(CERUBLOSSOM);
    DeferredBlock<FlowerPotBlock>          POTTED_DESERT_NOVA          = flowerPot(DESERT_NOVA);
    DeferredBlock<FlowerPotBlock>          POTTED_TARMA_ROOT           = flowerPot(TARMA_ROOT);
    DeferredBlock<FlowerPotBlock>          POTTED_WAKEBLOOM            = flowerPot(WAKEBLOOM);
    DeferredBlock<TorchBlock>              VINTEUM_TORCH               = BLOCKS.register("vinteum_torch",               () -> new TorchBlock(ParticleTypes.SMOKE, BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50886_) -> 14).sound(SoundType.WOOD)));
    DeferredBlock<WallTorchBlock>          VINTEUM_WALL_TORCH          = BLOCKS.register("vinteum_wall_torch",          () -> new WallTorchBlock(ParticleTypes.SMOKE, BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((p_50886_) -> 14).sound(SoundType.WOOD)));
    DeferredBlock<InlayBlock>              IRON_INLAY                  = BLOCKS.register("iron_inlay",                  () -> new InlayBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.RAIL)));
    DeferredBlock<InlayBlock>              REDSTONE_INLAY              = BLOCKS.register("redstone_inlay",              () -> new InlayBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.RAIL)));
    DeferredBlock<InlayBlock>              GOLD_INLAY                  = BLOCKS.register("gold_inlay",                  () -> new InlayBlock(BlockBehaviour.Properties.ofLegacyCopy(Blocks.RAIL)));
    DeferredBlock<LiquidBlock>             LIQUID_ESSENCE              = BLOCKS.register("liquid_essence",              () -> new LiquidBlock(AMFluids.LIQUID_ESSENCE, BlockBehaviour.Properties.of().noCollission().strength(100.0F).noLootTable().lightLevel((state) -> 5)));

    private static DeferredBlock<FlowerPotBlock> flowerPot(DeferredBlock<? extends BushBlock> flower) {
        DeferredBlock<FlowerPotBlock> register = BLOCKS.register("potted_" + flower.getId().getPath(), () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), flower, FLOWER_POT));
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
