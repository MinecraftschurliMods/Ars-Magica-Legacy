package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Optional;

public record ObeliskFuel(Ingredient ingredient, int burnTime, int etheriumPerTick) {
    public static final String OBELISK_FUEL = "obelisk_fuel";
    public static final ResourceKey<Registry<ObeliskFuel>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, OBELISK_FUEL));
    public static final Codec<ObeliskFuel> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecHelper.INGREDIENT.fieldOf("input").forGetter(ObeliskFuel::ingredient),
            Codec.INT.optionalFieldOf("burn_time").xmap(o -> o.orElse(200), Optional::of).forGetter(ObeliskFuel::burnTime),
            Codec.INT.optionalFieldOf("etherium_per_tick").xmap(o -> o.orElse(1), Optional::of).forGetter(ObeliskFuel::etheriumPerTick)
    ).apply(instance, ObeliskFuel::new));
    public static final Codec<ObeliskFuel> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CodecHelper.NETWORK_INGREDIENT.fieldOf("input").forGetter(ObeliskFuel::ingredient),
            Codec.INT.optionalFieldOf("burn_time", 200).forGetter(ObeliskFuel::burnTime),
            Codec.INT.optionalFieldOf("etherium_per_tick", 1).forGetter(ObeliskFuel::etheriumPerTick)
    ).apply(instance, ObeliskFuel::new));
    public static final Codec<Holder<ObeliskFuel>> REFERENCE_CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<ObeliskFuel>> LIST_CODEC = RegistryCodecs.homogeneousList(REGISTRY_KEY, DIRECT_CODEC);
}
