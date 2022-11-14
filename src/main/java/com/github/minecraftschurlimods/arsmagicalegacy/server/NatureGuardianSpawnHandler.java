package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public final class NatureGuardianSpawnHandler {
    private static final HashMap<UUID, Long> lastDryadKills = new HashMap<>();
    private static final HashMap<UUID, Integer> dryadKills = new HashMap<>();

    static void dryadDeath(LivingDeathEvent event) {
        if (!(event.getEntity().getLevel() instanceof ServerLevel serverLevel)) return;
        long cooldown = Config.SERVER.DRYAD_KILL_COOLDOWN.get();
        if (cooldown == 0) return;
        if (event.getEntity().getType() != AMEntities.DRYAD.get()) return;
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        UUID id = player.getGameProfile().getId();
        long time = serverLevel.getGameTime();
        BlockPos pos = event.getEntity().blockPosition();
        dryadKills.compute(id, (playerId, kills) -> {
            long lastDryadKill = lastDryadKills.getOrDefault(playerId, -1L);
            long dTime = time - lastDryadKill;
            if (lastDryadKill != -1L && dTime > cooldown * 20) return 1;
            int count = Config.SERVER.DRYAD_KILLS_TO_NATURE_GUARDIAN_SPAWN.get();
            if (kills == null) {
                kills = 0;
            }
            kills++;
            if (kills >= count) {
                var spawned = AMEntities.NATURE_GUARDIAN.get().spawn(serverLevel, null, null, player, pos, MobSpawnType.TRIGGERED, false, false);
                if (spawned != null) {
                    spawned.setTarget(player);
                    return 0;
                }
            }
            return kills;
        });
        lastDryadKills.put(id, time);
    }
}
