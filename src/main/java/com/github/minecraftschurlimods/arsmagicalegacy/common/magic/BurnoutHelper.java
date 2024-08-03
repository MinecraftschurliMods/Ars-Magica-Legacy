package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class BurnoutHelper implements IBurnoutHelper {
    private static final Lazy<BurnoutHelper> INSTANCE = Lazy.of(BurnoutHelper::new);
    private static final Supplier<AttachmentType<Float>> BURNOUT = ATTACHMENT_TYPES.register("burnout", () -> AttachmentType.builder(() -> 0f).serialize(Codec.FLOAT).copyOnDeath().copyHandler((inst, owner, provider) -> inst).build());

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
        return entity.getAttributes().hasAttribute(AMAttributes.MAX_BURNOUT) ? (float) entity.getAttributeValue(AMAttributes.MAX_BURNOUT) : 0f;
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
        serverPlayer.connection.send(new BurnoutSyncPacket(serverPlayer.getData(BURNOUT)));
    }

    public static void registerSyncPacket(PayloadRegistrar registrar) {
        registrar.playToClient(BurnoutSyncPacket.TYPE, BurnoutSyncPacket.STREAM_CODEC, BurnoutSyncPacket::handle);
    }

    private record BurnoutSyncPacket(float burnout) implements CustomPacketPayload {
        public static final Type<BurnoutSyncPacket> TYPE = new Type<>(ArsMagicaAPI.resource("burnout_sync"));
        public static final StreamCodec<ByteBuf, BurnoutSyncPacket> STREAM_CODEC = ByteBufCodecs.FLOAT.map(BurnoutSyncPacket::new, BurnoutSyncPacket::burnout);

        private void handle(IPayloadContext context) {
            context.player().setData(BURNOUT, this.burnout());
        }

        @Override
        public Type<BurnoutSyncPacket> type() {
            return TYPE;
        }
    }
}
