package com.github.minecraftschurlimods.arsmagicalegacy.common.advancement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriterionTriggers;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.StringRepresentableEnum;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.TriState;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SkillChangeTrigger extends SimpleCriterionTrigger<SkillChangeTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, t -> t.matches(player));
    }

    public static Criterion<TriggerInstance> create(Requirements requirements) {
        return AMCriterionTriggers.SKILL_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Either.left(requirements)));
    }

    public static Criterion<TriggerInstance> create(List<Holder<Skill>> requirements) {
        return AMCriterionTriggers.SKILL_CHANGE.get().createCriterion(new TriggerInstance(Optional.empty(), Either.right(requirements)));
    }

    public enum Requirements implements StringRepresentableEnum {
        ANY(false, TriState.DEFAULT),
        ALL(true, TriState.DEFAULT),
        ANY_NON_HIDDEN(false, TriState.FALSE),
        ALL_NON_HIDDEN(true, TriState.FALSE),
        ANY_HIDDEN(false, TriState.TRUE),
        ALL_HIDDEN(true, TriState.TRUE);

        public final boolean all;
        public final TriState hidden;

        Requirements(boolean all, TriState hidden) {
            this.all = all;
            this.hidden = hidden;
        }

        public static final Codec<Requirements> CODEC = StringRepresentable.fromEnum(Requirements::values);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Either<Requirements, List<Holder<Skill>>> requirements) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
            Codec.either(Requirements.CODEC, Skill.CODEC.listOf()).fieldOf("requirements").forGetter(TriggerInstance::requirements)
        ).apply(inst, TriggerInstance::new));

        public boolean matches(Player player) {
            Predicate<Holder<Skill>> predicate = requirements.map(left -> switch (left.hidden) {
                case TRUE -> skill -> skill.value().hidden();
                case DEFAULT -> _ -> true;
                case FALSE -> skill -> !skill.value().hidden();
            }, right -> right::contains);
            Predicate<Holder<Skill>> knows = skill -> ArsMagicaApi.magicHelper().knows(player, skill);
            Stream<Holder.Reference<Skill>> stream = AMRegistries.skills(player.registryAccess())
                .listElements()
                .filter(predicate);
            return requirements.map(left -> left.all, right -> true) ? stream.allMatch(knows) : stream.anyMatch(knows);
        }
    }
}
