package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.block;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.AltarCoreBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.AltarCoreBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AltarCoreRenderer extends AbstractEtheriumBlockEntityRenderer<AltarCoreBlockEntity, AltarCoreRenderer.State> {
    private static final ItemStackTemplate BARRIER = new ItemStackTemplate(Items.BARRIER);
    private final Font font;
    private final ItemModelResolver itemModelResolver;

    public AltarCoreRenderer(BlockEntityRendererProvider.Context context) {
        font = context.font();
        itemModelResolver = context.itemModelResolver();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(AltarCoreBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        state.disabled = false;
        state.itemDisabled = false;
        Level level = blockEntity.getLevel();
        BlockPos lecternPos = blockEntity.getLecternPos();
        SpellIngredient ingredient = blockEntity.getCurrentIngredient();
        if (!blockEntity.getBlockState().getValue(AltarCoreBlock.FORMED) || level == null || lecternPos == null) {
            state.disabled = true;
            return;
        }
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        BlockState lectern = level.getBlockState(lecternPos);
        if (!lectern.is(Blocks.LECTERN) || !lectern.getValue(LecternBlock.HAS_BOOK)) {
            state.itemDisabled = true;
            return;
        }
        BlockPos pos = blockEntity.getBlockPos();
        state.translateX = lecternPos.getX() - pos.getX() + 0.5;
        state.translateY = lecternPos.getY() - pos.getY() + 1.5;
        state.translateZ = lecternPos.getZ() - pos.getZ() + 0.5;
        List<Component> components = blockEntity.hasRecipe() && ingredient != null ? ingredient.tooltip(level) : List.of(AMTranslations.ALTAR_CORE_LOW_POWER);
        int lineHeight = font.lineHeight + 1;
        float offset = lineHeight * (components.size() - 1.5f);
        state.strings.clear();
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            state.strings.add(new FormattedCharSequenceXY(component.getVisualOrderText(), -font.width(component) / 2f, lineHeight * i - offset));
        }
        state.light = LevelRenderer.getLightCoords(level, lecternPos.above());
        state.backgroundColor = (int) (AMClientUtil.mc().options.getBackgroundOpacity(0.25f) * 255) << 24;
        state.rotation = Axis.YP.rotationDegrees(level.getGameTime() % 360 + partialTicks);
        itemModelResolver.updateForTopItem(state.item, blockEntity.hasRecipe() && ingredient != null ? Objects.requireNonNull(AMUtil.getByTick(ingredient.asItemStacks(), (int) (level.getGameTime() / 20))).copyWithCount(1) : BARRIER.create(), ItemDisplayContext.FIXED, level, null, (int) pos.asLong());
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (state.disabled) return;
        super.submit(state, poseStack, submitNodeCollector, camera);
        if (state.itemDisabled) return;
        poseStack.pushPose();
        poseStack.translate(state.translateX, state.translateY, state.translateZ);
        poseStack.pushPose();
        poseStack.translate(0, 0.9, 0);
        poseStack.mulPose(camera.orientation);
        poseStack.scale(0.025f, -0.025f, 0.025f);
        for (FormattedCharSequenceXY component : state.strings) {
            submitNodeCollector.submitText(poseStack, component.x(), component.y(), component.string(), false, Font.DisplayMode.SEE_THROUGH, state.light, 0xbbffffff, state.backgroundColor, 0);
            submitNodeCollector.submitText(poseStack, component.x(), component.y(), component.string(), false, Font.DisplayMode.NORMAL, state.light, 0xffffffff, 0, 0);
        }
        poseStack.popPose();
        poseStack.pushPose();
        poseStack.mulPose(state.rotation);
        poseStack.translate(0, 0.15, 0);
        poseStack.scale(0.65f, 0.65f, 0.65f);
        state.item.submit(poseStack, submitNodeCollector, state.light, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(AltarCoreBlockEntity blockEntity) {
        return AABB.INFINITE;
    }

    @NullUnmarked
    public static class State extends AbstractEtheriumBlockEntityRenderer.RenderState {
        public boolean disabled = false;
        public boolean itemDisabled = false;
        public double translateX;
        public double translateY;
        public double translateZ;
        public List<FormattedCharSequenceXY> strings = new ArrayList<>();
        public int light;
        public int backgroundColor;
        public Quaternionf rotation;
        public ItemStackRenderState item = new ItemStackRenderState();
    }

    public record FormattedCharSequenceXY(FormattedCharSequence string, float x, float y) {
    }
}
