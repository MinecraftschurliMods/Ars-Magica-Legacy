package at.minecraftschurli.mods.arsmagicalegacy.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class AirSled extends AbstractStackOwnableEntity implements GeoEntity, PlayerRideable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Vec3 input = Vec3.ZERO;
    private boolean oldShift = false;
    private boolean sprint = false;

    public AirSled(EntityType<? extends AbstractOwnableEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (isInvulnerable()) return false;
        for (Entity entity : getPassengers()) {
            entity.stopRiding();
        }
        LivingEntity owner = getOwner();
        ItemStack stack = getStack();
        if (owner != null && (!(owner instanceof Player player) || !player.addItem(stack))) {
            ItemEntity item = new ItemEntity(level(), getX(), getY(), getZ(), stack);
            level().addFreshEntity(item);
        }
        remove(RemovalReason.KILLED);
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericLivingController());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity other) {
        return true;
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        return isVehicle() && getPassengers().getFirst() instanceof Player player ? player : null;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        if (isVehicle()) return super.interact(player, hand, location);
        if (player.isSecondaryUseActive() && player.getItemInHand(hand).isEmpty()) {
            player.setItemInHand(hand, getStack());
            remove(RemovalReason.KILLED);
        } else {
            player.startRiding(this);
        }
        return super.interact(player, hand, location);
    }

    @Override
    public boolean isPickable() {
        return !isRemoved();
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!isVehicle()) {
            setDeltaMovement(0, getDeltaMovement().y, 0);
            sprint = false;
        } else {
            setYRot(getPassengers().getFirst().getYRot());
            sprint &= input.horizontalDistance() >= 1.0e-4;
        }
        if (oldShift || !isVehicle()) {
            applyGravity();
        }
        float friction = sprint ? 1 : 0.91f;
        moveRelative(getSpeed() * 0.216f, new Vec3(input.x, 0, input.z));
        Vec3 movement = getDeltaMovement();
        move(MoverType.SELF, movement);
        double y = onGround() ? 0 : isVehicle() ? horizontalCollision ? 0.196 : (input.y * 0.6 + movement.y) * (sprint ? 0.98 : 0.91) : movement.y;
        setDeltaMovement(movement.x * friction, Math.abs(y) < 1.0e-4 ? 0 : y, movement.z * friction);
    }

    public void control(boolean w, boolean s, boolean a, boolean d, boolean inputSpace, boolean inputShift, boolean inputCtrl) {
        if (!isVehicle()) return;
        Vec2 direction = new Vec2(a == d ? 0 : a ? 1 : -1, w == s ? 0 : w ? 1 : -1).normalized();
        if (direction.length() > 0) {
            float dx = Math.abs(direction.x);
            float dz = Math.abs(direction.y);
            direction = direction.scale(Math.min(0.98f * Mth.sqrt(1 + Mth.square(dz > dx ? dx / dz : dz / dx)), 1));
            sprint |= inputCtrl;
        }
        int dy = 0;
        if (inputSpace) {
            dy++;
        }
        boolean jump = !oldShift;
        if (inputShift) {
            dy--;
            oldShift = true;
        } else {
            oldShift = false;
        }
        input = new Vec3(direction.x, dy * getFlyingSpeed() * (jump ? 3 : 1), direction.y);
    }

    private float getFlyingSpeed() {
        return getControllingPassenger() instanceof Player ? getSpeed() * 0.216f : 0.02432f;
    }

    private float getSpeed() {
        return getControllingPassenger() instanceof Player player ? sprint ? player.getSpeed() * 1.3f : player.getSpeed() : 0;
    }
}
