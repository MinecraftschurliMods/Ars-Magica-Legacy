package com.github.minecraftschurlimods.arsmagicalegacy.datagen.data;

import com.github.minecraftschurlimods.arsmagicalegacy.common.advancement.AffinityChangeTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.advancement.LevelChangeTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.advancement.SkillChangeTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMagic;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class AMAdvancementProvider extends AdvancementProvider {
    public AMAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new AdvancementSubProvider()));
    }

    private static class AdvancementSubProvider implements net.minecraft.data.advancements.AdvancementSubProvider {
        @SuppressWarnings({"DataFlowIssue", "unused"})
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            HolderLookup.RegistryLookup<Affinity> affinities = registries.lookupOrThrow(AMRegistries.Keys.AFFINITY);
            ItemStackTemplate book = ArsMagicaApi.book();
            Criterion<InventoryChangeTrigger.TriggerInstance> bookCriterion = InventoryChangeTrigger.TriggerInstance
                .hasItems(new ItemPredicate(
                    Optional.of(HolderSet.direct(book.item())),
                    MinMaxBounds.Ints.ANY,
                    DataComponentMatchers.Builder.components()
                        .exact(DataComponentExactPredicate.allOf(PatchedDataComponentMap.fromPatch(DataComponentMap.EMPTY, book.components()))).build()));

            AdvancementHolder bookRoot = Advancement.Builder.advancement()
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.id("book/root").toString());
            registries.lookupOrThrow(AMRegistries.Keys.SKILL).listElements().forEach(skill -> Advancement.Builder.advancement()
                .parent(bookRoot)
                .addCriterion("knows", SkillChangeTrigger.create(List.of(skill)))
                .save(saver, ArsMagicaApi.id("book/" + skill.getKey().identifier().getPath()).toString()));

            AdvancementHolder root = Advancement.Builder.advancement()
                .display(book, title("root"), description("root"), ArsMagicaApi.id("textures/gui/advancements/background.png"), AdvancementType.TASK, false, false, true)
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.id("root").toString());
            AdvancementHolder skill = advancement(saver, "skill", root, AMItems.OCCULUS, AdvancementType.TASK, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ANY_NON_HIDDEN)));
            AdvancementHolder allSkills = advancement(saver, "all_skills", skill, AMItems.OCCULUS, AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ALL_NON_HIDDEN)));
            AdvancementHolder hiddenSkill = advancement(saver, "hidden_skill", skill, AMItems.OCCULUS, AdvancementType.TASK, true,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ANY_HIDDEN)));
            AdvancementHolder allHiddenSkills = advancement(saver, "all_hidden_skills", hiddenSkill, AMItems.OCCULUS, AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ALL_HIDDEN)));
            AdvancementHolder spell = advancement(saver, "spell", skill, AMItems.SPELL, AdvancementType.TASK, false,
                builder -> builder.addCriterion("spell", InventoryChangeTrigger.TriggerInstance.hasItems(AMItems.SPELL)));
            AdvancementHolder affinityOnePercent = advancement(saver, "affinity_one_percent", spell, AMUtil.template(AMItems.AFFINITY_ESSENCE, AMDataComponents.AFFINITY.get(), affinities.getOrThrow(AMMagic.WATER)), AdvancementType.TASK, false,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(0.01)));
            AdvancementHolder affinityFiftyPercent = advancement(saver, "affinity_fifty_percent", affinityOnePercent, AMUtil.template(AMItems.AFFINITY_ESSENCE, AMDataComponents.AFFINITY.get(), affinities.getOrThrow(AMMagic.LIFE)), AdvancementType.TASK, false,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(0.5)));
            AdvancementHolder affinityFull = advancement(saver, "affinity_full", affinityFiftyPercent, AMUtil.template(AMItems.AFFINITY_ESSENCE, AMDataComponents.AFFINITY.get(), affinities.getOrThrow(AMMagic.ENDER)), AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(1)));
            AdvancementHolder affinityTome = advancement(saver, "affinity_tome", affinityFull, AMUtil.template(AMItems.AFFINITY_TOME, AMDataComponents.AFFINITY.get(), affinities.getOrThrow(Affinity.NONE)), AdvancementType.TASK, true,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(1, 0)));
            AdvancementHolder level10 = advancement(saver, "level_10", spell, AMItems.MOONSTONE, AdvancementType.TASK, false,
                builder -> builder.addCriterion("level", LevelChangeTrigger.create(10)));
            AdvancementHolder level100 = advancement(saver, "level_100", level10, AMItems.SUNSTONE, AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("level", LevelChangeTrigger.create(100)));
        }

        private Component title(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title");
        }

        private Component description(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description");
        }

        private AdvancementHolder advancement(Consumer<AdvancementHolder> saver, String name, AdvancementHolder parent, DeferredItem<?> icon, AdvancementType type, boolean hidden, Consumer<Advancement.Builder> consumer) {
            return advancement(saver, name, parent, new ItemStackTemplate(icon), type, hidden, consumer);
        }

        private AdvancementHolder advancement(Consumer<AdvancementHolder> saver, String name, AdvancementHolder parent, ItemStackTemplate icon, AdvancementType type, boolean hidden, Consumer<Advancement.Builder> consumer) {
            Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), null, type, true, true, hidden);
            consumer.accept(builder);
            return builder.save(saver, ArsMagicaApi.id(name).toString());
        }
    }
}
