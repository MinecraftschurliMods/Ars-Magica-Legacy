package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class LightningCharmItem extends Item {
    public LightningCharmItem(Properties properties) {
        super(properties);
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMServerConfig.LIGHTNING_CHARM_ENABLE_IN_INVENTORY.get() && entity instanceof Player player && player.getInventory().contains(e -> e.is(AMItems.LIGHTNING_CHARM)) || AMUtil.isInCurioSlot(entity, AMItems.LIGHTNING_CHARM.get());
    }
}
