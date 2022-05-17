package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.codeclib.CodecEntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMDataSerializers {
    RegistrationProvider<DataSerializerEntry> DATA_SERIALIZERS = RegistrationProvider.create(ForgeRegistries.Keys.DATA_SERIALIZERS, ArsMagicaAPI.MOD_ID);
    EntityDataSerializer<ISpell> SPELL_SERIALIZER = new CodecEntityDataSerializer<>(ISpell.CODEC);

    RegistryObject<DataSerializerEntry> SPELL = DATA_SERIALIZERS.register("spell", () -> new DataSerializerEntry(SPELL_SERIALIZER));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
