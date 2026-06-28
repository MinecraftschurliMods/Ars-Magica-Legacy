package at.minecraftschurli.mods.arsmagicalegacy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class AMMobEffect extends MobEffect {
    public AMMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public void startEffect(LivingEntity entity, MobEffectInstance effect) {}

    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {}
}
