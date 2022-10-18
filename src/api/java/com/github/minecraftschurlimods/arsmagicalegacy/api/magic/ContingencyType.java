package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class representing a contingency type. Only used to register them.
 */
public final class ContingencyType extends ForgeRegistryEntry<ContingencyType> {
    public static final ResourceLocation NONE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "none");
    public static final ResourceLocation DEATH = new ResourceLocation(ArsMagicaAPI.MOD_ID, "death");
    public static final ResourceLocation FIRE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire");
    public static final ResourceLocation HEALTH = new ResourceLocation(ArsMagicaAPI.MOD_ID, "health");
    public static final ResourceLocation FALL = new ResourceLocation(ArsMagicaAPI.MOD_ID, "fall");
    public static final ResourceLocation DAMAGE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "damage");

    public static final Codec<ContingencyType> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getContingencyTypeRegistry);
}
