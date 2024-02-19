package com.github.minecraftschurlimods.arsmagicalegacy.api.advancement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Advancement trigger for when a player levels up in magic.
 */
public class PlayerLevelUpTrigger extends SimpleCriterionTrigger<PlayerLevelUpTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "player_level_up");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected PlayerLevelUpTrigger.TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pContext) {
        return new PlayerLevelUpTrigger.TriggerInstance(pPredicate, MinMaxBounds.Ints.fromJson(pJson.get("level")));
    }

    /**
     * Triggers the advancement trigger.
     * @param pPlayer The affected player.
     * @param level   The new level.
     */
    public void trigger(ServerPlayer pPlayer, int level) {
        trigger(pPlayer, t -> t.matches(level));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final MinMaxBounds.Ints level;

        public TriggerInstance(ContextAwarePredicate pPredicate, MinMaxBounds.Ints level) {
            super(PlayerLevelUpTrigger.ID, pPredicate);
            this.level = level;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("level", this.level.serializeToJson());
            return jsonobject;
        }

        /**
         * @param level The level to check.
         * @return Whether the given level matches this instance's level or not.
         */
        public boolean matches(int level) {
            return this.level.matches(level);
        }
    }
}
