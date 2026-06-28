package at.minecraftschurli.mods.arsmagicalegacy.util;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BossBar {
    public static final Codec<BossBar> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        UUIDUtil.CODEC.fieldOf("uuid").forGetter(e -> e.event.getId()),
        UUIDUtil.CODEC.fieldOf("entity").forGetter(e -> e.entityUuid),
        BossEvent.BossBarColor.CODEC.fieldOf("color").forGetter(e -> e.event.getColor()),
        Codec.FLOAT.fieldOf("progress").forGetter(e -> e.event.getProgress())
    ).apply(inst, BossBar::new));
    private final ServerBossEvent event;
    private final UUID entityUuid;
    @Nullable
    private LivingEntity entity;

    public BossBar(ServerLevel level, LivingEntity entity, BossEvent.BossBarColor color) {
        this.entity = entity;
        this.entityUuid = entity.getUUID();
        event = new ServerBossEvent(Mth.createInsecureUUID(level.getRandom()), entity.getName(), color, BossEvent.BossBarOverlay.PROGRESS);
        getList(level).add(this);
    }

    private BossBar(UUID uuid, UUID entityUuid, BossEvent.BossBarColor color, float progress) {
        this.entityUuid = entityUuid;
        event = new ServerBossEvent(uuid, Component.empty(), color, BossEvent.BossBarOverlay.PROGRESS);
        event.setProgress(progress);
    }

    public static BossBarList getList(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(BossBarList.TYPE);
    }

    public void tick(ServerLevel level) {
        if (entity == null) {
            if (level.getEntity(entityUuid) instanceof LivingEntity living) {
                entity = living;
            } else {
                remove();
                return;
            }
        }
        if (entity.isRemoved()) {
            remove();
            return;
        }
        event.setProgress(entity.getHealth() / entity.getMaxHealth());
        if (level.getGameTime() % AMServerConfig.BOSS_PLAYER_CHECK_INTERVAL.getAsInt() == 0) {
            event.setName(entity.getName());
            for (ServerPlayer player : level.getPlayers(p -> !p.isSpectator())) {
                if (player.distanceTo(entity) > AMServerConfig.BOSS_PLAYER_CHECK_DISTANCE.getAsDouble()) {
                    event.removePlayer(player);
                } else {
                    event.addPlayer(player);
                }
            }
        }
    }

    private void remove() {
        event.removeAllPlayers();
        event.setVisible(false);
    }

    public static void tickAll(ServerLevel level) {
        BossBarList list = getList(level);
        List<BossBar> bossBars = list.getAll();
        for (BossBar bossBar : bossBars) {
            bossBar.tick(level);
        }
        List<BossBar> toRemove = bossBars.stream().filter(e -> !e.event.isVisible()).toList();
        for (BossBar bossBar : toRemove) {
            list.remove(bossBar);
        }
    }

    public static class BossBarList extends SavedData {
        public static final Codec<BossBarList> CODEC = BossBar.CODEC.listOf().xmap(BossBarList::new, e -> e.list);
        public static final SavedDataType<BossBarList> TYPE = new SavedDataType<>(ArsMagicaApi.id("boss_bars"), _ -> new BossBarList(List.of()), _ -> CODEC);
        private final List<BossBar> list;
        private final Map<UUID, BossBar> map;

        public BossBarList(List<BossBar> list) {
            this.list = new ArrayList<>(list);
            this.map = new HashMap<>();
            for (BossBar bossBar : list) {
                if (!map.containsKey(bossBar.entityUuid)) {
                    map.put(bossBar.entityUuid, bossBar);
                }
            }
        }

        public List<BossBar> getAll() {
            return list;
        }

        public void add(BossBar bossBar) {
            if (!map.containsKey(bossBar.entityUuid)) {
                list.add(bossBar);
                map.put(bossBar.entityUuid, bossBar);
                setDirty();
            }
        }

        public void remove(BossBar bossBar) {
            if (map.containsKey(bossBar.entityUuid)) {
                list.remove(bossBar);
                map.remove(bossBar.entityUuid);
                setDirty();
            }
        }
    }
}
