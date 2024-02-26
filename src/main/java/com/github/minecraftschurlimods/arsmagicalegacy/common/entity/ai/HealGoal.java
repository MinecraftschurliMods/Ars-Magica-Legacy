package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class HealGoal<T extends AbstractBoss> extends ExecuteBossSpellGoal<T> {
    public HealGoal(T caster) {
        super(caster, caster.level().registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "heal_self")).spell(), 20);
    }

    @Override
    public boolean canUse() {
        return caster.getHealth() != caster.getMaxHealth() && super.canUse();
    }

    @Override
    @Nullable
    protected SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_HEAL.value();
    }
}
