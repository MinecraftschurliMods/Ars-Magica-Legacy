package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.WizardsChalkBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks.*;

@SuppressWarnings({"SameParameterValue"})
class AMBlockStateProvider extends BlockStateProvider {
    AMBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        altarCoreBlock(ALTAR_CORE);
        inscriptionTableBlock(INSCRIPTION_TABLE);
        horizontalBlock(OCCULUS.get(), models().getExistingFile(modLoc("block/occulus")));
        airBlock(ALTAR_VIEW);
        simpleBlock(MAGIC_WALL, "translucent");
        obeliskBlock(OBELISK);
        celestialPrismBlock(CELESTIAL_PRISM);
        blackAuremBlock(BLACK_AUREM);
        wizardsChalkBlock(WIZARDS_CHALK);
        simpleBlock(CHIMERITE_ORE);
        simpleBlock(DEEPSLATE_CHIMERITE_ORE);
        simpleBlock(CHIMERITE_BLOCK);
        simpleBlock(TOPAZ_ORE);
        simpleBlock(DEEPSLATE_TOPAZ_ORE);
        simpleBlock(TOPAZ_BLOCK);
        simpleBlock(VINTEUM_ORE);
        simpleBlock(DEEPSLATE_VINTEUM_ORE);
        simpleBlock(VINTEUM_BLOCK);
        simpleBlock(MOONSTONE_ORE);
        simpleBlock(DEEPSLATE_MOONSTONE_ORE);
        simpleBlock(MOONSTONE_BLOCK);
        simpleBlock(SUNSTONE_ORE);
        simpleBlock(SUNSTONE_BLOCK);
        logBlock(WITCHWOOD_LOG);
        woodBlock(WITCHWOOD, WITCHWOOD_LOG);
        logBlock(STRIPPED_WITCHWOOD_LOG);
        woodBlock(STRIPPED_WITCHWOOD, STRIPPED_WITCHWOOD_LOG);
        simpleBlock(WITCHWOOD_LEAVES);
        crossBlock(WITCHWOOD_SAPLING);
        flowerPotBlock(POTTED_WITCHWOOD_SAPLING, WITCHWOOD_SAPLING);
        simpleBlock(WITCHWOOD_PLANKS);
        slabBlock(WITCHWOOD_SLAB, WITCHWOOD_PLANKS);
        stairsBlock(WITCHWOOD_STAIRS, WITCHWOOD_PLANKS);
        fenceBlock(WITCHWOOD_FENCE, WITCHWOOD_PLANKS);
        fenceGateBlock(WITCHWOOD_FENCE_GATE, WITCHWOOD_PLANKS);
        doorBlock(WITCHWOOD_DOOR);
        trapdoorBlock(WITCHWOOD_TRAPDOOR);
        buttonBlock(WITCHWOOD_BUTTON, WITCHWOOD_PLANKS);
        pressurePlateBlock(WITCHWOOD_PRESSURE_PLATE, WITCHWOOD_PLANKS);
        crossBlock(AUM);
        crossBlock(CERUBLOSSOM);
        crossBlock(DESERT_NOVA);
        crossBlock(TARMA_ROOT);
        crossBlock(WAKEBLOOM);
        flowerPotBlock(POTTED_AUM, AUM);
        flowerPotBlock(POTTED_CERUBLOSSOM, CERUBLOSSOM);
        flowerPotBlock(POTTED_DESERT_NOVA, DESERT_NOVA);
        flowerPotBlock(POTTED_TARMA_ROOT, TARMA_ROOT);
        flowerPotBlock(POTTED_WAKEBLOOM, WAKEBLOOM);
        torchBlock(VINTEUM_TORCH, VINTEUM_WALL_TORCH);
        spellRuneBlock(SPELL_RUNE);
        railBlock(IRON_INLAY);
        railBlock(REDSTONE_INLAY);
        railBlock(GOLD_INLAY);
        simpleBlock(LIQUID_ESSENCE.get(), models().getBuilder(LIQUID_ESSENCE.getId().getPath()).texture("particle", modLoc("block/" + LIQUID_ESSENCE.getId().getPath() + "_still")));
    }

    /**
     * Adds a simple block model that uses its block id as the texture name on all six sides.
     *
     * @param block The block to generate the model for.
     */
    private void simpleBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
    }

    /**
     * Adds a simple block model that uses its block id as the texture name on all six sides.
     *
     * @param block The block to generate the model for.
     */
    private void simpleBlock(Supplier<? extends Block> block, String renderType) {
        simpleBlock(block.get(), modelFile -> new ConfiguredModel[]{ new ConfiguredModel(((BlockModelBuilder) modelFile).renderType(renderType))});
    }

    /**
     * Adds a block model that uses block/air as the parent.
     *
     * @param block The block to generate the model for.
     */
    private void airBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get(), models().getExistingFile(mcLoc("block/air")));
    }

    /**
     * Adds a rotated block model that uses the block id as the side texture,
     * and block id + "_top" as the top texture. Rotates accordingly.
     *
     * @param block The block to generate the model for.
     */
    private void logBlock(Supplier<? extends RotatedPillarBlock> block) {
        logBlock(block.get());
    }

    /**
     * Adds a rotated block model that uses its block id as the texture name on all six sides. Rotates accordingly.
     *
     * @param block The block to generate the model for.
     * @param log   The corresponding log block.
     */
    private void woodBlock(RegistryObject<? extends RotatedPillarBlock> block, Supplier<? extends Block> log) {
        axisBlock(block.get(),
                models().cubeColumn(block.getId().getPath(), blockTexture(log.get()), blockTexture(log.get())),
                models().cubeColumnHorizontal(block.getId().getPath(), blockTexture(log.get()), blockTexture(log.get())));
    }

    /**
     * Adds a cross block model, as seen on flowers and saplings. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void crossBlock(RegistryObject<? extends Block> block) {
        simpleBlock(block.get(), models().cross(block.getId().getPath(), blockTexture(block.get())).renderType("cutout"));
    }

    /**
     * Adds a flower pot model with a flower inside.
     *
     * @param pot   The flower pot block to generate the model for.
     * @param plant The plant to place inside the flower pot.
     */
    private void flowerPotBlock(RegistryObject<? extends Block> pot, Supplier<? extends Block> plant) {
        simpleBlock(pot.get(), models().withExistingParent(pot.getId().getPath(), "block/flower_pot_cross").texture("plant", blockTexture(plant.get())).renderType("cutout"));
    }

    /**
     * Adds a slab model.
     *
     * @param slab  The block to generate the model for.
     * @param block The block to take the texture from.
     */
    private void slabBlock(Supplier<? extends SlabBlock> slab, Supplier<? extends Block> block) {
        slabBlock(slab.get(), cubeAll(block.get()).getLocation(), blockTexture(block.get()));
    }

    /**
     * Adds a stair model.
     *
     * @param stairs The block to generate the model for.
     * @param block  The block to take the texture from.
     */
    private void stairsBlock(Supplier<? extends StairBlock> stairs, Supplier<? extends Block> block) {
        stairsBlock(stairs.get(), blockTexture(block.get()));
    }

    /**
     * Adds a fence model.
     *
     * @param fence The block to generate the model for.
     * @param block The block to take the texture from.
     */
    private void fenceBlock(RegistryObject<? extends FenceBlock> fence, Supplier<? extends Block> block) {
        fenceBlock(fence.get(), blockTexture(block.get()));
        models().fenceInventory(fence.getId().getPath() + "_inventory", blockTexture(block.get()));
    }

    /**
     * Adds a fence gate model.
     *
     * @param fenceGate The block to generate the model for.
     * @param block     The block to take the texture from.
     */
    private void fenceGateBlock(Supplier<? extends FenceGateBlock> fenceGate, Supplier<? extends Block> block) {
        fenceGateBlock(fenceGate.get(), blockTexture(block.get()));
    }

    /**
     * Adds a door block model.
     *
     * @param block The block to generate the model for.
     */
    private void doorBlock(RegistryObject<? extends DoorBlock> block) {
        doorBlockWithRenderType(block.get(), block.getId().getPath().replace("_door", ""), modLoc("block/" + block.getId().getPath() + "_bottom"), modLoc("block/" + block.getId().getPath() + "_top"), "cutout");
    }

    /**
     * Adds a trapdoor block model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void trapdoorBlock(Supplier<? extends TrapDoorBlock> block) {
        trapdoorBlockWithRenderType(block.get(), blockTexture(block.get()), true, "cutout");
    }

    /**
     * Adds a button model.
     *
     * @param button The block to generate the model for.
     * @param block  The block to take the texture from.
     */
    private void buttonBlock(RegistryObject<? extends ButtonBlock> button, Supplier<? extends Block> block) {
        ResourceLocation texture = blockTexture(block.get());
        ModelFile normal = models().withExistingParent(button.getId().getPath(), "block/button").texture("texture", texture);
        ModelFile pressed = models().withExistingParent(button.getId().getPath() + "_pressed", "block/button_pressed").texture("texture", texture);
        getVariantBuilder(button.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.POWERED) ? pressed : normal);
            return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case NORTH -> switch (state.getValue(BlockStateProperties.ATTACH_FACE)) {
                    case FLOOR -> builder.build();
                    case CEILING -> builder.rotationX(180).rotationY(180).build();
                    case WALL -> builder.rotationX(90).uvLock(true).build();
                };
                case EAST -> switch (state.getValue(BlockStateProperties.ATTACH_FACE)) {
                    case FLOOR -> builder.rotationY(90).build();
                    case CEILING -> builder.rotationX(180).rotationY(270).build();
                    case WALL -> builder.rotationX(90).rotationY(90).uvLock(true).build();
                };
                case SOUTH -> switch (state.getValue(BlockStateProperties.ATTACH_FACE)) {
                    case FLOOR -> builder.rotationY(180).build();
                    case CEILING -> builder.rotationX(180).build();
                    case WALL -> builder.rotationX(90).rotationY(180).uvLock(true).build();
                };
                case WEST -> switch (state.getValue(BlockStateProperties.ATTACH_FACE)) {
                    case FLOOR -> builder.rotationY(270).build();
                    case CEILING -> builder.rotationX(180).rotationY(90).build();
                    case WALL -> builder.rotationX(90).rotationY(270).uvLock(true).build();
                };
                default -> new ConfiguredModel[0];
            };
        });
        models().withExistingParent(button.getId().getPath() + "_inventory", "block/button_inventory").texture("texture", texture);
    }

    /**
     * Adds a pressure plate model.
     *
     * @param pressurePlate The block to generate the model for.
     * @param block         The block to take the texture from.
     */
    private void pressurePlateBlock(RegistryObject<? extends PressurePlateBlock> pressurePlate, Supplier<? extends Block> block) {
        ResourceLocation texture = blockTexture(block.get());
        ModelFile up = models().withExistingParent(pressurePlate.getId().getPath(), "block/pressure_plate_up").texture("texture", texture);
        ModelFile down = models().withExistingParent(pressurePlate.getId().getPath() + "_down", "block/pressure_plate_down").texture("texture", texture);
        getVariantBuilder(pressurePlate.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.POWERED) ? down : up).build());
    }

    /**
     * Adds a torch/wall torch model. Uses the normal torch block id as the texture name.
     *
     * @param torch     The TorchBlock to generate the model for.
     * @param wallTorch The WallTorchBlock to generate the model for.
     */
    private void torchBlock(RegistryObject<? extends TorchBlock> torch, RegistryObject<? extends WallTorchBlock> wallTorch) {
        ModelFile file = models().withExistingParent(torch.getId().getPath(), "block/template_torch").texture("torch", modLoc("block/" + torch.getId().getPath())).renderType("cutout");
        ModelFile wallFile = models().withExistingParent(wallTorch.getId().getPath(), "block/template_torch_wall").texture("torch", modLoc("block/" + torch.getId().getPath())).renderType("cutout");
        getVariantBuilder(torch.get()).partialState().setModels(ConfiguredModel.builder().modelFile(file).build());
        getVariantBuilder(wallTorch.get()).forAllStates(state -> switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case EAST -> ConfiguredModel.builder().modelFile(wallFile).build();
            case SOUTH -> ConfiguredModel.builder().modelFile(wallFile).rotationY(90).build();
            case WEST -> ConfiguredModel.builder().modelFile(wallFile).rotationY(180).build();
            case NORTH -> ConfiguredModel.builder().modelFile(wallFile).rotationY(270).build();
            default -> new ConfiguredModel[0];
        });
    }

    /**
     * Adds a rail model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void railBlock(RegistryObject<? extends BaseRailBlock> block) {
        ResourceLocation texture = blockTexture(block.get());
        ModelFile straight = models().withExistingParent(block.getId().getPath(), mcLoc("block/rail")).texture("rail", texture).renderType("cutout");
        ModelFile curved = models().withExistingParent(block.getId().getPath() + "_corner", mcLoc("block/rail_curved")).texture("rail", new ResourceLocation(texture.getNamespace(), texture.getPath() + "_corner")).renderType("cutout");
        ModelFile raisedNE = models().withExistingParent(block.getId().getPath() + "_raised_ne", mcLoc("block/template_rail_raised_ne")).texture("rail", texture).renderType("cutout");
        ModelFile raisedSW = models().withExistingParent(block.getId().getPath() + "_raised_sw", mcLoc("block/template_rail_raised_sw")).texture("rail", texture).renderType("cutout");
        getVariantBuilder(block.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            //noinspection deprecation
            return switch (state.getValue(block.get().getShapeProperty())) {
                case NORTH_SOUTH -> builder.modelFile(straight).build();
                case EAST_WEST -> builder.modelFile(straight).rotationY(90).build();
                case SOUTH_EAST -> builder.modelFile(curved).build();
                case SOUTH_WEST -> builder.modelFile(curved).rotationY(90).build();
                case NORTH_WEST -> builder.modelFile(curved).rotationY(180).build();
                case NORTH_EAST -> builder.modelFile(curved).rotationY(270).build();
                case ASCENDING_NORTH -> builder.modelFile(raisedNE).build();
                case ASCENDING_EAST -> builder.modelFile(raisedNE).rotationY(90).build();
                case ASCENDING_SOUTH -> builder.modelFile(raisedSW).build();
                case ASCENDING_WEST -> builder.modelFile(raisedSW).rotationY(90).build();
            };
        });
    }

    /**
     * Adds an altar core model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void altarCoreBlock(RegistryObject<? extends AltarCoreBlock> block) {
        String texture = block.getId().getPath();
        getVariantBuilder(block.get())
                .partialState().with(AltarCoreBlock.FORMED, false).modelForState().modelFile(((BlockModelBuilder) cubeAll(block.get())).renderType("translucent")).addModel()
                .partialState().with(AltarCoreBlock.FORMED, true).modelForState().modelFile(models().getBuilder(texture + "_overlay")
                        .texture("particle", "block/" + texture)
                        .texture("overlay", "block/" + texture + "_overlay")
                        .parent(models().getExistingFile(new ResourceLocation("block/block")))
                        .element().from(0, 0, 0).to(16, 0, 16).face(Direction.DOWN).texture("#overlay")
                        .end().end().renderType("translucent")
                ).addModel();
    }

    /**
     * Adds an obelisk model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void obeliskBlock(RegistryObject<? extends ObeliskBlock> block) {
        String texture = block.getId().getPath();
        getVariantBuilder(block.get()).forAllStates(state -> {
            if (state.getValue(ObeliskBlock.PART) == ObeliskBlock.Part.LOWER) {
                return ConfiguredModel.builder().modelFile(models().getBuilder(texture + (state.getValue(AbstractFurnaceBlock.LIT) ? "_lit" : ""))
                        .parent(models().getExistingFile(new ResourceLocation("forge", "item/default")))
                        .customLoader(ObjModelBuilder::begin)
                        .modelLocation(modLoc("models/block/obj/" + texture + ".obj"))
                        .emissiveAmbient(false)
                        .automaticCulling(false)
                        .shadeQuads(false)
                        .end()
                        .texture("tex", state.getValue(AbstractFurnaceBlock.LIT) ? modLoc("block/" + texture + "_lit") : blockTexture(block.get()))
                        .texture("particle", mcLoc("block/stone_bricks"))
                ).rotationY((state.getValue(AbstractFurnaceBlock.FACING).get2DDataValue() + 2) % 4 * 90).build();
            } else {
                return ConfiguredModel.builder().modelFile(models().getBuilder(texture + "_upper").texture("particle", mcLoc("block/stone_bricks"))).build();
            }
        });
    }

    /**
     * Adds a celestial prism model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void celestialPrismBlock(RegistryObject<? extends CelestialPrismBlock> block) {
        String texture = block.getId().getPath();
        getVariantBuilder(block.get()).forAllStates(state -> {
            if (state.getValue(CelestialPrismBlock.HALF) == DoubleBlockHalf.LOWER) {
                return ConfiguredModel.builder().modelFile(models().getBuilder(texture)
                        .parent(models().getExistingFile(new ResourceLocation("forge", "item/default")))
                        .customLoader(ObjModelBuilder::begin)
                        .modelLocation(modLoc("models/block/obj/" + texture + ".obj"))
                        .end()
                        .texture("tex", blockTexture(block.get()))
                        .texture("particle", blockTexture(block.get()))
                ).build();
            } else {
                return ConfiguredModel.builder().modelFile(models().getBuilder(texture + "_upper").texture("particle", blockTexture(block.get()))).build();
            }
        });
    }

    /**
     * Adds a black aurem model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void blackAuremBlock(RegistryObject<? extends BlackAuremBlock> block) {
        getVariantBuilder(block.get()).partialState().setModels(ConfiguredModel.builder().modelFile(models().getBuilder(block.getId().getPath()).texture("particle", blockTexture(block.get()))).build());
    }

    /**
     * Adds a wizard's chalk model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void wizardsChalkBlock(RegistryObject<? extends WizardsChalkBlock> block) {
        ModelFile[] models = new ModelFile[16];
        for (int i = 0; i < models.length; i++) {
            models[i] = models().withExistingParent(block.getId().getPath() + "_" + i, "block/rail_flat").texture("rail", new ResourceLocation(block.getId().getNamespace(), "block/" + block.getId().getPath() + "_" + i)).renderType("cutout");
        }
        getVariantBuilder(block.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(models[state.getValue(WizardsChalkBlock.VARIANT)]);
            return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case NORTH -> builder.build();
                case EAST -> builder.rotationY(90).build();
                case SOUTH -> builder.rotationY(180).build();
                case WEST -> builder.rotationY(270).build();
                default -> new ConfiguredModel[0];
            };
        });
    }

    /**
     * Adds a spell rune model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void spellRuneBlock(RegistryObject<? extends SpellRuneBlock> block) {
        String texture = block.getId().getPath();
        getVariantBuilder(block.get()).forAllStates(state -> {
            Direction face = state.getValue(SpellRuneBlock.FACE);
            AABB shape = SpellRuneBlock.COLLISION_SHAPES.get(face).bounds();
            return ConfiguredModel.builder().modelFile(models().getBuilder(texture + "_" + face.getName().toLowerCase())
                    .parent(models().getExistingFile(new ResourceLocation("block/block")))
                    .texture("texture", blockTexture(block.get()))
                    .element()
                    .from((float) shape.minX * 16f, (float) shape.minY * 16f, (float) shape.minZ * 16f)
                    .to((float) shape.maxX * 16f, (float) shape.maxY * 16f, (float) shape.maxZ * 16f)
                    .face(face.getOpposite())
                    .texture("#texture")
                    .end()
                    .end()
            ).build();
        });
    }

    /**
     * Adds an inscription table block state definition.
     * @param block The block to generate the block state definition for.
     */
    private void inscriptionTableBlock(RegistryObject<? extends InscriptionTableBlock> block) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get());
        for (Direction facing : InscriptionTableBlock.FACING.getPossibleValues()) {
            for (InscriptionTableBlock.Half half : InscriptionTableBlock.HALF.getPossibleValues()) {
                for (int tier : InscriptionTableBlock.TIER.getPossibleValues()) {
                    if (tier == 1 && half == InscriptionTableBlock.Half.LEFT) continue;
                    ModelFile.ExistingModelFile existingFile = models().getExistingFile(modLoc("block/inscription_table_" + half.getSerializedName() + (tier > 0 ? tier : "")));
                    MultiPartBlockStateBuilder.PartBuilder partBuilder = builder.part().rotationY((int) facing.toYRot()).modelFile(existingFile).addModel();
                    partBuilder.condition(InscriptionTableBlock.FACING, facing);
                    partBuilder.condition(InscriptionTableBlock.HALF, half);
                    if (tier > 0) {
                        ArrayList<Integer> ints = new ArrayList<>();
                        for (int i = 3; i >= tier; i--) {
                            ints.add(i);
                        }
                        partBuilder.condition(InscriptionTableBlock.TIER, ints.toArray(new Integer[0]));
                    }
                }
            }
        }
    }
}
