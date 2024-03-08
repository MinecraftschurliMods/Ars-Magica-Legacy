package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;

public class AffinityTomeItem extends Item implements IAffinityItem {
    public AffinityTomeItem(Properties properties) {
        super(properties);
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
            if (a.getId() == Affinity.NONE) continue;
            if (affinity == a) {
                AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(pPlayer, affinity, shift, false);
                if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
                    helper.increaseAffinityDepth(pPlayer, event.affinity, event.shift);
                    NeoForge.EVENT_BUS.post(new AffinityChangingEvent.Post(pPlayer, affinity, (float) helper.getAffinityDepth(pPlayer, event.affinity), false));
                }
            } else {
                AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(pPlayer, affinity, -shift, false);
                if (!NeoForge.EVENT_BUS.post(event).isCanceled()) {
                    helper.decreaseAffinityDepth(pPlayer, event.affinity, -event.shift);
                    NeoForge.EVENT_BUS.post(new AffinityChangingEvent.Post(pPlayer, affinity, (float) helper.getAffinityDepth(pPlayer, event.affinity), false));
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
