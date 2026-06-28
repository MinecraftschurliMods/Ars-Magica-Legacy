package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCasterEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Mob;

public class DispelGoal<T extends Mob & SpellCasterEntity> extends ExecuteSpellGoal<T> {
    private static final ResourceKey<Spell> SPELL = ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id("dispel"));

    public DispelGoal(T caster) {
        super(caster, caster.registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB).getValueOrThrow(SPELL), 0);
    }

    @Override
    public boolean canUse() {
        return (!caster.getActiveEffects().isEmpty() || caster.isOnFire()) && super.canUse();
    }

    @Override
    public void stop() {
        super.stop();
        caster.clearFire();
    }
}
