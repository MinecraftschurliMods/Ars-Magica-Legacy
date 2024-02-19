package com.github.minecraftschurlimods.arsmagicalegacy.api;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredientType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

@NonExtendable
public interface ArsMagicaAPI {
    String MOD_ID = "arsmagicalegacy";
    ResourceLocation PREFAB_SPELLS_CREATIVE_TAB = new ResourceLocation(MOD_ID, "prefab_spells");
    ResourceLocation MAIN_CREATIVE_TAB = new ResourceLocation(MOD_ID, "main");

    @Internal
    final class InstanceHolder {
        private InstanceHolder() {}

        private static final Lazy<ArsMagicaAPI> LAZY_INSTANCE = Lazy.concurrentOf(() -> {
            Optional<ArsMagicaAPI> impl = ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaAPI.class).findFirst();
            if (!FMLEnvironment.production) {
                return impl.orElseThrow(() -> {
                    IllegalStateException exception = new IllegalStateException("Unable to find implementation for IArsMagicaAPI!");
                    LoggerFactory.getLogger(MOD_ID).error(exception.getMessage(), exception);
                    return exception;
                });
            }
            return impl.orElseGet(() -> {
                LoggerFactory.getLogger(MOD_ID).error("Unable to find implementation for IArsMagicaAPI!");
                return null;
            });
        });
    }

    /**
     * @return The API instance.
     */
    static ArsMagicaAPI get() {
        return InstanceHolder.LAZY_INSTANCE.get();
    }

    /**
     * @return The arcane compendium item stack.
     */
    ItemStack getBookStack();

    /**
     * @return The registry for skill points.
     */
    IForgeRegistry<SkillPoint> getSkillPointRegistry();

    /**
     * @return The registry for affinities.
     */
    IForgeRegistry<Affinity> getAffinityRegistry();

    /**
     * @return The registry for spell parts.
     */
    IForgeRegistry<ISpellPart> getSpellPartRegistry();

    /**
     * @return The registry for contingency types.
     */
    IForgeRegistry<ContingencyType> getContingencyTypeRegistry();

    @Experimental
    IForgeRegistry<Codec<? extends RitualTrigger>> getRitualTriggerTypeRegistry();

    @Experimental
    IForgeRegistry<Codec<? extends RitualRequirement>> getRitualRequirementTypeRegistry();

    @Experimental
    IForgeRegistry<Codec<? extends RitualEffect>> getRitualEffectTypeRegistry();

    IForgeRegistry<SpellIngredientType<?>> getSpellIngredientTypeRegistry();

    /**
     * @return The spell data manager instance.
     */
    ISpellDataManager getSpellDataManager();

    /**
     * @return The skill helper instance.
     */
    ISkillHelper getSkillHelper();

    /**
     * @return The affinity helper instance.
     */
    IAffinityHelper getAffinityHelper();

    /**
     * @return The magic helper instance.
     */
    IMagicHelper getMagicHelper();

    /**
     * @return The mana helper instance.
     */
    IManaHelper getManaHelper();

    /**
     * @return The burnout helper instance.
     */
    IBurnoutHelper getBurnoutHelper();

    /**
     * @return The spell helper instance.
     */
    ISpellHelper getSpellHelper();

    /**
     * @return The rift helper instance.
     */
    IRiftHelper getRiftHelper();

    /**
     * @return The etherium helper instance.
     */
    IEtheriumHelper getEtheriumHelper();

    /**
     * @return The contingency helper instance.
     */
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
     * Opens the spell recipe gui for the given player.
     *
     * @param level  The level to open the gui in.
     * @param player The player to open the gui for.
     * @param stack  The spell recipe item stack to open the gui for.
     */
    void openSpellRecipeGui(Level level, Player player, ItemStack stack);

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

    /**
     * @param block     The block to check the transition for.
     * @param level     The level to check the transition for.
     * @param spellPart The spell part to check the transition for.
     * @return The transitioned block state for the given block and spell part or empty.
     */
    Optional<BlockState> getSpellTransformationFor(BlockState block, Level level, ResourceLocation spellPart);
}
