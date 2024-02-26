package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class AMParticleDefinitionsProvider extends ParticleDescriptionProvider {
    protected AMParticleDefinitionsProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(AMParticleTypes.NONE_HAND, "none_hand", 15);
        spriteSet(AMParticleTypes.WATER_HAND, "water_hand", 30);
        spriteSet(AMParticleTypes.FIRE_HAND, "fire_hand", 25);
        spriteSet(AMParticleTypes.EARTH_HAND, "earth_hand", 18);
        spriteSet(AMParticleTypes.AIR_HAND, "air_hand", 25);
        spriteSet(AMParticleTypes.ICE_HAND, "ice_hand", 30);
        spriteSet(AMParticleTypes.LIGHTNING_HAND, "lightning_hand", 20);
        spriteSet(AMParticleTypes.NATURE_HAND, "nature_hand", 30);
        spriteSet(AMParticleTypes.LIFE_HAND, "life_hand", 25);
        spriteSet(AMParticleTypes.ARCANE_HAND, "arcane_hand", 28);
        spriteSet(AMParticleTypes.ENDER_HAND, "ender_hand", 30);
        spriteSet(AMParticleTypes.ARCANE, "arcane", 8);
        sprite(AMParticleTypes.CLOCK, "clock");
        sprite(AMParticleTypes.EMBER, "ember");
        spriteSet(AMParticleTypes.EXPLOSION, "explosion", 24);
        sprite(AMParticleTypes.GHOST, "ghost");
        sprite(AMParticleTypes.LEAF, "leaf");
        spriteSet(AMParticleTypes.LENS_FLARE, "lens_flare", 13);
        spriteSet(AMParticleTypes.LIGHTS, "lights", 8);
        spriteSet(AMParticleTypes.PLANT, "plant", 13);
        spriteSet(AMParticleTypes.PULSE, "pulse", 24);
        spriteSet(AMParticleTypes.ROCK, "rock", 16);
        spriteSet(AMParticleTypes.ROTATING_RINGS, "rotating_rings", 60);
        sprite(AMParticleTypes.STARDUST, "stardust");
        sprite(AMParticleTypes.WATER_BALL, "water_ball");
        spriteSet(AMParticleTypes.WIND, "wind", 10);
        for (int i = 0; i < 28; i++) {
            sprite(AMParticleTypes.SYMBOLS.registryObject(i), "symbols_" + i);
        }
    }

    private void spriteSet(Supplier<SimpleParticleType> particle, String name, int textureCount) {
        spriteSet(particle.get(), new ResourceLocation(ArsMagicaAPI.MOD_ID, name), textureCount, false);
    }

    private void sprite(Supplier<SimpleParticleType> particle, String name) {
        sprite(particle.get(), new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }
}
