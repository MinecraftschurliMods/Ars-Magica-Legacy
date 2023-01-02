package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.SunstoneOreFeature;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite.MeteoriteFeature;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMFeatures {
    RegistryObject<MeteoriteFeature>   METEORITE            = AMRegistries.FEATURES.register("meteorite", MeteoriteFeature::new);
    RegistryObject<SunstoneOreFeature> SUNSTONE_ORE_FEATURE = AMRegistries.FEATURES.register("sunstone_ore", SunstoneOreFeature::new);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
