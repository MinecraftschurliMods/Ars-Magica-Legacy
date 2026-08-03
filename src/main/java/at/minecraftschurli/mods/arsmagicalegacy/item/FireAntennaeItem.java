package at.minecraftschurli.mods.arsmagicalegacy.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class FireAntennaeItem extends AMArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("fire_antennae");

    public FireAntennaeItem(Properties properties) {
        super(properties.fireResistant(), EquipmentSlot.HEAD, SoundEvents.ARMOR_EQUIP_TURTLE, ASSET_ID);
    }
}
