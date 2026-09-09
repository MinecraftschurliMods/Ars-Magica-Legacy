package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningCharmItem extends Item {
    public LightningCharmItem(Properties properties) {
        super(properties);
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMServerConfig.LIGHTNING_CHARM_ENABLE_IN_INVENTORY.get() && entity instanceof Player player && player.getInventory().contains(e -> e.is(AMItems.LIGHTNING_CHARM)) || AMUtil.isInCurioSlot(entity, AMItems.LIGHTNING_CHARM.get());
    }

    public static void tick(LivingEntity entity) {
        if (!isEquipped(entity) || entity.level().isClientSide()) return;
        Vec3 pos = entity.position().add(0, 0.75, 0);
        double range = AMServerConfig.LIGHTNING_CHARM_RANGE.get();
        for (ItemEntity item : entity.level().getEntitiesOfClass(ItemEntity.class, new AABB(pos.subtract(range), pos.add(range)))) {
            if (!item.isAlive() || item.hasPickUpDelay()) continue;
            Entity owner = item.getOwner();
            if (owner != null && owner.getId() == entity.getId()) continue;
            Vec3 motion = pos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
            item.setDeltaMovement(Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1 ? motion.normalize() : motion);
        }
    }
}
