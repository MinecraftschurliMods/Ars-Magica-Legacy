package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.CUSTOM_STATS;

@NonExtendable
public interface AMStats {
    Map<ResourceLocation, StatFormatter> FORMATTERS = new HashMap<>();

    Holder<ResourceLocation> INTERACT_WITH_OBELISK           = register("interact_with_obelisk",           StatFormatter.DEFAULT);
    Holder<ResourceLocation> INTERACT_WITH_OCCULUS           = register("interact_with_occulus",           StatFormatter.DEFAULT);
    Holder<ResourceLocation> INTERACT_WITH_INSCRIPTION_TABLE = register("interact_with_inscription_table", StatFormatter.DEFAULT);
    Holder<ResourceLocation> SPELL_CAST                      = register("spell_cast",                      StatFormatter.DEFAULT);
    Holder<ResourceLocation> RITUALS_TRIGGERED               = register("rituals_triggered",               StatFormatter.DEFAULT);

    private static Holder<ResourceLocation> register(String name, StatFormatter formatter) {
        var reg = CUSTOM_STATS.register(name, Function.identity());
        FORMATTERS.put(reg.getId(), formatter);
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
