package com.github.minecraftschurlimods.arsmagicalegacy.compat.jei;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;

@JeiPlugin
public final class AMJeiPlugin implements IModPlugin {
    public static final IIngredientType<Skill> SKILL_TYPE = () -> Skill.class;
    private static final Identifier ID = ArsMagicaApi.id(ArsMagicaApi.MOD_ID);
    @Nullable
    private static IJeiRuntime runtime = null;

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.SPELL.get(), DataComponentSubtypeInterpreter.SPELL);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.INFINITY_ORB.get(), DataComponentSubtypeInterpreter.SKILL_POINT);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_ESSENCE.get(), DataComponentSubtypeInterpreter.AFFINITY);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_TOME.get(), DataComponentSubtypeInterpreter.AFFINITY);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.CRYSTAL_PHYLACTERY.get(), CrystalPhylacterySubtypeInterpreter.INSTANCE);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(SKILL_TYPE, AMRegistries.skills(true)
            .listElements()
            .filter(e -> AMRegistries.SPELL_PARTS.containsKey(e.key().identifier()))
            .sorted(Comparator.comparing(e -> Skill.getName(e).getString()))
            .map(Holder::value)
            .toList(), new SkillIngredientHelper(), new SkillIngredientRenderer(), Skill.CODEC.xmap(Holder::value, AMRegistries.skills(true)::wrapAsHolder));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SkillCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(SkillCategory.RECIPE_TYPE, AMItems.OCCULUS.toStack(), AMItems.INSCRIPTION_TABLE.toStack(), AMItems.ALTAR_CORE.toStack());
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
        HiddenSkills.update();
        IIngredientManager ingredientManager = runtime.getIngredientManager();
        ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK)
            .stream()
            .filter(e -> e.is(AMItems.SPELL))
            .toList());
    }

    @Override
    public void onRuntimeUnavailable() {
        runtime = null;
        HiddenSkills.clear();
    }

    @Nullable
    static IJeiRuntime getRuntime() {
        return runtime;
    }
}
