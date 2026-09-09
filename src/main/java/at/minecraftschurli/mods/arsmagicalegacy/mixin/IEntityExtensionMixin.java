package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.item.FireAntennaeItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.WaterOrbsItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.extensions.IEntityExtension;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IEntityExtension.class)
public interface IEntityExtensionMixin {
    @ModifyReturnValue(method = "getFluidMotionScale", at = @At("RETURN"))
    default double modifyGetFluidMotionScale(double original, FluidType type) {
        if (!(((Entity) this) instanceof LivingEntity living)) return original;
        if (type == NeoForgeMod.WATER_TYPE.value() && WaterOrbsItem.isEquipped(living)) return 0;
        if (type == NeoForgeMod.LAVA_TYPE.value() && FireAntennaeItem.isEquipped(living)) return 0;
        return original;
    }

    @ModifyReturnValue(method = "canSwimInFluidType", at = @At("RETURN"))
    default boolean modifyCanSwimInFluidType(boolean original) {
        return original || ((Entity) this) instanceof LivingEntity living && living.isInLava() && FireAntennaeItem.isEquipped(living);
    }
}
