package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AirSled;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    @ModifyReturnValue(method = "wantsToStopRiding", at = @At("RETURN"))
    private boolean modifyWantsToStopRiding(boolean original) {
        if (!original) return false;
        Player self = (Player) (Object) this;
        return !self.isPassenger() || !(self.getVehicle() instanceof AirSled airSled) || airSled.onGround();
    }
}
