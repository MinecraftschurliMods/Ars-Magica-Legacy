package com.github.minecraftschurli.arsmagicalegacy.common.effect;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class InstantManaEffect extends InstantenousMobEffect {
    public InstantManaEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00ffff);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
        ArsMagicaAPI.get().getMagicHelper().increaseMana(pLivingEntity, ArsMagicaAPI.get().getMagicHelper().getMaxMana(pLivingEntity) / 5 * pAmplifier);
    }
}
