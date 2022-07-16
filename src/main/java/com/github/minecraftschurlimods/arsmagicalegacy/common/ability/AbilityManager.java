package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class AbilityManager extends CodecDataManager<IAbilityData> implements IAbilityManager {
    private static final Lazy<AbilityManager> INSTANCE = Lazy.concurrentOf(AbilityManager::new);

    private AbilityManager() {
        super(ArsMagicaAPI.MOD_ID, "affinity_abilities", AbilityData.CODEC, (data, logger) -> {
            IForgeRegistry<IAbility> affinityRegistry = ArsMagicaAPI.get().getAbilityRegistry();
            data.keySet().removeIf(ability -> !affinityRegistry.containsKey(ability));
        }, LoggerFactory.getLogger(AbilityManager.class));
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    @Override
    public List<ResourceLocation> getAbilitiesForPlayer(Player player) {
        return entrySet().stream().filter(e -> e.getValue().test(player)).map(Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public List<ResourceLocation> getAbilitiesForAffinity(IAffinity affinity) {
        return getAbilitiesForAffinity(affinity.getId());
    }

    @Override
    public List<ResourceLocation> getAbilitiesForAffinity(ResourceLocation affinity) {
        return entrySet().stream().filter(entry -> entry.getValue().affinity().getId().equals(affinity)).map(Entry::getKey).collect(Collectors.toList());
    }

    @Override
    public boolean hasAbility(Player player, ResourceLocation ability) {
        return get(ability).test(player);
    }

    /**
     * @return The only instance of this class.
     */
    public static AbilityManager instance() {
        return INSTANCE.get();
    }

    @Override
    public IAbilityData getOrThrow(@Nullable ResourceLocation id) {
        return super.getOrThrow(id);
    }

    @Override
    public Optional<IAbilityData> getOptional(@Nullable ResourceLocation id) {
        return super.getOptional(id);
    }

    private record AbilityData(IAffinity affinity, MinMaxBounds.Doubles bounds) implements IAbilityData {
        private static final Codec<IAbilityData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                CodecHelper.forRegistry(ArsMagicaAPI.get()::getAffinityRegistry).fieldOf("affinity").forGetter(IAbilityData::affinity),
                CodecHelper.DOUBLE_MIN_MAX_BOUNDS.fieldOf("bounds").forGetter(IAbilityData::bounds)
        ).apply(inst, AbilityData::new));
    }
}
