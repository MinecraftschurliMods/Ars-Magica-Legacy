package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.BLUE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.GREEN;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints.RED;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts.*;

class AMSkillProvider extends SkillProvider {
    private static final ResourceLocation OFFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense");
    private static final ResourceLocation DEFENSE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "defense");
    private static final ResourceLocation UTILITY = new ResourceLocation(ArsMagicaAPI.MOD_ID, "utility");
    private static final ResourceLocation TALENT = new ResourceLocation(ArsMagicaAPI.MOD_ID, "talent");

    AMSkillProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate() {
        add(offense(AOE, 210, 165)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(FROST_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .addParent(MAGIC_DAMAGE.getId()));
        add(offense(BEAM, 210, 210)
                .addCost(RED.get())
                .addParent(AOE.getId()));
        add(offense(CHAIN, 210, 255)
                .addCost(RED.get())
                .addParent(BEAM.getId()));
        add(utility(CHANNEL, 165, 255)
                .addCost(GREEN.get())
                .addParent(RIFT.getId()));
        add(defense(CONTINGENCY_DAMAGE, 255, 345)
                .addCost(RED.get())
                .addParent(REFLECT.getId()));
        add(utility(CONTINGENCY_DEATH, 165, 345)
                .addCost(RED.get())
                .addParent(ENDER_INTERVENTION.getId()));
        add(defense(CONTINGENCY_FALL, 75, 120)
                .addCost(RED.get())
                .addParent(SLOW_FALLING.getId()));
        add(offense(CONTINGENCY_FIRE, 75, 165)
                .addCost(RED.get())
                .addParent(FORGE.getId()));
        add(defense(CONTINGENCY_HEALTH, 300, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId()));
        add(offense(PROJECTILE, 210, 30)
                .addCost(BLUE.get()));
        add(defense(RUNE, 120, 300)
                .addCost(GREEN.get())
                .addParent(AGILITY.getId())
                .addParent(ENTANGLE.getId()));
        add(defense(SELF, 165, 30)
                .addCost(BLUE.get()));
        add(utility(TOUCH, 120, 30)
                .addCost(BLUE.get()));
        add(defense(WALL, 30, 210)
                .addCost(GREEN.get())
                .addParent(REPEL.getId()));
        add(offense(WAVE, 255, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .addParent(VELOCITY.getId()));
        add(defense(ZONE, 255, 210)
                .addCost(RED.get())
                .addParent(DISPEL.getId()));
        add(offense(DROWNING_DAMAGE, 300, 165)
                .addCost(BLUE.get())
                .addParent(MAGIC_DAMAGE.getId()));
        add(offense(FIRE_DAMAGE, 165, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(offense(FROST_DAMAGE, 255, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(offense(LIGHTNING_DAMAGE, 165, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(offense(MAGIC_DAMAGE, 255, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId()));
        add(offense(PHYSICAL_DAMAGE, 210, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId()));
        add(defense(ABSORPTION, 210, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId()));
        add(offense(BLINDNESS, 165, 210)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId()));
        add(defense(HASTE, 165, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId()));
        add(defense(HEALTH_BOOST, 30, 30)
                .setHidden());
        add(utility(INVISIBILITY, 30, 210)
                .addCost(GREEN.get())
                .addParent(TRUE_SIGHT.getId()));
        add(defense(JUMP_BOOST, 120, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId()));
        add(defense(LEVITATION, 120, 210)
                .addCost(GREEN.get())
                .addParent(GRAVITY_WELL.getId()));
        add(utility(NIGHT_VISION, 75, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId()));
        add(defense(REGENERATION, 255, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId()));
        add(defense(SLOWNESS, 75, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId()));
        add(defense(SLOW_FALLING, 120, 120)
                .addCost(BLUE.get())
                .addParent(JUMP_BOOST.getId()));
        add(utility(WATER_BREATHING, 255, 255)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId()));
        add(defense(AGILITY, 165, 255)
                .addCost(GREEN.get())
                .addParent(SWIFT_SWIM.getId()));
        add(offense(ASTRAL_DISTORTION, 255, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId()));
        add(defense(ENTANGLE, 75, 255)
                .addCost(GREEN.get())
                .addParent(REPEL.getId()));
        add(defense(FLIGHT, 120, 255)
                .addCost(RED.get())
                .addParent(LEVITATION.getId()));
        add(offense(FURY, 165, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId()));
        add(defense(GRAVITY_WELL, 120, 165)
                .addCost(GREEN.get())
                .addParent(SLOW_FALLING.getId()));
        add(defense(SHIELD, 255, 255)
                .addCost(RED.get())
                .addParent(ZONE.getId()));
        add(defense(SHRINK, 300, 75)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId()));
        add(offense(SILENCE, 255, 255)
                .addCost(RED.get())
                .addParent(ASTRAL_DISTORTION.getId()));
        add(defense(SWIFT_SWIM, 165, 210)
                .addCost(GREEN.get())
                .addParent(HASTE.getId()));
        add(defense(TEMPORAL_ANCHOR, 210, 300)
                .addCost(RED.get())
                .addParent(REFLECT.getId()));
        add(utility(TRUE_SIGHT, 75, 210)
                .addCost(BLUE.get())
                .addParent(NIGHT_VISION.getId()));
        add(offense(WATERY_GRAVE, 345, 165)
                .addCost(GREEN.get())
                .addParent(DROWNING_DAMAGE.getId()));
        add(utility(ATTRACT, 120, 300)
                .addCost(GREEN.get())
                .addParent(RIFT.getId()));
        add(utility(BANISH_RAIN, 255, 300)
                .addCost(GREEN.get())
                .addParent(DROUGHT.getId()));
        add(utility(BLINK, 30, 300)
                .addCost(GREEN.get())
                .addParent(RANDOM_TELEPORT.getId()));
        add(offense(BLIZZARD, 30, 30)
                .setHidden());
        add(utility(CHARM, 165, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId()));
        add(utility(CREATE_WATER, 210, 255)
                .addCost(GREEN.get())
                .addParent(PLANT.getId()));
        add(utility(DAYLIGHT, 30, 30)
                .setHidden());
        add(utility(DIG, 120, 75)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId()));
        add(defense(DISARM, 300, 165)
                .addCost(BLUE.get())
                .addParent(DISPEL.getId()));
        add(defense(DISPEL, 255, 165)
                .addCost(BLUE.get())
                .addParent(HEAL.getId()));
        add(utility(DIVINE_INTERVENTION, 30, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId()));
        add(utility(DROUGHT, 210, 300)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId()));
        add(utility(ENDER_INTERVENTION, 120, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId()));
        add(offense(EXPLOSION, 120, 300)
                .addCost(RED.get())
                .addParent(FURY.getId()));
        add(offense(FALLING_STAR, 30, 75)
                .setHidden());
        add(offense(FIRE_RAIN, 30, 120)
                .setHidden());
        add(offense(FLING, 300, 255)
                .addCost(GREEN.get())
                .addParent(KNOCKBACK.getId()));
        add(offense(FORGE, 75, 120)
                .addCost(GREEN.get())
                .addParent(IGNITION.getId()));
        add(offense(FROST, 300, 120)
                .addCost(GREEN.get())
                .addParent(FROST_DAMAGE.getId()));
        add(utility(GROW, 255, 210)
                .addCost(GREEN.get())
                .addParent(PLANT.getId()));
        add(utility(HARVEST, 210, 120)
                .addCost(GREEN.get())
                .addParent(PLOW.getId()));
        add(defense(HEAL, 255, 120)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId()));
        add(offense(IGNITION, 120, 120)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId()));
        add(offense(KNOCKBACK, 300, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId()));
        add(defense(LIFE_DRAIN, 210, 165)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId()));
        add(defense(LIFE_TAP, 210, 120)
                .addCost(GREEN.get())
                .addParent(HEAL.getId()));
        add(utility(LIGHT, 120, 165)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(offense(MANA_BLAST, 30, 165)
                .setHidden());
        add(defense(MANA_DRAIN, 210, 210)
                .addCost(GREEN.get())
                .addParent(LIFE_DRAIN.getId()));
        add(utility(MOONRISE, 30, 75)
                .setHidden());
        add(utility(PLACE_BLOCK, 165, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(utility(PLANT, 210, 210)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId()));
        add(utility(PLOW, 210, 165)
                .addCost(BLUE.get())
                .addParent(PLANT.getId()));
        add(utility(RANDOM_TELEPORT, 30, 255)
                .addCost(GREEN.get())
                .addParent(INVISIBILITY.getId()));
        add(utility(RECALL, 75, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId()));
        add(defense(REFLECT, 255, 300)
                .addCost(RED.get())
                .addParent(SHIELD.getId()));
        add(defense(REPEL, 75, 210)
                .addCost(GREEN.get())
                .addParent(SLOWNESS.getId()));
        add(utility(RIFT, 120, 255)
                .addCost(GREEN.get())
                .addParent(LIGHT.getId()));
        add(offense(STORM, 120, 165)
                .addCost(RED.get())
                .addParent(LIGHTNING_DAMAGE.getId()));
        add(defense(SUMMON, 165, 120)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId()));
        add(utility(TELEKINESIS, 165, 300)
                .addCost(GREEN.get())
                .addParent(ATTRACT.getId()));
        add(utility(TRANSPLACE, 75, 300)
                .addCost(GREEN.get())
                .addParent(BLINK.getId()));
        add(utility(WIZARDS_AUTUMN, 165, 120)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(offense(BOUNCE, 255, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId()));
        add(offense(DAMAGE, 210, 300)
                .addCost(RED.get())
                .addParent(CHAIN.getId()));
        add(offense(DISMEMBERING, 30, 210)
                .setHidden());
        add(defense(DURATION, 210, 345)
                .addCost(RED.get())
                .addParent(TEMPORAL_ANCHOR.getId()));
        add(defense(EFFECT_POWER, 30, 75)
                .setHidden());
        add(offense(GRAVITY, 165, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId()));
        add(defense(HEALING, 300, 120)
                .addCost(RED.get())
                .addParent(HEAL.getId()));
        add(utility(LUNAR, 30, 165)
                .addCost(RED.get())
                .addParent(NIGHT_VISION.getId()));
        add(utility(MINING_POWER, 75, 120)
                .addCost(GREEN.get())
                .addParent(SILK_TOUCH.getId()));
        add(offense(PIERCING, 345, 120)
                .addCost(RED.get())
                .addParent(FROST.getId()));
        add(utility(PROSPERITY, 30, 120)
                .setHidden());
        add(utility(RANGE, 75, 255)
                .addCost(RED.get())
                .addParent(RANDOM_TELEPORT.getId()));
        add(defense(RUNE_PROCS, 120, 345)
                .addCost(GREEN.get())
                .addParent(RUNE.getId()));
        add(utility(SILK_TOUCH, 75, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId()));
        add(offense(SOLAR, 120, 210)
                .addCost(RED.get())
                .addParent(BLINDNESS.getId()));
        add(utility(TARGET_NON_SOLID, 75, 30)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId()));
        add(offense(VELOCITY, 300, 300)
                .addCost(RED.get())
                .addParent(FLING.getId()));
    }

    private Builder offense(RegistryObject<? extends ISpellPart> part, int x, int y) {
        return builder(part.getId().getPath(), OFFENSE, x, y);
    }

    private Builder defense(RegistryObject<? extends ISpellPart> part, int x, int y) {
        return builder(part.getId().getPath(), DEFENSE, x, y);
    }

    private Builder utility(RegistryObject<? extends ISpellPart> part, int x, int y) {
        return builder(part.getId().getPath(), UTILITY, x, y);
    }

    private Builder talent(RegistryObject<? extends ISpellPart> part, int x, int y) {
        return builder(part.getId().getPath(), TALENT, x, y);
    }
}
