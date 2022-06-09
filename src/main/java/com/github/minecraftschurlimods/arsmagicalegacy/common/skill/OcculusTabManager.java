package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTabManager;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class OcculusTabManager extends CodecDataManager<IOcculusTab> implements IOcculusTabManager {
    private static final Supplier<OcculusTabManager> INSTANCE = Lazy.concurrentOf(OcculusTabManager::new);

    private OcculusTabManager() {
        super(ArsMagicaAPI.MOD_ID, "occulus_tabs", OcculusTab.CODEC, OcculusTab.NETWORK_CODEC, OcculusTabManager::validate, LoggerFactory.getLogger(OcculusTabManager.class));
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    /**
     * @return The only instance of this class.
     */
    public static OcculusTabManager instance() {
        return INSTANCE.get();
    }

    @Override
    public IOcculusTab getOrThrow(@Nullable ResourceLocation id) {
        return super.getOrThrow(id);
    }

    @Override
    public Optional<IOcculusTab> getOptional(@Nullable ResourceLocation id) {
        return super.getOptional(id);
    }

    private static void validate(Map<ResourceLocation, IOcculusTab> data, Logger logger) {
        data.forEach((id, tab) -> ((OcculusTab) tab).setId(id));
    }

    @Override
    public Collection<IOcculusTab> getTabs() {
        return values();
    }

    @Override
    public IOcculusTab getByIndex(int index) {
        return values().stream().sorted(Comparator.comparing(IOcculusTab::getOcculusIndex)).toArray(IOcculusTab[]::new)[index];
    }
}
