package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
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
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.client.model.data.ModelData;
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
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AltarCoreBlockEntity extends BlockEntity implements IEtheriumConsumer {
    public static final Codec<Set<BlockPos>> SET_OF_POSITIONS_CODEC = CodecHelper.setOf(BlockPos.CODEC);
    public static final ModelProperty<BlockState> CAMO_STATE = new ModelProperty<>();
    public static final String PROVIDERS_KEY = ArsMagicaAPI.MOD_ID + ":bound_providers";
    public static final String RECIPE_KEY = ArsMagicaAPI.MOD_ID + ":recipe";
    public static final String CAMO_KEY = ArsMagicaAPI.MOD_ID + ":camo";
    public static final String ALTAR_POWER_KEY = ArsMagicaAPI.MOD_ID + ":altar_power_key";
    public static final String REQUIRED_POWER_KEY = ArsMagicaAPI.MOD_ID + ":required_power_key";
    private final LazyOptional<IEtheriumConsumer> capHolder = LazyOptional.of(() -> this);
    private BlockState camoState;
    public int checkCounter;
    @Nullable
    private AltarStructureMaterial structureMaterial;
    @Nullable
    private AltarCapMaterial capMaterial;
    private Set<BlockPos> boundPositions = new HashSet<>();
    private Deque<ISpellIngredient> recipe;
    private int requiredPower = 0;
    private boolean isCrafting;
    private int powerLevel = -1;
    @Nullable
    private BlockPos lecternPos;
    @Nullable
    private BlockPos leverPos;
    @Nullable
    private BlockPos viewPos;
    @Nullable
    private Direction direction;
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

    public AltarCoreBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(AMBlockEntities.ALTAR_CORE.get(), pWorldPosition, pBlockState);
    }

    /**
     * @return An unmodifiable list of all bound etherium provider positions.
     */
    public Collection<BlockPos> getBoundPositions() {
        return Collections.unmodifiableCollection(boundPositions);
    }

    private boolean checkStair(BlockInWorld blockInWorld, int i, Half half) {
        BlockState state = blockInWorld.getState();
        if (structureMaterial == null) return false;
        if (!state.is(structureMaterial.stair())) return false;
        if (direction == null) return false;
        return state.getValue(StairBlock.FACING) == Rotation.values()[i].rotate(direction).getOpposite() && state.getValue(StairBlock.HALF) == half && state.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT && !state.getValue(StairBlock.WATERLOGGED);
    }

    /**
     * Invalidates the multiblock.
     */
    public void invalidateMultiblock() {
        if (viewPos != null && getLevel() != null && getLevel().getBlockState(viewPos).is(AMBlocks.ALTAR_VIEW.get())) {
            getLevel().setBlock(viewPos, AMBlocks.ALTAR_VIEW.get().defaultBlockState(), Block.UPDATE_ALL);
        }
        lecternPos = null;
        leverPos = null;
        viewPos = null;
        direction = null;
        powerLevel = -1;
        recipe = null;
        camoState = null;
        sync();
        setChanged();
    }

    /**
     * Checks for a valid multiblock.
     */
    public void checkMultiblock() {
        if (getLevel() == null) return;
        boolean b = checkMultiblockInternal();
        if (!b) {
            invalidateMultiblock();
        }
        if (getBlockState().getValue(AltarCoreBlock.FORMED) != b) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(AltarCoreBlock.FORMED, b), Block.UPDATE_ALL);
        }
    }

    private boolean checkMultiblockInternal() {
        if (getLevel() == null) return false;
        var structureRegistry = getLevel().registryAccess().registryOrThrow(AltarStructureMaterial.REGISTRY_KEY);
        var capRegistry = getLevel().registryAccess().registryOrThrow(AltarCapMaterial.REGISTRY_KEY);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos relative = getBlockPos().relative(direction, 2).relative(direction.getCounterClockWise(), 2).below(3);
            BlockState blockState = getLevel().getBlockState(relative);
            if (blockState.is(Blocks.LECTERN)) {
                this.direction = direction;
                lecternPos = relative;
                viewPos = relative.above();
                Block sBlock = getLevel().getBlockState(getBlockPos().relative(direction.getClockWise())).getBlock();
                structureMaterial = structureRegistry.stream().filter(mat -> mat.block() == sBlock || mat.stair() == sBlock).findFirst().orElse(null);
                Block cBlock = getLevel().getBlockState(getBlockPos().relative(direction).relative(direction.getClockWise(), 2)).getBlock();
                capMaterial = capRegistry.stream().filter(mat -> mat.cap() == cBlock).findFirst().orElse(null);
                leverPos = relative.relative(direction.getClockWise(), 4).above(1);
                camoState = getLevel().getBlockState(getBlockPos().relative(direction.getClockWise()));
                break;
            }
        }
        if (capMaterial == null || structureMaterial == null || leverPos == null || viewPos == null || lecternPos == null || direction == null)
            return false;
        if (!getLevel().getBlockState(leverPos).is(Blocks.LEVER)) return false;
        BlockPos offset = getBlockPos().relative(direction, 2).relative(direction.getClockWise(), 2).below(4);
        BlockPattern.BlockPatternMatch x = MULTIBLOCK.matches(getLevel(), offset, Direction.UP, direction);
        if (x == null) return false;
        getLevel().setBlock(viewPos, AMBlocks.ALTAR_VIEW.get().defaultBlockState(), Block.UPDATE_ALL);
        ((AltarViewBlockEntity) getLevel().getBlockEntity(viewPos)).setAltarPos(getBlockPos());
        if (getLevel().getBlockState(lecternPos).getValue(LecternBlock.HAS_BOOK)) {
            if (recipe == null || recipe.isEmpty()) {
                getRecipe();
            }
        } else {
            recipe = null;
            isCrafting = false;
        }
        powerLevel = structureMaterial.power() + capMaterial.power();
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
        CompoundTag tag = new CompoundTag();
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
        boundPositions = SET_OF_POSITIONS_CODEC.decode(NbtOps.INSTANCE, pTag.get(PROVIDERS_KEY))
                .map(Pair::getFirst)
                .get()
                .mapRight(DataResult.PartialResult::message)
                .ifRight(ArsMagicaLegacy.LOGGER::warn)
                .left()
                .orElse(Set.of());
        recipe = Codec.either(ISpellIngredient.NETWORK_CODEC, ISpellIngredient.CODEC)
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
        powerLevel = pTag.getInt(ALTAR_POWER_KEY);
        requiredPower = pTag.getInt(REQUIRED_POWER_KEY);
        if (pTag.contains(CAMO_KEY)) {
            camoState = BlockState.CODEC.decode(NbtOps.INSTANCE, pTag.get(CAMO_KEY))
                    .map(Pair::getFirst)
                    .get()
                    .mapRight(DataResult.PartialResult::message)
                    .ifRight(ArsMagicaLegacy.LOGGER::warn)
                    .left()
                    .orElse(null);
        }
        super.load(pTag);
    }

    /**
     * Saves the altar to the given tag.
     *
     * @param tag        The tag to save to.
     * @param forNetwork Whether the tag is meant for network transfer or not.
     */
    public void saveAltar(CompoundTag tag, boolean forNetwork) {
        tag.put(PROVIDERS_KEY, SET_OF_POSITIONS_CODEC.encodeStart(NbtOps.INSTANCE, boundPositions).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        tag.put(RECIPE_KEY, (forNetwork ? ISpellIngredient.NETWORK_CODEC : ISpellIngredient.CODEC).listOf().encodeStart(NbtOps.INSTANCE, recipe != null ? new ArrayList<>(recipe) : new ArrayList<>(0)).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        if (camoState != null) {
            tag.put(CAMO_KEY, BlockState.CODEC.encodeStart(NbtOps.INSTANCE, camoState).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
        }
    }

    void consumeTick() {
        Level level = getLevel();
        if (level == null) return;
        if (recipe != null && recipe.isEmpty()) {
            if (isCrafting) {
                finishRecipe();
                recipe = null;
                isCrafting = false;
            }
        } else {
            if (getRecipe() == null) return;
            isCrafting = true;
            BlockPos blockPos = getBlockPos();
            Optional.ofNullable(recipe.poll())
                    .map(ingredient -> ingredient.consume(level, blockPos))
                    .ifPresent(ingredient -> {
                        recipe.offerFirst(ingredient);
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
        ItemEntity entityitem = new ItemEntity(level, blockPos.getX(), blockPos.getY() - 2, blockPos.getZ(), makeSpell());
        entityitem.setPickUpDelay(40);
        entityitem.setExtendedLifetime();
        level.addFreshEntity(entityitem);
        level.playSound(null, blockPos.getX(), blockPos.getY() - 2, blockPos.getZ(), AMSounds.CRAFTING_ALTAR_FINISH.get(), SoundSource.BLOCKS, 1f, 1f);
    }

    private ItemStack makeSpell() {
        ItemStack stack = new ItemStack(AMItems.SPELL.get());
        SpellItem.saveSpell(stack, SpellItem.getSpell(getBook()));
        SpellItem.setSpellName(stack, getBook().getOrCreateTag().getString(WrittenBookItem.TAG_TITLE));
        return stack;
    }

    @Override
    public List<IEtheriumProvider> getBoundProviders() {
        var helper = ArsMagicaAPI.get().getEtheriumHelper();
        Level level = getLevel();
        if (level != null) {
            boundPositions.removeIf(blockPos -> !helper.hasEtheriumProvider(level, blockPos));
            return boundPositions
                    .stream()
                    .map(level::getBlockEntity)
                    .map(helper::getEtheriumProvider)
                    .map(opt -> opt.orElseThrow(() -> new RuntimeException("IEtheriumProvider not present!")))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public void bindProvider(BlockPos pos) {
        if (ArsMagicaAPI.get().getEtheriumHelper().hasEtheriumProvider(getLevel(), pos)) {
            if (boundPositions.contains(pos)) {
                boundPositions.remove(pos);
            } else {
                boundPositions.add(pos);
            }
        }
    }

    @NotNull
    @Override
    public ModelData getModelData() {
        return ModelData.builder().with(CAMO_STATE, camoState).build();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        return EtheriumHelper.instance().getEtheriumConsumerCapability().orEmpty(cap, capHolder);
    }

    /**
     * @return Whether the multiblock is formed or not.
     */
    public boolean isMultiblockFormed() {
        return getBlockState().hasProperty(AltarCoreBlock.FORMED) && getBlockState().getValue(AltarCoreBlock.FORMED);
    }

    /**
     * @return The power level of the altar.
     */
    public int getPowerLevel() {
        return powerLevel;
    }

    /**
     * @return Whether the altar has enough power or not.
     */
    public boolean hasEnoughPower() {
        return getRequiredPower() <= powerLevel;
    }

    private int getRequiredPower() {
        return requiredPower;
    }

    @Nullable
    public ISpellIngredient getCurrentIngredient() {
        return recipe.peekFirst();
    }

    /**
     * @return Whether the altar's lever is down or not.
     */
    public boolean isLeverActive() {
        return getLevel() != null && leverPos != null && getLevel().getBlockState(leverPos).getValue(LeverBlock.POWERED);
    }

    @Nullable
    public Queue<ISpellIngredient> getRecipe() {
        if (recipe == null || recipe.isEmpty()) {
            Optional.of(SpellItem.getSpell(getBook())).filter(ISpell::isValid).filter(((Predicate<ISpell>) ISpell::isEmpty).negate()).ifPresentOrElse(spell -> {
                this.recipe = new ArrayDeque<>(spell.recipe());
                requiredPower = this.recipe.size();
            }, () -> {
                recipe = null;
                requiredPower = 0;
            });
        }
        return recipe;
    }

    /**
     * @return The book in the altar's lectern.
     */
    public ItemStack getBook() {
        if (getLevel() == null || lecternPos == null) return ItemStack.EMPTY;
        BlockState lecternState = getLevel().getBlockState(lecternPos);
        if (lecternState.getBlock() instanceof LecternBlock && lecternState.hasProperty(LecternBlock.HAS_BOOK) && lecternState.getValue(LecternBlock.HAS_BOOK) && getLevel().getBlockEntity(lecternPos) instanceof LecternBlockEntity lecternBlockEntity)
            return lecternBlockEntity.getBook();
        return ItemStack.EMPTY;
    }
}
