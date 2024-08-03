package com.github.minecraftschurlimods.arsmagicalegacy.common.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AbilityIDs {
    ResourceLocation SWIM_SPEED       = ArsMagicaAPI.resource("swim_speed");
    ResourceLocation HASTE            = ArsMagicaAPI.resource("haste");
    ResourceLocation GRAVITY          = ArsMagicaAPI.resource("gravity");
    ResourceLocation SLOWNESS         = ArsMagicaAPI.resource("slowness");
    ResourceLocation SPEED            = ArsMagicaAPI.resource("speed");
    ResourceLocation STEP_ASSIST      = ArsMagicaAPI.resource("step_assist");
    ResourceLocation HEALTH_REDUCTION = ArsMagicaAPI.resource("health_reduction");
}
