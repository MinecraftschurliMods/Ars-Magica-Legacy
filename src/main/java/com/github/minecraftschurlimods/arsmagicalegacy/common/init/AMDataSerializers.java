package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.codeclib.CodecEntityDataSerializer;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.DATA_SERIALIZERS;

@NonExtendable
public interface AMDataSerializers {
    RegistryObject<CodecEntityDataSerializer<ISpell>> SPELL = DATA_SERIALIZERS.register("spell", () -> new CodecEntityDataSerializer<>(ISpell.CODEC));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
