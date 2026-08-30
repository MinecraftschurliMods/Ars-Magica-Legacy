package at.minecraftschurli.mods.arsmagicalegacy.item;

import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Consumer;

public class AirSledItem extends Item implements GeoItem {
    public AirSledItem(Properties properties) {
        super(properties);
    }
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

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
}
