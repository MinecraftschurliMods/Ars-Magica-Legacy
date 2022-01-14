package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SkillBuilder;
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

    protected AMSkillProvider(DataGenerator generator) {
        super(generator, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public String getName() {
        return "AMSkillProvider";
    }

    @Override
    protected void createSkills(Consumer<SkillBuilder> consumer) {
        createOffense(AOE)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(FROST_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        createOffense(BEAM)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(AOE.getId())
                .build(consumer);
        createUtility(CHAIN)
                .setPosition(255, 165)
                .addCost(RED.get())
                .addParent(GROW.getId())
                .build(consumer);
        createUtility(CHANNEL)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build(consumer);
        createOffense(PROJECTILE)
                .setPosition(210, 30)
                .addCost(BLUE.get())
                .build(consumer);
        createDefense(RUNE)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(AGILITY.getId())
                .addParent(ENTANGLE.getId())
                .build(consumer);
        createDefense(SELF)
                .setPosition(165, 30)
                .addCost(BLUE.get())
                .build(consumer);
        createUtility(TOUCH)
                .setPosition(120, 30)
                .addCost(BLUE.get())
                .build(consumer);
        createDefense(WALL)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(REPEL.getId())
                .build(consumer);
        createOffense(WAVE)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .addParent(VELOCITY.getId())
                .build(consumer);
        createDefense(ZONE)
                .setPosition(255, 210)
                .addCost(RED.get())
                .addParent(DISPEL.getId())
                .build(consumer);

        createOffense(DROWNING_DAMAGE)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        createOffense(FIRE_DAMAGE)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(FROST_DAMAGE)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(LIGHTNING_DAMAGE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(MAGIC_DAMAGE)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(PHYSICAL_DAMAGE)
                .setPosition(210, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build(consumer);
        createDefense(ABSORPTION)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build(consumer);
        createOffense(BLINDNESS)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build(consumer);
        createDefense(HASTE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        createUtility(INVISIBILITY)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(TRUE_SIGHT.getId())
                .build(consumer);
        createDefense(JUMP_BOOST)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId())
                .build(consumer);
        createDefense(LEVITATION)
                .setPosition(120, 210)
                .addCost(GREEN.get())
                .addParent(GRAVITY_WELL.getId())
                .build(consumer);
        createUtility(NIGHT_VISION)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        createDefense(REGENERATION)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId())
                .build(consumer);
        createDefense(SLOWNESS)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        createDefense(SLOW_FALLING)
                .setPosition(120, 120)
                .addCost(BLUE.get())
                .addParent(JUMP_BOOST.getId())
                .build(consumer);
        createUtility(WATER_BREATHING)
                .setPosition(255, 255)
                .addCost(BLUE.get())
                .addParent(CREATE_WATER.getId())
                .build(consumer);
        createDefense(AGILITY)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(SWIFT_SWIM.getId())
                .build(consumer);
        createOffense(ASTRAL_DISTORTION)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        createDefense(ENTANGLE)
                .setPosition(75, 255)
                .addCost(GREEN.get())
                .addParent(REPEL.getId())
                .build(consumer);
        createDefense(FLIGHT)
                .setPosition(120, 255)
                .addCost(RED.get())
                .addParent(LEVITATION.getId())
                .build(consumer);
        createOffense(FURY)
                .setPosition(165, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .build(consumer);
        createDefense(GRAVITY_WELL)
                .setPosition(120, 165)
                .addCost(GREEN.get())
                .addParent(SLOW_FALLING.getId())
                .build(consumer);
        createDefense(SHIELD)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(ZONE.getId())
                .build(consumer);
        createDefense(SHRINK)
                .setPosition(300, 75)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build(consumer);
        createOffense(SILENCE)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(ASTRAL_DISTORTION.getId())
                .build(consumer);
        createDefense(SWIFT_SWIM)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(HASTE.getId())
                .build(consumer);
        createDefense(TEMPORAL_ANCHOR)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(REFLECT.getId())
                .build(consumer);
        createUtility(TRUE_SIGHT)
                .setPosition(75, 210)
                .addCost(BLUE.get())
                .addParent(NIGHT_VISION.getId())
                .build(consumer);
        createOffense(WATERY_GRAVE)
                .setPosition(345, 165)
                .addCost(GREEN.get())
                .addParent(DROWNING_DAMAGE.getId())
                .build(consumer);
        createUtility(ATTRACT)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build(consumer);
        createUtility(BANISH_RAIN)
                .setPosition(255, 300)
                .addCost(GREEN.get())
                .addParent(DROUGHT.getId())
                .build(consumer);
        createUtility(BLINK)
                .setPosition(30, 300)
                .addCost(GREEN.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build(consumer);
        createOffense(BLIZZARD)
                .setPosition(30, 30)
                .hidden()
                .build(consumer);
        createUtility(CHARM)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        createUtility(CREATE_WATER)
                .setPosition(210, 255)
                .addCost(GREEN.get())
                .addParent(PLANT.getId())
                .build(consumer);
        createUtility(DAYLIGHT)
                .setPosition(30, 30)
                .hidden()
                .build(consumer);
        createUtility(DIG)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId())
                .build(consumer);
        createDefense(DISARM)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(DISPEL.getId())
                .build(consumer);
        createDefense(DISPEL)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(HEAL.getId())
                .build(consumer);
        createUtility(DIVINE_INTERVENTION)
                .setPosition(30, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId())
                .build(consumer);
        createUtility(DROUGHT)
                .setPosition(210, 300)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId())
                .build(consumer);
        createUtility(ENDER_INTERVENTION)
                .setPosition(120, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId())
                .build(consumer);
        createOffense(FALLING_STAR)
                .setPosition(30, 75)
                .hidden()
                .build(consumer);
        createOffense(FIRE_RAIN)
                .setPosition(30, 120)
                .hidden()
                .build(consumer);
        createOffense(FLING)
                .setPosition(300, 255)
                .addCost(GREEN.get())
                .addParent(KNOCKBACK.getId())
                .build(consumer);
        createOffense(FORGE)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(IGNITION.getId())
                .build(consumer);
        createOffense(FROST)
                .setPosition(300, 120)
                .addCost(GREEN.get())
                .addParent(FROST_DAMAGE.getId())
                .build(consumer);
        createUtility(GROW)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(PLANT.getId())
                .build(consumer);
        createUtility(HARVEST)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(PLOW.getId())
                .build(consumer);
        createDefense(HEAL)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build(consumer);
        createOffense(IGNITION)
                .setPosition(120, 120)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .build(consumer);
        createOffense(KNOCKBACK)
                .setPosition(300, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build(consumer);
        createDefense(LIFE_DRAIN)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId())
                .build(consumer);
        createDefense(LIFE_TAP)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(HEAL.getId())
                .build(consumer);
        createUtility(LIGHT)
                .setPosition(120, 165)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        createOffense(MANA_BLAST)
                .setPosition(30, 165)
                .hidden()
                .build(consumer);
        createDefense(MANA_DRAIN)
                .setPosition(210, 210)
                .addCost(GREEN.get())
                .addParent(LIFE_DRAIN.getId())
                .build(consumer);
        createDefense(MANA_SHIELD)
                .setPosition(30, 30)
                .hidden()
                .build(consumer);
        createUtility(MOONRISE)
                .setPosition(30, 75)
                .hidden()
                .build(consumer);
        createUtility(PLACE_BLOCK)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        createUtility(PLANT)
                .setPosition(210, 210)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        createUtility(PLOW)
                .setPosition(210, 165)
                .addCost(BLUE.get())
                .addParent(PLANT.getId())
                .build(consumer);
        createUtility(RANDOM_TELEPORT)
                .setPosition(30, 255)
                .addCost(GREEN.get())
                .addParent(INVISIBILITY.getId())
                .build(consumer);
        createUtility(RECALL)
                .setPosition(75, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId())
                .build(consumer);
        createDefense(REFLECT)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build(consumer);
        createDefense(REPEL)
                .setPosition(75, 210)
                .addCost(GREEN.get())
                .addParent(SLOWNESS.getId())
                .build(consumer);
        createUtility(RIFT)
                .setPosition(120, 255)
                .addCost(GREEN.get())
                .addParent(LIGHT.getId())
                .build(consumer);
        createOffense(STORM)
                .setPosition(120, 165)
                .addCost(RED.get())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build(consumer);
        createDefense(SUMMON)
                .setPosition(165, 120)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId())
                .build(consumer);
        createUtility(TELEKINESIS)
                .setPosition(165, 300)
                .addCost(GREEN.get())
                .addParent(ATTRACT.getId())
                .build(consumer);
        createUtility(TRANSPLACE)
                .setPosition(75, 300)
                .addCost(GREEN.get())
                .addParent(BLINK.getId())
                .build(consumer);
        createUtility(WIZARDS_AUTUMN)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);

        createOffense(BOUNCE)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build(consumer);
        createOffense(DAMAGE)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(BEAM.getId())
                .build(consumer);
        createOffense(DISMEMBERING)
                .setPosition(30, 210)
                .hidden()
                .build(consumer);
        createDefense(DURATION)
                .setPosition(210, 345)
                .addCost(RED.get())
                .addParent(TEMPORAL_ANCHOR.getId())
                .build(consumer);
        createDefense(EFFECT_POWER)
                .setPosition(30, 75)
                .hidden()
                .build(consumer);
        createOffense(GRAVITY)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build(consumer);
        createDefense(HEALING)
                .setPosition(300, 120)
                .addCost(RED.get())
                .addParent(HEAL.getId())
                .build(consumer);
        createUtility(LUNAR)
                .setPosition(30, 165)
                .addCost(RED.get())
                .addParent(NIGHT_VISION.getId())
                .build(consumer);
        createUtility(MINING_POWER)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(SILK_TOUCH.getId())
                .build(consumer);
        createOffense(PIERCING)
                .setPosition(345, 120)
                .addCost(RED.get())
                .addParent(FROST.getId())
                .build(consumer);
        createUtility(PROSPERITY)
                .setPosition(30, 120)
                .hidden()
                .build(consumer);
        createUtility(RANGE)
                .setPosition(75, 255)
                .addCost(RED.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build(consumer);
        createDefense(RUNE_PROCS)
                .setPosition(120, 345)
                .addCost(GREEN.get())
                .addParent(RUNE.getId())
                .build(consumer);
        createUtility(SILK_TOUCH)
                .setPosition(75, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build(consumer);
        createOffense(SOLAR)
                .setPosition(120, 210)
                .addCost(RED.get())
                .addParent(BLINDNESS.getId())
                .build(consumer);
        createUtility(TARGET_NON_SOLID)
                .setPosition(75, 30)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId())
                .build(consumer);
        createOffense(VELOCITY)
                .setPosition(300, 300)
                .addCost(RED.get())
                .addParent(FLING.getId())
                .build(consumer);
    }

    private SkillBuilder createOffense(RegistryObject<? extends ISpellPart> registryObject) {
        return createSkillBuilder(registryObject, OFFENSE);
    }

    private SkillBuilder createDefense(RegistryObject<? extends ISpellPart> registryObject) {
        return createSkillBuilder(registryObject, DEFENSE);
    }

    private SkillBuilder createUtility(RegistryObject<? extends ISpellPart> registryObject) {
        return createSkillBuilder(registryObject, UTILITY);
    }

    private SkillBuilder createTalent(RegistryObject<? extends ISpellPart> registryObject) {
        return createSkillBuilder(registryObject, TALENT);
    }

    private SkillBuilder createSkillBuilder(RegistryObject<? extends ISpellPart> registryObject, ResourceLocation tree) {
        return SkillBuilder.create(registryObject.getId(), tree);
    }
}
