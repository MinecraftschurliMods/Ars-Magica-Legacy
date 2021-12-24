package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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
        //        RenderSystem.enableBlend();
        //        context.getGui().getMinecraft().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaAPI.MODID, "textures/gui/arcane_compendium_gui_extras.png"));
        //        context.getGui().setBlitOffset(context.getGui().getBlitOffset()+1);
        //        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        //        RenderUtil.drawTexturedModalRectClassic(x + 42, y + 15, 112, 145, 60, 40, 40, 40, context.getGui().getBlitOffset());
        //        RenderUtil.drawTexturedModalRectClassic(x, y , 112, 175, 60, 40, 40, 40, context.getGui().getBlitOffset());
        //        context.getGui().setBlitOffset(context.getGui().getBlitOffset()-1);
        //        RenderSystem.disableBlend();
        //List<ISpellModifier> modifiers = cacheModifiers();
        //if (modifiers.isEmpty()) cy -= 16;
        //else cy += ((modifiers.size() / 7) * 16) + 8;
        renderRecipe(poseStack, context, cx, cy, mouseX, mouseY);
        RenderSystem.enableBlend();
        ISkill skill = ArsMagicaAPI.get().getSkillManager().get(this._part.getRegistryName());
        TextureAtlasSprite sprite = SkillIconAtlas.instance().getSprite(skill.getId());
        RenderSystem.setShaderTexture(0, sprite.atlas().location());
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        GuiComponent.blit(poseStack, cx - 2, cy - 2, context.getGui().getBlitOffset(), 20, 20, sprite);
        if (context.isAreaHovered(mouseX, mouseY, cx - 2, cy - 2, 20, 20)) {
            context.setHoverTooltipComponents(Stream.of(skill.getDisplayName(), skill.getDescription()).map(Component::copy).map(mutableComponent -> mutableComponent.setStyle(context.getFont())).map(Component.class::cast).toList());
        }
        // renderModifiers(context, x, y, mouseX, mouseY, modifiers);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> unaryOperator) {

    }

    private void renderRecipe(PoseStack poseStack, IComponentRenderContext context, int cx, int cy, int mousex, int mousey) {
        if (_part == null) return;
        List<ISpellIngredient> recipe = ArsMagicaAPI.get().getSpellDataManager().getDataForPart(_part).recipe();
        if (recipe.isEmpty()) return;
        final float angleStep = 360.0f / recipe.size();
        final float dist = 45;
        float lastAngle = (angleStep * (recipe.size() - 1) + (context.getTicksInBook() * 0.5f)) % 360f;
        float lastX = (float) (cx - Math.cos(Math.toRadians(lastAngle)) * dist);
        float lastY = (float) (cy - Math.sin(Math.toRadians(lastAngle)) * dist);
        for (int i = 0; i <= recipe.size(); i++) {
            float angle = (lastAngle + angleStep) % 360f;
            float x = (float) (cx - (Math.cos(Math.toRadians(angle)) * dist));
            float y = (float) (cy - (Math.sin(Math.toRadians(angle)) * dist));
            RenderUtil.line2d(poseStack, x + 8, y + 8, cx + 8, cy + 8, context.getGui().getBlitOffset(), 0x0000DD);
            RenderUtil.gradientLine2d(poseStack, lastX + 8, lastY + 8, x + 8, y + 8, context.getGui().getBlitOffset(), 0x0000DD, 0xDD00DD);
            if (i < recipe.size()) {
                renderCraftingComponent(poseStack, context, recipe.get(i), (int) x, (int) y, mousex, mousey);
            }
            lastX = x;
            lastY = y;
            lastAngle = angle;
        }
    }

    private void renderCraftingComponent(PoseStack poseStack, IComponentRenderContext context, ISpellIngredient craftingComponent, int sx, int sy, int mousex, int mousey) {
        ArsMagicaAPI.get().getSpellDataManager().getSpellIngredientRenderer(craftingComponent.getType()).renderInGui(craftingComponent, poseStack, sx, sy, mousex, mousey);
        if (context.isAreaHovered(mousex, mousey, sx, sy, 16, 16)) {
            context.setHoverTooltipComponents(List.of(craftingComponent.getTooltip()));
        }
    }
}
