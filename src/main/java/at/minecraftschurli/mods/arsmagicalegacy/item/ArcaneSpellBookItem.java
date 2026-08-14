package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class ArcaneSpellBookItem extends SpellBookItem {
    public ArcaneSpellBookItem(Properties properties) {
        super(properties.fireResistant());
    }

    @Override
    public <T> T getOnSpell(ItemStack spell, Function<ItemStack, T> toRun) {
        spell.set(AMDataComponents.BONUS_MANA_MULTIPLIER, AMServerConfig.ARCANE_SPELL_BOOK_MANA_MULTIPLIER.get());
        spell.set(AMDataComponents.BONUS_STAT_MULTIPLIER, AMServerConfig.ARCANE_SPELL_BOOK_STAT_MULTIPLIER.get());
        T t = super.getOnSpell(spell, toRun);
        spell.remove(AMDataComponents.BONUS_MANA_MULTIPLIER);
        spell.remove(AMDataComponents.BONUS_STAT_MULTIPLIER);
        return t;
    }

    @Override
    public void runOnSpell(ItemStack spell, Consumer<ItemStack> toRun) {
        spell.set(AMDataComponents.BONUS_MANA_MULTIPLIER, AMServerConfig.ARCANE_SPELL_BOOK_MANA_MULTIPLIER.get());
        spell.set(AMDataComponents.BONUS_STAT_MULTIPLIER, AMServerConfig.ARCANE_SPELL_BOOK_STAT_MULTIPLIER.get());
        super.runOnSpell(spell, toRun);
        spell.remove(AMDataComponents.BONUS_MANA_MULTIPLIER);
        spell.remove(AMDataComponents.BONUS_STAT_MULTIPLIER);
    }
}
