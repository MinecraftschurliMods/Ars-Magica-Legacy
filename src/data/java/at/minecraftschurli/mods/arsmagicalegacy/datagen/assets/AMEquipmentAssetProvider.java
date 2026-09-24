package at.minecraftschurli.mods.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMEquipmentUtil;
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
        output.accept(AMEquipmentUtil.MAGITECH_GOGGLES_ASSET_ID, EquipmentClientInfo.builder()
            .addMainHumanoidLayer(ArsMagicaApi.id("magitech_goggles"), false)
            .build());
        output.accept(AMEquipmentUtil.MAGE_ASSET_ID, EquipmentClientInfo.builder()
            .addHumanoidLayers(ArsMagicaApi.id("mage"))
            .build());
        output.accept(AMEquipmentUtil.BATTLEMAGE_ASSET_ID, EquipmentClientInfo.builder()
            .addHumanoidLayers(ArsMagicaApi.id("battlemage"))
            .build());
        output.accept(AMEquipmentUtil.ENDER_BOOTS_ASSET_ID, EquipmentClientInfo.builder()
            .addMainHumanoidLayer(ArsMagicaApi.id("ender_boots"), false)
            .build());
    }
}
