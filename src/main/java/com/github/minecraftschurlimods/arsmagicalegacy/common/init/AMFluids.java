package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Consumer;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.FLUIDS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.FLUID_TYPES;

@NonExtendable
public interface AMFluids {
    DeferredHolder<FluidType, FluidType> LIQUID_ESSENCE_TYPE = FLUID_TYPES.register("liquid_essence", () -> new FluidType(FluidType.Properties.create().fallDistanceModifier(0F)) {
        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/liquid_essence_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/liquid_essence_flowing");
                }
            });
        }
    });
    DeferredHolder<Fluid, BaseFlowingFluid> LIQUID_ESSENCE = FLUIDS.register("liquid_essence", () -> new BaseFlowingFluid.Source(AMFluids.LIQUID_ESSENCE_PROPERTIES));
    DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_LIQUID_ESSENCE = FLUIDS.register("flowing_liquid_essence", () -> new BaseFlowingFluid.Flowing(AMFluids.LIQUID_ESSENCE_PROPERTIES));
    BaseFlowingFluid.Properties LIQUID_ESSENCE_PROPERTIES = new BaseFlowingFluid.Properties(LIQUID_ESSENCE_TYPE, LIQUID_ESSENCE, FLOWING_LIQUID_ESSENCE).bucket(AMItems.LIQUID_ESSENCE_BUCKET).block(AMBlocks.LIQUID_ESSENCE);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
