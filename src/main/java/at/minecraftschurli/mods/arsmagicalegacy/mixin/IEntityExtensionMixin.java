package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.item.FireAntennaeItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.extensions.IEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IEntityExtension.class)
public interface IEntityExtensionMixin {
    @ModifyReturnValue(method = "canSwimInFluidType", at = @At("RETURN"))
    default boolean modifyCanSwimInFluidType(boolean original) {
        return original || ((Entity) this) instanceof LivingEntity living && living.isInLava() && FireAntennaeItem.isEquipped(living);
    }
}
