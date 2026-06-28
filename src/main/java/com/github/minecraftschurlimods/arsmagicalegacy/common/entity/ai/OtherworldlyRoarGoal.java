package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

public class OtherworldlyRoarGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    private static final ResourceKey<Spell> SPELL = ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id("otherworldly_roar"));

    public OtherworldlyRoarGoal(EnderGuardian caster) {
        super(caster, caster.registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB).getValueOrThrow(SPELL), 0);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ROAR.value();
    }
}
