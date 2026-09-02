package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.LifeWardAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.world.damagesource.DamageSource;
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

    public static void tick(LivingEntity entity) {
        if (isEquipped(entity)) {
            LifeWardAttachment attachment = entity.getData(AMAttachments.LIFE_WARD);
            float health = attachment.health();
            int timeUntilHeal = attachment.isEmpty() ? -AMServerConfig.LIFE_WARD_COOLDOWN.get() : attachment.timeUntilHeal();
            timeUntilHeal++;
            if (timeUntilHeal >= 0 && health < Math.min(AMServerConfig.LIFE_WARD_MAX_HEALTH.get(), entity.getMaxHealth())) {
                health++;
                timeUntilHeal = -AMServerConfig.LIFE_WARD_INTERVAL.get();
            }
            entity.setData(AMAttachments.LIFE_WARD, new LifeWardAttachment(health, timeUntilHeal));
        } else {
            entity.setData(AMAttachments.LIFE_WARD, LifeWardAttachment.EMPTY);
        }
    }

    public static float attack(LivingEntity entity, DamageSource source, float amount) {
        LifeWardAttachment attachment = entity.getData(AMAttachments.LIFE_WARD);
        if (attachment.isEmpty()) return amount;
        float health = attachment.health();
        float newAmount;
        if (source.is(AMTags.DamageTypes.BYPASSES_LIFE_WARD)) {
            newAmount = amount;
        } else if (health >= amount) {
            health -= amount;
            newAmount = 0;
        } else {
            newAmount = amount - health;
            health = 0;
        }
        entity.setData(AMAttachments.LIFE_WARD, new LifeWardAttachment(health, -AMServerConfig.LIFE_WARD_COOLDOWN.get()));
        return newAmount;
    }
}
