package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.block.AMFlowerBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.BlackAuremBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.GoldInlayBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.InscriptionTableBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.IronInlayBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.LiquidEtheriumCauldronBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.OcculusBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.RedstoneInlayBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.SpellRuneBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.WakebloomBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.WizardsChalkBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.BlockFamily;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
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
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface AMBlocks {
    BlockSetType WITCHWOOD_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType(ArsMagicaApi.MOD_ID + ":witchwood"));
    WoodType WITCHWOOD_WOOD_TYPE = WoodType.register(new WoodType(ArsMagicaApi.MOD_ID + ":witchwood", WITCHWOOD_BLOCK_SET_TYPE));
    Lazy<BlockFamily> WITCHWOOD_BLOCK_FAMILY = Lazy.of(() -> new BlockFamily.Builder(AMBlocks.WITCHWOOD_PLANKS.get())
        .slab(AMBlocks.WITCHWOOD_SLAB.get())
        .stairs(AMBlocks.WITCHWOOD_STAIRS.get())
        .fence(AMBlocks.WITCHWOOD_FENCE.get())
        .fenceGate(AMBlocks.WITCHWOOD_FENCE_GATE.get())
        .door(AMBlocks.WITCHWOOD_DOOR.get())
        .trapdoor(AMBlocks.WITCHWOOD_TRAPDOOR.get())
        .button(AMBlocks.WITCHWOOD_BUTTON.get())
        .pressurePlate(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get())
        .sign(AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get())
        .getFamily());

    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredBlock<AirBlock>                    SPELL_LIGHT                 = register("spell_light",                 AirBlock::new, copyProperties(Blocks.AIR, p -> p.lightLevel(_ -> 15)));
    DeferredBlock<SpellRuneBlock>              SPELL_RUNE                  = register("spell_rune",                  SpellRuneBlock::new, copyProperties(Blocks.AIR, BlockBehaviour.Properties::noOcclusion));
    DeferredBlock<LiquidBlock>                 LIQUID_ETHERIUM             = register("liquid_etherium",             p -> new LiquidBlock(AMFluids.LIQUID_ETHERIUM.get(), p), properties(p -> p.mapColor(MapColor.CLAY).replaceable().noCollision().strength(100).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY).lightLevel(_ -> 5)));
    DeferredBlock<LiquidEtheriumCauldronBlock> LIQUID_ETHERIUM_CAULDRON    = register("liquid_etherium_cauldron",    LiquidEtheriumCauldronBlock::new, copyProperties(Blocks.CAULDRON, p -> p.lightLevel(_ -> 5)));
    DeferredBlock<OcculusBlock>                OCCULUS                     = register("occulus",                     OcculusBlock::new, properties(p -> p.mapColor(MapColor.COLOR_GRAY).strength(3, 5)));
    DeferredBlock<InscriptionTableBlock>       INSCRIPTION_TABLE           = register("inscription_table",           InscriptionTableBlock::new, properties(p -> p.mapColor(MapColor.WOOD).strength(2).lightLevel(_ -> 1).noOcclusion()));
    DeferredBlock<AltarCoreBlock>              ALTAR_CORE                  = register("altar_core",                  AltarCoreBlock::new, properties(p -> p.mapColor(MapColor.METAL).strength(3)));
    DeferredBlock<TransparentBlock>            MAGIC_WALL                  = register("magic_wall",                  TransparentBlock::new, properties(p -> p.strength(3).noOcclusion().isValidSpawn(Blocks::never).isRedstoneConductor((_, _, _) -> false).isSuffocating((_, _, _) -> false).isViewBlocking((_, _, _) -> false)));
    DeferredBlock<ObeliskBlock>                OBELISK                     = register("obelisk",                     ObeliskBlock::new, copyProperties(Blocks.STONE, p -> p.noOcclusion().lightLevel(state -> state.getValue(ObeliskBlock.LIT) ? 11 : 1)));
    DeferredBlock<CelestialPrismBlock>         CELESTIAL_PRISM             = register("celestial_prism",             CelestialPrismBlock::new, properties(p -> p.requiresCorrectToolForDrops().strength(1.5f, 6).noOcclusion().lightLevel(_ -> 1).emissiveRendering((_, _, _) -> true)));
    DeferredBlock<BlackAuremBlock>             BLACK_AUREM                 = register("black_aurem",                 BlackAuremBlock::new, properties(p -> p.mapColor(MapColor.COLOR_RED).noOcclusion().noCollision().lightLevel(_ -> 2)));
    DeferredBlock<WizardsChalkBlock>           WIZARDS_CHALK               = register("wizards_chalk",               WizardsChalkBlock::new, properties(p -> p.instabreak().noCollision().sound(SoundType.GRAVEL)));
    DeferredBlock<RedstoneInlayBlock>          REDSTONE_INLAY              = register("redstone_inlay",              RedstoneInlayBlock::new, copyProperties(Blocks.RAIL));
    DeferredBlock<IronInlayBlock>              IRON_INLAY                  = register("iron_inlay",                  IronInlayBlock::new, copyProperties(Blocks.RAIL));
    DeferredBlock<GoldInlayBlock>              GOLD_INLAY                  = register("gold_inlay",                  GoldInlayBlock::new, copyProperties(Blocks.RAIL));
    DeferredBlock<TorchBlock>                  VINTEUM_TORCH               = register("vinteum_torch",               p -> new TorchBlock(ParticleTypes.SMOKE, p), copyProperties(Blocks.TORCH));
    DeferredBlock<WallTorchBlock>              VINTEUM_WALL_TORCH          = register("vinteum_wall_torch",          p -> new WallTorchBlock(ParticleTypes.SMOKE, p), copyProperties(Blocks.WALL_TORCH, p -> p.overrideDescription(VINTEUM_TORCH.get().getDescriptionId()).overrideLootTable(VINTEUM_TORCH.get().getLootTable())));
    DeferredBlock<DropExperienceBlock>         CHIMERITE_ORE               = register("chimerite_ore",               p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties(p -> p.mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         DEEPSLATE_CHIMERITE_ORE     = register("deepslate_chimerite_ore",     p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties(p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE)));
    DeferredBlock<Block>                       CHIMERITE_BLOCK             = register("chimerite_block",             properties(p -> p.mapColor(MapColor.COLOR_PINK).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         TOPAZ_ORE                   = register("topaz_ore",                   p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties(p -> p.mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         DEEPSLATE_TOPAZ_ORE         = register("deepslate_topaz_ore",         p -> new DropExperienceBlock(UniformInt.of(0, 2), p), properties(p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE)));
    DeferredBlock<Block>                       TOPAZ_BLOCK                 = register("topaz_block",                 properties(p -> p.mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         VINTEUM_ORE                 = register("vinteum_ore",                 p -> new DropExperienceBlock(UniformInt.of(1, 3), p), properties(p -> p.mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         DEEPSLATE_VINTEUM_ORE       = register("deepslate_vinteum_ore",       p -> new DropExperienceBlock(UniformInt.of(1, 3), p), properties(p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE)));
    DeferredBlock<Block>                       VINTEUM_BLOCK               = register("vinteum_block",               properties(p -> p.mapColor(MapColor.LAPIS).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         MOONSTONE_ORE               = register("moonstone_ore",               p -> new DropExperienceBlock(UniformInt.of(3, 7), p), properties(p -> p.mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         DEEPSLATE_MOONSTONE_ORE     = register("deepslate_moonstone_ore",     p -> new DropExperienceBlock(UniformInt.of(3, 7), p), properties(p -> p.mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5f, 3f).sound(SoundType.DEEPSLATE)));
    DeferredBlock<Block>                       MOONSTONE_BLOCK             = register("moonstone_block",             properties(p -> p.mapColor(MapColor.COLOR_LIGHT_BLUE).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<DropExperienceBlock>         SUNSTONE_ORE                = register("sunstone_ore",                p -> new DropExperienceBlock(UniformInt.of(0, 1), p), properties(p -> p.mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50f, 1200f)));
    DeferredBlock<Block>                       SUNSTONE_BLOCK              = register("sunstone_block",              properties(p -> p.mapColor(MapColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3f, 3f)));
    DeferredBlock<RotatedPillarBlock>          WITCHWOOD_LOG               = register("witchwood_log",               RotatedPillarBlock::new, copyProperties(Blocks.OAK_LOG, p -> p.mapColor(s -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_LIGHT_BLUE : MapColor.TERRACOTTA_BLUE)));
    DeferredBlock<RotatedPillarBlock>          WITCHWOOD_WOOD              = register("witchwood_wood",              RotatedPillarBlock::new, copyProperties(Blocks.OAK_WOOD, p -> p.mapColor(MapColor.TERRACOTTA_BLUE)));
    DeferredBlock<RotatedPillarBlock>          STRIPPED_WITCHWOOD_LOG      = register("stripped_witchwood_log",      RotatedPillarBlock::new, copyProperties(Blocks.STRIPPED_OAK_LOG, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<RotatedPillarBlock>          STRIPPED_WITCHWOOD_WOOD     = register("stripped_witchwood_wood",     RotatedPillarBlock::new, copyProperties(Blocks.STRIPPED_OAK_WOOD, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<LeavesBlock>                 WITCHWOOD_LEAVES            = register("witchwood_leaves",            p -> new UntintedParticleLeavesBlock(0.01f, AMParticles.LEAF.get(), p), copyProperties(Blocks.OAK_LEAVES, p -> p.mapColor(MapColor.QUARTZ)));
    DeferredBlock<SaplingBlock>                WITCHWOOD_SAPLING           = register("witchwood_sapling",           p -> new SaplingBlock(AMWorldgen.WITCHWOOD_TREE_GROWER, p), copyProperties(Blocks.OAK_SAPLING));
    DeferredBlock<FlowerPotBlock>              POTTED_WITCHWOOD_SAPLING    = register("potted_witchwood_sapling",    p -> flowerPot(WITCHWOOD_SAPLING, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<Block>                       WITCHWOOD_PLANKS            = register("witchwood_planks",            copyProperties(Blocks.OAK_PLANKS, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<SlabBlock>                   WITCHWOOD_SLAB              = register("witchwood_slab",              SlabBlock::new, copyProperties(Blocks.OAK_SLAB, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<StairBlock>                  WITCHWOOD_STAIRS            = register("witchwood_stairs",            p -> new StairBlock(WITCHWOOD_PLANKS.get().defaultBlockState(), p), copyProperties(Blocks.OAK_STAIRS, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<FenceBlock>                  WITCHWOOD_FENCE             = register("witchwood_fence",             FenceBlock::new, copyProperties(Blocks.OAK_FENCE, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<FenceGateBlock>              WITCHWOOD_FENCE_GATE        = register("witchwood_fence_gate",        p -> new FenceGateBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_FENCE_GATE, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<DoorBlock>                   WITCHWOOD_DOOR              = register("witchwood_door",              p -> new DoorBlock(WITCHWOOD_BLOCK_SET_TYPE, p), copyProperties(Blocks.OAK_DOOR, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<TrapDoorBlock>               WITCHWOOD_TRAPDOOR          = register("witchwood_trapdoor",          p -> new TrapDoorBlock(WITCHWOOD_BLOCK_SET_TYPE, p), copyProperties(Blocks.OAK_TRAPDOOR, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<ButtonBlock>                 WITCHWOOD_BUTTON            = register("witchwood_button",            p -> new ButtonBlock(WITCHWOOD_BLOCK_SET_TYPE, 30, p), copyProperties(Blocks.OAK_BUTTON));
    DeferredBlock<PressurePlateBlock>          WITCHWOOD_PRESSURE_PLATE    = register("witchwood_pressure_plate",    p -> new PressurePlateBlock(WITCHWOOD_BLOCK_SET_TYPE, p), copyProperties(Blocks.OAK_PRESSURE_PLATE, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<StandingSignBlock>           WITCHWOOD_SIGN              = register("witchwood_sign",              p -> new StandingSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_SIGN, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<WallSignBlock>               WITCHWOOD_WALL_SIGN         = register("witchwood_wall_sign",         p -> new WallSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_WALL_SIGN, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).overrideDescription(WITCHWOOD_SIGN.get().getDescriptionId()).overrideLootTable(WITCHWOOD_SIGN.get().getLootTable())));
    DeferredBlock<CeilingHangingSignBlock>     WITCHWOOD_HANGING_SIGN      = register("witchwood_hanging_sign",      p -> new CeilingHangingSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_HANGING_SIGN, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)));
    DeferredBlock<WallHangingSignBlock>        WITCHWOOD_WALL_HANGING_SIGN = register("witchwood_wall_hanging_sign", p -> new WallHangingSignBlock(WITCHWOOD_WOOD_TYPE, p), copyProperties(Blocks.OAK_WALL_HANGING_SIGN, p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).overrideDescription(WITCHWOOD_HANGING_SIGN.get().getDescriptionId()).overrideLootTable(WITCHWOOD_HANGING_SIGN.get().getLootTable())));
    DeferredBlock<AMFlowerBlock>               AUM                         = register("aum",                         p -> new AMFlowerBlock(AMMobEffects.MANA_REGENERATION, 7, AMTags.Blocks.AUM_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>              POTTED_AUM                  = register("potted_aum",                  p -> flowerPot(AUM, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<AMFlowerBlock>               CERUBLOSSOM                 = register("cerublossom",                 p -> new AMFlowerBlock(MobEffects.LEVITATION, 7, AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>              POTTED_CERUBLOSSOM          = register("potted_cerublossom",          p -> flowerPot(CERUBLOSSOM, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<AMFlowerBlock>               DESERT_NOVA                 = register("desert_nova",                 p -> new AMFlowerBlock(MobEffects.FIRE_RESISTANCE, 7, AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>              POTTED_DESERT_NOVA          = register("potted_desert_nova",          p -> flowerPot(DESERT_NOVA, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<AMFlowerBlock>               TARMA_ROOT                  = register("tarma_root",                  p -> new AMFlowerBlock(MobEffects.SLOWNESS, 7, AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON, p), copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>              POTTED_TARMA_ROOT           = register("potted_tarma_root",           p -> flowerPot(TARMA_ROOT, p).get(), copyProperties(Blocks.FLOWER_POT));
    DeferredBlock<WakebloomBlock>              WAKEBLOOM                   = register("wakebloom",                   WakebloomBlock::new, copyProperties(Blocks.POPPY));
    DeferredBlock<FlowerPotBlock>              POTTED_WAKEBLOOM            = register("potted_wakebloom",            p -> flowerPot(WAKEBLOOM, p).get(), copyProperties(Blocks.FLOWER_POT));
    // @formatter:on

    private static <B extends Block> DeferredBlock<B> register(String name, Function<BlockBehaviour.Properties, B> function, Supplier<BlockBehaviour.Properties> properties) {
        return BLOCKS.registerBlock(name, function, properties);
    }

    private static DeferredBlock<Block> register(String name, Supplier<BlockBehaviour.Properties> properties) {
        return BLOCKS.registerSimpleBlock(name, properties);
    }

    private static Supplier<BlockBehaviour.Properties> properties(UnaryOperator<BlockBehaviour.Properties> operator) {
        return () -> operator.apply(BlockBehaviour.Properties.of());
    }

    private static Supplier<BlockBehaviour.Properties> copyProperties(Block block) {
        return () -> BlockBehaviour.Properties.ofFullCopy(block);
    }

    private static Supplier<BlockBehaviour.Properties> copyProperties(Block block, UnaryOperator<BlockBehaviour.Properties> operator) {
        return () -> operator.apply(BlockBehaviour.Properties.ofFullCopy(block));
    }

    private static Supplier<FlowerPotBlock> flowerPot(DeferredBlock<?> flower, BlockBehaviour.Properties properties) {
        return () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, flower, properties);
    }
}
