package at.minecraftschurli.mods.arsmagicalegacy.container;

import at.minecraftschurli.mods.arsmagicalegacy.item.SpellBookItem;
import net.minecraft.world.item.ItemStack;

public class SpellBookContainer extends ItemStackContainer {
    public SpellBookContainer(ItemStack stack) {
        super(stack, SpellBookItem.TOTAL_SLOTS);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        SpellBookItem.updateSpell(stack);
    }
}
