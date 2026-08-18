package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.phys.Vec3;

public class FireAntennaeItem extends AMArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("fire_antennae");
    private static final float LAVA_VISION_MIN = 100;
    private static final float LAVA_VISION_MAX = 600;
    private static float lavaVision = 0;

    public FireAntennaeItem(Properties properties) {
        super(properties.fireResistant(), EquipmentSlot.HEAD, SoundEvents.ARMOR_EQUIP_TURTLE, ASSET_ID);
    }

    public static void tick(Player player) {
        Pose forcedPose = player.getForcedPose();
        boolean inLava = player.isEyeInFluid(FluidTags.LAVA);
        if (forcedPose == Pose.SWIMMING && (!inLava || player.isSpectator())) {
            player.setForcedPose(null);
        } else if (isEquipped(player) && forcedPose == null && inLava && player.isSprinting() && !player.isSpectator()) {
            player.setForcedPose(Pose.SWIMMING);
        }
        if (player.isLocalPlayer()) {
            if (!inLava) {
                lavaVision = 0;
            } else if (lavaVision < 600) {
                lavaVision++;
            }
        }
    }

    public static float getLavaVision(Player player) {
        if (!player.isEyeInFluid(FluidTags.LAVA)) return 0;
        if (lavaVision >= LAVA_VISION_MAX) return 1;
        float a = Mth.clamp(lavaVision / LAVA_VISION_MIN, 0, 1);
        float b = lavaVision < LAVA_VISION_MIN ? 0 : Mth.clamp((lavaVision - LAVA_VISION_MIN) / (LAVA_VISION_MAX - LAVA_VISION_MIN), 0, 1);
        return a * 0.6f + b * 0.39999998f;
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMUtil.isInEquipmentOrCurioSlot(entity, EquipmentSlot.HEAD, AMItems.FIRE_ANTENNAE.get());
    }

    public static boolean travelInLava(LivingEntity entity, Vec3 input, double baseGravity, boolean isFalling, double oldY) {
        if (!isEquipped(entity)) return false;
        entity.moveRelative(0.02f, input);
        Vec3 movement = entity.getDeltaMovement();
        if (movement.horizontalDistance() > 1.0e-4) {
            System.out.println(movement);
        }
        entity.move(MoverType.SELF, movement);
        movement = entity.getFluidFallingAdjustedMovement(baseGravity, isFalling, movement.multiply(0.96, 0.8, 0.96));
        if (entity.horizontalCollision && entity.isFree(movement.x, movement.y + 0.6 - entity.getY() + oldY, movement.z)) {
            movement = new Vec3(movement.x, 0.3, movement.z);
        }
        entity.setDeltaMovement(movement);
        return true;
    }
}
