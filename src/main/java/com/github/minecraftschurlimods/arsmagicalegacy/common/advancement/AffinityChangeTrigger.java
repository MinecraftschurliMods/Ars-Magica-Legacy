package com.github.minecraftschurlimods.arsmagicalegacy.common.advancement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriterionTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMExtraCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class AffinityChangeTrigger extends SimpleCriterionTrigger<AffinityChangeTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Holder<Affinity> affinity, double oldDepth, double newDepth) {
        trigger(player, t -> t.matches(affinity, oldDepth, newDepth));
    }

    public static Criterion<TriggerInstance> create(double newDepth) {
        return AMCriterionTriggers.AFFINITY_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), 0, newDepth));
    }

    public static Criterion<TriggerInstance> create(Holder<Affinity> affinity, double newDepth) {
        return AMCriterionTriggers.AFFINITY_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(affinity), 0, newDepth));
    }

    public static Criterion<TriggerInstance> create(double oldDepth, double newDepth) {
        return AMCriterionTriggers.AFFINITY_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), oldDepth, newDepth));
    }

    public static Criterion<TriggerInstance> create(Holder<Affinity> affinity, double oldDepth, double newDepth) {
        return AMCriterionTriggers.AFFINITY_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(affinity), oldDepth, newDepth));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Holder<Affinity>> affinity, double oldDepth, double newDepth) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
            Affinity.CODEC.optionalFieldOf("affinity").forGetter(TriggerInstance::affinity),
            AMExtraCodecs.doubleRangeCodec(0, 1).optionalFieldOf("old_depth", 0.).forGetter(TriggerInstance::oldDepth),
            AMExtraCodecs.doubleRangeCodec(0, 1).fieldOf("new_depth").forGetter(TriggerInstance::newDepth)
        ).apply(inst, TriggerInstance::new));

        public boolean matches(Holder<Affinity> affinity, double oldDepth, double newDepth) {
            return this.affinity.map(e -> e.getKey() == affinity.getKey()).orElse(true) && oldDepth >= this.oldDepth && newDepth >= this.newDepth;
        }
    }
}
