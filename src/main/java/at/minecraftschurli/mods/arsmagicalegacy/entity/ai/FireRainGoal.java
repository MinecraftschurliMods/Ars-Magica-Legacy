package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.entity.FireGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.entity.FireRain;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class FireRainGoal extends AbstractBossGoal<FireGuardian> {
    public FireRainGoal(FireGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public void perform() {
        if (!boss.level().isClientSide()) {
            FireRain fireRain = Objects.requireNonNull(AMEntities.FIRE_RAIN.get().create(boss.level(), EntitySpawnReason.MOB_SUMMONED));
            LivingEntity target = boss.getTarget();
            fireRain.setPos((target != null ? target.position() : boss.position()).add(0, 1.5, 0));
            fireRain.setDuration(200);
            fireRain.setOwner(boss);
            fireRain.setDamage(2);
            fireRain.setRange(2);
            boss.level().addFreshEntity(fireRain);
        }
    }
}
