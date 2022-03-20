package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.DATA_SERIALIZERS;

@NonExtendable
public interface AMDataSerializers {
    EntityDataSerializer<Spell> SPELL_SERIALIZER = new EntityDataSerializer<>() {
        public void write(FriendlyByteBuf p_135182_, Spell p_135183_) {
            p_135182_.writeNbt((CompoundTag) Spell.CODEC.encodeStart(NbtOps.INSTANCE, p_135183_).getOrThrow(false, ArsMagicaLegacy.LOGGER::error));
        }

        public Spell read(FriendlyByteBuf p_135188_) {
            return Spell.CODEC.decode(NbtOps.INSTANCE, p_135188_.readNbt()).getOrThrow(false, ArsMagicaLegacy.LOGGER::error).getFirst();
        }

        public Spell copy(Spell p_135176_) {
            return Spell.of(p_135176_.spellStack(), p_135176_.shapeGroups().toArray(new ShapeGroup[0]));
        }
    };

    RegistryObject<DataSerializerEntry> SPELL = DATA_SERIALIZERS.register("spell", () -> new DataSerializerEntry(SPELL_SERIALIZER));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
