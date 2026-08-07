package at.minecraftschurli.mods.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.item.AMArmorItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.EnderBootsItem;
import at.minecraftschurli.mods.arsmagicalegacy.item.ManaArmorItem;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

public final class AMEquipmentAssetProvider extends EquipmentAssetProvider {
    public AMEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(AMArmorItem.MAGITECH_GOGGLES_ASSET_ID, EquipmentClientInfo.builder()
            .addMainHumanoidLayer(ArsMagicaApi.id("magitech_goggles"), false)
            .build());
        output.accept(ManaArmorItem.MAGE_ASSET_ID, EquipmentClientInfo.builder()
            .addHumanoidLayers(ArsMagicaApi.id("mage"))
            .build());
        output.accept(ManaArmorItem.BATTLEMAGE_ASSET_ID, EquipmentClientInfo.builder()
            .addHumanoidLayers(ArsMagicaApi.id("battlemage"))
            .build());
        output.accept(EnderBootsItem.ASSET_ID, EquipmentClientInfo.builder()
            .addMainHumanoidLayer(ArsMagicaApi.id("ender_boots"), false)
            .build());
    }
}
