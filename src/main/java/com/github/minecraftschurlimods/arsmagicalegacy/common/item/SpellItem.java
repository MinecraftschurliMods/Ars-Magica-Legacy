package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.item.SpellItemRenderProperties;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SpellItem extends Item implements ISpellItem {

    public SpellItem() {
        super(AMItems.ITEM_1);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Player player = null;
        if (EffectiveSide.get().isClient()) {
            player = ClientHelper.getLocalPlayer();
        }
        if (player == null) return;
        var api = ArsMagicaAPI.get();
        if (!api.getMagicHelper().knowsMagic(player)) {
            pTooltipComponents.add(Component.translatable(TranslationConstants.PREVENT_ITEM));
            return;
        }
        ISpell spell = api.getSpellHelper().getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) {
            pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_INVALID_DESCRIPTION));
            return;
        }
        pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_MANA_COST, spell.mana(player)));
        pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_BURNOUT, spell.burnout(player)));
        if (EffectiveSide.get().isClient() && ClientHelper.showAdvancedTooltips()) {
            List<ItemFilter> reagents = spell.reagents(player);
            if (reagents.isEmpty()) return;
            pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_REAGENTS));
            for (ItemFilter filter : reagents) {
                pTooltipComponents.add(Arrays.stream(filter.getMatchedStacks())
                        .map(e -> e.getHoverName().copy())
                        .collect(AMUtil.joiningComponents(" | ")));
            }
        } else {
            pTooltipComponents.add(Component.translatable(TranslationConstants.HOLD_SHIFT_FOR_DETAILS));
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        var api = ArsMagicaAPI.get();
        if (EffectiveSide.get().isClient()) {
            Player player = ClientHelper.getLocalPlayer();
            if (player == null || !api.getMagicHelper().knowsMagic(player))
                return Component.translatable(TranslationConstants.SPELL_UNKNOWN);
        }
        var helper = api.getSpellHelper();
        ISpell spell = helper.getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) return Component.translatable(TranslationConstants.SPELL_INVALID);
        return helper.getSpellName(pStack).orElseGet(() -> pStack.hasCustomHoverName() ? pStack.getHoverName() : Component.translatable(TranslationConstants.SPELL_UNNAMED));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return false;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new SpellItemRenderProperties());
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        ISpell spell = helper.getSpell(stack);
        if (!spell.isContinuous()) return;
        SpellCastResult result = spell.cast(entity, level, remainingUseDuration - 1, true, true);
        Holder<SoundEvent> sound = spell.primaryAffinity().loopSound();
        if (sound != null) {
            level.playSeededSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 0.1f, 1f, level.random.nextLong());
        }
        if (entity instanceof Player player) {
            if (result.isConsume()) {
                player.awardStat(AMStats.SPELL_CAST.value());
            }
            if (result.isFail()) {
                player.displayClientMessage(Component.translatable(TranslationConstants.SPELL_CAST + result.name().toLowerCase(), stack.getDisplayName()), true);
            }
        }
        helper.setSpell(stack, spell);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        var api = ArsMagicaAPI.get();
        if (pLivingEntity instanceof Player player) {
            if (!api.getMagicHelper().knowsMagic(player)) return;
            Optional<ResourceLocation> spellIcon = api.getSpellHelper().getSpellIcon(pStack);
            if (spellIcon.isPresent()) {
                castSpell(pLevel, player, player.getUsedItemHand(), pStack);
            } else {
                api.openSpellCustomizationGui(pLevel, player, pStack);
            }
        } else {
            castSpell(pLevel, pLivingEntity, pLivingEntity.getUsedItemHand(), pStack);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var api = ArsMagicaAPI.get();
        ItemStack heldItem = player.getItemInHand(hand);
        if (!api.getMagicHelper().knowsMagic(player)) {
            player.displayClientMessage(Component.translatable(TranslationConstants.PREVENT_ITEM), true);
            return InteractionResultHolder.fail(heldItem);
        }
        if (heldItem.getItem() instanceof SpellBookItem) {
            heldItem = SpellBookItem.getSelectedSpell(heldItem);
        }
        if (heldItem.hasTag() && api.getSpellHelper().getSpellIcon(heldItem).isPresent()) {
            castSpell(level, player, hand, heldItem);
            return InteractionResultHolder.success(heldItem);
        }
        api.openSpellCustomizationGui(level, player, heldItem);
        return InteractionResultHolder.success(heldItem);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var api = ArsMagicaAPI.get();
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;
        if (!api.getMagicHelper().knowsMagic(player)) return InteractionResult.FAIL;
        ItemStack item = context.getItemInHand();
        if (item.getItem() instanceof SpellBookItem) {
            item = SpellBookItem.getSelectedSpell(item);
        }
        Optional<ResourceLocation> spellIcon = api.getSpellHelper().getSpellIcon(item);
        if (spellIcon.isPresent()) {
            castSpell(context.getLevel(), context.getPlayer(), context.getHand(), item);
        } else {
            api.openSpellCustomizationGui(context.getLevel(), player, item);
        }
        return InteractionResult.SUCCESS;
    }

    private void castSpell(Level level, LivingEntity entity, InteractionHand hand, ItemStack stack) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        ISpell spell = helper.getSpell(stack);
        String name = ArsMagicaLegacy.LOGGER.isTraceEnabled() ? helper.getSpellName(stack).map(Component::getString).orElse("") : "";
        if (spell.isContinuous()) {
            ArsMagicaLegacy.LOGGER.trace("{} starts casting continuous spell {}", entity, name);
            entity.startUsingItem(hand);
        } else {
            ArsMagicaLegacy.LOGGER.trace("{} is casting instantaneous spell {}", entity, name);
            SpellCastResult result = spell.cast(entity, level, 0, true, true);
            ArsMagicaLegacy.LOGGER.trace("{} casted instantaneous spell {} with result {}", entity, name, result);
            Holder<SoundEvent> sound = spell.primaryAffinity().castSound();
            if (sound != null) {
                level.playSeededSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 0.1f, 1f, level.random.nextLong());
            }
            if (entity instanceof Player player) {
                if (result.isConsume()) {
                    player.awardStat(AMStats.SPELL_CAST.value());
                }
                if (result.isFail()) {
                    player.displayClientMessage(Component.translatable(TranslationConstants.SPELL_CAST + result.name().toLowerCase(), stack.getDisplayName()), true);
                }
            }
        }
        helper.setSpell(stack, spell);
    }
}
