package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Consumer;

public final class ManaHelper implements IManaHelper {
    private static final Lazy<ManaHelper> INSTANCE = Lazy.concurrentOf(ManaHelper::new);
    private static final Capability<ManaHolder> MANA = CapabilityManager.get(new CapabilityToken<>() {});
    private static final ManaHolder EMPTY = new ManaHolder();

    private ManaHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static ManaHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The mana capability.
     */
    public static Capability<ManaHolder> getManaCapability() {
        return MANA;
    }

    private static void handleManaSync(ManaHolder holder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(MANA).ifPresent(cap -> cap.onSync(holder)));
    }

    @Override
    public boolean decreaseMana(LivingEntity entity, float mana, boolean force) {
        if (!force) return decreaseMana(entity, mana);
        LazyOptional<ManaHolder> holder = getManaHolder(entity);
        if (!holder.isPresent()) return false;
        float newMana = holder.orElse(EMPTY).getMana() - mana;
        float clamped = Mth.clamp(newMana, 0, getMaxMana(entity));
        holder.orElse(EMPTY).setMana(clamped);
        if (entity instanceof Player player) {
            syncMana(player);
        }
        return clamped == newMana;
    }

    @Override
    public float getMana(LivingEntity entity) {
        return getManaHolder(entity).orElse(EMPTY).getMana();
    }

    @Override
    public float getMaxMana(LivingEntity entity) {
        return entity.getAttributes().hasAttribute(AMAttributes.MAX_MANA.get()) ? (float) entity.getAttributeValue(AMAttributes.MAX_MANA.get()) : 0f;
    }

    @Override
    public boolean increaseMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        runIfPresent(entity, holder -> {
            float max = getMaxMana(entity);
            float current = holder.getMana();
            holder.setMana(Math.min(current + amount, max));
            if (entity instanceof Player player) {
                syncMana(player);
            }
        });
        return true;
    }

    @Override
    public boolean decreaseMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        runIfPresent(entity, holder -> {
            float current = holder.getMana();
            holder.setMana(Math.max(current - amount, 0));
            if (entity instanceof Player player) {
                syncMana(player);
            }
        });
        return true;
    }

    @Override
    public boolean setMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        runIfPresent(entity, holder -> {
            float max = getMaxMana(entity);
            holder.setMana(Math.min(amount, max));
            if (entity instanceof Player player) {
                syncMana(player);
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
        player.getAttribute(AMAttributes.MAX_MANA.get()).setBaseValue(original.getAttribute(AMAttributes.MAX_MANA.get()).getBaseValue());
        player.getAttribute(AMAttributes.MANA_REGEN.get()).setBaseValue(original.getAttribute(AMAttributes.MANA_REGEN.get()).getBaseValue());
        original.getCapability(MANA).ifPresent(manaHolder -> player.getCapability(MANA).ifPresent(holder -> holder.onSync(manaHolder)));
        syncMana(player);
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncMana(Player player) {
        runIfPresent(player, holder -> ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new ManaSyncPacket(holder), player));
    }

    private void runIfPresent(LivingEntity entity, Consumer<ManaHolder> consumer) {
        getManaHolder(entity).ifPresent(consumer::accept);
    }

    private LazyOptional<ManaHolder> getManaHolder(LivingEntity entity) {
        if (entity instanceof Player && entity.isDeadOrDying()) {
            entity.reviveCaps();
        }
        LazyOptional<ManaHolder> manaHolder = entity.getCapability(MANA);
        if (entity instanceof Player && entity.isDeadOrDying()) {
            entity.invalidateCaps();
        }
        return manaHolder;
    }

    public static final class ManaSyncPacket extends CodecPacket<ManaHolder> {
        public ManaSyncPacket(ManaHolder data) {
            super(data);
        }

        public ManaSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            handleManaSync(data, context);
        }

        @Override
        protected Codec<ManaHolder> getCodec() {
            return ManaHolder.CODEC;
        }
    }

    public static final class ManaHolder {
        public static final Codec<ManaHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.FLOAT.fieldOf("mana").forGetter(ManaHolder::getMana)).apply(inst, mana -> {
            ManaHolder manaHolder = new ManaHolder();
            manaHolder.setMana(mana);
            return manaHolder;
        }));
        private float mana;

        public float getMana() {
            return mana;
        }

        public void setMana(float amount) {
            mana = amount;
        }

        /**
         * Syncs the values with the given data object.
         *
         * @param data The data object to sync with.
         */
        public void onSync(ManaHolder data) {
            mana = data.mana;
        }
    }
}
