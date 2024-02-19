package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class RitualBuilder {
    private final List<RitualRequirement> requirements = new ArrayList<>();
    private final ResourceLocation id;
    private RitualEffect effect;
    private RitualTrigger trigger;
    private BlockPos offset = BlockPos.ZERO;

    RitualBuilder(ResourceLocation id) {
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
