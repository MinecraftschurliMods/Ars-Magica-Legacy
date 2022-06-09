package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class ObeliskFuelManager extends CodecDataManager<ObeliskFuelManager.ObeliskFuel> {
    private static final Lazy<ObeliskFuelManager> INSTANCE = Lazy.concurrentOf(ObeliskFuelManager::new);

    private ObeliskFuelManager() {
        super(ArsMagicaAPI.MOD_ID, "obelisk_fuel", ObeliskFuel.CODEC, ObeliskFuel.NETWORK_CODEC, LoggerFactory.getLogger(ObeliskFuelManager.class));
        subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    }

    /**
     * @return The only instance of this class.
     */
    public static ObeliskFuelManager instance() {
        return INSTANCE.get();
    }

    /**
     * @param stack The item stack to get the fuel for.
     * @return An optional containing the fuel, or an empty optional if there is no fuel associated with the given stack.
     */
    public Optional<ObeliskFuel> getFuelFor(ItemStack stack) {
        return values().stream().filter(obeliskFuel -> obeliskFuel.ingredient.test(stack)).findFirst();
    }

    /**
     * @param stack The item stack to test the fuel for.
     * @return Whether the given stack is a valid obelisk fuel or not.
     */
    public boolean isFuel(ItemStack stack) {
        return getFuelFor(stack).isPresent();
    }

    public record ObeliskFuel(Ingredient ingredient, int burnTime, int etheriumPerTick) {
        public static final Codec<ObeliskFuel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CodecHelper.INGREDIENT.fieldOf("input").forGetter(ObeliskFuel::ingredient),
                Codec.INT.optionalFieldOf("burn_time").xmap(o -> o.orElse(200), Optional::of).forGetter(ObeliskFuel::burnTime),
                Codec.INT.optionalFieldOf("etherium_per_tick").xmap(o -> o.orElse(1), Optional::of).forGetter(ObeliskFuel::etheriumPerTick)
        ).apply(instance, ObeliskFuel::new));
        public static final Codec<ObeliskFuel> NETWORK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CodecHelper.NETWORK_INGREDIENT.fieldOf("input").forGetter(ObeliskFuel::ingredient),
                Codec.INT.optionalFieldOf("burn_time", 200).forGetter(ObeliskFuel::burnTime),
                Codec.INT.optionalFieldOf("etherium_per_tick", 1).forGetter(ObeliskFuel::etheriumPerTick)
        ).apply(instance, ObeliskFuel::new));
    }
}
