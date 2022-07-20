package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.CUSTOM_STATS;

@NonExtendable
public interface AMStats {
    Map<ResourceLocation, StatFormatter> FORMATTERS = new HashMap<>();

    RegistryObject<ResourceLocation> INTERACT_WITH_OBELISK           = register("interact_with_obelisk",           StatFormatter.DEFAULT);
    RegistryObject<ResourceLocation> INTERACT_WITH_OCCULUS           = register("interact_with_occulus",           StatFormatter.DEFAULT);
    RegistryObject<ResourceLocation> INTERACT_WITH_INSCRIPTION_TABLE = register("interact_with_inscription_table", StatFormatter.DEFAULT);
    RegistryObject<ResourceLocation> SPELL_CAST                      = register("spell_cast",                      StatFormatter.DEFAULT);
    RegistryObject<ResourceLocation> RITUALS_TRIGGERED               = register("rituals_triggered",               StatFormatter.DEFAULT);

    private static RegistryObject<ResourceLocation> register(String name, StatFormatter formatter) {
        AtomicReference<RegistryObject<ResourceLocation>> ref = new AtomicReference<>();
        var reg = CUSTOM_STATS.register(name, () -> ref.get().getId());
        FORMATTERS.put(reg.getId(), formatter);
        ref.set(reg);
        return reg;
    }

    static void onRegister(FMLCommonSetupEvent event) {
        CUSTOM_STATS.getEntries().forEach((reg) -> Stats.CUSTOM.get(reg.get(), FORMATTERS.get(reg.getId())));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
