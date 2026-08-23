package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.item.FireAntennaeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "travelInLava", at = @At("HEAD"), cancellable = true)
    private void injectTravelInLava(Vec3 input, double baseGravity, boolean isFalling, double oldY, CallbackInfo ci) {
        if (FireAntennaeItem.travelInLava((LivingEntity) (Object) this, input, baseGravity, isFalling, oldY)) {
            ci.cancel();
        }
    }
}
