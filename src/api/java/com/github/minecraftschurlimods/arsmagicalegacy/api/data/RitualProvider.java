package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class RitualProvider implements DataProvider {
    protected final Map<ResourceLocation, Ritual> data = new HashMap<>();
    protected final JsonCodecProvider<Ritual> provider;
    private final String namespace;

    public RitualProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.namespace = namespace;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), Ritual.REGISTRY_KEY, data);
    }

    @Override
    public void run(CachedOutput pCache) throws IOException {
        addRituals(this::addRitual);
        provider.run(pCache);
    }

    @Override
    public String getName() {
        return "AMRituals[" + namespace + "]";
    }

    public RitualBuilder builder(String id) {
        return new RitualBuilder(new ResourceLocation(namespace, id));
    }

    protected abstract void addRituals(BiConsumer<ResourceLocation, Ritual> consumer);

    private void addRitual(ResourceLocation id, Ritual ritual) {
        data.put(id, ritual);
    }

    protected static class RitualBuilder {
        private final List<RitualRequirement> requirements = new ArrayList<>();
        private final ResourceLocation id;
        private RitualEffect effect;
        private RitualTrigger trigger;
        private BlockPos offset = BlockPos.ZERO;

        private RitualBuilder(ResourceLocation id) {
            this.id = id;
        }

        public RitualBuilder with(RitualRequirement requirement) {
            this.requirements.add(requirement);
            return this;
        }

        public RitualBuilder with(RitualRequirement... requirements) {
            return with(Arrays.asList(requirements));
        }

        public RitualBuilder with(List<RitualRequirement> requirements) {
            this.requirements.addAll(requirements);
            return this;
        }

        public RitualBuilder with(RitualEffect effect) {
            this.effect = effect;
            return this;
        }

        public RitualBuilder with(RitualTrigger trigger) {
            this.trigger = trigger;
            return this;
        }

        public RitualBuilder with(BlockPos offset) {
            this.offset = offset;
            return this;
        }

        private Ritual buildInternal() {
            if (trigger == null) {
                throw new IllegalStateException("Trigger must be set");
            }
            if (effect == null) {
                throw new IllegalStateException("Effect must be set");
            }
            return new Ritual(trigger, requirements, effect, offset);
        }

        public void build(BiConsumer<ResourceLocation, Ritual> consumer) {
            consumer.accept(id, buildInternal());
        }
    }
}
