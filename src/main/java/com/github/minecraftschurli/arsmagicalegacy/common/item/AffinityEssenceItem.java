package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AffinityEssenceItem extends Item implements IAffinityItem {
    public AffinityEssenceItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (allowdedIn(pCategory)) {
            ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
            for (IAffinity affinity : api.getAffinityRegistry()) {
                if (IAffinity.NONE.equals(affinity.getRegistryName())) continue;
                pItems.add(api.getAffinityHelper().getStackForAffinity(this, affinity.getRegistryName()));
            }
        }
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        ResourceLocation affinity = ArsMagicaAPI.get().getAffinityHelper().getAffinityForStack(pStack).getRegistryName();
        if (affinity == null) {
            affinity = IAffinity.NONE;
        }
        return Util.makeDescriptionId(super.getDescriptionId(pStack), affinity);
    }
}
