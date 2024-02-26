package com.github.minecraftschurlimods.arsmagicalegacy.api.advancement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

/**
 * Advancement trigger for when a player levels up in magic.
 */
public class PlayerLevelUpTrigger extends SimpleCriterionTrigger<PlayerLevelUpTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "player_level_up");

    /**
     * Triggers the advancement trigger.
     * @param pPlayer The affected player.
     * @param level   The new level.
     */
    public void trigger(ServerPlayer pPlayer, int level) {
        trigger(pPlayer, t -> t.matches(level));
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MinMaxBounds.Ints> level) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player),
                ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "level").forGetter(TriggerInstance::level)
        ).apply(inst, TriggerInstance::new));

        /**
         * @param level The level to check.
         * @return Whether the given level matches this instance's level or not.
         */
        public boolean matches(int level) {
            return this.level().map(l -> l.matches(level)).orElse(true);
        }
    }
}
