package at.minecraftschurli.mods.arsmagicalegacy.item;

import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Consumer;

public class EarthArmorItem extends ManaArmorItem implements GeoItem {
    public static final ResourceKey<EquipmentAsset> ASSET_ID = createAssetId("earth_armor");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public EarthArmorItem(Properties properties) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(EquipmentSlot.CHEST);
        Identifier modifierId = Identifier.withDefaultNamespace("armor." + EquipmentSlot.CHEST.getName());
        builder.add(Attributes.ARMOR, new AttributeModifier(modifierId, 16, AttributeModifier.Operation.ADD_VALUE), group);
        builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierId, 4f, AttributeModifier.Operation.ADD_VALUE), group);
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(modifierId, 1f, AttributeModifier.Operation.ADD_VALUE), group);
        super(properties.fireResistant().durability(1000).enchantable(10), EquipmentSlot.CHEST, SoundEvents.ARMOR_EQUIP_DIAMOND, ASSET_ID, builder.build(), 6.);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final Lazy<GeoArmorRenderer<EarthArmorItem, HumanoidRenderState>> armorRenderer = Lazy.of(() -> new GeoArmorRenderer<>(EarthArmorItem.this));
            private final Lazy<GeoItemRenderer<EarthArmorItem>> itemRenderer = Lazy.of(() -> new GeoItemRenderer<>(EarthArmorItem.this));

            @Override
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
                return armorRenderer.get();
            }

            @Override
            public GeoItemRenderer<?> getGeoItemRenderer() {
                return itemRenderer.get();
            }
        });
    }
}
