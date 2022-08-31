package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public void render(PoseStack poseStack, IComponentRenderContext context, float partialTicks, int mouseX, int mouseY) {
        int cx = x + 50;
        int cy = y + 70;
        poseStack.pushPose();
        List<ISpellModifier> modifiers = cacheModifiers();
        if (modifiers.isEmpty()) cy -= 16;
        else cy += ((modifiers.size() / 7) * 16) + 8;
        renderRecipe(poseStack, context, cx, cy, mouseX, mouseY);
        RenderSystem.enableBlend();
        ResourceLocation registryName = this._part.getId();
        Skill skill = Minecraft.getInstance().level.registryAccess().registryOrThrow(Skill.REGISTRY_KEY).get(registryName);
        TextureAtlasSprite sprite = SkillIconAtlas.instance().getSprite(skill.getId());
        RenderSystem.setShaderTexture(0, SkillIconAtlas.SKILL_ICON_ATLAS);
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        GuiComponent.blit(poseStack, cx - 2, cy - 2, context.getGui().getBlitOffset(), 20, 20, sprite);
        if (context.isAreaHovered(mouseX, mouseY, cx - 2, cy - 2, 20, 20)) {
            context.setHoverTooltipComponents(List.of(skill.getDisplayName(), skill.getDescription()));
        }
        renderModifiers(poseStack, context, x, y, mouseX, mouseY, modifiers);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    private List<ISpellModifier> cacheModifiers() {
        if (_part.getType() == ISpellPart.SpellPartType.MODIFIER) return List.of();
        Set<ISpellPartStat> stats;
        if (_part instanceof ISpellComponent component) {
            stats = component.getStatsUsed();
        } else if (_part instanceof ISpellShape shape) {
            stats = shape.getStatsUsed();
        } else {
            return List.of();
        }
        List<ISpellModifier> modifiers = new ArrayList<>();
        for (ISpellPart part : ArsMagicaAPI.get().getSpellPartRegistry()) {
            if (_part == part) continue;
            if (part instanceof ISpellModifier modifier) {
                for (ISpellPartStat stat : modifier.getStatsModified()) {
                    if (stats.contains(stat)) {
                        modifiers.add(modifier);
                        break;
                    }
                }
            }
        }
        return modifiers;
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
        part = lookup.apply(IVariable.wrap(part)).asString();
    }

    /*@Override
    public boolean mouseClicked(final IComponentRenderContext context, final double mouseX, final double mouseY, final int mouseButton) {
        int posX = x;
        int posY = y;
        int startX = 0;
        int yOffset = -6;
        List<ISpellModifier> modifiers = cacheModifiers();
        for (int i = 0; i < modifiers.size(); i++) {
            if (i % 7 == 0) {
                startX = (114 / 2) - ((Math.min(7, modifiers.size() - i) * 16) / 2);
                yOffset += 16;
            }
            if (context.isAreaHovered((int)mouseX, (int)mouseY, posX + startX, posY + yOffset, 16, 16)) {
                PatchouliAPI.get().openBookEntry(PatchouliCompat.ARCANE_COMPENDIUM, modifiers.get(i).getRegistryName(), 0);
                return true;
            }
            startX += 16;
        }
        return ICustomComponent.super.mouseClicked(context, mouseX, mouseY, mouseButton);
    }*/

    private void renderModifiers(PoseStack stack, IComponentRenderContext context, int posX, int posY, int mouseX, int mouseY, List<ISpellModifier> modifiers) {
        if (modifiers.isEmpty()) return;
        Component shapeName = Component.translatable(_part.getType() == ISpellPart.SpellPartType.MODIFIER ? TranslationConstants.SPELL_PART_MODIFIES : TranslationConstants.SPELL_PART_MODIFIED_BY);
        Font font = context.getGui().getMinecraft().font;
        font.draw(stack, shapeName, posX + 58 - (font.width(shapeName) / 2f), posY, 0);
        RenderSystem.setShaderFogColor(1.0f, 1.0f, 1.0f);
        int startX = 0;
        int yOffset = -6;
        RenderSystem.setShaderTexture(0, SkillIconAtlas.SKILL_ICON_ATLAS);
        Registry<Skill> skillRegistry = ClientHelper.getRegistry(Skill.REGISTRY_KEY);
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
            Screen.blit(stack, posX + startX, posY + yOffset, context.getGui().getBlitOffset(), 16, 16, SkillIconAtlas.instance().getSprite(registryName));
            RenderSystem.disableBlend();
            if (context.isAreaHovered(mouseX, mouseY, posX + startX, posY + yOffset, 16, 16)) {
                context.setHoverTooltipComponents(List.of(skill.getDisplayName(), skill.getDescription()));
            }
            startX += 16;
        }
    }


    private void renderRecipe(PoseStack poseStack, IComponentRenderContext context, int cx, int cy, int mousex, int mousey) {
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
            RenderUtil.line2d(poseStack, x + 8, y + 8, cx + 8, cy + 8, context.getGui().getBlitOffset(), 0x0000DD, 2f);
            RenderUtil.gradientLine2d(poseStack, lastX + 8, lastY + 8, x + 8, y + 8, context.getGui().getBlitOffset(), 0x0000DD, 0xDD00DD, 2f);
            if (i < recipe.size()) {
                poseStack.pushPose();
                poseStack.translate(x - (int)x, y - (int)y, 0);
                renderCraftingComponent(poseStack, context, recipe.get(i), (int) x, (int) y, mousex, mousey);
                poseStack.popPose();
            }
            lastX = x;
            lastY = y;
            lastAngle = angle;
        }
    }

    private void renderCraftingComponent(PoseStack poseStack, IComponentRenderContext context, ISpellIngredient craftingComponent, int sx, int sy, int mousex, int mousey) {
        ArsMagicaAPI.get().getSpellDataManager().getSpellIngredientRenderer(craftingComponent.getType()).renderInGui(craftingComponent, poseStack, sx, sy, mousex, mousey);
        if (context.isAreaHovered(mousex, mousey, sx, sy, 16, 16)) {
            context.setHoverTooltipComponents(craftingComponent.getTooltip());
        }
    }
}
