package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public interface AMFluids {
    DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, ArsMagicaApi.MOD_ID);
    DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ArsMagicaApi.MOD_ID);

    DeferredHolder<FluidType, FluidType> LIQUID_ETHERIUM_TYPE = FLUID_TYPES.register("liquid_etherium", () -> new FluidType(FluidType.Properties.create()
        .descriptionId("block." + ArsMagicaApi.MOD_ID + ".liquid_etherium")
        .canExtinguish(true)
        .canConvertToSource(false)
        .supportsBoating(true)
        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
        .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
        .canHydrate(true)));
    DeferredHolder<Fluid, BaseFlowingFluid.Source> LIQUID_ETHERIUM = FLUIDS.register("liquid_etherium", () -> new BaseFlowingFluid.Source(AMFluids.LIQUID_ETHERIUM_PROPERTIES));
    DeferredHolder<Fluid, BaseFlowingFluid.Flowing> FLOWING_LIQUID_ETHERIUM = FLUIDS.register("flowing_liquid_etherium", () -> new BaseFlowingFluid.Flowing(AMFluids.LIQUID_ETHERIUM_PROPERTIES));
    BaseFlowingFluid.Properties LIQUID_ETHERIUM_PROPERTIES = new BaseFlowingFluid.Properties(LIQUID_ETHERIUM_TYPE, LIQUID_ETHERIUM, FLOWING_LIQUID_ETHERIUM).block(AMBlocks.LIQUID_ETHERIUM).bucket(AMItems.LIQUID_ETHERIUM_BUCKET);
}
