package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.Summon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.UUID;

/**
 * Holds all event handlers required for the summon component.
 */
final class SummonHandler {
    @Internal
    static void init(IEventBus forgeBus) {
        forgeBus.addListener(SummonHandler::entityJoinWorld);
        forgeBus.addListener(SummonHandler::livingDeath);
        forgeBus.addListener(SummonHandler::livingExperienceDrop);
        forgeBus.addListener(SummonHandler::livingChangeTarget);
        forgeBus.addListener(SummonHandler::livingIncomingDamage);
        forgeBus.addListener(SummonHandler::livingDamagePost);
    }

    private static void entityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        Entity entity = event.getEntity();
        if (entity.hasData(AMAttachments.SUMMON_MINIONS)) {
            Summon.Minions minions = entity.getData(AMAttachments.SUMMON_MINIONS);
            for (UUID uuid : minions.uuids()) {
                Entity e = serverLevel.getEntity(uuid);
                if (!(e instanceof Mob) || !e.isAlive()) {
                    minions = minions.remove(uuid);
                }
            }
            entity.setData(AMAttachments.SUMMON_MINIONS, minions);
        }
    }

    private static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        if (!entity.hasData(AMAttachments.SUMMON_OWNER)) return;
        Summon.Owner owner = entity.getData(AMAttachments.SUMMON_OWNER);
        if (owner.uuid().isEmpty()) return;
        Entity ownerEntity = serverLevel.getEntity(owner.uuid().get());
        if (ownerEntity == null) return;
        ownerEntity.setData(AMAttachments.SUMMON_MINIONS, ownerEntity.getData(AMAttachments.SUMMON_MINIONS).remove(entity.getUUID()));
    }

    private static void livingExperienceDrop(LivingExperienceDropEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.hasData(AMAttachments.SUMMON_OWNER)) return;
        Summon.Owner owner = entity.getData(AMAttachments.SUMMON_OWNER);
        if (owner.uuid().isEmpty()) return;
        event.setCanceled(true);
    }

    private static void livingChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        if (entity.hasData(AMAttachments.SUMMON_OWNER)) {
            Summon.Owner owner = entity.getData(AMAttachments.SUMMON_OWNER);
            if (owner.uuid().isPresent() && event.getNewAboutToBeSetTarget() == serverLevel.getEntity(owner.uuid().get())) {
                event.setCanceled(true);
            }
        }
        LivingEntity target = event.getNewAboutToBeSetTarget();
        if (target != null) {
            setTargetForMinions(serverLevel, entity, target);
        }
    }

    // Attempting to attack another entity.
    private static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity source)) return;
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        setTargetForMinions(serverLevel, source, entity);
    }
    
    // Getting attacked by another entity.
    private static void livingDamagePost(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity source)) return;
        LivingEntity entity = event.getEntity();
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        setTargetForMinions(serverLevel, entity, source);
    }

    private static void setTargetForMinions(ServerLevel level, LivingEntity owner, LivingEntity target) {
        if (owner.hasData(AMAttachments.SUMMON_MINIONS)) {
            owner.getData(AMAttachments.SUMMON_MINIONS).uuids()
                  .stream()
                  .map(level::getEntity)
                  .filter(Mob.class::isInstance)
                  .map(Mob.class::cast)
                  .filter(mob -> mob.canAttack(target))
                  .forEach(mob -> mob.setTarget(target));
        }
    }
}
