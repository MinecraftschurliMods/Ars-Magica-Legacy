package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.PlaceBlockRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionTypeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.EnderDragonDimensionRequirement;
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
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

public final class RitualManager extends CodecDataManager<Ritual> {
    private static final Lazy<RitualManager> INSTANCE = Lazy.concurrentOf(RitualManager::new);
    private static final BiMap<ResourceLocation, Codec<? extends RitualTrigger>> ritualTriggerCodecs = HashBiMap.create();
    private static final BiMap<ResourceLocation, Codec<? extends RitualRequirement>> ritualRequirementCodecs = HashBiMap.create();
    private static final BiMap<ResourceLocation, Codec<? extends RitualEffect>> ritualEffectCodecs = HashBiMap.create();

    private RitualManager() {
        super("am_rituals", Ritual.CODEC, LogManager.getLogger());
        useRegistryOps();
        init();
    }

    public static RitualManager instance() {
        return INSTANCE.get();
    }

    public static void registerRitualRequirement(ResourceLocation id, Codec<? extends RitualRequirement> codec) {
        ritualRequirementCodecs.put(id, codec);
    }

    public static void registerRitualEffect(ResourceLocation id, Codec<? extends RitualEffect> codec) {
        ritualEffectCodecs.put(id, codec);
    }

    public static void registerRitualTrigger(ResourceLocation id, Codec<? extends RitualTrigger> codec) {
        ritualTriggerCodecs.put(id, codec);
    }

    public static ResourceLocation getRitualRequirementType(RitualRequirement ritualRequirement) {
        return ritualRequirementCodecs.inverse().get(ritualRequirement.codec());
    }

    public static Codec<? extends RitualRequirement> getRitualRequirementCodec(ResourceLocation resourceLocation) {
        return ritualRequirementCodecs.get(resourceLocation);
    }

    public static ResourceLocation getRitualTriggerType(RitualTrigger ritualTrigger) {
        return ritualTriggerCodecs.inverse().get(ritualTrigger.codec());
    }

    public static Codec<? extends RitualTrigger> getRitualTriggerCodec(ResourceLocation resourceLocation) {
        return ritualTriggerCodecs.get(resourceLocation);
    }

    public static ResourceLocation getRitualEffectType(RitualEffect ritualEffect) {
        return ritualEffectCodecs.inverse().get(ritualEffect.codec());
    }

    public static Codec<? extends RitualEffect> getRitualEffectCodec(ResourceLocation resourceLocation) {
        return ritualEffectCodecs.get(resourceLocation);
    }

    private static void init() {
        registerRitualTrigger(new ResourceLocation(ArsMagicaAPI.MOD_ID, "entity_death"), EntityDeathTrigger.CODEC);
        registerRitualTrigger(new ResourceLocation(ArsMagicaAPI.MOD_ID, "entity_summon"), EntitySummonTrigger.CODEC);
        registerRitualTrigger(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item_drop"), ItemDropRitualTrigger.CODEC);
        registerRitualTrigger(new ResourceLocation(ArsMagicaAPI.MOD_ID, "game_event"), GameEventRitualTrigger.CODEC);
        registerRitualTrigger(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_component_cast"), SpellComponentCastRitualTrigger.CODEC);
        registerRitualEffect(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_entity"), EntitySpawnRitualEffect.CODEC);
        registerRitualEffect(new ResourceLocation(ArsMagicaAPI.MOD_ID, "place_block"), PlaceBlockRitualEffect.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "biome"), BiomeRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dimension"), DimensionRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dimension_type"), DimensionTypeRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_dragon_dimension"), EnderDragonDimensionRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "height"), HeightRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic_level"), MagicLevelRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "moon_phase"), MoonPhaseRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "structure"), RitualStructureRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ultrawarm_dimension"), UltrawarmDimensionRequirement.CODEC);
        registerRitualRequirement(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item"), ItemRequirement.CODEC);
    }
}
