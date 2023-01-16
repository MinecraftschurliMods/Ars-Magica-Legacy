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
        add(consumer, offense(AOE)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(FROST_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .addParent(MAGIC_DAMAGE.getId()));
        add(consumer, offense(BEAM)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(AOE.getId()));
        add(consumer, utility(CHAIN)
                .setPosition(255, 165)
                .addCost(RED.get())
                .addParent(GROW.getId()));
        add(consumer, utility(CHANNEL)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(RIFT.getId()));
        add(consumer, defense(CONTINGENCY_DAMAGE)
                .setPosition(255, 345)
                .addCost(RED.get())
                .addParent(REFLECT.getId()));
        add(consumer, utility(CONTINGENCY_DEATH)
                .setPosition(165, 345)
                .addCost(RED.get())
                .addParent(ENDER_INTERVENTION.getId()));
        add(consumer, defense(CONTINGENCY_FALL)
                .setPosition(75, 120)
                .addCost(RED.get())
                .addParent(SLOW_FALLING.getId()));
        add(consumer, offense(CONTINGENCY_FIRE)
                .setPosition(75, 165)
                .addCost(RED.get())
                .addParent(FORGE.getId()));
        add(consumer, defense(CONTINGENCY_HEALTH)
                .setPosition(300, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId()));
        add(consumer, offense(PROJECTILE)
                .setPosition(210, 30)
                .addCost(BLUE.get()));
        add(consumer, defense(RUNE)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(AGILITY.getId())
                .addParent(ENTANGLE.getId()));
        add(consumer, defense(SELF)
                .setPosition(165, 30)
                .addCost(BLUE.get()));
        add(consumer, utility(TOUCH)
                .setPosition(120, 30)
                .addCost(BLUE.get()));
        add(consumer, defense(WALL)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(REPEL.getId()));
        add(consumer, offense(WAVE)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .addParent(VELOCITY.getId()));
        add(consumer, defense(ZONE)
                .setPosition(255, 210)
                .addCost(RED.get())
                .addParent(DISPEL.getId()));
        add(consumer, offense(DROWNING_DAMAGE)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(MAGIC_DAMAGE.getId()));
        add(consumer, offense(FIRE_DAMAGE)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(consumer, offense(FROST_DAMAGE)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(consumer, offense(LIGHTNING_DAMAGE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(consumer, offense(MAGIC_DAMAGE)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(consumer, offense(PHYSICAL_DAMAGE)
                .setPosition(210, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId()));
        add(consumer, defense(ABSORPTION)
                .setPosition(210, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId()));
        add(consumer, offense(BLINDNESS)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId()));
        add(consumer, defense(HASTE)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId()));
        add(consumer, utility(INVISIBILITY)
                .setPosition(30, 210)
                .addCost(GREEN.get())
                .addParent(TRUE_SIGHT.getId()));
        add(consumer, defense(JUMP_BOOST)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId()));
        add(consumer, defense(LEVITATION)
                .setPosition(120, 210)
                .addCost(GREEN.get())
                .addParent(GRAVITY_WELL.getId()));
        add(consumer, utility(NIGHT_VISION)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId()));
        add(consumer, defense(REGENERATION)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId()));
        add(consumer, defense(SLOWNESS)
                .setPosition(75, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId()));
        add(consumer, defense(SLOW_FALLING)
                .setPosition(120, 120)
                .addCost(BLUE.get())
                .addParent(JUMP_BOOST.getId()));
        add(consumer, utility(WATER_BREATHING)
                .setPosition(255, 255)
                .addCost(BLUE.get())
                .addParent(CREATE_WATER.getId()));
        add(consumer, defense(AGILITY)
                .setPosition(165, 255)
                .addCost(GREEN.get())
                .addParent(SWIFT_SWIM.getId()));
        add(consumer, offense(ASTRAL_DISTORTION)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId()));
        add(consumer, defense(ENTANGLE)
                .setPosition(75, 255)
                .addCost(GREEN.get())
                .addParent(REPEL.getId()));
        add(consumer, defense(FLIGHT)
                .setPosition(120, 255)
                .addCost(RED.get())
                .addParent(LEVITATION.getId()));
        add(consumer, offense(FURY)
                .setPosition(165, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId()));
        add(consumer, defense(GRAVITY_WELL)
                .setPosition(120, 165)
                .addCost(GREEN.get())
                .addParent(SLOW_FALLING.getId()));
        add(consumer, defense(SHIELD)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(ZONE.getId()));
        add(consumer, defense(SHRINK)
                .setPosition(300, 75)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId()));
        add(consumer, offense(SILENCE)
                .setPosition(255, 255)
                .addCost(RED.get())
                .addParent(ASTRAL_DISTORTION.getId()));
        add(consumer, defense(SWIFT_SWIM)
                .setPosition(165, 210)
                .addCost(GREEN.get())
                .addParent(HASTE.getId()));
        add(consumer, defense(TEMPORAL_ANCHOR)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(REFLECT.getId()));
        add(consumer, utility(TRUE_SIGHT)
                .setPosition(75, 210)
                .addCost(BLUE.get())
                .addParent(NIGHT_VISION.getId()));
        add(consumer, offense(WATERY_GRAVE)
                .setPosition(345, 165)
                .addCost(GREEN.get())
                .addParent(DROWNING_DAMAGE.getId()));
        add(consumer, utility(ATTRACT)
                .setPosition(120, 300)
                .addCost(GREEN.get())
                .addParent(RIFT.getId()));
        add(consumer, utility(BANISH_RAIN)
                .setPosition(255, 300)
                .addCost(GREEN.get())
                .addParent(DROUGHT.getId()));
        add(consumer, utility(BLINK)
                .setPosition(30, 300)
                .addCost(GREEN.get())
                .addParent(RANDOM_TELEPORT.getId()));
        add(consumer, offense(BLIZZARD)
                .setPosition(30, 30)
                .setHidden());
        add(consumer, utility(CHARM)
                .setPosition(165, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId()));
        add(consumer, utility(CREATE_WATER)
                .setPosition(210, 255)
                .addCost(GREEN.get())
                .addParent(PLANT.getId()));
        add(consumer, utility(DAYLIGHT)
                .setPosition(30, 30)
                .setHidden());
        add(consumer, utility(DIG)
                .setPosition(120, 75)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId()));
        add(consumer, defense(DISARM)
                .setPosition(300, 165)
                .addCost(BLUE.get())
                .addParent(DISPEL.getId()));
        add(consumer, defense(DISPEL)
                .setPosition(255, 165)
                .addCost(BLUE.get())
                .addParent(HEAL.getId()));
        add(consumer, utility(DIVINE_INTERVENTION)
                .setPosition(30, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId()));
        add(consumer, utility(DROUGHT)
                .setPosition(210, 300)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId()));
        add(consumer, utility(ENDER_INTERVENTION)
                .setPosition(120, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId()));
        add(consumer, offense(EXPLOSION)
                .setPosition(120, 300)
                .addCost(RED.get())
                .addParent(FURY.getId()));
        add(consumer, offense(FALLING_STAR)
                .setPosition(30, 75)
                .setHidden());
        add(consumer, offense(FIRE_RAIN)
                .setPosition(30, 120)
                .setHidden());
        add(consumer, offense(FLING)
                .setPosition(300, 255)
                .addCost(GREEN.get())
                .addParent(KNOCKBACK.getId()));
        add(consumer, offense(FORGE)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(IGNITION.getId()));
        add(consumer, offense(FROST)
                .setPosition(300, 120)
                .addCost(GREEN.get())
                .addParent(FROST_DAMAGE.getId()));
        add(consumer, utility(GROW)
                .setPosition(255, 210)
                .addCost(GREEN.get())
                .addParent(PLANT.getId()));
        add(consumer, utility(HARVEST)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(PLOW.getId()));
        add(consumer, defense(HEAL)
                .setPosition(255, 120)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId()));
        add(consumer, offense(IGNITION)
                .setPosition(120, 120)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId()));
        add(consumer, offense(KNOCKBACK)
                .setPosition(300, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId()));
        add(consumer, defense(LIFE_DRAIN)
                .setPosition(210, 165)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId()));
        add(consumer, defense(LIFE_TAP)
                .setPosition(210, 120)
                .addCost(GREEN.get())
                .addParent(HEAL.getId()));
        add(consumer, utility(LIGHT)
                .setPosition(120, 165)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(consumer, offense(MANA_BLAST)
                .setPosition(30, 165)
                .setHidden());
        add(consumer, defense(MANA_DRAIN)
                .setPosition(210, 210)
                .addCost(GREEN.get())
                .addParent(LIFE_DRAIN.getId()));
        add(consumer, defense(MANA_SHIELD)
                .setPosition(30, 30)
                .setHidden());
        add(consumer, utility(MOONRISE)
                .setPosition(30, 75)
                .setHidden());
        add(consumer, utility(PLACE_BLOCK)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(consumer, utility(PLANT)
                .setPosition(210, 210)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId()));
        add(consumer, utility(PLOW)
                .setPosition(210, 165)
                .addCost(BLUE.get())
                .addParent(PLANT.getId()));
        add(consumer, utility(RANDOM_TELEPORT)
                .setPosition(30, 255)
                .addCost(GREEN.get())
                .addParent(INVISIBILITY.getId()));
        add(consumer, utility(RECALL)
                .setPosition(75, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId()));
        add(consumer, defense(REFLECT)
                .setPosition(255, 300)
                .addCost(RED.get())
                .addParent(SHIELD.getId()));
        add(consumer, defense(REPEL)
                .setPosition(75, 210)
                .addCost(GREEN.get())
                .addParent(SLOWNESS.getId()));
        add(consumer, utility(RIFT)
                .setPosition(120, 255)
                .addCost(GREEN.get())
                .addParent(LIGHT.getId()));
        add(consumer, offense(STORM)
                .setPosition(120, 165)
                .addCost(RED.get())
                .addParent(LIGHTNING_DAMAGE.getId()));
        add(consumer, defense(SUMMON)
                .setPosition(165, 120)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId()));
        add(consumer, utility(TELEKINESIS)
                .setPosition(165, 300)
                .addCost(GREEN.get())
                .addParent(ATTRACT.getId()));
        add(consumer, utility(TRANSPLACE)
                .setPosition(75, 300)
                .addCost(GREEN.get())
                .addParent(BLINK.getId()));
        add(consumer, utility(WIZARDS_AUTUMN)
                .setPosition(165, 120)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(consumer, offense(BOUNCE)
                .setPosition(255, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId()));
        add(consumer, offense(DAMAGE)
                .setPosition(210, 300)
                .addCost(RED.get())
                .addParent(BEAM.getId()));
        add(consumer, offense(DISMEMBERING)
                .setPosition(30, 210)
                .setHidden());
        add(consumer, defense(DURATION)
                .setPosition(210, 345)
                .addCost(RED.get())
                .addParent(TEMPORAL_ANCHOR.getId()));
        add(consumer, defense(EFFECT_POWER)
                .setPosition(30, 75)
                .setHidden());
        add(consumer, offense(GRAVITY)
                .setPosition(165, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId()));
        add(consumer, defense(HEALING)
                .setPosition(300, 120)
                .addCost(RED.get())
                .addParent(HEAL.getId()));
        add(consumer, utility(LUNAR)
                .setPosition(30, 165)
                .addCost(RED.get())
                .addParent(NIGHT_VISION.getId()));
        add(consumer, utility(MINING_POWER)
                .setPosition(75, 120)
                .addCost(GREEN.get())
                .addParent(SILK_TOUCH.getId()));
        add(consumer, offense(PIERCING)
                .setPosition(345, 120)
                .addCost(RED.get())
                .addParent(FROST.getId()));
        add(consumer, utility(PROSPERITY)
                .setPosition(30, 120)
                .setHidden());
        add(consumer, utility(RANGE)
                .setPosition(75, 255)
                .addCost(RED.get())
                .addParent(RANDOM_TELEPORT.getId()));
        add(consumer, defense(RUNE_PROCS)
                .setPosition(120, 345)
                .addCost(GREEN.get())
                .addParent(RUNE.getId()));
        add(consumer, utility(SILK_TOUCH)
                .setPosition(75, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(consumer, offense(SOLAR)
                .setPosition(120, 210)
                .addCost(RED.get())
                .addParent(BLINDNESS.getId()));
        add(consumer, utility(TARGET_NON_SOLID)
                .setPosition(75, 30)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId()));
        add(consumer, offense(VELOCITY)
                .setPosition(300, 300)
                .addCost(RED.get())
                .addParent(FLING.getId()));
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
