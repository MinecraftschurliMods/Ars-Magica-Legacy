package at.minecraftschurli.mods.arsmagicalegacy.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class EnderBootsItem extends ManaArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("ender_boots");

    public EnderBootsItem(Properties properties) {
        super(properties.fireResistant().durability(1000).enchantable(10), EquipmentSlot.FEET, SoundEvents.ARMOR_EQUIP_NETHERITE, ASSET_ID, 3, 3f, 6.);
    }
}
