package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * TODO
 */
public interface IAffinity extends IForgeRegistryEntry<IAffinity>, ITranslatable {
    int getColor();
}
