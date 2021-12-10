package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSkillPoints;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts.*;

class AMSkillProvider extends SkillProvider {
    private static final ResourceLocation OFFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense");
    private static final ResourceLocation DEFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "defense");
    private static final ResourceLocation UTILITY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "utility");
    private static final ResourceLocation TALENT  = new ResourceLocation(ArsMagicaAPI.MOD_ID, "talent");

    protected AMSkillProvider(DataGenerator generator) {
        super(generator, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public String getName() {
        return "AMSkillProvider";
    }

    @Override
    protected void createSkills(Consumer<SkillBuilder> consumer) {
        createOffense(AOE              ).setPosition(300, 180).addCost(AMSkillPoints.GREEN.getId()).addParent(FROST_DAMAGE.getId()).addParent(LIGHTNING_DAMAGE.getId()).build(consumer);
        createOffense(PROJECTILE       ).setPosition(300,  45).addCost(AMSkillPoints.BLUE .getId()).build(consumer);
        createDefense(RUNE             ).setPosition(157, 315).addCost(AMSkillPoints.GREEN.getId())/*.addParent(ACCELERATE.getId())*/.addParent(ENTANGLE  .getId()).build(consumer);
        createDefense(SELF             ).setPosition(267,  45).addCost(AMSkillPoints.BLUE .getId()).build(consumer);
        createUtility(TOUCH            ).setPosition(275,  75).addCost(AMSkillPoints.BLUE .getId()).build(consumer);
        createDefense(WALL             ).setPosition( 87, 200).addCost(AMSkillPoints.GREEN.getId())/*.addParent(REPEL.getId())*/.build(consumer);
        createOffense(WAVE             ).setPosition(367, 315).addCost(AMSkillPoints.RED  .getId())/*.addParent(BEAM.getId()).addParent(FLING.getId())*/.build(consumer);
        createDefense(ZONE             ).setPosition(357, 225).addCost(AMSkillPoints.RED  .getId())/*.addParent(DISPEL.getId())*/.build(consumer);

        createOffense(DROWNING_DAMAGE  ).setPosition(435, 135).addCost(AMSkillPoints.BLUE .getId()).addParent(MAGIC_DAMAGE.getId()).build(consumer);
        createOffense(FIRE_DAMAGE      ).setPosition(255, 135).addCost(AMSkillPoints.BLUE .getId()).addParent(PHYSICAL_DAMAGE.getId()).build(consumer);
        createOffense(FROST_DAMAGE     ).setPosition(390, 135).addCost(AMSkillPoints.BLUE .getId()).addParent(MAGIC_DAMAGE.getId()).build(consumer);
        createOffense(LIGHTNING_DAMAGE ).setPosition(210, 135).addCost(AMSkillPoints.BLUE .getId()).addParent(FIRE_DAMAGE.getId()).build(consumer);
        createOffense(MAGIC_DAMAGE     ).setPosition(345, 135).addCost(AMSkillPoints.BLUE .getId()).addParent(PHYSICAL_DAMAGE.getId()).build(consumer);
        createOffense(PHYSICAL_DAMAGE  ).setPosition(300,  90).addCost(AMSkillPoints.BLUE .getId()).addParent(PROJECTILE.getId()).build(consumer);
        createDefense(ABSORPTION       ).setPosition(312, 270).addCost(AMSkillPoints.RED  .getId()).addParent(SHIELD.getId()).build(consumer);
        createOffense(BLINDNESS        ).setPosition(233, 180).addCost(AMSkillPoints.GREEN.getId()).addParent(FIRE_DAMAGE.getId()).addParent(LIGHTNING_DAMAGE.getId()).build(consumer);
        createDefense(HASTE            ).setPosition(177, 155).addCost(AMSkillPoints.BLUE .getId()).addParent(SLOW_FALLING.getId()).build(consumer);
        createUtility(INVISIBILITY     ).setPosition(185, 255).addCost(AMSkillPoints.GREEN.getId()).addParent(TRUE_SIGHT.getId()).build(consumer);
        createDefense(JUMP_BOOST       ).setPosition(222,  90).addCost(AMSkillPoints.BLUE .getId()).addParent(SELF.getId()).build(consumer);
        createDefense(LEVITATION       ).setPosition(222, 225).addCost(AMSkillPoints.GREEN.getId()).addParent(GRAVITY_WELL.getId()).build(consumer);
        createUtility(NIGHT_VISION     ).setPosition(185, 165).addCost(AMSkillPoints.GREEN.getId())/*.addParent(LIGHT.getId())*/.build(consumer);
        createDefense(REGENERATION     ).setPosition(357,  90).addCost(AMSkillPoints.BLUE .getId()).addParent(SELF.getId()).build(consumer);
        createDefense(SLOWNESS         ).setPosition(132, 155).addCost(AMSkillPoints.BLUE .getId()).addParent(SLOW_FALLING.getId()).build(consumer);
        createDefense(SLOW_FALLING     ).setPosition(222, 135).addCost(AMSkillPoints.BLUE .getId()).addParent(JUMP_BOOST.getId()).build(consumer);
        createUtility(WATER_BREATHING  ).setPosition(410, 345).addCost(AMSkillPoints.BLUE .getId())/*.addParent(DROUGHT.getId())*/.build(consumer);
        createOffense(ASTRAL_DISTORTION).setPosition(367, 215).addCost(AMSkillPoints.GREEN.getId()).addParent(MAGIC_DAMAGE.getId()).build(consumer);
        createDefense(ENTANGLE         ).setPosition(132, 245).addCost(AMSkillPoints.GREEN.getId())/*.addParent(REPEL.getId())*/.build(consumer);
        createDefense(FLIGHT           ).setPosition(222, 270).addCost(AMSkillPoints.RED  .getId()).addParent(LEVITATION.getId()).build(consumer);
        createOffense(FURY             ).setPosition(255, 315).addCost(AMSkillPoints.RED  .getId())/*.addParent(BEAM.getId())*/.build(consumer);
        createDefense(GRAVITY_WELL     ).setPosition(222, 180).addCost(AMSkillPoints.GREEN.getId()).addParent(SLOW_FALLING.getId()).build(consumer);
        createDefense(SHIELD           ).setPosition(357, 270).addCost(AMSkillPoints.BLUE .getId())/*.addParent(ZONE.getId())*/.build(consumer);
        createDefense(SHRINK           ).setPosition(402,  90).addCost(AMSkillPoints.BLUE .getId()).addParent(REGENERATION.getId()).build(consumer);
        createOffense(SILENCE          ).setPosition(345, 245).addCost(AMSkillPoints.RED  .getId()).addParent(ASTRAL_DISTORTION.getId()).build(consumer);
        createDefense(SWIFT_SWIM       ).setPosition(177, 200).addCost(AMSkillPoints.BLUE .getId()).addParent(HASTE.getId()).build(consumer);
        createDefense(TEMPORAL_ANCHOR  ).setPosition(312, 315).addCost(AMSkillPoints.RED  .getId())/*.addParent(REFLECT.getId())*/.build(consumer);
        createUtility(TRUE_SIGHT       ).setPosition(185, 210).addCost(AMSkillPoints.BLUE .getId()).addParent(NIGHT_VISION.getId()).build(consumer);
        createOffense(WATERY_GRAVE     ).setPosition(435, 245).addCost(AMSkillPoints.GREEN.getId()).addParent(DROWNING_DAMAGE.getId()).build(consumer);
        createUtility(DIG              ).setPosition(275, 120).addCost(AMSkillPoints.BLUE .getId()).addParent(TOUCH.getId()).build(consumer);

        createOffense(BOUNCE           ).setPosition(345,  70).addCost(AMSkillPoints.BLUE .getId()).addParent(PROJECTILE.getId()).build(consumer);
        createOffense(DAMAGE           ).setPosition(300, 315).addCost(AMSkillPoints.BLUE .getId())/*.addParent(BEAM.getId())*/.build(consumer);
        createOffense(DISMEMBERING     ).setPosition( 30, 135).hidden().build(consumer);
        createDefense(DURATION         ).setPosition(312, 360).addCost(AMSkillPoints.RED  .getId()).addParent(TEMPORAL_ANCHOR.getId()).build(consumer);
        createDefense(EFFECT_POWER     ).setPosition( 75, 225).hidden().build(consumer);
        createOffense(GRAVITY          ).setPosition(255,  70).addCost(AMSkillPoints.BLUE .getId()).addParent(PROJECTILE.getId()).build(consumer);
        createDefense(HEALING          ).setPosition(402, 135).addCost(AMSkillPoints.RED  .getId())/*.addParent(HEAL.getId())*/.build(consumer);
        createUtility(LUNAR            ).setPosition(145, 210).addCost(AMSkillPoints.RED  .getId()).addParent(TRUE_SIGHT.getId()).build(consumer);
        createUtility(MINING_POWER     ).setPosition(185, 137).addCost(AMSkillPoints.GREEN.getId()).addParent(SILK_TOUCH.getId()).build(consumer);
        createOffense(PIERCING         ).setPosition(323, 215).addCost(AMSkillPoints.RED  .getId())/*.addParent(FREEZE.getId())*/.build(consumer);
        createUtility(PROSPERITY       ).setPosition( 75, 135).hidden().build(consumer);
        createUtility(RADIUS           ).setPosition(275, 390).addCost(AMSkillPoints.RED  .getId())/*.addParent(CHANNEL.getId())*/.build(consumer);
        createUtility(RANGE            ).setPosition(140, 345).addCost(AMSkillPoints.RED  .getId())/*.addParent(RANDOM_TELEPORT.getId())*/.build(consumer);
        createDefense(RUNE_PROCS       ).setPosition(157, 360).addCost(AMSkillPoints.GREEN.getId()).addParent(RUNE.getId()).build(consumer);
        createUtility(SILK_TOUCH       ).setPosition(230, 137).addCost(AMSkillPoints.BLUE .getId()).addParent(DIG.getId()).build(consumer);
        createOffense(SOLAR            ).setPosition(210, 255).addCost(AMSkillPoints.RED  .getId()).addParent(BLINDNESS.getId()).build(consumer);
        createUtility(TARGET_NON_SOLID ).setPosition(230,  75).addCost(AMSkillPoints.BLUE .getId()).addParent(TOUCH.getId()).build(consumer);
        createOffense(VELOCITY         ).setPosition(390, 290).addCost(AMSkillPoints.RED  .getId())/*.addParent(FLING.getId())*/.build(consumer);
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
