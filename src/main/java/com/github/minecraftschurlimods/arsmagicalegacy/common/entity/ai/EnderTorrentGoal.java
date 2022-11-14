package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.EntityHitResult;

public class EnderTorrentGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    public EnderTorrentGoal(EnderGuardian caster) {
        super(caster, caster.level.registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_bolt")).spell(), 10);
    }

    @Override
    public void tick() {
        super.tick();
        if (caster.getTarget() != null) {
            caster.getLookControl().setLookAt(caster.getTarget(), 30, 30);
            if (caster.getTicksInAction() % 2 == 0) {
                ArsMagicaAPI.get().getSpellHelper().invoke(spell, caster, caster.getLevel(), new EntityHitResult(caster), caster.getTicksInAction(), 0, false);
            } else if (caster.getTicksInAction() == 10) {
                caster.getLevel().playSound(null, caster, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + caster.getRandom().nextDouble() * 0.5f));
            }
        }
    }
}
