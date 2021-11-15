package com.github.minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.api.TriPredicate;

public class PatchouliCompat {
    public static final ResourceLocation CRAFTING_ALTAR = new ResourceLocation(ArsMagicaAPI.MOD_ID, "crafting_altar");

    public static void init() {
        CapStateMatcher capStateMatcher = new CapStateMatcher();
        PatchouliAPI.get().registerMultiblock(CRAFTING_ALTAR, PatchouliAPI.get().makeMultiblock(
                new String[][]{{
                            " C2C ",
                            " 3B1 ",
                            " 3O1 ",
                            " 3B1 ",
                            " C4C "
                        }, {
                            " BMB ",
                            " 6 6 ",
                            "     ",
                            " 5 5 ",
                            " BMB "
                        }, {
                            " BMBI",
                            "     ",
                            "     ",
                            "     ",
                            " BMB "
                        }, {
                            " BMB ",
                            "     ",
                            "     ",
                            "     ",
                            " BMBL"
                        }, {
                            "BBBBB",
                            "BBBBB",
                            "BB0BB",
                            "BBBBB",
                            "BBBBB"
                        }},
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
                )
        );
    }

    private static class CapStateMatcher implements IStateMatcher {
        private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

        private CapStateMatcher() {
            this.predicate = (blockGetter, blockPos, state) -> AltarMaterialManager.instance().getCapMaterial(state.getBlock()).map(
                    AltarCapMaterial::cap).filter(state::is).isPresent();
        }

        @Override
        public BlockState getDisplayedState(int ticks) {
            AltarCapMaterial mat = AltarMaterialManager.instance().getRandomCapMaterial(ticks / 20);
            return mat.cap().defaultBlockState();
        }

        @Override
        public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
            return this.predicate;
        }
    }

    private static class MainBlockStateMatcher implements IStateMatcher {
        private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

        private MainBlockStateMatcher() {
            this.predicate = (blockGetter, blockPos, state) -> AltarMaterialManager.instance().getStructureMaterial(state.getBlock()).map(
                    AltarStructureMaterial::block).filter(state::is).isPresent();
        }

        @Override
        public BlockState getDisplayedState(int ticks) {
            AltarStructureMaterial mat = AltarMaterialManager.instance().getRandomStructureMaterial(ticks / 20);
            return mat.block().defaultBlockState();
        }

        @Override
        public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
            return this.predicate;
        }
    }

    private static class StairBlockStateMatcher implements IStateMatcher {
        private final Direction                                       direction;
        private final Half                                            half;
        private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

        public StairBlockStateMatcher(Direction direction, Half half) {
            this.direction = direction;
            this.half = half;
            this.predicate = (blockGetter, blockPos, state) -> AltarMaterialManager.instance().getStructureMaterial(state.getBlock()).map(AltarStructureMaterial::stair).filter(state::is).isPresent() && state.getValue(
                    StairBlock.FACING) == this.direction && state.getValue(StairBlock.HALF) == this.half;
        }

        @Override
        public BlockState getDisplayedState(int ticks) {
            AltarStructureMaterial mat = AltarMaterialManager.instance().getRandomStructureMaterial(ticks / 20);
            return mat.stair()
                      .defaultBlockState()
                      .setValue(StairBlock.FACING, direction)
                      .setValue(StairBlock.HALF, half);
        }

        @Override
        public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
            return this.predicate;
        }
    }
}
