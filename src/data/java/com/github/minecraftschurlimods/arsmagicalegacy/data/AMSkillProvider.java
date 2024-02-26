package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SkillProvider;
import net.minecraft.resources.ResourceLocation;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.BLUE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.GREEN;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.RED;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts.*;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMTalents.*;

class AMSkillProvider extends SkillProvider {
    private static final ResourceLocation OFFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense");
    private static final ResourceLocation DEFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "defense");
    private static final ResourceLocation UTILITY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "utility");
    private static final ResourceLocation TALENT = new ResourceLocation(ArsMagicaAPI.MOD_ID, "talent");

    AMSkillProvider() {
        super(ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        add(AOE.getId(), offense(210, 165)
                .addCost(GREEN)
                .addParent(FIRE_DAMAGE.getId())
                .addParent(FROST_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .addParent(MAGIC_DAMAGE.getId())
                .build());
        add(BEAM.getId(), offense(210, 210)
                .addCost(RED)
                .addParent(AOE.getId())
                .build());
        add(CHAIN.getId(), offense(210, 255)
                .addCost(RED)
                .addParent(BEAM.getId())
                .build());
        add(CHANNEL.getId(), utility(165, 255)
                .addCost(GREEN)
                .addParent(RIFT.getId())
                .build());
        add(CONTINGENCY_DAMAGE.getId(), defense(255, 345)
                .addCost(RED)
                .addParent(REFLECT.getId())
                .build());
        add(CONTINGENCY_DEATH.getId(), utility(165, 345)
                .addCost(RED)
                .addParent(ENDER_INTERVENTION.getId())
                .build());
        add(CONTINGENCY_FALL.getId(), defense(75, 120)
                .addCost(RED)
                .addParent(SLOW_FALLING.getId())
                .build());
        add(CONTINGENCY_FIRE.getId(), offense(75, 165)
                .addCost(RED)
                .addParent(FORGE.getId())
                .build());
        add(CONTINGENCY_HEALTH.getId(), defense(300, 255)
                .addCost(RED)
                .addParent(SHIELD.getId())
                .build());
        add(PROJECTILE.getId(), offense(210, 30)
                .addCost(BLUE)
                .build());
        add(RUNE.getId(), defense(120, 300)
                .addCost(GREEN)
                .addParent(AGILITY.getId())
                .addParent(ENTANGLE.getId())
                .build());
        add(SELF.getId(), defense(165, 30)
                .addCost(BLUE)
                .build());
        add(TOUCH.getId(), utility(120, 30)
                .addCost(BLUE)
                .build());
        add(WALL.getId(), defense(30, 210)
                .addCost(GREEN)
                .addParent(REPEL.getId())
                .build());
        add(WAVE.getId(), offense(255, 300)
                .addCost(RED)
                .addParent(DAMAGE.getId())
                .addParent(VELOCITY.getId())
                .build());
        add(ZONE.getId(), defense(255, 210)
                .addCost(RED)
                .addParent(DISPEL.getId())
                .build());
        add(DROWNING_DAMAGE.getId(), offense(300, 165)
                .addCost(BLUE)
                .addParent(MAGIC_DAMAGE.getId())
                .build());
        add(FIRE_DAMAGE.getId(), offense(165, 120)
                .addCost(BLUE)
                .addParent(PHYSICAL_DAMAGE.getId())
                .build());
        add(FROST_DAMAGE.getId(), offense(255, 120)
                .addCost(BLUE)
                .addParent(PHYSICAL_DAMAGE.getId())
                .build());
        add(LIGHTNING_DAMAGE.getId(), offense(165, 165)
                .addCost(BLUE)
                .addParent(PHYSICAL_DAMAGE.getId())
                .build());
        add(MAGIC_DAMAGE.getId(), offense(255, 165)
                .addCost(BLUE)
                .addParent(PHYSICAL_DAMAGE.getId())
                .build());
        add(PHYSICAL_DAMAGE.getId(), offense(210, 75)
                .addCost(BLUE)
                .addParent(PROJECTILE.getId())
                .build());
        add(ABSORPTION.getId(), defense(210, 255)
                .addCost(RED)
                .addParent(SHIELD.getId())
                .build());
        add(BLINDNESS.getId(), offense(165, 210)
                .addCost(GREEN)
                .addParent(FIRE_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build());
        add(HASTE.getId(), defense(165, 165)
                .addCost(BLUE)
                .addParent(SLOW_FALLING.getId())
                .build());
        add(HEALTH_BOOST.getId(), defense(30, 30)
                .setHidden()
                .build());
        add(INVISIBILITY.getId(), utility(30, 210)
                .addCost(GREEN)
                .addParent(TRUE_SIGHT.getId())
                .build());
        add(JUMP_BOOST.getId(), defense(120, 75)
                .addCost(BLUE)
                .addParent(SELF.getId())
                .build());
        add(LEVITATION.getId(), defense(120, 210)
                .addCost(GREEN)
                .addParent(GRAVITY_WELL.getId())
                .build());
        add(NIGHT_VISION.getId(), utility(75, 165)
                .addCost(BLUE)
                .addParent(LIGHT.getId())
                .build());
        add(REGENERATION.getId(), defense(255, 75)
                .addCost(BLUE)
                .addParent(SELF.getId())
                .build());
        add(SLOWNESS.getId(), defense(75, 165)
                .addCost(BLUE)
                .addParent(SLOW_FALLING.getId())
                .build());
        add(SLOW_FALLING.getId(), defense(120, 120)
                .addCost(BLUE)
                .addParent(JUMP_BOOST.getId())
                .build());
        add(WATER_BREATHING.getId(), utility(255, 255)
                .addCost(GREEN)
                .addParent(CREATE_WATER.getId())
                .build());
        add(AGILITY.getId(), defense(165, 255)
                .addCost(GREEN)
                .addParent(SWIFT_SWIM.getId())
                .build());
        add(ASTRAL_DISTORTION.getId(), offense(255, 210)
                .addCost(GREEN)
                .addParent(MAGIC_DAMAGE.getId())
                .build());
        add(ENTANGLE.getId(), defense(75, 255)
                .addCost(GREEN)
                .addParent(REPEL.getId())
                .build());
        add(FLIGHT.getId(), defense(120, 255)
                .addCost(RED)
                .addParent(LEVITATION.getId())
                .build());
        add(FURY.getId(), offense(165, 300)
                .addCost(RED)
                .addParent(DAMAGE.getId())
                .build());
        add(GRAVITY_WELL.getId(), defense(120, 165)
                .addCost(GREEN)
                .addParent(SLOW_FALLING.getId())
                .build());
        add(SHIELD.getId(), defense(255, 255)
                .addCost(RED)
                .addParent(ZONE.getId())
                .build());
        add(SHRINK.getId(), defense(300, 75)
                .addCost(BLUE)
                .addParent(REGENERATION.getId())
                .build());
        add(SILENCE.getId(), offense(255, 255)
                .addCost(RED)
                .addParent(ASTRAL_DISTORTION.getId())
                .build());
        add(SWIFT_SWIM.getId(), defense(165, 210)
                .addCost(GREEN)
                .addParent(HASTE.getId())
                .build());
        add(TEMPORAL_ANCHOR.getId(), defense(210, 300)
                .addCost(RED)
                .addParent(REFLECT.getId())
                .build());
        add(TRUE_SIGHT.getId(), utility(75, 210)
                .addCost(BLUE)
                .addParent(NIGHT_VISION.getId())
                .build());
        add(WATERY_GRAVE.getId(), offense(345, 165)
                .addCost(GREEN)
                .addParent(DROWNING_DAMAGE.getId())
                .build());
        add(ATTRACT.getId(), utility(120, 300)
                .addCost(GREEN)
                .addParent(RIFT.getId())
                .build());
        add(BANISH_RAIN.getId(), utility(255, 300)
                .addCost(GREEN)
                .addParent(DROUGHT.getId())
                .build());
        add(BLINK.getId(), utility(30, 300)
                .addCost(GREEN)
                .addParent(RANDOM_TELEPORT.getId())
                .build());
        add(BLIZZARD.getId(), offense(30, 30)
                .setHidden()
                .build());
        add(CHARM.getId(), utility(165, 165)
                .addCost(BLUE)
                .addParent(LIGHT.getId())
                .build());
        add(CREATE_WATER.getId(), utility(210, 255)
                .addCost(GREEN)
                .addParent(PLANT.getId())
                .build());
        add(DAYLIGHT.getId(), utility(30, 30)
                .setHidden()
                .build());
        add(DIG.getId(), utility(120, 75)
                .addCost(BLUE)
                .addParent(TOUCH.getId())
                .build());
        add(DISARM.getId(), defense(300, 165)
                .addCost(BLUE)
                .addParent(DISPEL.getId())
                .build());
        add(DISPEL.getId(), defense(255, 165)
                .addCost(BLUE)
                .addParent(HEAL.getId())
                .build());
        add(DIVINE_INTERVENTION.getId(), utility(30, 345)
                .addCost(RED)
                .addParent(RECALL.getId())
                .build());
        add(DROUGHT.getId(), utility(210, 300)
                .addCost(GREEN)
                .addParent(CREATE_WATER.getId())
                .build());
        add(ENDER_INTERVENTION.getId(), utility(120, 345)
                .addCost(RED)
                .addParent(RECALL.getId())
                .build());
        add(EXPLOSION.getId(), offense(120, 300)
                .addCost(RED)
                .addParent(FURY.getId())
                .build());
        add(FALLING_STAR.getId(), offense(30, 75)
                .setHidden()
                .build());
        add(FIRE_RAIN.getId(), offense(30, 120)
                .setHidden()
                .build());
        add(FLING.getId(), offense(300, 255)
                .addCost(GREEN)
                .addParent(KNOCKBACK.getId())
                .build());
        add(FORGE.getId(), offense(75, 120)
                .addCost(GREEN)
                .addParent(IGNITION.getId())
                .build());
        add(FROST.getId(), offense(300, 120)
                .addCost(GREEN)
                .addParent(FROST_DAMAGE.getId())
                .build());
        add(GROW.getId(), utility(255, 210)
                .addCost(GREEN)
                .addParent(PLANT.getId())
                .build());
        add(HARVEST.getId(), utility(210, 120)
                .addCost(GREEN)
                .addParent(PLOW.getId())
                .build());
        add(HEAL.getId(), defense(255, 120)
                .addCost(BLUE)
                .addParent(REGENERATION.getId())
                .build());
        add(IGNITION.getId(), offense(120, 120)
                .addCost(GREEN)
                .addParent(FIRE_DAMAGE.getId())
                .build());
        add(KNOCKBACK.getId(), offense(300, 210)
                .addCost(GREEN)
                .addParent(MAGIC_DAMAGE.getId())
                .build());
        add(LIFE_DRAIN.getId(), defense(210, 165)
                .addCost(GREEN)
                .addParent(LIFE_TAP.getId())
                .build());
        add(LIFE_TAP.getId(), defense(210, 120)
                .addCost(GREEN)
                .addParent(HEAL.getId())
                .build());
        add(LIGHT.getId(), utility(120, 165)
                .addCost(BLUE)
                .addParent(DIG.getId())
                .build());
        add(MANA_BLAST.getId(), offense(30, 165)
                .setHidden()
                .build());
        add(MANA_DRAIN.getId(), defense(210, 210)
                .addCost(GREEN)
                .addParent(LIFE_DRAIN.getId())
                .build());
        add(MOONRISE.getId(), utility(30, 75)
                .setHidden()
                .build());
        add(PLACE_BLOCK.getId(), utility(165, 75)
                .addCost(BLUE)
                .addParent(DIG.getId())
                .build());
        add(PLANT.getId(), utility(210, 210)
                .addCost(BLUE)
                .addParent(LIGHT.getId())
                .build());
        add(PLOW.getId(), utility(210, 165)
                .addCost(BLUE)
                .addParent(PLANT.getId())
                .build());
        add(RANDOM_TELEPORT.getId(), utility(30, 255)
                .addCost(GREEN)
                .addParent(INVISIBILITY.getId())
                .build());
        add(RECALL.getId(), utility(75, 345)
                .addCost(GREEN)
                .addParent(TRANSPLACE.getId())
                .build());
        add(REFLECT.getId(), defense(255, 300)
                .addCost(RED)
                .addParent(SHIELD.getId())
                .build());
        add(REPEL.getId(), defense(75, 210)
                .addCost(GREEN)
                .addParent(SLOWNESS.getId())
                .build());
        add(RIFT.getId(), utility(120, 255)
                .addCost(GREEN)
                .addParent(LIGHT.getId())
                .build());
        add(STORM.getId(), offense(120, 165)
                .addCost(RED)
                .addParent(LIGHTNING_DAMAGE.getId())
                .build());
        add(SUMMON.getId(), defense(165, 120)
                .addCost(GREEN)
                .addParent(LIFE_TAP.getId())
                .build());
        add(TELEKINESIS.getId(), utility(165, 300)
                .addCost(GREEN)
                .addParent(ATTRACT.getId())
                .build());
        add(TRANSPLACE.getId(), utility(75, 300)
                .addCost(GREEN)
                .addParent(BLINK.getId())
                .build());
        add(WIZARDS_AUTUMN.getId(), utility(165, 120)
                .addCost(BLUE)
                .addParent(DIG.getId())
                .build());
        add(BOUNCE.getId(), offense(255, 75)
                .addCost(BLUE)
                .addParent(PROJECTILE.getId())
                .build());
        add(DAMAGE.getId(), offense(210, 300)
                .addCost(RED)
                .addParent(CHAIN.getId())
                .build());
        add(DISMEMBERING.getId(), offense(30, 210)
                .setHidden()
                .build());
        add(DURATION.getId(), defense(210, 345)
                .addCost(RED)
                .addParent(TEMPORAL_ANCHOR.getId())
                .build());
        add(EFFECT_POWER.getId(), defense(30, 75)
                .setHidden()
                .build());
        add(GRAVITY.getId(), offense(165, 75)
                .addCost(BLUE)
                .addParent(PROJECTILE.getId())
                .build());
        add(HEALING.getId(), defense(300, 120)
                .addCost(RED)
                .addParent(HEAL.getId())
                .build());
        add(LUNAR.getId(), utility(30, 165)
                .addCost(RED)
                .addParent(NIGHT_VISION.getId())
                .build());
        add(MINING_POWER.getId(), utility(75, 120)
                .addCost(GREEN)
                .addParent(SILK_TOUCH.getId())
                .build());
        add(PIERCING.getId(), offense(345, 120)
                .addCost(RED)
                .addParent(FROST.getId())
                .build());
        add(PROSPERITY.getId(), utility(30, 120)
                .setHidden()
                .build());
        add(RANGE.getId(), utility(75, 255)
                .addCost(RED)
                .addParent(RANDOM_TELEPORT.getId())
                .build());
        add(RUNE_PROCS.getId(), defense(120, 345)
                .addCost(GREEN)
                .addParent(RUNE.getId())
                .build());
        add(SILK_TOUCH.getId(), utility(75, 75)
                .addCost(BLUE)
                .addParent(DIG.getId())
                .build());
        add(SOLAR.getId(), offense(120, 210)
                .addCost(RED)
                .addParent(BLINDNESS.getId())
                .build());
        add(TARGET_NON_SOLID.getId(), utility(75, 30)
                .addCost(BLUE)
                .addParent(TOUCH.getId())
                .build());
        add(VELOCITY.getId(), offense(300, 300)
                .addCost(RED)
                .addParent(FLING.getId())
                .build());
        add(AFFINITY_GAINS_BOOST, talent(120, 30)
                .addCost(BLUE)
                .addParent(MANA_REGEN_BOOST_1)
                .build());
        add(AUGMENTED_CASTING, talent(30, 120)
                .addCost(RED)
                .addParent(SPELL_MOTION)
                .build());
        /*add(EXTRA_SUMMONS, talent(30, 165)
                .addCost(RED)
                .addParent(AUGMENTED_CASTING)
                .build());
        add(MAGE_BAND_1, talent(120, 75)
                .addCost(GREEN)
                .addParent(MANA_REGEN_BOOST_2)
                .build());
        add(MAGE_BAND_2, talent(120, 120)
                .addCost(RED)
                .addParent(MAGE_BAND_1)
                .build());*/
        add(MANA_REGEN_BOOST_1, talent(75, 30)
                .addCost(BLUE)
                .build());
        add(MANA_REGEN_BOOST_2, talent(75, 75)
                .addCost(GREEN)
                .addParent(MANA_REGEN_BOOST_1)
                .build());
        add(MANA_REGEN_BOOST_3, talent(75, 120)
                .addCost(RED)
                .addParent(MANA_REGEN_BOOST_2)
                .build());
        add(SHIELD_OVERLOAD, talent(30, 30)
                .setHidden()
                .build());
        add(SPELL_MOTION, talent(30, 75)
                .addCost(GREEN)
                .addParent(MANA_REGEN_BOOST_2)
                .build());
        add(COLOR.getId(), talent(30, 30)
                .addCost(BLUE)
                .build());
    }

    private Builder offense(int x, int y) {
        return builder(OFFENSE, x, y);
    }

    private Builder defense(int x, int y) {
        return builder(DEFENSE, x, y);
    }

    private Builder utility(int x, int y) {
        return builder(UTILITY, x, y);
    }

    private Builder talent(int x, int y) {
        return builder(TALENT, x, y);
    }
}
