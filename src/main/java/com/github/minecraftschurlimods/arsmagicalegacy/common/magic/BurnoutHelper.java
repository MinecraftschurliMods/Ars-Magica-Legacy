package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;

public final class BurnoutHelper implements IBurnoutHelper {
    private static final Lazy<BurnoutHelper> INSTANCE = Lazy.concurrentOf(BurnoutHelper::new);
    private static final Capability<BurnoutHolder> BURNOUT = CapabilityManager.get(new CapabilityToken<>() {});
    private static final BurnoutHolder EMPTY = new BurnoutHolder();

    private BurnoutHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static BurnoutHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The burnout capability.
     */
    public static Capability<BurnoutHolder> getBurnoutCapability() {
        return BURNOUT;
    }

    private static void handleBurnoutSync(BurnoutHolder holder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(BURNOUT).ifPresent(cap -> cap.onSync(holder)));
    }

    @Override
    public float getBurnout(LivingEntity entity) {
        return getBurnoutHolder(entity).orElse(EMPTY).getBurnout();
    }

    @Override
    public float getMaxBurnout(LivingEntity entity) {
        return entity.getAttributes().hasAttribute(AMAttributes.MAX_BURNOUT.get()) ? (float) entity.getAttributeValue(AMAttributes.MAX_BURNOUT.get()) : 0f;
    }

    @Override
    public boolean increaseBurnout(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        runIfPresent(entity, holder -> {
            float max = getMaxBurnout(entity);
            float current = holder.getBurnout();
            holder.setBurnout(Math.min(current + amount, max));
            if (entity instanceof Player player) {
                syncBurnout(player);
            }
        });
        return true;
    }

    @Override
    public boolean decreaseBurnout(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        runIfPresent(entity, holder -> {
            float current = holder.getBurnout();
            holder.setBurnout(Math.max(current - amount, 0));
            if (entity instanceof Player player) {
                syncBurnout(player);
            }
        });
        return true;
    }

    @Override
    public boolean setBurnout(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        runIfPresent(entity, holder -> {
            float max = getMaxBurnout(entity);
            holder.setBurnout(Math.min(amount, max));
            if (entity instanceof Player player) {
                syncBurnout(player);
            }
        });
        return true;
    }

    /**
     * Called on player death, syncs the capability.
     *
     * @param original The now-dead player.
     * @param player   The respawning player.
     */
    public void syncOnDeath(Player original, Player player) {
        player.getAttribute(AMAttributes.MAX_BURNOUT.get()).setBaseValue(original.getAttribute(AMAttributes.MAX_BURNOUT.get()).getBaseValue());
        player.getAttribute(AMAttributes.BURNOUT_REGEN.get()).setBaseValue(original.getAttribute(AMAttributes.BURNOUT_REGEN.get()).getBaseValue());
        original.getCapability(BURNOUT).ifPresent(burnoutHolder -> player.getCapability(BURNOUT).ifPresent(holder -> holder.onSync(burnoutHolder)));
        syncBurnout(player);
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncBurnout(Player player) {
        runIfPresent(player, holder -> ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new BurnoutSyncPacket(holder), player));
    }

    private void runIfPresent(LivingEntity entity, Consumer<BurnoutHolder> consumer) {
        getBurnoutHolder(entity).ifPresent(consumer::accept);
    }

    private LazyOptional<BurnoutHolder> getBurnoutHolder(LivingEntity entity) {
        if (entity instanceof Player && entity.isDeadOrDying()) {
            entity.reviveCaps();
        }
        LazyOptional<BurnoutHolder> burnoutHolder = entity.getCapability(BURNOUT);
        if (entity instanceof Player && entity.isDeadOrDying()) {
            entity.invalidateCaps();
        }
        return burnoutHolder;
    }

    public static final class BurnoutSyncPacket extends CodecPacket<BurnoutHolder> {
        public BurnoutSyncPacket(BurnoutHolder data) {
            super(data);
        }

        public BurnoutSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            handleBurnoutSync(data, context);
        }

        @Override
        protected Codec<BurnoutHolder> getCodec() {
            return BurnoutHolder.CODEC;
        }
    }

    public static final class BurnoutHolder {
        public static final Codec<BurnoutHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.FLOAT.fieldOf("burnout").forGetter(BurnoutHolder::getBurnout)).apply(inst, burnout -> {
            BurnoutHolder burnoutHolder = new BurnoutHolder();
            burnoutHolder.setBurnout(burnout);
            return burnoutHolder;
        }));
        private float burnout;

        public float getBurnout() {
            return burnout;
        }

        public void setBurnout(float amount) {
            burnout = amount;
        }

        /**
         * Syncs the values with the given data object.
         *
         * @param data The data object to sync with.
         */
        public void onSync(BurnoutHolder data) {
            burnout = data.burnout;
        }
    }
}
