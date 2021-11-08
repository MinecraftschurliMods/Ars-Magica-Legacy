package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurli.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurli.arsmagicalegacy.client.model.SpellItemRenderProperties;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMStats;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SpellItem extends Item implements ISpellItem {
    public static final ResourceLocation SPELL_ICON_ATLAS = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/atlas/spell_icons.png");

    public static final BiConsumer<MutableComponent, MutableComponent> COMPONENT_OR_JOINER = (component, component2) -> component.append(new TextComponent(" | ")).append(component2);

    public static final String SPELL_CAST_FAIL        = "message." + ArsMagicaAPI.MOD_ID + ".spell_cast.fail";
    public static final String UNNAMED_SPELL          = "item."    + ArsMagicaAPI.MOD_ID + ".spell.unnamed";
    public static final String UNKNOWN_ITEM           = "item."    + ArsMagicaAPI.MOD_ID + ".spell.unknown";
    public static final String UNKNOWN_ITEM_DESC      = "item."    + ArsMagicaAPI.MOD_ID + ".spell.unknown.description";
    public static final String INVALID_SPELL          = "item."    + ArsMagicaAPI.MOD_ID + ".spell.invalid";
    public static final String INVALID_SPELL_DESC     = "item."    + ArsMagicaAPI.MOD_ID + ".spell.invalid.description";
    public static final String MANA_COST              = "item."    + ArsMagicaAPI.MOD_ID + ".spell.mana_cost";
    public static final String BURNOUT                = "item."    + ArsMagicaAPI.MOD_ID + ".spell.burnout";
    public static final String REAGENTS               = "item."    + ArsMagicaAPI.MOD_ID + ".spell.reagents";
    public static final String HOLD_SHIFT_FOR_DETAILS = "tooltip." + ArsMagicaAPI.MOD_ID + ".hold_shift_for_details";

    private static final String SPELL_KEY      = ArsMagicaAPI.MOD_ID + ":spell";
    private static final String SPELL_ICON_KEY = ArsMagicaAPI.MOD_ID + ":spell_icon";
    private static final String SPELL_NAME_KEY = ArsMagicaAPI.MOD_ID + ":spell_name";

    private static final Logger LOGGER = LogManager.getLogger();

    public SpellItem() {
        super(new Item.Properties().stacksTo(1));
    }

    private void castSpell(Level level, LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (level.isClientSide()) return;
        Spell spell = getSpell(stack);
        if (spell.isContinuous()){
            LOGGER.trace("{} starts casting continuous spell {}", entity, getSpellName(stack));
            entity.startUsingItem(hand);
        } else {
            LOGGER.trace("{} is casting instantaneous spell {}", entity, getSpellName(stack));
            var result = spell.cast(entity, level, 0, true, true);
            LOGGER.trace("{} casted instantaneous spell {} with result {}", entity, getSpellName(stack), result);
            if (entity instanceof Player player) {
                if (result.isConsume()) {
                    player.awardStat(AMStats.SPELL_CAST);
                }
                if (result.isFail()) {
                    player.displayClientMessage(new TranslatableComponent(SPELL_CAST_FAIL, stack.getDisplayName()), true);
                }
            }
        }
        saveSpell(stack, spell);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player))
            return InteractionResultHolder.fail(heldItem);
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
        ArsMagicaAPI.get().openSpellCustomizationGui(level, player, heldItem);
        return InteractionResultHolder.success(heldItem);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) return;
            Optional<ResourceLocation> spellIcon = getSpellIcon(pStack);
            if (spellIcon.isPresent()) {
                castSpell(pLevel, player, player.getUsedItemHand(), pStack);
            } else {
                ArsMagicaAPI.get().openSpellCustomizationGui(pLevel, player, pStack);
            }
        } else {
            castSpell(pLevel, pLivingEntity, pLivingEntity.getUsedItemHand(), pStack);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) return InteractionResult.FAIL;
        ItemStack item = context.getItemInHand();
        Optional<ResourceLocation> spellIcon = getSpellIcon(item);
        if (spellIcon.isPresent()) {
            castSpell(context.getLevel(), context.getPlayer(), context.getHand(), item);
        } else {
            ArsMagicaAPI.get().openSpellCustomizationGui(context.getLevel(), player, item);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
        if (entity.level.isClientSide()) return;
        Spell spell = getSpell(stack);
        if (!spell.isContinuous()) return;
        SpellCastResult result = spell.cast(entity, entity.level, count - 1, true, true);
        if (entity instanceof Player player) {
            if (result.isConsume()) {
                player.awardStat(AMStats.SPELL_CAST);
            }
            if (result.isFail()) {
                player.displayClientMessage(new TranslatableComponent(SPELL_CAST_FAIL, stack.getDisplayName()), true);
            }
        }
        saveSpell(stack, spell);
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
    public Component getName(ItemStack pStack) {
        if (EffectiveSide.get().isClient()) {
            Player player = ClientHelper.getLocalPlayer();
            assert player != null;
            if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player))
                return new TranslatableComponent(UNKNOWN_ITEM);
        }
        if (getSpell(pStack).isEmpty())
            return new TranslatableComponent(INVALID_SPELL);
        return getSpellName(pStack).<Component>map(TextComponent::new).orElse(new TranslatableComponent(UNNAMED_SPELL));
    }

    @Override
    public void appendHoverText(ItemStack pStack,
                                @Nullable Level pLevel,
                                List<Component> pTooltipComponents,
                                TooltipFlag pIsAdvanced) {
        Player player = null;
        if (EffectiveSide.get().isClient()) {
            player = ClientHelper.getLocalPlayer();
        }
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) {
            pTooltipComponents.add(new TranslatableComponent(UNKNOWN_ITEM_DESC));
            return;
        }
        Spell spell = getSpell(pStack);
        if (spell.isEmpty()) {
            pTooltipComponents.add(new TranslatableComponent(INVALID_SPELL_DESC));
            return;
        }
        pTooltipComponents.add(new TranslatableComponent(MANA_COST, spell.manaCost(player)));
        pTooltipComponents.add(new TranslatableComponent(BURNOUT, spell.burnout()));
        if (player == null) return;
        if (EffectiveSide.get().isClient() && ClientHelper.showAdvancedTooltips()) {
            List<Either<Ingredient, ItemStack>> reagents = spell.reagents();
            if (reagents.isEmpty()) return;
            pTooltipComponents.add(new TranslatableComponent(REAGENTS));
            reagents.forEach(e -> pTooltipComponents.add(
                    Arrays.stream(e.map(Ingredient::getItems, stack -> new ItemStack[]{stack}))
                          .map(ItemStack::getHoverName)
                          .map(Component::copy)
                          .collect(TextComponent.EMPTY::copy, COMPONENT_OR_JOINER, COMPONENT_OR_JOINER)));
        } else {
            pTooltipComponents.add(new TranslatableComponent(HOLD_SHIFT_FOR_DETAILS));
        }
    }

    public static Optional<ResourceLocation> getSpellIcon(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_ICON_KEY))
                       .filter(s -> !s.isEmpty())
                       .map(ResourceLocation::tryParse);
    }

    public static void setSpellIcon(ItemStack stack, ResourceLocation icon) {
        stack.getOrCreateTag().putString(SPELL_ICON_KEY, icon.toString());
    }

    public static Optional<String> getSpellName(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_NAME_KEY)).filter(s -> !s.isEmpty());
    }

    public static void setSpellName(ItemStack stack, String name) {
        stack.getOrCreateTag().putString(SPELL_NAME_KEY, name);
    }

    public static void saveSpell(ItemStack stack, Spell spell) {
        stack.getOrCreateTag().put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, spell)
                                                         .get()
                                                         .mapRight(DataResult.PartialResult::message)
                                                         .ifRight(LOGGER::warn)
                                                         .left()
                                                         .orElse(new CompoundTag()));
    }

    public static Spell getSpell(ItemStack stack) {
        return Spell.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTagElement(SPELL_KEY))
                          .get()
                          .mapLeft(Pair::getFirst)
                          .mapRight(DataResult.PartialResult::message)
                          .ifRight(LOGGER::warn)
                          .left()
                          .orElse(Spell.EMPTY);
    }
}
