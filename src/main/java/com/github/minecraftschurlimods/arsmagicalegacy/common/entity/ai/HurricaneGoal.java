package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HurricaneGoal extends AbstractBossGoal<AirGuardian> {
    public HurricaneGoal(AirGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getRandom().nextBoolean();
    }

    @Override
    public void tick() {
        Level level = boss.getLevel();
        if (!level.isClientSide()) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    int y = (int) (boss.getY() + 1);
                    BlockPos pos = new BlockPos(boss.getX() + x, y, boss.getZ() + z);
                    while (!level.canSeeSky(pos) && level.getBlockState(pos).getDestroySpeed(level, pos) != -1) {
                        if (Math.abs(y - boss.getY()) > 10) break;
                        level.destroyBlock(new BlockPos(pos.getX(), y++, pos.getZ()), true);
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    public void perform() {
        Level level = boss.getLevel();
        for (Entity e : level.getEntitiesOfClass(Entity.class, boss.getBoundingBox().inflate(6, 3, 6), e -> !(e instanceof AbstractBoss))) {
            if (!level.isClientSide() && e instanceof Whirlwind) {
                e.remove(Entity.RemovalReason.KILLED);
                continue;
            }
            if (e instanceof LivingEntity living) {
                living.hurt(AMDamageSources.WIND, 12);
            }
            Vec3 movement = boss.getEyePosition().subtract(e.position()).normalize();
            e.push(-movement.x() * 2.5, 2.5, -movement.z() * 2.5);
            e.fallDistance = 0;
        }
    }
}
