package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.ChorusGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.CropGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.HangingBushGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.HangingGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.StemGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.TallCropGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.UpwardsGrowthType;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMGrowthTypes {
    DeferredRegister<MapCodec<? extends GrowthType>> GROWTH_TYPES = DeferredRegister.create(AMRegistries.Keys.GROWTH_TYPE, ArsMagicaApi.MOD_ID);

    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<BushGrowthType>>        BUSH         = GROWTH_TYPES.register("bush",         () -> BushGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<ChorusGrowthType>>      CHORUS       = GROWTH_TYPES.register("chorus",       () -> ChorusGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<CropGrowthType>>        CROP         = GROWTH_TYPES.register("crop",         () -> CropGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<HangingGrowthType>>     HANGING      = GROWTH_TYPES.register("hanging",      () -> HangingGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<HangingBushGrowthType>> HANGING_BUSH = GROWTH_TYPES.register("hanging_bush", () -> HangingBushGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<StemGrowthType>>        STEM         = GROWTH_TYPES.register("stem",         () -> StemGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<TallCropGrowthType>>    TALL_CROP    = GROWTH_TYPES.register("tall_crop",    () -> TallCropGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<UpwardsGrowthType>>     UPWARDS      = GROWTH_TYPES.register("upwards",      () -> UpwardsGrowthType.CODEC);
}
