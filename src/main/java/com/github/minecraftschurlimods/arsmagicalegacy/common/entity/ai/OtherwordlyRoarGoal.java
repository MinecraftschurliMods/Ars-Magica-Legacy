package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.EntityHitResult;

public class OtherwordlyRoarGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private final ISpell spell = PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "otherworldly_roar")).spell();
    private int cooldown = 0;

    public OtherwordlyRoarGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        return enderGuardian.getTarget() != null && enderGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, enderGuardian.getBoundingBox().inflate(9, 3, 9)).size() >= 2 && cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 100;
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            if (enderGuardian.getTicksInAction() == 33) {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
                ArsMagicaAPI.get().getSpellHelper().invoke(spell, enderGuardian, enderGuardian.getLevel(), new EntityHitResult(enderGuardian), enderGuardian.getTicksInAction(), 0, false);
            } else {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
            }
        }
    }
}
