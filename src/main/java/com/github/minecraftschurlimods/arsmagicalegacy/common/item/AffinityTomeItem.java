package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AffinityTomeItem extends Item implements IAffinityItem {
    public AffinityTomeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (allowedIn(pCategory)) {
            var api = ArsMagicaAPI.get();
            for (Affinity affinity : api.getAffinityRegistry()) {
                if (Affinity.NONE.equals(affinity.getId())) continue;
                pItems.add(api.getAffinityHelper().getStackForAffinity(this, affinity.getId()));
            }
        }
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        ResourceLocation affinity = ArsMagicaAPI.get().getAffinityHelper().getAffinityForStack(pStack).getId();
        return Util.makeDescriptionId(super.getDescriptionId(pStack), affinity);
    }
}
