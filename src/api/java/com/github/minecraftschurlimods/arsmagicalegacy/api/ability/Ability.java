package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @param affinity The affinity the ability belongs to.
 * @param bounds   The bounds of affinity depth the ability is active in.
 */
public record Ability(Affinity affinity, MinMaxBounds.Doubles bounds) implements ITranslatable.WithDescription, Predicate<Player> {
    public static final String ABILITY = "ability";
    public static final ResourceKey<Registry<Ability>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, ABILITY));

    public static final Codec<Ability> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CodecHelper.forRegistry(ArsMagicaAPI.get()::getAffinityRegistry).fieldOf("affinity").forGetter(Ability::affinity),
            CodecHelper.DOUBLE_MIN_MAX_BOUNDS.fieldOf("bounds").forGetter(Ability::bounds)
    ).apply(inst, Ability::new));

    public static final Codec<Holder<Ability>> REFERENCE_CODEC = RegistryFileCodec.create(REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<Ability>> LIST_CODEC = RegistryCodecs.homogeneousList(REGISTRY_KEY, DIRECT_CODEC);

    @Override
    public boolean test(Player player) {
        return bounds().matches(ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity()));
    }

    @Override
    public String getType() {
        return ABILITY;
    }

    @Override
    public ResourceLocation getId(RegistryAccess registryAccess) {
        return Objects.requireNonNull(registryAccess.registryOrThrow(REGISTRY_KEY).getKey(this));
    }
}
