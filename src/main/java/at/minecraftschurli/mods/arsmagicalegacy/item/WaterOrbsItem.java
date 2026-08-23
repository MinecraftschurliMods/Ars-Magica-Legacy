package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class WaterOrbsItem extends AMArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("water_orbs");

    public WaterOrbsItem(Properties properties) {
        super(properties, EquipmentSlot.LEGS, SoundEvents.ARMOR_EQUIP_GOLD, ASSET_ID);
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMUtil.isInEquipmentOrCurioSlot(entity, EquipmentSlot.LEGS, AMItems.WATER_ORBS.get());
    }
}
