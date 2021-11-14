package com.github.minecraftschurli.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.api.TriPredicate;

import java.util.List;

public class AltarCoreBlockEntity extends BlockEntity {
    public  static final ModelProperty<BlockState> CAMO_STATE = new ModelProperty<>();

    private final ModelDataMap modelData = new ModelDataMap.Builder().withProperty(CAMO_STATE).build();

    @Nullable private AltarStructureMaterial structureMaterial;
    @Nullable private AltarCapMaterial       capMaterial;

    private final BlockPattern MULTIBLOCK = BlockPatternBuilder.start()
     .aisle(
            "BBBBB",
            "BBBBB",
            "BBCBB",
            "BBBBB",
            "BBBBB"
     )
     .aisle(
            "    L",
            "B   B",
            "M   M",
            "B   B",
            "     "
     )
     .aisle(
            "I    ",
            "B   B",
            "M   M",
            "B   B",
            "     "
     )
     .aisle(
            "     ",
            "B6 5B",
            "M   M",
            "B6 5B",
            "     "
     )
     .aisle(
            "     ",
            "C111C",
            "2BOB4",
            "C333C",
            "     "
     )
     .where(' ', blockInWorld -> blockInWorld.getState().isAir())
     .where('L', blockInWorld -> blockInWorld.getState().is(Blocks.LECTERN))
     .where('I', blockInWorld -> blockInWorld.getState().is(Blocks.LEVER))
     .where('O', blockInWorld -> blockInWorld.getState().is(AMBlocks.ALTAR_CORE.get()))
     .where('M', blockInWorld -> blockInWorld.getState().is(AMBlocks.MAGIC_WALL.get()))
     .where('C', blockInWorld -> capMaterial != null && blockInWorld.getState().is(capMaterial.cap()))
     .where('B', blockInWorld -> structureMaterial != null && blockInWorld.getState().is(structureMaterial.block()))
     .where('1', blockInWorld -> checkStair(blockInWorld, 0, Half.BOTTOM))
     .where('2', blockInWorld -> checkStair(blockInWorld, 1, Half.BOTTOM))
     .where('3', blockInWorld -> checkStair(blockInWorld, 2, Half.BOTTOM))
     .where('4', blockInWorld -> checkStair(blockInWorld, 3, Half.BOTTOM))
     .where('5', blockInWorld -> checkStair(blockInWorld, 1, Half.TOP))
     .where('6', blockInWorld -> checkStair(blockInWorld, 3, Half.TOP))
     .build();

    private boolean checkStair(BlockInWorld blockInWorld, int i, Half half) {
        BlockState state = blockInWorld.getState();
        if (this.structureMaterial == null) return false;
        if (!state.is(this.structureMaterial.stair())) return false;
        if (this.direction == null) return false;
        return state.getValue(StairBlock.FACING) == Rotation.values()[i].rotate(this.direction).getOpposite() &&
               state.getValue(StairBlock.HALF) == half &&
               state.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT &&
               !state.getValue(StairBlock.WATERLOGGED);
    }

    private int currentIngredient = -1;
    private int powerLevel        = -1;
    public  int checkCounter;

    @Nullable private BlockPos  lecternPos;
    @Nullable private BlockPos  leverPos;
    @Nullable private BlockPos  viewPos;
    @Nullable private Direction direction;

    public AltarCoreBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.ALTAR_CORE.get(), pWorldPosition, pBlockState);
    }
    
    public void invalidateMultiblock() {
        if (this.viewPos != null && getLevel() != null && getLevel().getBlockState(this.viewPos).is(AMBlocks.ALTAR_VIEW.get())) {
            getLevel().setBlock(this.viewPos, AMBlocks.ALTAR_VIEW.get().defaultBlockState(), Constants.BlockFlags.BLOCK_UPDATE | Constants.BlockFlags.UPDATE_NEIGHBORS);
        }
        this.lecternPos = null;
        this.leverPos = null;
        this.viewPos = null;
        this.direction = null;
        this.currentIngredient = -1;
        this.powerLevel = -1;
        if (getLevel() != null && getBlockState().getValue(AltarCoreBlock.FORMED)) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(AltarCoreBlock.FORMED, false), 2);
        }
        modelData.setData(CAMO_STATE, null);
    }
    
    public boolean checkMultiblock() {
        if (getLevel() == null) return false;
        if (checkMultiblockInt()) {
            if (!getBlockState().getValue(AltarCoreBlock.FORMED)) {
                getLevel().setBlock(getBlockPos(), getBlockState().setValue(AltarCoreBlock.FORMED, true), 2);
            }
            return true;
        }
        invalidateMultiblock();
        return false;
    }

    public void consumeTick() {

    }

    private boolean checkMultiblockInt() {
        if (getLevel() == null) return false;
        AltarMaterialManager manager = AltarMaterialManager.instance();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos relative = getBlockPos().relative(direction, 2).relative(direction.getCounterClockWise(), 2).below(3);
            BlockState blockState = getLevel().getBlockState(relative);
            if (blockState.is(Blocks.LECTERN)) {
                this.direction = direction;
                this.lecternPos = relative;
                this.viewPos = relative.above();
                this.structureMaterial = manager.getStructureMaterial(getLevel().getBlockState(getBlockPos().relative(direction.getClockWise())).getBlock()).orElse(null);
                this.capMaterial = manager.getCapMaterial(getLevel().getBlockState(getBlockPos().relative(direction).relative(direction.getClockWise(), 2)).getBlock()).orElse(null);
                this.leverPos = relative.relative(direction.getClockWise(), 4).above(1);
                this.modelData.setData(CAMO_STATE, getLevel().getBlockState(getBlockPos().relative(this.direction.getClockWise())));
                break;
            }
        }
        if (this.capMaterial == null || this.structureMaterial == null || this.leverPos == null || this.viewPos == null || this.direction == null) return false;
        if (!getLevel().getBlockState(this.leverPos).is(Blocks.LEVER)) return false;
        BlockPattern.BlockPatternMatch x = MULTIBLOCK.matches(getLevel(), getBlockPos().offset(-2, -4, -2), Direction.UP, this.direction);
        if (x == null) return false;
        getLevel().setBlock(this.viewPos, AMBlocks.ALTAR_VIEW.get().defaultBlockState(), 18);
        this.powerLevel = this.structureMaterial.power() + this.capMaterial.power();
        return true;
    }

    @NotNull
    @Override
    public ModelDataMap getModelData() {
        return modelData;
    }

    public boolean isMultiblockFormed() {
        return getBlockState().hasProperty(AltarCoreBlock.FORMED) && getBlockState().getValue(AltarCoreBlock.FORMED);
    }

    public boolean hasEnoughPower() {
        return getRequiredPower() <= this.powerLevel;
    }

    private int getRequiredPower() {
        return getRecipe().size();
    }

    @Nullable
    public ISpellIngredient getCurrentIngredient() {
        return currentIngredient >= 0 && currentIngredient < getRecipe().size() ? getRecipe().get(currentIngredient) : null;
    }

    public boolean isLeverActive() {
        return getLevel() != null && this.leverPos != null && getLevel().getBlockState(this.leverPos).getValue(LeverBlock.POWERED);
    }

    public List<ISpellIngredient> getRecipe() {
        return getRecipeFromBook(getBook());
    }

    public ItemStack getBook() {
        if (this.getLevel() == null || this.lecternPos == null) return ItemStack.EMPTY;
        BlockState lecternState = this.getLevel().getBlockState(this.lecternPos);
        if (lecternState.getBlock() instanceof LecternBlock) {
            if (lecternState.hasProperty(LecternBlock.HAS_BOOK) && lecternState.getValue(LecternBlock.HAS_BOOK)) {
                if (this.getLevel().getBlockEntity(this.lecternPos) instanceof LecternBlockEntity lecternBlockEntity) {
                    return lecternBlockEntity.getBook();
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private static List<ISpellIngredient> getRecipeFromBook(ItemStack book) {
        return Spell.CODEC.decode(NbtOps.INSTANCE, book.getOrCreateTagElement(ArsMagicaAPI.MOD_ID+":spell")).map(Pair::getFirst).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn).recipe();
    }
}
