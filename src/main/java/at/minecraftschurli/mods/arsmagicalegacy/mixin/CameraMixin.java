package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.item.EnderBootsItem;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public class CameraMixin {
    @ModifyArgs(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(DDD)D", ordinal = 1))
    private void modifyYLerp(Args args) {
        LocalPlayer player = AMClientUtil.player();
        if (player == null || !EnderBootsItem.isActive(player)) return;
        double oldY = args.get(1);
        double y = args.get(2);
        float bbHeight = player.getBbHeight();
        double newOldY = oldY + bbHeight;
        double newY = y + bbHeight;
        args.set(1, newOldY);
        args.set(2, newY);
    }
}
