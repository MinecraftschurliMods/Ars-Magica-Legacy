package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AffinityTabRenderer extends OcculusTabRenderer {
    private static final Component DETAILS = Component.translatable(AMTranslations.OCCULUS_DETAILS_KEY).withStyle(ChatFormatting.GRAY);
    private static final int RADIUS = 5;
    private static final int DISTANCE = 60;
    private static final float FRACTAL = 0.1f;
    private final RandomSource random = Objects.requireNonNull(AMClientUtil.level()).getRandom();
    private final List<Component> tooltip = new ArrayList<>();

    public AffinityTabRenderer(Holder<OcculusTab> occulusTab) {
        super(occulusTab);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        tooltip.clear();
        LocalPlayer player = AMClientUtil.player();
        if (player == null) return;
        Font font = AMClientUtil.font();
        Registry<Affinity> affinities = AMRegistries.affinities(true);
        Registry<Ability> abilities = AMRegistries.abilities(true);
        int center = TAB_SIZE / 2 + RADIUS;
        int count = affinities.size() - 1;
        double angleStep = Math.toRadians(360. / count);
        List<? extends Holder<Affinity>> list = affinities.listElements()
            .filter(holder -> holder.value().index() >= 0)
            .sorted(Comparator.comparing(holder -> holder.value().index()))
            .toList();
        for (int i = 0; i < list.size(); i++) {
            Holder<Affinity> affinity = list.get(i);
            int color = 0xff000000 | affinity.value().color();
            double depth = ArsMagicaApi.magicHelper().getAffinityDepth(player, affinity);
            double angle = angleStep * i;
            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);
            double angleMinusHalf = angle - angleStep / 2;
            double anglePlusHalf = angle + angleStep / 2;
            float startX1 = (float) (Math.cos(angleMinusHalf) * RADIUS) + center;
            float startY1 = (float) (Math.sin(angleMinusHalf) * RADIUS) + center;
            float startX2 = (float) (Math.cos(anglePlusHalf) * RADIUS) + center;
            float startY2 = (float) (Math.sin(anglePlusHalf) * RADIUS) + center;
            float endX = (float) (cosAngle * (RADIUS + depth * DISTANCE)) + center;
            float endY = (float) (sinAngle * (RADIUS + depth * DISTANCE)) + center;
            if (depth >= 0.01) {
                float displace = (Math.abs(startX1 - startX2) + Math.abs(startY1 - startY2)) * (float) depth;
                renderFractalLine(graphics, startX1, startY1, endX, endY, color, displace, 1 - FRACTAL);
                renderFractalLine(graphics, startX2, startY2, endX, endY, color, displace, 1 - FRACTAL);
                renderFractalLine(graphics, startX1, startY1, endX, endY, color, displace, 1 + FRACTAL);
                renderFractalLine(graphics, startX2, startY2, endX, endY, color, displace, 1 + FRACTAL);
            } else {
                SkillTreeTabRenderer.fillLine(graphics, startX1, startY1, startX2, startY2, color, color, 0.25f);
            }
            String text = percent(depth);
            double width = font.width(text);
            double height = font.lineHeight;
            double anchorX = (cosAngle * (RADIUS + DISTANCE)) + center;
            double anchorY = (sinAngle * (RADIUS + DISTANCE)) + center;
            int textX, textY;
            if (angle == 0 || angle == Math.PI) {
                textX = (int) (anchorX < center ? anchorX - width - 1 : anchorX);
                textY = (int) anchorY;
            } else {
                textX = (int) (anchorX - width / 2);
                textY = (int) (anchorY < center ? anchorY - height : anchorY + 17);
            }
            graphics.text(font, text, textX, textY, color, false);
            int stackX = (int) (textX + width / 2 - 8);
            int stackY = textY - 17;
            AMClientUtil.renderItem(graphics, font, AMUtil.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinity), stackX, stackY);
            if (mouseX < stackX || mouseX >= stackX + 16 || mouseY < stackY || mouseY >= stackY + 16) continue;
            tooltip.add(Affinity.getName(affinity).copy().withColor(color));
            if (AMClientUtil.mc().hasShiftDown()) {
                abilities.listElements()
                    .filter(e -> e.value().affinity().getKey() == affinity.getKey())
                    .sorted((a, b) -> (int) (a.value().bounds().max().orElse(0.) * 100 - b.value().bounds().max().orElse(0.) * 100))
                    .sorted((a, b) -> (int) (a.value().bounds().min().orElse(0.) * 100 - b.value().bounds().min().orElse(0.) * 100))
                    .sorted((a, b) -> a.value().negative() == b.value().negative() ? 0 : a.value().negative() ? 1 : -1)
                    .forEach(holder -> {
                        Ability ability = holder.value();
                        MinMaxBounds.Doubles bounds = ability.bounds();
                        tooltip.add(Component.translatable(AMTranslations.OCCULUS_ABILITY_KEY, Ability.getName(holder), percent(bounds.min().orElse(0.)), percent(bounds.max().orElse(1.))).withStyle(ability.test(player) ? ability.negative() ? ChatFormatting.RED : ChatFormatting.GREEN : ChatFormatting.GRAY));
                    });
            } else {
                tooltip.add(DETAILS);
            }
        }
    }

    @Override
    public void renderTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (!tooltip.isEmpty()) {
            graphics.setTooltipForNextFrame(AMClientUtil.font(), tooltip.stream().map(Component::getVisualOrderText).toList(), mouseX, mouseY);
        }
    }

    @Override
    public boolean hasSkillPointPanel() {
        return false;
    }

    private void renderFractalLine(GuiGraphicsExtractor graphics, float startX, float startY, float endX, float endY, int color, float displace, float fractal) {
        if (displace < fractal) {
            SkillTreeTabRenderer.fillLine(graphics, startX, startY, endX, endY, color, color, 0.25f);
            return;
        }
        float x = (startX + endX) / 2 + (random.nextFloat() - 0.5f) * displace;
        float y = (startY + endY) / 2 + (random.nextFloat() - 0.5f) * displace;
        renderFractalLine(graphics, startX, startY, x, y, color, displace / 2, fractal);
        renderFractalLine(graphics, endX, endY, x, y, color, displace / 2, fractal);
    }

    private static String percent(double d) {
        return (int) (d * 100) + "%";
    }
}
