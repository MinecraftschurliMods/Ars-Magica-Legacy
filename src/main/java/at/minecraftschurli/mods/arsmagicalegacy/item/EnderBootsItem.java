package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderBootsItem extends ManaArmorItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("ender_boots");
    private static final Identifier ATTRIBUTE_MODIFIER_KEY = ArsMagicaApi.id("ender_boots");
    private static final AttributeModifier ATTRIBUTE_MODIFIER = new AttributeModifier(ATTRIBUTE_MODIFIER_KEY, -2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public EnderBootsItem(Properties properties) {
        super(properties.fireResistant().durability(1000).enchantable(10), EquipmentSlot.FEET, SoundEvents.ARMOR_EQUIP_NETHERITE, ASSET_ID, 3, 3f, 6.);
    }

    public static boolean isEquipped(LivingEntity entity) {
        return AMUtil.isInEquipmentSlot(entity, EquipmentSlot.FEET, AMItems.ENDER_BOOTS.get());
    }

    public static void toggle(LivingEntity entity) {
        if (!isEquipped(entity)) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        List<ItemAttributeModifiers.Entry> modifiers = new ArrayList<>(boots.getAttributeModifiers().modifiers());
        Optional<ItemAttributeModifiers.Entry> modifier = modifiers.stream()
            .filter(e -> e.matches(Attributes.GRAVITY, ATTRIBUTE_MODIFIER_KEY))
            .findFirst();
        if (modifier.isPresent()) {
            modifiers.remove(modifier.get());
        } else {
            modifiers.add(new ItemAttributeModifiers.Entry(Attributes.GRAVITY, ATTRIBUTE_MODIFIER, EquipmentSlotGroup.FEET));
        }
        boots.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers));
        entity.fallDistance = 0;
    }

    public static boolean isActive(ItemStack stack) {
        return stack.is(AMItems.ENDER_BOOTS) && stack.getAttributeModifiers()
            .modifiers()
            .stream()
            .anyMatch(e -> e.matches(Attributes.GRAVITY, ATTRIBUTE_MODIFIER_KEY));
    }
}
