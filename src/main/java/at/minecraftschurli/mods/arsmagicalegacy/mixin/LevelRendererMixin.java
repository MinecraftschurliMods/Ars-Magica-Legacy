package at.minecraftschurli.mods.arsmagicalegacy.mixin;

import at.minecraftschurli.mods.arsmagicalegacy.item.EnderBootsItem;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @ModifyVariable(method = "renderLevel", at = @At("HEAD"), name = "modelViewMatrix", argsOnly = true)
    private Matrix4fc modifyRenderingModelViewMatrix(Matrix4fc modelViewMatrix) {
        LocalPlayer player = AMClientUtil.player();
        return player != null && EnderBootsItem.isActive(player) ? modelViewMatrix.rotateX((float) Math.PI, new Matrix4f()) : modelViewMatrix;
    }

    @ModifyArg(method = "extractLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;prepareChunkRenders(Lorg/joml/Matrix4fc;)Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;"))
    private Matrix4fc modifyExtractingModelViewMatrix(Matrix4fc modelViewMatrix) {
        LocalPlayer player = AMClientUtil.player();
        return player != null && EnderBootsItem.isActive(player) ? modelViewMatrix.rotateX((float) Math.PI, new Matrix4f()) : modelViewMatrix;
    }
}
