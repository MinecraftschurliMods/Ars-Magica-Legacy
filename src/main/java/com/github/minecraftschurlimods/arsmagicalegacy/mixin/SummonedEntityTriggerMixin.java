package com.github.minecraftschurlimods.arsmagicalegacy.mixin;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Context;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.EntitySummonTrigger;
import net.minecraft.advancements.critereon.SummonedEntityTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(SummonedEntityTrigger.class)
public class SummonedEntityTriggerMixin {
    private static final Map<Integer, Long> PROCESSED_ENTITY_IDS = new HashMap<>();

    @Inject(method = "trigger", at = @At("HEAD"))
    private void trigger(ServerPlayer player, Entity entity, CallbackInfo ci) {
        if (PROCESSED_ENTITY_IDS.containsKey(entity.getId())) {
            if (PROCESSED_ENTITY_IDS.get(entity.getId()) > entity.level.getGameTime()) return;
            PROCESSED_ENTITY_IDS.remove(entity.getId());
        }
        for (Ritual ritual : player.level.registryAccess().registryOrThrow(Ritual.REGISTRY_KEY)) {
            if (!(ritual.trigger() instanceof EntitySummonTrigger trigger)) continue;
            if (!trigger.predicate().matches(player, entity)) continue;
            if (ritual.perform(player, player.getLevel(), entity.blockPosition(), new Context.MapContext(Map.of("entity", entity)))) {
                PROCESSED_ENTITY_IDS.put(entity.getId(), entity.level.getGameTime() + 200);
                return;
            }
        }
    }
}
