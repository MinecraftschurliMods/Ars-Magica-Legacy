package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EtheriumPlaceholderItem extends Item {
    public EtheriumPlaceholderItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        EtheriumType type = ArsMagicaAPI.get().getEtheriumHelper().getEtheriumType(pStack);
        return type != null ? Component.translatable(super.getDescriptionId(pStack) + "." + type.getId(ClientHelper.getRegistryAccess()).getPath()) : super.getName(pStack);
    }
}
