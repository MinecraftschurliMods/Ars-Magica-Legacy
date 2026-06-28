package at.minecraftschurli.mods.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.InlayBlock;
import at.minecraftschurli.mods.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.neoforged.neoforge.common.Tags;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;

public final class AMMultiblocks {
    public static final Identifier ALTAR = ArsMagicaApi.id("altar");
    public static final Identifier OBELISK_CHALK = ArsMagicaApi.id("obelisk_chalk");
    public static final Identifier OBELISK_PILLARS = ArsMagicaApi.id("obelisk_pillars");
    public static final Identifier PURIFICATION = ArsMagicaApi.id("purification");
    public static final Identifier CELESTIAL_PRISM_CHALK = ArsMagicaApi.id("celestial_prism_chalk");
    public static final Identifier CELESTIAL_PRISM_PILLARS_1 = ArsMagicaApi.id("celestial_prism_pillars_1");
    public static final Identifier CELESTIAL_PRISM_PILLARS_2 = ArsMagicaApi.id("celestial_prism_pillars_2");
    public static final Identifier CELESTIAL_PRISM_PILLARS_3 = ArsMagicaApi.id("celestial_prism_pillars_3");
    public static final Identifier CELESTIAL_PRISM_PILLARS_4 = ArsMagicaApi.id("celestial_prism_pillars_4");
    public static final Identifier CORRUPTION = ArsMagicaApi.id("corruption");
    public static final Identifier BLACK_AUREM_CHALK = ArsMagicaApi.id("black_aurem_chalk");
    public static final Identifier BLACK_AUREM_PILLARS_1 = ArsMagicaApi.id("black_aurem_pillars_1");
    public static final Identifier BLACK_AUREM_PILLARS_2 = ArsMagicaApi.id("black_aurem_pillars_2");
    public static final Identifier BLACK_AUREM_PILLARS_3 = ArsMagicaApi.id("black_aurem_pillars_3");
    public static final Identifier BLACK_AUREM_PILLARS_4 = ArsMagicaApi.id("black_aurem_pillars_4");
    public static final Identifier WATER_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("water_guardian_spawn_ritual");
    public static final Identifier FIRE_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("fire_guardian_spawn_ritual");
    public static final Identifier EARTH_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("earth_guardian_spawn_ritual");
    public static final Identifier AIR_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("air_guardian_spawn_ritual");
    public static final Identifier ICE_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("ice_guardian_spawn_ritual");
    public static final Identifier LIGHTNING_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("lightning_guardian_spawn_ritual");
    public static final Identifier LIFE_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("life_guardian_spawn_ritual");
    public static final Identifier ARCANE_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("arcane_guardian_spawn_ritual");
    public static final Identifier ENDER_GUARDIAN_SPAWN_RITUAL = ArsMagicaApi.id("ender_guardian_spawn_ritual");
    private static final String[][] ALTAR_STRUCTURE = new String[][]{
        {" C2C ", " 3B1 ", " 3O1 ", " 3B1 ", " C4C "},
        {" BMB ", " 6 6 ", "     ", " 5 5 ", " BMB "},
        {" BMBI", "     ", "     ", "     ", " BMB "},
        {" BMB ", "     ", "     ", "     ", " BMBL"},
        {"BBBBB", "BBBBB", "BB0BB", "BBBBB", "BBBBB"}};
    private static final String[][] OBELISK_CHALK_STRUCTURE = new String[][]{
        {"   ", " 2 ", "   "},
        {"   ", " 1 ", "   "},
        {"WWW", "W0W", "WWW"}};
    private static final String[][] PILLARS_STRUCTURE = new String[][]{
        {"T   T", "     ", "  2  ", "     ", "T   T"},
        {"P   P", "     ", "  1  ", "     ", "P   P"},
        {"P   P", " WWW ", " W0W ", " WWW ", "P   P"}};
    private static final String[][] PURIFICATION_STRUCTURE = new String[][]{
        {"       ", "       ", "       ", "   2   ", "       ", "       ", "       "},
        {"       ", "       ", "       ", "   1   ", "       ", "       ", "       "},
        {"  WWW  ", " CW WC ", "WWW WWW", "W  0  W", "WWW WWW", " CW WC ", "  WWW  "}};
    private static final String[][] CELESTIAL_PRISM_CHALK_STRUCTURE = new String[][]{
        {"   ", " 1 ", "   "},
        {"WWW", "W0W", "WWW"}};
    private static final String[][] CORRUPTION_STRUCTURE = new String[][]{
        {"     ", "     ", "     ", "  2  ", "     ", "     ", "     "},
        {"     ", "     ", "     ", "  1  ", "     ", "     ", "     "},
        {" W W ", "WCWCW", "W   W", " W0W ", "W   W", "WCWCW", " W W "}};
    private static final String[][] BLACK_AUREM_CHALK_STRUCTURE = new String[][]{
        {"WWW", "W0W", "WWW"}};
    private static final String[][] WATER_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"1N2", "E0E", "3N4"}};
    private static final String[][] FIRE_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"1N2", "E0E", "3N4"},
        {"OOO", "OOO", "OOO"}};
    private static final String[][] EARTH_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"  T  ", " 1N2 ", "TE0ET", " 3N4 ", "  T  "},
        {"AAAAA", "AOOOA", "AOCOA", "AOOOA", "AAAAA"}};
    private static final String[][] AIR_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"1N2", "E0E", "3N4"}};
    private static final String[][] ICE_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"   ", " P ", "   "},
        {"   ", " S ", "   "},
        {"1N2", "E0E", "3N4"}};
    private static final String[][] LIGHTNING_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"   ", " R ", "   "},
        {"   ", " I ", "   "},
        {"1N2", "E0E", "3N4"}};
    private static final String[][] LIFE_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"   C   ", " WWWWW ", " W   W ", "CW 0 WC", " W   W ", " WWWWW ", "   C   "}};
    private static final String[][] ARCANE_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"B B", "   ", "   ", "   "},
        {"B B", "   ", "   ", "   "},
        {"BLB", "1N2", "E0E", "3N4"}};
    private static final String[][] ENDER_GUARDIAN_SPAWN_RITUAL_STRUCTURE = new String[][]{
        {"  F  ", " 1N2 ", "FE0EF", " 3N4 ", "  F  "},
        {"CCCCC", "CCCCC", "CCCCC", "CCCCC", "CCCCC"}};

    private AMMultiblocks() {
    }

    public static void init() {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        IStateMatcher air = api.airMatcher();
        IStateMatcher chalk = api.looseBlockMatcher(AMBlocks.WIZARDS_CHALK.get());
        IStateMatcher candle = api.propertyMatcher(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true), CandleBlock.LIT);
        IStateMatcher obeliskLower = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState(), ObeliskBlock.PART);
        IStateMatcher obeliskMiddle = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE), ObeliskBlock.PART);
        IStateMatcher obeliskUpper = api.propertyMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.UPPER), ObeliskBlock.PART);
        IStateMatcher celestialPrismLower = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState());
        IStateMatcher celestialPrismUpper = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER));
        IStateMatcher blackAurem = api.strictBlockMatcher(AMBlocks.BLACK_AUREM.get());
        IStateMatcher quartzPillar = api.strictBlockMatcher(Blocks.QUARTZ_PILLAR);
        IStateMatcher netherBricks = api.strictBlockMatcher(Blocks.NETHER_BRICKS);
        IStateMatcher redstoneInlayEastWest = api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.EAST_WEST));
        IStateMatcher redstoneInlayNorthSouth = api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_SOUTH));
        IStateMatcher redstoneInlayNorthEast = api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_EAST));
        IStateMatcher redstoneInlayNorthWest = api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_WEST));
        IStateMatcher redstoneInlaySouthEast = api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_EAST));
        IStateMatcher redstoneInlaySouthWest = api.stateMatcher(AMBlocks.REDSTONE_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_WEST));
        IStateMatcher ironInlayEastWest = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.EAST_WEST));
        IStateMatcher ironInlayNorthSouth = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_SOUTH));
        IStateMatcher ironInlayNorthEast = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_EAST));
        IStateMatcher ironInlayNorthWest = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_WEST));
        IStateMatcher ironInlaySouthEast = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_EAST));
        IStateMatcher ironInlaySouthWest = api.stateMatcher(AMBlocks.IRON_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_WEST));
        IStateMatcher goldInlayEastWest = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.EAST_WEST));
        IStateMatcher goldInlayNorthSouth = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_SOUTH));
        IStateMatcher goldInlayNorthEast = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_EAST));
        IStateMatcher goldInlayNorthWest = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.NORTH_WEST));
        IStateMatcher goldInlaySouthEast = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_EAST));
        IStateMatcher goldInlaySouthWest = api.stateMatcher(AMBlocks.GOLD_INLAY.get().defaultBlockState().setValue(InlayBlock.SHAPE, RailShape.SOUTH_WEST));
        api.registerMultiblock(ALTAR, api.makeMultiblock(ALTAR_STRUCTURE,
            'L', api.predicateMatcher(Blocks.LECTERN.defaultBlockState().setValue(LecternBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LECTERN) && state.getValue(LecternBlock.FACING) == Direction.SOUTH),
            'I', api.predicateMatcher(Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LEVER) && state.getValue(LeverBlock.FACE) == AttachFace.WALL && state.getValue(LeverBlock.FACING) == Direction.SOUTH),
            'O', api.looseBlockMatcher(AMBlocks.ALTAR_CORE.get()),
            'M', api.strictBlockMatcher(AMBlocks.MAGIC_WALL.get()),
            'B', new AltarStateMatcher(),
            'C', new AltarCapStateMatcher(),
            '0', new AltarCapStateMatcher(),
            '1', new AltarStairStateMatcher(Direction.NORTH, Half.BOTTOM),
            '2', new AltarStairStateMatcher(Direction.EAST, Half.BOTTOM),
            '3', new AltarStairStateMatcher(Direction.SOUTH, Half.BOTTOM),
            '4', new AltarStairStateMatcher(Direction.WEST, Half.BOTTOM),
            '5', new AltarStairStateMatcher(Direction.EAST, Half.TOP),
            '6', new AltarStairStateMatcher(Direction.WEST, Half.TOP)
        ));
        api.registerMultiblock(OBELISK_CHALK, api.makeMultiblock(OBELISK_CHALK_STRUCTURE,
            'W', chalk,
            '0', obeliskLower,
            '1', obeliskMiddle,
            '2', obeliskUpper
        ).setSymmetrical(true));
        api.registerMultiblock(OBELISK_PILLARS, makePillarsMultiblock(api, obeliskLower, obeliskMiddle, obeliskUpper, chalk, api.strictBlockMatcher(Blocks.STONE_BRICKS), api.strictBlockMatcher(Blocks.CHISELED_STONE_BRICKS)));
        api.registerMultiblock(PURIFICATION, api.makeMultiblock(PURIFICATION_STRUCTURE,
            'W', chalk,
            'C', candle,
            '0', obeliskLower,
            '1', obeliskMiddle,
            '2', obeliskUpper));
        api.registerMultiblock(CELESTIAL_PRISM_CHALK, api.makeMultiblock(CELESTIAL_PRISM_CHALK_STRUCTURE,
            'W', chalk,
            '0', celestialPrismLower,
            '1', celestialPrismUpper
        ).setSymmetrical(true));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_1, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(AMBlocks.CHIMERITE_BLOCK.get())));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_2, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(Blocks.GOLD_BLOCK)));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_3, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(Blocks.DIAMOND_BLOCK)));
        api.registerMultiblock(CELESTIAL_PRISM_PILLARS_4, makePillarsMultiblock(api, celestialPrismLower, celestialPrismUpper, air, chalk, quartzPillar, api.strictBlockMatcher(AMBlocks.SUNSTONE_BLOCK.get())));
        api.registerMultiblock(CORRUPTION, api.makeMultiblock(CORRUPTION_STRUCTURE,
            'W', chalk,
            'C', candle,
            '0', obeliskLower,
            '1', obeliskMiddle,
            '2', obeliskUpper));
        api.registerMultiblock(BLACK_AUREM_CHALK, api.makeMultiblock(BLACK_AUREM_CHALK_STRUCTURE,
            'W', chalk,
            '0', blackAurem
        ).setSymmetrical(true));
        api.registerMultiblock(BLACK_AUREM_PILLARS_1, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(AMBlocks.CHIMERITE_BLOCK.get())));
        api.registerMultiblock(BLACK_AUREM_PILLARS_2, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(Blocks.GOLD_BLOCK)));
        api.registerMultiblock(BLACK_AUREM_PILLARS_3, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(Blocks.DIAMOND_BLOCK)));
        api.registerMultiblock(BLACK_AUREM_PILLARS_4, makePillarsMultiblock(api, blackAurem, air, air, chalk, netherBricks, api.strictBlockMatcher(AMBlocks.SUNSTONE_BLOCK.get())));
        api.registerMultiblock(WATER_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            WATER_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
            'E', redstoneInlayEastWest,
            'N', redstoneInlayNorthSouth,
            '1', redstoneInlaySouthEast,
            '2', redstoneInlayNorthEast,
            '3', redstoneInlaySouthWest,
            '4', redstoneInlayNorthWest
        ).setSymmetrical(true));
        api.registerMultiblock(FIRE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            FIRE_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
            'E', goldInlayEastWest,
            'N', goldInlayNorthSouth,
            '1', goldInlaySouthEast,
            '2', goldInlayNorthEast,
            '3', goldInlaySouthWest,
            '4', goldInlayNorthWest,
            'O', api.strictBlockMatcher(Blocks.OBSIDIAN)
        ).setSymmetrical(true));
        api.registerMultiblock(EARTH_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            EARTH_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
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
            AIR_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
            'E', goldInlayEastWest,
            'N', goldInlayNorthSouth,
            '1', goldInlaySouthEast,
            '2', goldInlayNorthEast,
            '3', goldInlaySouthWest,
            '4', goldInlayNorthWest
        ).setSymmetrical(true));
        api.registerMultiblock(ICE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            ICE_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
            'E', ironInlayEastWest,
            'N', ironInlayNorthSouth,
            '1', ironInlaySouthEast,
            '2', ironInlayNorthEast,
            '3', ironInlaySouthWest,
            '4', ironInlayNorthWest,
            '0', api.strictBlockMatcher(Blocks.SNOW_BLOCK),
            'S', api.strictBlockMatcher(Blocks.SNOW_BLOCK),
            'P', api.looseBlockMatcher(Blocks.CARVED_PUMPKIN)
        ).setSymmetrical(true));
        api.registerMultiblock(LIGHTNING_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            LIGHTNING_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
            'E', goldInlayEastWest,
            'N', goldInlayNorthSouth,
            '1', goldInlaySouthEast,
            '2', goldInlayNorthEast,
            '3', goldInlaySouthWest,
            '4', goldInlayNorthWest,
            '0', api.tagMatcher(Tags.Blocks.BARS_COPPER),
            'I', api.tagMatcher(Tags.Blocks.BARS_COPPER),
            'R', api.propertyMatcher(Blocks.LIGHTNING_ROD.defaultBlockState(), LightningRodBlock.FACING, LightningRodBlock.WATERLOGGED)
        ).setSymmetrical(true));
        api.registerMultiblock(LIFE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            LIFE_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
            'W', chalk,
            'C', api.stateMatcher(Blocks.CANDLE.defaultBlockState().setValue(CandleBlock.LIT, true))
        ).setSymmetrical(true));
        api.registerMultiblock(ARCANE_GUARDIAN_SPAWN_RITUAL, api.makeMultiblock(
            ARCANE_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
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
            ENDER_GUARDIAN_SPAWN_RITUAL_STRUCTURE,
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
    }

    private static IMultiblock makePillarsMultiblock(PatchouliAPI.IPatchouliAPI api, IStateMatcher lower, IStateMatcher middle, IStateMatcher upper, IStateMatcher chalk, IStateMatcher pillar, IStateMatcher top) {
        return api.makeMultiblock(PILLARS_STRUCTURE,
            '0', lower,
            '1', middle,
            '2', upper,
            'W', chalk,
            'P', pillar,
            'T', top
        ).setSymmetrical(true);
    }
}
