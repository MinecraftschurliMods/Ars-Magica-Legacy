package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Holds all event handlers required for the boss spawning.
 */
final class BossSpawnHandler {
    static void init(IEventBus forgeBus) {
        forgeBus.addListener(BossSpawnHandler::livingDeath);
    }

    private static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = entity.getLevel();
        if (entity instanceof Villager villager && villager.isBaby() && level.getMoonPhase() == 0 && PatchouliCompat.getMultiblockMatcher(PatchouliCompat.LIFE_GUARDIAN_SPAWN_RITUAL).test(level, entity.blockPosition())) {
            LifeGuardian guardian = new LifeGuardian(AMEntities.LIFE_GUARDIAN.get(), level);
            guardian.moveTo(villager.position());
            level.addFreshEntity(guardian);
        }
    }
}
