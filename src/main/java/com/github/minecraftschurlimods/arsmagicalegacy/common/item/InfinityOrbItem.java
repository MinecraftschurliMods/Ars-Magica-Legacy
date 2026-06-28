package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InfinityOrbItem extends HolderDataComponentItem<SkillPoint> {
    public InfinityOrbItem(Properties properties) {
        super(properties, AMDataComponents.SKILL_POINT.get());
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!stack.has(AMDataComponents.SKILL_POINT)) return super.use(level, player, usedHand);
        ArsMagicaApi.magicHelper().addSkillPoint(player, stack.get(AMDataComponents.SKILL_POINT));
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        level.playSound(null, player, AMSounds.INFINITY_ORB.get(), SoundSource.PLAYERS, 1, 1);
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
    }
}
