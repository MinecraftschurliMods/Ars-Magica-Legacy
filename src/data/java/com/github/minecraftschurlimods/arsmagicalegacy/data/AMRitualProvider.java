package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.RitualProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.EntitySpawnRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.LearnSkillRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.PlaceBlockRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionTypeRequirement;
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
import com.google.gson.JsonElement;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;

public class AMRitualProvider extends RitualProvider {
    public AMRitualProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate() {
        add(builder("spawn_water_guardian", new EntitySpawnRitualEffect(AMEntities.WATER_GUARDIAN.get()),
                ItemDropRitualTrigger.ingredients(Ingredient.of(ItemTags.BOATS), Ingredient.of(Items.WATER_BUCKET)))
                .with(new RitualStructureRequirement(PatchouliCompat.WATER_GUARDIAN_SPAWN_RITUAL))
                .with(new BiomeRequirement(AMTags.Biomes.CAN_SPAWN_WATER_GUARDIAN)));
        add(builder("spawn_fire_guardian", new EntitySpawnRitualEffect(AMEntities.FIRE_GUARDIAN.get()),
                ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(Affinity.WATER)))
                .with(new RitualStructureRequirement(PatchouliCompat.FIRE_GUARDIAN_SPAWN_RITUAL))
                .with(new UltrawarmDimensionRequirement()));
        add(builder("spawn_earth_guardian", new EntitySpawnRitualEffect(AMEntities.EARTH_GUARDIAN.get()),
                ItemDropRitualTrigger.tags(Tags.Items.GEMS_EMERALD, AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ))
                .with(new RitualStructureRequirement(PatchouliCompat.EARTH_GUARDIAN_SPAWN_RITUAL)));
        add(builder("spawn_air_guardian", new EntitySpawnRitualEffect(AMEntities.AIR_GUARDIAN.get()),
                ItemDropRitualTrigger.item(AMItems.TARMA_ROOT.get()))
                .with(new RitualStructureRequirement(PatchouliCompat.AIR_GUARDIAN_SPAWN_RITUAL))
                .with(new HeightRequirement(MinMaxBounds.Ints.atLeast(128))));
        add(builder("spawn_ice_guardian", new EntitySpawnRitualEffect(AMEntities.ICE_GUARDIAN.get()),
                new EntitySummonTrigger(EntityType.SNOW_GOLEM))
                .with(new RitualStructureRequirement(PatchouliCompat.ICE_GUARDIAN_SPAWN_RITUAL))
                .with(new BiomeRequirement(Tags.Biomes.IS_COLD)));
        add(builder("spawn_lightning_guardian", new EntitySpawnRitualEffect(AMEntities.LIGHTNING_GUARDIAN.get()),
                new GameEventRitualTrigger(GameEvent.LIGHTNING_STRIKE))
                .with(new RitualStructureRequirement(PatchouliCompat.LIGHTNING_GUARDIAN_SPAWN_RITUAL))
                .with(new BlockPos(0, -2, 0)));
        add(builder("spawn_life_guardian", new EntitySpawnRitualEffect(AMEntities.LIFE_GUARDIAN.get()),
                new EntityDeathTrigger(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true).build()).build()))
                .with(new RitualStructureRequirement(PatchouliCompat.LIFE_GUARDIAN_SPAWN_RITUAL))
                .with(new MoonPhaseRequirement(0)));
        add(builder("spawn_arcane_guardian", new EntitySpawnRitualEffect(AMEntities.ARCANE_GUARDIAN.get()),
                ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getBookStack()))
                .with(new RitualStructureRequirement(PatchouliCompat.ARCANE_GUARDIAN_SPAWN_RITUAL)));
        add(builder("spawn_ender_guardian", new EntitySpawnRitualEffect(AMEntities.ENDER_GUARDIAN.get()),
                ItemDropRitualTrigger.item(Items.ENDER_EYE))
                .with(new RitualStructureRequirement(PatchouliCompat.ENDER_GUARDIAN_SPAWN_RITUAL))
                .with(new DimensionTypeRequirement(BuiltinDimensionTypes.END)));
        add(builder("purification", new PlaceBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.LIGHT.get())))
                .with(new RitualStructureRequirement(PatchouliCompat.PURIFICATION_RITUAL))
                .with(new ItemRequirement(List.of(Ingredient.of(AMItems.MOONSTONE.get())), 3)));
        add(builder("corruption", new PlaceBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.above()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FIRE_DAMAGE.get())))
                .with(new RitualStructureRequirement(PatchouliCompat.CORRUPTION_RITUAL))
                .with(new ItemRequirement(List.of(Ingredient.of(AMItems.SUNSTONE.get())), 3)));
        add(builder("unlock_blizzard", new LearnSkillRitualEffect(AMSpellParts.BLIZZARD.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.FROST.get(), AMSpellParts.STORM.get()), List.of(AMSpellParts.DAMAGE.get()))));
        add(builder("unlock_daylight", new LearnSkillRitualEffect(AMSpellParts.DAYLIGHT.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.DIVINE_INTERVENTION.get(), AMSpellParts.TRUE_SIGHT.get()), List.of(AMSpellParts.SOLAR.get()))));
        add(builder("unlock_dismembering", new LearnSkillRitualEffect(AMSpellParts.DISMEMBERING.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.PHYSICAL_DAMAGE.get()), List.of(AMSpellParts.DAMAGE.get(), AMSpellParts.PIERCING.get()))));
        add(builder("unlock_effect_power", new LearnSkillRitualEffect(AMSpellParts.EFFECT_POWER.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.AGILITY.get(), AMSpellParts.FLIGHT.get(), AMSpellParts.REFLECT.get(), AMSpellParts.SHRINK.get(), AMSpellParts.SWIFT_SWIM.get(), AMSpellParts.TEMPORAL_ANCHOR.get()))));
        add(builder("unlock_falling_star", new LearnSkillRitualEffect(AMSpellParts.FALLING_STAR.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.MAGIC_DAMAGE.get()), List.of(AMSpellParts.SOLAR.get()))));
        add(builder("unlock_fire_rain", new LearnSkillRitualEffect(AMSpellParts.FIRE_RAIN.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get(), AMSpellParts.STORM.get()))));
        add(builder("unlock_mana_blast", new LearnSkillRitualEffect(AMSpellParts.MANA_BLAST.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.EXPLOSION.get(), AMSpellParts.MANA_DRAIN.get()))));
        add(builder("unlock_health_boost", new LearnSkillRitualEffect(AMSpellParts.HEALTH_BOOST.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.SHIELD.get(), AMSpellParts.LIFE_TAP.get()))));
        add(builder("unlock_moonrise", new LearnSkillRitualEffect(AMSpellParts.MOONRISE.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.ENDER_INTERVENTION.get(), AMSpellParts.NIGHT_VISION.get()), List.of(AMSpellParts.LUNAR.get()))));
        add(builder("unlock_prosperity", new LearnSkillRitualEffect(AMSpellParts.PROSPERITY.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.DIG.get(), AMSpellParts.PHYSICAL_DAMAGE.get()), List.of(AMSpellParts.MINING_POWER.get(), AMSpellParts.SILK_TOUCH.get()))));
    }
}
