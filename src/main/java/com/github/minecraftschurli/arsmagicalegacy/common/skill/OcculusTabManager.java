package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTabManager;
import com.github.minecraftschurli.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;

public final class OcculusTabManager extends CodecDataManager<IOcculusTab> implements IOcculusTabManager {
    private static final Supplier<OcculusTabManager> INSTANCE = Lazy.concurrentOf(OcculusTabManager::new);

    private OcculusTabManager() {
        super("occulus_tabs", OcculusTab.CODEC, OcculusTab.NETWORK_CODEC, OcculusTabManager::validate, LogManager.getLogger());
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    private static void validate(Map<ResourceLocation, IOcculusTab> data, Logger logger) {
        data.forEach((id, tab) -> ((OcculusTab)tab).setId(id));
    }

    public static OcculusTabManager instance() {
        return INSTANCE.get();
    }

    @Override
    public Collection<IOcculusTab> getTabs() {
        return getValues();
    }

    @Override
    public IOcculusTab getByIndex(int index) {
        return getValues().stream().sorted(Comparator.comparing(IOcculusTab::getOcculusIndex)).toArray(IOcculusTab[]::new)[index];
    }
}
