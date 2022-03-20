package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.world.entity.ai.goal.Goal;

public class ChaosWaterBoltGoal extends Goal {
    private final WaterGuardian waterGuardian;
    //    private static final ItemStack castStack = createDummyStack();
    //    private static ISpellPart WateryGrave() { return ArsMagicaAPI.get().getSpellPartRegistry().getValue(new ResourceLocation(ArsMagicaAPI.MOD_ID, "watery_grave"));}
    //    private static ISpellPart Projectile() { return ArsMagicaAPI.get().getSpellPartRegistry().getValue(new ResourceLocation(ArsMagicaAPI.MOD_ID, "projectile"));}
    //    private static ISpellPart MagicDamage() { return ArsMagicaAPI.get().getSpellPartRegistry().getValue(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic_damage"));}
    //    private static ISpellPart Knockback() { return ArsMagicaAPI.get().getSpellPartRegistry().getValue(new ResourceLocation(ArsMagicaAPI.MOD_ID, "knockback"));}


    public ChaosWaterBoltGoal(WaterGuardian waterGuardian) {
        this.waterGuardian = waterGuardian;
    }

    //    private static ItemStack createDummyStack() {
    //        return new SpellStack(new ArrayList<>(), Lists.newArrayList(Projectile(), WateryGrave(), MagicDamage(), Knockback()));
    //    }

    @Override
    public boolean canUse() {
        if (waterGuardian.getWaterGuardianAction() == WaterGuardian.WaterGuardianAction.IDLE && waterGuardian.isWaterGuardianActionValid(WaterGuardian.WaterGuardianAction.CASTING)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (waterGuardian.getWaterGuardianAction() == WaterGuardian.WaterGuardianAction.CASTING && waterGuardian.getTicksInAction() > 100) {
            waterGuardian.setWaterGuardianAction(WaterGuardian.WaterGuardianAction.IDLE);
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (waterGuardian.getWaterGuardianAction() != WaterGuardian.WaterGuardianAction.CASTING) {
            waterGuardian.setWaterGuardianAction(WaterGuardian.WaterGuardianAction.CASTING);
        }

        if (!waterGuardian.level.isClientSide() && waterGuardian.getWaterGuardianAction() == WaterGuardian.WaterGuardianAction.CASTING) {
            float yaw = waterGuardian.level.random.nextFloat() * 360;
            waterGuardian.setYRot(yaw);
            waterGuardian.yRotO = yaw;
            // SpellUtils.applyStackStage(castStack, host, host, host.posX, host.posY, host.posZ, null, host.worldObj, false, false, 0);
        }
    }
}
