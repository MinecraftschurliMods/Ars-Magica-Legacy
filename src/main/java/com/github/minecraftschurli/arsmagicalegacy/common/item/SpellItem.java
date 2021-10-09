package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpellItem extends Item implements ISpellItem {
    private static final Logger LOGGER                  = LogManager.getLogger();
    private static final String SPELL_KEY               = "Spell";
    private static final String CURRENT_SHAPE_GROUP_KEY = "CurrentShapeGroup";
    private static final String SPELL_CAST_FAIL         = "message.%s.spell_cast.fail".formatted(ArsMagicaAPI.MOD_ID)
                                                                                      .intern();

    public SpellItem(Properties pProperties) {
        super(pProperties);
    }

    private void openIconPickGui(Level level, Player player, ItemStack heldItem) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (ArsMagicaAPI.get()
                        .getMagicHelper()
                        .getLevel(player) < 0 && !player.isCreative()) {
            return InteractionResultHolder.fail(heldItem);
        }
        if (heldItem.hasTag()) {
            String icon = heldItem.getTag().getString("Icon");
            if (!icon.isEmpty()) {
                ResourceLocation iconLoc = ResourceLocation.tryParse(icon);
                if (iconLoc != null) {
                    player.startUsingItem(hand);
                    return InteractionResultHolder.success(heldItem);
                }
            }
        }
        openIconPickGui(level, player, heldItem);
        return InteractionResultHolder.success(heldItem);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        if (ArsMagicaAPI.get().getMagicHelper().getLevel(player) < 0 && !player.isCreative()) {
            return InteractionResult.FAIL;
        }
        player.startUsingItem(context.getHand());
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
        if (entity.level.isClientSide()) {
            return;
        }
        Spell spell = getSpell(stack);
        byte currentGroup = stack.getOrCreateTag()
                                 .getByte(CURRENT_SHAPE_GROUP_KEY);
        if (spell.isContinuous(currentGroup)) {
            var result = spell.cast(stack, entity, entity.level, true, true, count - 1);
            if (result.isFail() && entity instanceof Player player) {
                player.displayClientMessage(new TranslatableComponent(SPELL_CAST_FAIL, stack.getDisplayName()), true);
            }
            //SpellUtil.applyStage(stack, player, null, player.getPosX(), player.getPosY(), player.getPosZ(),
            // Direction.UP, player.world, true, true, count - 1);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (level.isClientSide()) {
            return stack;
        }
        if (!stack.hasTag()) {
            return stack;
        }
        Spell spell = getSpell(stack);
        byte currentGroup = stack.getOrCreateTag()
                                 .getByte(CURRENT_SHAPE_GROUP_KEY);
        if (!spell.isContinuous(currentGroup)) {
            var result = spell.cast(stack, entity, entity.level, true, true, 0);
            if (result.isFail() && entity instanceof Player player) {
                player.displayClientMessage(new TranslatableComponent(SPELL_CAST_FAIL, stack.getDisplayName()), true);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    private Spell getSpell(ItemStack stack) {
        return Spell.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTagElement(SPELL_KEY))
                          .get()
                          .mapLeft(Pair::getFirst)
                          .mapRight(DataResult.PartialResult::message)
                          .ifRight(LOGGER::warn)
                          .left()
                          .orElse(Spell.EMPTY);
    }
}
