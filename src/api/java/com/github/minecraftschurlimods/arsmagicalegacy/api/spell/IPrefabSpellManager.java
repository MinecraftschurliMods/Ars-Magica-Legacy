package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public interface IPrefabSpellManager extends Map<ResourceLocation, IPrefabSpell> {
    IPrefabSpell getOrDefault(@Nullable ResourceLocation id, Supplier<IPrefabSpell> defaultSupplier);

    Optional<IPrefabSpell> getOptional(@Nullable ResourceLocation id);

    Map<ResourceLocation, IPrefabSpell> getData();
}
