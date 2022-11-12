package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellBuilder;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createPrefabSpells(Consumer<PrefabSpellBuilder> consumer) {
        var api = ArsMagicaAPI.get();
        addPrefabSpell("water_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_blue_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("fire_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("earth_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_acid_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.PHYSICAL_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("ice_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FROST_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("lightning_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("arcane_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("strong_water_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_blue_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("strong_fire_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("strong_earth_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_acid_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.PHYSICAL_DAMAGE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("strong_ice_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FROST_DAMAGE.get(), AMSpellParts.FROST.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("strong_lightning_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.BLINDNESS.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("strong_arcane_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.LEVITATION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("area_lightning", new ResourceLocation(ArsMagicaAPI.MOD_ID, "rip_water_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get())
        )).build(consumer);
        addPrefabSpell("blink", new ResourceLocation(ArsMagicaAPI.MOD_ID, "whirlwind_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINK.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        )).build(consumer);
        addPrefabSpell("chaos_water_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_red_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("debuff", new ResourceLocation(ArsMagicaAPI.MOD_ID, "explosion_sky_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get(), AMSpellParts.SLOWNESS.get(), AMSpellParts.ASTRAL_DISTORTION.get(), AMSpellParts.ENTANGLE.get(), AMSpellParts.GRAVITY_WELL.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("dispel", new ResourceLocation(ArsMagicaAPI.MOD_ID, "shield_royal_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DISPEL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        )).build(consumer);
        addPrefabSpell("ender_bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam_jade_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.RANDOM_TELEPORT.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("ender_torrent", new ResourceLocation(ArsMagicaAPI.MOD_ID, "light_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.KNOCKBACK.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get(), AMSpellParts.AOE.get())
        )).build(consumer);
        addPrefabSpell("ender_wave", new ResourceLocation(ArsMagicaAPI.MOD_ID, "wind_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.KNOCKBACK.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.WAVE.get(), AMSpellParts.RANGE.get())
        )).build(consumer);
        addPrefabSpell("heal_self", new ResourceLocation(ArsMagicaAPI.MOD_ID, "heart_royal_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.HEAL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        )).build(consumer);
        addPrefabSpell("lightning_rune", new ResourceLocation(ArsMagicaAPI.MOD_ID, "rune_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.RUNE.get())
        )).build(consumer);
        addPrefabSpell("melt_armor", new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawner_fire_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MELT_ARMOR.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("nausea", new ResourceLocation(ArsMagicaAPI.MOD_ID, "sword_eerie_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        )).build(consumer);
        addPrefabSpell("otherworldly_roar", new ResourceLocation(ArsMagicaAPI.MOD_ID, "gravity_magenta_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINDNESS.get(), AMSpellParts.SLOWNESS.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get())
        )).build(consumer);
        addPrefabSpell("scramble_synapses", new ResourceLocation(ArsMagicaAPI.MOD_ID, "slice_orange_3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.SCRAMBLE_SYNAPSES.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get())
        )).build(consumer);
    }
}
