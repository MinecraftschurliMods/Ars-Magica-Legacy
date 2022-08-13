package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Base class for occulus tab data generators.
 */
public abstract class OcculusTabProvider implements DataProvider {
    private final JsonCodecProvider<OcculusTab> provider;
    private final Map<ResourceLocation, OcculusTab> data = new HashMap<>();
    private final String namespace;

    protected OcculusTabProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.namespace = namespace;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), OcculusTab.REGISTRY_KEY, data);
    }

    protected abstract void createOcculusTabs(Consumer<OcculusTabBuilder> consumer);

    @Internal
    @Override
    public void run(CachedOutput pCache) throws IOException {
        createOcculusTabs(occulusTabBuilder -> data.put(occulusTabBuilder.getId(), occulusTabBuilder.build()));
        provider.run(pCache);
    }

    /**
     * @param name  The occulus tab name.
     * @param index The index of the occulus tab.
     * @return A new occulus tab.
     */
    protected OcculusTabBuilder createOcculusTab(String name, int index) {
        return OcculusTabBuilder.create(new ResourceLocation(namespace, name)).setIndex(index);
    }
}
