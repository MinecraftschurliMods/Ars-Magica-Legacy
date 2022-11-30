package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.advancement.PlayerLearnedSkillTrigger;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

class AMAdvancementProvider extends AdvancementProvider {
    private final ImmutableList<Consumer<Consumer<Advancement>>> tabs;

    AMAdvancementProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper, AMSkillProvider skillProvider) {
        super(pGenerator, existingFileHelper);
        this.tabs = ImmutableList.of(new AMBookAdvancements(skillProvider));
    }

    protected void registerAdvancements(Consumer<Advancement> consumer, net.minecraftforge.common.data.ExistingFileHelper fileHelper) {
        for(Consumer<Consumer<Advancement>> consumer1 : this.tabs) {
            consumer1.accept(consumer);
        }
    }

    /**
     * Contains all advancements that are relevant for book locking.
     */
    private record AMBookAdvancements(AMSkillProvider skillProvider) implements Consumer<Consumer<Advancement>> {
        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement root = Advancement.Builder.advancement()
                    .addCriterion("arcane_compendium", InventoryChangeTrigger.TriggerInstance.hasItems(ArsMagicaAPI.get().getBookStack().getItem()))
                    .save(consumer, ArsMagicaAPI.MOD_ID + ":book/root");
            for (ResourceLocation skill : skillProvider.getSkills()) {
                Advancement.Builder.advancement().parent(root)
                        .addCriterion("knows", new PlayerLearnedSkillTrigger.TriggerInstance(EntityPredicate.Composite.ANY, skill))
                        .save(consumer, ArsMagicaAPI.MOD_ID + ":book/" + skill.getPath());
            }
        }
    }
}
