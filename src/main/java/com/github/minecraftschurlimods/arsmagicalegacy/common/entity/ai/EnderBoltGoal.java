package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.EntityHitResult;

public class EnderBoltGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private final ISpell spell;
    private int cooldown = 0;

    public EnderBoltGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
        spell = enderGuardian.level.registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_bolt")).spell();
    }

    @Override
    public boolean canUse() {
        if (enderGuardian.getTarget() == null) {
            return false;
        }
        return cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 20;
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
            if (enderGuardian.getTicksInAction() == 7) {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
                enderGuardian.getLevel().playSound(null, enderGuardian, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
                ArsMagicaAPI.get().getSpellHelper().invoke(spell, enderGuardian, enderGuardian.getLevel(), new EntityHitResult(enderGuardian), enderGuardian.getTicksInAction(), 0, false);
            } else {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
            }
        }
    }
}
