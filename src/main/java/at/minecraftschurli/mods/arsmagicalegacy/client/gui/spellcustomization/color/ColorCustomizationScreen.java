package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.screen.AbstractSpellPartCustomizationScreen;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import org.jspecify.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorCustomizationScreen extends AbstractSpellPartCustomizationScreen<Integer> {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/spell_customization/color.png");
    private static final int WIDTH = 180;
    private static final int HEIGHT = 130;
    private static final int COLUMNS = 4;
    private final Consumer<String> responder = s -> {
        if (s.startsWith("#") && s.length() > 1 && s.length() <= 7) {
            setColorRgb(Integer.parseInt(s.substring(1), 16), false);
        }
    };
    private int leftPos;
    private int topPos;
    private float hue;
    private float saturation;
    private float brightness;
    private int red;
    private int green;
    private int blue;
    @Nullable
    private ColorWheel colorWheel;
    @Nullable
    private BrightnessSlider brightnessSlider;
    @Nullable
    private EditBox editBox;

    public ColorCustomizationScreen(Function<DataComponentType<Integer>, @Nullable Integer> valueGetter, BiConsumer<DataComponentType<Integer>, @Nullable Integer> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_COLOR, AMDataComponents.SPELL_COLOR.get(), valueGetter, valueSetter);
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT - 48) / 2;
        colorWheel = addRenderableWidget(new ColorWheel(leftPos + 58, topPos + 58, 50, this::setColorHsb));
        editBox = addRenderableWidget(new EditBox(AMClientUtil.font(), leftPos + 26, topPos + 111, 60, 14, Component.empty()));
        editBox.setFilter(s -> {
            if (s.isEmpty() || "#".equals(s)) return true;
            if (!s.startsWith("#") || s.length() > 7) return false;
            try {
                Integer.parseInt(s.substring(1), 16);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        brightnessSlider = addRenderableWidget(new BrightnessSlider(leftPos + 113, topPos + 8, 10, 100, this::setColorHsb));
        int i = 0;
        int buttonX = leftPos + 128;
        int buttonY = topPos + 8;
        for (ChatFormatting chatFormatting : ChatFormatting.values()) {
            if (chatFormatting.getColor() == null) continue;
            addRenderableWidget(new ColorButton(buttonX + (i % COLUMNS) * 11, buttonY + (i / COLUMNS) * 11, chatFormatting.getColor(), this::setColorRgb, Component.translatable("color." + chatFormatting.getName())));
            i++;
        }
        for (DyeColor dyeColor : DyeColor.values()) {
            addRenderableWidget(new ColorButton(buttonX + (i % COLUMNS) * 11, buttonY + (i / COLUMNS) * 11, dyeColor.getTextureDiffuseColor(), this::setColorRgb, Component.translatable("color." + dyeColor.getName() + "_dye")));
            i++;
        }
        addRenderableWidget(Button.builder(AMTranslations.SPELL_CUSTOMIZATION_COLOR_CLEAR, _ -> {
            value = null;
            setValue();
            onClose();
        }).bounds(leftPos - 10, topPos + HEIGHT + 4, 200, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, _ -> {
            value = null;
            onClose();
        }).bounds(leftPos - 10, topPos + HEIGHT + 28, 98, 20).build());
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(leftPos + 92, topPos + HEIGHT + 28, 98, 20).build());
        setColorRgb(value == null ? 0xffffff : value, true);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blitFull(graphics, BACKGROUND, leftPos, topPos, WIDTH, HEIGHT);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        return editBox != null && editBox.isFocused() ? editBox.keyPressed(event) : super.keyPressed(event);
    }

    private void setColorRgb(int rgb) {
        setColorRgb(rgb, true);
    }

    private void setColorRgb(int rgb, boolean padZeroes) {
        red = AMClientUtil.getRedI(rgb);
        green = AMClientUtil.getGreenI(rgb);
        blue = AMClientUtil.getBlueI(rgb);
        float[] hsb = AMClientUtil.rgbToHsb(red, green, blue);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
        updateColorWidgets(padZeroes);
    }

    private void setColorHsb(float h, float s, float b) {
        hue = h;
        saturation = s;
        brightness = b;
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, brightness);
        red = rgb[0];
        green = rgb[1];
        blue = rgb[2];
        updateColorWidgets(true);
    }

    @SuppressWarnings("DataFlowIssue")
    private void updateColorWidgets(boolean padZeroes) {
        value = red << 16 | green << 8 | blue;
        colorWheel.setValue(hue, saturation, brightness);
        brightnessSlider.setValue(hue, saturation, brightness);
        String hex = Integer.toHexString(value);
        editBox.setResponder(null);
        editBox.setValue("#" + (padZeroes ? "0".repeat(6 - hex.length()) + hex : hex));
        editBox.setResponder(responder);
    }
}
