package at.minecraftschurli.mods.arsmagicalegacy.attachment;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.entity.NatureGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record DryadKillsAttachment(List<Integer> timers) {
    public static final Codec<DryadKillsAttachment> CODEC = Codec.INT.listOf().xmap(DryadKillsAttachment::new, DryadKillsAttachment::timers);
    public static final DryadKillsAttachment EMPTY = new DryadKillsAttachment(List.of());

    public static void tick(Player player) {
        if (AMServerConfig.DRYAD_KILL_COOLDOWN.get() == 0 || !(player.level() instanceof ServerLevel) || !player.hasData(AMAttachments.DRYAD_KILLS)) return;
        List<Integer> list = new ArrayList<>(player.getData(AMAttachments.DRYAD_KILLS).timers);
        list.replaceAll(i -> i - 1);
        list.removeIf(i -> i <= 0);
        if (list.isEmpty()) {
            player.removeData(AMAttachments.DRYAD_KILLS);
        } else {
            player.setData(AMAttachments.DRYAD_KILLS, new DryadKillsAttachment(list));
        }
    }

    public static void kill(Player player, LivingEntity dryad) {
        int cooldown = AMServerConfig.DRYAD_KILL_COOLDOWN.get();
        if (cooldown == 0 || !(player.level() instanceof ServerLevel level)) return;
        List<Integer> list = new ArrayList<>(player.getData(AMAttachments.DRYAD_KILLS).timers);
        list.add(cooldown);
        if (list.size() >= AMServerConfig.DRYAD_KILLS_FOR_NATURE_GUARDIAN_SPAWN.get()) {
            NatureGuardian natureGuardian = AMEntities.NATURE_GUARDIAN.get().spawn(level, ItemStack.EMPTY, player, dryad.blockPosition(), EntitySpawnReason.TRIGGERED, false, false);
            if (natureGuardian != null) {
                natureGuardian.setTarget(player);
                player.removeData(AMAttachments.DRYAD_KILLS);
                return;
            }
        }
        player.setData(AMAttachments.DRYAD_KILLS, new DryadKillsAttachment(list));
    }
}
