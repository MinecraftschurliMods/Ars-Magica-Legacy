package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.PrefabSpellProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createPrefabSpells() {
        addPrefabSpell("dispel", new PrefabSpellManager.PrefabSpell("Dispel", Spell.of(
                SpellStack.of(AMSpellParts.DISPEL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "fog-magenta-3")));
        addPrefabSpell("water_bolt", new PrefabSpellManager.PrefabSpell("Water Bolt", Spell.of(
                SpellStack.of(AMSpellParts.DROWNING_DAMAGE.get(), AMSpellParts.WATERY_GRAVE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "beam-blue-3")));
        addPrefabSpell("melt_armor", new PrefabSpellManager.PrefabSpell("Melt Armor", Spell.of(
                SpellStack.of(AMSpellParts.MELT_ARMOR.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "enchant-red-3")));
        addPrefabSpell("fire_bolt", new PrefabSpellManager.PrefabSpell("Fire Bolt", Spell.of(
                SpellStack.of(AMSpellParts.FIRE_DAMAGE.get(), AMSpellParts.IGNITION.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "enchant-orange-3")));
        addPrefabSpell("lightning_bolt", new PrefabSpellManager.PrefabSpell("Lightning Bolt", Spell.of(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "lighting-eerie-3")));
        addPrefabSpell("lightning_rune", new PrefabSpellManager.PrefabSpell("Lightning Rune", Spell.of(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.RUNE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning-orange-3")));
        addPrefabSpell("area_lightning", new PrefabSpellManager.PrefabSpell("Area Lightning", Spell.of(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "lighting-sky-3")));
        addPrefabSpell("scramble_synapses", new PrefabSpellManager.PrefabSpell("Scramble Synapses", Spell.of(
                SpellStack.of(AMSpellParts.LIGHTNING_DAMAGE.get(), AMSpellParts.SCRAMBLE_SYNAPSES.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get(), AMSpellParts.AOE.get(), AMSpellParts.VELOCITY.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get(), AMSpellParts.RANGE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "lighting-royal-3")));
        addPrefabSpell("heal_self", new PrefabSpellManager.PrefabSpell("Heal Self", Spell.of(
                SpellStack.of(AMSpellParts.HEAL.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "protect-royal-3")));
        addPrefabSpell("nausea", new PrefabSpellManager.PrefabSpell("Nausea", Spell.of(
                SpellStack.of(AMSpellParts.NAUSEA.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "enchant-acid-3")));
        addPrefabSpell("blink", new PrefabSpellManager.PrefabSpell("Blink", Spell.of(
                SpellStack.of(AMSpellParts.BLINK.get()),
                ShapeGroup.of(AMSpellParts.SELF.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "fog-magenta-3")));
        addPrefabSpell("arcane_bolt", new PrefabSpellManager.PrefabSpell("Arcane Bolt", Spell.of(
                SpellStack.of(AMSpellParts.MAGIC_DAMAGE.get()),
                ShapeGroup.of(AMSpellParts.PROJECTILE.get())
        ), new ResourceLocation(ArsMagicaAPI.MOD_ID, "air-burst-magenta-3")));
    }
}
