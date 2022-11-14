package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
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
                registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item, AffinitySubtypeInterpreter.INSTANCE);
            }
            if (item instanceof ISkillPointItem) {
                registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item, SkillPointSubtypeInterpreter.INSTANCE);
            }
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(new ItemStack(AMItems.SPELL.get())));
        jeiRuntime.getIngredientManager().addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, ClientHelper.getRegistryAccess()
                .registryOrThrow(PrefabSpell.REGISTRY_KEY)
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
            return ArsMagicaAPI.get().getAffinityHelper().getAffinityForStack(ingredient).getId().toString();
        }
    }

    public static class SkillPointSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
        public static final SkillPointSubtypeInterpreter INSTANCE = new SkillPointSubtypeInterpreter();

        private SkillPointSubtypeInterpreter() {
        }

        @Override
        public String apply(ItemStack ingredient, UidContext context) {
            return ArsMagicaAPI.get().getSkillHelper().getSkillPointForStack(ingredient).getId().toString();
        }
    }
}
