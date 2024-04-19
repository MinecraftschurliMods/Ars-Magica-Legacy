package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLearnedSkillTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

class AMAdvancements extends AdvancementProvider {

    public AMAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Book()));
    }

    /**
     * Contains all advancements that are relevant for book locking.
     */
    record Book() implements AdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder
                    .advancement()
                    .addCriterion("arcane_compendium", InventoryChangeTrigger.TriggerInstance.hasItems(ArsMagicaAPI.get().getBookStack().getItem()))
                    .save(saver, ArsMagicaAPI.MOD_ID + ":book/root");
            registries.lookupOrThrow(Skill.REGISTRY_KEY).listElementIds().forEach(skill -> Advancement.Builder
                    .advancement()
                    .parent(root)
                    .addCriterion("knows", AMCriteriaTriggers.PLAYER_LEARNED_SKILL.get().createCriterion(new PlayerLearnedSkillTrigger.TriggerInstance(Optional.empty(), Optional.of(skill.location()))))
                    .save(saver, ArsMagicaAPI.MOD_ID + ":book/" + skill.location().getPath()));
        }
    }
}
