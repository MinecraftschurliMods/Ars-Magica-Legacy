package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Consumer;

public class FireAntennaeItem extends AMArmorItem implements GeoItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("fire_antennae");
    private static final float LAVA_VISION_MIN = 100;
    private static final float LAVA_VISION_MAX = 600;
    private static float lavaVision = 0;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FireAntennaeItem(Properties properties) {
        super(properties.fireResistant(), EquipmentSlot.HEAD, SoundEvents.ARMOR_EQUIP_TURTLE, ASSET_ID);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final Lazy<GeoArmorRenderer<FireAntennaeItem, HumanoidRenderState>> armorRenderer = Lazy.of(() -> new GeoArmorRenderer<>(FireAntennaeItem.this));
            private final Lazy<GeoItemRenderer<FireAntennaeItem>> itemRenderer = Lazy.of(() -> new GeoItemRenderer<>(FireAntennaeItem.this));

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

    public static void tick(LivingEntity entity) {
        if (isEquipped(entity) && entity.isOnFire()) {
            entity.clearFire();
        }
        if (!(entity instanceof Player player)) return;
        Pose forcedPose = player.getForcedPose();
        boolean inLava = player.isEyeInFluid(NeoForgeMod.LAVA_TYPE.value());
        if (forcedPose == Pose.SWIMMING && (!inLava || player.isSpectator())) {
            player.setForcedPose(null);
        } else if (isEquipped(player) && forcedPose == null && inLava && player.isSprinting() && !player.isSpectator()) {
            player.setForcedPose(Pose.SWIMMING);
        }
        if (!player.isLocalPlayer()) return;
        if (!inLava) {
            lavaVision = 0;
        } else if (lavaVision < 600) {
            lavaVision++;
        }
    }

    public static float getLavaVision(Player player) {
        if (!player.isEyeInFluid(NeoForgeMod.LAVA_TYPE.value())) return 0;
        if (lavaVision >= LAVA_VISION_MAX) return 1;
        float a = Mth.clamp(lavaVision / LAVA_VISION_MIN, 0, 1);
        float b = lavaVision < LAVA_VISION_MIN ? 0 : Mth.clamp((lavaVision - LAVA_VISION_MIN) / (LAVA_VISION_MAX - LAVA_VISION_MIN), 0, 1);
        return a * 0.6f + b * 0.4f;
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMUtil.isInEquipmentOrCurioSlot(entity, EquipmentSlot.HEAD, AMItems.FIRE_ANTENNAE.get());
    }

    public static void modifyTravelInLava(LivingEntity entity, Vec3 movement, double baseGravity, boolean isFalling, double oldY) {
        if (!FireAntennaeItem.isEquipped(entity)) return;
        movement = entity.getFluidFallingAdjustedMovement(baseGravity, isFalling, movement.multiply(0.96, 0.8, 0.96));
        if (entity.horizontalCollision && entity.isFree(movement.x, movement.y + 0.6 - entity.getY() + oldY, movement.z)) {
            movement = new Vec3(movement.x, 0.3, movement.z);
        }
        entity.setDeltaMovement(movement);
    }
}
