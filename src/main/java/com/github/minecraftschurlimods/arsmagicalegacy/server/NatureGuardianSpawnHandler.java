package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public final class NatureGuardianSpawnHandler {
    private static final HashMap<UUID, Long>    lastDryadKills = new HashMap<>();
    private static final HashMap<UUID, Integer> dryadKills     = new HashMap<>();

    static void dryadDeath(LivingDeathEvent event) {
        if (!(event.getEntity().getLevel() instanceof ServerLevel serverLevel)) return;
        if (event.getEntity().getType() == AMEntities.DRYAD.get() && event.getSource().getEntity() instanceof Player player) {
            UUID id = player.getGameProfile().getId();
            long lastDryadKill = lastDryadKills.getOrDefault(id, -1L);
            if (lastDryadKill == -1L || serverLevel.getGameTime() - lastDryadKill <= Config.SERVER.DRYAD_KILL_COOLDOWN.get()) {
                int kills = dryadKills.getOrDefault(id, 0) + 1;
                if (kills < Config.SERVER.DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN.get()) {
                    dryadKills.put(id, kills);
                } else {
                    dryadKills.put(id, 0);
                    AMEntities.NATURE_GUARDIAN.get().spawn(serverLevel, null, null, player, event.getEntity().blockPosition(), MobSpawnType.TRIGGERED, false, false);
                }
            } else {
                dryadKills.put(id, 1);
            }
            lastDryadKills.put(id, serverLevel.getGameTime());
        }
    }
}
