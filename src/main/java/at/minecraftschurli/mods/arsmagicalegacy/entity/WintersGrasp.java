package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.entity.PartEntity;

public class WintersGrasp extends AbstractStackOwnableEntity {
    private boolean hasHit = false;
    private int hitTicks = -1;

    public WintersGrasp(EntityType<? extends WintersGrasp> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public void tick() {
        if (getOwner() == null || getOwner().isDeadOrDying()) {
            remove(RemovalReason.KILLED);
            return;
        }
        if (hitTicks != -1 && tickCount / 2 > hitTicks) {
            returnToOwner();
        } else if (tickCount > 50) {
            setHasHit();
        }
        HitResult result = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, false);
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            while (entity instanceof PartEntity<?> part) {
                entity = part.getParent();
            }
            if (entity instanceof LivingEntity && entity != getOwner() && !hasPassenger(entity)) {
                if (level() instanceof ServerLevel level) {
                    entity.hurtServer(level, damageSources().freeze(), 4);
                }
                if (getPassengers().isEmpty() && canAddPassenger(entity)) {
                    entity.startRiding(this, true, true);
                }
                setHasHit();
            }
            if (hasHit && distanceTo(getOwner()) < 4) {
                returnToOwner();
            }
        } else if (result.getType() == HitResult.Type.BLOCK) {
            setHasHit();
        }
        setPos(position().add(getDeltaMovement()));
    }

    private void setHasHit() {
        if (!hasHit) {
            setDeltaMovement(getDeltaMovement().multiply(-1, -1, -1));
            hasHit = true;
            hitTicks = tickCount;
        }
    }

    private void returnToOwner() {
        LivingEntity owner = getOwner();
        getPassengers().forEach(Entity::stopRiding);
        if (owner instanceof IceGuardian guardian) {
            guardian.returnArm();
        } else if (owner != null && (!(owner instanceof Player player) || !player.addItem(getStack()))) {
            ItemEntity item = new ItemEntity(level(), owner.getX(), owner.getY(), owner.getZ(), getStack());
            level().addFreshEntity(item);
        }
        remove(RemovalReason.KILLED);
    }
}
