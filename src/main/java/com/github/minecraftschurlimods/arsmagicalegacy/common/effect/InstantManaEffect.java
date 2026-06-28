package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

public class InstantManaEffect extends InstantenousMobEffect {
    public InstantManaEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00ffff);
    }

    @Override
    public void applyInstantenousEffect(ServerLevel level, @Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        if (livingEntity.getAttributes().hasAttribute(AMAttributes.MAX_MANA)) {
            manaHelper.increaseMana(livingEntity, manaHelper.getMaxMana(livingEntity) / 5 * amplifier);
        }
    }
}
