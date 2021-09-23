package com.github.minecraftschurli.arsmagicalegacy;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.*;
import com.github.minecraftschurli.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.KnowledgeHelper;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.function.BiFunction;

@MethodsReturnNonnullByDefault
public final class ArsMagicaAPIImpl implements ArsMagicaAPI.IArsMagicaAPI {

    @Override
    public CreativeModeTab getCreativeModeTab() {
        return AMItems.TAB;
    }

    @Override
    public ItemStack getBookStack() {
        if (ModList.get().isLoaded("patchouli")) {
            return PatchouliAPI.get().getBookStack(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_compendium"));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public IForgeRegistry<IOcculusTab> getOcculusTabRegistry() {
        return AMRegistries.OCCULUS_TAB_REGISTRY.get();
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
    public ISkillManager getSkillManager() {
        return SkillManager.instance();
    }

    @Override
    public IKnowledgeHelper getKnowledgeHelper() {
        return KnowledgeHelper.instance();
    }

    @Override
    public IAffinityHelper getAffinityHelper() {
        return AffinityHelper.instance();
    }

    @Override
    public void registerOcculusTabRenderer(IOcculusTab tab, BiFunction<IOcculusTab, Player, OcculusTabRenderer> factory) {
        OcculusScreen.registerRenderer(tab, factory);
    }

    @Override
    public void openOcculusGui(Player player) {
        ClientHelper.openOcculusGui(player);
    }

    @Override
    public Capability<IKnowledgeHolder> getKnowledgeCapability() {
        return KnowledgeHelper.getCapability();
    }
}
