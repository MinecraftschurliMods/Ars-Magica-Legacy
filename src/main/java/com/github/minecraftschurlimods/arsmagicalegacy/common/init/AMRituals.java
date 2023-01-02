package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.LearnSkillRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.PlaceBlockRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionTypeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.ItemRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MagicLevelRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MoonPhaseRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.RitualStructureRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.UltrawarmDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntityDeathTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntitySummonTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.ItemDropRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.SpellComponentCastRitualTrigger;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.RITUAL_EFFECT_TYPES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.RITUAL_REQUIREMENT_TYPES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.RITUAL_TRIGGER_TYPES;

@NonExtendable
public interface AMRituals {
    RegistryObject<Codec<EntityDeathTrigger>> ENTITY_DEATH_TRIGGER = RITUAL_TRIGGER_TYPES.register("entity_death", () -> EntityDeathTrigger.CODEC);
    RegistryObject<Codec<EntitySummonTrigger>> ENTITY_SUMMON_TRIGGER = RITUAL_TRIGGER_TYPES.register("entity_summon", () -> EntitySummonTrigger.CODEC);
    RegistryObject<Codec<ItemDropRitualTrigger>> ITEM_DROP_TRIGGER = RITUAL_TRIGGER_TYPES.register("item_drop", () -> ItemDropRitualTrigger.CODEC);
    RegistryObject<Codec<GameEventRitualTrigger>> GAME_EVENT_TRIGGER = RITUAL_TRIGGER_TYPES.register("game_event", () -> GameEventRitualTrigger.CODEC);
    RegistryObject<Codec<SpellComponentCastRitualTrigger>> SPELL_COMPONENT_CAST_TRIGGER = RITUAL_TRIGGER_TYPES.register("spell_component_cast", () -> SpellComponentCastRitualTrigger.CODEC);

    RegistryObject<Codec<EntitySpawnRitualEffect>> SPAWN_ENTITY_EFFECT = RITUAL_EFFECT_TYPES.register("spawn_entity", () -> EntitySpawnRitualEffect.CODEC);
    RegistryObject<Codec<LearnSkillRitualEffect>> LEARN_SKILL_EFFECT = RITUAL_EFFECT_TYPES.register("learn_skill", () -> LearnSkillRitualEffect.CODEC);
    RegistryObject<Codec<PlaceBlockRitualEffect>> PLACE_BLOCK_EFFECT = RITUAL_EFFECT_TYPES.register("place_block", () -> PlaceBlockRitualEffect.CODEC);

    RegistryObject<Codec<BiomeRequirement>> BIOME_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("biome", () -> BiomeRequirement.CODEC);
    RegistryObject<Codec<DimensionRequirement>> DIMENSION_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("dimension", () -> DimensionRequirement.CODEC);
    RegistryObject<Codec<DimensionTypeRequirement>> DIMENSION_TYPE_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("dimension_type", () -> DimensionTypeRequirement.CODEC);
    RegistryObject<Codec<HeightRequirement>> HEIGHT_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("height", () -> HeightRequirement.CODEC);
    RegistryObject<Codec<MagicLevelRequirement>> MAGIC_LEVEL_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("magic_level", () -> MagicLevelRequirement.CODEC);
    RegistryObject<Codec<MoonPhaseRequirement>> MOON_PHASE_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("moon_phase", () -> MoonPhaseRequirement.CODEC);
    RegistryObject<Codec<RitualStructureRequirement>> RITUAL_STRUCTURE_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("structure", () -> RitualStructureRequirement.CODEC);
    RegistryObject<Codec<UltrawarmDimensionRequirement>> ULTRAWARM_DIMENSION_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("ultrawarm_dimension", () -> UltrawarmDimensionRequirement.CODEC);
    RegistryObject<Codec<ItemRequirement>> ITEM_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("item", () -> ItemRequirement.CODEC);

    /**
     * Empty method that is required for classloading
     */
    @ApiStatus.Internal
    static void register() {}
}
