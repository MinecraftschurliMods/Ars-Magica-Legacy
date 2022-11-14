package com.github.minecraftschurlimods.arsmagicalegacy.mixin;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
abstract class EntityMixin {
    @ModifyVariable(method = "getBoundingBoxForPose", at = @At(value = "STORE"))
    private EntityDimensions modifyBoundingBoxForPose(final EntityDimensions value) {
        if (((Object) this) instanceof LivingEntity le) {
            return value.scale((float) le.getAttributeValue(AMAttributes.SCALE.get()));
        }
        return value;
    }
}
