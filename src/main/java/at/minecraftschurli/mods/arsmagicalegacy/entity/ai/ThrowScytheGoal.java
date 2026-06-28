package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.NatureGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.NatureScythe;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class ThrowScytheGoal extends AbstractBossGoal<NatureGuardian> {
    public ThrowScytheGoal(NatureGuardian boss) {
        super(boss, AbstractBoss.Action.THROW, 10, 10);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4 && boss.hasScythe();
    }

    @Override
    public void perform() {
        Level level = boss.level();
        if (!level.isClientSide()) {
            NatureScythe entity = Objects.requireNonNull(AMEntities.NATURE_SCYTHE.get().create(level, EntitySpawnReason.MOB_SUMMONED));
            Vec3 vec = boss.position().add(0, 3, 0).add(boss.getLookAngle());
            entity.teleportTo(vec.x(), vec.y(), vec.z());
            entity.setDeltaMovement(boss.getLookAngle());
            entity.setXRot(boss.getXRot());
            entity.setYRot(boss.getYRot());
            entity.setOwner(boss);
            level.addFreshEntity(entity);
            boss.setHasScythe(false);
        }
    }
}
