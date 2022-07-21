package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Consumer;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.FLUIDS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.FLUID_TYPES;

@NonExtendable
public interface AMFluids {
    RegistryObject<FluidType> LIQUID_ESSENCE_TYPE = FLUID_TYPES.register("liquid_essence", () -> new FluidType(FluidType.Properties.create().fallDistanceModifier(0F)) {
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
    RegistryObject<ForgeFlowingFluid> LIQUID_ESSENCE = FLUIDS.register("liquid_essence", () -> new ForgeFlowingFluid.Source(AMFluids.LIQUID_ESSENCE_PROPERTIES));
    RegistryObject<ForgeFlowingFluid> FLOWING_LIQUID_ESSENCE = FLUIDS.register("flowing_liquid_essence", () -> new ForgeFlowingFluid.Flowing(AMFluids.LIQUID_ESSENCE_PROPERTIES));
    ForgeFlowingFluid.Properties LIQUID_ESSENCE_PROPERTIES = new ForgeFlowingFluid.Properties(LIQUID_ESSENCE_TYPE, LIQUID_ESSENCE, FLOWING_LIQUID_ESSENCE).bucket(AMItems.LIQUID_ESSENCE_BUCKET).block(AMBlocks.LIQUID_ESSENCE);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
