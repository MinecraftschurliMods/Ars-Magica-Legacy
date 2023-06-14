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
        offense(AOE, 210, 165)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(FROST_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .addParent(MAGIC_DAMAGE.getId())
                .build();
        offense(BEAM, 210, 210)
                .addCost(RED.get())
                .addParent(AOE.getId())
                .build();
        offense(CHAIN, 210, 255)
                .addCost(RED.get())
                .addParent(BEAM.getId())
                .build();
        utility(CHANNEL, 165, 255)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build();
        defense(CONTINGENCY_DAMAGE, 255, 345)
                .addCost(RED.get())
                .addParent(REFLECT.getId())
                .build();
        utility(CONTINGENCY_DEATH, 165, 345)
                .addCost(RED.get())
                .addParent(ENDER_INTERVENTION.getId())
                .build();
        defense(CONTINGENCY_FALL, 75, 120)
                .addCost(RED.get())
                .addParent(SLOW_FALLING.getId())
                .build();
        offense(CONTINGENCY_FIRE, 75, 165)
                .addCost(RED.get())
                .addParent(FORGE.getId())
                .build();
        defense(CONTINGENCY_HEALTH, 300, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build();
        offense(PROJECTILE, 210, 30)
                .addCost(BLUE.get())
                .build();
        defense(RUNE, 120, 300)
                .addCost(GREEN.get())
                .addParent(AGILITY.getId())
                .addParent(ENTANGLE.getId())
                .build();
        defense(SELF, 165, 30)
                .addCost(BLUE.get())
                .build();
        utility(TOUCH, 120, 30)
                .addCost(BLUE.get())
                .build();
        defense(WALL, 30, 210)
                .addCost(GREEN.get())
                .addParent(REPEL.getId())
                .build();
        offense(WAVE, 255, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .addParent(VELOCITY.getId())
                .build();
        defense(ZONE, 255, 210)
                .addCost(RED.get())
                .addParent(DISPEL.getId())
                .build();
        offense(DROWNING_DAMAGE, 300, 165)
                .addCost(BLUE.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build();
        offense(FIRE_DAMAGE, 165, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build();
        offense(FROST_DAMAGE, 255, 120)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build();
        offense(LIGHTNING_DAMAGE, 165, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build();
        offense(MAGIC_DAMAGE, 255, 165)
                .addCost(BLUE.get())
                .addParent(PHYSICAL_DAMAGE.getId())
                .build();
        offense(PHYSICAL_DAMAGE, 210, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build();
        defense(ABSORPTION, 210, 255)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build();
        offense(BLINDNESS, 165, 210)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build();
        defense(HASTE, 165, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId())
                .build();
        defense(HEALTH_BOOST, 30, 30)
                .setHidden()
                .build();
        utility(INVISIBILITY, 30, 210)
                .addCost(GREEN.get())
                .addParent(TRUE_SIGHT.getId())
                .build();
        defense(JUMP_BOOST, 120, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId())
                .build();
        defense(LEVITATION, 120, 210)
                .addCost(GREEN.get())
                .addParent(GRAVITY_WELL.getId())
                .build();
        utility(NIGHT_VISION, 75, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build();
        defense(REGENERATION, 255, 75)
                .addCost(BLUE.get())
                .addParent(SELF.getId())
                .build();
        defense(SLOWNESS, 75, 165)
                .addCost(BLUE.get())
                .addParent(SLOW_FALLING.getId())
                .build();
        defense(SLOW_FALLING, 120, 120)
                .addCost(BLUE.get())
                .addParent(JUMP_BOOST.getId())
                .build();
        utility(WATER_BREATHING, 255, 255)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId())
                .build();
        defense(AGILITY, 165, 255)
                .addCost(GREEN.get())
                .addParent(SWIFT_SWIM.getId())
                .build();
        offense(ASTRAL_DISTORTION, 255, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build();
        defense(ENTANGLE, 75, 255)
                .addCost(GREEN.get())
                .addParent(REPEL.getId())
                .build();
        defense(FLIGHT, 120, 255)
                .addCost(RED.get())
                .addParent(LEVITATION.getId())
                .build();
        offense(FURY, 165, 300)
                .addCost(RED.get())
                .addParent(DAMAGE.getId())
                .build();
        defense(GRAVITY_WELL, 120, 165)
                .addCost(GREEN.get())
                .addParent(SLOW_FALLING.getId())
                .build();
        defense(SHIELD, 255, 255)
                .addCost(RED.get())
                .addParent(ZONE.getId())
                .build();
        defense(SHRINK, 300, 75)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build();
        offense(SILENCE, 255, 255)
                .addCost(RED.get())
                .addParent(ASTRAL_DISTORTION.getId())
                .build();
        defense(SWIFT_SWIM, 165, 210)
                .addCost(GREEN.get())
                .addParent(HASTE.getId())
                .build();
        defense(TEMPORAL_ANCHOR, 210, 300)
                .addCost(RED.get())
                .addParent(REFLECT.getId())
                .build();
        utility(TRUE_SIGHT, 75, 210)
                .addCost(BLUE.get())
                .addParent(NIGHT_VISION.getId())
                .build();
        offense(WATERY_GRAVE, 345, 165)
                .addCost(GREEN.get())
                .addParent(DROWNING_DAMAGE.getId())
                .build();
        utility(ATTRACT, 120, 300)
                .addCost(GREEN.get())
                .addParent(RIFT.getId())
                .build();
        utility(BANISH_RAIN, 255, 300)
                .addCost(GREEN.get())
                .addParent(DROUGHT.getId())
                .build();
        utility(BLINK, 30, 300)
                .addCost(GREEN.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build();
        offense(BLIZZARD, 30, 30)
                .setHidden()
                .build();
        utility(CHARM, 165, 165)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build();
        utility(CREATE_WATER, 210, 255)
                .addCost(GREEN.get())
                .addParent(PLANT.getId())
                .build();
        utility(DAYLIGHT, 30, 30)
                .setHidden()
                .build();
        utility(DIG, 120, 75)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId())
                .build();
        defense(DISARM, 300, 165)
                .addCost(BLUE.get())
                .addParent(DISPEL.getId())
                .build();
        defense(DISPEL, 255, 165)
                .addCost(BLUE.get())
                .addParent(HEAL.getId())
                .build();
        utility(DIVINE_INTERVENTION, 30, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId())
                .build();
        utility(DROUGHT, 210, 300)
                .addCost(GREEN.get())
                .addParent(CREATE_WATER.getId())
                .build();
        utility(ENDER_INTERVENTION, 120, 345)
                .addCost(RED.get())
                .addParent(RECALL.getId())
                .build();
        offense(EXPLOSION, 120, 300)
                .addCost(RED.get())
                .addParent(FURY.getId())
                .build();
        offense(FALLING_STAR, 30, 75)
                .setHidden()
                .build();
        offense(FIRE_RAIN, 30, 120)
                .setHidden()
                .build();
        offense(FLING, 300, 255)
                .addCost(GREEN.get())
                .addParent(KNOCKBACK.getId())
                .build();
        offense(FORGE, 75, 120)
                .addCost(GREEN.get())
                .addParent(IGNITION.getId())
                .build();
        offense(FROST, 300, 120)
                .addCost(GREEN.get())
                .addParent(FROST_DAMAGE.getId())
                .build();
        utility(GROW, 255, 210)
                .addCost(GREEN.get())
                .addParent(PLANT.getId())
                .build();
        utility(HARVEST, 210, 120)
                .addCost(GREEN.get())
                .addParent(PLOW.getId())
                .build();
        defense(HEAL, 255, 120)
                .addCost(BLUE.get())
                .addParent(REGENERATION.getId())
                .build();
        offense(IGNITION, 120, 120)
                .addCost(GREEN.get())
                .addParent(FIRE_DAMAGE.getId())
                .build();
        offense(KNOCKBACK, 300, 210)
                .addCost(GREEN.get())
                .addParent(MAGIC_DAMAGE.getId())
                .build();
        defense(LIFE_DRAIN, 210, 165)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId())
                .build();
        defense(LIFE_TAP, 210, 120)
                .addCost(GREEN.get())
                .addParent(HEAL.getId())
                .build();
        utility(LIGHT, 120, 165)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build();
        offense(MANA_BLAST, 30, 165)
                .setHidden()
                .build();
        defense(MANA_DRAIN, 210, 210)
                .addCost(GREEN.get())
                .addParent(LIFE_DRAIN.getId())
                .build();
        utility(MOONRISE, 30, 75)
                .setHidden()
                .build();
        utility(PLACE_BLOCK, 165, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build();
        utility(PLANT, 210, 210)
                .addCost(BLUE.get())
                .addParent(LIGHT.getId())
                .build();
        utility(PLOW, 210, 165)
                .addCost(BLUE.get())
                .addParent(PLANT.getId())
                .build();
        utility(RANDOM_TELEPORT, 30, 255)
                .addCost(GREEN.get())
                .addParent(INVISIBILITY.getId())
                .build();
        utility(RECALL, 75, 345)
                .addCost(GREEN.get())
                .addParent(TRANSPLACE.getId())
                .build();
        defense(REFLECT, 255, 300)
                .addCost(RED.get())
                .addParent(SHIELD.getId())
                .build();
        defense(REPEL, 75, 210)
                .addCost(GREEN.get())
                .addParent(SLOWNESS.getId())
                .build();
        utility(RIFT, 120, 255)
                .addCost(GREEN.get())
                .addParent(LIGHT.getId())
                .build();
        offense(STORM, 120, 165)
                .addCost(RED.get())
                .addParent(LIGHTNING_DAMAGE.getId())
                .build();
        defense(SUMMON, 165, 120)
                .addCost(GREEN.get())
                .addParent(LIFE_TAP.getId())
                .build();
        utility(TELEKINESIS, 165, 300)
                .addCost(GREEN.get())
                .addParent(ATTRACT.getId())
                .build();
        utility(TRANSPLACE, 75, 300)
                .addCost(GREEN.get())
                .addParent(BLINK.getId())
                .build();
        utility(WIZARDS_AUTUMN, 165, 120)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build();
        offense(BOUNCE, 255, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build();
        offense(DAMAGE, 210, 300)
                .addCost(RED.get())
                .addParent(CHAIN.getId())
                .build();
        offense(DISMEMBERING, 30, 210)
                .setHidden()
                .build();
        defense(DURATION, 210, 345)
                .addCost(RED.get())
                .addParent(TEMPORAL_ANCHOR.getId())
                .build();
        defense(EFFECT_POWER, 30, 75)
                .setHidden()
                .build();
        offense(GRAVITY, 165, 75)
                .addCost(BLUE.get())
                .addParent(PROJECTILE.getId())
                .build();
        defense(HEALING, 300, 120)
                .addCost(RED.get())
                .addParent(HEAL.getId())
                .build();
        utility(LUNAR, 30, 165)
                .addCost(RED.get())
                .addParent(NIGHT_VISION.getId())
                .build();
        utility(MINING_POWER, 75, 120)
                .addCost(GREEN.get())
                .addParent(SILK_TOUCH.getId())
                .build();
        offense(PIERCING, 345, 120)
                .addCost(RED.get())
                .addParent(FROST.getId())
                .build();
        utility(PROSPERITY, 30, 120)
                .setHidden()
                .build();
        utility(RANGE, 75, 255)
                .addCost(RED.get())
                .addParent(RANDOM_TELEPORT.getId())
                .build();
        defense(RUNE_PROCS, 120, 345)
                .addCost(GREEN.get())
                .addParent(RUNE.getId())
                .build();
        utility(SILK_TOUCH, 75, 75)
                .addCost(BLUE.get())
                .addParent(DIG.getId())
                .build();
        offense(SOLAR, 120, 210)
                .addCost(RED.get())
                .addParent(BLINDNESS.getId())
                .build();
        utility(TARGET_NON_SOLID, 75, 30)
                .addCost(BLUE.get())
                .addParent(TOUCH.getId())
                .build();
        offense(VELOCITY, 300, 300)
                .addCost(RED.get())
                .addParent(FLING.getId())
                .build();
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
