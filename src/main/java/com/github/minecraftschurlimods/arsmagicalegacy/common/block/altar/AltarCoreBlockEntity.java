package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.network.BEClientSyncPacket;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AltarCoreBlockEntity extends BlockEntity implements IEtheriumConsumer {
    public static final Codec<Set<BlockPos>>      SET_OF_POSITIONS_CODEC = CodecHelper.setOf(BlockPos.CODEC);
    public static final ModelProperty<BlockState> CAMO_STATE             = new ModelProperty<>();

    public static final String PROVIDERS_KEY      = ArsMagicaAPI.MOD_ID + ":bound_providers";
    public static final String RECIPE_KEY         = ArsMagicaAPI.MOD_ID + ":recipe";
    public static final String CAMO_KEY           = ArsMagicaAPI.MOD_ID + ":camo";
    public static final String ALTAR_POWER_KEY    = ArsMagicaAPI.MOD_ID + ":altar_power_key";
    public static final String REQUIRED_POWER_KEY = ArsMagicaAPI.MOD_ID + ":required_power_key";

    private final ModelDataMap modelData = new ModelDataMap.Builder().withProperty(CAMO_STATE).build();

    private AltarStructureMaterial structureMaterial;
    private AltarCapMaterial       capMaterial;

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

    public Collection<BlockPos> getBoundPositions() {
        return Collections.unmodifiableCollection(boundPositions);
    }

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

    private Set<BlockPos>           boundPositions = new HashSet<>();
    private Deque<ISpellIngredient> recipe;

    private int     requiredPower = 0;
    private boolean isCrafting;
    private int     powerLevel = -1;
    public  int     checkCounter;

    private BlockPos  lecternPos;
    private BlockPos  leverPos;
    private BlockPos  viewPos;
    private Direction direction;

    public AltarCoreBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.ALTAR_CORE.get(), pWorldPosition, pBlockState);
    }

    public void invalidateMultiblock() {
        if (this.viewPos != null &&
            getLevel() != null &&
            getLevel().getBlockState(this.viewPos).is(AMBlocks.ALTAR_VIEW.get())) {
            getLevel().setBlock(this.viewPos,
                                AMBlocks.ALTAR_VIEW.get().defaultBlockState(),
                                Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_CLIENTS);
        }
        this.lecternPos = null;
        this.leverPos = null;
        this.viewPos = null;
        this.direction = null;
        this.powerLevel = -1;
        this.recipe = null;
        this.modelData.setData(CAMO_STATE, null);
        sync();
        setChanged();
    }

    public void checkMultiblock() {
        if (getLevel() == null) return;
        boolean b = checkMultiblockInt();
        if (!b) {
            invalidateMultiblock();
        }
        if (getBlockState().getValue(AltarCoreBlock.FORMED) != b) {
            getLevel().setBlock(getBlockPos(),
                                getBlockState().setValue(AltarCoreBlock.FORMED, b),
                                Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_CLIENTS);
        }
    }

    private boolean checkMultiblockInt() {
        if (getLevel() == null) return false;

        AltarMaterialManager manager = AltarMaterialManager.instance();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos relative = getBlockPos().relative(direction, 2)
                                             .relative(direction.getCounterClockWise(), 2)
                                             .below(3);
            BlockState blockState = getLevel().getBlockState(relative);
            if (blockState.is(Blocks.LECTERN)) {
                this.direction = direction;
                this.lecternPos = relative;
                this.viewPos = relative.above();
                this.structureMaterial = manager.getStructureMaterial(getLevel().getBlockState(
                        getBlockPos().relative(direction.getClockWise())
                ).getBlock()).orElse(null);
                this.capMaterial = manager.getCapMaterial(getLevel().getBlockState(
                        getBlockPos().relative(direction).relative(direction.getClockWise(), 2)
                ).getBlock()).orElse(null);
                this.leverPos = relative.relative(direction.getClockWise(), 4).above(1);
                this.modelData.setData(
                        CAMO_STATE,
                        getLevel().getBlockState(getBlockPos().relative(this.direction.getClockWise()))
                );
                break;
            }
        }

        if (this.capMaterial == null ||
            this.structureMaterial == null ||
            this.leverPos == null ||
            this.viewPos == null ||
            this.lecternPos == null ||
            this.direction == null) return false;

        if (!getLevel().getBlockState(this.leverPos).is(Blocks.LEVER)) return false;

        BlockPos offset = getBlockPos().relative(this.direction, 2).relative(this.direction.getClockWise(), 2).below(4);
        BlockPattern.BlockPatternMatch x = MULTIBLOCK.matches(getLevel(), offset, Direction.UP, this.direction);

        if (x == null) return false;

        getLevel().setBlock(this.viewPos,
                            AMBlocks.ALTAR_VIEW.get().defaultBlockState(),
                            Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_CLIENTS);
        ((AltarViewBlockEntity) getLevel().getBlockEntity(this.viewPos)).setAltarPos(getBlockPos());

        if (getLevel().getBlockState(this.lecternPos).getValue(LecternBlock.HAS_BOOK)) {
            if (this.recipe == null || this.recipe.isEmpty()) {
                getRecipe();
            }
        } else {
            this.recipe = null;
            this.isCrafting = false;
        }

        this.powerLevel = this.structureMaterial.power() + this.capMaterial.power();

        sync();
        setChanged();
        return true;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAltar(tag, true);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        saveAltar(tag, false);
    }

    @Override
    public void load(CompoundTag pTag) {
        this.boundPositions = SET_OF_POSITIONS_CODEC.decode(NbtOps.INSTANCE, pTag.get(PROVIDERS_KEY))
                                                    .map(Pair::getFirst)
                                                    .get()
                                                    .mapRight(DataResult.PartialResult::message)
                                                    .ifRight(ArsMagicaLegacy.LOGGER::warn)
                                                    .left()
                                                    .orElse(Set.of());
        this.recipe = Codec.either(ISpellIngredient.NETWORK_CODEC, ISpellIngredient.CODEC)
                           .xmap(i -> i.map(Function.identity(), Function.identity()), Either::right)
                           .listOf()
                           .decode(NbtOps.INSTANCE, pTag.get(RECIPE_KEY))
                           .map(Pair::getFirst)
                           .get()
                           .mapRight(DataResult.PartialResult::message)
                           .ifRight(ArsMagicaLegacy.LOGGER::warn)
                           .left()
                           .map(ArrayDeque::new)
                           .orElse(null);
        this.powerLevel = pTag.getInt(ALTAR_POWER_KEY);
        this.requiredPower = pTag.getInt(REQUIRED_POWER_KEY);
        if (pTag.contains(CAMO_KEY)) {
            this.modelData.setData(CAMO_STATE, BlockState.CODEC.decode(NbtOps.INSTANCE, pTag.get(CAMO_KEY))
                                                               .map(Pair::getFirst)
                                                               .get()
                                                               .mapRight(DataResult.PartialResult::message)
                                                               .ifRight(ArsMagicaLegacy.LOGGER::warn)
                                                               .left()
                                                               .orElse(null));
        }
        super.load(pTag);
    }

    public void saveAltar(CompoundTag tag, boolean forNetwork) {
        tag.put(PROVIDERS_KEY, SET_OF_POSITIONS_CODEC.encodeStart(NbtOps.INSTANCE, this.boundPositions)
                                                     .getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        tag.put(RECIPE_KEY, (forNetwork ? ISpellIngredient.NETWORK_CODEC : ISpellIngredient.CODEC).listOf()
                                                                                                  .encodeStart(NbtOps.INSTANCE,
                                                                                                               this.recipe != null
                                                                                                                       ? new ArrayList<>(this.recipe)
                                                                                                                       : new ArrayList<>(0))
                                                                                                  .getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        if (this.modelData.getData(CAMO_STATE) != null) {
            tag.put(CAMO_KEY, BlockState.CODEC.encodeStart(NbtOps.INSTANCE, this.modelData.getData(CAMO_STATE))
                                              .getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
    }

    void consumeTick() {
        Level level = getLevel();
        if (level == null) return;
        if (this.recipe != null && this.recipe.isEmpty()) {
            if (this.isCrafting) {
                finishRecipe();
                this.recipe = null;
                this.isCrafting = false;
            }
        } else {
            if (getRecipe() == null) return;
            this.isCrafting = true;
            BlockPos blockPos = getBlockPos();
            Optional.ofNullable(this.recipe.poll())
                    .map(ingredient -> ingredient.consume(level, blockPos))
                    .ifPresent(ingredient -> {
                        this.recipe.offerFirst(ingredient);
                        sync();
                        setChanged();
                    });
        }
    }

    private void sync() {
        if (getLevel() != null && !getLevel().isClientSide()) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToAllTracking(new BEClientSyncPacket(this), getLevel(), getBlockPos());
        }
    }

    private void finishRecipe() {
        Level level = getLevel();
        if (level == null) return;
        BlockPos blockPos = getBlockPos();
        ItemEntity entityitem = new ItemEntity(level,
                                               blockPos.getX(),
                                               blockPos.getY() - 2,
                                               blockPos.getZ(),
                                               makeSpell());
        entityitem.setPickUpDelay(40);
        entityitem.setExtendedLifetime();
        level.addFreshEntity(entityitem);
    }

    private ItemStack makeSpell() {
        ItemStack stack = new ItemStack(AMItems.SPELL.get());
        SpellItem.saveSpell(stack, SpellItem.getSpell(getBook()));
        SpellItem.setSpellName(stack, getBook().getOrCreateTag().getString(WrittenBookItem.TAG_TITLE));
        return stack;
    }

    @Override
    public List<IEtheriumProvider> getBoundProviders() {
        Level level = getLevel();
        if (level != null) {
            this.boundPositions.removeIf(blockPos -> !ArsMagicaAPI.get().getEtheriumHelper().hasEtheriumProvider(level, blockPos));
            return this.boundPositions.stream()
                                      .map(level::getBlockEntity)
                                      .map(ArsMagicaAPI.get().getEtheriumHelper()::getEtheriumProvider)
                                      .map(opt -> opt.orElseThrow(() -> new RuntimeException("IEtheriumProvider not present!")))
                                      .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public void bindProvider(BlockPos pos) {
        if (ArsMagicaAPI.get().getEtheriumHelper().hasEtheriumProvider(getLevel(), pos)) {
            if (this.boundPositions.contains(pos)) {
                this.boundPositions.remove(pos);
            } else {
                this.boundPositions.add(pos);
            }
        }
    }

    @Override
    public ModelDataMap getModelData() {
        return modelData;
    }

    private final LazyOptional<IEtheriumConsumer> capHolder = LazyOptional.of(() -> this);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        return EtheriumHelper.instance().getEtheriumConsumerCapability().orEmpty(cap, capHolder);
    }

    public boolean isMultiblockFormed() {
        return getBlockState().hasProperty(AltarCoreBlock.FORMED) && getBlockState().getValue(AltarCoreBlock.FORMED);
    }

    public boolean hasEnoughPower() {
        return getRequiredPower() <= this.powerLevel;
    }

    private int getRequiredPower() {
        return requiredPower;
    }

    @Nullable
    public ISpellIngredient getCurrentIngredient() {
        return this.recipe.peekFirst();
    }

    public boolean isLeverActive() {
        return getLevel() != null &&
               this.leverPos != null &&
               getLevel().getBlockState(this.leverPos).getValue(LeverBlock.POWERED);
    }

    public Queue<ISpellIngredient> getRecipe() {
        if (this.recipe == null || this.recipe.isEmpty()) {
            Optional.of(SpellItem.getSpell(getBook())).filter(Spell::isValid).filter(((Predicate<Spell>)Spell::isEmpty).negate()).ifPresentOrElse(spell -> {
                List<ISpellIngredient> recipe = spell.recipe();
                this.recipe = new ArrayDeque<>(recipe);
                this.requiredPower = spell.parts().size();
            }, () -> {
                this.recipe = null;
                this.requiredPower = 0;
            });
        }
        return this.recipe;
    }

    public ItemStack getBook() {
        if (this.getLevel() == null || this.lecternPos == null) return ItemStack.EMPTY;
        BlockState lecternState = this.getLevel().getBlockState(this.lecternPos);
        if (lecternState.getBlock() instanceof LecternBlock &&
            lecternState.hasProperty(LecternBlock.HAS_BOOK) &&
            lecternState.getValue(LecternBlock.HAS_BOOK) &&
            this.getLevel().getBlockEntity(this.lecternPos) instanceof LecternBlockEntity lecternBlockEntity)
            return lecternBlockEntity.getBook();
        return ItemStack.EMPTY;
    }
}
