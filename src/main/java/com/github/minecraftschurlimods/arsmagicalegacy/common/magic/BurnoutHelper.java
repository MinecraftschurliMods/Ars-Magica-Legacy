package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class BurnoutHelper implements IBurnoutHelper {
    private static final Lazy<BurnoutHelper> INSTANCE = Lazy.concurrentOf(BurnoutHelper::new);
    private static final Supplier<AttachmentType<Float>> BURNOUT = ATTACHMENT_TYPES.register("burnout", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).copyOnDeath().copyHandler((owner, inst) -> inst).build());

    private BurnoutHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static BurnoutHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public float getBurnout(LivingEntity entity) {
        return entity.getData(BURNOUT);
    }

    @Override
    public float getMaxBurnout(LivingEntity entity) {
        return entity.getAttributes().hasAttribute(AMAttributes.MAX_BURNOUT.value()) ? (float) entity.getAttributeValue(AMAttributes.MAX_BURNOUT.value()) : 0f;
    }

    @Override
    public boolean increaseBurnout(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxBurnout(entity);
        if (max == 0) return false;
        float current = entity.getData(BURNOUT);
        entity.setData(BURNOUT, Math.min(current + amount, max));
        syncToPlayer(entity);
        return true;
    }

    @Override
    public boolean decreaseBurnout(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxBurnout(entity);
        if (max == 0) return false;
        float current = entity.getData(BURNOUT);
        entity.setData(BURNOUT, Math.max(current - amount, 0));
        syncToPlayer(entity);
        return true;
    }

    @Override
    public boolean setBurnout(LivingEntity entity, float amount) {
        if (amount < 0) return false;
        float max = getMaxBurnout(entity);
        if (max == 0) return false;
        entity.setData(BURNOUT, Math.min(amount, max));
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
        PacketDistributor.PLAYER.with(serverPlayer).send(new BurnoutSyncPacket(serverPlayer.getData(BURNOUT)));
    }

    public static void registerSyncPacket(IPayloadRegistrar registrar) {
        registrar.play(BurnoutSyncPacket.ID, BurnoutSyncPacket::new, builder -> builder.client(BurnoutSyncPacket::handle));
    }

    private record BurnoutSyncPacket(float burnout) implements CustomPacketPayload {
        public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "burnout_sync");

        public BurnoutSyncPacket(FriendlyByteBuf buf) {
            this(buf.readFloat());
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeFloat(burnout());
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        private void handle(PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> context.player().orElseThrow().setData(BURNOUT, this.burnout()));
        }
    }
}
