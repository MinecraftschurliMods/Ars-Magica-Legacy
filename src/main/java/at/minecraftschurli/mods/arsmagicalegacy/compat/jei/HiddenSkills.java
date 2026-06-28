package at.minecraftschurli.mods.arsmagicalegacy.compat.jei;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.google.common.collect.Sets;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class HiddenSkills {
    private static final List<ResourceKey<Skill>> VISIBLE_SKILLS = new ArrayList<>();
    private static final List<SkillCategory.Recipe> VISIBLE_RECIPES = new ArrayList<>();
    private static final Map<ResourceKey<Skill>, Map<String, SkillCategory.Recipe>> RECIPES = new HashMap<>();

    private HiddenSkills() {}

    public static void update() {
        IJeiRuntime runtime = AMJeiPlugin.getRuntime();
        if (runtime == null) return;
        IIngredientManager ingredientManager = runtime.getIngredientManager();
        IRecipeManager recipeManager = runtime.getRecipeManager();
        Registry<Skill> skills = AMRegistries.skills(true);
        if (!VISIBLE_RECIPES.isEmpty()) {
            recipeManager.hideRecipes(SkillCategory.RECIPE_TYPE, VISIBLE_RECIPES);
            VISIBLE_RECIPES.clear();
        }
        if (RECIPES.isEmpty()) {
            registerRecipes(runtime);
        }
        VISIBLE_SKILLS.clear();
        addVisibleSkillsAndRecipes();
        ingredientManager.removeIngredientsAtRuntime(AMJeiPlugin.SKILL_TYPE, getSkills()
            .map(Holder::value)
            .toList());
        ingredientManager.addIngredientsAtRuntime(AMJeiPlugin.SKILL_TYPE, VISIBLE_SKILLS.stream()
            .map(skills::getValue)
            .filter(Objects::nonNull)
            .toList());
        recipeManager.unhideRecipes(SkillCategory.RECIPE_TYPE, VISIBLE_RECIPES);
    }

    static void clear() {
        VISIBLE_SKILLS.clear();
        VISIBLE_RECIPES.clear();
        RECIPES.clear();
    }

    private static void registerRecipes(IJeiRuntime runtime) {
        getSkills().forEach(holder -> {
                List<ResourceKey<Skill>> keys = getHiddenModifiers(holder.key())
                .map(Holder::getKey)
                .filter(Objects::nonNull)
                .toList();
            ResourceKey<Skill> key = holder.getKey();
            if (key == null) return;
            Map<String, SkillCategory.Recipe> recipes = RECIPES.computeIfAbsent(key, _ -> new HashMap<>());
            for (Set<ResourceKey<Skill>> set : Sets.powerSet(new HashSet<>(keys))) {
                Set<ResourceKey<Skill>> hiddenModifiers = new HashSet<>(keys);
                set.forEach(hiddenModifiers::remove);
                recipes.put(getKey(set), recipe(holder, hiddenModifiers));
            }
        });
        List<SkillCategory.Recipe> recipes = RECIPES.values()
            .stream()
            .map(Map::values)
            .flatMap(Collection::stream)
            .sorted(Comparator.comparing(e -> Skill.getName(e.skill()).toString()))
            .toList();
        runtime.getRecipeManager().addRecipes(SkillCategory.RECIPE_TYPE, recipes);
        runtime.getRecipeManager().hideRecipes(SkillCategory.RECIPE_TYPE, recipes);
    }

    @SuppressWarnings("DataFlowIssue")
    private static void addVisibleSkillsAndRecipes() {
        getSkills().filter(HiddenSkills::shouldShow)
            .map(Holder.Reference::key)
            .forEach(skill -> {
                VISIBLE_SKILLS.add(skill);
                VISIBLE_RECIPES.add(RECIPES.get(skill).get(getKey(getHiddenModifiers(skill)
                    .filter(HiddenSkills::shouldShow)
                    .map(Holder::getKey)
                    .collect(Collectors.toSet()))));
            });
    }

    private static Stream<Holder.Reference<Skill>> getSkills() {
        return AMRegistries.skills(true)
            .listElements()
            .filter(e -> AMRegistries.SPELL_PARTS.containsKey(e.key().identifier()))
            .sorted(Comparator.comparing(e -> Skill.getName(e).getString()));
    }

    private static Stream<Holder<Skill>> getHiddenModifiers(ResourceKey<Skill> skill) {
        Registry<Skill> skills = AMRegistries.skills(true);
        Registry<SpellPart> spellParts = AMRegistries.SPELL_PARTS;
        SpellPart part = spellParts.getValue(ResourceKey.create(AMRegistries.Keys.SPELL_PART, skill.identifier()));
        return part == null ? Stream.of() : ArsMagicaApi.spellHelper()
            .getModifiers(part)
            .stream()
            .map(e -> skills.getValue(spellParts.getKey(e)))
            .filter(Objects::nonNull)
            .filter(Skill::hidden)
            .map(skills::wrapAsHolder);
    }

    private static boolean shouldShow(Holder<Skill> skill) {
        LocalPlayer player = AMClientUtil.player();
        return player != null && (!skill.value().hidden() || ArsMagicaApi.magicHelper().knows(player, skill));
    }

    private static String getKey(Collection<ResourceKey<Skill>> set) {
        return String.join(",", set.stream()
            .map(ResourceKey::identifier)
            .map(Identifier::toString)
            .sorted()
            .toList());
    }

    private static SkillCategory.Recipe recipe(Holder<Skill> holder, Collection<ResourceKey<Skill>> hiddenModifiers) {
        return SkillCategory.Recipe.of(holder, hiddenModifiers.stream()
            .map(AMRegistries.skills(true)::getOrThrow)
            .collect(Collectors.toSet()));
    }
}
