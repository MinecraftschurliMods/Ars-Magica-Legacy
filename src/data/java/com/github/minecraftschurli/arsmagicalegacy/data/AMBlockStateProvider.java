package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks.*;

class AMBlockStateProvider extends BlockStateProvider {
    public AMBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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
        simpleBlock(CHIMERITE_ORE);
        simpleBlock(CHIMERITE_BLOCK);
        simpleBlock(TOPAZ_ORE);
        simpleBlock(TOPAZ_BLOCK);
    }

    private void simpleBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get());
    }

    private void logBlock(Supplier<? extends Block> block) {
        if (block.get() instanceof RotatedPillarBlock) logBlock((RotatedPillarBlock) block.get());
    }
    
    private void woodBlock(Supplier<? extends Block> block, Supplier<? extends Block> log) {
        if (block.get() instanceof RotatedPillarBlock) axisBlock((RotatedPillarBlock) block.get(), models().cubeColumn(block.get().getRegistryName().getPath(), blockTexture(log.get()), blockTexture(log.get())), models().cubeColumnHorizontal(block.get().getRegistryName().getPath(), blockTexture(log.get()), blockTexture(log.get())));
    }

    private void airBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get(), models().getExistingFile(mcLoc("block/air")));
    }

    private void crossBlock(Supplier<? extends Block> block) {
        simpleBlock(block.get(), models().cross(block.get().getRegistryName().getPath(), blockTexture(block.get())));
    }

    private void doorBlock(Supplier<? extends Block> block, String name) {
        if (block.get() instanceof DoorBlock) doorBlock((DoorBlock) block.get(), name, modLoc("block/" + name + "_door_bottom"), modLoc("block/" + name + "_door_top"));
    }

    private void trapdoorBlock(Supplier<? extends Block> block) {
        if (block.get() instanceof TrapDoorBlock) trapdoorBlock((TrapDoorBlock) block.get(), blockTexture(block.get()), true);
    }
    
    private void slabBlock(Supplier<? extends Block> slab, Supplier<? extends Block> block) {
        if (slab.get() instanceof SlabBlock) slabBlock((SlabBlock) slab.get(), cubeAll(block.get()).getLocation(), blockTexture(block.get()));
    }

    private void stairsBlock(Supplier<? extends Block> stairs, Supplier<? extends Block> block) {
        if (stairs.get() instanceof StairBlock) stairsBlock((StairBlock) stairs.get(), blockTexture(block.get()));
    }

    private void fenceBlock(Supplier<? extends Block> fence, Supplier<? extends Block> block) {
        if (fence.get() instanceof FenceBlock) fenceBlock((FenceBlock) fence.get(), blockTexture(block.get()));
        models().fenceInventory(fence.get().getRegistryName().getPath() + "_inventory", blockTexture(block.get()));
    }

    private void fenceGateBlock(Supplier<? extends Block> fenceGate, Supplier<? extends Block> block) {
        if (fenceGate.get() instanceof FenceGateBlock) fenceGateBlock((FenceGateBlock) fenceGate.get(), blockTexture(block.get()));
    }

    private void railBlock(Supplier<? extends RailBlock> block) {
        VariantBlockStateBuilder builder = getVariantBuilder(block.get());
        ResourceLocation blockTex = blockTexture(block.get());
        ModelFile straight = models().withExistingParent(block.get().getRegistryName().getPath(), mcLoc("block/rail")).texture("rail", blockTex);
        ModelFile curved = models().withExistingParent(block.get().getRegistryName().getPath() + "_corner", mcLoc("block/rail_curved")).texture("rail", new ResourceLocation(blockTex.getNamespace(), blockTex.getPath() + "_corner"));
        ModelFile raisedNE = models().withExistingParent(block.get().getRegistryName().getPath() + "_raised_ne", mcLoc("block/template_rail_raised_ne")).texture("rail", blockTex);
        ModelFile raisedSW = models().withExistingParent(block.get().getRegistryName().getPath() + "_raised_sw", mcLoc("block/template_rail_raised_sw")).texture("rail", blockTex);
        builder.forAllStates(state -> switch (state.getValue(((RailBlock) block).getShapeProperty())) {
            case NORTH_SOUTH -> ConfiguredModel.builder().modelFile(straight).build();
            case EAST_WEST -> ConfiguredModel.builder().modelFile(straight).rotationY(90).build();
            case SOUTH_EAST -> ConfiguredModel.builder().modelFile(curved).build();
            case SOUTH_WEST -> ConfiguredModel.builder().modelFile(curved).rotationY(90).build();
            case NORTH_WEST -> ConfiguredModel.builder().modelFile(curved).rotationY(180).build();
            case NORTH_EAST -> ConfiguredModel.builder().modelFile(curved).rotationY(270).build();
            case ASCENDING_NORTH -> ConfiguredModel.builder().modelFile(raisedNE).build();
            case ASCENDING_EAST -> ConfiguredModel.builder().modelFile(raisedNE).rotationY(90).build();
            case ASCENDING_SOUTH -> ConfiguredModel.builder().modelFile(raisedSW).build();
            case ASCENDING_WEST -> ConfiguredModel.builder().modelFile(raisedSW).rotationY(90).build();
        });
    }
}
