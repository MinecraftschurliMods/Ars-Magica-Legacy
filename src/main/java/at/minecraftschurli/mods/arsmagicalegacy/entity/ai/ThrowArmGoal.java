package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.IceGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.WintersGrasp;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class ThrowArmGoal extends AbstractBossGoal<IceGuardian> {
    public ThrowArmGoal(IceGuardian boss) {
        super(boss, AbstractBoss.Action.THROW, 10, 10);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4 && boss.canLaunchArm();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ICE_GUARDIAN_LAUNCH_ARM.value();
    }

    @Override
    public void perform() {
        Level level = boss.level();
        if (level.isClientSide()) return;
        WintersGrasp entity = Objects.requireNonNull(AMEntities.WINTERS_GRASP.get().create(level, EntitySpawnReason.MOB_SUMMONED));
        Vec3 vec = boss.position().add(0, 1, 0).add(boss.getLookAngle());
        entity.teleportTo(vec.x(), vec.y(), vec.z());
        entity.setDeltaMovement(boss.getLookAngle());
        entity.setXRot(boss.getXRot());
        entity.setYRot(boss.getYRot());
        entity.setOwner(boss);
        level.addFreshEntity(entity);
        boss.launchArm();
    }
}
