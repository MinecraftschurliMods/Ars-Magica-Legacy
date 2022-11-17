package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * Marker interface for a spell item.
 */
public interface ISpellItem {
    String SPELL_KEY = ArsMagicaAPI.MOD_ID + ":spell";
    String SPELL_ICON_KEY = ArsMagicaAPI.MOD_ID + ":spell_icon";
    String SPELL_NAME_KEY = ArsMagicaAPI.MOD_ID + ":spell_name";
    Logger LOGGER = LogUtils.getLogger();

    /**
     * @param stack The stack to get the spell icon for.
     * @return An optional containing the spell icon id, or an empty optional if the given stack does not have a spell icon.
     */
    static Optional<ResourceLocation> getSpellIcon(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_ICON_KEY)).filter(s -> !s.isEmpty()).map(ResourceLocation::tryParse);
    }

    /**
     * Sets the given icon to the given stack.
     *
     * @param stack The stack to set the icon on.
     * @param icon  The icon to set.
     */
    static void setSpellIcon(ItemStack stack, ResourceLocation icon) {
        stack.getOrCreateTag().putString(SPELL_ICON_KEY, icon.toString());
    }

    /**
     * @param stack The stack to get the spell name for.
     * @return An optional containing the spell name, or an empty optional if the given stack does not have a spell name.
     */
    static Optional<Component> getSpellName(ItemStack stack) {
        return Optional.of(stack.getOrCreateTag().getString(SPELL_NAME_KEY))
                       .filter(s -> !s.isEmpty())
                       .map(pJson -> {
                           try {
                               return Component.Serializer.fromJson(pJson);
                           } catch (JsonParseException e) {
                               return null;
                           }
                       });
    }

    /**
     * Sets the given name to the given stack.
     *
     * @param stack The stack to set the name on.
     * @param name  The name component to set.
     */
    static void setSpellName(ItemStack stack, Component name) {
        stack.getOrCreateTag().putString(SPELL_NAME_KEY, Component.Serializer.toJson(name));
    }

    /**
     * Sets the given name to the given stack.
     *
     * @param stack The stack to set the name on.
     * @param name  The name to set.
     */
    static void setSpellName(ItemStack stack, String name) {
        setSpellName(stack, Component.nullToEmpty(name));
    }

    /**
     * Sets the given spell to the given stack.
     *
     * @param stack The stack to set the spell on.
     * @param spell The spell to set.
     */
    static void saveSpell(ItemStack stack, ISpell spell) {
        stack.getOrCreateTag().put(SPELL_KEY, ISpell.CODEC.encodeStart(NbtOps.INSTANCE, spell).get().mapRight(DataResult.PartialResult::message).ifRight(LOGGER::warn).left().orElse(new CompoundTag()));
    }

    /**
     * @param stack The stack to get the spell for.
     * @return An optional containing the spell, or an empty optional if the given stack does not have a spell.
     */
    static ISpell getSpell(ItemStack stack) {
        if (stack.isEmpty()) return ISpell.EMPTY;
        return ISpell.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTagElement(SPELL_KEY)).map(Pair::getFirst).get().mapRight(DataResult.PartialResult::message).ifRight(LOGGER::warn).left().orElse(ISpell.EMPTY);
    }

}
