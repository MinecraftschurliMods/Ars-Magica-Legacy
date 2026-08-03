package at.minecraftschurli.mods.arsmagicalegacy.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class WaterOrbsItem extends AMArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("water_orbs");

    public WaterOrbsItem(Properties properties) {
        super(properties, EquipmentSlot.LEGS, SoundEvents.ARMOR_EQUIP_GOLD, ASSET_ID);
    }
}
