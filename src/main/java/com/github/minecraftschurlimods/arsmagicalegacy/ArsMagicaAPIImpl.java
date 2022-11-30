package com.github.minecraftschurlimods.arsmagicalegacy;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
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
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbilityManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.etherium.EtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ContingencyHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.MagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.RiftHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.OcculusTabManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellDataManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellTransformationManager;
import com.github.minecraftschurlimods.arsmagicalegacy.network.OpenOcculusGuiPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Unmodifiable;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public final class ArsMagicaAPIImpl implements ArsMagicaAPI {
    @Override
    public CreativeModeTab getCreativeModeTab() {
        return AMItems.TAB;
    }

    @Override
    public ItemStack getBookStack() {
        if (ModList.get().isLoaded("patchouli"))
            return PatchouliAPI.get().getBookStack(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"));
        return ItemStack.EMPTY;
    }

    @Override
    public IForgeRegistry<ISkillPoint> getSkillPointRegistry() {
        return AMRegistries.SKILL_POINT_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<IAffinity> getAffinityRegistry() {
        return AMRegistries.AFFINITY_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<ISpellPart> getSpellPartRegistry() {
        return AMRegistries.SPELL_PART_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<IAbility> getAbilityRegistry() {
        return AMRegistries.ABILITY_REGISTRY.get();
    }

    @Override
    public IForgeRegistry<ContingencyType> getContingencyTypeRegistry() {
        return AMRegistries.CONTINGENCY_TYPE_REGISTRY.get();
    }

    @Override
    public ISkillManager getSkillManager() {
        return SkillManager.instance();
    }

    @Override
    public IOcculusTabManager getOcculusTabManager() {
        return OcculusTabManager.instance();
    }

    @Override
    public ISpellDataManager getSpellDataManager() {
        return SpellDataManager.instance();
    }

    @Override
    public IAbilityManager getAbilityManager() {
        return AbilityManager.instance();
    }

    @Override
    public ISpellTransformationManager getSpellTransformationManager() {
        return SpellTransformationManager.instance();
    }

    @Unmodifiable
    @Override
    public ISkillHelper getSkillHelper() {
        return SkillHelper.instance();
    }

    @Unmodifiable
    @Override
    public IAffinityHelper getAffinityHelper() {
        return AffinityHelper.instance();
    }

    @Unmodifiable
    @Override
    public IMagicHelper getMagicHelper() {
        return MagicHelper.instance();
    }

    @Unmodifiable
    @Override
    public IManaHelper getManaHelper() {
        return ManaHelper.instance();
    }

    @Unmodifiable
    @Override
    public IBurnoutHelper getBurnoutHelper() {
        return BurnoutHelper.instance();
    }

    @Unmodifiable
    @Override
    public ISpellHelper getSpellHelper() {
        return SpellHelper.instance();
    }

    @Unmodifiable
    @Override
    public IRiftHelper getRiftHelper() {
        return RiftHelper.instance();
    }

    @Unmodifiable
    @Override
    public IEtheriumHelper getEtheriumHelper() {
        return EtheriumHelper.instance();
    }

    @Unmodifiable
    @Override
    public IContingencyHelper getContingencyHelper() {
        return ContingencyHelper.instance();
    }

    @Override
    public void openOcculusGui(Player player) {
        if (player instanceof ServerPlayer) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new OpenOcculusGuiPacket(), player);
        } else if (player.isLocalPlayer()) {
            ClientHelper.openOcculusGui();
        }
    }

    @Override
    public void openSpellCustomizationGui(Level level, Player player, ItemStack stack) {
        if (level.isClientSide()) {
            ClientHelper.openSpellCustomizationGui(stack);
        }
    }

    @Override
    public void openSpellRecipeGui(Level level, Player player, ItemStack stack) {
        if (level.isClientSide()) {
            ClientHelper.openSpellRecipeGui(stack, true, 0, null);
        }
    }

    @Override
    public ISpell makeSpell(List<ShapeGroup> shapeGroups, SpellStack spellStack, CompoundTag additionalData) {
        return new Spell(shapeGroups, spellStack, additionalData);
    }

    @Override
    public ISpell makeSpell(SpellStack spellStack, ShapeGroup... shapeGroups) {
        return Spell.of(spellStack, shapeGroups);
    }
}
