package com.github.minecraftschurlimods.arsmagicalegacy.api.advancement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class PlayerLearnedSkillTrigger extends SimpleCriterionTrigger<PlayerLearnedSkillTrigger.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "player_learned_skill");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pPlayer, DeserializationContext pContext) {
        ResourceLocation skill = pJson.has("skill") ? new ResourceLocation(GsonHelper.getAsString(pJson, "skill")) : null;
        return new TriggerInstance(pPlayer, skill);
    }

    public void trigger(ServerPlayer pPlayer, ResourceLocation skill) {
        this.trigger(pPlayer, (p_70648_) -> p_70648_.matches(skill));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ResourceLocation skill;

        public TriggerInstance(EntityPredicate.Composite pPlayer, ResourceLocation skill) {
            super(PlayerLearnedSkillTrigger.ID, pPlayer);
            this.skill = skill;
        }

        public boolean matches(ResourceLocation skill) {
            return this.skill == null || this.skill.equals(skill);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonObject = super.serializeToJson(pConditions);
            if (this.skill != null) {
                jsonObject.addProperty("skill", this.skill.toString());
            }
            return jsonObject;
        }
    }
}
