package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.google.gson.JsonElement;
import net.minecraft.core.BlockPos;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class RitualProvider extends AbstractDataProvider<Ritual, RitualProvider.Builder> {

    protected RitualProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(Ritual.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "AMRituals[" + namespace + "]";
    }

    public Builder builder(String id) {
        return new Builder(new ResourceLocation(namespace, id));
    }

    protected static class Builder extends AbstractDataBuilder<Ritual, Builder> {
        private final List<RitualRequirement> requirements = new ArrayList<>();
        private RitualEffect effect;
        private RitualTrigger trigger;
        private BlockPos offset = BlockPos.ZERO;

        private Builder(ResourceLocation id) {
            super(id);
        }

        public Builder with(RitualRequirement requirement) {
            this.requirements.add(requirement);
            return this;
        }

        public Builder with(RitualRequirement... requirements) {
            return with(Arrays.asList(requirements));
        }

        public Builder with(List<RitualRequirement> requirements) {
            this.requirements.addAll(requirements);
            return this;
        }

        public Builder with(RitualEffect effect) {
            this.effect = effect;
            return this;
        }

        public Builder with(RitualTrigger trigger) {
            this.trigger = trigger;
            return this;
        }

        public Builder with(BlockPos offset) {
            this.offset = offset;
            return this;
        }

        @Override
        protected Ritual build() {
            if (trigger == null) {
                throw new IllegalStateException("Trigger must be set");
            }
            if (effect == null) {
                throw new IllegalStateException("Effect must be set");
            }
            return new Ritual(trigger, requirements, effect, offset);
        }
    }
}
