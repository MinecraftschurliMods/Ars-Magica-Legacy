package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WintersGrasp;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WintersGraspItem extends Item {
    public WintersGraspItem() {
        super(AMItems.ITEM_1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            WintersGrasp entity = WintersGrasp.create(pLevel);
            entity.moveTo(pPlayer.getEyePosition().add(pPlayer.getLookAngle()));
            entity.setDeltaMovement(pPlayer.getLookAngle());
            entity.setXRot(pPlayer.getXRot());
            entity.setYRot(pPlayer.getYRot());
            entity.setOwner(pPlayer);
            entity.setStack(pPlayer.getItemInHand(pUsedHand));
            pLevel.addFreshEntity(entity);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
