package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.item.SpellItemRenderProperties;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SpellItem extends Item implements ISpellItem {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab PREFAB_SPELLS_TAB = new CreativeModeTab(ArsMagicaAPI.MOD_ID + ".prefab_spells") {
        @Override
        public ItemStack makeIcon() {
            return AMItems.SPELL_PARCHMENT.map(ItemStack::new).orElse(ItemStack.EMPTY);
        }
    };

    public SpellItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Player player = null;
        if (EffectiveSide.get().isClient()) {
            player = ClientHelper.getLocalPlayer();
        }
        if (player == null) return;
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) {
            pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_UNKNOWN_DESCRIPTION));
            return;
        }
        ISpell spell = ISpellItem.getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) {
            pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_INVALID_DESCRIPTION));
            return;
        }
        pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_MANA_COST, spell.mana(player)));
        pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_BURNOUT, spell.burnout(player)));
        if (EffectiveSide.get().isClient() && ClientHelper.showAdvancedTooltips()) {
            List<Either<Ingredient, ItemStack>> reagents = spell.reagents(player);
            if (reagents.isEmpty()) return;
            pTooltipComponents.add(Component.translatable(TranslationConstants.SPELL_REAGENTS));
            for (Either<Ingredient, ItemStack> e : reagents) {
                pTooltipComponents.add(Arrays.stream(e.map(Ingredient::getItems, stack -> new ItemStack[]{stack}))
                        .map(stack1 -> stack1.getHoverName().copy())
                        .collect(AMUtil.joiningComponents(" | ")));
            }
        } else {
            pTooltipComponents.add(Component.translatable(TranslationConstants.HOLD_SHIFT_FOR_DETAILS));
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        if (EffectiveSide.get().isClient()) {
            Player player = ClientHelper.getLocalPlayer();
            if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player))
                return Component.translatable(TranslationConstants.SPELL_UNKNOWN);
        }
        ISpell spell = ISpellItem.getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) return Component.translatable(TranslationConstants.SPELL_INVALID);
        return ISpellItem.getSpellName(pStack).orElseGet(() -> pStack.hasCustomHoverName() ? pStack.getHoverName() : Component.translatable(TranslationConstants.SPELL_UNNAMED));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new SpellItemRenderProperties());
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
        if (entity.level.isClientSide()) return;
        ISpell spell = ISpellItem.getSpell(stack);
        if (!spell.isContinuous()) return;
        SpellCastResult result = spell.cast(entity, entity.level, count - 1, true, true);
        SoundEvent sound = ISpellItem.getSpell(stack).primaryAffinity().getLoopSound();
        if (sound != null) {
            entity.getLevel().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 0.1f, 1f);
        }
        if (entity instanceof Player player) {
            if (result.isConsume()) {
                player.awardStat(AMStats.SPELL_CAST.get());
            }
            if (result.isFail()) {
                player.displayClientMessage(Component.translatable(TranslationConstants.SPELL_CAST + result.name().toLowerCase(), stack.getDisplayName()), true);
            }
        }
        ISpellItem.saveSpell(stack, spell);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        var api = ArsMagicaAPI.get();
        if (pLivingEntity instanceof Player player) {
            if (!api.getMagicHelper().knowsMagic(player)) return;
            Optional<ResourceLocation> spellIcon = ISpellItem.getSpellIcon(pStack);
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
        if (!api.getMagicHelper().knowsMagic(player)) return InteractionResultHolder.fail(heldItem);
        if (heldItem.getItem() instanceof SpellBookItem) {
            heldItem = SpellBookItem.getSelectedSpell(heldItem);
        }
        if (heldItem.hasTag()) {
            assert heldItem.getTag() != null;
            String icon = heldItem.getTag().getString(SPELL_ICON_KEY);
            if (!icon.isEmpty()) {
                ResourceLocation iconLoc = ResourceLocation.tryParse(icon);
                if (iconLoc != null) {
                    castSpell(level, player, hand, heldItem);
                    return InteractionResultHolder.success(heldItem);
                }
            }
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
        Optional<ResourceLocation> spellIcon = ISpellItem.getSpellIcon(item);
        if (spellIcon.isPresent()) {
            castSpell(context.getLevel(), context.getPlayer(), context.getHand(), item);
        } else {
            api.openSpellCustomizationGui(context.getLevel(), player, item);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void fillItemCategory(CreativeModeTab category, NonNullList<ItemStack> items) {
        if (category == PREFAB_SPELLS_TAB) {
            ClientHelper.getRegistry(PrefabSpell.REGISTRY_KEY)
                        .stream()
                        .map(IPrefabSpell::makeSpell)
                        .forEach(items::add);
        }
    }

    private void castSpell(Level level, LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (level.isClientSide()) return;
        ISpell spell = ISpellItem.getSpell(stack);
        String name = LOGGER.isTraceEnabled() ? ISpellItem.getSpellName(stack).map(Component::getString).orElse("") : "";
        if (spell.isContinuous()) {
            LOGGER.trace("{} starts casting continuous spell {}", entity, name);
            entity.startUsingItem(hand);
        } else {
            LOGGER.trace("{} is casting instantaneous spell {}", entity, name);
            SpellCastResult result = spell.cast(entity, level, 0, true, true);
            LOGGER.trace("{} casted instantaneous spell {} with result {}", entity, name, result);
            SoundEvent sound = ISpellItem.getSpell(stack).primaryAffinity().getCastSound();
            if (sound != null) {
                entity.getLevel().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 0.1f, 1f);
            }
            if (entity instanceof Player player) {
                if (result.isConsume()) {
                    player.awardStat(AMStats.SPELL_CAST.get());
                }
                if (result.isFail()) {
                    player.displayClientMessage(Component.translatable(TranslationConstants.SPELL_CAST + result.name().toLowerCase(), stack.getDisplayName()), true);
                }
            }
        }
        ISpellItem.saveSpell(stack, spell);
    }
}
