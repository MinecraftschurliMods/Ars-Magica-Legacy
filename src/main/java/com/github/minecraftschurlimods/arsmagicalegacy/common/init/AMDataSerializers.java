package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.codeclib.CodecEntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.DATA_SERIALIZERS;

@NonExtendable
public interface AMDataSerializers {
    EntityDataSerializer<Spell> SPELL_SERIALIZER = new CodecEntityDataSerializer<>(Spell.CODEC);

    RegistryObject<DataSerializerEntry> SPELL = DATA_SERIALIZERS.register("spell", () -> new DataSerializerEntry(SPELL_SERIALIZER));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}