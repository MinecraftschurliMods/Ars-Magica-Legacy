package at.minecraftschurli.mods.arsmagicalegacy.client.model.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record EtheriumTypeItemTintSource() implements ItemTintSource {
    public static final MapCodec<EtheriumTypeItemTintSource> CODEC = MapCodec.unit(EtheriumTypeItemTintSource::new);

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        if (itemStack.has(AMDataComponents.ETHERIUM_TYPE)) {
            Holder<EtheriumType> type = itemStack.get(AMDataComponents.ETHERIUM_TYPE);
            assert type != null;
            return 0xff000000 | type.value().color();
        }
        return -1;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
