package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

public class AffinityTomeItem extends Item implements IAffinityItem {
    public AffinityTomeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (allowedIn(pCategory)) {
            var api = ArsMagicaAPI.get();
            for (Affinity affinity : api.getAffinityRegistry()) {
                pItems.add(api.getAffinityHelper().getStackForAffinity(this, affinity.getId()));
            }
        }
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        ResourceLocation affinity = ArsMagicaAPI.get().getAffinityHelper().getAffinityForStack(pStack).getId();
        return Util.makeDescriptionId(super.getDescriptionId(pStack), affinity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        //if (pLevel.isClientSide()) return InteractionResultHolder.fail(stack);
        var api = ArsMagicaAPI.get();
        if (!api.getMagicHelper().knowsMagic(pPlayer)) {
            pPlayer.displayClientMessage(Component.translatable(TranslationConstants.PREVENT_ITEM), true);
            return InteractionResultHolder.fail(stack);
        }
        var helper = api.getAffinityHelper();
        Affinity affinity = helper.getAffinityForStack(stack);
        float shift = Config.SERVER.AFFINITY_TOME_SHIFT.get().floatValue();
        for (Affinity a : api.getAffinityRegistry()) {
            if (affinity == a && a.getId() != Affinity.NONE) {
                AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(pPlayer, affinity, (float) helper.getAffinityDepth(pPlayer, a) + shift, false);
                if (!MinecraftForge.EVENT_BUS.post(event)) {
                    helper.addAffinityDepth(pPlayer, a, shift);
                    MinecraftForge.EVENT_BUS.post(new AffinityChangingEvent.Post(pPlayer, affinity, (float) helper.getAffinityDepth(pPlayer, a), false));
                }
            } else {
                AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(pPlayer, affinity, (float) helper.getAffinityDepth(pPlayer, a) - shift, false);
                if (!MinecraftForge.EVENT_BUS.post(event)) {
                    helper.subtractAffinityDepth(pPlayer, a, shift);
                    MinecraftForge.EVENT_BUS.post(new AffinityChangingEvent.Post(pPlayer, affinity, (float) helper.getAffinityDepth(pPlayer, a), false));
                }
            }
        }
        helper.updateLock(pPlayer);
        if (!pPlayer.isCreative()) {
            stack.shrink(1);
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean hasNoneVariant() {
        return true;
    }
}
