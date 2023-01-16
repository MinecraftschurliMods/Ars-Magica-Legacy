package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.LearnSkillRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.PlaceBlockRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.EnderDragonDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.ItemRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MoonPhaseRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.RitualStructureRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.UltrawarmDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntityDeathTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntitySummonTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.ItemDropRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.SpellComponentCastRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class AMRitualProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, JsonElement> elements = new HashMap<>();
    private final DataGenerator generator;

    public AMRitualProvider(final DataGenerator generator) {
        this.generator = generator;
    }

    private static Path createPath(Path path, ResourceLocation resourceLocation) {
        return path.resolve("data/" + resourceLocation.getNamespace() + "/am_rituals/" + resourceLocation.getPath() + ".json");
    }

    public static RitualBuilder builder(ResourceLocation id) {
        return new RitualBuilder(id);
    }

    @Override
    public void run(final HashCache pCache) {
        addRituals(this::addRitual);
        Path outputFolder = this.generator.getOutputFolder();
        elements.forEach((id, jsonElement) -> {
            try {
                DataProvider.save(GSON, pCache, jsonElement, createPath(outputFolder, id));
            } catch (IOException e) {
                LOGGER.error("Couldn't save ritual {}", id, e);
            }
        });
    }

    @Override
    public String getName() {
        return "AMRituals[" + ArsMagicaAPI.MOD_ID + "]";
    }

    protected void addRituals(BiConsumer<ResourceLocation, Ritual> consumer) {
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_water_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.WATER_GUARDIAN_SPAWN_RITUAL))
                .with(new BiomeRequirement(AMTags.Biomes.CAN_SPAWN_WATER_GUARDIAN))
                .with(ItemDropRitualTrigger.ingredients(Ingredient.of(ItemTags.BOATS), Ingredient.of(Items.WATER_BUCKET)))
                .with(new EntitySpawnRitualEffect(AMEntities.WATER_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_fire_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.FIRE_GUARDIAN_SPAWN_RITUAL))
                .with(new UltrawarmDimensionRequirement())
                .with(ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(IAffinity.WATER)))
                .with(new EntitySpawnRitualEffect(AMEntities.FIRE_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_earth_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.EARTH_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.tags(Tags.Items.GEMS_EMERALD, AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ))
                .with(new EntitySpawnRitualEffect(AMEntities.EARTH_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_air_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.AIR_GUARDIAN_SPAWN_RITUAL))
                .with(new HeightRequirement(MinMaxBounds.Ints.atLeast(128)))
                .with(ItemDropRitualTrigger.item(AMItems.TARMA_ROOT.get()))
                .with(new EntitySpawnRitualEffect(AMEntities.AIR_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_ice_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.ICE_GUARDIAN_SPAWN_RITUAL))
                .with(new BiomeRequirement(Tags.Biomes.IS_COLD))
                .with(new EntitySummonTrigger(EntityType.SNOW_GOLEM))
                .with(new EntitySpawnRitualEffect(AMEntities.ICE_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_lightning_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.LIGHTNING_GUARDIAN_SPAWN_RITUAL))
                .with(new GameEventRitualTrigger(GameEvent.LIGHTNING_STRIKE))
                .with(new EntitySpawnRitualEffect(AMEntities.LIGHTNING_GUARDIAN.get()))
                .with(new BlockPos(0, -2, 0))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_life_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.LIFE_GUARDIAN_SPAWN_RITUAL))
                .with(new MoonPhaseRequirement(0))
                .with(new EntityDeathTrigger(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true).build()).build()))
                .with(new EntitySpawnRitualEffect(AMEntities.LIFE_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_arcane_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.ARCANE_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getBookStack()))
                .with(new EntitySpawnRitualEffect(AMEntities.ARCANE_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_ender_guardian"))
                .with(new RitualStructureRequirement(PatchouliCompat.ENDER_GUARDIAN_SPAWN_RITUAL))
                .with(new EnderDragonDimensionRequirement())
                .with(ItemDropRitualTrigger.item(Items.ENDER_EYE))
                .with(new EntitySpawnRitualEffect(AMEntities.ENDER_GUARDIAN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "purification"))
                .with(new RitualStructureRequirement(PatchouliCompat.PURIFICATION_RITUAL))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.LIGHT.get())))
                .with(new ItemRequirement(List.of(Ingredient.of(AMItems.MOONSTONE.get())), 3))
                .with(new PlaceBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "corruption"))
                .with(new RitualStructureRequirement(PatchouliCompat.CORRUPTION_RITUAL))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FIRE_DAMAGE.get())))
                .with(new ItemRequirement(List.of(Ingredient.of(AMItems.SUNSTONE.get())), 3))
                .with(new PlaceBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.above()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_blizzard"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.FROST.get(), AMSpellParts.STORM.get()), List.of(AMSpellParts.DAMAGE.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.BLIZZARD.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_daylight"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.DIVINE_INTERVENTION.get(), AMSpellParts.TRUE_SIGHT.get()), List.of(AMSpellParts.SOLAR.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.DAYLIGHT.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_dismembering"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.PHYSICAL_DAMAGE.get()), List.of(AMSpellParts.DAMAGE.get(), AMSpellParts.PIERCING.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.DISMEMBERING.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_effect_power"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.AGILITY.get(), AMSpellParts.FLIGHT.get(), AMSpellParts.REFLECT.get(), AMSpellParts.SHRINK.get(), AMSpellParts.SWIFT_SWIM.get(), AMSpellParts.TEMPORAL_ANCHOR.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.EFFECT_POWER.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_falling_star"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.MAGIC_DAMAGE.get()), List.of(AMSpellParts.SOLAR.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.FALLING_STAR.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_fire_rain"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get(), AMSpellParts.STORM.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.FIRE_RAIN.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_mana_blast"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.EXPLOSION.get(), AMSpellParts.MANA_DRAIN.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.MANA_BLAST.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_mana_shield"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.SHIELD.get(), AMSpellParts.LIFE_TAP.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.MANA_SHIELD.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_moonrise"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.ENDER_INTERVENTION.get(), AMSpellParts.NIGHT_VISION.get()), List.of(AMSpellParts.LUNAR.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.MOONRISE.get()))
                .build(consumer);
        builder(new ResourceLocation(ArsMagicaAPI.MOD_ID, "unlock_prosperity"))
                .with(new SpellComponentCastRitualTrigger(List.of(AMSpellParts.DIG.get(), AMSpellParts.PHYSICAL_DAMAGE.get()), List.of(AMSpellParts.MINING_POWER.get(), AMSpellParts.SILK_TOUCH.get())))
                .with(new LearnSkillRitualEffect(AMSpellParts.PROSPERITY.get()))
                .build(consumer);
    }

    private void addRitual(ResourceLocation id, Ritual ritual) {
        elements.put(id, Ritual.CODEC.encodeStart(RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), ritual).getOrThrow(false, LOGGER::warn));
    }

    protected static class RitualBuilder {
        private final List<IRitualRequirement> requirements = new ArrayList<>();
        private final ResourceLocation id;
        private RitualStructureRequirement structure;
        private IRitualEffect effect;
        private IRitualTrigger trigger;
        private BlockPos offset = BlockPos.ZERO;

        private RitualBuilder(ResourceLocation id) {
            this.id = id;
        }

        public RitualBuilder with(RitualStructureRequirement structure) {
            this.structure = structure;
            return this;
        }

        public RitualBuilder with(IRitualRequirement requirement) {
            this.requirements.add(requirement);
            return this;
        }

        public RitualBuilder with(IRitualRequirement... requirements) {
            return with(Arrays.asList(requirements));
        }

        public RitualBuilder with(List<IRitualRequirement> requirements) {
            this.requirements.addAll(requirements);
            return this;
        }

        public RitualBuilder with(IRitualEffect effect) {
            this.effect = effect;
            return this;
        }

        public RitualBuilder with(IRitualTrigger trigger) {
            this.trigger = trigger;
            return this;
        }

        public RitualBuilder with(BlockPos offset) {
            this.offset = offset;
            return this;
        }

        private Ritual buildInternal() {
            if (trigger == null) throw new IllegalStateException("Trigger must be set");
            if (effect == null) throw new IllegalStateException("Effect must be set");
            if (structure != null) {
                requirements.add(0, structure);
            }
            return new Ritual(trigger, requirements, effect, offset);
        }

        public void build(BiConsumer<ResourceLocation, Ritual> consumer) {
            consumer.accept(id, buildInternal());
        }
    }
}
