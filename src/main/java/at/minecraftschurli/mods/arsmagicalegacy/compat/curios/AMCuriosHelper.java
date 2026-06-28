package at.minecraftschurli.mods.arsmagicalegacy.compat.curios;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public final class AMCuriosHelper {
    private AMCuriosHelper() {}

    public static boolean hasItemEquipped(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player)
            .map(ICuriosItemHandler::getCurios)
            .map(map -> map.values()
                .stream()
                .map(ICurioStacksHandler::getStacks)
                .anyMatch(items -> {
                    for (int i = 0; i < items.getSlots(); i++) {
                        if (items.getStackInSlot(i).is(item)) return true;
                    }
                    return false;
                }))
            .orElse(false);
    }

    public static void registerMagitechGogglesRenderer() {
        ICurioRenderer.register(AMItems.MAGITECH_GOGGLES.get(), MagitechGogglesCurioRenderer::new);
    }
}
