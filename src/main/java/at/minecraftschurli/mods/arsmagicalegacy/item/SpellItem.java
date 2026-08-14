package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.UseEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class SpellItem extends Item {
    public static final UseEffects SPELL_MOTION_USE_EFFECTS = new UseEffects(false, true, 1);

    public SpellItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        Spell spell = stack.get(AMDataComponents.SPELL);
        if (spell == null) return InteractionResult.FAIL;
        if (spell.name().isEmpty() || spell.icon().isEmpty()) {
            if (level.isClientSide()) {
                AMClientUtil.setSpellCustomizationScreen(spell, usedHand);
            }
            return InteractionResult.CONSUME.heldItemTransformedTo(stack);
        }
        if (spell.isContinuous()) {
            player.startUsingItem(usedHand);
            return InteractionResult.CONSUME.heldItemTransformedTo(stack);
        }
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(spell, level, player, true, true, getManaMultiplier(stack), getStatMultiplier(stack));
        if (result.isSuccess()) {
            onSuccess(level, player, stack, result.getSpell());
            return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
        } else {
            onFailure(player, result.getMessage());
            return InteractionResult.FAIL;
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        Spell spell = stack.get(AMDataComponents.SPELL);
        if (spell == null || !spell.isContinuous()) return;
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(spell, level, livingEntity, true, true, getManaMultiplier(stack), getStatMultiplier(stack));
        if (result.isSuccess()) {
            onSuccess(level, livingEntity, stack, result.getSpell());
        } else if (livingEntity instanceof Player player) {
            onFailure(player, result.getMessage());
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return stack.getOrDefault(AMDataComponents.SPELL, Spell.EMPTY)
            .name()
            .map(e -> e.getString().isEmpty() ? null : e)
            .orElse(super.getName(stack));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        Spell spell = stack.get(AMDataComponents.SPELL);
        Level level = context.level();
        builder.accept((level == null || spell == null || spell.isMalformed() ? AMTranslations.SPELL_INVALID.copy() : Component.translatable(AMTranslations.SPELL_MANA_COST_KEY, spell.getManaCost(level.registryAccess()))).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean canDestroyBlock(ItemStack itemStack, BlockState state, Level level, BlockPos pos, LivingEntity user) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    private double getManaMultiplier(ItemStack stack) {
        return stack.getOrDefault(AMDataComponents.BONUS_MANA_MULTIPLIER, 1.);
    }

    private double getStatMultiplier(ItemStack stack) {
        return stack.getOrDefault(AMDataComponents.BONUS_STAT_MULTIPLIER, 1.);
    }

    private void onSuccess(Level level, LivingEntity entity, ItemStack stack, Spell spell) {
        stack.set(AMDataComponents.SPELL, spell);
        Affinity affinity = AMRegistries.affinities(level.registryAccess()).getValue(spell.grammar().primaryAffinity(level.registryAccess()));
        if (affinity == null) return;
        Optional<Holder<SoundEvent>> optional = spell.isContinuous() ? affinity.loopSound() : affinity.castSound();
        optional.ifPresent(sound -> level.playSeededSound(null, entity, sound, SoundSource.PLAYERS, 1f, 1f, level.getRandom().nextLong()));
    }

    private void onFailure(Player player, @Nullable Component message) {
        if (message != null) {
            player.sendOverlayMessage(message);
        }
    }
}
