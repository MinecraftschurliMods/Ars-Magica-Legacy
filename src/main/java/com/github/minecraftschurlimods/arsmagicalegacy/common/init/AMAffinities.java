package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.Affinity;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMAffinities {
    RegistryObject<IAffinity> NONE = AMRegistries.AFFINITIES.register("none", () -> Affinity.builder().setColor(0).setDirectOpposite(IAffinity.NONE).build());
    RegistryObject<IAffinity> WATER = AMRegistries.AFFINITIES.register("water", () -> Affinity.builder().setColor(0x0b5cef).setDirectOpposite(IAffinity.FIRE).addMajorOpposites(IAffinity.LIGHTNING, IAffinity.EARTH, IAffinity.ARCANE, IAffinity.ENDER).addMinorOpposites(IAffinity.AIR, IAffinity.ICE).build());
    RegistryObject<IAffinity> FIRE = AMRegistries.AFFINITIES.register("fire", () -> Affinity.builder().setColor(0xef260b).setDirectOpposite(IAffinity.WATER).addMajorOpposites(IAffinity.AIR, IAffinity.ICE, IAffinity.NATURE, IAffinity.LIFE).addMinorOpposites(IAffinity.EARTH, IAffinity.LIGHTNING).build());
    RegistryObject<IAffinity> EARTH = AMRegistries.AFFINITIES.register("earth", () -> Affinity.builder().setColor(0x61330b).setDirectOpposite(IAffinity.AIR).addMajorOpposites(IAffinity.WATER, IAffinity.ARCANE, IAffinity.LIFE, IAffinity.LIGHTNING).addMinorOpposites(IAffinity.NATURE, IAffinity.FIRE).build());
    RegistryObject<IAffinity> AIR = AMRegistries.AFFINITIES.register("air", () -> Affinity.builder().setColor(0x777777).setDirectOpposite(IAffinity.EARTH).addMajorOpposites(IAffinity.NATURE, IAffinity.FIRE, IAffinity.ICE, IAffinity.ENDER).addMinorOpposites(IAffinity.WATER, IAffinity.ARCANE).build());
    RegistryObject<IAffinity> ICE = AMRegistries.AFFINITIES.register("ice", () -> Affinity.builder().setColor(0xd3e8fc).setDirectOpposite(IAffinity.LIGHTNING).addMajorOpposites(IAffinity.LIFE, IAffinity.FIRE, IAffinity.AIR, IAffinity.ARCANE).addMinorOpposites(IAffinity.WATER, IAffinity.ENDER).build());
    RegistryObject<IAffinity> LIGHTNING = AMRegistries.AFFINITIES.register("lightning", () -> Affinity.builder().setColor(0xdece19).setDirectOpposite(IAffinity.ICE).addMajorOpposites(IAffinity.WATER, IAffinity.ENDER, IAffinity.NATURE, IAffinity.EARTH).addMinorOpposites(IAffinity.LIFE, IAffinity.FIRE).build());
    RegistryObject<IAffinity> NATURE = AMRegistries.AFFINITIES.register("nature", () -> Affinity.builder().setColor(0x228718).setDirectOpposite(IAffinity.ARCANE).addMajorOpposites(IAffinity.AIR, IAffinity.ENDER, IAffinity.LIGHTNING, IAffinity.FIRE).addMinorOpposites(IAffinity.LIFE, IAffinity.EARTH).build());
    RegistryObject<IAffinity> LIFE = AMRegistries.AFFINITIES.register("life", () -> Affinity.builder().setColor(0x34e122).setDirectOpposite(IAffinity.ENDER).addMajorOpposites(IAffinity.ARCANE, IAffinity.ICE, IAffinity.FIRE, IAffinity.EARTH).addMinorOpposites(IAffinity.NATURE, IAffinity.LIGHTNING).build());
    RegistryObject<IAffinity> ARCANE = AMRegistries.AFFINITIES.register("arcane", () -> Affinity.builder().setColor(0xb935cd).setDirectOpposite(IAffinity.NATURE).addMajorOpposites(IAffinity.LIFE, IAffinity.EARTH, IAffinity.WATER, IAffinity.ICE).addMinorOpposites(IAffinity.AIR, IAffinity.ENDER).build());
    RegistryObject<IAffinity> ENDER = AMRegistries.AFFINITIES.register("ender", () -> Affinity.builder().setColor(0x3f043d).setDirectOpposite(IAffinity.LIFE).addMajorOpposites(IAffinity.NATURE, IAffinity.LIGHTNING, IAffinity.WATER, IAffinity.AIR).addMinorOpposites(IAffinity.ARCANE, IAffinity.ICE).build());

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
