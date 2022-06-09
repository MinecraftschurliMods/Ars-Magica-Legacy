package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkEvent;

public final class ManaHelper implements IManaHelper {
    private static final Lazy<ManaHelper> INSTANCE = Lazy.concurrentOf(ManaHelper::new);
    private static final Capability<ManaHolder> MANA = CapabilityManager.get(new CapabilityToken<>() {});

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
        ManaHolder magicHolder = getManaHolder(entity);
        float newMana = magicHolder.getMana() - mana;
        float clamped = Mth.clamp(newMana, 0, getMaxMana(entity));
        magicHolder.setMana(clamped);
        if (entity instanceof Player player) {
            syncMana(player);
        }
        return clamped == newMana;
    }

    @Override
    public float getMana(LivingEntity livingEntity) {
        return getManaHolder(livingEntity).getMana();
    }

    @Override
    public float getMaxMana(LivingEntity livingEntity) {
        return (float) livingEntity.getAttributeValue(AMAttributes.MAX_MANA.get());
    }

    @Override
    public boolean increaseMana(LivingEntity livingEntity, float amount) {
        if (amount < 0) return false;
        float max = getMaxMana(livingEntity);
        ManaHolder magicHolder = getManaHolder(livingEntity);
        float current = magicHolder.getMana();
        magicHolder.setMana(Math.min(current + amount, max));
        if (livingEntity instanceof Player player) {
            syncMana(player);
        }
        return true;
    }

    @Override
    public boolean decreaseMana(LivingEntity livingEntity, float amount) {
        if (amount < 0) return false;
        ManaHolder magicHolder = getManaHolder(livingEntity);
        float current = magicHolder.getMana();
        if (current - amount < 0) return false;
        magicHolder.setMana(current - amount);
        if (livingEntity instanceof Player player) {
            syncMana(player);
        }
        return true;
    }

    @Override
    public boolean setMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxMana(entity);
        ManaHolder magicHolder = getManaHolder(entity);
        magicHolder.setMana(Math.min(amount, max));
        if (entity instanceof Player player) {
            syncMana(player);
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
        player.getAttribute(AMAttributes.MAX_MANA.get()).setBaseValue(original.getAttribute(AMAttributes.MAX_MANA.get()).getBaseValue());
        original.getCapability(MANA).ifPresent(manaHolder -> player.getCapability(MANA).ifPresent(holder -> holder.onSync(manaHolder)));
        syncMana(player);
    }

    /**
     * Syncs the capability to the client.
     *
     * @param player The player to sync to.
     */
    public void syncMana(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new ManaSyncPacket(getManaHolder(player)), player);
    }

    private ManaHolder getManaHolder(LivingEntity livingEntity) {
        if (livingEntity instanceof Player && livingEntity.isDeadOrDying()) {
            livingEntity.reviveCaps();
        }
        ManaHolder manaHolder = livingEntity.getCapability(MANA).orElseThrow(() -> new RuntimeException("Could not retrieve mana capability for LivingEntity %s{%s}".formatted(livingEntity.getDisplayName().getString(), livingEntity.getUUID())));
        if (livingEntity instanceof Player && livingEntity.isDeadOrDying()) {
            livingEntity.invalidateCaps();
        }
        return manaHolder;
    }

    public static final class ManaSyncPacket extends CodecPacket<ManaHolder> {
        public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "mana_sync");

        public ManaSyncPacket(ManaHolder data) {
            super(ID, data);
        }

        public ManaSyncPacket(FriendlyByteBuf buf) {
            super(ID, buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            handleManaSync(data, context);
        }

        @Override
        protected Codec<ManaHolder> codec() {
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
