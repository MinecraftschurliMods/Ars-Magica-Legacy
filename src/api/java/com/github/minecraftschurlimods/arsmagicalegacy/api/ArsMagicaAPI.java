package com.github.minecraftschurlimods.arsmagicalegacy.api;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

@NonExtendable
public interface ArsMagicaAPI {
    String MOD_ID = "arsmagicalegacy";

    class Internal {
        private Internal() {}

        private static final Lazy<ArsMagicaAPI> LAZY_INSTANCE = Lazy.concurrentOf(() -> {
            Optional<ArsMagicaAPI> impl = ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaAPI.class).findFirst();
            if (!FMLEnvironment.production) {
                return impl.orElseThrow(() -> LogManager.getLogger(MOD_ID).throwing(new IllegalStateException("Unable to find implementation for IArsMagicaAPI!")));
            }
            return impl.orElseGet(() -> {
                LogManager.getLogger(MOD_ID).error("Unable to find implementation for IArsMagicaAPI!");
                return null;
            });
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
    IForgeRegistry<ISkillPoint> getSkillPointRegistry();

    /**
     * @return The registry for affinities.
     */
    IForgeRegistry<IAffinity> getAffinityRegistry();

    /**
     * @return The registry for spell parts.
     */
    IForgeRegistry<ISpellPart> getSpellPartRegistry();

    /**
     * @return The registry for abilities.
     */
    IForgeRegistry<IAbility> getAbilityRegistry();

    /**
     * @return The registry for contingency types.
     */
    IForgeRegistry<ContingencyType> getContingencyTypeRegistry();

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
     * @return The etherium helper instance.
     */
    @Unmodifiable
    IEtheriumHelper getEtheriumHelper();

    /**
     * @return The contingency helper instance.
     */
    @Unmodifiable
    IContingencyHelper getContingencyHelper();

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
