package at.minecraftschurli.mods.arsmagicalegacy.effect;

import at.minecraftschurli.mods.arsmagicalegacy.attachment.TemporalAnchorAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class TemporalAnchorEffect extends AMMobEffect {
    public TemporalAnchorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2a2a2);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.setData(AMAttachments.TEMPORAL_ANCHOR, TemporalAnchorAttachment.from(entity));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        TemporalAnchorAttachment attachment = entity.removeData(AMAttachments.TEMPORAL_ANCHOR);
        if (attachment != null) {
            attachment.apply(entity);
        }
    }
}
