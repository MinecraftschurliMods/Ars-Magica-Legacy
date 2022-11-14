package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.RitualProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionTypeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.MoonPhaseRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.RitualStructureRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.UltrawarmDimensionRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntityDeathTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntitySummonTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.ItemDropRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;

public class AMRitualProvider extends RitualProvider {

    public AMRitualProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper);
    }

    @Override
    protected void addRituals(BiConsumer<ResourceLocation, Ritual> consumer) {
        builder("spawn_water_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.WATER_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.ingredients(Ingredient.of(ItemTags.BOATS), Ingredient.of(Items.WATER_BUCKET)))
                .with(new BiomeRequirement(AMTags.Biomes.CAN_SPAWN_WATER_GUARDIAN))
                .with(new EntitySpawnRitualEffect(AMEntities.WATER_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_fire_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.FIRE_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(Affinity.WATER)))
                .with(new UltrawarmDimensionRequirement())
                .with(new EntitySpawnRitualEffect(AMEntities.FIRE_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_earth_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.EARTH_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.tags(Tags.Items.GEMS_EMERALD, AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ))
                .with(new EntitySpawnRitualEffect(AMEntities.EARTH_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_air_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.AIR_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.item(AMItems.TARMA_ROOT.get()))
                .with(new HeightRequirement(MinMaxBounds.Ints.atLeast(128)))
                .with(new EntitySpawnRitualEffect(AMEntities.AIR_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_ice_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.ICE_GUARDIAN_SPAWN_RITUAL))
                .with(new EntitySummonTrigger(EntityType.SNOW_GOLEM))
                .with(new BiomeRequirement(Tags.Biomes.IS_SNOWY)) // TODO check biome requirement
                .with(new EntitySpawnRitualEffect(AMEntities.ICE_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_lightning_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.LIGHTNING_GUARDIAN_SPAWN_RITUAL))
                .with(new GameEventRitualTrigger(GameEvent.LIGHTNING_STRIKE))
                .with(new EntitySpawnRitualEffect(AMEntities.LIGHTNING_GUARDIAN.get()))
                .with(new BlockPos(0, -2, 0))
                .build(consumer);
        builder("spawn_life_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.LIFE_GUARDIAN_SPAWN_RITUAL))
                .with(new EntityDeathTrigger(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true).build()).build()))
                .with(new MoonPhaseRequirement(0))
                .with(new EntitySpawnRitualEffect(AMEntities.LIFE_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_arcane_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.ARCANE_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getBookStack()))
                .with(new EntitySpawnRitualEffect(AMEntities.ARCANE_GUARDIAN.get()))
                .build(consumer);
        builder("spawn_ender_guardian")
                .with(new RitualStructureRequirement(PatchouliCompat.ENDER_GUARDIAN_SPAWN_RITUAL))
                .with(ItemDropRitualTrigger.item(Items.ENDER_EYE))
                .with(new DimensionTypeRequirement(BuiltinDimensionTypes.END))
                .with(new EntitySpawnRitualEffect(AMEntities.ENDER_GUARDIAN.get()))
                .build(consumer);
    }
}
