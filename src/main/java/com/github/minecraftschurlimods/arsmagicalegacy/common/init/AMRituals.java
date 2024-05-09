package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
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
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.RITUAL_EFFECT_TYPES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.RITUAL_REQUIREMENT_TYPES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.RITUAL_TRIGGER_TYPES;

@NonExtendable
public interface AMRituals {
    Holder<MapCodec<? extends RitualTrigger>> ENTITY_DEATH_TRIGGER = RITUAL_TRIGGER_TYPES.register("entity_death", () -> EntityDeathTrigger.CODEC);
    Holder<MapCodec<? extends RitualTrigger>> ENTITY_SUMMON_TRIGGER = RITUAL_TRIGGER_TYPES.register("entity_summon", () -> EntitySummonTrigger.CODEC);
    Holder<MapCodec<? extends RitualTrigger>> ITEM_DROP_TRIGGER = RITUAL_TRIGGER_TYPES.register("item_drop", () -> ItemDropRitualTrigger.CODEC);
    Holder<MapCodec<? extends RitualTrigger>> GAME_EVENT_TRIGGER = RITUAL_TRIGGER_TYPES.register("game_event", () -> GameEventRitualTrigger.CODEC);
    Holder<MapCodec<? extends RitualTrigger>> SPELL_COMPONENT_CAST_TRIGGER = RITUAL_TRIGGER_TYPES.register("spell_component_cast", () -> SpellComponentCastRitualTrigger.CODEC);

    Holder<MapCodec<? extends RitualEffect>> SPAWN_ENTITY_EFFECT = RITUAL_EFFECT_TYPES.register("spawn_entity", () -> EntitySpawnRitualEffect.CODEC);
    Holder<MapCodec<? extends RitualEffect>> LEARN_SKILL_EFFECT = RITUAL_EFFECT_TYPES.register("learn_skill", () -> LearnSkillRitualEffect.CODEC);
    Holder<MapCodec<? extends RitualEffect>> PLACE_BLOCK_EFFECT = RITUAL_EFFECT_TYPES.register("place_block", () -> PlaceBlockRitualEffect.CODEC);

    Holder<MapCodec<? extends RitualRequirement>> BIOME_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("biome", () -> BiomeRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> DIMENSION_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("dimension", () -> DimensionRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> DIMENSION_TYPE_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("dimension_type", () -> DimensionTypeRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> HEIGHT_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("height", () -> HeightRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> MAGIC_LEVEL_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("magic_level", () -> MagicLevelRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> MOON_PHASE_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("moon_phase", () -> MoonPhaseRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> RITUAL_STRUCTURE_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("structure", () -> RitualStructureRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> ULTRAWARM_DIMENSION_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("ultrawarm_dimension", () -> UltrawarmDimensionRequirement.CODEC);
    Holder<MapCodec<? extends RitualRequirement>> ITEM_REQUIREMENT = RITUAL_REQUIREMENT_TYPES.register("item", () -> ItemRequirement.CODEC);

    /**
     * Empty method that is required for classloading
     */
    @ApiStatus.Internal
    static void register() {}
}
