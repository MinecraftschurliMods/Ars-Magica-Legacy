package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageTypes;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownRock extends AbstractOwnableEntity {
    public ThrownRock(EntityType<? extends ThrownRock> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (getOwner() == null || getOwner().isDeadOrDying() || tickCount >= 50) {
            setRemoved(RemovalReason.KILLED);
        }
        Vec3 oldPos = position();
        Vec3 newPos = position().add(getDeltaMovement());
        HitResult hit = AMUtil.getHitResult(oldPos, newPos, this, false);
        if (hit.getType() != HitResult.Type.MISS) {
            newPos = hit.getLocation();
        }
        if (hit.getType() == HitResult.Type.ENTITY && !level().isClientSide()) {
            if (((EntityHitResult) hit).getEntity() instanceof LivingEntity living && living != getOwner() && level() instanceof ServerLevel level) {
                if (living.isBlocking()) {
                    living.stopUsingItem();
                    ItemStack itemBlockingWith = living.getItemBlockingWith();
                    if (itemBlockingWith != null && living instanceof Player player && random.nextFloat() < 0.25f) {
                        player.getCooldowns().addCooldown(itemBlockingWith, 100);
                    }
                } else {
                    living.hurtServer(level, damageSource(AMDamageTypes.THROWN_ROCK), 6);
                }
                setRemoved(RemovalReason.KILLED);
            }
        }
        setPos(newPos);
    }
}
