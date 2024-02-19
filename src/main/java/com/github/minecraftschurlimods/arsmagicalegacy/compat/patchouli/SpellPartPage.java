package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class SpellPartPage implements ICustomComponent {
    private String part;
    private transient int x, y;
    private transient ISpellPart _part;

    @Override
    public void build(int x, int y, int page) {
        this.x = x;
        this.y = y;
        this._part = ArsMagicaAPI.get().getSpellPartRegistry().getValue(ResourceLocation.tryParse(part));
    }

    @Override
    public void render(GuiGraphics graphics, IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {
        int cx = x + 50;
        int cy = y + 70;
        graphics.pose().pushPose();
        List<ISpellModifier> modifiers = AMUtil.getModifiersForPart(_part);
        if (modifiers.isEmpty()) cy -= 16;
        else cy += ((modifiers.size() / 7) * 16) + 8;
        renderRecipe(graphics, context, cx, cy, mouseX, mouseY);
        RenderSystem.enableBlend();
        ResourceLocation registryName = this._part.getId();
        RegistryAccess registryAccess = ClientHelper.getRegistryAccess();
        Skill skill = registryAccess.registryOrThrow(Skill.REGISTRY_KEY).get(registryName);
        TextureAtlasSprite sprite = SkillIconAtlas.instance().getSprite(Objects.requireNonNull(skill).getId(registryAccess));
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        graphics.blit(cx - 2, cy - 2, 0, 20, 20, sprite);
        if (context.isAreaHovered(mouseX, mouseY, cx - 2, cy - 2, 20, 20)) {
            context.setHoverTooltipComponents(List.of(skill.getDisplayName(registryAccess), skill.getDescription(registryAccess)));
        }
        renderModifiers(graphics, context, x, y, mouseX, mouseY, modifiers);
        RenderSystem.disableBlend();
        graphics.pose().popPose();
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
        part = lookup.apply(IVariable.wrap(part)).asString();
    }

    private void renderModifiers(GuiGraphics graphics, IComponentRenderContext context, int posX, int posY, int mouseX, int mouseY, List<ISpellModifier> modifiers) {
        if (modifiers.isEmpty()) return;
        Component shapeName = Component.translatable(_part.getType() == ISpellPart.SpellPartType.MODIFIER ? TranslationConstants.SPELL_PART_MODIFIES : TranslationConstants.SPELL_PART_MODIFIED_BY);
        Font font = context.getGui().getMinecraft().font;
        graphics.drawString(font, shapeName, (int)(posX + 58 - (font.width(shapeName) / 2f)), posY, 0, false);
        RenderSystem.setShaderFogColor(1.0f, 1.0f, 1.0f);
        int startX = 0;
        int yOffset = -6;
        RegistryAccess registryAccess = ClientHelper.getRegistryAccess();
        Registry<Skill> skillRegistry = AMUtil.getRegistry(Skill.REGISTRY_KEY);
        for (int i = 0; i < modifiers.size(); i++) {
            ISpellModifier modifier = modifiers.get(i);
            ResourceLocation registryName = modifier.getId();
            Skill skill = skillRegistry.get(registryName);
            if (skill == null) continue;
            if (i % 7 == 0) {
                startX = (114 / 2) - ((Math.min(7, modifiers.size() - i) * 16) / 2);
                yOffset += 16;
            }
            RenderSystem.enableBlend();
            graphics.blit(posX + startX, posY + yOffset, 0, 16, 16, SkillIconAtlas.instance().getSprite(registryName));
            RenderSystem.disableBlend();
            if (context.isAreaHovered(mouseX, mouseY, posX + startX, posY + yOffset, 16, 16)) {
                context.setHoverTooltipComponents(List.of(skill.getDisplayName(registryAccess), skill.getDescription(registryAccess)));
            }
            startX += 16;
        }
    }

    private void renderRecipe(GuiGraphics graphics, IComponentRenderContext context, int cx, int cy, int mousex, int mousey) {
        if (this._part == null) return;
        ISpellPartData data = ArsMagicaAPI.get().getSpellDataManager().getDataForPart(this._part);
        if (data == null) return;
        List<ISpellIngredient> recipe = data.recipe();
        if (recipe.isEmpty()) return;
        float angleStep = 360.0f / recipe.size();
        float dist = 45;
        float lastAngle = (angleStep * (recipe.size() - 1) + (context.getTicksInBook() * 0.5f)) % 360f;
        float lastX = (float) (cx - Math.cos(Math.toRadians(lastAngle)) * dist);
        float lastY = (float) (cy - Math.sin(Math.toRadians(lastAngle)) * dist);
        for (int i = 0; i <= recipe.size(); i++) {
            float angle = (lastAngle + angleStep) % 360f;
            float x = (float) (cx - (Math.cos(Math.toRadians(angle)) * dist));
            float y = (float) (cy - (Math.sin(Math.toRadians(angle)) * dist));
            RenderUtil.line2d(graphics, x + 8, y + 8, cx + 8, cy + 8, 0, 0x0000DD, 2f);
            RenderUtil.gradientLine2d(graphics, lastX + 8, lastY + 8, x + 8, y + 8, 0, 0x0000DD, 0xDD00DD, 2f);
            if (i < recipe.size()) {
                graphics.pose().pushPose();
                graphics.pose().translate(x - (int) x, y - (int) y, 0);
                renderCraftingComponent(graphics, context, recipe.get(i), (int) x, (int) y, mousex, mousey);
                graphics.pose().popPose();
            }
            lastX = x;
            lastY = y;
            lastAngle = angle;
        }
    }

    private void renderCraftingComponent(GuiGraphics graphics, IComponentRenderContext context, ISpellIngredient craftingComponent, int sx, int sy, int mousex, int mousey) {
        ISpellIngredientRenderer.getFor(craftingComponent.getType()).renderInGui(craftingComponent, graphics, sx, sy, mousex, mousey);
        if (context.isAreaHovered(mousex, mousey, sx, sy, 16, 16)) {
            context.setHoverTooltipComponents(craftingComponent.getTooltip());
        }
    }
}
