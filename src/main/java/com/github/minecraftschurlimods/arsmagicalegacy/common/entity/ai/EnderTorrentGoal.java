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

public class EnderTorrentGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private final ISpell spell;
    private int cooldown = 0;

    public EnderTorrentGoal(EnderGuardian enderGuardian) {
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
        cooldown = 100;
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
            if (enderGuardian.getTicksInAction() > 15) {
                if ((enderGuardian.getTicksInAction() - 15) % 10 == 0) {
                    ArsMagicaAPI.get().getSpellHelper().invoke(spell, enderGuardian, enderGuardian.getLevel(), new EntityHitResult(enderGuardian), enderGuardian.getTicksInAction(), 0, false);
                }
                enderGuardian.lookAt(enderGuardian.getTarget(), 15, 180);
            } else if (enderGuardian.getTicksInAction() == 15) {
                enderGuardian.getLevel().playSound(null, enderGuardian, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + enderGuardian.getRandom().nextDouble() * 0.5f));
            } else {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
            }
        }
    }
}
