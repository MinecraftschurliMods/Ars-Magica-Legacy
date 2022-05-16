package com.github.minecraftschurlimods.arsmagicalegacy.api;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IShrinkHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTabManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellTransformationManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Supplier;

@NonExtendable
public interface ArsMagicaAPI {
    String MOD_ID = "arsmagicalegacy";

    class Internal {
        private Internal() {}

        private static final Supplier<ArsMagicaAPI> LAZY_INSTANCE = Suppliers.memoize(() -> {
            Optional<ArsMagicaAPI> impl = ServiceLoader.load(Internal.class.getModule().getLayer(), ArsMagicaAPI.class).findFirst();
            return impl.orElseThrow(() -> LogManager.getLogger(MOD_ID).throwing(new IllegalStateException("Unable to find implementation for IArsMagicaAPI!")));
        });
    }

    /**
     * @return The API instance.
     */
    static ArsMagicaAPI get() {
        return Internal.LAZY_INSTANCE.get();
    }

    /**
     * @return The creative mode tab of the mod.
     */
    CreativeModeTab getCreativeModeTab();

    /**
     * @return The arcane compendium item stack.
     */
    ItemStack getBookStack();

    /**
     * @return The registry for skill points.
     */
    Registry<ISkillPoint> getSkillPointRegistry();

    /**
     * @return The registry for affinities.
     */
    Registry<IAffinity> getAffinityRegistry();

    /**
     * @return The registry for spell parts.
     */
    Registry<ISpellPart> getSpellPartRegistry();

    /**
     * @return The registry for abilities.
     */
    Registry<IAbility> getAbilityRegistry();

    /**
     * @return The skill manager instance.
     */
    ISkillManager getSkillManager();

    /**
     * @return The occulus tab manager instance.
     */
    IOcculusTabManager getOcculusTabManager();

    /**
     * @return The spell data manager instance.
     */
    ISpellDataManager getSpellDataManager();

    /**
     * @return The ability manager instance.
     */
    IAbilityManager getAbilityManager();

    /**
     * @return The spell data manager instance.
     */
    ISpellTransformationManager getSpellTransformationManager();

    /**
     * @return The skill helper instance.
     */
    @Unmodifiable
    ISkillHelper getSkillHelper();

    /**
     * @return The affinity helper instance.
     */
    @Unmodifiable
    IAffinityHelper getAffinityHelper();

    /**
     * @return The magic helper instance.
     */
    @Unmodifiable
    IMagicHelper getMagicHelper();

    /**
     * @return The mana helper instance.
     */
    @Unmodifiable
    IManaHelper getManaHelper();

    /**
     * @return The burnout helper instance.
     */
    @Unmodifiable
    IBurnoutHelper getBurnoutHelper();

    /**
     * @return The spell helper instance.
     */
    @Unmodifiable
    ISpellHelper getSpellHelper();

    /**
     * @return The rift helper instance.
     */
    @Unmodifiable
    IRiftHelper getRiftHelper();

    /**
     * @return The shrink helper instance.
     */
    @Unmodifiable
    IShrinkHelper getShrinkHelper();

    /**
     * @return The etherium helper instance.
     */
    @Unmodifiable
    IEtheriumHelper getEtheriumHelper();

    /**
     * Opens the occulus gui for the given player.
     *
     * @param player The player to open the gui for.
     */
    void openOcculusGui(Player player);

    /**
     * Opens the spell customization gui for the given player.
     *
     * @param level  The level to open the gui in.
     * @param player The player to open the gui for.
     * @param stack  The spell item stack to open the gui for.
     */
    void openSpellCustomizationGui(Level level, Player player, ItemStack stack);

    /**
     * Make an instance of ISpell.
     *
     * @param shapeGroups    The shape groups to use.
     * @param spellStack     The spell stack to use.
     * @param additionalData The additional data to use.
     * @return The spell instance.
     */
    ISpell makeSpell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData);

    /**
     * Make an instance of ISpell.
     *
     * @param spellStack     The spell stack to use.
     * @param shapeGroups    The shape groups to use.
     * @return The spell instance.
     */
    ISpell makeSpell(SpellStack spellStack, ShapeGroup... shapeGroups);
}
