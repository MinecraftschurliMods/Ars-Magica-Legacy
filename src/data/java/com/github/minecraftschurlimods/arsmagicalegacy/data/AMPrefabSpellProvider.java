package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(LanguageProvider languageProvider, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps, languageProvider);
    }

    @Override
    protected void generate() {
        var api = ArsMagicaAPI.get();
        builder("water_bolt", "Water Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_blue_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("fire_bolt", "Fire Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("earth_bolt", "Earth Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_acid_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.PHYSICAL_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("ice_bolt", "Ice Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FROST_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("lightning_bolt", "Lightning Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("arcane_bolt", "Arcane Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("strong_water_bolt", "Strong Water Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_blue_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("strong_fire_bolt", "Strong Fire Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("strong_earth_bolt", "Strong Earth Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_acid_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.PHYSICAL_DAMAGE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("strong_ice_bolt", "Strong Ice Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.FROST.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("strong_lightning_bolt", "Strong Lightning Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.BLINDNESS.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("strong_arcane_bolt", "Strong Arcane Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.LEVITATION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("area_lightning", "Area Lightning", new ResourceLocation(ArsMagicaAPI.MOD_ID, "rip_water_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get()))
        ).build();
        builder("blink", "Blink", new ResourceLocation(ArsMagicaAPI.MOD_ID, "whirlwind_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINK.get()),
                ShapeGroup.of(AMSpellParts.SELF.get()))
        ).build();
        builder("chaos_water_bolt", "Chaos Water Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_red_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("debuff", "Debuff", new ResourceLocation(ArsMagicaAPI.MOD_ID, "explosion_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get(), AMSpellParts.SLOWNESS.get(), AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.ENTANGLE.get(), AMSpellParts.GRAVITY_WELL.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("dispel", "Dispel", new ResourceLocation(ArsMagicaAPI.MOD_ID, "shield_royal_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DISPEL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get()))
        ).build();
        builder("ender_bolt", "Ender Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_jade_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.RANDOM_TELEPORT.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("ender_torrent", "Ender Torrent", new ResourceLocation(ArsMagicaAPI.MOD_ID, "light_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.KNOCKBACK.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get(), AMSpellParts.AOE.get()))
        ).build();
        builder("ender_wave", "Ender Wave", new ResourceLocation(ArsMagicaAPI.MOD_ID, "wind_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.KNOCKBACK.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.WAVE.get(), AMSpellParts.RANGE.get()))
        ).build();
        builder("heal_self", "Heal Self", new ResourceLocation(ArsMagicaAPI.MOD_ID, "heart_royal_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.HEAL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get()))
        ).build();
        builder("lightning_rune", "Lightning Rune", new ResourceLocation(ArsMagicaAPI.MOD_ID, "rune_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.RUNE.get()))
        ).build();
        builder("melt_armor", "Melt Armor", new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawner_fire_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MELT_ARMOR.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("nausea", "Nausea", new ResourceLocation(ArsMagicaAPI.MOD_ID, "sword_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get()))
        ).build();
        builder("otherworldly_roar", "Otherworldly Roar", new ResourceLocation(ArsMagicaAPI.MOD_ID, "gravity_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINDNESS.get(), AMSpellParts.SLOWNESS.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get()))
        ).build();
        builder("scramble_synapses", "Scramble Synapses", new ResourceLocation(ArsMagicaAPI.MOD_ID, "slice_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.SCRAMBLE_SYNAPSES.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get()))
        ).build();
    }
}
