package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.OcculusTab;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.OCCULUS_TABS;

@NonExtendable
public interface AMOcculusTabs {
    RegistryObject<IOcculusTab> OFFENSE  = OCCULUS_TABS.register("offense",  () -> new OcculusTab(0));
    RegistryObject<IOcculusTab> DEFENSE  = OCCULUS_TABS.register("defense",  () -> new OcculusTab(1));
    RegistryObject<IOcculusTab> UTILITY  = OCCULUS_TABS.register("utility",  () -> new OcculusTab(2));
    RegistryObject<IOcculusTab> AFFINITY = OCCULUS_TABS.register("affinity", () -> new OcculusTab(3));
    RegistryObject<IOcculusTab> TALENT   = OCCULUS_TABS.register("talent",   () -> new OcculusTab(4));

    @Internal
    static void register() {}
}
