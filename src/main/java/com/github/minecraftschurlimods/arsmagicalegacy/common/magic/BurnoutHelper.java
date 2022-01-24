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
import net.minecraftforge.network.NetworkEvent;

public final class BurnoutHelper implements IBurnoutHelper {
    private static final Lazy<BurnoutHelper> INSTANCE = Lazy.concurrentOf(BurnoutHelper::new);
    private static final Capability<BurnoutHolder> BURNOUT = CapabilityManager.get(new CapabilityToken<>() {
    });

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

    /**
     * Handles synchronization with the client.
     *
     * @param holder  The capability to sync.
     * @param context The networking context.
     */
    private static void handleBurnoutSync(BurnoutHolder holder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(BURNOUT).ifPresent(cap -> cap.onSync(holder)));
    }

    @Override
    public float getBurnout(LivingEntity livingEntity) {
        return getBurnoutHolder(livingEntity).getBurnout();
    }

    @Override
    public float getMaxBurnout(LivingEntity livingEntity) {
        return (float) livingEntity.getAttributeValue(AMAttributes.MAX_BURNOUT.get());
    }

    @Override
    public boolean increaseBurnout(LivingEntity livingEntity, float amount) {
        if (amount < 0) return false;
        float max = getMaxBurnout(livingEntity);
        BurnoutHelper.BurnoutHolder magicHolder = getBurnoutHolder(livingEntity);
        float current = magicHolder.getBurnout();
        magicHolder.setBurnout(Math.min(current + amount, max));
        if (livingEntity instanceof Player player) {
            syncBurnout(player);
        }
        return true;
    }

    @Override
    public boolean decreaseBurnout(LivingEntity livingEntity, float amount) {
        if (amount < 0) return false;
        BurnoutHelper.BurnoutHolder magicHolder = getBurnoutHolder(livingEntity);
        float current = magicHolder.getBurnout();
        if (current - amount < 0) {
            amount = current;
        }
        magicHolder.setBurnout(current - amount);
        if (livingEntity instanceof Player player) {
            syncBurnout(player);
        }
        return true;
    }

    @Override
    public boolean setBurnout(LivingEntity entity, float amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must not be negative!");
        float max = getMaxBurnout(entity);
        BurnoutHelper.BurnoutHolder magicHolder = getBurnoutHolder(entity);
        magicHolder.setBurnout(Math.min(amount, max));
        if (entity instanceof Player player) {
            syncBurnout(player);
        }
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
        original.getCapability(BurnoutHelper.BURNOUT).ifPresent(burnoutHolder -> player.getCapability(BurnoutHelper.BURNOUT).ifPresent(holder -> holder.onSync(burnoutHolder)));
        syncBurnout(player);
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncBurnout(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new BurnoutHelper.BurnoutSyncPacket(getBurnoutHolder(player)), player);
    }

    private BurnoutHelper.BurnoutHolder getBurnoutHolder(LivingEntity livingEntity) {
        if (livingEntity instanceof Player && livingEntity.isDeadOrDying()) {
            livingEntity.reviveCaps();
        }
        BurnoutHelper.BurnoutHolder burnoutHolder = livingEntity.getCapability(BurnoutHelper.BURNOUT).orElseThrow(() -> new RuntimeException("Could not retrieve burnout capability for LivingEntity %s{%s}".formatted(livingEntity.getDisplayName().getString(), livingEntity.getUUID())));
        if (livingEntity instanceof Player && livingEntity.isDeadOrDying()) {
            livingEntity.invalidateCaps();
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
        public static final Codec<BurnoutHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.FLOAT.fieldOf("burnout").forGetter(
                BurnoutHolder::getBurnout)).apply(inst, burnout -> {
            BurnoutHolder manaHolder = new BurnoutHolder();
            manaHolder.setBurnout(burnout);
            return manaHolder;
        }));
        private float burnout;

        public float getBurnout() {
            return this.burnout;
        }

        public void setBurnout(float amount) {
            this.burnout = amount;
        }

        public void onSync(BurnoutHolder data) {
            this.burnout = data.burnout;
        }
    }
}
