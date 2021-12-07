package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringUtil;

class SelfClearingEditBox extends EditBox {

    public SelfClearingEditBox(int x, int y, int width, int height, int maxTextLength, EditBox prev, Font font, Component label) {
        super(font, x, y, width, height, prev, label);
        if (StringUtil.isNullOrEmpty(getValue())) {
            setValue(getMessage().getString());
            setTextColor(0x555555);
        }
        setMaxLength(maxTextLength);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isFocused()) {
            if (this.getValue().equals(this.getMessage().getString())) {
                this.setValue("");
                this.setTextColor(0xffffff);
            }
        } else if (StringUtil.isNullOrEmpty(this.getValue())) {
            this.setValue(this.getMessage().getString());
            this.setTextColor(0x555555);
        }
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
    }
}
