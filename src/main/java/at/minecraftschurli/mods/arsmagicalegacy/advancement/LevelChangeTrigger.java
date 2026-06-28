package at.minecraftschurli.mods.arsmagicalegacy.advancement;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMCriterionTriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public class LevelChangeTrigger extends SimpleCriterionTrigger<LevelChangeTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int level) {
        trigger(player, t -> t.matches(level));
    }

    public static Criterion<TriggerInstance> create(int level) {
        return AMCriterionTriggers.LEVEL_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), level));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, int level) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
            ExtraCodecs.POSITIVE_INT.fieldOf("level").forGetter(TriggerInstance::level)
        ).apply(inst, TriggerInstance::new));

        public boolean matches(int level) {
            return level >= this.level;
        }
    }
}
