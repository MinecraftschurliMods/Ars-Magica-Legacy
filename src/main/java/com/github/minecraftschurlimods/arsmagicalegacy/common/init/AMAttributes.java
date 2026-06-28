package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Util;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public interface AMAttributes {
    DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<Attribute, Attribute> BURNOUT_REGENERATION = register("burnout_regeneration", key -> new RangedAttribute(key, 0.1, 0, Short.MAX_VALUE), Attribute.Sentiment.POSITIVE);
    DeferredHolder<Attribute, Attribute> MANA_REGENERATION    = register("mana_regeneration",    key -> new RangedAttribute(key, 0.1, 0, Short.MAX_VALUE), Attribute.Sentiment.POSITIVE);
    DeferredHolder<Attribute, Attribute> MAX_BURNOUT          = register("max_burnout",          key -> new RangedAttribute(key, 0, 0, Short.MAX_VALUE), Attribute.Sentiment.NEGATIVE);
    DeferredHolder<Attribute, Attribute> MAX_MANA             = register("max_mana",             key -> new RangedAttribute(key, 0, 0, Short.MAX_VALUE), Attribute.Sentiment.POSITIVE);
    // @formatter:on

    private static DeferredHolder<Attribute, Attribute> register(String name, Function<String, Attribute> factory, Attribute.Sentiment sentiment) {
        return ATTRIBUTES.register(name, () -> factory.apply(Util.makeDescriptionId("attribute", ArsMagicaApi.id(name))).setSentiment(sentiment).setSyncable(true));
    }
}
