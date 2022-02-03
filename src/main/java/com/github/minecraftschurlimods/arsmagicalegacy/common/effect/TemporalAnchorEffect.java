package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class TemporalAnchorEffect extends AMMobEffect {
    public TemporalAnchorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2a2a2);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        var api = ArsMagicaAPI.get();
        CompoundTag tag = new CompoundTag();
        tag.putDouble("X", entity.getX());
        tag.putDouble("Y", entity.getY());
        tag.putDouble("Z", entity.getZ());
        tag.putFloat("RotationPitch", entity.getXRot());
        tag.putFloat("RotationYaw", entity.getYRot());
        tag.putFloat("RotationYawHead", entity.getYHeadRot());
        tag.putFloat("Mana", api.getManaHelper().getMana(entity));
        tag.putFloat("Burnout", api.getBurnoutHelper().getBurnout(entity));
        tag.putFloat("Health", entity.getHealth());
        entity.getPersistentData().put(ArsMagicaAPI.MOD_ID, new CompoundTag());
        entity.getPersistentData().getCompound(ArsMagicaAPI.MOD_ID).put("TemporalAnchor", tag);
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        var api = ArsMagicaAPI.get();
        CompoundTag tag = entity.getPersistentData().getCompound(ArsMagicaAPI.MOD_ID).getCompound("TemporalAnchor");
        entity.setPos(tag.getDouble("X"), tag.getDouble("Y"), tag.getDouble("Z"));
        entity.setXRot(tag.getFloat("RotationPitch"));
        entity.setYRot(tag.getFloat("RotationYaw"));
        entity.setYHeadRot(tag.getFloat("RotationYawHead"));
        api.getManaHelper().setMana(entity, tag.getFloat("Mana"));
        api.getBurnoutHelper().setBurnout(entity, tag.getFloat("Burnout"));
        entity.setHealth(tag.getFloat("Health"));
    }
}
