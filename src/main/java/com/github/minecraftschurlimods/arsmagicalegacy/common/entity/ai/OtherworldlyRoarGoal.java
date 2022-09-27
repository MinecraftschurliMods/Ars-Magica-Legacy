package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class OtherworldlyRoarGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    public OtherworldlyRoarGoal(EnderGuardian caster) {
        super(caster, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "otherworldly_roar")).spell(), 0);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ROAR.get();
    }
}
