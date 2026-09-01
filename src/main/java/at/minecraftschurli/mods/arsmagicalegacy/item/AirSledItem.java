package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AirSled;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.Objects;
import java.util.function.Consumer;

public class AirSledItem extends Item implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AirSledItem(Properties properties) {
        super(properties.stacksTo(1));
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
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final Lazy<GeoItemRenderer<AirSledItem>> itemRenderer = Lazy.of(() -> new GeoItemRenderer<>(AirSledItem.this));

            @Override
            public GeoItemRenderer<?> getGeoItemRenderer() {
                return itemRenderer.get();
            }
        });
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        if (!level.isClientSide()) {
            AirSled entity = Objects.requireNonNull(AMEntities.AIR_SLED.get().create(level, EntitySpawnReason.MOB_SUMMONED));
            Vec3 location = context.getClickLocation();
            entity.teleportTo(location.x, location.y, location.z);
            if (player != null) {
                entity.setOwner(player);
                entity.setStack(player.getItemInHand(hand));
            }
            level.addFreshEntity(entity);
        }
        if (player != null) {
            player.setItemInHand(hand, ItemStack.EMPTY);
        }
        return InteractionResult.CONSUME;
    }
}
