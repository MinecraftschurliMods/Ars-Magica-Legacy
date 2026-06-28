package at.minecraftschurli.mods.arsmagicalegacy.client.model.item;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record CrystalWrenchActiveItemModelProperty() implements ConditionalItemModelProperty {
    public static final MapCodec<CrystalWrenchActiveItemModelProperty> CODEC = MapCodec.unit(CrystalWrenchActiveItemModelProperty::new);

    @Override
    public MapCodec<CrystalWrenchActiveItemModelProperty> type() {
        return CODEC;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        return itemStack.has(AMDataComponents.STORED_POSITIONS) && !itemStack.get(AMDataComponents.STORED_POSITIONS).isEmpty();
    }
}
