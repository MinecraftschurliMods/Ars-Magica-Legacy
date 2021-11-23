package com.github.minecraftschurli.arsmagicalegacy.api.advancement;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class PlayerLevelUpTrigger extends SimpleCriterionTrigger<PlayerLevelUpTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "player_level_up");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected PlayerLevelUpTrigger.TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        IntegerPredicate level = IntegerPredicate.fromJson(pJson.get("level"));
        return new PlayerLevelUpTrigger.TriggerInstance(pPlayer, level);
    }

    public void trigger(ServerPlayer pPlayer, int level) {
        this.trigger(pPlayer, (p_70648_) -> p_70648_.matches(level));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final IntegerPredicate level;

        public TriggerInstance(EntityPredicate.Composite pPlayer, IntegerPredicate level) {
            super(PlayerLevelUpTrigger.ID, pPlayer);
            this.level = level;
        }

        public boolean matches(int level) {
            return this.level.matches(level);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("level", this.level.serializeToJson());
            return jsonobject;
        }
    }
}
