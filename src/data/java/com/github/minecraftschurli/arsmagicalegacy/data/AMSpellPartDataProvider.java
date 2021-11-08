package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.data.SpellPartDataProvider;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.data.DataGenerator;

public class AMSpellPartDataProvider extends SpellPartDataProvider {
    public AMSpellPartDataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "AMSpellPartData";
    }

    @Override
    protected void createSpellPartData() {
        createSpellPartData(AMSpellParts.FIRE_DAMAGE, 8f, 3f).withAffinity(AMAffinities.FIRE).build();
        createSpellPartData(AMSpellParts.SELF, 1f, 3f).withAffinity(AMAffinities.ARCANE).build();
    }
}
