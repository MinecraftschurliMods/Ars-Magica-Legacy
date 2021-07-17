package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.Constants;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

/**
 * @author Minecraftschurli
 * @version 2021-07-12
 */
public final class Items {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final ItemGroup GROUP = new ItemGroup(Constants.MOD_ID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };
}
