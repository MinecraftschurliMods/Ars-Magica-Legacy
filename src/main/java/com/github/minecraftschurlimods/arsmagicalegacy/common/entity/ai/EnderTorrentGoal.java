package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class EnderTorrentGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    private static final ResourceKey<Spell> SPELL = ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id("ender_bolt"));

    public EnderTorrentGoal(EnderGuardian caster) {
        super(caster, caster.registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB).getValueOrThrow(SPELL), 10);
    }

    @Override
    public void tick() {
        super.tick();
        if (caster.getTarget() != null) {
            caster.getLookControl().setLookAt(caster.getTarget(), 30, 30);
            Level level = caster.level();
            if (caster.getTicksInAction() % 2 == 0 && spell != null) {
                ArsMagicaApi.spellHelper().cast(spell, level, caster, false, false);
            } else if (caster.getTicksInAction() == 10) {
                level.playSound(null, caster, AMSounds.ENDER_GUARDIAN_ATTACK.value(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + caster.getRandom().nextDouble() * 0.5f));
            }
        }
    }
}
