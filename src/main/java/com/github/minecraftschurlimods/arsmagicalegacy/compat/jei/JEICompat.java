package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.InscriptionTableScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;

@JeiPlugin
public class JEICompat implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ArsMagicaAPI.MOD_ID, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof IAffinityItem) {
                registration.registerSubtypeInterpreter(item, AffinitySubtypeInterpreter.INSTANCE);
            }
            if (item instanceof ISkillPointItem) {
                registration.registerSubtypeInterpreter(item, SkillPointSubtypeInterpreter.INSTANCE);
            }
        }
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(InscriptionTableScreen.class, new IGuiContainerHandler<>() {
            @Override
            public List<Rect2i> getGuiExtraAreas(InscriptionTableScreen containerScreen) {
                return containerScreen.getMinecraft().player.isCreative() ? Collections.singletonList(new Rect2i(containerScreen.getGuiLeft() + containerScreen.getXSize(), containerScreen.getGuiTop(), 105, 25)) : Collections.emptyList();
            }
        });
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(new ItemStack(AMItems.SPELL.get())));
        jeiRuntime.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, PrefabSpellManager.instance().values()
                .stream()
                .sorted()
                .map(IPrefabSpell::makeSpell)
                .toList());
    }

    public static class AffinitySubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        public static final AffinitySubtypeInterpreter INSTANCE = new AffinitySubtypeInterpreter();

        private AffinitySubtypeInterpreter() {
        }

        @Override
        public String apply(ItemStack ingredient, UidContext context) {
            return ArsMagicaAPI.get().getAffinityHelper().getAffinityForStack(ingredient).getRegistryName().toString();
        }
    }

    public static class SkillPointSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        public static final SkillPointSubtypeInterpreter INSTANCE = new SkillPointSubtypeInterpreter();

        private SkillPointSubtypeInterpreter() {
        }

        @Override
        public String apply(ItemStack ingredient, UidContext context) {
            return ArsMagicaAPI.get().getSkillHelper().getSkillPointForStack(ingredient).getRegistryName().toString();
        }
    }
}
