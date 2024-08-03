package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(BiConsumer<String, String> langConsumer) {
        super(ArsMagicaAPI.MOD_ID, langConsumer);
    }

    @Override
    public void generate() {
        var api = ArsMagicaAPI.get();
        add("water_bolt", "Water Bolt", ArsMagicaAPI.resource("beam_blue_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("fire_bolt", "Fire Bolt", ArsMagicaAPI.resource("beam_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("earth_bolt", "Earth Bolt", ArsMagicaAPI.resource("beam_acid_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.PHYSICAL_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("ice_bolt", "Ice Bolt", ArsMagicaAPI.resource("beam_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FROST_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("lightning_bolt", "Lightning Bolt", ArsMagicaAPI.resource("beam_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("arcane_bolt", "Arcane Bolt", ArsMagicaAPI.resource("beam_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("strong_water_bolt", "Strong Water Bolt", ArsMagicaAPI.resource("lightning_blue_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("strong_fire_bolt", "Strong Fire Bolt", ArsMagicaAPI.resource("lightning_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("strong_earth_bolt", "Strong Earth Bolt", ArsMagicaAPI.resource("lightning_acid_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.PHYSICAL_DAMAGE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("strong_ice_bolt", "Strong Ice Bolt", ArsMagicaAPI.resource("lightning_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.FROST.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("strong_lightning_bolt", "Strong Lightning Bolt", ArsMagicaAPI.resource("lightning_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.BLINDNESS.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("strong_arcane_bolt", "Strong Arcane Bolt", ArsMagicaAPI.resource("lightning_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.LEVITATION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("area_lightning", "Area Lightning", ArsMagicaAPI.resource("rip_water_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get()))
        );
        add("blink", "Blink", ArsMagicaAPI.resource("whirlwind_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINK.get()),
                ShapeGroup.of(AMSpellParts.SELF.get()))
        );
        add("chaos_water_bolt", "Chaos Water Bolt", ArsMagicaAPI.resource("beam_red_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("debuff", "Debuff", ArsMagicaAPI.resource("explosion_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get(), AMSpellParts.SLOWNESS.get(), AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.ENTANGLE.get(), AMSpellParts.GRAVITY_WELL.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("dispel", "Dispel", ArsMagicaAPI.resource("shield_royal_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DISPEL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get()))
        );
        add("ender_bolt", "Ender Bolt", ArsMagicaAPI.resource("beam_jade_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.RANDOM_TELEPORT.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("ender_torrent", "Ender Torrent", ArsMagicaAPI.resource("light_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.KNOCKBACK.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get(), AMSpellParts.AOE.get()))
        );
        add("ender_wave", "Ender Wave", ArsMagicaAPI.resource("wind_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.KNOCKBACK.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.WAVE.get(), AMSpellParts.RANGE.get()))
        );
        add("heal_self", "Heal Self", ArsMagicaAPI.resource("heart_royal_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.HEAL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get()))
        );
        add("lightning_rune", "Lightning Rune", ArsMagicaAPI.resource("rune_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.RUNE.get()))
        );
        add("melt_armor", "Melt Armor", ArsMagicaAPI.resource("spawner_fire_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MELT_ARMOR.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("nausea", "Nausea", ArsMagicaAPI.resource("sword_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        );
        add("otherworldly_roar", "Otherworldly Roar", ArsMagicaAPI.resource("gravity_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINDNESS.get(), AMSpellParts.SLOWNESS.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get()))
        );
        add("scramble_synapses", "Scramble Synapses", ArsMagicaAPI.resource("slice_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.SCRAMBLE_SYNAPSES.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get()))
        );
    }
}
