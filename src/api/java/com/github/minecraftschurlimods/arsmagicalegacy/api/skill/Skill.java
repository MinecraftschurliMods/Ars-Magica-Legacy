package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public record Skill(Set<ResourceLocation> parents, Map<ResourceLocation, Integer> cost, ResourceLocation occulusTab, int x, int y, boolean hidden) implements ITranslatable.WithDescription {
    public static final String SKILL = "skill";
    public static final ResourceKey<Registry<Skill>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, SKILL));

    public static final Codec<Skill> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.listOf().<Set<ResourceLocation>>xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("parents").orElseGet(Sets::newHashSet).forGetter(Skill::parents),
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.INT).fieldOf("cost").orElseGet(Maps::newHashMap).forGetter(Skill::cost),
            ResourceLocation.CODEC.fieldOf("occulus_tab").forGetter(Skill::occulusTab),
            Codec.INT.fieldOf("x").forGetter(Skill::x),
            Codec.INT.fieldOf("y").forGetter(Skill::y),
            Codec.BOOL.fieldOf("hidden").orElse(false).forGetter(Skill::hidden)
    ).apply(inst, Skill::new));
    public static final Codec<Holder<Skill>> REFERENCE_CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<Skill>> LIST_CODEC = RegistryCodecs.homogeneousList(REGISTRY_KEY, DIRECT_CODEC);

    @Override
    public Set<ResourceLocation> parents() {
        return Collections.unmodifiableSet(parents);
    }

    @Override
    public Map<ResourceLocation, Integer> cost() {
        return Collections.unmodifiableMap(cost);
    }

    @Override
    public ResourceLocation getId(RegistryAccess access) {
        return access.registryOrThrow(REGISTRY_KEY).getKey(this);
    }

    @Override
    public String getType() {
        return SKILL;
    }
}
