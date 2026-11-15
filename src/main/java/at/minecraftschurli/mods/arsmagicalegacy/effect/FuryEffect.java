package at.minecraftschurli.mods.arsmagicalegacy.effect;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FuryEffect extends AMMobEffect {
    public FuryEffect() {
        super(MobEffectCategory.HARMFUL, 0xff8033);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.addEffect(new MobEffectInstance(MobEffects.STRENGTH, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.HASTE, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, effect.getDuration(), effect.getAmplifier()));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        MinecraftServer server = entity.level().getServer();
        if (server == null) return;
        int amplifier = effect.getAmplifier();
        // stopEffect runs from MobEffectEvent.Remove, which can fire while LivingEntity#removeAllEffects is iterating
        // the active effects map (Dispel, milk, /effect clear). Adding effects there throws a
        // ConcurrentModificationException, so the after-effects are applied in a task after the current tick.
        server.schedule(new TickTask(server.getTickCount(), () -> {
            if (entity.isRemoved()) return;
            entity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, amplifier));
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, amplifier));
        }));
    }
}
