package at.minecraftschurli.mods.arsmagicalegacy.item;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class EarthArmorItem extends ManaArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("earth_armor");

    public EarthArmorItem(Properties properties) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(EquipmentSlot.CHEST);
        Identifier modifierId = Identifier.withDefaultNamespace("armor." + EquipmentSlot.CHEST.getName());
        builder.add(Attributes.ARMOR, new AttributeModifier(modifierId, 16, AttributeModifier.Operation.ADD_VALUE), group);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierId, 4f, AttributeModifier.Operation.ADD_VALUE), group);
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(modifierId, 1f, AttributeModifier.Operation.ADD_VALUE), group);
        super(properties.fireResistant().durability(1000).enchantable(10), EquipmentSlot.CHEST, SoundEvents.ARMOR_EQUIP_DIAMOND, ASSET_ID, builder.build(), 6.);
    }
}
