package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class InfinityOrbItem extends Item implements ISkillPointItem {
    public InfinityOrbItem() {
        super(AMItems.ITEM_1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) return InteractionResultHolder.fail(stack);
        ArsMagicaAPI.get().getSkillHelper().addSkillPoint(player, getSkillPoint(stack));
        stack.shrink(1);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), AMSounds.GET_KNOWLEDGE_POINT.get(), SoundSource.PLAYERS, 1f, 1f);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(stack), getSkillPoint(stack));
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        ResourceLocation skillPoint = Objects.requireNonNull(ArsMagicaAPI.get().getSkillHelper().getSkillPointForStack(pStack)).getId();
        return Util.makeDescriptionId(super.getDescriptionId(pStack), skillPoint);
    }
}
