package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

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
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        jeiRuntime.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, PrefabSpellManager.instance().values()
                .stream()
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
