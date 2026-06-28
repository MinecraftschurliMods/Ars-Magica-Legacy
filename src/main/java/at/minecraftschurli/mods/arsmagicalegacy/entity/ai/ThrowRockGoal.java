package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.EarthGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ThrownRock;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class ThrowRockGoal extends AbstractBossGoal<EarthGuardian> {
    public ThrowRockGoal(EarthGuardian boss) {
        super(boss, AbstractBoss.Action.THROW, 15, 5);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4;
    }

    @Override
    public void performTick() {
        super.performTick();
        boss.setHasRock(ticks > 2 && ticks < 16);
    }

    @Override
    public void perform() {
        Level level = boss.level();
        if (!level.isClientSide()) {
            ThrownRock entity = Objects.requireNonNull(AMEntities.THROWN_ROCK.get().create(level, EntitySpawnReason.MOB_SUMMONED));
            Vec3 vec = boss.getEyePosition().add(boss.getLookAngle());
            entity.teleportTo(vec.x(), vec.y(), vec.z());
            entity.setDeltaMovement(boss.getLookAngle());
            entity.setOwner(boss);
            level.addFreshEntity(entity);
        }
    }
}
