package at.minecraftschurli.mods.arsmagicalegacy.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AirSled extends AbstractStackOwnableEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

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
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
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
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }
}
