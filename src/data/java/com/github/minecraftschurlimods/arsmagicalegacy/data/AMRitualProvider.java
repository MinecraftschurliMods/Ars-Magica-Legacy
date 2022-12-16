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
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.Tags;

import java.util.List;

public class AMRitualProvider extends RitualProvider {
    public AMRitualProvider() {
        super(ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        add("spawn_water_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.WATER_GUARDIAN.get()),
                ItemDropRitualTrigger.ingredients(Ingredient.of(ItemTags.BOATS), Ingredient.of(Items.WATER_BUCKET)))
                .with(new RitualStructureRequirement(PatchouliCompat.WATER_GUARDIAN_SPAWN_RITUAL))
                .with(new BiomeRequirement(tag(AMTags.Biomes.CAN_SPAWN_WATER_GUARDIAN)))
                .build());
        add("spawn_fire_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.FIRE_GUARDIAN.get()),
                ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(Affinity.WATER)))
                .with(new RitualStructureRequirement(PatchouliCompat.FIRE_GUARDIAN_SPAWN_RITUAL))
                .with(UltrawarmDimensionRequirement.INSTANCE)
                .build());
        add("spawn_earth_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.EARTH_GUARDIAN.get()),
                ItemDropRitualTrigger.tags(Tags.Items.GEMS_EMERALD, AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ))
                .with(new RitualStructureRequirement(PatchouliCompat.EARTH_GUARDIAN_SPAWN_RITUAL))
                .build());
        add("spawn_air_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.AIR_GUARDIAN.get()),
                ItemDropRitualTrigger.item(AMItems.TARMA_ROOT.get()))
                .with(new RitualStructureRequirement(PatchouliCompat.AIR_GUARDIAN_SPAWN_RITUAL))
                .with(HeightRequirement.atLeast(128))
                .build());
        add("spawn_ice_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.ICE_GUARDIAN.get()),
                EntitySummonTrigger.simple(EntityType.SNOW_GOLEM))
                .with(new RitualStructureRequirement(PatchouliCompat.ICE_GUARDIAN_SPAWN_RITUAL))
                .with(new BiomeRequirement(tag(Tags.Biomes.IS_COLD)))
                .build());
        add("spawn_lightning_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.LIGHTNING_GUARDIAN.get()),
                GameEventRitualTrigger.simple(GameEvent.LIGHTNING_STRIKE))
                .with(new RitualStructureRequirement(PatchouliCompat.LIGHTNING_GUARDIAN_SPAWN_RITUAL))
                .with(new BlockPos(0, -2, 0))
                .build());
        add("spawn_life_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.LIFE_GUARDIAN.get()),
                new EntityDeathTrigger(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true).build()).build()))
                .with(new RitualStructureRequirement(PatchouliCompat.LIFE_GUARDIAN_SPAWN_RITUAL))
                .with(MoonPhaseRequirement.exactly(0))
                .build());
        add("spawn_arcane_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.ARCANE_GUARDIAN.get()),
                ItemDropRitualTrigger.stackExact(ArsMagicaAPI.get().getBookStack()))
                .with(new RitualStructureRequirement(PatchouliCompat.ARCANE_GUARDIAN_SPAWN_RITUAL))
                .build());
        add("spawn_ender_guardian", builder(EntitySpawnRitualEffect.simple(AMEntities.ENDER_GUARDIAN.get()),
                ItemDropRitualTrigger.item(Items.ENDER_EYE))
                .with(new RitualStructureRequirement(PatchouliCompat.ENDER_GUARDIAN_SPAWN_RITUAL))
                .with(DimensionTypeRequirement.any(holder(BuiltinDimensionTypes.END)))
                .build());
        add("purification", builder(new PlaceBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.LIGHT.get())))
                .with(new RitualStructureRequirement(PatchouliCompat.PURIFICATION_RITUAL))
                .with(new ItemRequirement(List.of(Ingredient.of(AMItems.MOONSTONE.get())), 3))
                .build());
        add("corruption", builder(new PlaceBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.above()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FIRE_DAMAGE.get())))
                .with(new RitualStructureRequirement(PatchouliCompat.CORRUPTION_RITUAL))
                .with(new ItemRequirement(List.of(Ingredient.of(AMItems.SUNSTONE.get())), 3))
                .build());
        add("unlock_blizzard", builder(new LearnSkillRitualEffect(AMSpellParts.BLIZZARD.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.FROST.get(), AMSpellParts.STORM.get()), List.of(AMSpellParts.DAMAGE.get())))
                .build());
        add("unlock_daylight", builder(new LearnSkillRitualEffect(AMSpellParts.DAYLIGHT.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.DIVINE_INTERVENTION.get(), AMSpellParts.TRUE_SIGHT.get()), List.of(AMSpellParts.SOLAR.get())))
                .build());
        add("unlock_dismembering", builder(new LearnSkillRitualEffect(AMSpellParts.DISMEMBERING.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.PHYSICAL_DAMAGE.get()), List.of(AMSpellParts.DAMAGE.get(), AMSpellParts.PIERCING.get())))
                .build());
        add("unlock_effect_power", builder(new LearnSkillRitualEffect(AMSpellParts.EFFECT_POWER.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.AGILITY.get(), AMSpellParts.FLIGHT.get(), AMSpellParts.REFLECT.get(), AMSpellParts.SHRINK.get(), AMSpellParts.SWIFT_SWIM.get(), AMSpellParts.TEMPORAL_ANCHOR.get())))
                .build());
        add("unlock_falling_star", builder(new LearnSkillRitualEffect(AMSpellParts.FALLING_STAR.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.MAGIC_DAMAGE.get()), List.of(AMSpellParts.SOLAR.get())))
                .build());
        add("unlock_fire_rain", builder(new LearnSkillRitualEffect(AMSpellParts.FIRE_RAIN.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get(), AMSpellParts.STORM.get())))
                .build());
        add("unlock_mana_blast", builder(new LearnSkillRitualEffect(AMSpellParts.MANA_BLAST.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.EXPLOSION.get(), AMSpellParts.MANA_DRAIN.get())))
                .build());
        add("unlock_health_boost", builder(new LearnSkillRitualEffect(AMSpellParts.HEALTH_BOOST.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.SHIELD.get(), AMSpellParts.LIFE_TAP.get())))
                .build());
        add("unlock_moonrise", builder(new LearnSkillRitualEffect(AMSpellParts.MOONRISE.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.ENDER_INTERVENTION.get(), AMSpellParts.NIGHT_VISION.get()), List.of(AMSpellParts.LUNAR.get())))
                .build());
        add("unlock_prosperity", builder(new LearnSkillRitualEffect(AMSpellParts.PROSPERITY.get()),
                new SpellComponentCastRitualTrigger(List.of(AMSpellParts.DIG.get(), AMSpellParts.PHYSICAL_DAMAGE.get()), List.of(AMSpellParts.MINING_POWER.get(), AMSpellParts.SILK_TOUCH.get())))
                .build());
    }
}
