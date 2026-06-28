package com.github.minecraftschurlimods.arsmagicalegacy.common.apiimpl;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("DataFlowIssue")
final class BurnoutHelperImpl implements BurnoutHelper {
    @Override
    public double getBurnoutBase() {
        return AMServerConfig.BURNOUT_BASE.get();
    }

    @Override
    public double getBurnoutMultiplier() {
        return AMServerConfig.BURNOUT_MULTIPLIER.get();
    }

    @Override
    public double getBurnoutRegenerationMultiplier() {
        return AMServerConfig.BURNOUT_REGENERATION.get();
    }

    @Override
    public double getBurnout(LivingEntity entity) {
        return entity.isDeadOrDying() ? 0 : entity.getData(AMAttachments.BURNOUT);
    }

    @Override
    public double getMaxBurnout(LivingEntity entity) {
        return entity.isDeadOrDying() || !entity.getAttributes().hasAttribute(AMAttributes.MAX_BURNOUT) ? 0 : entity.getAttributeValue(AMAttributes.MAX_BURNOUT);
    }

    @Override
    public double getBurnoutRegeneration(LivingEntity entity) {
        return entity.isDeadOrDying() || !entity.getAttributes().hasAttribute(AMAttributes.BURNOUT_REGENERATION) ? 0 : entity.getAttributeValue(AMAttributes.BURNOUT_REGENERATION);
    }

    @Override
    public boolean setBurnout(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxBurnout(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.BURNOUT, Math.min(amount, max));
        return true;
    }

    @Override
    public boolean increaseBurnout(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxBurnout(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.BURNOUT, Math.min(entity.getData(AMAttachments.BURNOUT) + amount, max));
        return true;
    }

    @Override
    public boolean decreaseBurnout(LivingEntity entity, double amount) {
        if (amount < 0) return false;
        double max = getMaxBurnout(entity);
        if (max <= 0) return false;
        entity.setData(AMAttachments.BURNOUT, Math.max(entity.getData(AMAttachments.BURNOUT) - amount, 0));
        return true;
    }

    @Override
    public boolean setMaxBurnout(LivingEntity entity, double amount) {
        if (!entity.getAttributes().hasAttribute(AMAttributes.MAX_BURNOUT)) return false;
        entity.getAttribute(AMAttributes.MAX_BURNOUT).setBaseValue(amount);
        return true;
    }

    @Override
    public boolean setBurnoutRegeneration(LivingEntity entity, double amount) {
        if (!entity.getAttributes().hasAttribute(AMAttributes.BURNOUT_REGENERATION)) return false;
        entity.getAttribute(AMAttributes.BURNOUT_REGENERATION).setBaseValue(amount);
        return true;
    }
}
