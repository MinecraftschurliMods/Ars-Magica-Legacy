package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.WizardsChalkBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks.*;

@SuppressWarnings({"ConstantConditions", "SameParameterValue"})
class AMBlockStateProvider extends BlockStateProvider {
    AMBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ALTAR_CORE.get())
                .partialState().with(AltarCoreBlock.FORMED, false).modelForState().modelFile(
                        cubeAll(ALTAR_CORE.get())
                ).addModel()
                .partialState().with(AltarCoreBlock.FORMED, true).modelForState().modelFile(
                        models().getBuilder("altar_core_overlay")
                                .texture("particle", "block/altar_core")
                                .texture("overlay", "block/altar_core_overlay")
                                .parent(models().getExistingFile(new ResourceLocation("block/block")))
                                .element()
                                .from(0, 0, 0)
                                .to(16, 0, 16)
                                .face(Direction.DOWN)
                                .texture("#overlay")
                                .end()
                                .end()
                ).addModel();
        airBlock(ALTAR_VIEW);
        simpleBlock(MAGIC_WALL);
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
        simpleBlock(WITCHWOOD_PLANKS);
        slabBlock(WITCHWOOD_SLAB, WITCHWOOD_PLANKS);
        stairsBlock(WITCHWOOD_STAIRS, WITCHWOOD_PLANKS);
        fenceBlock(WITCHWOOD_FENCE, WITCHWOOD_PLANKS);
        fenceGateBlock(WITCHWOOD_FENCE_GATE, WITCHWOOD_PLANKS);
        doorBlock(WITCHWOOD_DOOR, "witchwood");
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
        flowerPotBlock(POTTED_WITCHWOOD_SAPLING, WITCHWOOD_SAPLING);
        torchBlock(VINTEUM_TORCH, VINTEUM_WALL_TORCH);
        wizardsChalkBlock(WIZARDS_CHALK);
        getVariantBuilder(SPELL_RUNE.get()).forAllStates(state -> {
            Direction face = state.getValue(SpellRuneBlock.FACE);
            AABB shape = SpellRuneBlock.COLLISION_SHAPES.get(face).bounds();
            return ConfiguredModel.builder().modelFile(
                    models().getBuilder(SPELL_RUNE.getId().getPath() + "_" + face.getName().toLowerCase())
                            .parent(models().getExistingFile(new ResourceLocation("block/block")))
                            .texture("texture", "block/" + SPELL_RUNE.getId().getPath())
                            .element()
                            .from((float) shape.minX * 16f, (float) shape.minY * 16f, (float) shape.minZ * 16f)
                            .to((float) shape.maxX * 16f, (float) shape.maxY * 16f, (float) shape.maxZ * 16f)
                            .face(face.getOpposite())
                            .texture("#texture")
                            .end()
                            .end()
            ).build();
        });
        ConfiguredModel[] obeliskUpper = ConfiguredModel.builder().modelFile(models().getBuilder(OBELISK.getId().getPath() + "_upper").texture("particle", mcLoc("block/stone_bricks"))).build();
        getVariantBuilder(OBELISK.get()).forAllStates(state -> {
            if (state.getValue(ObeliskBlock.PART) == ObeliskBlock.Part.LOWER) {
                ResourceLocation texture = state.getValue(AbstractFurnaceBlock.LIT) ? modLoc("block/obelisk_lit") : modLoc("block/obelisk");
                return ConfiguredModel.builder().modelFile(
                        models().getBuilder(OBELISK.getId().getPath())
                                .parent(models().getExistingFile(new ResourceLocation("forge", "item/default")))
                                .customLoader(OBJLoaderBuilder::begin)
                                .modelLocation(modLoc("models/block/obj/obelisk.obj"))
                                .end()
                                .texture("tex", texture)
                                .texture("particle", mcLoc("block/stone_bricks"))
                ).rotationY((state.getValue(AbstractFurnaceBlock.FACING).get2DDataValue() + 2) % 4 * 90).build();
            } else {
                return obeliskUpper;
            }
        });
        getVariantBuilder(CELESTIAL_PRISM.get()).forAllStates(state -> {
            if (state.getValue(CelestialPrismBlock.HALF) == DoubleBlockHalf.LOWER) {
                return ConfiguredModel.builder().modelFile(
                        models().getBuilder(CELESTIAL_PRISM.getId().getPath())
                                .parent(models().getExistingFile(new ResourceLocation("forge", "item/default")))
                                .customLoader(OBJLoaderBuilder::begin)
                                .modelLocation(modLoc("models/block/obj/celestial_prism.obj"))
                                .end()
                                .texture("tex", modLoc("block/celestial_prism"))
                                .texture("particle", modLoc("block/celestial_prism"))
                ).build();
            } else {
                return ConfiguredModel.builder().modelFile(models().getBuilder(CELESTIAL_PRISM.getId().getPath() + "_upper").texture("particle", modLoc("block/celestial_prism"))).build();
            }
        });
        getVariantBuilder(BLACK_AUREM.get()).partialState().setModels(ConfiguredModel.builder().modelFile(models().getBuilder(BLACK_AUREM.getId().getPath()).texture("particle", blockTexture(BLACK_AUREM.get()))).build());
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
    private void woodBlock(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends Block> log) {
        axisBlock(block.get(),
                models().cubeColumn(block.get().getRegistryName().getPath(), blockTexture(log.get()), blockTexture(log.get())),
                models().cubeColumnHorizontal(block.get().getRegistryName().getPath(), blockTexture(log.get()), blockTexture(log.get())));
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
     * Adds a cross block model, as seen on flowers and saplings. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void crossBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get(), models().cross(block.get().getRegistryName().getPath(), blockTexture(block.get())));
    }

    private void flowerPotBlock(Supplier<? extends Block> pot, Supplier<? extends Block> flower) {
        simpleBlock(pot.get(), models().withExistingParent(pot.get().getRegistryName().getPath(), "block/flower_pot_cross").texture("plant", blockTexture(flower.get())));
    }

    /**
     * Adds a door block model.
     *
     * @param block The block to generate the model for.
     * @param name  The base name of the door texture, excluding "_door". E.g. "oak" or "acacia".
     */
    private void doorBlock(Supplier<? extends DoorBlock> block, String name) {
        doorBlock(block.get(), name, modLoc("block/" + name + "_door_bottom"), modLoc("block/" + name + "_door_top"));
    }

    /**
     * Adds a trapdoor block model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void trapdoorBlock(Supplier<? extends TrapDoorBlock> block) {
        trapdoorBlock(block.get(), blockTexture(block.get()), true);
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
    private void fenceBlock(Supplier<? extends FenceBlock> fence, Supplier<? extends Block> block) {
        fenceBlock(fence.get(), blockTexture(block.get()));
        models().fenceInventory(fence.get().getRegistryName().getPath() + "_inventory", blockTexture(block.get()));
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
     * Adds a button model.
     *
     * @param button The block to generate the model for.
     * @param block  The block to take the texture from.
     */
    private void buttonBlock(Supplier<? extends ButtonBlock> button, Supplier<? extends Block> block) {
        ResourceLocation texture = blockTexture(block.get());
        ModelFile normal = models().withExistingParent(
                button.get().getRegistryName().getPath(),
                "block/button"
        ).texture("texture", texture);
        ModelFile pressed = models().withExistingParent(
                button.get().getRegistryName().getPath() + "_pressed",
                "block/button_pressed"
        ).texture("texture", texture);
        getVariantBuilder(button.get()).forAllStates(state -> {
            boolean powered = state.getValue(BlockStateProperties.POWERED);
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(powered ? pressed : normal);
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
        models().withExistingParent(button.get().getRegistryName().getPath() + "_inventory", "block/button_inventory").texture("texture", texture);
    }

    private void pressurePlateBlock(Supplier<? extends PressurePlateBlock> pressurePlate,
                                    Supplier<? extends Block> block) {
        getVariantBuilder(pressurePlate.get()).forAllStates(state -> state.getValue(BlockStateProperties.POWERED)
                ? ConfiguredModel.builder().modelFile(
                models().withExistingParent(
                        pressurePlate.get().getRegistryName().getPath() + "_down",
                        "block/pressure_plate_down"
                ).texture("texture", blockTexture(block.get()))).build()
                : ConfiguredModel.builder().modelFile(
                models().withExistingParent(
                        pressurePlate.get().getRegistryName().getPath(),
                        "block/pressure_plate_up"
                ).texture("texture", blockTexture(block.get()))).build()
        );
    }

    /**
     * Adds a torch/wall torch model. Uses the normal torch block id as the texture name.
     *
     * @param torch     The TorchBlock to generate the model for.
     * @param wallTorch The WallTorchBlock to generate the model for.
     */
    private void torchBlock(Supplier<? extends TorchBlock> torch, Supplier<? extends WallTorchBlock> wallTorch) {
        ModelFile file = models().withExistingParent(
                torch.get().getRegistryName().getPath(),
                "block/template_torch"
        ).texture(
                "torch",
                new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/" + torch.get().getRegistryName().getPath())
        );
        getVariantBuilder(torch.get()).partialState().setModels(ConfiguredModel.builder().modelFile(file).build());
        ModelFile wallFile = models().withExistingParent(
                wallTorch.get().getRegistryName().getPath(),
                "block/template_torch_wall"
        ).texture(
                "torch",
                new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/" + torch.get().getRegistryName().getPath())
        );
        getVariantBuilder(wallTorch.get())
                .forAllStates(state -> switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
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
    private void railBlock(Supplier<? extends RailBlock> block) {
        ResourceLocation texture = blockTexture(block.get());
        ModelFile straight = models().withExistingParent(
                block.get().getRegistryName().getPath(),
                mcLoc("block/rail")
        ).texture("rail", texture);
        ModelFile curved = models().withExistingParent(
                block.get().getRegistryName().getPath() + "_corner",
                mcLoc("block/rail_curved")
        ).texture("rail", new ResourceLocation(texture.getNamespace(), texture.getPath() + "_corner"));
        ModelFile raisedNE = models().withExistingParent(
                block.get().getRegistryName().getPath() + "_raised_ne",
                mcLoc("block/template_rail_raised_ne")
        ).texture("rail", texture);
        ModelFile raisedSW = models().withExistingParent(
                block.get().getRegistryName().getPath() + "_raised_sw",
                mcLoc("block/template_rail_raised_sw")
        ).texture("rail", texture);
        getVariantBuilder(block.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            return switch (state.getValue(((RailBlock) block).getShapeProperty())) {
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
     * Adds a wizard's chalk model. Uses the block id as the texture name.
     *
     * @param block The block to generate the model for.
     */
    private void wizardsChalkBlock(Supplier<? extends WizardsChalkBlock> block) {
        ModelFile[] models = new ModelFile[16];
        for (int i = 0; i < models.length; i++)
            models[i] = models().withExistingParent(
                    block.get().getRegistryName().getPath() + "_" + i,
                    "block/rail_flat"
            ).texture("rail", new ResourceLocation(
                    block.get().getRegistryName().getNamespace(),
                    "block/" + block.get().getRegistryName().getPath() + "_" + i));
        getVariantBuilder(block.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case NORTH -> builder.modelFile(models[state.getValue(WizardsChalkBlock.VARIANT)])
                        .build();
                case EAST -> builder.modelFile(models[state.getValue(WizardsChalkBlock.VARIANT)])
                        .rotationY(90)
                        .build();
                case SOUTH -> builder.modelFile(models[state.getValue(WizardsChalkBlock.VARIANT)])
                        .rotationY(180)
                        .build();
                case WEST -> builder.modelFile(models[state.getValue(WizardsChalkBlock.VARIANT)])
                        .rotationY(270)
                        .build();
                default -> new ConfiguredModel[0];
            };
        });
    }
}
