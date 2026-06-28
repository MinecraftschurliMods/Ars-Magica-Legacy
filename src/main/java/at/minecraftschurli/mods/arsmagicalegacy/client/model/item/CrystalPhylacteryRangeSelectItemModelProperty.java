package at.minecraftschurli.mods.arsmagicalegacy.client.model.item;

import at.minecraftschurli.mods.arsmagicalegacy.item.CrystalPhylacteryItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record CrystalPhylacteryRangeSelectItemModelProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<CrystalPhylacteryRangeSelectItemModelProperty> CODEC = MapCodec.unit(CrystalPhylacteryRangeSelectItemModelProperty::new);

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        return CrystalPhylacteryItem.getFill(itemStack);
    }

    @Override
    public MapCodec<CrystalPhylacteryRangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
