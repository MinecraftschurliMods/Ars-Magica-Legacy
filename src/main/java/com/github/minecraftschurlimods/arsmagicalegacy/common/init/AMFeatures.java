package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.SunstoneOreFeature;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite.MeteoriteFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.FEATURES;

@NonExtendable
public interface AMFeatures {
    DeferredHolder<Feature<?>, MeteoriteFeature>   METEORITE            = FEATURES.register("meteorite", MeteoriteFeature::new);
    DeferredHolder<Feature<?>, SunstoneOreFeature> SUNSTONE_ORE_FEATURE = FEATURES.register("sunstone_ore", SunstoneOreFeature::new);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
