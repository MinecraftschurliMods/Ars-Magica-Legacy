package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.InlayBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.ICompatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.BiPredicate;

@CompatManager.ModCompat("patchouli")
public class PatchouliCompat implements ICompatHandler {
    public static final ResourceLocation ARCANE_COMPENDIUM               = new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium");
    public static final ResourceLocation SPELL_PART_PAGE                 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_part");
    public static final ResourceLocation CRAFTING_ALTAR                  = new ResourceLocation(ArsMagicaAPI.MOD_ID, "crafting_altar");
    public static final ResourceLocation OBELISK_CHALK                   = new ResourceLocation(ArsMagicaAPI.MOD_ID, "obelisk_chalk");
    public static final ResourceLocation OBELISK_PILLARS                 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "obelisk_pillars");
    public static final ResourceLocation CELESTIAL_PRISM_CHALK           = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_chalk");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR1         = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar1");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR2         = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar2");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR3         = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar3");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR4         = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar4");
    public static final ResourceLocation BLACK_AUREM_CHALK               = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_chalk");
    public static final ResourceLocation BLACK_AUREM_PILLAR1             = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar1");
    public static final ResourceLocation BLACK_AUREM_PILLAR2             = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar2");
    public static final ResourceLocation BLACK_AUREM_PILLAR3             = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar3");
    public static final ResourceLocation BLACK_AUREM_PILLAR4             = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar4");
    public static final ResourceLocation WATER_GUARDIAN_SPAWN_RITUAL     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_guardian_spawn_ritual");
    public static final ResourceLocation FIRE_GUARDIAN_SPAWN_RITUAL      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire_guardian_spawn_ritual");
    public static final ResourceLocation EARTH_GUARDIAN_SPAWN_RITUAL     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth_guardian_spawn_ritual");
    public static final ResourceLocation AIR_GUARDIAN_SPAWN_RITUAL       = new ResourceLocation(ArsMagicaAPI.MOD_ID, "air_guardian_spawn_ritual");
    public static final ResourceLocation ICE_GUARDIAN_SPAWN_RITUAL       = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ice_guardian_spawn_ritual");
    public static final ResourceLocation LIGHTNING_GUARDIAN_SPAWN_RITUAL = new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_guardian_spawn_ritual");
    public static final ResourceLocation LIFE_GUARDIAN_SPAWN_RITUAL      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "life_guardian_spawn_ritual");
    public static final ResourceLocation ARCANE_GUARDIAN_SPAWN_RITUAL    = new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_guardian_spawn_ritual");
    public static final ResourceLocation ENDER_GUARDIAN_SPAWN_RITUAL     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_guardian_spawn_ritual");
    public static final ResourceLocation PURIFICATION_RITUAL             = new ResourceLocation(ArsMagicaAPI.MOD_ID, "purification_ritual");
    public static final ResourceLocation CORRUPTION_RITUAL               = new ResourceLocation(ArsMagicaAPI.MOD_ID, "corruption_ritual");
    private static final String[][] CRAFTING_ALTAR_STRUCTURE = new String[][]{
            {" C2C ", " 3B1 ", " 3O1 ", " 3B1 ", " C4C "},
            {" BMB ", " 6 6 ", "     ", " 5 5 ", " BMB "},
            {" BMBI", "     ", "     ", "     ", " BMB "},
            {" BMB ", "     ", "     ", "     ", " BMBL"},
            {"BBBBB", "BBBBB", "BB0BB", "BBBBB", "BBBBB"}};
    private static final String[][] PILLARS_STRUCTURE = new String[][]{
            {"T   T", "     ", "  U  ", "     ", "T   T"},
            {"P   P", "     ", "  M  ", "     ", "P   P"},
            {"P   P", " CCC ", " C0C ", " CCC ", "P   P"}};
    private static final String[][] OBELISK_CHALK_STRUCTURE = new String[][]{
            {"   ", " U ", "   "},
            {"   ", " M ", "   "},
            {"CCC", "C0C", "CCC"}};
    private static final String[][] CELESTIAL_PRISM_CHALK_STRUCTURE = new String[][]{
            {"   ", " P ", "   "},
            {"CCC", "C0C", "CCC"}};
    private static final String[][] BLACK_AUREM_CHALK_STRUCTURE = new String[][]{
            {"   ", " B ", "   "},
            {"CCC", "C0C", "CCC"}};
    private static final String[][] INLAY_RING = new String[][]{
            {"1N2", "E0E", "3N4"}};
    private static final String[][] FIRE_GUARDIAN_SPAWN_STRUCTURE = new String[][]{
            {"1N2", "E0E", "3N4"},
            {"OOO", "OOO", "OOO"}};
    private static final String[][] EARTH_GUARDIAN_SPAWN_STRUCTURE = new String[][]{
            {"  T  ", " 1N2 ", "TE0ET", " 3N4 ", "  T  "},
            {"AAAAA", "AOOOA", "AOCOA", "AOOOA", "AAAAA"}};
    private static final String[][] LIGHTNING_GUARDIAN_SPAWN_STRUCTURE = new String[][]{
            {"   ", " R ", "   "},
            {"   ", " I ", "   "},
            {"1N2", "E0E", "3N4"}};
    private static final String[][] LIFE_GUARDIAN_SPAWN_STRUCTURE = new String[][]{
            {"   C   ", " WWWWW ", " W   W ", "CW 0 WC", " W   W ", " WWWWW ", "   C   "}};
    private static final String[][] ARCANE_GUARDIAN_SPAWN_STRUCTURE = new String[][]{
            {"B B", "   ", "   ", "   "},
            {"B B", "   ", "   ", "   "},
            {"BLB", "1N2", "E0E", "3N4"}};
    private static final String[][] ENDER_GUARDIAN_SPAWN_STRUCTURE = new String[][]{
            {"  F  ", " 1N2 ", "FE0EF", " 3N4 ", "  F  "},
            {"CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC"}};
    private static final String[][] PURIFICATION_STRUCTURE = new String[][]{
            {"       ", "       ", "       ", "   T   ", "       ", "       ", "       "},
            {"       ", "       ", "       ", "   O   ", "       ", "       ", "       "},
            {"  HHH  ", " CH HC ", "HHH HHH", "H  0  H", "HHH HHH", " CH HC ", "  HHH  "}
    };
    private static final String[][] CORRUPTION_STRUCTURE = new String[][]{
            {"     ", "     ", "     ", "  T  ", "     ", "     ", "     "},
            {"     ", "     ", "     ", "  O  ", "     ", "     ", "     "},
            {" H H ", "HCHCH", "H   H", " H0H ", "H   H", "HCHCH", " H H "}
    };
    private static final String SPELL_PART_TEMPLATE = "{\"components\": [{\"type\": \"patchouli:custom\",\"class\": \"com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.SpellPartPage\",\"part\": \"#part\"}]}";

    public static BiPredicate<Level, BlockPos> getMultiblockMatcher(ResourceLocation location) {
        return (level, pos) -> PatchouliAPI.get().getMultiblock(location).validate(level, pos) != null;
    }

    private IMultiblock makePillarsMultiblock(PatchouliAPI.IPatchouliAPI api, IStateMatcher pillar, IStateMatcher cap, IStateMatcher lower, IStateMatcher middle, IStateMatcher upper, IStateMatcher chalk) {
        return api.makeMultiblock(
                PILLARS_STRUCTURE,
                '0', lower,
                'M', middle,
                'U', upper,
                'P', pillar,
                'T', cap,
                'C', chalk
        ).setSymmetrical(true);
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        IStateMatcher capStateMatcher = new CapStateMatcher();
        IStateMatcher air = api.airMatcher();
        IStateMatcher chalk = api.looseBlockMatcher(AMBlocks.WIZARDS_CHALK.get());
        IStateMatcher obeliskLower = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState(), ObeliskBlock.PART);
        IStateMatcher obeliskMiddle = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE), ObeliskBlock.PART);
        IStateMatcher obeliskUpper = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.UPPER), ObeliskBlock.PART);
        IStateMatcher celestialPrismLower = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState());
        IStateMatcher celestialPrismUpper = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER));
        IStateMatcher blackAurem = api.strictBlockMatcher(AMBlocks.BLACK_AUREM.get());
        IStateMatcher quartzPillar = api.strictBlockMatcher(Blocks.QUARTZ_PILLAR);
        IStateMatcher netherBricks = api.strictBlockMatcher(Blocks.NETHER_BRICKS);
        IStateMatcher goldBlock = api.strictBlockMatcher(Blocks.GOLD_BLOCK);
        IStateMatcher diamondBlock = api.strictBlockMatcher(Blocks.DIAMOND_BLOCK);
        IStateMatcher goldInlayEastWest = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.EAST_WEST));
        IStateMatcher goldInlayNorthSouth = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_SOUTH));
        IStateMatcher goldInlayNorthEast = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_EAST));
        IStateMatcher goldInlayNorthWest = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_WEST));
        IStateMatcher goldInlaySouthEast = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_EAST));
        IStateMatcher goldInlaySouthWest = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_WEST));
        IStateMatcher ironInlayEastWest = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.EAST_WEST));
        IStateMatcher ironInlayNorthSouth = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_SOUTH));
        IStateMatcher ironInlayNorthEast = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_EAST));
        IStateMatcher ironInlayNorthWest = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_WEST));
        IStateMatcher ironInlaySouthEast = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_EAST));
        IStateMatcher ironInlaySouthWest = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_WEST));
        IStateMatcher candle = api.stateMatcher(Blocks.CANDLE.defaultBlockState().setValue(BlockStateProperties.CANDLES, 4).setValue(BlockStateProperties.LIT, true));
        api.registerMultiblock(CRAFTING_ALTAR, api.makeMultiblock(
                CRAFTING_ALTAR_STRUCTURE,
                'L', api.predicateMatcher(Blocks.LECTERN.defaultBlockState().setValue(LecternBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LECTERN) && state.getValue(LecternBlock.FACING) == Direction.SOUTH),
                'I', api.predicateMatcher(Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LEVER) && state.getValue(LeverBlock.FACE) == AttachFace.WALL && state.getValue(LeverBlock.FACING) == Direction.SOUTH),
                'O', api.looseBlockMatcher(AMBlocks.ALTAR_CORE.get()),
                'M', api.strictBlockMatcher(AMBlocks.MAGIC_WALL.get()),
                'B', new MainBlockStateMatcher(),
                'C', capStateMatcher,
                '0', capStateMatcher,
                '1', new StairBlockStateMatcher(Direction.NORTH, Half.BOTTOM),
                '2', new StairBlockStateMatcher(Direction.EAST, Half.BOTTOM),
                '3', new StairBlockStateMatcher(Direction.SOUTH, Half.BOTTOM),
                '4', new StairBlockStateMatcher(Direction.WEST, Half.BOTTOM),
                '5', new StairBlockStateMatcher(Direction.EAST, Half.TOP),
                '6', new StairBlockStateMatcher(Direction.WEST, Half.TOP)
        ));
        api.registerMultiblock(OBELISK_CHALK, api.makeMultiblock(
                OBELISK_CHALK_STRUCTURE,
                '0', obeliskLower,
                'M', obeliskMiddle,
                'U', obeliskUpper,
                'C', chalk
        ).setSymmetrical(true));
        api.registerMultiblock(CELESTIAL_PRISM_CHALK, api.makeMultiblock(
                CELESTIAL_PRISM_CHALK_STRUCTURE,
                'P', celestialPrismUpper,
                '0', celestialPrismLower,
                'C', chalk
        ).setSymmetrical(true));
        api.registerMultiblock(BLACK_AUREM_CHALK, api.makeMultiblock(
                BLACK_AUREM_CHALK_STRUCTURE,
                'B', blackAurem,
                'C', chalk
        ).setSymmetrical(true));
        api.registerMultiblock(OBELISK_PILLARS, makePillarsMultiblock(api, api.strictBlockMatcher(Blocks.STONE_BRICKS), api.strictBlockMatcher(Blocks.CHISELED_STONE_BRICKS), obeliskLower, obeliskMiddle, obeliskUpper, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR1, makePillarsMultiblock(api, quartzPillar, api.strictBlockMatcher(Blocks.GLASS), celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR2, makePillarsMultiblock(api, quartzPillar, goldBlock, celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR3, makePillarsMultiblock(api, quartzPillar, diamondBlock, celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR4, makePillarsMultiblock(api, quartzPillar, api.strictBlockMatcher(AMBlocks.MOONSTONE_BLOCK.get()), celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR1, makePillarsMultiblock(api, netherBricks, api.strictBlockMatcher(AMBlocks.CHIMERITE_BLOCK.get()), air, blackAurem, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR2, makePillarsMultiblock(api, netherBricks, goldBlock, air, blackAurem, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR3, makePillarsMultiblock(api, netherBricks, diamondBlock, air, blackAurem, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR4, makePillarsMultiblock(api, netherBricks, api.strictBlockMatcher(AMBlocks.SUNSTONE_BLOCK.get()), air, blackAurem, air, chalk));
        api.registerMultiblock(WATER_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                INLAY_RING,
                'E', api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.EAST_WEST)),
                'N', api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_SOUTH)),
                '1', api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_EAST)),
                '2', api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_EAST)),
                '3', api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_WEST)),
                '4', api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_WEST))
        ).setSymmetrical(true));
        api.registerMultiblock(FIRE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                FIRE_GUARDIAN_SPAWN_STRUCTURE,
                'E', goldInlayEastWest,
                'N', goldInlayNorthSouth,
                '1', goldInlaySouthEast,
                '2', goldInlayNorthEast,
                '3', goldInlaySouthWest,
                '4', goldInlayNorthWest,
                'O', api.strictBlockMatcher(Blocks.OBSIDIAN)
        ).setSymmetrical(true));
        api.registerMultiblock(EARTH_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                EARTH_GUARDIAN_SPAWN_STRUCTURE,
                'E', ironInlayEastWest,
                'N', ironInlayNorthSouth,
                '1', ironInlaySouthEast,
                '2', ironInlayNorthEast,
                '3', ironInlaySouthWest,
                '4', ironInlayNorthWest,
                'A', api.anyMatcher(),
                'T', api.strictBlockMatcher(AMBlocks.VINTEUM_TORCH.get()),
                'O', api.strictBlockMatcher(Blocks.OBSIDIAN),
                'C', api.strictBlockMatcher(Blocks.CHISELED_STONE_BRICKS)
        ).setSymmetrical(true));
        api.registerMultiblock(AIR_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                INLAY_RING,
                'E', goldInlayEastWest,
                'N', goldInlayNorthSouth,
                '1', goldInlaySouthEast,
                '2', goldInlayNorthEast,
                '3', goldInlaySouthWest,
                '4', goldInlayNorthWest
        ).setSymmetrical(true));
        api.registerMultiblock(ICE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                INLAY_RING,
                'E', ironInlayEastWest,
                'N', ironInlayNorthSouth,
                '1', ironInlaySouthEast,
                '2', ironInlayNorthEast,
                '3', ironInlaySouthWest,
                '4', ironInlayNorthWest
        ).setSymmetrical(true));
        api.registerMultiblock(LIGHTNING_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                LIGHTNING_GUARDIAN_SPAWN_STRUCTURE,
                'E', goldInlayEastWest,
                'N', goldInlayNorthSouth,
                '1', goldInlaySouthEast,
                '2', goldInlayNorthEast,
                '3', goldInlaySouthWest,
                '4', goldInlayNorthWest,
                '0', api.strictBlockMatcher(Blocks.IRON_BARS),
                'I', api.strictBlockMatcher(Blocks.IRON_BARS),
                'R', api.propertyMatcher(Blocks.LIGHTNING_ROD.defaultBlockState(), LightningRodBlock.FACING, LightningRodBlock.WATERLOGGED)
        ).setSymmetrical(true));
        api.registerMultiblock(LIFE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                LIFE_GUARDIAN_SPAWN_STRUCTURE,
                'W', chalk,
                'C', api.stateMatcher(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true))
        ).setSymmetrical(true));
        api.registerMultiblock(ARCANE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                ARCANE_GUARDIAN_SPAWN_STRUCTURE,
                'E', ironInlayEastWest,
                'N', ironInlayNorthSouth,
                '1', ironInlaySouthEast,
                '2', ironInlayNorthEast,
                '3', ironInlaySouthWest,
                '4', ironInlayNorthWest,
                'B', api.strictBlockMatcher(Blocks.BOOKSHELF),
                'L', api.stateMatcher(Blocks.LECTERN.defaultBlockState().setValue(LecternBlock.FACING, Direction.EAST))
        ).setSymmetrical(true));
        api.registerMultiblock(ENDER_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
                ENDER_GUARDIAN_SPAWN_STRUCTURE,
                'E', goldInlayEastWest,
                'N', goldInlayNorthSouth,
                '1', goldInlaySouthEast,
                '2', goldInlayNorthEast,
                '3', goldInlaySouthWest,
                '4', goldInlayNorthWest,
                'C', api.strictBlockMatcher(Blocks.COAL_BLOCK),
                'F', api.strictBlockMatcher(Blocks.FIRE),
                '0', blackAurem
        ).setSymmetrical(true));
        api.registerMultiblock(PURIFICATION_RITUAL, api.makeMultiblock(
                PURIFICATION_STRUCTURE,
                '0', obeliskLower,
                'O', obeliskMiddle,
                'T', obeliskUpper,
                'C', candle,
                'H', chalk
        ).setSymmetrical(true));
        api.registerMultiblock(CORRUPTION_RITUAL, api.makeMultiblock(
                CORRUPTION_STRUCTURE,
                '0', obeliskLower,
                'O', obeliskMiddle,
                'T', obeliskUpper,
                'C', candle,
                'H', chalk
        ));
    }

    static <T> Registry<T> getRegistry(@Nullable BlockGetter blockGetter, ResourceKey<Registry<T>> key) {
        return (blockGetter instanceof Level level ? level.registryAccess() : AMUtil.getRegistryAccess()).registryOrThrow(key);
    }

    @Override
    public void clientInit(FMLClientSetupEvent event) {
        PatchouliAPI.get().registerTemplateAsBuiltin(SPELL_PART_PAGE, () -> new ByteArrayInputStream(SPELL_PART_TEMPLATE.getBytes(StandardCharsets.UTF_8)));
    }
}
