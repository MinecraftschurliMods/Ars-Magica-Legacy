package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.client.renderer.WaterOrbsArmorRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Consumer;

public class WaterOrbsItem extends AMArmorItem implements GeoItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("water_orbs");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public WaterOrbsItem(Properties properties) {
        super(properties, EquipmentSlot.LEGS, SoundEvents.ARMOR_EQUIP_GOLD, ASSET_ID);
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
            private final Lazy<GeoArmorRenderer<WaterOrbsItem, HumanoidRenderState>> armorRenderer = Lazy.of(() -> new WaterOrbsArmorRenderer(WaterOrbsItem.this));
            private final Lazy<GeoItemRenderer<WaterOrbsItem>> itemRenderer = Lazy.of(() -> new GeoItemRenderer<>(WaterOrbsItem.this));

            @Override
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
                return armorRenderer.get();
            }

            @Override
            public GeoItemRenderer<?> getGeoItemRenderer() {
                return itemRenderer.get();
            }
        });
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMUtil.isInEquipmentOrCurioSlot(entity, EquipmentSlot.LEGS, AMItems.WATER_ORBS.get());
    }
}
