package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.HashMap;
import java.util.Map;

@NonExtendable
public interface AMStats {
    Map<ResourceLocation, StatFormatter> STAT_REGISTER = new HashMap<>();
    ResourceLocation INTERACT_WITH_OBELISK           = register(new ResourceLocation(ArsMagicaAPI.MOD_ID, "interact_with_obelisk"),           StatFormatter.DEFAULT);
    ResourceLocation INTERACT_WITH_OCCULUS           = register(new ResourceLocation(ArsMagicaAPI.MOD_ID, "interact_with_occulus"),           StatFormatter.DEFAULT);
    ResourceLocation INTERACT_WITH_INSCRIPTION_TABLE = register(new ResourceLocation(ArsMagicaAPI.MOD_ID, "interact_with_inscription_table"), StatFormatter.DEFAULT);
    ResourceLocation SPELL_CAST                      = register(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_cast"),                      StatFormatter.DEFAULT);

    private static ResourceLocation register(ResourceLocation location, StatFormatter formatter) {
        STAT_REGISTER.put(location, formatter);
        return location;
    }

    static void onRegister(FMLCommonSetupEvent event) {
        STAT_REGISTER.forEach((location, formatter) -> {
            Registry.register(Registry.CUSTOM_STAT, location, location);
            Stats.CUSTOM.get(location, formatter);
        });
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
