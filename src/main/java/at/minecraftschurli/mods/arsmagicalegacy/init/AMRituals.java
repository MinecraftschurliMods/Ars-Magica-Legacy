package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.effect.LearnSkillRitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.effect.SetBlockRitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.effect.SpawnEntityRitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.BiomeTagRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.DimensionRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.EnvironmentAttributeRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.HeightRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.IngredientRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement.StructureRitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.DroppedItemRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.GameEventRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.KillEntityRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.SetBlockStateRitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger.SpellCastRitualTrigger;
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
