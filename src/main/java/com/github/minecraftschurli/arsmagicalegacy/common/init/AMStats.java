package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.Internal;

@NonExtendable
public interface AMStats {
    ResourceLocation INTERACT_WITH_OCCULUS = register(new ResourceLocation(ArsMagicaAPI.MOD_ID, "interact_with_occulus"), StatFormatter.DEFAULT);

    private static ResourceLocation register(ResourceLocation location, StatFormatter formatter) {
        Registry.register(Registry.CUSTOM_STAT, location, location);
        Stats.CUSTOM.get(location, formatter);
        return location;
    }

    @Internal
    static void register() {}
}