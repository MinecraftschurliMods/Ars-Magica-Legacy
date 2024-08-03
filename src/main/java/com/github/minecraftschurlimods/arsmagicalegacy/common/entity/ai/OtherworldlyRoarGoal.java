package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;

public class OtherworldlyRoarGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    public OtherworldlyRoarGoal(EnderGuardian caster) {
        super(caster, caster.level().registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(ArsMagicaAPI.resource("otherworldly_roar")).spell(), 10);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ROAR.value();
    }
}
