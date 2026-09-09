package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public class AMArmorItem extends Item {
    public static final ResourceKey<EquipmentAsset> MAGITECH_GOGGLES_ASSET_ID = createAssetId("magitech_goggles");

    public AMArmorItem(Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId) {
        super(properties.stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.builder(slot).setEquipSound(equipSound).setAsset(assetId).build()));
    }

    public AMArmorItem(Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, ItemAttributeModifiers attributes) {
        this(properties.attributes(attributes), slot, equipSound, assetId);
    }

    public AMArmorItem(Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
        Identifier modifierId = Identifier.withDefaultNamespace("armor." + slot.getName());
        if (defense > 0) {
            builder.add(Attributes.ARMOR, new AttributeModifier(modifierId, defense, AttributeModifier.Operation.ADD_VALUE), group);
        }
        if (toughness > 0) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierId, toughness, AttributeModifier.Operation.ADD_VALUE), group);
        }
        this(properties, slot, equipSound, assetId, builder.build());
    }

    public static ResourceKey<EquipmentAsset> createAssetId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ArsMagicaApi.id(name));
    }
}
