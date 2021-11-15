package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

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
        SkillBuilder.create(AMSpellParts.SELF.getId(), DEFENSE).setPosition(267, 45).addCost(AMSkillPoints.BLUE.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.TOUCH.getId(), UTILITY).setPosition(275, 75).addCost(AMSkillPoints.BLUE.getId()).build(consumer);

        SkillBuilder.create(AMSpellParts.FIRE_DAMAGE.getId(), OFFENSE).setPosition(210, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.PHYSICAL_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.FROST_DAMAGE.getId(), OFFENSE).setPosition(345, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.MAGIC_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.LIGHTNING_DAMAGE.getId(), OFFENSE).setPosition(255, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.FIRE_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.MAGIC_DAMAGE.getId(), OFFENSE).setPosition(390, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.PHYSICAL_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.PHYSICAL_DAMAGE.getId(), OFFENSE).setPosition(300, 90).addCost(AMSkillPoints.BLUE.getId())/*.addParent(projectile)*/.build(consumer); // TODO
        SkillBuilder.create(AMSpellParts.ABSORPTION.getId(), DEFENSE).setPosition(312, 270).addCost(AMSkillPoints.RED.getId())/*.addParent(shield)*/.build(consumer); // TODO
        SkillBuilder.create(AMSpellParts.BLINDNESS.getId(), OFFENSE).setPosition(233, 180).addCost(AMSkillPoints.GREEN.getId()).addParent(AMSpellParts.FIRE_DAMAGE.getId()).addParent(AMSpellParts.LIGHTNING_DAMAGE.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.HASTE.getId(), DEFENSE).setPosition(177, 155).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.SLOW_FALLING.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.INVISIBILITY.getId(), UTILITY).setPosition(185, 255).addCost(AMSkillPoints.GREEN.getId())/*.addParent(true_sight)*/.build(consumer); // TODO
        SkillBuilder.create(AMSpellParts.JUMP_BOOST.getId(), DEFENSE).setPosition(222, 90).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.SELF.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.LEVITATION.getId(), DEFENSE).setPosition(222, 225).addCost(AMSkillPoints.GREEN.getId())/*.addParent(gravity_well)*/.build(consumer); // TODO
        //SkillBuilder.create(AMSpellParts.NAUSEA.getId(), ).setPosition().addCost().build(consumer); TODO
        SkillBuilder.create(AMSpellParts.REGENERATION.getId(), DEFENSE).setPosition(357, 90).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.SELF.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.SLOWNESS.getId(), DEFENSE).setPosition(132, 155).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.SLOW_FALLING.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.SLOW_FALLING.getId(), DEFENSE).setPosition(222, 135).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.JUMP_BOOST.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.WATER_BREATHING.getId(), UTILITY).setPosition(410, 345).addCost(AMSkillPoints.BLUE.getId())/*.addParent(drought)*/.build(consumer); // TODO

        SkillBuilder.create(AMSpellParts.DIG.getId(), UTILITY).setPosition(275, 120).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.TOUCH.getId()).build(consumer);

        SkillBuilder.create(AMSpellParts.MINING_POWER.getId(), UTILITY).setPosition(185, 137).addCost(AMSkillPoints.GREEN.getId()).addParent(AMSpellParts.SILK_TOUCH.getId()).build(consumer);
        //SkillBuilder.create(AMSpellParts.FORTUNE.getId(), ).setPosition().addCost().build(consumer); TODO
        SkillBuilder.create(AMSpellParts.SILK_TOUCH.getId(), UTILITY).setPosition(230, 137).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.DIG.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.TARGET_NON_SOLID.getId(), UTILITY).setPosition(230, 75).addCost(AMSkillPoints.BLUE.getId()).addParent(AMSpellParts.TOUCH.getId()).build(consumer);
        SkillBuilder.create(AMSpellParts.RANGE.getId(), UTILITY).setPosition(140, 345).addCost(AMSkillPoints.RED.getId())/*.addParent(random_teleport)*/.build(consumer); // TODO
    }
}
