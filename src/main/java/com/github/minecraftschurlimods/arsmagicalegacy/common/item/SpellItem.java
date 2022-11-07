package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.item.SpellItemRenderProperties;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SpellItem extends Item implements ISpellItem {
    public static final String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";
    private static final String SPELL_ICON_KEY = ArsMagicaAPI.MOD_ID + ":spell_icon";
    private static final String SPELL_NAME_KEY = ArsMagicaAPI.MOD_ID + ":spell_name";
    private static final Logger LOGGER = LogManager.getLogger();

    public SpellItem() {
        super(new Item.Properties().stacksTo(1));
    }

    /**
     * @param stack The stack to get the spell icon for.
     * @return An optional containing the spell icon id, or an empty optional if the given stack does not have a spell icon.
     */
    public static Optional<ResourceLocation> getSpellIcon(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_ICON_KEY)).filter(s -> !s.isEmpty()).map(ResourceLocation::tryParse);
    }

    /**
     * Sets the given icon to the given stack.
     *
     * @param stack The stack to set the icon on.
     * @param icon  The icon to set.
     */
    public static void setSpellIcon(ItemStack stack, ResourceLocation icon) {
        stack.getOrCreateTag().putString(SPELL_ICON_KEY, icon.toString());
    }

    /**
     * @param stack The stack to get the spell name for.
     * @return An optional containing the spell name, or an empty optional if the given stack does not have a spell name.
     */
    public static Optional<String> getSpellName(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_NAME_KEY)).filter(s -> !s.isEmpty());
    }

    /**
     * Sets the given name to the given stack.
     *
     * @param stack The stack to set the name on.
     * @param name  The name to set.
     */
    public static void setSpellName(ItemStack stack, String name) {
        stack.getOrCreateTag().putString(SPELL_NAME_KEY, name);
    }

    /**
     * Sets the given spell to the given stack.
     *
     * @param stack The stack to set the spell on.
     * @param spell The spell to set.
     */
    public static void saveSpell(ItemStack stack, ISpell spell) {
        stack.getOrCreateTag().put(SPELL_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spell).get().mapRight(DataResult.PartialResult::message).ifRight(LOGGER::warn).left().orElse(new CompoundTag()));
    }

    /**
     * @param stack The stack to get the spell for.
     * @return An optional containing the spell, or an empty optional if the given stack does not have a spell.
     */
    public static ISpell getSpell(ItemStack stack) {
        if (stack.isEmpty()) return ISpell.EMPTY;
        return ISpell.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTagElement(SPELL_KEY)).map(Pair::getFirst).get().mapRight(DataResult.PartialResult::message).ifRight(SpellItem.LOGGER::warn).left().orElse(ISpell.EMPTY);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Player player = null;
        if (EffectiveSide.get().isClient()) {
            player = ClientHelper.getLocalPlayer();
        }
        if (player == null) return;
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) {
            pTooltipComponents.add(new TranslatableComponent(TranslationConstants.SPELL_UNKNOWN_DESCRIPTION));
            return;
        }
        ISpell spell = getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) {
            pTooltipComponents.add(new TranslatableComponent(TranslationConstants.SPELL_INVALID_DESCRIPTION));
            return;
        }
        pTooltipComponents.add(new TranslatableComponent(TranslationConstants.SPELL_MANA_COST, spell.mana(player)));
        pTooltipComponents.add(new TranslatableComponent(TranslationConstants.SPELL_BURNOUT, spell.burnout(player)));
        if (EffectiveSide.get().isClient() && ClientHelper.showAdvancedTooltips()) {
            List<Either<Ingredient, ItemStack>> reagents = spell.reagents(player);
            if (reagents.isEmpty()) return;
            pTooltipComponents.add(new TranslatableComponent(TranslationConstants.SPELL_REAGENTS));
            for (Either<Ingredient, ItemStack> e : reagents) {
                pTooltipComponents.add(Arrays.stream(e.map(Ingredient::getItems, stack -> new ItemStack[]{stack}))
                        .map(stack1 -> stack1.getHoverName().copy())
                        .collect(AMUtil.joiningComponents(" | ")));
            }
        } else {
            pTooltipComponents.add(new TranslatableComponent(TranslationConstants.HOLD_SHIFT_FOR_DETAILS));
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        if (EffectiveSide.get().isClient()) {
            Player player = ClientHelper.getLocalPlayer();
            if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player))
                return new TranslatableComponent(TranslationConstants.SPELL_UNKNOWN);
        }
        ISpell spell = getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) return new TranslatableComponent(TranslationConstants.SPELL_INVALID);
        return getSpellName(pStack).<Component>map(TextComponent::new).orElseGet(() -> pStack.hasCustomHoverName() ? pStack.getHoverName() : new TranslatableComponent(TranslationConstants.SPELL_UNNAMED));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new SpellItemRenderProperties());
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
        if (entity.level.isClientSide()) return;
        ISpell spell = getSpell(stack);
        if (!spell.isContinuous()) return;
        SpellCastResult result = spell.cast(entity, entity.level, count - 1, true, true);
        SoundEvent sound = getSpell(stack).primaryAffinity().getLoopSound();
        if (sound != null) {
            entity.getLevel().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 0.1f, 1f);
        }
        if (entity instanceof Player player) {
            if (result.isConsume()) {
                player.awardStat(AMStats.SPELL_CAST);
            }
            if (result.isFail()) {
                player.displayClientMessage(new TranslatableComponent(TranslationConstants.SPELL_CAST + result.name().toLowerCase(), stack.getDisplayName()), true);
            }
        }
        saveSpell(stack, spell);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        var api = ArsMagicaAPI.get();
        if (pLivingEntity instanceof Player player) {
            if (!api.getMagicHelper().knowsMagic(player)) return;
            Optional<ResourceLocation> spellIcon = getSpellIcon(pStack);
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
        Optional<ResourceLocation> spellIcon = getSpellIcon(item);
        if (spellIcon.isPresent()) {
            castSpell(context.getLevel(), context.getPlayer(), context.getHand(), item);
        } else {
            api.openSpellCustomizationGui(context.getLevel(), player, item);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void fillItemCategory(CreativeModeTab category, NonNullList<ItemStack> items) {
        if (category == PrefabSpellManager.ITEM_CATEGORY) {
            PrefabSpellManager.instance()
                    .values()
                    .stream()
                    .sorted()
                    .map(IPrefabSpell::makeSpell)
                    .forEach(items::add);
        }
    }

    private void castSpell(Level level, LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (level.isClientSide()) return;
        ISpell spell = getSpell(stack);
        if (spell.isContinuous()) {
            LOGGER.trace("{} starts casting continuous spell {}", entity, getSpellName(stack));
            entity.startUsingItem(hand);
        } else {
            LOGGER.trace("{} is casting instantaneous spell {}", entity, getSpellName(stack));
            SpellCastResult result = spell.cast(entity, level, 0, true, true);
            LOGGER.trace("{} casted instantaneous spell {} with result {}", entity, getSpellName(stack), result);
            SoundEvent sound = getSpell(stack).primaryAffinity().getCastSound();
            if (sound != null) {
                entity.getLevel().playSound(null, entity.getX(), entity.getY(), entity.getZ(), sound, SoundSource.PLAYERS, 0.1f, 1f);
            }
            if (entity instanceof Player player) {
                if (result.isConsume()) {
                    player.awardStat(AMStats.SPELL_CAST);
                }
                if (result.isFail()) {
                    player.displayClientMessage(new TranslatableComponent(TranslationConstants.SPELL_CAST + result.name().toLowerCase(), stack.getDisplayName()), true);
                }
            }
        }
        saveSpell(stack, spell);
    }
}
