package com.github.minecraftschurli.arsmagicalegacy.common.effect;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
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
        CompoundTag tag = new CompoundTag();
        tag.putDouble("X", entity.getX());
        tag.putDouble("Y", entity.getY());
        tag.putDouble("Z", entity.getZ());
        tag.putFloat("RotationPitch", entity.getXRot());
        tag.putFloat("RotationYaw", entity.getYRot());
        tag.putFloat("RotationYawHead", entity.getYHeadRot());
        tag.putFloat("Mana", ArsMagicaAPI.get().getMagicHelper().getMana(entity));
        tag.putFloat("Burnout", ArsMagicaAPI.get().getMagicHelper().getBurnout(entity));
        tag.putFloat("Health", entity.getHealth());
        entity.getPersistentData().put("AMTemporalAnchor", tag);
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        CompoundTag tag = entity.getPersistentData().getCompound("AMTemporalAnchor");
        entity.setPos(tag.getDouble("X"), tag.getDouble("Y"), tag.getDouble("Z"));
        entity.setXRot(tag.getFloat("RotationPitch"));
        entity.setYRot(tag.getFloat("RotationYaw"));
        entity.setYHeadRot(tag.getFloat("RotationYawHead"));
        ArsMagicaAPI.get().getMagicHelper().setMana(entity, tag.getFloat("Mana"));
        ArsMagicaAPI.get().getMagicHelper().setBurnout(entity, tag.getFloat("Burnout"));
        entity.setHealth(tag.getFloat("Health"));
    }
}
