package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 * @author Minecraftschurli
 * @version 2021-07-12
 */
public final class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArsMagicaAPI.MOD_ID);
    public static final CreativeModeTab GROUP = new CreativeModeTab(ArsMagicaAPI.MOD_ID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };
    private Items() {}
}
