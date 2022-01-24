package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.ICompatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.BiPredicate;

@CompatManager.ModCompat("patchouli")
public class PatchouliCompat implements ICompatHandler {
    public static final ResourceLocation SPELL_PART_PAGE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_part");
    public static final ResourceLocation CRAFTING_ALTAR = new ResourceLocation(ArsMagicaAPI.MOD_ID, "crafting_altar");
    public static final ResourceLocation OBELISK_CHALK = new ResourceLocation(ArsMagicaAPI.MOD_ID, "obelisk_chalk");
    public static final ResourceLocation OBELISK_PILLARS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "obelisk_pillars");
    public static final ResourceLocation CELESTIAL_PRISM_CHALK = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_chalk");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR1 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar1");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR2 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar2");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR3 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar3");
    public static final ResourceLocation CELESTIAL_PRISM_PILLAR4 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "celestial_prism_pillar4");
    public static final ResourceLocation BLACK_AUREM_CHALK = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_chalk");
    public static final ResourceLocation BLACK_AUREM_PILLAR1 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar1");
    public static final ResourceLocation BLACK_AUREM_PILLAR2 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar2");
    public static final ResourceLocation BLACK_AUREM_PILLAR3 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar3");
    public static final ResourceLocation BLACK_AUREM_PILLAR4 = new ResourceLocation(ArsMagicaAPI.MOD_ID, "black_aurem_pillar4");
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
    private static final String SPELL_PART_TEMPLATE = "{\"components\": [{\"type\": \"custom\",\"class\": \"com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.SpellPartPage\",\"part\": \"#part\"}]}";

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

    public void init(FMLCommonSetupEvent event) {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.get();
        IStateMatcher capStateMatcher = new CapStateMatcher();
        IStateMatcher chalk = api.looseBlockMatcher(AMBlocks.WIZARDS_CHALK.get());
        IStateMatcher obeliskLower = api.stateMatcher(AMBlocks.OBELISK.get().defaultBlockState());
        IStateMatcher obeliskMiddle = api.stateMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE));
        IStateMatcher obeliskUpper = api.stateMatcher(AMBlocks.OBELISK.get().defaultBlockState().setValue(ObeliskBlock.PART, ObeliskBlock.Part.UPPER));
        IStateMatcher celestialPrismLower = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState());
        IStateMatcher celestialPrismUpper = api.stateMatcher(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER));
        IStateMatcher blackAurem = api.strictBlockMatcher(AMBlocks.BLACK_AUREM.get());
        IStateMatcher quartzPillar = api.strictBlockMatcher(Blocks.QUARTZ_PILLAR);
        IStateMatcher netherBricks = api.strictBlockMatcher(Blocks.NETHER_BRICKS);
        IStateMatcher stoneBricks = api.looseBlockMatcher(Blocks.STONE_BRICKS);
        IStateMatcher chiseledStoneBricks = api.looseBlockMatcher(Blocks.CHISELED_STONE_BRICKS);
        IStateMatcher glass = api.strictBlockMatcher(Blocks.GLASS);
        IStateMatcher chimeriteBlock = api.strictBlockMatcher(AMBlocks.CHIMERITE_BLOCK.get());
        IStateMatcher goldBlock = api.strictBlockMatcher(Blocks.GOLD_BLOCK);
        IStateMatcher diamondBlock = api.strictBlockMatcher(Blocks.DIAMOND_BLOCK);
        IStateMatcher moonstoneBlock = api.strictBlockMatcher(AMBlocks.MOONSTONE_BLOCK.get());
        IStateMatcher sunstoneBlock = api.strictBlockMatcher(AMBlocks.SUNSTONE_BLOCK.get());
        IStateMatcher air = api.airMatcher();
        api.registerMultiblock(CRAFTING_ALTAR, api.makeMultiblock(
                CRAFTING_ALTAR_STRUCTURE,
                'L', api.predicateMatcher(Blocks.LECTERN.defaultBlockState().setValue(LecternBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LECTERN) && state.getValue(LecternBlock.FACING) == Direction.SOUTH),
                'I', api.predicateMatcher(Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LEVER) && state.getValue(LeverBlock.FACE) == AttachFace.WALL && state.getValue(LeverBlock.FACING) == Direction.SOUTH),
                'O', api.looseBlockMatcher(AMBlocks.ALTAR_CORE.get()),
                'M', api.looseBlockMatcher(AMBlocks.MAGIC_WALL.get()),
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
        api.registerMultiblock(OBELISK_PILLARS, makePillarsMultiblock(api, stoneBricks, chiseledStoneBricks, obeliskLower, obeliskMiddle, obeliskUpper, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR1, makePillarsMultiblock(api, quartzPillar, glass, celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR2, makePillarsMultiblock(api, quartzPillar, goldBlock, celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR3, makePillarsMultiblock(api, quartzPillar, diamondBlock, celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(CELESTIAL_PRISM_PILLAR4, makePillarsMultiblock(api, quartzPillar, moonstoneBlock, celestialPrismLower, celestialPrismUpper, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR1, makePillarsMultiblock(api, netherBricks, chimeriteBlock, air, blackAurem, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR2, makePillarsMultiblock(api, netherBricks, goldBlock, air, blackAurem, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR3, makePillarsMultiblock(api, netherBricks, diamondBlock, air, blackAurem, air, chalk));
        api.registerMultiblock(BLACK_AUREM_PILLAR4, makePillarsMultiblock(api, netherBricks, sunstoneBlock, air, blackAurem, air, chalk));
    }

    @Override
    public void clientInit(FMLClientSetupEvent event) {
        PatchouliAPI.get().registerTemplateAsBuiltin(SPELL_PART_PAGE, () -> new ByteArrayInputStream(SPELL_PART_TEMPLATE.getBytes(StandardCharsets.UTF_8)));
    }
}
