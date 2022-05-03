package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.EnderDragonDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntityDeathTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntitySummonTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.ItemDropRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MoonPhaseRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.UltrawarmDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.NBTIngredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class AMRitualProvider implements DataProvider {
    private static final Gson   GSON   = new GsonBuilder().create();
    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<ResourceLocation, JsonElement> elements = new HashMap<>();
    private final DataGenerator generator;

    public AMRitualProvider(final DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(final HashCache pCache) {
        addRituals();
        Path outputFolder = this.generator.getOutputFolder();
        elements.forEach((id, jsonElement) -> {
            try {
                DataProvider.save(GSON, pCache, jsonElement, createPath(outputFolder, id));
            } catch (IOException e) {
                LOGGER.error("Couldn't save ritual {}", id, e);
            }
        });
    }

    protected void addRituals() {
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_arcane_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.ARCANE_GUARDIAN_SPAWN_RITUAL), new ItemDropRitualTrigger(List.of(NBTIngredient.of(ArsMagicaAPI.get().getBookStack()))), List.of(), new EntitySpawnRitualEffect(AMEntities.ARCANE_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_air_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.AIR_GUARDIAN_SPAWN_RITUAL), new ItemDropRitualTrigger(List.of(Ingredient.of(AMItems.TARMA_ROOT.get()))), List.of(new HeightRequirement(MinMaxBounds.Ints.atLeast(128))), new EntitySpawnRitualEffect(AMEntities.AIR_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_earth_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.EARTH_GUARDIAN_SPAWN_RITUAL), new ItemDropRitualTrigger(List.of(Ingredient.of(Tags.Items.GEMS_EMERALD), Ingredient.of(AMTags.Items.GEMS_CHIMERITE), Ingredient.of(AMTags.Items.GEMS_TOPAZ))), List.of(), new EntitySpawnRitualEffect(AMEntities.EARTH_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_fire_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.FIRE_GUARDIAN_SPAWN_RITUAL), new ItemDropRitualTrigger(List.of(NBTIngredient.of(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(IAffinity.WATER)))), List.of(new UltrawarmDimensionRequirement()), new EntitySpawnRitualEffect(AMEntities.FIRE_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_water_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.WATER_GUARDIAN_SPAWN_RITUAL), new ItemDropRitualTrigger(List.of(Ingredient.of(ItemTags.BOATS), Ingredient.of(Items.WATER_BUCKET))), List.of(new BiomeRequirement(new HolderSet.Named<>(BuiltinRegistries.BIOME, AMTags.Biomes.CAN_SPAWN_WATER_GUARDIAN))), new EntitySpawnRitualEffect(AMEntities.WATER_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_ender_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.ENDER_GUARDIAN_SPAWN_RITUAL), new ItemDropRitualTrigger(List.of(Ingredient.of(Items.ENDER_EYE))), List.of(new EnderDragonDimensionRequirement()), new EntitySpawnRitualEffect(AMEntities.ENDER_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_ice_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.ICE_GUARDIAN_SPAWN_RITUAL), new EntitySummonTrigger(EntityPredicate.Builder.entity().of(EntityType.SNOW_GOLEM).build()), List.of(/*new BiomeRequirement(Tags.Biomes.IS_FROZEN)*/), new EntitySpawnRitualEffect(AMEntities.ICE_GUARDIAN.get())));//TODO: Add biome requirement
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_life_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.LIFE_GUARDIAN_SPAWN_RITUAL), new EntityDeathTrigger(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true).build()).build()), List.of(new MoonPhaseRequirement(MinMaxBounds.Ints.exactly(0))), new EntitySpawnRitualEffect(AMEntities.LIFE_GUARDIAN.get())));
        addRitual(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_lightning_guardian"), new Ritual(new Ritual.RitualStructure(PatchouliCompat.LIGHTNING_GUARDIAN_SPAWN_RITUAL), new GameEventRitualTrigger(HolderSet.direct(Holder.direct(GameEvent.LIGHTNING_STRIKE))), List.of(), new EntitySpawnRitualEffect(AMEntities.LIGHTNING_GUARDIAN.get())));
    }

    public void addRitual(ResourceLocation id, Ritual ritual) {
        elements.put(id, Ritual.CODEC.encodeStart(JsonOps.INSTANCE, ritual).getOrThrow(false, LOGGER::warn));
    }

    private static Path createPath(Path path, ResourceLocation resourceLocation) {
        return path.resolve("data/" + resourceLocation.getNamespace() + "/am_rituals/" + resourceLocation.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "AMRituals";
    }

}
