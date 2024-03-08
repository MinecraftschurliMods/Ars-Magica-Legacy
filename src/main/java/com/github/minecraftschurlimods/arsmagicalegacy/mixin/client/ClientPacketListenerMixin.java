package com.github.minecraftschurlimods.arsmagicalegacy.mixin.client;

import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin {
    @Inject(method = "handleUpdateAttributes", at = @At(value = "RETURN"))
    private void afterHandleUpdateAttributes(ClientboundUpdateAttributesPacket packet, CallbackInfo ci) {
        if (packet.getValues().stream().noneMatch(a -> a.getAttribute() == AMAttributes.SCALE.value())) return;
        Level localLevel = ClientHelper.getLocalLevel();
        if (localLevel == null) return;
        Entity entity = localLevel.getEntity(packet.getEntityId());
        if (entity == null) return;
        entity.refreshDimensions();
    }
}
