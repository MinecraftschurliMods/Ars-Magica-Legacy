package com.github.minecraftschurli.arsmagicalegacy.mixin;

import com.github.minecraftschurli.arsmagicalegacy.common.util.AffinityUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public abstract class MixinEnderMan extends Monster {
    protected MixinEnderMan(EntityType<? extends EnderMan> type, Level level) {
        super(type, level);
    }

    @Inject(at = @At("HEAD"), method = "isLookingAtMe", cancellable = true)
    public void doesEndermanGetAngryAt(Player pPlayer, CallbackInfoReturnable<Boolean> cir) {
        if (!AffinityUtil.doesEndermanGetAngryAt(pPlayer)) {
            cir.setReturnValue(false);
        }
    }
}
