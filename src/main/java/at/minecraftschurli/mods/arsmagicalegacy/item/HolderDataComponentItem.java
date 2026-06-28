package at.minecraftschurli.mods.arsmagicalegacy.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HolderDataComponentItem<T> extends Item {
    private final DataComponentType<Holder<T>> dataComponent;

    public HolderDataComponentItem(Properties properties, DataComponentType<Holder<T>> dataComponent) {
        super(properties);
        this.dataComponent = dataComponent;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Component getName(ItemStack stack) {
        Holder<T> holder = stack.get(dataComponent);
        return holder == null ? super.getName(stack) : Component.translatable(Util.makeDescriptionId(getDescriptionId(), holder.getKey().identifier()));
    }
}
