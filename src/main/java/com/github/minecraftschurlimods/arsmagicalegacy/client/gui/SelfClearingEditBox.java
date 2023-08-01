package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;

public class SelfClearingEditBox extends EditBox {
    public SelfClearingEditBox(int x, int y, int width, int height, int maxTextLength, EditBox prev, Font font, Component label) {
        super(font, x, y, width, height, prev, label);
        if (StringUtil.isNullOrEmpty(getValue())) {
            setValue(getMessage().getString());
            setTextColor(0x555555);
        }
        setMaxLength(maxTextLength);
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isFocused()) {
            if (getValue().equals(getMessage().getString())) {
                setValue("");
                setTextColor(0xffffff);
            }
        } else if (StringUtil.isNullOrEmpty(getValue())) {
            setValue(getMessage().getString());
            setTextColor(0x555555);
        }
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
    }
}
