package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * @author Minecraftschurli
 * @version 2021-06-18
 */
public class ArsMagicaAPIImpl implements ArsMagicaAPI.IArsMagicaAPI {

    @Override
    public ItemGroup getItemGroup() {
        return Items.GROUP;
    }

    @Override
    public ItemStack getBookStack() {
        return ModList.get().isLoaded("patchouli")
                ? PatchouliAPI.get().getBookStack(new ResourceLocation(Constants.MOD_ID, "arcane_compendium"))
                : ItemStack.EMPTY;
    }
}
