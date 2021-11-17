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
        createSpellPartData(AMSpellParts.SELF, 0.5f).build();
        createSpellPartData(AMSpellParts.TOUCH, 1f).build();

        createSpellPartData(AMSpellParts.FIRE_DAMAGE, 120f).withAffinity(AMAffinities.FIRE, 0.01f).build();
        createSpellPartData(AMSpellParts.FROST_DAMAGE, 80f).withAffinity(AMAffinities.ICE, 0.01f).build();
        createSpellPartData(AMSpellParts.LIGHTNING_DAMAGE, 180f).withAffinity(AMAffinities.LIGHTNING, 0.01f).build();
        createSpellPartData(AMSpellParts.MAGIC_DAMAGE, 80f).withAffinity(AMAffinities.ARCANE, 0.01f).withAffinity(AMAffinities.ENDER, 0.005f).build();
        createSpellPartData(AMSpellParts.PHYSICAL_DAMAGE, 40f).withAffinity(AMAffinities.EARTH, 0.01f).build();

        createSpellPartData(AMSpellParts.ABSORPTION, 100f).withAffinity(AMAffinities.LIFE, 0.05f).build();
        createSpellPartData(AMSpellParts.BLINDNESS, 80f).withAffinity(AMAffinities.ENDER, 0.05f).build();
        createSpellPartData(AMSpellParts.HASTE, 80f).withAffinity(AMAffinities.LIGHTNING, 0.05f).build();
        createSpellPartData(AMSpellParts.INVISIBILITY, 80f).withAffinity(AMAffinities.ARCANE, 0.05f).build();
        createSpellPartData(AMSpellParts.JUMP_BOOST, 70f).withAffinity(AMAffinities.AIR, 0.01f).build();
        createSpellPartData(AMSpellParts.LEVITATION, 80f).withAffinity(AMAffinities.AIR, 0.05f).build();
        createSpellPartData(AMSpellParts.NAUSEA, 200f).withAffinity(AMAffinities.LIFE, 0.05f).build();
        createSpellPartData(AMSpellParts.REGENERATION, 540f).withAffinity(AMAffinities.LIFE, 0.05f).withAffinity(AMAffinities.NATURE, 0.05f).build();
        createSpellPartData(AMSpellParts.SLOWNESS, 80f).withAffinity(AMAffinities.ICE, 0.05f).build();
        createSpellPartData(AMSpellParts.SLOW_FALLING, 80f).withAffinity(AMAffinities.AIR, 0.05f).build();
        createSpellPartData(AMSpellParts.WATER_BREATHING, 80f).withAffinity(AMAffinities.WATER, 0.05f).build();

        createSpellPartData(AMSpellParts.DIG, 10f).withAffinity(AMAffinities.EARTH, 0.001f).build();

        createSpellPartData(AMSpellParts.RANGE, 1.2f).build();
        createSpellPartData(AMSpellParts.FORTUNE, 1.25f).build();
        createSpellPartData(AMSpellParts.SILK_TOUCH, 1.25f).build();
        createSpellPartData(AMSpellParts.MINING_POWER, 1.25f).build();
        createSpellPartData(AMSpellParts.TARGET_NON_SOLID, 1f).build();
    }
}
