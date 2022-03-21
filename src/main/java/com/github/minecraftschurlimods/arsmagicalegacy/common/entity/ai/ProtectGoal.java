package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.EntityHitResult;

public class ProtectGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private final ISpell spell = PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell();
    private int cooldown = 0;

    public ProtectGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        return enderGuardian.getTarget() != null && enderGuardian.getTicksSinceLastAttack() <= 40 && cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 20;
        enderGuardian.clearFire();
        ArsMagicaAPI.get().getSpellHelper().invoke(spell, enderGuardian, enderGuardian.getLevel(), new EntityHitResult(enderGuardian), enderGuardian.getTicksInAction(), 0, false);
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
        }
    }
}
