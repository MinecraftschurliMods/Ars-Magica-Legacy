package com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMCapabilities;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.AltarMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.network.LecternSyncPacket;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.model.data.ModelData;
import net.neoforged.neoforge.model.data.ModelProperty;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedSet;
import java.util.Set;

@SuppressWarnings("DataFlowIssue")
public class AltarCoreBlockEntity extends AMBlockEntity<AltarCoreBlockEntity.Data> implements EtheriumHandler {
    public static final ModelProperty<BlockState> CAMO = new ModelProperty<>();
    private final SequencedSet<BlockPos> etheriumProviders = new LinkedHashSet<>();
    private final Map<ResourceKey<EtheriumType>, Integer> etherium = new HashMap<>();
    private int checkCounter = 0;
    @Nullable
    private Direction direction;
    @Nullable
    private BlockPos lecternPos;
    @Nullable
    private BlockPos leverPos;
    @Nullable
    private AltarMaterial material;
    @Nullable
    private AltarCapMaterial capMaterial;
    @Nullable
    private BlockState camo;
    private int power = 0;
    private int currentIngredient = 0;
    private Spell spell = Spell.EMPTY;
    @Nullable
    private List<SpellIngredient> recipe;
    private final BlockPattern pattern = BlockPatternBuilder.start()
        .aisle("BBBBB", "BBBBB", "BBCBB", "BBBBB", "BBBBB")
        .aisle("    L", "B   B", "M   M", "B   B", "     ")
        .aisle("I    ", "B   B", "M   M", "B   B", "     ")
        .aisle("     ", "B6 5B", "M   M", "B6 5B", "     ")
        .aisle("     ", "C111C", "2BOB4", "C333C", "     ")
        .where(' ', block -> block.getState().isAir())
        .where('L', block -> block.getState().is(Blocks.LECTERN))
        .where('I', block -> block.getState().is(Blocks.LEVER))
        .where('O', block -> block.getState().is(AMBlocks.ALTAR_CORE.get()))
        .where('M', block -> block.getState().is(AMBlocks.MAGIC_WALL.get()))
        .where('C', block -> capMaterial != null && block.getState().is(capMaterial.block()))
        .where('B', block -> material != null && block.getState().is(material.block()))
        .where('1', block -> checkStair(block, Rotation.NONE, Half.BOTTOM))
        .where('2', block -> checkStair(block, Rotation.CLOCKWISE_90, Half.BOTTOM))
        .where('3', block -> checkStair(block, Rotation.CLOCKWISE_180, Half.BOTTOM))
        .where('4', block -> checkStair(block, Rotation.COUNTERCLOCKWISE_90, Half.BOTTOM))
        .where('5', block -> checkStair(block, Rotation.CLOCKWISE_90, Half.TOP))
        .where('6', block -> checkStair(block, Rotation.COUNTERCLOCKWISE_90, Half.TOP))
        .build();

    public AltarCoreBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.ALTAR_CORE.get(), pos, state, Data.CODEC);
    }

    private boolean checkStair(BlockInWorld block, Rotation rotation, Half half) {
        BlockState state = block.getState();
        if (material == null) return false;
        if (!state.is(material.stair())) return false;
        if (direction == null) return false;
        return state.getValue(StairBlock.FACING) == rotation.rotate(direction).getOpposite() && state.getValue(StairBlock.HALF) == half && state.getValue(StairBlock.SHAPE) == StairsShape.STRAIGHT && !state.getValue(StairBlock.WATERLOGGED);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        checkCounter--;
        if (checkCounter <= 0) {
            checkCounter = AMServerConfig.ALTAR_CHECK_INTERVAL.get();
            boolean multiblock = checkMultiblock();
            BlockState lectern = lecternPos == null ? null : level.getBlockState(lecternPos);
            if (!multiblock || lectern == null || !lectern.is(Blocks.LECTERN)) {
                direction = null;
                lecternPos = null;
                leverPos = null;
                material = null;
                capMaterial = null;
                camo = null;
                power = 0;
                currentIngredient = 0;
                spell = Spell.EMPTY;
                recipe = null;
                setChanged();
            }
            if (state.getValue(AltarCoreBlock.FORMED) != multiblock) {
                level.setBlockAndUpdate(pos, state.setValue(AltarCoreBlock.FORMED, multiblock));
            }
            requestModelDataUpdate();
        } else {
            checkRecipe();
        }
        if (!state.getValue(AltarCoreBlock.FORMED) || spell.isEmpty() || recipe == null) return;
        if (currentIngredient >= recipe.size()) {
            currentIngredient = 0;
        }
        SpellIngredient ingredient = getCurrentIngredient();
        if (ingredient == null) return;
        if (ingredient instanceof EtheriumSpellIngredient etheriumIngredient) {
            BlockState lever = level.getBlockState(leverPos);
            if (!lever.hasProperty(LeverBlock.POWERED) || !lever.getValue(LeverBlock.POWERED)) return;
            Optional<Holder<EtheriumType>> etheriumType = etheriumIngredient.etheriumType();
            etheriumProviders.stream()
                .map(p -> level.getCapability(AMCapabilities.BLOCK_ETHERIUM, p, null))
                .filter(Objects::nonNull)
                .filter(e -> etheriumType.isEmpty() || e.getEtheriumTypes().stream().anyMatch(p -> etheriumType.get().is(p.getKey())))
                .forEach(e -> {
                    for (Holder<EtheriumType> type : getEtheriumTypes()) {
                        etherium.put(type.getKey(), etherium.getOrDefault(type.getKey(), 0) + power - e.subtractAmount(type, power));
                    }
                });
            setChanged();
        }
        if (!ingredient.consume(level, pos)) return;
        currentIngredient++;
        setChanged();
        if (currentIngredient < recipe.size()) return;
        currentIngredient = 0;
        setChanged();
        if (level.isClientSide()) return;
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() - 1.5, pos.getZ() + 0.5, AMUtil.set(AMItems.SPELL.toStack(), AMDataComponents.SPELL.get(), spell), 0, 0.2, 0);
        entity.setPickUpDelay(40);
        entity.setExtendedLifetime();
        level.addFreshEntity(entity);
        level.playSound(null, pos.getX(), pos.getY() - 2, pos.getZ(), AMSounds.SPELLCRAFTING_FINISH.get(), SoundSource.BLOCKS, 1, 1);
    }

    private boolean checkMultiblock() {
        Registry<AltarCapMaterial> capMaterialRegistry = AMRegistries.altarCapMaterials(level.registryAccess());
        Registry<AltarMaterial> materialRegistry = AMRegistries.altarMaterials(level.registryAccess());
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos pos = getBlockPos().relative(direction, 2).relative(direction.getCounterClockWise(), 2).below(3);
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.LECTERN)) {
                this.direction = direction;
                lecternPos = pos;
                leverPos = pos.relative(direction.getClockWise(), 4).above(1);
                Block block = level.getBlockState(getBlockPos().relative(direction.getClockWise())).getBlock();
                material = materialRegistry.stream().filter(m -> block == m.block()).findFirst().orElse(null);
                Block capBlock = level.getBlockState(getBlockPos().relative(direction).relative(direction.getClockWise(), 2)).getBlock();
                capMaterial = capMaterialRegistry.stream().filter(m -> capBlock == m.block()).findFirst().orElse(null);
                break;
            }
        }
        if (lecternPos == null || leverPos == null || material == null || capMaterial == null || direction == null) return false;
        if (!level.getBlockState(lecternPos).is(Blocks.LECTERN) || !level.getBlockState(leverPos).is(Blocks.LEVER)) return false;
        if (pattern.matches(level, getBlockPos().relative(direction, 2).relative(direction.getClockWise(), 2).below(4), Direction.UP, direction) == null) return false;
        camo = material.block().defaultBlockState();
        power = material.power() + capMaterial.power();
        checkRecipe();
        setChanged();
        return true;
    }

    private void checkRecipe() {
        if (lecternPos == null || !(level.getBlockEntity(lecternPos) instanceof LecternBlockEntity lectern)) return;
        ItemStack stack = lectern.getBook();
        if (level instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, ChunkPos.containing(lecternPos), new LecternSyncPacket(lecternPos, stack));
        }
        spell = stack.has(AMDataComponents.SPELL) ? stack.get(AMDataComponents.SPELL) : Spell.EMPTY;
        SpellHelper helper = ArsMagicaApi.spellHelper();
        RegistryAccess registryAccess = level.registryAccess();
        recipe = helper.getFlatRecipe(spell, registryAccess).size() <= power ? helper.getRecipe(spell, registryAccess) : null;
        if (recipe == null || spell.isEmpty()) {
            currentIngredient = 0;
        }
        setChanged();
    }

    @Override
    public void fromData(Data data) {
        camo = data.camo.orElse(null);
        power = data.power;
        currentIngredient = data.current;
        spell = data.spell;
        etheriumProviders.clear();
        etheriumProviders.addAll(data.providers);
        etherium.clear();
        etherium.putAll(data.etherium);
    }

    @Override
    public Data toData() {
        return new Data(Optional.ofNullable(camo), power, currentIngredient, spell, List.copyOf(etheriumProviders), etherium);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        if (level != null) {
            checkMultiblock();
        }
        requestModelDataUpdate();
    }

    @Override
    public ModelData getModelData() {
        return !getBlockState().getValue(AltarCoreBlock.FORMED) || camo == null ? ModelData.EMPTY : ModelData.builder().with(CAMO, camo).build();
    }

    @Override
    public void addConnectedPosition(BlockPos pos) {
        etheriumProviders.add(pos);
        setChanged();
    }

    @Override
    public void removeConnectedPosition(BlockPos pos) {
        etheriumProviders.remove(pos);
        setChanged();
    }

    @Override
    public List<Holder<EtheriumType>> getEtheriumTypes() {
        return AMRegistries.etheriumTypes(level.registryAccess())
            .listElements()
            .map(e -> (Holder<EtheriumType>) e)
            .toList();
    }

    @Override
    public int getAmount(Holder<EtheriumType> type) {
        return Math.min(etherium.getOrDefault(type.getKey(), 0), getMaxAmount(type));
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return getCurrentIngredient() instanceof EtheriumSpellIngredient(Optional<Holder<EtheriumType>> etheriumType, int count) && (etheriumType.isEmpty() || etheriumType.get().is(type.getKey())) ? count : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        etherium.put(type.getKey(), amount);
        setChanged();
    }

    @Override
    public int addAmount(Holder<EtheriumType> type, int amount) {
        int min = Math.min(etherium.getOrDefault(type.getKey(), getMaxAmount(type)), amount);
        etherium.put(type.getKey(), etherium.get(type.getKey()) - min);
        setChanged();
        return amount - min;
    }

    @Override
    public int subtractAmount(Holder<EtheriumType> type, int amount) {
        return amount;
    }

    @Override
    public AABB getOutline(Level level, BlockPos pos, BlockState state) {
        return AABB.unitCubeFromLowerCorner(Vec3.ZERO);
    }

    @Override
    public int getOutlineColor(Level level, BlockPos pos, BlockState state) {
        return 0xffffff;
    }

    @Override
    public boolean canHaveConnectedPositions() {
        return true;
    }

    @Override
    public SequencedSet<BlockPos> getConnectedPositions() {
        return etheriumProviders;
    }

    @Nullable
    public BlockPos getLecternPos() {
        return lecternPos;
    }

    @Nullable
    public SpellIngredient getCurrentIngredient() {
        return hasRecipe() && recipe.size() > currentIngredient ? recipe.get(currentIngredient) : null;
    }

    public int getPower() {
        return power;
    }

    public boolean hasRecipe() {
        return recipe != null && !recipe.isEmpty();
    }

    public boolean consumeEtherium(EtheriumSpellIngredient ingredient) {
        if (ingredient.etheriumType().isPresent()) {
            int count = etherium.getOrDefault(ingredient.etheriumType().get().getKey(), 0);
            if (count < ingredient.count()) return false;
            etherium.put(ingredient.etheriumType().get().getKey(), count - ingredient.count());
        } else {
            int count = etherium.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
            if (count < ingredient.count()) return false;
            Set<ResourceKey<EtheriumType>> set = new HashSet<>();
            while (count > 0) {
                set.clear();
                int decrease = Math.min(count / etherium.size(), etherium.values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .min()
                    .orElse(0));
                for (Map.Entry<ResourceKey<EtheriumType>, Integer> entry : etherium.entrySet()) {
                    int value = entry.getValue();
                    if (value > decrease) {
                        count -= decrease;
                        etherium.compute(entry.getKey(), (_, v) -> v - decrease);
                    } else {
                        count -= value;
                        set.add(entry.getKey());
                    }
                }
                for (ResourceKey<EtheriumType> key : set) {
                    etherium.remove(key);
                }
            }
        }
        setChanged();
        return true;
    }

    public record Data(Optional<BlockState> camo, int power, int current, Spell spell, List<BlockPos> providers, Map<ResourceKey<EtheriumType>, Integer> etherium) {
        private static final Codec<Data> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.optionalFieldOf("camo").forGetter(Data::camo),
            Codec.INT.fieldOf("power").forGetter(Data::power),
            Codec.INT.fieldOf("current").forGetter(Data::current),
            Spell.CODEC.optionalFieldOf("spell", Spell.EMPTY).forGetter(Data::spell),
            BlockPos.CODEC.listOf().optionalFieldOf("providers", List.of()).forGetter(Data::providers),
            Codec.unboundedMap(ResourceKey.codec(AMRegistries.Keys.ETHERIUM_TYPE), Codec.INT).optionalFieldOf("etherium", Map.of()).forGetter(Data::etherium)
        ).apply(inst, Data::new));
    }
}
