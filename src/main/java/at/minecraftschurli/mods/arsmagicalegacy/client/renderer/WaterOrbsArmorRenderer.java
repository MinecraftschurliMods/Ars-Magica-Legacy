package at.minecraftschurli.mods.arsmagicalegacy.client.renderer;

import at.minecraftschurli.mods.arsmagicalegacy.item.WaterOrbsItem;
import com.geckolib.renderer.GeoArmorRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.List;

public class WaterOrbsArmorRenderer extends GeoArmorRenderer<WaterOrbsItem, HumanoidRenderState> {
    public <I extends WaterOrbsItem> WaterOrbsArmorRenderer(I armorItem) {
        super(armorItem);
    }

    @Override
    public List<ArmorSegment> getSegmentsForSlot(HumanoidRenderState renderState, EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS ? List.of(ArmorSegment.CHEST) : super.getSegmentsForSlot(renderState, slot);
    }
}
