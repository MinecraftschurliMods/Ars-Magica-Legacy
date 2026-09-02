package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageTypes;
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

public class NatureScythe extends AbstractStackOwnableEntity {
    private boolean hasHit = false;
    private int hitTicks = -1;

    public NatureScythe(EntityType<? extends NatureScythe> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public float getXRot(float partialTicks) {
        return super.getXRot(partialTicks) + (tickCount + partialTicks) * 36;
    }

    @Override
    public void tick() {
        if (getOwner() == null) {
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
            if (entity instanceof LivingEntity living && entity != getOwner()) {
                if (level() instanceof ServerLevel level) {
                    living.hurtServer(level, damageSource(AMDamageTypes.NATURE_SCYTHE), 12);
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
        if (owner instanceof NatureGuardian guardian) {
            guardian.setHasScythe(true);
        } else if (owner != null && (!(owner instanceof Player player) || !player.addItem(getStack()))) {
            ItemEntity item = new ItemEntity(level(), owner.getX(), owner.getY(), owner.getZ(), getStack());
            level().addFreshEntity(item);
        }
        remove(RemovalReason.KILLED);
    }
}
