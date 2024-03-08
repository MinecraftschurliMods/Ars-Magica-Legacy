package com.github.minecraftschurlimods.arsmagicalegacy.api.advancement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

/**
 * Advancement trigger for when a player learns a new skill.
 */
public class PlayerLearnedSkillTrigger extends SimpleCriterionTrigger<PlayerLearnedSkillTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "player_learned_skill");

    /**
     * Triggers the advancement trigger.
     * @param pPlayer The affected player.
     * @param skill   The skill learned.
     */
    public void trigger(ServerPlayer pPlayer, ResourceLocation skill) {
        this.trigger(pPlayer, t -> t.matches(skill));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceLocation> skill) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<PlayerLearnedSkillTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player),
                ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "skill").forGetter(TriggerInstance::skill)
        ).apply(inst, TriggerInstance::new));

        /**
         * @param skill The skill id to check.
         * @return Whether the given skill id matches this instance's skill id or not.
         */
        public boolean matches(ResourceLocation skill) {
            return this.skill().map(skill::equals).orElse(true);
        }
    }
}
