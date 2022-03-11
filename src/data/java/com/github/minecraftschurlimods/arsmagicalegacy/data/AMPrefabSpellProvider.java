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

import java.util.List;

class AMPrefabSpellProvider extends PrefabSpellProvider {
    AMPrefabSpellProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createPrefabSpells() {
        addPrefabSpell("boss_dispel", new PrefabSpellManager.PrefabSpell("Dispel", Spell.of(SpellStack.of(AMSpellParts.DISPEL.get()), ShapeGroup.of(AMSpellParts.SELF.get())), new ResourceLocation(ArsMagicaAPI.MOD_ID, "fog-magenta-3")));
    }
}
