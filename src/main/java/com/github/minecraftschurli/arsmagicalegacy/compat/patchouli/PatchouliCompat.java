package com.github.minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.compat.CompatManager;
import com.github.minecraftschurli.arsmagicalegacy.compat.ICompatHandler;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@CompatManager.ModCompat("patchouli")
public class PatchouliCompat implements ICompatHandler {
    public static final ResourceLocation CRAFTING_ALTAR = new ResourceLocation(ArsMagicaAPI.MOD_ID, "crafting_altar");
    public static final ResourceLocation SPELL_PART_PAGE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_part");

    private static final String[][] MULTIBLOCK_TEMPLATE = new String[][]{
            {" C2C ", " 3B1 ", " 3O1 ", " 3B1 ", " C4C "},
            {" BMB ", " 6 6 ", "     ", " 5 5 ", " BMB "},
            {" BMBI", "     ", "     ", "     ", " BMB "},
            {" BMB ", "     ", "     ", "     ", " BMBL"},
            {"BBBBB", "BBBBB", "BB0BB", "BBBBB", "BBBBB"}
    };
    private static final String SPELL_PART_TEMPLATE = "{\"components\": [{\"type\": \"custom\",\"class\": \"com.github.minecraftschurli.arsmagicalegacy.compat.patchouli.SpellPartPage\",\"part\": \"#part\"}]}";

    public void init(FMLCommonSetupEvent event) {
        CapStateMatcher capStateMatcher = new CapStateMatcher();
        PatchouliAPI.get().registerMultiblock(CRAFTING_ALTAR, PatchouliAPI.get().makeMultiblock(MULTIBLOCK_TEMPLATE,
                'L', PatchouliAPI.get().predicateMatcher(Blocks.LECTERN.defaultBlockState().setValue(LecternBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LECTERN) && state.getValue(LecternBlock.FACING) == Direction.SOUTH),
                'I', PatchouliAPI.get().predicateMatcher(Blocks.LEVER.defaultBlockState().setValue(LeverBlock.FACING, Direction.SOUTH), state -> state.is(Blocks.LEVER) && state.getValue(LeverBlock.FACE) == AttachFace.WALL && state.getValue(LeverBlock.FACING) == Direction.SOUTH),
                'O', PatchouliAPI.get().looseBlockMatcher(AMBlocks.ALTAR_CORE.get()),
                'M', PatchouliAPI.get().looseBlockMatcher(AMBlocks.MAGIC_WALL.get()),
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
    }

    @Override
    public void clientInit(FMLClientSetupEvent event) {
        PatchouliAPI.get().registerTemplateAsBuiltin(SPELL_PART_PAGE, () -> new ByteArrayInputStream(SPELL_PART_TEMPLATE.getBytes(StandardCharsets.UTF_8)));
    }
}
