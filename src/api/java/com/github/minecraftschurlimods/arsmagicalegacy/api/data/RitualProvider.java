package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.google.gson.JsonElement;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class RitualProvider extends AbstractRegistryDataProvider<Ritual, RitualProvider.Builder> {
    protected RitualProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(Ritual.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "AMRituals[" + namespace + "]";
    }

    /**
     * @param id      The id of the ritual.
     * @param effect  The effect of the ritual.
     * @param trigger The trigger of the trigger.
     */
    public Builder builder(String id, RitualEffect effect, RitualTrigger trigger) {
        return new Builder(new ResourceLocation(namespace, id), this, effect, trigger);
    }

    protected static class Builder extends AbstractRegistryDataProvider.Builder<Ritual, Builder> {
        private final List<RitualRequirement> requirements = new ArrayList<>();
        private final RitualEffect effect;
        private final RitualTrigger trigger;
        private BlockPos offset = BlockPos.ZERO;

        private Builder(ResourceLocation id, RitualProvider provider, RitualEffect effect, RitualTrigger trigger) {
            super(id, provider, Ritual.DIRECT_CODEC);
            this.effect = effect;
            this.trigger = trigger;
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

        public Builder with(BlockPos offset) {
            this.offset = offset;
            return this;
        }

        @Override
        protected Ritual get() {
            return new Ritual(trigger, requirements, effect, offset);
        }
    }
}
