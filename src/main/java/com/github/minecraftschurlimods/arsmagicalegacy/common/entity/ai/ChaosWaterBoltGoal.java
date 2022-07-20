package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.EntityHitResult;

public class ChaosWaterBoltGoal extends Goal {
    private final WaterGuardian waterGuardian;
    private final ISpell spell;

    public ChaosWaterBoltGoal(WaterGuardian waterGuardian) {
        this.waterGuardian = waterGuardian;
        spell = waterGuardian.level.registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "chaos_water_bolt")).spell();
    }

    @Override
    public boolean canUse() {
        return waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.IDLE && waterGuardian.isWaterGuardianActionValid(WaterGuardian.WaterGuardianAction.CASTING);
    }

    @Override
    public boolean canContinueToUse() {
        if (waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.CASTING && waterGuardian.getTicksInAction() > 100) {
            waterGuardian.setAction(WaterGuardian.WaterGuardianAction.IDLE);
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (waterGuardian.getAction() != WaterGuardian.WaterGuardianAction.CASTING) {
            waterGuardian.setAction(WaterGuardian.WaterGuardianAction.CASTING);
        }
        if (!waterGuardian.getLevel().isClientSide() && waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.CASTING) {
            float yaw = waterGuardian.getLevel().random.nextFloat() * 360;
            waterGuardian.setYRot(yaw);
            waterGuardian.yRotO = yaw;
            ArsMagicaAPI.get().getSpellHelper().invoke(spell, waterGuardian, waterGuardian.getLevel(), new EntityHitResult(waterGuardian), waterGuardian.getTicksInAction(), 0, false);
        }
    }
}
