package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ATTRIBUTES;

@NonExtendable
public interface AMAttributes {
    RegistryObject<Attribute> MAX_MANA      = ATTRIBUTES.register("max_mana", () -> new RangedAttribute(Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, "max_mana")), 0d, 0d, Short.MAX_VALUE).setSyncable(true));
    RegistryObject<Attribute> MAX_BURNOUT   = ATTRIBUTES.register("max_burnout", () -> new RangedAttribute(Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, "max_burnout")), 0d, 0d, Short.MAX_VALUE).setSyncable(true));
    RegistryObject<Attribute> MANA_REGEN    = ATTRIBUTES.register("mana_regen", () -> new RangedAttribute(Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, "mana_regen")), 0.1, 0d, Short.MAX_VALUE).setSyncable(false));
    RegistryObject<Attribute> BURNOUT_REGEN = ATTRIBUTES.register("burnout_regen", () -> new RangedAttribute(Util.makeDescriptionId("attribute", new ResourceLocation(ArsMagicaAPI.MOD_ID, "burnout_regen")), 0.2, 0d, Short.MAX_VALUE).setSyncable(false));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
