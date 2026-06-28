package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SpellRecipeItem extends Item {
    public SpellRecipeItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("DataFlowIssue")
    public static int getPageCount(ItemStack stack) {
        if (!stack.has(AMDataComponents.SPELL)) return 0;
        Spell spell = stack.get(AMDataComponents.SPELL);
        return spell.isEmpty() ? 0 : spell.shapeGroups().size() + 3;
    }

    @Override
    public Component getName(ItemStack stack) {
        return stack.getOrDefault(AMDataComponents.SPELL, Spell.EMPTY)
            .name()
            .map(e -> e.getString().isEmpty() ? null : e)
            .orElse(super.getName(stack));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        return state.hasProperty(LecternBlock.HAS_BOOK) && LecternBlock.tryPlaceBook(context.getPlayer(), level, pos, state, context.getItemInHand()) ? InteractionResult.SUCCESS : super.useOn(context);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!ArsMagicaApi.magicHelper().knowsMagic(player)) {
            player.sendOverlayMessage(AMTranslations.PREVENT_ITEM);
            return InteractionResult.CONSUME.heldItemTransformedTo(stack);
        }
        if (level.isClientSide()) {
            AMClientUtil.setSpellRecipeScreen(stack, true, 0, null);
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
    }
}
