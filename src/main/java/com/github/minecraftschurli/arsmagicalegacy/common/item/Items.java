package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import static com.github.minecraftschurli.arsmagicalegacy.common.Registries.ITEMS;

@NonExtendable
public interface Items {
    CreativeModeTab GROUP = new CreativeModeTab(ArsMagicaAPI.MOD_ID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };

    static void setup() {
        ITEMS.getEntries()
                .stream()
                .flatMap(RegistryObject::stream)
                .filter(AMSpawnEggItem.class::isInstance)
                .map(AMSpawnEggItem.class::cast)
                .forEach(AMSpawnEggItem::init);
    }
}
