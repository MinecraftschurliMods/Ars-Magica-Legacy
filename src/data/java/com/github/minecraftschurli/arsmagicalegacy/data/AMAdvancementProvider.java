package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
class AMAdvancementProvider extends AdvancementProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    AMAdvancementProvider(DataGenerator pGenerator) {
        super(pGenerator);
        generator = pGenerator;
    }

    @Override
    public void run(HashCache pCache) {
        Path path = generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = a -> {
            if (!set.add(a.getId())) throw new IllegalStateException("Duplicate advancement " + a.getId());
            else {
                Path path1 = path.resolve("data/" + a.getId().getNamespace() + "/advancements/" + a.getId().getPath() + ".json");
                try {
                    DataProvider.save(GSON, pCache, a.deconstruct().serializeToJson(), path1);
                } catch (IOException e) {
                    LOGGER.error("Couldn't save advancement {}", path1, e);
                }
            }
        };
        for (Consumer<Consumer<Advancement>> c : ImmutableList.of(new AMAdvancements(), new AMBookAdvancements())) c.accept(consumer);
    }

    /**
     * Adds an advancement.
     *
     * @param consumer The advancement consumer provided by the data generator.
     * @param folder   The folder name of this advancement's tab.
     * @param id       The id of this advancement.
     * @param parent   The id of this advancement's parent.
     * @param item     The item to display.
     * @param type     The frame type of this advancement.
     * @param toast    Whether to show a toast in the top right corner or not.
     * @param chat     Whether to announce the advancement to chat or not.
     * @param hide     Whether the advancement should be visible before unlocking or not.
     * @param criteria Optional advancement criteria. Should be made through Pair.of("id", criterion);
     */
    static Advancement addAdvancement(Consumer<Advancement> consumer, String folder, String id, Advancement parent, ItemLike item, FrameType type, boolean toast, boolean chat, boolean hide, Pair<String, CriterionTriggerInstance>... criteria) {
        Advancement.Builder builder = Advancement.Builder.advancement().parent(parent).display(item, new TranslatableComponent("advancements." + ArsMagicaAPI.MOD_ID + "." + id + ".title"), new TranslatableComponent("advancements." + ArsMagicaAPI.MOD_ID + "." + id + ".description"), null, type, toast, chat, hide);
        for (Pair<String, CriterionTriggerInstance> criterion : criteria)
            builder.addCriterion(criterion.getFirst(), criterion.getSecond());
        return builder.save(consumer, ArsMagicaAPI.MOD_ID + ":" + folder + "/" + id);
    }

    /**
     * Adds an advancement.
     *
     * @param consumer The advancement consumer provided by the data generator.
     * @param folder   The folder name of this advancement's tab.
     * @param id       The id of this advancement.
     * @param parent   The id of this advancement's parent.
     * @param item     The ItemStack to display.
     * @param type     The frame type of this advancement.
     * @param toast    Whether to show a toast in the top right corner or not.
     * @param chat     Whether to announce the advancement to chat or not.
     * @param hide     Whether the advancement should be visible before unlocking or not.
     * @param criteria Optional advancement criteria. Should be made through Pair.of("id", criterion);
     */
    static Advancement addAdvancement(Consumer<Advancement> consumer, String folder, String id, Advancement parent, ItemStack item, FrameType type, boolean toast, boolean chat, boolean hide, Pair<String, CriterionTriggerInstance>... criteria) {
        Advancement.Builder builder = Advancement.Builder.advancement().parent(parent).display(item, new TranslatableComponent("advancements." + ArsMagicaAPI.MOD_ID + "." + id + ".title"), new TranslatableComponent("advancements." + ArsMagicaAPI.MOD_ID + "." + id + ".description"), null, type, toast, chat, hide);
        for (Pair<String, CriterionTriggerInstance> criterion : criteria)
            builder.addCriterion(criterion.getFirst(), criterion.getSecond());
        return builder.save(consumer, ArsMagicaAPI.MOD_ID + ":" + folder + "/" + id);
    }

    /**
     * Contains all normal advancements.
     */
    public static final class AMAdvancements implements Consumer<Consumer<Advancement>> {
        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement root = Advancement.Builder.advancement().display(ArsMagicaAPI.get().getBookStack(), new TranslatableComponent(Util.makeDescriptionId("advancements", new ResourceLocation(ArsMagicaAPI.MOD_ID, "root/title"))), new TranslatableComponent(Util.makeDescriptionId("advancements", new ResourceLocation(ArsMagicaAPI.MOD_ID, "root/description"))), new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/advancements/backgrounds/arsmagicalegacy.png"), FrameType.TASK, false, false, false).addCriterion("arcane_compendium", InventoryChangeTrigger.TriggerInstance.hasItems(ArsMagicaAPI.get().getBookStack().getItem())).save(consumer, ArsMagicaAPI.MOD_ID + ":root");
        }
    }

    /**
     * Contains all advancements that are relevant for book locking. Should be hidden.
     */
    public static final class AMBookAdvancements implements Consumer<Consumer<Advancement>> {
        @Override
        public void accept(Consumer<Advancement> consumer) {
            Advancement root = Advancement.Builder.advancement().addCriterion("arcane_compendium", InventoryChangeTrigger.TriggerInstance.hasItems(ArsMagicaAPI.get().getBookStack().getItem())).save(consumer, ArsMagicaAPI.MOD_ID + ":book/root");
        }
    }
}
