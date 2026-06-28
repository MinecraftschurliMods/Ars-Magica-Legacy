package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AirGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.Whirlwind;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class WhirlwindGoal extends AbstractBossGoal<AirGuardian> {
    public WhirlwindGoal(AirGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 40);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4;
    }

    @Override
    public void perform() {
        Level level = boss.level();
        if (!level.isClientSide()) {
            Whirlwind entity = Objects.requireNonNull(AMEntities.WHIRLWIND.get().create(level, EntitySpawnReason.MOB_SUMMONED));
            entity.teleportTo(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
            entity.setDeltaMovement(boss.getLookAngle());
            level.addFreshEntity(entity);
        }
    }
}
