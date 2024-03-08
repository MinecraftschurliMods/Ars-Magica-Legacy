package com.github.minecraftschurlimods.arsmagicalegacy.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
abstract class EntityMixin {
    /*@ModifyVariable(method = "getBoundingBoxForPose", at = @At(value = "STORE"))
    private EntityDimensions modifyBoundingBoxForPose(final EntityDimensions value) {
        if (((Object) this) instanceof LivingEntity le) {
            return value.scale((float) le.getAttributeValue(AMAttributes.SCALE.get()));
        }
        return value;
    }*/
}
