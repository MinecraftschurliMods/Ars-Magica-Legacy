package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ATTRIBUTES;

@NonExtendable
public interface AMAttributes {
    RegistryObject<Attribute> MAX_MANA      = registerRanged("max_mana", 0d, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> MAX_BURNOUT   = registerRanged("max_burnout", 0d, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> MANA_REGEN    = registerRanged("mana_regen", 0.1, 0d, Short.MAX_VALUE, true);
    RegistryObject<Attribute> BURNOUT_REGEN = registerRanged("burnout_regen", 0.4, 0d, Short.MAX_VALUE, true);

    private static RegistryObject<Attribute> registerRanged(String id, double defaultValue, double minValue, double maxValue, boolean syncable) {
        String key = Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, id));
        return ATTRIBUTES.register(id, () -> new RangedAttribute(key, defaultValue, minValue, maxValue).setSyncable(syncable));
    }

    @Internal
    static void register() {
    }
}
