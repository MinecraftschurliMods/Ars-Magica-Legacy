package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding.Listenable;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ColorPickerScreen extends Screen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/color_picker.png");
    private static final int textureWidth = 180;
    private static final int textureHeight = 130;
    private final Consumer<Integer> onPick;
    private final List<PresetColor> presetColors;
    private ColorPickerWidget colorPicker;
    private int top;
    private int left;

    public static CompletableFuture<Integer> pickColor(Component title, int initialColor) {
        Minecraft instance = Minecraft.getInstance();
        CompletableFuture<Integer> future = new CompletableFuture<>();
        ColorPickerScreen screen = new ColorPickerScreen(title, initialColor, future::complete);
        if (instance.screen != null) {
            instance.pushGuiLayer(screen);
        } else {
            instance.setScreen(screen);
        }
        return future;
    }

    public ColorPickerScreen(Component title, Consumer<Integer> onPick) {
        this(title, 0xFFFFFFFF, List.of(), true, onPick);
    }

    public ColorPickerScreen(Component title, int initialColor, Consumer<Integer> onPick) {
        this(title, initialColor, List.of(), true, onPick);
    }

    public ColorPickerScreen(Component title, int initialColor, List<PresetColor> presetColors, Consumer<Integer> onPick) {
        this(title, initialColor, presetColors, false, onPick);
    }

    public ColorPickerScreen(Component title, List<PresetColor> presetColors, Consumer<Integer> onPick) {
        this(title, 0xFFFFFFFF, presetColors, false, onPick);
    }

    public ColorPickerScreen(Component title, int initialColor, boolean useDyeColors, Consumer<Integer> onPick) {
        this(title, initialColor, List.of(), useDyeColors, onPick);
    }

    public ColorPickerScreen(Component title, int initialColor, List<PresetColor> presetColors, boolean useDyeColors, Consumer<Integer> onPick) {
        super(title);
        this.onPick = onPick;
        ImmutableList.Builder<PresetColor> builder = ImmutableList.builder();
        if (useDyeColors) {
            for (DyeColor color : DyeColor.values()) {
                builder.add(PresetColor.of(color));
            }
        }
        builder.addAll(presetColors);
        this.presetColors = builder.build();
        this.colorPicker = new ColorPickerWidget(0, 0, 0, getTitle(), initialColor);
    }

    private void save() {
        onPick.accept(colorPicker.color.get());
    }

    @Override
    protected void init() {
        int cX = width / 2 + 40;
        int cY = height / 2 - 5;
        top = cY - textureHeight / 2 + 7;
        left = cX - textureWidth / 2 - 17;
        addRenderableOnly((pPoseStack, pMouseX, pMouseY, pPartialTick) -> drawString(pPoseStack, font, title, left + 6, top + 6, 0xFFFFFFFF));
        colorPicker = addRenderableWidget(new ColorPickerWidget(cX, cY, 50, getTitle(), colorPicker));
        addRenderableWidget(colorPicker.brightnessSlider(cX + 55, cY - 50, 10, 100, getTitle(), ColorPickerWidget.BrightnessSlider.Orientation.BOTTOM_TO_TOP));
        final int len = presetColors.size();
        final int s = 4;
        final int bX = cX - 55 - s * 11;
        final int bY = cY - 50 + (100 - s * 11) / 2;
        for (int i = 0; i < len; i++) {
            final PresetColor presetColor = presetColors.get(i);
            final int pX = bX + 11 * (i % s);
            final int pY = bY + 11 * (i / s);
            addRenderableWidget(colorPicker.presetColor(pX, pY, 10, 10, presetColor.color(), presetColor.name()));
        }
        addRenderableOnly(colorPicker.hoverPreview(cX - 15, cY + 55, 15, 10));
        addRenderableOnly(colorPicker.selectionPreview(cX, cY + 55, 15, 10));
        Listenable<String> hexString = colorPicker.color.xmap(ColorUtil::hexString, ColorUtil::fromHex);
        EditBox hexColor = addRenderableWidget(new EditBox(font, cX - 100, cY + 49, 50, 12, Component.nullToEmpty(hexString.get())));
        hexColor.setFilter(ColorUtil::isPartialHexColor);
        hexString.bind(hexColor::setValue, c -> hexColor.setResponder(colorString -> {
            String trimmed = colorString.trim();
            if (trimmed.equals(colorString)) {
                try {
                    c.accept(trimmed);
                } catch (IllegalArgumentException ignored) {}
            } else {
                hexColor.setValue(trimmed);
            }
        }));
        addRenderableWidget(new Button(cX - 95, cY + 75, 50, 20, Component.translatable(TranslationConstants.INSCRIPTION_TABLE_COLOR_PICKER_CANCEL), b -> onClose()));
        addRenderableWidget(new Button(cX + 10, cY + 75, 50, 20, Component.literal(TranslationConstants.INSCRIPTION_TABLE_COLOR_PICKER_DONE), b -> {
            save();
            onClose();
        }));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        pPoseStack.pushPose();
        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(pPoseStack, left, top, 0, 0, textureWidth, textureHeight);
        pPoseStack.popPose();
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public record PresetColor(int color, Component name) {
        public static PresetColor of(DyeColor dyeColor) {
            return new PresetColor(dyeColor.getTextColor(), getDyeColorName(dyeColor));
        }
    }

    private static Component getDyeColorName(DyeColor dyeColor) {
        return DyeItem.byColor(dyeColor).getDescription();
    }
}
