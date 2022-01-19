package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;

import java.util.Optional;

public final class ObeliskFuelManager extends CodecDataManager<ObeliskFuelManager.ObeliskFuel> {
    private static final Lazy<ObeliskFuelManager> INSTANCE = Lazy.concurrentOf(ObeliskFuelManager::new);

    private ObeliskFuelManager() {
        super("obelisk_fuel", ObeliskFuel.CODEC, ObeliskFuel.NETWORK_CODEC, LogManager.getLogger());
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    public static ObeliskFuelManager instance() {
        return INSTANCE.get();
    }

    public Optional<ObeliskFuel> getFuelFor(ItemStack stack) {
        return values().stream().filter(obeliskFuel -> obeliskFuel.ingredient.test(stack)).findFirst();
    }

    public boolean isFuel(ItemStack stack) {
        return getFuelFor(stack).isPresent();
    }

    public record ObeliskFuel(Ingredient ingredient, int burnTime, int etheriumPerTick) {
        public static final Codec<ObeliskFuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CodecHelper.INGREDIENT.fieldOf("input").forGetter(ObeliskFuel::ingredient),
                Codec.INT.optionalFieldOf("burn_time", 200).forGetter(ObeliskFuel::burnTime),
                Codec.INT.fieldOf("etherium_per_tick").forGetter(ObeliskFuel::etheriumPerTick)
        ).apply(instance, ObeliskFuel::new));
        public static final Codec<ObeliskFuel> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CodecHelper.NETWORK_INGREDIENT.fieldOf("input").forGetter(ObeliskFuel::ingredient),
                Codec.INT.optionalFieldOf("burn_time", 200).forGetter(ObeliskFuel::burnTime),
                Codec.INT.fieldOf("etherium_per_tick").forGetter(ObeliskFuel::etheriumPerTick)
        ).apply(instance, ObeliskFuel::new));
    }
}
