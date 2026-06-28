package at.minecraftschurli.mods.arsmagicalegacy.client.renderer;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.MagitechGogglesOverlayRenderState;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMRenderPipelines;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.LinkedList;
import java.util.List;

public class MagitechGogglesOverlayRenderStateImpl implements MagitechGogglesOverlayRenderState {
    private final List<Pair<CubeRenderState, Quaternionf>> lines = new LinkedList<>();
    private final List<CubeRenderState> boxes = new LinkedList<>();

    public void clear() {
        lines.clear();
        boxes.clear();
    }

    @Override
    public void extract(BlockEntity blockEntity) {
        ClientLevel level = AMClientUtil.level();
        if (level == null) return;
        BlockPos pos = blockEntity.getBlockPos();
        EtheriumHandler cap = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, pos, null);
        if (cap == null) return;
        BlockState state = level.getBlockState(pos);
        AABB outline = cap.getOutline(level, pos, state);
        int color = cap.getOutlineColor(level, pos, state);
        if (outline != null) {
            extractBox(outline, 0.025f, 0xff000000 | color);
        }
        for (BlockPos connectedPos : cap.getConnectedPositions()) {
            EtheriumHandler connectedCap = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, connectedPos, null);
            int connectedColor = connectedCap == null ? color : AMClientUtil.averageColors(color, connectedCap.getOutlineColor(level, connectedPos, level.getBlockState(connectedPos)));
            extractLine(pos, connectedPos, 0.025f, 0xff000000 | connectedColor);
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void extractLine(BlockPos pos1, BlockPos pos2, float lineWidth, int color) {
        Vec3 vec3 = Vec3.atLowerCornerOf(pos2).subtract(Vec3.atLowerCornerOf(pos1));
        Vector3f vec = vec3.toVector3f().normalize();
        float halfWidth = lineWidth / 2;
        lines.add(Pair.of(
            new CubeRenderState(-halfWidth, -halfWidth, -halfWidth, halfWidth + (float) vec3.length(), halfWidth, halfWidth, (color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff, (color >> 24) & 0xff),
            new Quaternionf().rotateAxis((float) Math.acos(new Vector3f(1, 0, 0).dot(vec)), new Vector3f(1, 0, 0).cross(vec))
        ));
    }

    @Override
    public void extractBox(AABB aabb, float lineWidth, int color) {
        float x1 = (float) aabb.minX;
        float y1 = (float) aabb.minY;
        float z1 = (float) aabb.minZ;
        float x2 = (float) aabb.maxX;
        float y2 = (float) aabb.maxY;
        float z2 = (float) aabb.maxZ;
        float halfWidth = lineWidth / 2;
        float minX1 = x1 - halfWidth;
        float minY1 = y1 - halfWidth;
        float minZ1 = z1 - halfWidth;
        float minX2 = x2 - halfWidth;
        float minY2 = y2 - halfWidth;
        float minZ2 = z2 - halfWidth;
        float maxX1 = x1 + halfWidth;
        float maxY1 = y1 + halfWidth;
        float maxZ1 = z1 + halfWidth;
        float maxX2 = x2 + halfWidth;
        float maxY2 = y2 + halfWidth;
        float maxZ2 = z2 + halfWidth;
        int r = color >> 16 & 0xff;
        int g = color >> 8 & 0xff;
        int b = color & 0xff;
        int a = color >> 24 & 0xff;
        boxes.add(new CubeRenderState(minX1, minY1, minZ1, maxX2, maxY1, maxZ1, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY2, minZ1, maxX2, maxY2, maxZ1, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY1, minZ2, maxX2, maxY1, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY2, minZ2, maxX2, maxY2, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY1, minZ1, maxX1, maxY2, maxZ1, r, g, b, a));
        boxes.add(new CubeRenderState(minX2, minY1, minZ1, maxX2, maxY2, maxZ1, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY1, minZ2, maxX1, maxY2, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX2, minY1, minZ2, maxX2, maxY2, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY1, minZ1, maxX1, maxY1, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX2, minY1, minZ1, maxX2, maxY1, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX1, minY2, minZ1, maxX1, maxY2, maxZ2, r, g, b, a));
        boxes.add(new CubeRenderState(minX2, minY2, minZ1, maxX2, maxY2, maxZ2, r, g, b, a));
    }

    @Override
    public void submit(PoseStack stack, SubmitNodeCollector collector) {
        stack.pushPose();
        stack.translate(0.5, 0.5, 0.5);
        for (Pair<CubeRenderState, Quaternionf> line : lines) {
            stack.pushPose();
            stack.mulPose(line.getSecond());
            collector.submitCustomGeometry(stack, AMRenderPipelines.MAGITECH_GOGGLES_TYPE, new Renderer(line.getFirst()));
            stack.popPose();
        }
        stack.popPose();
        for (CubeRenderState box : boxes) {
            collector.submitCustomGeometry(stack, AMRenderPipelines.MAGITECH_GOGGLES_TYPE, new Renderer(box));
        }
    }

    private record CubeRenderState(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int red, int green, int blue, int alpha) {
    }

    private record Renderer(CubeRenderState state) implements SubmitNodeCollector.CustomGeometryRenderer {
        @Override
        public void render(PoseStack.Pose pose, VertexConsumer buffer) {
            float minX = state.minX;
            float minY = state.minY;
            float minZ = state.minZ;
            float maxX = state.maxX;
            float maxY = state.maxY;
            float maxZ = state.maxZ;
            int r = state.red;
            int g = state.green;
            int b = state.blue;
            int a = state.alpha;
            Matrix4f m = pose.pose();
            // left
            vertex(buffer, m, minX, minY, minZ, r, g, b, a);
            vertex(buffer, m, minX, minY, maxZ, r, g, b, a);
            vertex(buffer, m, minX, maxY, maxZ, r, g, b, a);
            vertex(buffer, m, minX, maxY, minZ, r, g, b, a);
            // right
            vertex(buffer, m, maxX, maxY, minZ, r, g, b, a);
            vertex(buffer, m, maxX, maxY, maxZ, r, g, b, a);
            vertex(buffer, m, maxX, minY, maxZ, r, g, b, a);
            vertex(buffer, m, maxX, minY, minZ, r, g, b, a);
            // bottom
            vertex(buffer, m, minX, minY, minZ, r, g, b, a);
            vertex(buffer, m, maxX, minY, minZ, r, g, b, a);
            vertex(buffer, m, maxX, minY, maxZ, r, g, b, a);
            vertex(buffer, m, minX, minY, maxZ, r, g, b, a);
            // top
            vertex(buffer, m, minX, maxY, maxZ, r, g, b, a);
            vertex(buffer, m, maxX, maxY, maxZ, r, g, b, a);
            vertex(buffer, m, maxX, maxY, minZ, r, g, b, a);
            vertex(buffer, m, minX, maxY, minZ, r, g, b, a);
            // back
            vertex(buffer, m, minX, minY, maxZ, r, g, b, a);
            vertex(buffer, m, maxX, minY, maxZ, r, g, b, a);
            vertex(buffer, m, maxX, maxY, maxZ, r, g, b, a);
            vertex(buffer, m, minX, maxY, maxZ, r, g, b, a);
            // front
            vertex(buffer, m, minX, maxY, minZ, r, g, b, a);
            vertex(buffer, m, maxX, maxY, minZ, r, g, b, a);
            vertex(buffer, m, maxX, minY, minZ, r, g, b, a);
            vertex(buffer, m, minX, minY, minZ, r, g, b, a);
        }

        private static void vertex(VertexConsumer buffer, Matrix4f m, float x, float y, float z, int r, int g, int b, int a) {
            buffer.addVertex(m, x, y, z).setColor(r, g, b, a).setUv(0, 0);
        }
    }
}
