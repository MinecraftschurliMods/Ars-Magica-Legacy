package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.item.FireAntennaeItem;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyArg(method = "travelInLava", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"))
    private Vec3 saveTravelInLavaDeltaMovement(Vec3 deltaMovement, @Share("deltaMovement") LocalRef<Vec3> deltaMovementRef) {
        deltaMovementRef.set(deltaMovement);
        return deltaMovement;
    }

    @Inject(method = "travelInLava", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;jumpOutOfFluid(D)V"))
    private void injectTravelInLavaJumpOutOfFluid(Vec3 input, double baseGravity, boolean isFalling, double oldY, CallbackInfo ci, @Share("deltaMovement") LocalRef<Vec3> deltaMovementRef) {
        FireAntennaeItem.modifyTravelInLava((LivingEntity) (Object) this, deltaMovementRef.get(), baseGravity, isFalling, oldY);
    }
}
