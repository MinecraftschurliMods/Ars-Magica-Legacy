package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Optional;
import java.util.function.Predicate;

public record ObeliskFuel(Ingredient ingredient, int burnTime, int etheriumPerTick) implements Predicate<ItemStack> {
    public static final ResourceKey<Registry<ObeliskFuel>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "obelisk_fuel"));
    public static final Codec<ObeliskFuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(ObeliskFuel::ingredient),
            Codec.INT.optionalFieldOf("burn_time").xmap(o -> o.orElse(200), Optional::of).forGetter(ObeliskFuel::burnTime),
            Codec.INT.optionalFieldOf("etherium_per_tick").xmap(o -> o.orElse(1), Optional::of).forGetter(ObeliskFuel::etheriumPerTick)
    ).apply(instance, ObeliskFuel::new));

    public int totalEtherium() {
        return burnTime * etheriumPerTick;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return ingredient.test(itemStack);
    }
}
