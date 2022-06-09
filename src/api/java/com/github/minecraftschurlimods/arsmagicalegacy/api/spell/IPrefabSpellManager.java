package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Interface representing a prefab spell manager.
 */
public interface IPrefabSpellManager extends Map<ResourceLocation, IPrefabSpell> {
    /**
     * @param id              The id of the prefab spell to get.
     * @param defaultSupplier The fallback value.
     * @return The prefab spell with the given id, or the fallback value if there is no prefab spell with that id available.
     */
    IPrefabSpell getOrDefault(@Nullable ResourceLocation id, Supplier<IPrefabSpell> defaultSupplier);

    /**
     * @param id The id of the prefab spell to get.
     * @return An optional containing the prefab spell with the given id, or an empty optional if there is no prefab spell with that id available.
     */
    Optional<IPrefabSpell> getOptional(@Nullable ResourceLocation id);
}
