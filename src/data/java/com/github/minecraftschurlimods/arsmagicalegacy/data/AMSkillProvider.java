package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.BLUE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.GREEN;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.RED;

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
        createOffense(AMSpellParts.AOE)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.FIRE_DAMAGE.getId())
                .addParent(AMSpellParts.FROST_DAMAGE.getId())
                .addParent(AMSpellParts.LIGHTNING_DAMAGE.getId())
                .addParent(AMSpellParts.MAGIC_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.BEAM)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(AMSpellParts.AOE.getId())
                .build(consumer);
/*
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
*/
        createOffense(AMSpellParts.PROJECTILE)
                .setPosition(210, 30)
                .addCost(BLUE.get())
                .build(consumer);
        createDefense(AMSpellParts.RUNE)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.AGILITY.getId())
                .addParent(AMSpellParts.ENTANGLE.getId())
                .build(consumer);
        createDefense(AMSpellParts.SELF)
                .setPosition(165, 30)
                .addCost(BLUE.get())
                .build(consumer);
        createUtility(AMSpellParts.TOUCH)
                .setPosition(120, 30)
                .addCost(BLUE.get())
                .build(consumer);
        createDefense(AMSpellParts.WALL)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.REPEL.getId())
                .build(consumer);
        createOffense(AMSpellParts.WAVE)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(AMSpellParts.DAMAGE.getId())
                .addParent(AMSpellParts.VELOCITY.getId())
                .build(consumer);
        createDefense(AMSpellParts.ZONE)
                .setPosition(255, 210)
                .addCost(RED.get())
                .addParent(AMSpellParts.DISPEL.getId())
                .build(consumer);

        createOffense(AMSpellParts.DROWNING_DAMAGE)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.MAGIC_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.FIRE_DAMAGE)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.FROST_DAMAGE)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.LIGHTNING_DAMAGE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.MAGIC_DAMAGE)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PHYSICAL_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.PHYSICAL_DAMAGE)
                .setPosition(210, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PROJECTILE.getId())
                .build(consumer);
        createDefense(AMSpellParts.ABSORPTION)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(AMSpellParts.SHIELD.getId())
                .build(consumer);
        createOffense(AMSpellParts.BLINDNESS)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.FIRE_DAMAGE.getId())
                .addParent(AMSpellParts.LIGHTNING_DAMAGE.getId())
                .build(consumer);
        createDefense(AMSpellParts.HASTE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.SLOW_FALLING.getId())
                .build(consumer);
        createUtility(AMSpellParts.INVISIBILITY)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.TRUE_SIGHT.getId())
                .build(consumer);
        createDefense(AMSpellParts.JUMP_BOOST)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.SELF.getId())
                .build(consumer);
        createDefense(AMSpellParts.LEVITATION)
                .setPosition(120, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.GRAVITY_WELL.getId())
                .build(consumer);
        createUtility(AMSpellParts.NIGHT_VISION)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.LIGHT.getId())
                .build(consumer);
        createDefense(AMSpellParts.REGENERATION)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.SELF.getId())
                .build(consumer);
        createDefense(AMSpellParts.SLOWNESS)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.SLOW_FALLING.getId())
                .build(consumer);
        createDefense(AMSpellParts.SLOW_FALLING)
                .setPosition(120, 120)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.JUMP_BOOST.getId())
                .build(consumer);
        createUtility(AMSpellParts.WATER_BREATHING)
                .setPosition(255, 255)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.CREATE_WATER.getId())
                .build(consumer);
        createDefense(AMSpellParts.AGILITY)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.SWIFT_SWIM.getId())
                .build(consumer);
        createOffense(AMSpellParts.ASTRAL_DISTORTION)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.MAGIC_DAMAGE.getId())
                .build(consumer);
        createDefense(AMSpellParts.ENTANGLE)
                .setPosition(75, 255)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.REPEL.getId())
                .build(consumer);
        createDefense(AMSpellParts.FLIGHT)
                .setPosition(120, 255)
                .addCost(RED.get())
                .addParent(AMSpellParts.LEVITATION.getId())
                .build(consumer);
        createOffense(AMSpellParts.FURY)
                .setPosition(165, 300)
                .addCost(RED.get())
                .addParent(AMSpellParts.DAMAGE.getId())
                .build(consumer);
        createDefense(AMSpellParts.GRAVITY_WELL)
                .setPosition(120, 165)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.SLOW_FALLING.getId())
                .build(consumer);
        createDefense(AMSpellParts.SHIELD)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(AMSpellParts.ZONE.getId())
                .build(consumer);
/*
        createDefense(SHRINK)
                .setPosition(300, 75)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build(consumer);
*/
        createOffense(AMSpellParts.SILENCE)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(AMSpellParts.ASTRAL_DISTORTION.getId())
                .build(consumer);
        createDefense(AMSpellParts.SWIFT_SWIM)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.HASTE.getId())
                .build(consumer);
        createDefense(AMSpellParts.TEMPORAL_ANCHOR)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(AMSpellParts.REFLECT.getId())
                .build(consumer);
        createUtility(AMSpellParts.TRUE_SIGHT)
                .setPosition(75, 210)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.NIGHT_VISION.getId())
                .build(consumer);
        createOffense(AMSpellParts.WATERY_GRAVE)
                .setPosition(345, 165)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.DROWNING_DAMAGE.getId())
                .build(consumer);
/*
        createUtility(ATTRACT)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build(consumer);
*/
        createUtility(AMSpellParts.BANISH_RAIN)
                .setPosition(255, 300)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.DROUGHT.getId())
                .build(consumer);
/*
        createUtility(BLINK)
                .setPosition(30, 300)
                .addCost(GREEN.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build(consumer);
        createOffense(BLIZZARD)
                .setPosition(30, 30)
                .hidden()
                .build(consumer);
*/
        createUtility(AMSpellParts.CHARM)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.LIGHT.getId())
                .build(consumer);
        createUtility(AMSpellParts.CREATE_WATER)
                .setPosition(210, 255)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.PLANT.getId())
                .build(consumer);
/*
        createUtility(DAYLIGHT)
                .setPosition(30, 30)
                .hidden()
                .build(consumer);
*/
        createUtility(AMSpellParts.DIG)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.TOUCH.getId())
                .build(consumer);
        createDefense(AMSpellParts.DISARM)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.DISPEL.getId())
                .build(consumer);
        createDefense(AMSpellParts.DISPEL)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.HEAL.getId())
                .build(consumer);
/*
        createUtility(DIVINE_INTERVENTION)
                .setPosition(30, 390)
                .addCost(RED.get())
                .addParent(MARK.getId())
                .addParent(RECALL.getId())
                .build(consumer);
*/
        createUtility(AMSpellParts.DROUGHT)
                .setPosition(210, 300)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.CREATE_WATER.getId())
                .build(consumer);
/*
        createUtility(ENDER_INTERVENTION)
                .setPosition(75, 390)
                .addCost(RED.get())
                .addParent(MARK.getId())
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
*/
        createOffense(AMSpellParts.FLING)
                .setPosition(300, 255)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.KNOCKBACK.getId())
                .build(consumer);
        createOffense(AMSpellParts.FORGE)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.IGNITION.getId())
                .build(consumer);
        createOffense(AMSpellParts.FROST)
                .setPosition(300, 120)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.FROST_DAMAGE.getId())
                .build(consumer);
        createUtility(AMSpellParts.GROW)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.PLANT.getId())
                .build(consumer);
        createUtility(AMSpellParts.HARVEST)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.PLOW.getId())
                .build(consumer);
        createDefense(AMSpellParts.HEAL)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.REGENERATION.getId())
                .build(consumer);
        createOffense(AMSpellParts.IGNITION)
                .setPosition(120, 120)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.FIRE_DAMAGE.getId())
                .build(consumer);
        createOffense(AMSpellParts.KNOCKBACK)
                .setPosition(300, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.MAGIC_DAMAGE.getId())
                .build(consumer);
/*
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
*/
        createUtility(AMSpellParts.LIGHT)
                .setPosition(120, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.DIG.getId())
                .build(consumer);
/*
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
        createUtility(MARK)
                .setPosition(30, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId())
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
*/
        createUtility(AMSpellParts.PLANT)
                .setPosition(210, 210)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.LIGHT.getId())
                .build(consumer);
        createUtility(AMSpellParts.PLOW)
                .setPosition(210, 165)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PLANT.getId())
                .build(consumer);
        createUtility(AMSpellParts.RANDOM_TELEPORT)
                .setPosition(30, 255)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.INVISIBILITY.getId())
                .build(consumer);
/*
        createUtility(RECALL)
                .setPosition(75, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId())
                .build(consumer);
*/
        createDefense(AMSpellParts.REFLECT)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(AMSpellParts.SHIELD.getId())
                .build(consumer);
        createDefense(AMSpellParts.REPEL)
                .setPosition(75, 210)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.SLOWNESS.getId())
                .build(consumer);
/*
        createUtility(RIFT)
                .setPosition(120, 255)
                .addCost(GREEN.get())
                .addParent(LIGHT.getId())
                .build(consumer);
*/
        createOffense(AMSpellParts.STORM)
                .setPosition(120, 165)
                .addCost(RED.get())
                .addParent(AMSpellParts.LIGHTNING_DAMAGE.getId())
                .build(consumer);
/*
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
*/
        createUtility(AMSpellParts.WIZARDS_AUTUMN)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.DIG.getId())
                .build(consumer);

        createOffense(AMSpellParts.BOUNCE)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PROJECTILE.getId())
                .build(consumer);
        createOffense(AMSpellParts.DAMAGE)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(AMSpellParts.BEAM.getId())
                .build(consumer);
/*
        createOffense(DISMEMBERING)
                .setPosition(30, 210)
                .hidden()
                .build(consumer);
*/
        createDefense(AMSpellParts.DURATION)
                .setPosition(210, 345)
                .addCost(RED.get())
                .addParent(AMSpellParts.TEMPORAL_ANCHOR.getId())
                .build(consumer);
/*
        createDefense(EFFECT_POWER)
                .setPosition(30, 75)
                .hidden()
                .build(consumer);
*/
        createOffense(AMSpellParts.GRAVITY)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.PROJECTILE.getId())
                .build(consumer);
        createDefense(AMSpellParts.HEALING)
                .setPosition(300, 120)
                .addCost(RED.get())
                .addParent(AMSpellParts.HEAL.getId())
                .build(consumer);
        createUtility(AMSpellParts.LUNAR)
                .setPosition(30, 165)
                .addCost(RED.get())
                .addParent(AMSpellParts.NIGHT_VISION.getId())
                .build(consumer);
        createUtility(AMSpellParts.MINING_POWER)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.SILK_TOUCH.getId())
                .build(consumer);
        createOffense(AMSpellParts.PIERCING)
                .setPosition(345, 120)
                .addCost(RED.get())
                .addParent(AMSpellParts.FROST.getId())
                .build(consumer);
/*
        createUtility(PROSPERITY)
                .setPosition(30, 120)
                .hidden()
                .build(consumer);
*/
        createUtility(AMSpellParts.RANGE)
                .setPosition(75, 255)
                .addCost(RED.get())
                .addParent(AMSpellParts.RANDOM_TELEPORT.getId())
                .build(consumer);
        createDefense(AMSpellParts.RUNE_PROCS)
                .setPosition(120, 345)
                .addCost(GREEN.get())
                .addParent(AMSpellParts.RUNE.getId())
                .build(consumer);
        createUtility(AMSpellParts.SILK_TOUCH)
                .setPosition(75, 75)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.DIG.getId())
                .build(consumer);
        createOffense(AMSpellParts.SOLAR)
                .setPosition(120, 210)
                .addCost(RED.get())
                .addParent(AMSpellParts.BLINDNESS.getId())
                .build(consumer);
        createUtility(AMSpellParts.TARGET_NON_SOLID)
                .setPosition(75, 30)
                .addCost(BLUE.get())
                .addParent(AMSpellParts.TOUCH.getId())
                .build(consumer);
        createOffense(AMSpellParts.VELOCITY)
                .setPosition(300, 300)
                .addCost(RED.get())
                .addParent(AMSpellParts.FLING.getId())
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
