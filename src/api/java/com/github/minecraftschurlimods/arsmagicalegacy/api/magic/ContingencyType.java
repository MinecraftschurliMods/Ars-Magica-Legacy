package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Class representing a contingency type. Only used to register them.
 */
public final class ContingencyType {
    public static final ResourceKey<Registry<ContingencyType>> REGISTRY_KEY = ResourceKey.createRegistryKey(ArsMagicaAPI.resource("contingency_type"));
    public static final Codec<ContingencyType> CODEC = Codec.lazyInitialized(() -> ArsMagicaAPI.get().getContingencyTypeRegistry().byNameCodec());

    public static final ResourceLocation NONE = ArsMagicaAPI.resource("none");
    public static final ResourceLocation DEATH = ArsMagicaAPI.resource("death");
    public static final ResourceLocation FIRE = ArsMagicaAPI.resource("fire");
    public static final ResourceLocation HEALTH = ArsMagicaAPI.resource("health");
    public static final ResourceLocation FALL = ArsMagicaAPI.resource("fall");
    public static final ResourceLocation DAMAGE = ArsMagicaAPI.resource("damage");
}
