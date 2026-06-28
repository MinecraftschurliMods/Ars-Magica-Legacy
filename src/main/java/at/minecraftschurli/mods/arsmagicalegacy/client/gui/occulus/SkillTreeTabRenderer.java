package at.minecraftschurli.mods.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.mods.arsmagicalegacy.client.gui.ColoredFloatRectangleRenderState;
import at.minecraftschurli.mods.arsmagicalegacy.packet.LearnSkillPacket;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class SkillTreeTabRenderer extends OcculusTabRenderer {
    private static final Component MISSING = Component.translatable(AMTranslations.OCCULUS_MISSING_KEY).withStyle(ChatFormatting.DARK_RED);
    private static final int SKILL_SIZE = 32;
    private final List<Skill> skills;
    private double offsetX;
    private double offsetY;
    @Nullable
    private Skill hoveredSkill;

    public SkillTreeTabRenderer(Holder<OcculusTab> occulusTab) {
        super(occulusTab);
        offsetX = Math.max(0, occulusTab.value().startX());
        offsetY = Math.max(0, occulusTab.value().startY());
        skills = AMRegistries.skills(true)
            .stream()
            .filter(skill -> skill.tab().getKey() == occulusTab.getKey())
            .toList();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        MagicHelper helper = ArsMagicaApi.magicHelper();
        Registry<Skill> registry = AMRegistries.skills(true);
        LocalPlayer player = Objects.requireNonNull(AMClientUtil.player());
        mouseX += (int) offsetX;
        mouseY += (int) offsetY;
        hoveredSkill = null;
        Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        stack.translate((float) -offsetX, (float) -offsetY);
        for (Skill skill : skills) {
            float endX = skill.x() + SKILL_SIZE / 2f;
            float endY = skill.y() + SKILL_SIZE / 2f;
            boolean knowsSkill = helper.knows(player, registry.wrapAsHolder(skill));
            if (skill.hidden() && !knowsSkill) continue;
            for (Holder<Skill> holder : skill.parents()) {
                Skill parent = holder.value();
                float startX = parent.x() + SKILL_SIZE / 2f;
                float startY = parent.y() + SKILL_SIZE / 2f;
                boolean knowsParent = helper.knows(player, holder);
                int startColor = knowsParent && knowsSkill ? 0xffffffff : knowsParent ? getColorForSkill(parent) : 0xff000000;
                int endColor = knowsParent && knowsSkill ? 0xffffffff : knowsParent ? getColorForSkill(skill) : 0xff000000;
                fillLine(graphics, startX, startY, endX, endY, startColor, endColor, 1);
            }
        }
        float tick = 0.75f + ((player.tickCount % 80) >= 40 ? (player.tickCount % 40) / 80f - 0.25f : 0.25f - (player.tickCount % 40) / 80f);
        for (Skill skill : skills) {
            Holder<Skill> holder = registry.wrapAsHolder(skill);
            int c = -1;
            if (!helper.knows(player, holder)) {
                if (skill.hidden()) continue;
                if (!helper.canLearn(player, holder)) {
                    c = ARGB.colorFromFloat(1, 0.5f, 0.5f, 0.5f);
                } else {
                    int color = getColorForSkill(skill);
                    float red = Math.max(AMClientUtil.getRedF(color), 0.75f) * tick;
                    float green = Math.max(AMClientUtil.getGreenF(color), 0.75f) * tick;
                    float blue = Math.max(AMClientUtil.getBlueF(color), 0.75f) * tick;
                    c = ARGB.colorFromFloat(1, red, green, blue);
                }
            }
            AMClientUtil.blit(graphics, SkillAtlasHolder.getSprite(skill), skill.x(), skill.y(), SKILL_SIZE, SKILL_SIZE, c);
            if (mouseX >= skill.x() && mouseX <= skill.x() + SKILL_SIZE && mouseY >= skill.y() && mouseY <= skill.y() + SKILL_SIZE) {
                hoveredSkill = skill;
            }
        }
        stack.popMatrix();
    }

    @Override
    public void renderTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (hoveredSkill == null) return;
        MagicHelper helper = ArsMagicaApi.magicHelper();
        LocalPlayer player = Objects.requireNonNull(AMClientUtil.player());
        Registry<Skill> registry = AMRegistries.skills(true);
        Holder<Skill> holder = registry.wrapAsHolder(hoveredSkill);
        graphics.tooltip(AMClientUtil.font(), List.of(
            ClientTooltipComponent.create(Skill.getName(holder).withColor(getColorForSkill(hoveredSkill)).getVisualOrderText()),
            ClientTooltipComponent.create((helper.knows(player, holder) || helper.canLearn(player, holder) ? Skill.getDescription(holder).withStyle(ChatFormatting.DARK_GRAY) : MISSING).getVisualOrderText())
        ), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE, null);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() != 0 || !(event.x() > 0) || !(event.x() < TAB_SIZE) || !(event.y() > 0) || !(event.y() < TAB_SIZE))
            return super.mouseClicked(event, doubleClick);
        if (hoveredSkill != null) {
            Holder<Skill> holder = AMRegistries.skills(true).wrapAsHolder(hoveredSkill);
            LocalPlayer player = Objects.requireNonNull(AMClientUtil.player());
            if (ArsMagicaApi.magicHelper().canLearn(player, holder) || player.isCreative()) {
                ClientPacketDistributor.sendToServer(new LearnSkillPacket(holder));
                return true;
            }
        }
        setDragging(true);
        return true;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        offsetX = Math.clamp(offsetX - dx, 0, occulusTab.value().width() - TAB_SIZE);
        offsetY = Math.clamp(offsetY - dy, 0, occulusTab.value().height() - TAB_SIZE);
        return true;
    }

    public static void fillLine(GuiGraphicsExtractor graphics, float startX, float startY, float endX, float endY, int startColor, int endColor, float lineWidth) {
        Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        stack.translate(startX, startY);
        Vec2 vec = new Vec2(endX - startX, endY - startY);
        float angle = (float) Math.acos(new Vec2(0, 1).dot(vec.normalized()));
        stack.rotate(vec.x > 0 ? -angle : angle);
        stack.translate(-lineWidth / 2f, 0);
        graphics.submitGuiElementRenderState(new ColoredFloatRectangleRenderState(RenderPipelines.GUI, TextureSetup.noTexture(), new Matrix3x2f(stack), 0, 0, lineWidth, vec.length(), startColor, endColor, graphics.peekScissorStack()));
        stack.popMatrix();
    }

    private static int getColorForSkill(Skill skill) {
        return skill.cost().map(Holder::value).map(SkillPoint::color).orElse(0xcccccc) | 0xff000000;
    }
}
