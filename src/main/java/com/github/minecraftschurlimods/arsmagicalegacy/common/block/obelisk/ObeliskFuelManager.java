package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class ObeliskFuelManager {

    private ObeliskFuelManager() {}

    /**
     * @param registryAccess
     * @param stack          The item stack to test the fuel for.
     * @return Whether the given stack is a valid obelisk fuel or not.
     */
    public static boolean isFuel(RegistryAccess registryAccess, ItemStack stack) {
        return getFuelFor(registryAccess, stack).isPresent();
    }

    public static Optional<ObeliskFuel> getFuelFor(RegistryAccess registryAccess, ItemStack stack) {
        return registryAccess.registryOrThrow(ObeliskFuel.REGISTRY_KEY).stream().filter(obeliskFuel -> obeliskFuel.ingredient().test(stack)).findFirst();
    }
}
