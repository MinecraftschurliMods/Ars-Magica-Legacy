package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.BLUE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.GREEN;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.RED;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts.*;

class AMSkillProvider extends SkillProvider {
    private static final ResourceLocation OFFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense");
    private static final ResourceLocation DEFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "defense");
    private static final ResourceLocation UTILITY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "utility");
    private static final ResourceLocation TALENT = new ResourceLocation(ArsMagicaAPI.MOD_ID, "talent");

    AMSkillProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        offense(AOE)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(FROST_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        offense(BEAM)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(AOE.getId())
                .build(consumer);
        utility(CHAIN)
                .setPosition(255, 165)
                .addCost(RED.get())
                .addParent(GROW.getId())
                .build(consumer);
        utility(CHANNEL)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build(consumer);
        defense(CONTINGENCY_DAMAGE)
                .setPosition(255, 345)
                .addCost(RED.get())
                .addParent(REFLECT.getId())
                .build(consumer);
        utility(CONTINGENCY_DEATH)
                .setPosition(165, 345)
                .addCost(RED.get())
                .addParent(ENDER_INTERVENTION.getId())
                .build(consumer);
        defense(CONTINGENCY_FALL)
                .setPosition(75, 120)
                .addCost(RED.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        offense(CONTINGENCY_FIRE)
                .setPosition(75, 165)
                .addCost(RED.get())
                .addParent(FORGE.getId())
                .build(consumer);
        defense(CONTINGENCY_HEALTH)
                .setPosition(300, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build(consumer);
        offense(PROJECTILE)
                .setPosition(210, 30)
                .addCost(BLUE.get())
                .build(consumer);
        defense(RUNE)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(AGILITY.getId())
                .addParent(ENTANGLE.getId())
                .build(consumer);
        defense(SELF)
                .setPosition(165, 30)
                .addCost(BLUE.get())
                .build(consumer);
        utility(TOUCH)
                .setPosition(120, 30)
                .addCost(BLUE.get())
                .build(consumer);
        defense(WALL)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(REPEL.getId())
                .build(consumer);
        offense(WAVE)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .addParent(VELOCITY.getId())
                .build(consumer);
        defense(ZONE)
                .setPosition(255, 210)
                .addCost(RED.get())
                .addParent(DISPEL.getId())
                .build(consumer);
        offense(DROWNING_DAMAGE)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        offense(FIRE_DAMAGE)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        offense(FROST_DAMAGE)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        offense(LIGHTNING_DAMAGE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        offense(MAGIC_DAMAGE)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        offense(PHYSICAL_DAMAGE)
                .setPosition(210, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build(consumer);
        defense(ABSORPTION)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build(consumer);
        offense(BLINDNESS)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build(consumer);
        defense(HASTE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        utility(INVISIBILITY)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(TRUE_SIGHT.getId())
                .build(consumer);
        defense(JUMP_BOOST)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId())
                .build(consumer);
        defense(LEVITATION)
                .setPosition(120, 210)
                .addCost(GREEN.get())
                .addParent(GRAVITY_WELL.getId())
                .build(consumer);
        utility(NIGHT_VISION)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        defense(REGENERATION)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId())
                .build(consumer);
        defense(SLOWNESS)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        defense(SLOW_FALLING)
                .setPosition(120, 120)
                .addCost(BLUE.get())
                .addParent(JUMP_BOOST.getId())
                .build(consumer);
        utility(WATER_BREATHING)
                .setPosition(255, 255)
                .addCost(BLUE.get())
                .addParent(CREATE_WATER.getId())
                .build(consumer);
        defense(AGILITY)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(SWIFT_SWIM.getId())
                .build(consumer);
        offense(ASTRAL_DISTORTION)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        defense(ENTANGLE)
                .setPosition(75, 255)
                .addCost(GREEN.get())
                .addParent(REPEL.getId())
                .build(consumer);
        defense(FLIGHT)
                .setPosition(120, 255)
                .addCost(RED.get())
                .addParent(LEVITATION.getId())
                .build(consumer);
        offense(FURY)
                .setPosition(165, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .build(consumer);
        defense(GRAVITY_WELL)
                .setPosition(120, 165)
                .addCost(GREEN.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        defense(SHIELD)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(ZONE.getId())
                .build(consumer);
        defense(SHRINK)
                .setPosition(300, 75)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build(consumer);
        offense(SILENCE)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(ASTRAL_DISTORTION.getId())
                .build(consumer);
        defense(SWIFT_SWIM)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(HASTE.getId())
                .build(consumer);
        defense(TEMPORAL_ANCHOR)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(REFLECT.getId())
                .build(consumer);
        utility(TRUE_SIGHT)
                .setPosition(75, 210)
                .addCost(BLUE.get())
                .addParent(NIGHT_VISION.getId())
                .build(consumer);
        offense(WATERY_GRAVE)
                .setPosition(345, 165)
                .addCost(GREEN.get())
                .addParent(DROWNING_DAMAGE.getId())
                .build(consumer);
        utility(ATTRACT)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build(consumer);
        utility(BANISH_RAIN)
                .setPosition(255, 300)
                .addCost(GREEN.get())
                .addParent(DROUGHT.getId())
                .build(consumer);
        utility(BLINK)
                .setPosition(30, 300)
                .addCost(GREEN.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build(consumer);
        offense(BLIZZARD)
                .setPosition(30, 30)
                .setHidden()
                .build(consumer);
        utility(CHARM)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        utility(CREATE_WATER)
                .setPosition(210, 255)
                .addCost(GREEN.get())
                .addParent(PLANT.getId())
                .build(consumer);
        utility(DAYLIGHT)
                .setPosition(30, 30)
                .setHidden()
                .build(consumer);
        utility(DIG)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId())
                .build(consumer);
        defense(DISARM)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(DISPEL.getId())
                .build(consumer);
        defense(DISPEL)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(HEAL.getId())
                .build(consumer);
        utility(DIVINE_INTERVENTION)
                .setPosition(30, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId())
                .build(consumer);
        utility(DROUGHT)
                .setPosition(210, 300)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId())
                .build(consumer);
        utility(ENDER_INTERVENTION)
                .setPosition(120, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId())
                .build(consumer);
        offense(EXPLOSION)
                .setPosition(120, 300)
                .addCost(RED.get())
                .addParent(FURY.getId())
                .build(consumer);
        offense(FALLING_STAR)
                .setPosition(30, 75)
                .setHidden()
                .build(consumer);
        offense(FIRE_RAIN)
                .setPosition(30, 120)
                .setHidden()
                .build(consumer);
        offense(FLING)
                .setPosition(300, 255)
                .addCost(GREEN.get())
                .addParent(KNOCKBACK.getId())
                .build(consumer);
        offense(FORGE)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(IGNITION.getId())
                .build(consumer);
        offense(FROST)
                .setPosition(300, 120)
                .addCost(GREEN.get())
                .addParent(FROST_DAMAGE.getId())
                .build(consumer);
        utility(GROW)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(PLANT.getId())
                .build(consumer);
        utility(HARVEST)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(PLOW.getId())
                .build(consumer);
        defense(HEAL)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build(consumer);
        offense(IGNITION)
                .setPosition(120, 120)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .build(consumer);
        offense(KNOCKBACK)
                .setPosition(300, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        defense(LIFE_DRAIN)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId())
                .build(consumer);
        defense(LIFE_TAP)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(HEAL.getId())
                .build(consumer);
        utility(LIGHT)
                .setPosition(120, 165)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        offense(MANA_BLAST)
                .setPosition(30, 165)
                .setHidden()
                .build(consumer);
        defense(MANA_DRAIN)
                .setPosition(210, 210)
                .addCost(GREEN.get())
                .addParent(LIFE_DRAIN.getId())
                .build(consumer);
        defense(MANA_SHIELD)
                .setPosition(30, 30)
                .setHidden()
                .build(consumer);
        utility(MOONRISE)
                .setPosition(30, 75)
                .setHidden()
                .build(consumer);
        utility(PLACE_BLOCK)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        utility(PLANT)
                .setPosition(210, 210)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        utility(PLOW)
                .setPosition(210, 165)
                .addCost(BLUE.get())
                .addParent(PLANT.getId())
                .build(consumer);
        utility(RANDOM_TELEPORT)
                .setPosition(30, 255)
                .addCost(GREEN.get())
                .addParent(INVISIBILITY.getId())
                .build(consumer);
        utility(RECALL)
                .setPosition(75, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId())
                .build(consumer);
        defense(REFLECT)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build(consumer);
        defense(REPEL)
                .setPosition(75, 210)
                .addCost(GREEN.get())
                .addParent(SLOWNESS.getId())
                .build(consumer);
        utility(RIFT)
                .setPosition(120, 255)
                .addCost(GREEN.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        offense(STORM)
                .setPosition(120, 165)
                .addCost(RED.get())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build(consumer);
        defense(SUMMON)
                .setPosition(165, 120)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId())
                .build(consumer);
        utility(TELEKINESIS)
                .setPosition(165, 300)
                .addCost(GREEN.get())
                .addParent(ATTRACT.getId())
                .build(consumer);
        utility(TRANSPLACE)
                .setPosition(75, 300)
                .addCost(GREEN.get())
                .addParent(BLINK.getId())
                .build(consumer);
        utility(WIZARDS_AUTUMN)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        offense(BOUNCE)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build(consumer);
        offense(DAMAGE)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(BEAM.getId())
                .build(consumer);
        offense(DISMEMBERING)
                .setPosition(30, 210)
                .setHidden()
                .build(consumer);
        defense(DURATION)
                .setPosition(210, 345)
                .addCost(RED.get())
                .addParent(TEMPORAL_ANCHOR.getId())
                .build(consumer);
        defense(EFFECT_POWER)
                .setPosition(30, 75)
                .setHidden()
                .build(consumer);
        offense(GRAVITY)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build(consumer);
        defense(HEALING)
                .setPosition(300, 120)
                .addCost(RED.get())
                .addParent(HEAL.getId())
                .build(consumer);
        utility(LUNAR)
                .setPosition(30, 165)
                .addCost(RED.get())
                .addParent(NIGHT_VISION.getId())
                .build(consumer);
        utility(MINING_POWER)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(SILK_TOUCH.getId())
                .build(consumer);
        offense(PIERCING)
                .setPosition(345, 120)
                .addCost(RED.get())
                .addParent(FROST.getId())
                .build(consumer);
        utility(PROSPERITY)
                .setPosition(30, 120)
                .setHidden()
                .build(consumer);
        utility(RANGE)
                .setPosition(75, 255)
                .addCost(RED.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build(consumer);
        defense(RUNE_PROCS)
                .setPosition(120, 345)
                .addCost(GREEN.get())
                .addParent(RUNE.getId())
                .build(consumer);
        utility(SILK_TOUCH)
                .setPosition(75, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        offense(SOLAR)
                .setPosition(120, 210)
                .addCost(RED.get())
                .addParent(BLINDNESS.getId())
                .build(consumer);
        utility(TARGET_NON_SOLID)
                .setPosition(75, 30)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId())
                .build(consumer);
        offense(VELOCITY)
                .setPosition(300, 300)
                .addCost(RED.get())
                .addParent(FLING.getId())
                .build(consumer);
    }

    private Builder offense(RegistryObject<? extends ISpellPart> part) {
        return builder(part.getId().getPath()).setOcculusTab(OFFENSE);
    }

    private Builder defense(RegistryObject<? extends ISpellPart> part) {
        return builder(part.getId().getPath()).setOcculusTab(DEFENSE);
    }

    private Builder utility(RegistryObject<? extends ISpellPart> part) {
        return builder(part.getId().getPath()).setOcculusTab(UTILITY);
    }

    private Builder talent(RegistryObject<? extends ISpellPart> part) {
        return builder(part.getId().getPath()).setOcculusTab(TALENT);
    }
}
