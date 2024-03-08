package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class ManaHelper implements IManaHelper {
    private static final Lazy<ManaHelper> INSTANCE = Lazy.concurrentOf(ManaHelper::new);
    private static final Supplier<AttachmentType<Float>> MANA = ATTACHMENT_TYPES.register("mana", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).copyOnDeath().copyHandler((owner, inst) -> inst).build());

    private ManaHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static ManaHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public boolean decreaseMana(LivingEntity entity, float mana, boolean force) {
        if (!force) return decreaseMana(entity, mana);
        float max = getMaxMana(entity);
        if (max == 0) return false;
        float holder = entity.getData(MANA);
        float newMana = holder - mana;
        float clamped = Mth.clamp(newMana, 0, max);
        entity.setData(MANA, clamped);
        syncToPlayer(entity);
        return clamped == newMana;
    }

    @Override
    public float getMana(LivingEntity entity) {
        return entity.getData(MANA);
    }

    @Override
    public float getMaxMana(LivingEntity entity) {
        return entity.getAttributes().hasAttribute(AMAttributes.MAX_MANA.value()) ? (float) entity.getAttributeValue(AMAttributes.MAX_MANA.value()) : 0f;
    }

    @Override
    public boolean increaseMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxMana(entity);
        if (max == 0) return false;
        entity.setData(MANA, Math.min(entity.getData(MANA) + amount, max));
        syncToPlayer(entity);
        return true;
    }

    @Override
    public boolean decreaseMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxMana(entity);
        if (max == 0) return false;
        float current = entity.getData(MANA);
        entity.setData(MANA, Math.max(current - amount, 0));
        syncToPlayer(entity);
        return true;
    }

    @Override
    public boolean setMana(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxMana(entity);
        if (max == 0) return false;
        entity.setData(MANA, Math.min(amount, max));
        syncToPlayer(entity);
        return true;
    }

    /**
     * Syncs the attachment to the client.
     *
     * @param entity The player to sync to.
     */
    public void syncToPlayer(LivingEntity entity) {
        if (!(entity instanceof ServerPlayer serverPlayer)) return;
        PacketDistributor.PLAYER.with(serverPlayer).send(new ManaSyncPacket(entity.getData(MANA)));
    }

    public static void registerSyncPacket(IPayloadRegistrar registrar) {
        registrar.play(ManaSyncPacket.ID, ManaSyncPacket::new, builder -> builder.client(ManaSyncPacket::handle));
    }

    private record ManaSyncPacket(float mana) implements CustomPacketPayload {
        public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "mana_sync");

        public ManaSyncPacket(FriendlyByteBuf buf) {
            this(buf.readFloat());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeFloat(this.mana());
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        public void handle(PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> context.player().orElseThrow().setData(MANA, this.mana()));
        }
    }
}
