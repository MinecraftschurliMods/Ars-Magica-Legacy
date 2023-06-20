package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SpellRecipeItem extends Item {
    public SpellRecipeItem() {
        super(AMItems.ITEM_1);
    }

    public static boolean placeInLectern(ItemStack stack, Player player, Level level, BlockPos pos) {
        return ArsMagicaAPI.get().getSpellHelper().getSpell(stack) != ISpell.EMPTY && level.getBlockEntity(pos) instanceof LecternBlockEntity && LecternBlock.tryPlaceBook(player, level, pos, level.getBlockState(pos), stack);
    }

    public static void takeFromLectern(Player player, Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof LecternBlockEntity lectern && state.getValue(LecternBlock.HAS_BOOK)) {
            ItemStack stack = lectern.getBook();
            lectern.setBook(ItemStack.EMPTY);
            LecternBlock.resetBookState(player, level, pos, state, false);
            if (!player.getInventory().add(stack)) {
                player.drop(stack, false);
            }
        }
    }

    public static int getPageCount(ItemStack stack) {
        ISpell spell = ArsMagicaAPI.get().getSpellHelper().getSpell(stack);
        if (spell.isEmpty()) return 0;
        int result = 0;
        result += spell.shapeGroups().size();
        if (!AMUtil.reagentsToIngredients(spell.reagents(Objects.requireNonNull(ClientHelper.getLocalPlayer()))).isEmpty()) {
            result++;
        }
        return result + 3;
    }

    @Override
    public Component getName(ItemStack pStack) {
        var api = ArsMagicaAPI.get();
        if (EffectiveSide.get().isClient()) {
            Player player = ClientHelper.getLocalPlayer();
            if (player == null || !api.getMagicHelper().knowsMagic(player))
                return Component.translatable(TranslationConstants.SPELL_RECIPE_UNKNOWN);
        }
        var helper = api.getSpellHelper();
        ISpell spell = helper.getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) return Component.translatable(TranslationConstants.SPELL_RECIPE_INVALID);
        return helper.getSpellName(pStack).orElse(super.getName(pStack));
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        return placeInLectern(pContext.getItemInHand(), Objects.requireNonNull(pContext.getPlayer()), level, pContext.getClickedPos()) ? InteractionResult.sidedSuccess(level.isClientSide()) : super.useOn(pContext);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var api = ArsMagicaAPI.get();
        ItemStack stack = player.getItemInHand(hand);
        if (!api.getMagicHelper().knowsMagic(player)) {
            player.displayClientMessage(Component.translatable(TranslationConstants.PREVENT_ITEM), true);
            return InteractionResultHolder.fail(stack);
        }
        ISpell spell = api.getSpellHelper().getSpell(stack);
        if (!spell.isValid() || spell.isEmpty()) {
            player.displayClientMessage(Component.translatable(TranslationConstants.SPELL_RECIPE_INVALID_DESCRIPTION), true);
            return InteractionResultHolder.fail(stack);
        }
        api.openSpellRecipeGui(level, player, stack);
        return super.use(level, player, hand);
    }
}
