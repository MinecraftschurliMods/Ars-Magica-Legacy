package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class SpellRecipeItem extends Item {
    private static final String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";

    public SpellRecipeItem() {
        super(new Properties().stacksTo(1));
    }

    /**
     * Sets the given spell to the given stack.
     *
     * @param stack The stack to set the spell on.
     * @param spell The spell to set.
     */
    public static void saveSpell(ItemStack stack, ISpell spell) {
        stack.getOrCreateTag().put(SPELL_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spell).get().mapRight(DataResult.PartialResult::message).ifRight(ArsMagicaLegacy.LOGGER::warn).left().orElse(new CompoundTag()));
    }

    /**
     * @param stack The stack to get the spell for.
     * @return An optional containing the spell, or an empty optional if the given stack does not have a spell.
     */
    public static ISpell getSpell(ItemStack stack) {
        if (stack.isEmpty()) return ISpell.EMPTY;
        return ISpell.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTagElement(SPELL_KEY)).map(Pair::getFirst).get().mapRight(DataResult.PartialResult::message).ifRight(ArsMagicaLegacy.LOGGER::warn).left().orElse(ISpell.EMPTY);
    }

    @Override
    public Component getName(ItemStack pStack) {
        var api = ArsMagicaAPI.get();
        if (EffectiveSide.get().isClient()) {
            Player player = ClientHelper.getLocalPlayer();
            if (player == null || !api.getMagicHelper().knowsMagic(player))
                return new TranslatableComponent(TranslationConstants.SPELL_RECIPE_UNKNOWN);
        }
        var helper = api.getSpellHelper();
        ISpell spell = helper.getSpell(pStack);
        if (spell.isEmpty() || !spell.isValid()) return new TranslatableComponent(TranslationConstants.SPELL_RECIPE_INVALID);
        return helper.getSpellName(pStack).orElse(super.getName(pStack));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var api = ArsMagicaAPI.get();
        ItemStack stack = player.getItemInHand(hand);
        if (!api.getMagicHelper().knowsMagic(player)) {
            player.displayClientMessage(new TranslatableComponent(TranslationConstants.SPELL_RECIPE_UNKNOWN_DESCRIPTION), true);
            return InteractionResultHolder.fail(stack);
        }
        ISpell spell = api.getSpellHelper().getSpell(stack);
        if (spell == null || !spell.isValid() || spell.isEmpty()) {
            player.displayClientMessage(new TranslatableComponent(TranslationConstants.SPELL_RECIPE_INVALID_DESCRIPTION), true);
            return InteractionResultHolder.fail(stack);
        }
        api.openSpellRecipeGui(level, player, stack);
        return super.use(level, player, hand);
    }
}
