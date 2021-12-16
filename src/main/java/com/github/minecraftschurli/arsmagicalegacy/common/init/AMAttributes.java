package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ATTRIBUTES;

@NonExtendable
public interface AMAttributes {
    RegistryObject<Attribute> MAX_MANA      = registerRanged("max_mana", 0d, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> MAX_BURNOUT   = registerRanged("max_burnout", 0d, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> MANA_REGEN    = registerRanged("mana_regen", 0.1, 0d, Short.MAX_VALUE, false);
    RegistryObject<Attribute> BURNOUT_REGEN = registerRanged("burnout_regen", 0.2, 0d, Short.MAX_VALUE, false);
    RegistryObject<Attribute> MAGIC_VISION  = registerBool("magic_vision", false, true);

    private static RegistryObject<Attribute> registerRanged(String id, double defaultValue, double minValue, double maxValue, boolean syncable) {
        String key = Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, id));
        return ATTRIBUTES.register(id, () -> new RangedAttribute(key, defaultValue, minValue, maxValue).setSyncable(syncable));
    }

    private static RegistryObject<Attribute> registerBool(String id, boolean defaultValue, boolean syncable) {
        String key = Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, id));
        return ATTRIBUTES.register(id, () -> new BooleanAttribute(key, defaultValue).setSyncable(syncable));
    }

    class BooleanAttribute extends RangedAttribute {
        public BooleanAttribute(String id, boolean defaultValue) {
            super(id, defaultValue ? 1 : 0, 0, 1);
        }

        @Override
        public double sanitizeValue(double value) {
            return ((int) value) > 0 ? 1 : 0;
        }
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
