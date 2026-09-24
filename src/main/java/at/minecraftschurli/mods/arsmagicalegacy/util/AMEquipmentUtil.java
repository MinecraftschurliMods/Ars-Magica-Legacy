package at.minecraftschurli.mods.arsmagicalegacy.util;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.attachment.LifeWardAttachment;
import at.minecraftschurli.mods.arsmagicalegacy.compat.curios.AMCuriosHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AMEquipmentUtil {
    public static final ResourceKey<EquipmentAsset> MAGE_ASSET_ID = createAssetId("mage");
    public static final ResourceKey<EquipmentAsset> BATTLEMAGE_ASSET_ID = createAssetId("battlemage");
    public static final ResourceKey<EquipmentAsset> MAGITECH_GOGGLES_ASSET_ID = createAssetId("magitech_goggles");
    public static final ResourceKey<EquipmentAsset> ENDER_BOOTS_ASSET_ID = createAssetId("ender_boots");
    private static final Identifier ENDER_BOOTS_ATTRIBUTE_MODIFIER_KEY = ArsMagicaApi.id("ender_boots");
    private static final AttributeModifier ENDER_BOOTS_ATTRIBUTE_MODIFIER = new AttributeModifier(ENDER_BOOTS_ATTRIBUTE_MODIFIER_KEY, -2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public static ResourceKey<EquipmentAsset> createAssetId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ArsMagicaApi.id(name));
    }

    public static boolean isInEquipmentSlot(LivingEntity entity, EquipmentSlot slot, Item item) {
        return entity.getItemBySlot(slot).is(item);
    }

    public static boolean isInCurioSlot(LivingEntity entity, Item item) {
        return ModList.get().isLoaded("curios") && AMCuriosHelper.hasItemEquipped(entity, item);
    }

    public static boolean isInEquipmentOrCurioSlot(LivingEntity entity, EquipmentSlot slot, Item item) {
        return isInEquipmentSlot(entity, slot, item) || isInCurioSlot(entity, item);
    }

    public static Item.Properties armorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId) {
        return properties.stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.builder(slot).setEquipSound(equipSound).setAsset(assetId).build());
    }

    public static Item.Properties armorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, ItemAttributeModifiers attributes) {
        return armorProperties(properties, slot, equipSound, assetId).attributes(attributes);
    }

    public static Item.Properties armorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
        Identifier modifierId = Identifier.withDefaultNamespace("armor." + slot.getName());
        if (defense > 0) {
            builder.add(Attributes.ARMOR, new AttributeModifier(modifierId, defense, AttributeModifier.Operation.ADD_VALUE), group);
        }
        if (toughness > 0) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierId, toughness, AttributeModifier.Operation.ADD_VALUE), group);
        }
        return armorProperties(properties, slot, equipSound, assetId, builder.build());
    }

    public static Item.Properties manaArmorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness, int durability, int enchantmentValue, TagKey<Item> repairItems, double manaRepairCost) {
        return armorProperties(properties, slot, equipSound, assetId, defense, toughness).durability(durability).enchantable(enchantmentValue).repairable(repairItems).component(AMDataComponents.MANA_REPAIR_COST, manaRepairCost);
    }

    public static Item.Properties mageArmorProperties(Item.Properties properties, ArmorType type, int defense) {
        return manaArmorProperties(properties, type.getSlot(), SoundEvents.ARMOR_EQUIP_LEATHER, MAGE_ASSET_ID, defense, 0.5f, type.getDurability(8), 15, AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS, 2.);
    }

    public static Item.Properties battlemageArmorProperties(Item.Properties properties, ArmorType type, int defense) {
        return manaArmorProperties(properties, type.getSlot(), SoundEvents.ARMOR_EQUIP_NETHERITE, BATTLEMAGE_ASSET_ID, defense, 1f, type.getDurability(12), 10, AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS, 4.);
    }

    public static Item.Properties enderBootsProperties(Item.Properties properties) {
        return armorProperties(properties, EquipmentSlot.FEET, SoundEvents.ARMOR_EQUIP_NETHERITE, AMEquipmentUtil.ENDER_BOOTS_ASSET_ID, 3, 3f).fireResistant().durability(1000).enchantable(10).component(AMDataComponents.MANA_REPAIR_COST, 6.);
    }

    public static void toggleEnderBoots(Player player) {
        if (!isInEquipmentSlot(player, EquipmentSlot.FEET, AMItems.ENDER_BOOTS.get()) || player.getAbilities().flying) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        List<ItemAttributeModifiers.Entry> modifiers = new ArrayList<>(boots.getAttributeModifiers().modifiers());
        Optional<ItemAttributeModifiers.Entry> modifier = modifiers.stream()
            .filter(e -> e.matches(Attributes.GRAVITY, ENDER_BOOTS_ATTRIBUTE_MODIFIER_KEY))
            .findFirst();
        if (modifier.isPresent()) {
            modifiers.remove(modifier.get());
        } else {
            modifiers.add(new ItemAttributeModifiers.Entry(Attributes.GRAVITY, ENDER_BOOTS_ATTRIBUTE_MODIFIER, EquipmentSlotGroup.FEET));
        }
        boots.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers));
        player.refreshDimensions();
        player.fallDistance = 0;
    }

    public static void tickLifeWard(LivingEntity entity) {
        if (AMServerConfig.LIFE_WARD_ENABLE_IN_INVENTORY.get() && entity instanceof Player player && player.getInventory().contains(e -> e.is(AMItems.LIFE_WARD)) || isInCurioSlot(entity, AMItems.LIFE_WARD.get())) {
            LifeWardAttachment attachment = entity.getData(AMAttachments.LIFE_WARD);
            float health = attachment.health();
            int timeUntilHeal = attachment.isEmpty() ? -AMServerConfig.LIFE_WARD_COOLDOWN.get() : attachment.timeUntilHeal();
            timeUntilHeal++;
            if (timeUntilHeal >= 0 && health < Math.min(AMServerConfig.LIFE_WARD_MAX_HEALTH.get(), entity.getMaxHealth())) {
                health++;
                timeUntilHeal = -AMServerConfig.LIFE_WARD_INTERVAL.get();
            }
            entity.setData(AMAttachments.LIFE_WARD, new LifeWardAttachment(health, timeUntilHeal));
        } else {
            entity.setData(AMAttachments.LIFE_WARD, LifeWardAttachment.EMPTY);
        }
    }

    public static float lifeWardAttacked(LivingEntity entity, DamageSource source, float amount) {
        LifeWardAttachment attachment = entity.getData(AMAttachments.LIFE_WARD);
        if (attachment.isEmpty()) return amount;
        float health = attachment.health();
        float newAmount;
        if (source.is(AMTags.DamageTypes.BYPASSES_LIFE_WARD)) {
            newAmount = amount;
        } else if (health >= amount) {
            health -= amount;
            newAmount = 0;
        } else {
            newAmount = amount - health;
            health = 0;
        }
        entity.setData(AMAttachments.LIFE_WARD, new LifeWardAttachment(health, -AMServerConfig.LIFE_WARD_COOLDOWN.get()));
        return newAmount;
    }

    public static void tickLightningCharm(LivingEntity entity) {
        if (entity.level().isClientSide()) return;
        if (!(AMServerConfig.LIGHTNING_CHARM_ENABLE_IN_INVENTORY.get() && entity instanceof Player player && player.getInventory().contains(e -> e.is(AMItems.LIGHTNING_CHARM)) || isInCurioSlot(entity, AMItems.LIGHTNING_CHARM.get()))) return;
        Vec3 pos = entity.position().add(0, 0.75, 0);
        double range = AMServerConfig.LIGHTNING_CHARM_RANGE.get();
        for (ItemEntity item : entity.level().getEntitiesOfClass(ItemEntity.class, new AABB(pos.subtract(range), pos.add(range)))) {
            if (!item.isAlive() || item.hasPickUpDelay()) continue;
            Entity owner = item.getOwner();
            if (owner != null && owner.getId() == entity.getId()) continue;
            Vec3 motion = pos.subtract(item.position().add(0, item.getBbHeight() / 2, 0));
            item.setDeltaMovement(Math.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z) > 1 ? motion.normalize() : motion);
        }
    }
}
