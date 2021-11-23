package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSkillPoints;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts.*;

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
        SkillBuilder.create(PROJECTILE.getId(), OFFENSE).setPosition(300, 45).addCost(AMSkillPoints.BLUE.getId()).build(consumer);
        SkillBuilder.create(RUNE.getId(), DEFENSE).setPosition(157, 315).addCost(AMSkillPoints.GREEN.getId())/*.addParent(ACCELERATE.getId())*/.addParent(ENTANGLE.getId()).build(consumer);
        SkillBuilder.create(SELF.getId(), DEFENSE).setPosition(267, 45).addCost(AMSkillPoints.BLUE.getId()).build(consumer);
        SkillBuilder.create(TOUCH.getId(), UTILITY).setPosition(275, 75).addCost(AMSkillPoints.BLUE.getId()).build(consumer);

        SkillBuilder.create(DROWNING_DAMAGE.getId(), OFFENSE).setPosition(435, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(MAGIC_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(FIRE_DAMAGE.getId(), OFFENSE).setPosition(255, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(PHYSICAL_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(FROST_DAMAGE.getId(), OFFENSE).setPosition(390, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(MAGIC_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(LIGHTNING_DAMAGE.getId(), OFFENSE).setPosition(210, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(FIRE_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(MAGIC_DAMAGE.getId(), OFFENSE).setPosition(345, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(PHYSICAL_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(PHYSICAL_DAMAGE.getId(), OFFENSE).setPosition(300, 90).addCost(AMSkillPoints.BLUE.getId()).addParent(PROJECTILE.getId()).build(consumer);
        SkillBuilder.create(ABSORPTION.getId(), DEFENSE).setPosition(312, 270).addCost(AMSkillPoints.RED.getId()).addParent(SHIELD.getId()).build(consumer);
        SkillBuilder.create(BLINDNESS.getId(), OFFENSE).setPosition(233, 180).addCost(AMSkillPoints.GREEN.getId()).addParent(FIRE_DAMAGE.getId()).addParent(LIGHTNING_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(HASTE.getId(), DEFENSE).setPosition(177, 155).addCost(AMSkillPoints.BLUE.getId()).addParent(SLOW_FALLING.getId()).build(consumer);
        SkillBuilder.create(INVISIBILITY.getId(), UTILITY).setPosition(185, 255).addCost(AMSkillPoints.GREEN.getId()).addParent(TRUE_SIGHT.getId()).build(consumer);
        SkillBuilder.create(JUMP_BOOST.getId(), DEFENSE).setPosition(222, 90).addCost(AMSkillPoints.BLUE.getId()).addParent(SELF.getId()).build(consumer);
        SkillBuilder.create(LEVITATION.getId(), DEFENSE).setPosition(222, 225).addCost(AMSkillPoints.GREEN.getId()).addParent(GRAVITY_WELL.getId()).build(consumer);
        SkillBuilder.create(REGENERATION.getId(), DEFENSE).setPosition(357, 90).addCost(AMSkillPoints.BLUE.getId()).addParent(SELF.getId()).build(consumer);
        SkillBuilder.create(SLOWNESS.getId(), DEFENSE).setPosition(132, 155).addCost(AMSkillPoints.BLUE.getId()).addParent(SLOW_FALLING.getId()).build(consumer);
        SkillBuilder.create(SLOW_FALLING.getId(), DEFENSE).setPosition(222, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(JUMP_BOOST.getId()).build(consumer);
        SkillBuilder.create(WATER_BREATHING.getId(), UTILITY).setPosition(410, 345).addCost(AMSkillPoints.BLUE.getId())/*.addParent(DROUGHT.getId())*/.build(consumer);
        SkillBuilder.create(ASTRAL_DISTORTION.getId(), OFFENSE).setPosition(367, 215).addCost(AMSkillPoints.GREEN.getId()).addParent(MAGIC_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(ENTANGLE.getId(), DEFENSE).setPosition(132, 245).addCost(AMSkillPoints.GREEN.getId())/*.addParent(REPEL.getId())*/.build(consumer);
        SkillBuilder.create(FLIGHT.getId(), DEFENSE).setPosition(222, 270).addCost(AMSkillPoints.RED.getId()).addParent(LEVITATION.getId()).build(consumer);
        SkillBuilder.create(FURY.getId(), OFFENSE).setPosition(255, 315).addCost(AMSkillPoints.RED.getId())/*.addParent(BEAM.getId())*/.build(consumer);
        SkillBuilder.create(GRAVITY_WELL.getId(), DEFENSE).setPosition(222, 180).addCost(AMSkillPoints.GREEN.getId()).addParent(SLOW_FALLING.getId()).build(consumer);
        SkillBuilder.create(SHIELD.getId(), DEFENSE).setPosition(357, 270).addCost(AMSkillPoints.BLUE.getId())/*.addParent(ZONE.getId())*/.build(consumer);
        SkillBuilder.create(SHRINK.getId(), DEFENSE).setPosition(402, 90).addCost(AMSkillPoints.BLUE.getId()).addParent(REGENERATION.getId()).build(consumer);
        SkillBuilder.create(SILENCE.getId(), OFFENSE).setPosition(345, 245).addCost(AMSkillPoints.RED.getId()).addParent(ASTRAL_DISTORTION.getId()).build(consumer);
        SkillBuilder.create(SWIFT_SWIM.getId(), DEFENSE).setPosition(177, 200).addCost(AMSkillPoints.BLUE.getId()).addParent(HASTE.getId()).build(consumer);
        SkillBuilder.create(TEMPORAL_ANCHOR.getId(), DEFENSE).setPosition(312, 315).addCost(AMSkillPoints.RED.getId())/*.addParent(REFLECT.getId())*/.build(consumer);
        SkillBuilder.create(TRUE_SIGHT.getId(), UTILITY).setPosition(185, 210).addCost(AMSkillPoints.BLUE.getId()).addParent(NIGHT_VISION.getId()).build(consumer);
        SkillBuilder.create(WATERY_GRAVE.getId(), OFFENSE).setPosition(435, 245).addCost(AMSkillPoints.GREEN.getId()).addParent(DROWNING_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(DIG.getId(), UTILITY).setPosition(275, 120).addCost(AMSkillPoints.BLUE.getId()).addParent(TOUCH.getId()).build(consumer);

        SkillBuilder.create(BOUNCE.getId(), OFFENSE).setPosition(345, 70).addCost(AMSkillPoints.BLUE.getId()).addParent(PROJECTILE.getId()).build(consumer);
        SkillBuilder.create(DAMAGE.getId(), OFFENSE).setPosition(300, 315).addCost(AMSkillPoints.BLUE.getId())/*.addParent(BEAM.getId())*/.build(consumer);
        SkillBuilder.create(DISMEMBERING.getId(), OFFENSE).setPosition(30, 135).hidden().build(consumer);
        SkillBuilder.create(DURATION.getId(), DEFENSE).setPosition(312, 360).addCost(AMSkillPoints.RED.getId()).addParent(TEMPORAL_ANCHOR.getId()).build(consumer);
        SkillBuilder.create(EFFECT_POWER.getId(), DEFENSE).setPosition(75, 225).hidden().build(consumer);
        SkillBuilder.create(GRAVITY.getId(), OFFENSE).setPosition(255, 70).addCost(AMSkillPoints.BLUE.getId()).addParent(PROJECTILE.getId()).build(consumer);
        SkillBuilder.create(HEALING.getId(), DEFENSE).setPosition(402, 135).addCost(AMSkillPoints.RED.getId())/*.addParent(HEAL.getId())*/.build(consumer);
        SkillBuilder.create(LUNAR.getId(), UTILITY).setPosition(145, 210).addCost(AMSkillPoints.RED.getId()).addParent(TRUE_SIGHT.getId()).build(consumer);
        SkillBuilder.create(MINING_POWER.getId(), UTILITY).setPosition(185, 137).addCost(AMSkillPoints.GREEN.getId()).addParent(SILK_TOUCH.getId()).build(consumer);
        SkillBuilder.create(PIERCING.getId(), OFFENSE).setPosition(323, 215).addCost(AMSkillPoints.RED.getId())/*.addParent(FREEZE.getId())*/.build(consumer);
        SkillBuilder.create(PROSPERITY.getId(), UTILITY).setPosition(75, 135).hidden().build(consumer);
        SkillBuilder.create(RADIUS.getId(), UTILITY).setPosition(275, 390).addCost(AMSkillPoints.RED.getId())/*.addParent(CHANNEL.getId())*/.build(consumer);
        SkillBuilder.create(RANGE.getId(), UTILITY).setPosition(140, 345).addCost(AMSkillPoints.RED.getId())/*.addParent(RANDOM_TELEPORT.getId())*/.build(consumer);
        SkillBuilder.create(RUNE_PROCS.getId(), DEFENSE).setPosition(157, 360).addCost(AMSkillPoints.GREEN.getId()).addParent(RUNE.getId()).build(consumer);
        SkillBuilder.create(SILK_TOUCH.getId(), UTILITY).setPosition(230, 137).addCost(AMSkillPoints.BLUE.getId()).addParent(DIG.getId()).build(consumer);
        SkillBuilder.create(SOLAR.getId(), OFFENSE).setPosition(210, 255).addCost(AMSkillPoints.RED.getId()).addParent(BLINDNESS.getId()).build(consumer);
        SkillBuilder.create(TARGET_NON_SOLID.getId(), UTILITY).setPosition(230, 75).addCost(AMSkillPoints.BLUE.getId()).addParent(TOUCH.getId()).build(consumer);
        SkillBuilder.create(VELOCITY.getId(), OFFENSE).setPosition(390, 290).addCost(AMSkillPoints.RED.getId())/*.addParent(FLING.getId())*/.build(consumer);
    }
}
