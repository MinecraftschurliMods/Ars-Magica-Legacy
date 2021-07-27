package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
//import vazkii.patchouli.api.PatchouliAPI;

/**
 * @author Minecraftschurli
 * @version 2021-06-18
 */
public class ArsMagicaAPIImpl implements ArsMagicaAPI.IArsMagicaAPI {

    @Override
    public CreativeModeTab getItemGroup() {
        return Items.GROUP;
    }

    @Override
    public ItemStack getBookStack() {
        return //ModList.get().isLoaded("patchouli")
                //? PatchouliAPI.get().getBookStack(new ResourceLocation(Constants.MOD_ID, "arcane_compendium"))
                //: // patchouli is not on 1.17.1 yet
        ItemStack.EMPTY;
    }
}
