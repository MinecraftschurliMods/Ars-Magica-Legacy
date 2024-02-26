package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.codeclib.CodecEntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ENTITY_DATA_SERIALIZERS;

@NonExtendable
public interface AMDataSerializers {
    DeferredHolder<EntityDataSerializer<?>, CodecEntityDataSerializer<ISpell>> SPELL = ENTITY_DATA_SERIALIZERS.register("spell", () -> new CodecEntityDataSerializer<>(ISpell.CODEC));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
