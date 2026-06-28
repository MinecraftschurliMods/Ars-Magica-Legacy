package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.LearnSkillRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.SetBlockRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.SpawnEntityRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeTagRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.EnvironmentAttributeRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.IngredientRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.StructureRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.DroppedItemRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.KillEntityRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.SetBlockStateRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.SpellCastRitualTrigger;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMRituals {
    // @formatter:off
    DeferredRegister<MapCodec<? extends RitualEffect>>      RITUAL_EFFECTS      = DeferredRegister.create(AMRegistries.Keys.RITUAL_EFFECT,      ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends RitualRequirement>> RITUAL_REQUIREMENTS = DeferredRegister.create(AMRegistries.Keys.RITUAL_REQUIREMENT, ArsMagicaApi.MOD_ID);
    DeferredRegister<MapCodec<? extends RitualTrigger<?>>>  RITUAL_TRIGGERS     = DeferredRegister.create(AMRegistries.Keys.RITUAL_TRIGGER,     ArsMagicaApi.MOD_ID);

    DeferredHolder<MapCodec<? extends RitualEffect>, MapCodec<LearnSkillRitualEffect>>  LEARN_SKILL_EFFECT  = RITUAL_EFFECTS.register("learn_skill",  () -> LearnSkillRitualEffect.CODEC);
    DeferredHolder<MapCodec<? extends RitualEffect>, MapCodec<SetBlockRitualEffect>>    SET_BLOCK_EFFECT    = RITUAL_EFFECTS.register("set_block",    () -> SetBlockRitualEffect.CODEC);
    DeferredHolder<MapCodec<? extends RitualEffect>, MapCodec<SpawnEntityRitualEffect>> SPAWN_ENTITY_EFFECT = RITUAL_EFFECTS.register("spawn_entity", () -> SpawnEntityRitualEffect.CODEC);

    DeferredHolder<MapCodec<? extends RitualRequirement>, MapCodec<BiomeTagRitualRequirement>>                BIOME_TAG_REQUIREMENT             = RITUAL_REQUIREMENTS.register("biome_tag",             () -> BiomeTagRitualRequirement.CODEC);
    DeferredHolder<MapCodec<? extends RitualRequirement>, MapCodec<DimensionRitualRequirement>>               DIMENSION_REQUIREMENT             = RITUAL_REQUIREMENTS.register("dimension",             () -> DimensionRitualRequirement.CODEC);
    DeferredHolder<MapCodec<? extends RitualRequirement>, MapCodec<EnvironmentAttributeRitualRequirement<?>>> ENVIRONMENT_ATTRIBUTE_REQUIREMENT = RITUAL_REQUIREMENTS.register("environment_attribute", () -> EnvironmentAttributeRitualRequirement.CODEC);
    DeferredHolder<MapCodec<? extends RitualRequirement>, MapCodec<HeightRitualRequirement>>                  HEIGHT_REQUIREMENT                = RITUAL_REQUIREMENTS.register("height",                () -> HeightRitualRequirement.CODEC);
    DeferredHolder<MapCodec<? extends RitualRequirement>, MapCodec<IngredientRitualRequirement>>              INGREDIENT_REQUIREMENT            = RITUAL_REQUIREMENTS.register("ingredient",            () -> IngredientRitualRequirement.CODEC);
    DeferredHolder<MapCodec<? extends RitualRequirement>, MapCodec<StructureRitualRequirement>>               STRUCTURE_REQUIREMENT             = RITUAL_REQUIREMENTS.register("structure",             () -> StructureRitualRequirement.CODEC);

    DeferredHolder<MapCodec<? extends RitualTrigger<?>>, MapCodec<DroppedItemRitualTrigger>>   DROPPED_ITEM_TRIGGER    = RITUAL_TRIGGERS.register("dropped_item",    () -> DroppedItemRitualTrigger.CODEC);
    DeferredHolder<MapCodec<? extends RitualTrigger<?>>, MapCodec<GameEventRitualTrigger>>     GAME_EVENT_TRIGGER      = RITUAL_TRIGGERS.register("game_event",      () -> GameEventRitualTrigger.CODEC);
    DeferredHolder<MapCodec<? extends RitualTrigger<?>>, MapCodec<KillEntityRitualTrigger>>    KILL_ENTITY_TRIGGER     = RITUAL_TRIGGERS.register("kill_entity",     () -> KillEntityRitualTrigger.CODEC);
    DeferredHolder<MapCodec<? extends RitualTrigger<?>>, MapCodec<SetBlockStateRitualTrigger>> SET_BLOCK_STATE_TRIGGER = RITUAL_TRIGGERS.register("set_block_state", () -> SetBlockStateRitualTrigger.CODEC);
    DeferredHolder<MapCodec<? extends RitualTrigger<?>>, MapCodec<SpellCastRitualTrigger>>     SPELL_CAST_TRIGGER      = RITUAL_TRIGGERS.register("spell_cast",      () -> SpellCastRitualTrigger.CODEC);
    // @formatter:on
}
