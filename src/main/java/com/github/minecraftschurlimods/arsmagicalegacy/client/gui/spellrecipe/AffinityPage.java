package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.spellrecipe;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class AffinityPage extends Page<Pair<Holder<Affinity>, Double>> {
    @SuppressWarnings("DataFlowIssue")
    public AffinityPage(Map<Holder<Affinity>, Double> affinities) {
        super(5, 13, 16, 6, 1, affinities.keySet()
            .stream()
            .map(e -> new Pair<>(e, affinities.get(e)))
            .sorted(Comparator.comparing(e -> e.getFirst().getKey()))
            .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getSecond)))
            .toList());
    }

    @Override
    public Component getTitle() {
        return AMTranslations.SPELL_RECIPE_AFFINITIES;
    }

    @Override
    public void extractElement(Pair<Holder<Affinity>, Double> element, int index, GuiGraphicsExtractor graphics, int x, int y) {
        AMClientUtil.renderItem(graphics, AMUtil.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), element.getFirst()), x, y + index * (size + spacing));
        graphics.text(AMClientUtil.font(), "%.3f".formatted(element.getSecond()), x + size + spacing, y + 4 + index * (size + spacing), 0xff000000 | element.getFirst().value().color(), false);
    }

    @Override
    public List<Component> getElementTooltip(Pair<Holder<Affinity>, Double> element) {
        return List.of(Affinity.getName(element.getFirst()));
    }
}
