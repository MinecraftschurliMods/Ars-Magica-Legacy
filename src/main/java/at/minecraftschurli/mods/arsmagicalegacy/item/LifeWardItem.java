package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class LifeWardItem extends Item {
    public LifeWardItem(Properties properties) {
        super(properties);
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMServerConfig.LIFE_WARD_ENABLE_IN_INVENTORY.get() && entity instanceof Player player && player.getInventory().contains(e -> e.is(AMItems.LIFE_WARD)) || AMUtil.isInCurioSlot(entity, AMItems.LIFE_WARD.get());
    }
}
