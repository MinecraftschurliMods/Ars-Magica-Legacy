package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellCasterEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class DispelGoal<T extends Mob & ISpellCasterEntity> extends ExecuteSpellGoal<T> {
    public DispelGoal(T caster) {
        super(caster, caster.level().registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 0);
    }

    @Override
    public boolean canUse() {
        return (caster.getActiveEffects().size() > 0 || caster.isOnFire()) && super.canUse();
    }

    @Override
    public void stop() {
        super.stop();
        caster.clearFire();
    }
}
