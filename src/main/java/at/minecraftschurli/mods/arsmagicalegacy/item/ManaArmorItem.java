package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.jspecify.annotations.Nullable;

public class ManaArmorItem extends AMArmorItem {
    public static final ResourceKey<EquipmentAsset> MAGE_ASSET_ID = createAssetId("mage");
    public static final ResourceKey<EquipmentAsset> BATTLEMAGE_ASSET_ID = createAssetId("battlemage");

    public ManaArmorItem(Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, ItemAttributeModifiers attributes, double manaRepairCost) {
        super(properties.component(AMDataComponents.MANA_REPAIR_COST, manaRepairCost), slot, equipSound, assetId, attributes);
    }

    public ManaArmorItem(Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness, double manaRepairCost) {
        super(properties.component(AMDataComponents.MANA_REPAIR_COST, manaRepairCost), slot, equipSound, assetId, defense, toughness);
    }

    public ManaArmorItem(Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness, int durability, int enchantmentValue, TagKey<Item> repairItems, double manaRepairCost) {
        this(properties.durability(durability).enchantable(enchantmentValue).repairable(repairItems), slot, equipSound, assetId, defense, toughness, manaRepairCost);
    }

    public static ManaArmorItem mage(Properties properties, ArmorType type, int defense) {
        return new ManaArmorItem(properties, type.getSlot(), SoundEvents.ARMOR_EQUIP_LEATHER, MAGE_ASSET_ID, defense, 0.5f, type.getDurability(8), 15, AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS, 2.);
    }

    public static ManaArmorItem battlemage(Properties properties, ArmorType type, int defense) {
        return new ManaArmorItem(properties, type.getSlot(), SoundEvents.ARMOR_EQUIP_NETHERITE, BATTLEMAGE_ASSET_ID, defense, 1f, type.getDurability(12), 10, AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS, 4.);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (owner instanceof LivingEntity living && itemStack.isDamaged()) {
            ManaHelper helper = ArsMagicaApi.manaHelper();
            double cost = itemStack.getOrDefault(AMDataComponents.MANA_REPAIR_COST, 1.);
            if (helper.getMana(living) > cost) {
                itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                helper.decreaseMana(living, cost);
            }
        }
        super.inventoryTick(itemStack, level, owner, slot);
    }
}
