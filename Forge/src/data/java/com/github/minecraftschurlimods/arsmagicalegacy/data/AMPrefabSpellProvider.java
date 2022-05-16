package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createPrefabSpells() {
        var api = ArsMagicaAPI.get();
        addPrefabSpell("dispel", "Dispel", new ResourceLocation(ArsMagicaAPI.MOD_ID, "fog-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DISPEL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        ));
        addPrefabSpell("water_bolt", "Water Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam-blue-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("melt_armor", "Melt Armor", new ResourceLocation(ArsMagicaAPI.MOD_ID, "enchant-red-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MELT_ARMOR.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("fire_bolt", "Fire Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "enchant-orange-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("lightning_bolt", "Lightning Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lighting-eerie-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("lightning_rune", "Lightning Rune", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning-orange-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.RUNE.get())
        ));
        addPrefabSpell("area_lightning", "Area Lightning", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lighting-sky-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get())
        ));
        addPrefabSpell("scramble_synapses", "Scramble Synapses", new ResourceLocation(ArsMagicaAPI.MOD_ID, "lighting-royal-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.SCRAMBLE_SYNAPSES.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get(), AMSpellParts.VELOCITY.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get())
        ));
        addPrefabSpell("heal_self", "Heal Self", new ResourceLocation(ArsMagicaAPI.MOD_ID, "protect-royal-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.HEAL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        ));
        addPrefabSpell("nausea", "Nausea", new ResourceLocation(ArsMagicaAPI.MOD_ID, "enchant-acid-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.NAUSEA.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("blink", "Blink", new ResourceLocation(ArsMagicaAPI.MOD_ID, "fog-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINK.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        ));
        addPrefabSpell("arcane_bolt", "Arcane Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "air-burst-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("chaos_water_bolt", "Chaos Water Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire-arrows-sky-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("ender_bolt", "Ender Bolt", new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get(), AMSpellParts.RANDOM_TELEPORT.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ));
        addPrefabSpell("ender_torrent", "Ender Torrent", new ResourceLocation(ArsMagicaAPI.MOD_ID, "light-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.SILENCE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.VELOCITY.get(), AMSpellParts.AOE.get())
        ));
        addPrefabSpell("ender_wave", "Ender Wave", new ResourceLocation(ArsMagicaAPI.MOD_ID, "wind-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.SILENCE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.WAVE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.TOUCH.get())
        ));
        addPrefabSpell("otherworldly_roar", "Otherworldly Roar", new ResourceLocation(ArsMagicaAPI.MOD_ID, "wind-grasp-magenta-3"), api.makeSpell(
                SpellStack.of(AMSpellParts.BLINDNESS.get(), AMSpellParts.SILENCE.get(), AMSpellParts.KNOCKBACK.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get())
        ));
    }
}
